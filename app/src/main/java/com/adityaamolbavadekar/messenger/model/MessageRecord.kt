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

package com.adityaamolbavadekar.messenger.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages_table")
data class MessageRecord(
    @PrimaryKey(autoGenerate = false)
    override val id: String = Id.get(),
    override var conversationId: String = "",
    override var message: String? = null,
    override var attachments: List<String> = listOf(),
    override var documentAttachment: Attachment? = null,
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
    documentAttachment,
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

    class Builder(private val conversationId: String) {
        private var id: String = Id.get()
        private var message: String? = null
        private var attachments: List<String> = listOf()
        private var documentAttachment: Attachment? = null
        private var reactions: MutableList<ReactionRecord> = mutableListOf()
        private var timestamp: Long = System.currentTimeMillis()
        private var isDeleted: Boolean = false
        private var deletionTimestamp: Long = 0
        private var deliveryStatus: Int = DeliveryStatus.NOT_SENT
        private var senderUid: String = ""
        private var senderUsername: String = ""
        private var mentions: List<String> = listOf()
        private var viewedBy: List<String> = listOf()
        private var linkPreviewInfo: LinkPreviewInfo? = null
        private var messageReplyRecord: MessageReplyRecord? = null
        private var type: Int = RecyclerViewType.TYPE_ITEM

        fun setId(id:String): Builder {
            this.id = id
            return this
        }

        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun setPhotoAttachments(list: List<String>): Builder {
            this.attachments = list
            return this
        }

        fun setDocumentAttachment(attachment: Attachment): Builder {
            this.documentAttachment = attachment
            return this
        }

        fun setReactions(list: List<ReactionRecord>): Builder {
            this.reactions = list.toMutableList()
            return this
        }

        fun setTimestamp(time: Long): Builder {
            this.timestamp = time
            return this
        }

        fun setTimestampToNow(): Builder {
            this.timestamp = System.currentTimeMillis()
            return this
        }

        fun setDeleted(boolean: Boolean, time: Long): Builder {
            this.isDeleted = boolean
            this.deletionTimestamp = time
            return this
        }

        fun setDeliveryStatus(deliveryStatus: Int): Builder {
            this.deliveryStatus = deliveryStatus
            return this
        }

        fun withStatusNotSent(): Builder {
            this.deliveryStatus = DeliveryStatus.NOT_SENT
            return this
        }

        fun withStatusSent(): Builder {
            this.deliveryStatus = DeliveryStatus.SENT
            return this
        }

        fun withStatusRead(): Builder {
            this.deliveryStatus = DeliveryStatus.READ
            return this
        }

        fun setSender(sender: Recipient): Builder {
            this.senderUid = sender.uid
            this.senderUsername = sender.username
            this.viewedBy = listOf(sender.uid)
            return this
        }

        fun setSender(uid: String, username: String): Builder {
            this.senderUid = uid
            this.senderUsername = username
            this.viewedBy = listOf(uid)
            return this
        }

        fun setLinkPreviewInfo(linkPreviewInfo: LinkPreviewInfo): Builder {
            this.linkPreviewInfo = linkPreviewInfo
            return this
        }

        fun setReplyInfo(reply: MessageReplyRecord): Builder {
            this.messageReplyRecord = reply
            return this
        }

        fun build(): MessageRecord {
            return MessageRecord(
                id = id,
                conversationId = conversationId,
                message = message,
                attachments = attachments,
                documentAttachment = documentAttachment,
                reactions = reactions,
                timestamp = timestamp,
                isDeleted = isDeleted,
                deletionTimestamp = deletionTimestamp,
                deliveryStatus = deliveryStatus,
                senderUid = senderUid,
                senderUsername = senderUsername,
                mentions = mentions,
                viewedBy = viewedBy,
                linkPreviewInfo = linkPreviewInfo,
                messageReplyRecord = messageReplyRecord,
                type = type
            )
        }


    }

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

        fun from(
            sender: Recipient,
            conversationId: String,
            attachment: Attachment
        ): MessageRecord {
            return MessageRecord(
                conversationId = conversationId,
                message = null,
                documentAttachment = attachment,
                reactions = mutableListOf(),
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