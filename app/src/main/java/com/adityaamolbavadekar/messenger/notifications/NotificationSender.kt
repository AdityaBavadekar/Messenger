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

package com.adityaamolbavadekar.messenger.notifications

import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.gson.Gson

object NotificationSender {

    private val TAG = NotificationSender::class.java.simpleName
    private fun sendNotification(notificationData: NotificationData, toFcmToken: String) =
        runOnIOThread {
            try {
                val notification =
                    PushNotification(notificationData, toFcmToken)
                val response = RetrofitInstance.api.postNotification(notification)

                runOnIOThread {
                    if (response.isSuccessful) {
                        InternalLogger.logI(TAG, "Success Response : true")
                        InternalLogger.logI(TAG, "Success Response : ${Gson().toJson(response)}")
                    } else {
                        InternalLogger.logE(TAG, "Response Error ")
                        InternalLogger.logE(
                            TAG,
                            "Response Error : " + response.errorBody()?.string()
                        )
                    }
                }

            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Exception : Unable to send notification", e)
            }
        }

    fun sendNotification(
        data: NotificationData, recipients: List<Recipient>
    ) {
        val fcmTokens = mutableListOf<String>()
        recipients.forEach { fcmTokens.addAll(it.fcmTokens) }
        InternalLogger.logI(TAG,"Sending notification to ${fcmTokens.size} Tokens.")
        fcmTokens.forEach { token -> sendNotification(data, token) }
    }

}
