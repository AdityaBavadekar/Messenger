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

import java.util.*

abstract class BaseConversation constructor(
    open val conversationId: String = UUID.randomUUID().toString(),

    /* Title for conversation
    * For Person2PersonConversation this is same as sender's name.
    *  */
    open var title: String = "",

    /*Url of Photo for Group or Person2Person recipient.*/
    open var photoUrl: String? = null,

    /*About*/
    open var description: String? = null,

    /*Path for conversation*/
    open val databasePath: String? = null,

    /* Creation Timestamp */
    open val created: Long = 0,

    /* Last Updated */
    open var updated: Long = 0,

    /* List of Recipient uids*/
    open var recipientUids: MutableList<String> = mutableListOf(),

    /* List of RemoteRecipients*/
    open var recipientsInfo: MutableList<HashMap<String, Any?>> = mutableListOf(),

    /* The person who created this conversation */
    open val creatorUid: String = "",

    /*Whether a Recipient allowed to message*/
    open var messagingPermissionType: Int = MessagingPermissionType.permitAll(),

    /*Whether a Recipient allowed to edit conversation information*/
    open var editingPermissionType: Int = EditingPermissionType.permitAll(),

    ) {

    fun p2PRecipientUid(): String {
        return recipientUids.last()
    }

    fun remoteRecipients(): MutableList<RemoteRecipient> {
        val remoteRecipients = mutableListOf<RemoteRecipient>()
        recipientsInfo.forEach { remoteRecipients.add(RemoteRecipient.fromHashMap(it)) }
        return remoteRecipients
    }

    fun hasPicture(): Boolean {
        return (photoUrl != null)
    }

    fun removeRecipient(r: Recipient) {
        recipientUids.remove(r.uid)
        recipientsInfo.forEach {
            if (it["uid"] == r.uid) {
                recipientsInfo.remove(it)
                return
            }
        }
    }

    fun isManager(uid: String): Boolean {
        remoteRecipients().forEach { remoteRecipient ->
            if (remoteRecipient.uid == uid) {
                return remoteRecipient.isManager()
            }
        }
        return false
    }

    fun isMessagingRestrictedForUser(uid: String): Boolean {
        return if (messagingPermissionType == MessagingPermissionType.permitManagersOnly()) {
            !isManager(uid)
        } else false
    }

    fun isEditingingRestrictedForUser(uid: String): Boolean {
        return if (editingPermissionType == EditingPermissionType.permitManagersOnly()) {
            !isManager(uid)
        } else false
    }

}
