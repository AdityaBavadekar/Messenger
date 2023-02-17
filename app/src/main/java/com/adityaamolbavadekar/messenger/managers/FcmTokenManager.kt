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

package com.adityaamolbavadekar.messenger.managers

import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class FcmTokenManager {
    private val m = Firebase.messaging
    fun instance() {
        subscribe("Testing")
    }

    fun deleteToken() {
        m.deleteToken()
            .addOnSuccessListener {
                InternalLogger.logD(TAG, "Deleted FcmToken")
            }
            .addOnFailureListener {
                InternalLogger.logE(TAG, "Unable to Deleted FcmToken", it)
            }
    }

    private fun subscribe(topicName: String) {
        m.subscribeToTopic(topicName)
            .addOnSuccessListener {
                InternalLogger.logD(TAG, "Topic subscription [$topicName]: true")
            }
            .addOnFailureListener {
                InternalLogger.logE(
                    TAG,
                    "Exception: Topic subscription [$topicName]: false",
                    it
                )
            }
    }

/*
    fun saveFcmToken(context: Context, force: Boolean = false) {
        val prefsManager = PrefsManager(context)
        if (prefsManager.getFcmToken() == null || force) {
            m.token
                .addOnSuccessListener { token ->
                    InternalLogger.logD(TAG, "Notification token $token")
                    prefsManager.saveFcmToken(token)
                    runOnIOThread {
                        val repo = AccountRepository()
                        repo.get { user ->
                            user?.let {
                                user.fcmToken = token
                                repo.update(user)
                            }
                        }
                        AuthManager().getNullableUser()?.let { user ->
                            CloudDatabaseManager().updateUserProfileInfo(
                                hashMapOf("fcmToken" to token),
                                user.uid
                            ) {}
                        }
                    }
                }
                .addOnFailureListener {
                    InternalLogger.logE(TAG, "Unable to get token.", it)
                }
        }
    }
*/

    companion object {
        private val TAG = FcmTokenManager::class.java.simpleName
    }

}