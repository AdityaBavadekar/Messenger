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

package com.adityaamolbavadekar.messenger.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "message_replies_table")
data class MessageReplyRecord(
    val correspondingConversationId: String,
    @PrimaryKey(autoGenerate = false)
    val correspondingMessageId: String,
    val senderUid: String,
    val message: String?,
    val timestamp: Long,
    val attachment: String?,
) : RemoteDatabasePersitable {

    override fun hashMap(): HashMap<String, Any?> {
        return hashMapOf(
            Fields.CONVERSATION_ID to correspondingConversationId,
            Fields.MESSAGE_ID to correspondingMessageId,
            Fields.SENDER_UID to senderUid,
            Fields.MESSAGE to message,
            Fields.TIMESTAMP to timestamp,
            Fields.ATTACHMENT to attachment,
        )
    }

    private annotation class Fields {
        companion object {
            const val CONVERSATION_ID = "correspondingConversationId"
            const val MESSAGE_ID = "correspondingMessageId"
            const val SENDER_UID = "senderUid"
            const val MESSAGE = "message"
            const val TIMESTAMP = "timestamp"
            const val ATTACHMENT = "attachment"
        }
    }

    companion object {

        fun new(messageRecord: MessageRecord): MessageReplyRecord {
            return MessageReplyRecord(
                messageRecord.conversationId,
                messageRecord.id,
                messageRecord.senderUid,
                messageRecord.message,
                messageRecord.timestamp,
                messageRecord.attachments.firstOrNull()
            )
        }

    }

}
