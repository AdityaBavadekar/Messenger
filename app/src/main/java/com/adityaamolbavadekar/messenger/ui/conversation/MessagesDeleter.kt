package com.adityaamolbavadekar.messenger.ui.conversation

import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.utils.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MessagesDeleter {

    fun deleteForEveryoneP2P(p2pRecipientUid:String,message:MessageRecord): MutableList<Boolean> {
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

    fun deleteForEveryoneGroup(message:MessageRecord): Task<Void> {
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
