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

@Entity(tableName = "messages_table")
data class MessageRecord(
    @PrimaryKey(autoGenerate = false)
    override val id: String = Id.get(),
    override val conversationId: String="",
    override var message: String? = null,
    override val attachments: List<String> = listOf(),
    override var reactions: MutableList<ReactionRecord> = mutableListOf(),
    override val timestamp: Long = System.currentTimeMillis(),
    override var isDeleted: Boolean = false,
    override var deletionTimestamp: Long = 0,
    override var deliveryStatus: Int = DeliveryStatus.NOT_SENT,
    override val senderUid: String = "",
    override val senderUsername: String = "",
    override var mentions: List<String> = listOf(),
    override var viewedBy: List<String> = listOf(),
    override var linkPreviewInfo: LinkPreviewInfo? = null,
    override var messageReplyRecord: MessageReplyRecord? = null,
    override var type: Int = RecyclerViewType.TYPE_ITEM,
) : BaseMessage(
    id,
    conversationId,
    message,
    attachments,
    reactions,
    timestamp,
    isDeleted,
    deletionTimestamp,
    deliveryStatus,
    senderUid,
    senderUsername,
    mentions,
    viewedBy,
    linkPreviewInfo,
    messageReplyRecord,
    type
) {

    companion object {

        fun from(
            sender: Recipient,
            conversationId: String,
            text: String?,
            urls: List<String> = mutableListOf(),
            reactions: MutableList<ReactionRecord> = mutableListOf()
        ): MessageRecord {
            return MessageRecord(
                conversationId = conversationId,
                message = text,
                attachments = urls,
                reactions = reactions,
                senderUid = sender.uid,
                senderUsername = sender.username,
                viewedBy = listOf(sender.uid)
            )
        }

        fun timestampHeader(time: Long, conversationId: String): MessageRecord {
            return MessageRecord(
                conversationId = conversationId,
                timestamp = time,
                deliveryStatus = DeliveryStatus.UNKNOWN,
                senderUid = "",
                senderUsername = "",
                type = RecyclerViewType.TYPE_TIMESTAMP_HEADER
            )
        }

        fun header(
            time: Long,
            text: String,
            conversationId: String,
        ): MessageRecord {
            return MessageRecord(
                conversationId = conversationId,
                message = text,
                timestamp = time,
                deliveryStatus = DeliveryStatus.UNKNOWN,
                senderUid = "",
                senderUsername = "",
                type = RecyclerViewType.TYPE_HEADER
            )
        }

        fun from(
            sender: Recipient,
            conversationId: String,
            text: String,
            url: String?,
            reactions: MutableList<ReactionRecord> = mutableListOf()
        ): MessageRecord {
            return MessageRecord(
                conversationId = conversationId,
                message = text.trim(),
                attachments = if (url == null) listOf() else listOf(url),
                reactions = reactions,
                senderUid = sender.uid,
                senderUsername = sender.username,
            )
        }

        fun createCopyOf(
            messageRecord: MessageRecord,
            senderUid: String,
            senderUsername: String,
            timestamp: Long
        ): MessageRecord {
            return MessageRecord(
                conversationId = messageRecord.conversationId,
                message = messageRecord.message,
                attachments = messageRecord.attachments,
                reactions = messageRecord.reactions,
                timestamp = timestamp,
                isDeleted = messageRecord.isDeleted,
                deliveryStatus = messageRecord.deliveryStatus,
                senderUid = senderUid,
                senderUsername = senderUsername,
            )
        }

    }

}