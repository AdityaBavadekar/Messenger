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

package com.adityaamolbavadekar.messenger.ui.conversation

import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MessageSender {

    private val cloudDatabaseManager = CloudDatabaseManager()
    private val messagesManager = cloudDatabaseManager.getMessagesManager()
    private var lastMessage: MessageRecord? = null
    private val conversationsAdded = mutableListOf<String>()

    fun sendP2PMessage(
        message: MessageRecord,
        receiverUid: String,
        responseCallback: (Boolean) -> Unit
    ) = runOnIOThread {
        lastMessage = message
        savePerson2PersonMessage(message, receiverUid, responseCallback)
    }

    fun sendSelfMessage(
        message: MessageRecord,
        responseCallback: (Boolean) -> Unit
    ) =
        runOnIOThread {
            lastMessage = message
            saveMessageFromSelf(message, responseCallback)
        }

    fun sendGroupMessage(
        message: MessageRecord,
        responseCallback: (Boolean) -> Unit
    ) = runOnIOThread {
        lastMessage = message
        saveMessageToGroup(message, message.conversationId, responseCallback)
    }

    private fun savePerson2PersonMessage(
        m: MessageRecord,
        receiverUid: String,
        responseCallback: (Boolean) -> Unit
    ) {
        //Before sending message change it's status to sent
        m.markAsSent()
        InternalLogger.logD(TAG, "Saving message to sendersCollection")
        messagesManager.saveMessageToDatabase(
            m,
            uid = m.senderUid,
            uidB = receiverUid
        ) {}
        InternalLogger.logD(TAG, "Saving message to receiverCollection")
        messagesManager.saveMessageToDatabase(
            m,
            uid = receiverUid,
            uidB = m.senderUid,
            responseCallback
        )

        if (!conversationsAdded.contains(receiverUid)) {
            Firebase.database.getReference(Constants.CloudPaths.getUserConversationsPath(m.senderUid))
                .child(receiverUid)
                .setValue(false)
            Firebase.database.getReference(Constants.CloudPaths.getUserConversationsPath(receiverUid))
                .child(m.senderUid)
                .setValue(false)
            conversationsAdded.add(receiverUid)
        }

    }

    private fun saveMessageFromSelf(
        m: MessageRecord,
        responseCallback: (Boolean) -> Unit
    ) {
        //Before sending message change it's status to sent
        m.markAsSent()
        InternalLogger.logD(TAG, "Saving message to sendersCollection")
        messagesManager.saveMessageToDatabase(
            m,
            uid = m.senderUid,
            uidB = m.senderUid,
            responseCallback
        )
        if (!conversationsAdded.contains(m.senderUid)) {
            Firebase.database.getReference(Constants.CloudPaths.getUserConversationsPath(m.senderUid))
                .child(m.senderUid)
                .setValue(false)
                .addOnSuccessListener {
                    conversationsAdded.add(m.senderUid)
                }
        }
    }

    private fun saveMessageToGroup(
        m: MessageRecord,
        groupId: String,
        responseCallback: (Boolean) -> Unit
    ) {
        //Before sending message change it's status to sent
        m.markAsSent()
        InternalLogger.logD(TAG, "Saving message to Group")
        messagesManager.saveMessageToGroupDatabase(m, groupId, responseCallback)
    }

    fun updateMessage(message: MessageRecord, conversation: ConversationRecord) {
        when {
            conversation.isGroup -> {
                sendGroupMessage(message) { }
            }
            conversation.isP2P -> {
                sendP2PMessage(
                    message,
                    conversation.p2PRecipientUid()
                ) { }
            }
            conversation.isSelf -> {
                sendSelfMessage(message) { }
            }
        }
    }

    companion object {
        private val TAG = MessageSender::class.java.simpleName
    }

}