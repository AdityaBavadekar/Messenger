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

package com.adityaamolbavadekar.messenger.ui.conversation

import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.utils.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MessagesDeleter {

    fun deleteForEveryoneP2P(p2pRecipientUid:String,message: MessageRecord): MutableList<Boolean> {
        val completion = mutableListOf(false,false)
        Firebase.database
            .getReference(Constants.CloudPaths.getP2PMessagesPath(p2pRecipientUid,message.senderUid) + "/${message.id}")
            .setValue(message.delete())
            .addOnSuccessListener { completion[0] = true }
            .addOnFailureListener { completion[0] = false }
        Firebase.database
            .getReference(Constants.CloudPaths.getP2PMessagesPath(message.senderUid,p2pRecipientUid) + "/${message.id}")
            .setValue(message.delete())
            .addOnSuccessListener { completion[1] = true }
            .addOnFailureListener { completion[1] = false }
        return completion
    }

    fun deleteForEveryoneGroup(message: MessageRecord): Task<Void> {
        return Firebase.database
            .getReference(Constants.CloudPaths.getGroupMessagesPath(message.conversationId) + "/${message.id}")
            .setValue(message.delete())
    }

    /**
     * For now only supported for P2P Conversations.
     * */
    fun deleteForMe(p2pRecipientUid: String, message: MessageRecord): Task<Void> {
        return Firebase.database
            .getReference(
                Constants.CloudPaths.getP2PMessagesPath(
                    message.senderUid,p2pRecipientUid
                ) + "/${message.id}"
            )
            .setValue(message.delete())
    }

}
