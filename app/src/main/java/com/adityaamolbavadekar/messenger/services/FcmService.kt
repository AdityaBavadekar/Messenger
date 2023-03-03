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

package com.adityaamolbavadekar.messenger.services

import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.notifications.NotificationData
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {

    private var prefsManager: PrefsManager = PrefsManager(this)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        InternalLogger.logD(TAG, "onNewToken")
        prefsManager.saveFcmToken(token)
    }

    override fun onMessageReceived(m: RemoteMessage) {
        super.onMessageReceived(m)
        InternalLogger.logD(TAG, "onMessageReceived : ${m.toIntent()}")
        NotificationData.fromData(m.data)?.let {
            InternalLogger.logD(TAG, "NotificationData : \n$it")
            //NotificationCompat.Builder(this)
        }
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        InternalLogger.logD(TAG, "onMessageSent")
    }

    companion object {
        private val TAG = FcmService::class.java.simpleName
    }

}