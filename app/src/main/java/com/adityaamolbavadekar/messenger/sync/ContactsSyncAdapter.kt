/*
 *
 *    Copyright 2022 Aditya Bavadekar
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.adityaamolbavadekar.messenger.sync

import android.accounts.Account
import android.content.*
import android.os.Bundle
import android.text.format.DateUtils
import com.adityaamolbavadekar.messenger.contact.ContactsReader
import com.adityaamolbavadekar.messenger.database.conversations.ApplicationDatabase
import com.adityaamolbavadekar.messenger.database.conversations.ApplicationDatabaseRepository
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.model.User
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.Permissions
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class ContactsSyncAdapter @JvmOverloads constructor(
    context: Context,
    autoInitialize: Boolean,
    allowParallelSyncs: Boolean = false,
    val mContentResolver: ContentResolver = context.contentResolver
) : AbstractThreadedSyncAdapter(context, autoInitialize, allowParallelSyncs) {

    private val repo: ApplicationDatabaseRepository
    private val cloudDatabaseManager = CloudDatabaseManager()
    private val authManager = AuthManager()
    private val prefsManager = PrefsManager(context)

    init {
        val dao = ApplicationDatabase.get(context).dao()
        repo = ApplicationDatabaseRepository(dao)
    }

    private val onGetUserInfoCallback = object : OnResponseCallback<User, Exception?> {
        override fun onSuccess(t: User) {
            InternalLogger.logD(TAG, "Sync Success.")
            runOnIOThread { repo.addAccount(t) }
            prefsManager.saveUserObject(t)
        }

        override fun onFailure(e: Exception?) {
            InternalLogger.logE(TAG, "Sync Failure.", e)
        }
    }


    override fun onPerformSync(
        account: Account?,
        extras: Bundle?,
        authority: String?,
        provider: ContentProviderClient?,
        syncResult: SyncResult?
    ) {
        InternalLogger.logD(
            TAG,
            "onPerformSync(account=[${account ?: "null"}]\nauthority=[${authority ?: "null"}])"
        )
        // Return if user is not logged in or if Contacts Sync is not allowed
        if (!authManager.isLoggedIn || prefsManager.isContactsSyncAllowed() != Constants.CONTACTS_SYNC_ALLOWED) return

        cloudDatabaseManager.Users().getUser(authManager.uid, onGetUserInfoCallback)

        val lastUploaded = prefsManager.getLastContactsUploaded()
        if (lastUploaded == Constants.UNKNOWN_VALUE_LONG || !DateUtils.isToday(lastUploaded)
        ) {
            Permissions.ReadContacts.doIfGranted(context) {
                ContactsReader(context).read { list ->
                    if (list.isNotEmpty()) {
                        prefsManager.saveLastContactsUploaded(System.currentTimeMillis())
                        cloudDatabaseManager.Contacts()
                            .save(list.map { contactInfo -> contactInfo.number })
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "ContactsSyncAdapter"
    }

}