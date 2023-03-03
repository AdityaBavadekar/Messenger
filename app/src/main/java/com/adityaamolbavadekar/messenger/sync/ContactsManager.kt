/*
 *    Copyright 2023 Aditya Bavadekar
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
 */

package com.adityaamolbavadekar.messenger.sync

import android.accounts.Account
import android.content.ContentProviderOperation
import android.provider.ContactsContract
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class ContactsManager {

    companion object {

        fun addContacts(account: Account, displayName: String, uid: String) {

            val operationsList = mutableListOf<ContentProviderOperation>()

            var builder =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, account.name)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, account.type)
                    .withValue(ContactsContract.RawContacts.SYNC1, uid)
            operationsList.add(builder.build())

            builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                    ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID,
                    0
                )
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                    displayName
                )
            operationsList.add(builder.build())


            builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, CONTACTS_MIME_TYPE)
                .withValue(ContactsContract.Data.DATA1, displayName)
                .withValue(ContactsContract.Data.DATA2, "Messenger")
                .withValue(ContactsContract.Data.DATA3, "View contact")
            operationsList.add(builder.build())

            try {

            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Error occurred", e)
            }
        }

        private val TAG = ContactsManager::class.java.simpleName

        private const val CONTACTS_MIME_TYPE =
            "vnd.android.cursor.item/vnd.com.adityaamolbavadekar.messenger.contact"

    }

}