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

package com.adityaamolbavadekar.messenger.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.model.*
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource

class CreateGroupViewModel : ViewModel() {

    private val _recipients: MutableLiveData<List<Recipient>> = MutableLiveData(mutableListOf())
    val recipients: LiveData<List<Recipient>> = _recipients
    private val authManager = AuthManager()
    private lateinit var me: Recipient
    var messagingPermissionType = MessagingPermissionType.permitAll()

    fun createNewGroup(
        conversationId: String,
        database: DatabaseAndroidViewModel,
        title: String,
        description: String,
        photoUrl: String? = null
    ): Task<ConversationRecord> {
        val source = TaskCompletionSource<ConversationRecord>()
        val recipientsList = recipients.value!!.toMutableList()
        if (!recipientsList.contains(me)) {
            recipientsList.add(me)
        }
        val conversation = ConversationRecord
            .newGroup(
                title,
                recipientsList.toUidList(),
                authManager.uid,
                photoUrl,
                description = description,
                conversationId = conversationId,
                messagingPermissionType = messagingPermissionType
            )
        val groupCreationMessage =
            MessageRecord.header(
                conversation.created,
                "You created group " + "\"${conversation.title}\"",
                conversationId
            )
        database.insertConversation(conversation, recipientsList)
        database.insertOrUpdateMessage(groupCreationMessage)
        CloudDatabaseManager().Conversations()
            .createGroupConversation(conversation.toRemoteConversation())
            .addOnSuccessListener { source.setResult(conversation) }
            .addOnFailureListener { source.setException(it) }
        return source.task
    }

    fun setMeRecipient(meRecipient: Recipient) {
        me = meRecipient
    }

    fun onItemGroupContactsPickerItemClicked(item: Recipient) {
        InternalLogger.logD(TAG, "onItemGroupContactsPickerItemClicked : $item")
        val list: MutableList<Recipient> = _recipients.value!!.toMutableList()
        if (list.contains(item)) {
            list.remove(item)
        } else {
            list.add(item)
        }
        _recipients.postValue(list)
    }

    fun onItemCreateNewGroupItemClicked(item: Recipient) {
        val list: MutableList<Recipient> = recipients.value!!.toMutableList()
        if (list.contains(item)) {
            list.remove(item)
        }
        _recipients.postValue(list)
    }

    companion object {
        private val TAG = CreateGroupViewModel::class.java.simpleName
    }

}
