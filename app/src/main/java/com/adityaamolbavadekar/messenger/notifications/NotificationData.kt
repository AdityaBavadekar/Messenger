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

import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.utils.Constants

data class NotificationData(
    val hasNotificationData: String = "true",
    val title: String,
    val message: String,
    val timestamp: String = "0",
    var senderName: String = "",
    var senderUid: String = "",
    var messageId: String = "",
    var conversationUrl: String = "",
    var conversationId: String = "",
    var messagePath: String = "",
    var isP2P: String = "false",
    var isGroup: String = "true"
) {

    companion object {
        fun newP2PNotification(
            m: MessageRecord,
            receiverUid: String,
            senderName: String,
            senderPhotoUrl: String? = null
        ): NotificationData {
            val messagePath =
                Constants.CloudPaths.getP2PMessagesPath(receiverUid, m.senderUid) + "/" + m.id
            m.apply {
                return NotificationData(
                    "true",
                    senderName,
                    message?:"",
                    timestamp.toString(),
                    senderName,
                    senderUid,
                    id,
                    senderPhotoUrl ?: "",
                    conversationId,
                    messagePath,
                    "true",
                    "false"
                )
            }
        }

        fun newGroupNotification(
            m: MessageRecord,
            senderName: String,
            groupName: String,
            groupPhotoUrl: String? = null
        ): NotificationData {
            val messagePath =
                Constants.CloudPaths.getGroupMessagesPath(m.conversationId) + "/" + m.id
            m.apply {
                return NotificationData(
                    "true",
                    groupName,
                    message?:"",
                    timestamp.toString(),
                    senderName,
                    senderUid,
                    id,
                    groupPhotoUrl ?: "",
                    conversationId,
                    messagePath,
                    "false",
                    "true"
                )
            }
        }

        fun fromData(data: MutableMap<String, String>): NotificationData? {
            if (data["hasNotificationData"] == null) {
                return null
            }
            val notificationData =
                NotificationData("true", data["title"]!!, data["message"]!!, data["timestamp"]!!)
            if (data.containsKey("senderUid")) {
                notificationData.senderUid = data["senderUid"]!!
            }
            if (data.containsKey("senderName")) {
                notificationData.senderName = data["senderName"]!!
            }
            if (data.containsKey("messageId")) {
                notificationData.messageId = data["messageId"]!!
            }
            if (data.containsKey("conversationUrl")) {
                notificationData.conversationUrl = data["conversationUrl"]!!
            }
            if (data.containsKey("conversationId")) {
                notificationData.conversationId = data["conversationId"]!!
            }
            if (data.containsKey("messagePath")) {
                notificationData.messagePath = data["messagePath"]!!
            }
            if (data.containsKey("isP2P")) {
                notificationData.isP2P = data["isP2P"]!!
            }
            if (data.containsKey("isGroup")) {
                notificationData.isGroup = data["isGroup"]!!
            }
            return notificationData
        }

    }

}
