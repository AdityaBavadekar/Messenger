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
import java.util.*

/**
 * Represents Local Database (Room) Level Conversation.
 * */
@Entity(tableName = "conversation_table")
data class ConversationRecord(
    @PrimaryKey(autoGenerate = false)
    override val conversationId: String = UUID.randomUUID().toString(),

    /* Title for conversation
    * For Person2PersonConversation this is same as sender's name.
    *  */
    override var title: String,

    /* LastMessage metadata */
    var lastMessageId: String? = null,
    var lastMessageText: String? = null,
    var lastMessageTimestamp: Long? = null,
    var lastMessageSenderUid: String? = null,

    /*Url of Photo for Group or Person2Person recipient.*/
    override var photoUrl: String? = null,

    /*About*/
    override var description: String? = null,

    /*User-Specific Properties*/
    var archived: Boolean = false,
    var pinned: Boolean = false,
    /* Last scrolled position of user.*/
    var lastScrollPosition: Int = 0,
    /* Quantity of messages user hasn't read yet.*/
    var unreadCount: Int = 0,

    /*Path for conversation*/
    override val databasePath: String? = null,

    /* Creation Timestamp */
    override val created: Long,

    /* Last Updated */
    override var updated: Long,
    /* Weather this conversation is :
    * 1.a group
    * 2.self conversation
    * 3.person-to-person conversation
    * */
    val isGroup: Boolean,
    val isSelf: Boolean,
    val isP2P: Boolean,

    /* The person who created this conversation */
    override val creatorUid: String,

    /* List of Recipient uids*/
    override var recipientUids: MutableList<String>,

    /* HashMap created by [RemoteRecipient] */
    override var recipientsInfo: MutableList<HashMap<String, Any?>>,

    /*Whether a Recipient allowed to message*/
    override var messagingPermissionType: Int = MessagingPermissionType.permitAll(),

    /*Whether a Recipient allowed to edit conversation information*/
    override var editingPermissionType: Int = EditingPermissionType.permitAll(),

    ) :
    BaseConversation(conversationId, title, photoUrl, description, databasePath, created, updated) {

    constructor() : this(
        conversationId = UUID.randomUUID().toString(),
        title = "",
        lastMessageId = null,
        lastMessageText = null,
        lastMessageTimestamp = null,
        lastMessageSenderUid = null,
        photoUrl = null,
        description = null,
        archived = false,
        pinned = false,
        databasePath = null,
        unreadCount = 0,
        created = 0,
        updated = 0,
        lastScrollPosition = 0,
        isGroup = false,
        isSelf = false,
        isP2P = false,
        creatorUid = "",
        recipientUids = mutableListOf<String>(),
        recipientsInfo = mutableListOf<HashMap<String, Any?>>(),
        messagingPermissionType = MessagingPermissionType.permitAll(),
        editingPermissionType = EditingPermissionType.permitAll()
    )

    fun defaultIcon(): Int {
        return when {
            isGroup -> DEFAULT_GROUP_DRAWABLE
            isSelf -> DEFAULT_SELF_DRAWABLE
            else -> DEFAULT_P2P_DRAWABLE
        }
    }

    fun changeLabel(s: String) {
        if (isGroup) title = s
    }

    fun p2PRecipientUid(): String {
        if(!isP2P){
            throw IllegalArgumentException("P2PUid was requested while the conversation was not a P2P.")
        }
        return recipientUids.last()
    }

    fun loadDisplayName(youString: String): String {
        return when {
            isSelf -> youString
            else -> title
        }
    }

    companion object {

        fun newPerson2Person(
            recipient: Recipient,
            loggedInRecipient: Recipient
        ): ConversationRecord {
            if (recipient.uid == loggedInRecipient.uid) {
                return newSelfConversation(loggedInRecipient)
            }
            val recipientInfoHashMap = mutableListOf<RemoteRecipient>()
            recipientInfoHashMap.add(RemoteRecipient.fromRecipient(recipient))
            recipientInfoHashMap.add(RemoteRecipient.fromRecipient(loggedInRecipient))
            val c = ConversationRecord(
                conversationId = UUID.randomUUID().toString(),
                title = recipient.loadName(),
                photoUrl = recipient.photoUrl,
                description = recipient.tempAbout,
                created = System.currentTimeMillis(),
                updated = System.currentTimeMillis(),
                isGroup = false,
                isSelf = false,
                isP2P = true,
                creatorUid = loggedInRecipient.uid,
                recipientUids = mutableListOf(loggedInRecipient.uid, recipient.uid),
                recipientsInfo = recipientInfoHashMap.remoteRecipientToHashMapList(),
            )
            return c
        }

        private fun newSelfConversation(
            me: Recipient
        ): ConversationRecord {
            val recipientInfoHashMap = mutableListOf(RemoteRecipient.newManager(me.uid))
            return ConversationRecord(
                UUID.randomUUID().toString(),
                me.loadName(),
                photoUrl = me.photoUrl,
                databasePath = null,
                created = System.currentTimeMillis(),
                updated = System.currentTimeMillis(),
                recipientUids = mutableListOf(me.uid),
                isGroup = false,
                isSelf = true,
                isP2P = false,
                creatorUid = me.uid,
                recipientsInfo = recipientInfoHashMap.remoteRecipientToHashMapList(),
            )
        }

        fun newGroup(
            label: String,
            recipients: MutableList<String>,
            creatorUid: String,
            url: String? = null,
            conversationId: String = UUID.randomUUID().toString(),
            description: String? = null,
        ): ConversationRecord {
            val desc: String? = if (description == null || description.trim().isEmpty()) {
                null
            } else description
            val recipientInfoHashMap = mutableListOf<RemoteRecipient>()
            recipients.forEach {
                if (it == creatorUid) {
                    recipientInfoHashMap.add(RemoteRecipient.newManager(creatorUid = creatorUid))
                } else {
                    recipientInfoHashMap.add(RemoteRecipient.newParticipant(uid = it, creatorUid))
                }
            }
            return ConversationRecord(
                conversationId = conversationId,
                title = label,
                photoUrl = url,
                description = desc,
                databasePath = null,
                created = System.currentTimeMillis(),
                updated = System.currentTimeMillis(),
                recipientUids = recipients,
                isGroup = true,
                isSelf = false,
                isP2P = false,
                creatorUid = creatorUid,
                recipientsInfo = recipientInfoHashMap.remoteRecipientToHashMapList(),
            )
        }

        private const val SCROLL_POS_UNKNOWN = -1
        val DEFAULT_GROUP_DRAWABLE: Int = R.drawable.ic_group
        val DEFAULT_P2P_DRAWABLE: Int = R.drawable.ic_user_profile_default
        val DEFAULT_SELF_DRAWABLE: Int = R.drawable.ic_self_conversation

    }

    fun updateFrom(remoteConversation: RemoteConversation): ConversationRecord {
        title = remoteConversation.title
        photoUrl = remoteConversation.photoUrl
        description = remoteConversation.description
        updated = remoteConversation.updated
        recipientUids =
            remoteConversation.recipientsInfo.map { it["uid"] as String }.toMutableList()
        recipientsInfo = remoteConversation.recipientsInfo
        return this
    }

    override fun toString(): String {
        return "**CONVERSATION_START**" + "\n" +
                "ConversationId : $conversationId" + "\n" +
                "Title : $title" + "\n" +
                "LastMessageId : $lastMessageId" + "\n" +
                "LastMessageText : $lastMessageText" + "\n" +
                "LastMessageTimestamp : $lastMessageTimestamp" + "\n" +
                "LastMessageSender : $lastMessageSenderUid" + "\n" +
                "PhotoUrl : $photoUrl" + "\n" +
                "Description : $description" + "\n" +
                "Archived : $archived" + "\n" +
                "Pinned : $pinned" + "\n" +
                "DatabasePath : $databasePath" + "\n" +
                "UnreadCount : $unreadCount" + "\n" +
                "Created : $created" + "\n" +
                "Updated : $updated" + "\n" +
                "LastScrolledPos : $lastScrollPosition" + "\n" +
                "Recipients : $recipientsInfo" + "\n" +
                if (isP2P) "P2PRecipient : ${p2PRecipientUid()}" + "\n" else "\n" +
                        "Group : $isGroup" + "\n" +
                        "P2P : $isP2P" + "\n" +
                        "Self : $isSelf" + "\n" +
                        "RecipientsInfo : $recipientsInfo" + "\n" +
                        "MessagingPermissionType : ${
                            MessagingPermissionType.getPermissionString(
                                messagingPermissionType
                            )
                        }" + "\n" +
                        "EditingPermissionType : ${
                            EditingPermissionType.getPermissionString(
                                messagingPermissionType
                            )
                        }" + "\n" +
                        "**CONVERSATION_END**"
    }

    /**
     * Updates itself from provided [MessageRecord].
     * Fields [lastMessageId],[lastMessageText],[lastMessageTimestamp] and [lastMessageSenderUid] are updated.
     * */
    fun updateLastMessageData(m: MessageRecord): ConversationRecord {
        lastMessageId = m.id
        lastMessageText = m.message
        lastMessageTimestamp = m.timestamp
        lastMessageSenderUid = m.senderUid
        if (m.message?.trim()?.isEmpty() == true && m.attachments.isNotEmpty()) {
            lastMessageText = m.attachments.last()
        } else if (m.message?.trim()?.isEmpty() == true && m.reactions.isNotEmpty()) {
            lastMessageText = m.reactions.last().reaction
        }
        return this
    }

    fun toRemoteConversation(): RemoteConversation {
        return RemoteConversation(
            conversationId,
            title,
            photoUrl,
            description,
            databasePath,
            created,
            updated,
            recipientsInfo,
            creatorUid,
            messagingPermissionType,
            editingPermissionType
        )
    }

    enum class ConversationType { GROUP, P2P, SELF }

    fun conversationType(): ConversationType {
        return when {
            isGroup -> ConversationType.GROUP
            isP2P -> ConversationType.P2P
            else -> ConversationType.SELF
        }
    }

}