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

import java.util.*

/**
 * Represents Remote Database Level Conversation that can be fetched/uploaded
 * and later converted to [ConversationRecord].
 * This is always of type GroupConversation.
 * */
data class RemoteConversation(
    override val conversationId: String = UUID.randomUUID().toString(),

    /* Title for conversation
    * For Person2PersonConversation this is same as sender's name.
    *  */
    override var title: String = "",

    /*Url of Photo for Group or Person2Person recipient.*/
    override var photoUrl: String? = null,

    /*About*/
    override var description: String? = null,

    /*Path for conversation*/
    override val databasePath: String? = null,

    /* Creation Timestamp */
    override val created: Long = 0,

    /* Last Updated */
    override var updated: Long = 0,

    /* List of Recipient uids*/
    override var recipientsInfo: MutableList<HashMap<String, Any?>> = mutableListOf(),

    /* The person who created this conversation */
    override val creatorUid: String = "",

    override var messagingPermissionType: Int = MessagingPermissionType.permitAll(),
    override var editingPermissionType: Int = EditingPermissionType.permitAll(),
) : BaseConversation(
    conversationId,
    title,
    photoUrl,
    description,
    databasePath,
    created,
    updated,
    recipientsInfo.map { it["uid"] as String }.toMutableList(),
    recipientsInfo,
    creatorUid, messagingPermissionType, editingPermissionType
) {

    companion object {

        fun newGroup(
            label: String,
            recipients: MutableList<RemoteRecipient>,
            creatorUid: String,
            url: String? = null,
            description: String? = null,
            conversationId: String = UUID.randomUUID().toString(),
        ): RemoteConversation {
            return RemoteConversation(
                conversationId,
                label,
                url,
                description,
                null,
                created = System.currentTimeMillis(),
                updated = System.currentTimeMillis(),
                recipientsInfo = recipients.remoteRecipientToHashMapList(),
                creatorUid = creatorUid,
            )
        }

    }

}