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

abstract class BaseMessage public constructor(
    open val id: String = Id.get(),
    open val conversationId: String,
    open var message: String? = null,
    open var attachments: List<String> = listOf(),
    open var documentAttachment: Attachment? = null,
    open var reactions: MutableList<ReactionRecord> = mutableListOf(),
    open val timestamp: Long = System.currentTimeMillis(),
    open var isDeleted: Boolean = false,
    open var deletionTimestamp: Long = 0,
    open var deliveryStatus: Int = DeliveryStatus.NOT_SENT,
    open val senderUid: String = "",
    open val senderUsername: String = "",
    open var mentions: List<String> = listOf(),
    open var viewedBy: List<String> = listOf(),
    open var linkPreviewInfo: LinkPreviewInfo? = null,
    open var messageReplyRecord: MessageReplyRecord? = null,
    open var type: Int = RecyclerViewType.TYPE_ITEM,
) : RemoteDatabasePersitable {

    override fun hashMap(): HashMap<String, Any?> {
        val map = hashMapOf(
            Fields.ID to id,
            Fields.CONVERSATION_ID to conversationId,
            Fields.MESSAGE to message,
            Fields.ATTACHMENTS to attachments,
            Fields.DOC_ATTACHMENT to documentAttachment?.hashMap(),
            Fields.REACTIONS to reactions.toHashMapList(),
            Fields.TIMESTAMP to timestamp,
            Fields.IS_DELETED to isDeleted,
            Fields.DELETION_TIMESTAMP to deletionTimestamp,
            Fields.DELIVERY_STATUS to deliveryStatus,
            Fields.SENDER_UID to senderUid,
            Fields.SENDER_USERNAME to senderUsername,
            Fields.MENTIONS to mentions,
            Fields.VIEWED_BY to viewedBy,
            Fields.LINK_PREVIEW_INFO to if (linkPreviewInfo == null) null else linkPreviewInfo!!.hashMap(),
            Fields.MESSAGE_REPLY_RECORD to if (messageReplyRecord == null) null else messageReplyRecord!!.hashMap(),
        )
        return map
    }

    private annotation class Fields {
        companion object {
            const val ID = "id"
            const val CONVERSATION_ID = "conversationId"
            const val MESSAGE = "message"
            const val ATTACHMENTS = "attachments"
            const val DOC_ATTACHMENT = "documentAttachment"
            const val REACTIONS = "reactions"
            const val TIMESTAMP = "timestamp"
            const val IS_DELETED = "isDeleted"
            const val DELETION_TIMESTAMP = "deletionTimestamp"
            const val DELIVERY_STATUS = "deliveryStatus"
            const val SENDER_UID = "senderUid"
            const val SENDER_USERNAME = "senderUsername"
            const val MENTIONS = "mentions"
            const val VIEWED_BY = "viewedBy"
            const val LINK_PREVIEW_INFO = "linkPreviewInfo"
            const val MESSAGE_REPLY_RECORD = "messageReplyRecord"
        }
    }

    fun isSender(uid: String): Boolean {
        return senderUid == uid
    }

    fun markAsSent(): BaseMessage {
        deliveryStatus = DeliveryStatus.SENT
        return this
    }

    fun markAsNotSent(): BaseMessage {
        deliveryStatus = DeliveryStatus.NOT_SENT
        return this
    }

    fun markAsRead(uid: String): BaseMessage {
        deliveryStatus = DeliveryStatus.READ
        val newViewedByList = viewedBy.toMutableList()
        newViewedByList.add(uid)
        viewedBy = newViewedByList
        return this
    }

    fun delete(): BaseMessage {
        isDeleted = true
        deletionTimestamp = System.currentTimeMillis()
        viewedBy = listOf()
        mentions = listOf()
        attachments = listOf()
        reactions = mutableListOf()
        linkPreviewInfo = null
        messageReplyRecord = null
        message = null
        return this
    }

    fun hasMessage(): Boolean {
        return message != null && message?.trim()?.isNotEmpty() == true
    }

    fun hasReactions(): Boolean {
        return reactions.isNotEmpty()
    }

    fun hasPhotoAttachments(): Boolean {
        return attachments.isNotEmpty()
    }

    fun hasDocumentAttachments(): Boolean {
        return documentAttachment != null
    }

    fun addReplyMessage(reply: MessageReplyRecord) {
        this.messageReplyRecord = reply
    }

    fun removeReplyMessage() {
        this.messageReplyRecord = null
    }

    fun hasLinkPreview(): Boolean {
        return linkPreviewInfo != null && linkPreviewInfo?.lacksRequiredData() == false
    }

    fun hasReply(): Boolean {
        return messageReplyRecord != null
    }

    fun getReply(): MessageReplyRecord? {
        return messageReplyRecord
    }

    /**
     * Indicates whether this message has its [type] equal to [RecyclerViewType.TYPE_TIMESTAMP_HEADER]
     * */
    fun isTimestampHeader(): Boolean {
        return type == RecyclerViewType.TYPE_TIMESTAMP_HEADER
    }

    fun isTextOnly(): Boolean {
        if (isDeleted) return true
        return (!hasPhotoAttachments() && !hasDocumentAttachments() && !hasLinkPreview() && !hasReply() && hasMessage())
    }

    fun isReadBy(uid: String): Boolean {
        return viewedBy.contains(uid)
    }

    fun setReadBy(uid: String) {
        if (isReadBy(uid)) return
        val newList = viewedBy.toMutableList()
        newList.add(uid)
        this.viewedBy = newList
    }

    fun addLinkInfo(info: LinkPreviewInfo) {
        if (info.lacksRequiredData()) return
        this.linkPreviewInfo = info
    }

    fun getLinkInfo(): LinkPreviewInfo? {
        return linkPreviewInfo
    }

    fun addReaction(reactionRecord: ReactionRecord) {
        reactions.add(reactionRecord)
    }

    override fun equals(other: Any?): Boolean {
        other?.let {
            return if (it.javaClass.simpleName == this.javaClass.simpleName) {
                val m = (it as BaseMessage)
                (m.id == id &&
                        m.isDeleted == isDeleted &&
                        m.conversationId == conversationId &&
                        m.timestamp == timestamp &&
                        m.message == message &&
                        m.senderUid == senderUid &&
                        m.hasReactions() == hasReactions() &&
                        m.deliveryStatus == deliveryStatus &&
                        m.reactions.size == reactions.size && //TODO
                        m.message == message)
            } else false
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + conversationId.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + attachments.hashCode()
        result = 31 * result + documentAttachment.hashCode()
        result = 31 * result + reactions.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + isDeleted.hashCode()
        result = 31 * result + deletionTimestamp.hashCode()
        result = 31 * result + deliveryStatus
        result = 31 * result + senderUid.hashCode()
        result = 31 * result + senderUsername.hashCode()
        result = 31 * result + mentions.hashCode()
        result = 31 * result + viewedBy.hashCode()
        result = 31 * result + (linkPreviewInfo?.hashCode() ?: 0)
        result = 31 * result + (messageReplyRecord?.hashCode() ?: 0)
        result = 31 * result + type
        return result
    }

    fun containsSearchQuery(query: String): Boolean {
        return message?.lowercase()
            ?.contains(query) == true || documentAttachment?.fileName?.lowercase()
            ?.contains(query) == true || senderUsername.contains(query) || linkPreviewInfo?.containsSearchQuery(
            query
        ) == true
    }

}
