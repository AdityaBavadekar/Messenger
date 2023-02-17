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

package com.adityaamolbavadekar.messenger.ui.conversation_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.model.valueOf
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import kotlinx.coroutines.launch

class ConversationListViewModel : ViewModel(),
    OnResponseCallback<List<CloudDatabaseManager.ConversationResponse>, Exception> {

    private val cloudDatabaseManager = CloudDatabaseManager()
    private val authManager = AuthManager()
    private var isLoading = false
    private val _conversations: MutableLiveData<List<ConversationRecord>> =
        MutableLiveData(mutableListOf())
    val conversations: LiveData<List<ConversationRecord>> = _conversations
    private val _recipients: MutableLiveData<List<Recipient>> =
        MutableLiveData(mutableListOf())
    val recipients: LiveData<List<Recipient>> = _recipients
    private lateinit var database: DatabaseAndroidViewModel

    override fun onSuccess(t: List<CloudDatabaseManager.ConversationResponse>) {
        InternalLogger.debugInfo(TAG, "ConversationsList : $t")
        val remoteConversations = mutableListOf<ConversationRecord>()
        t.forEach {
            if (it.isGroup) {
                cloudDatabaseManager.Conversations().getGroupConversation(it.id)
                    .addOnSuccessListener { c -> remoteConversations.add(c) }
            } else {/*
            cloudDatabaseManager.Conversations().getP2PConversation(it.id)
                .addOnSuccessListener {r-> }*/
            }
        }
        val rs = recipients.value!!
        remoteConversations.forEach {
            val conversationRecipients = mutableListOf<Recipient>()
            it.recipientUids.forEach { uid -> conversationRecipients.add(rs.valueOf(uid)!!) }
            database.insertConversation(it, conversationRecipients)
        }
    }

    override fun onFailure(e: Exception) {
        InternalLogger.logD(TAG, "ConversationsList : Failure :$e")
    }

    fun load(me: Recipient) {
        if (isLoading) return
        cloudDatabaseManager.Conversations().observeConversations(authManager.uid, this)
        isLoading = true
    }

    fun refreshList() {
        val list = _conversations.value
        val newList = mutableListOf<ConversationRecord>()
        list!!.forEach {
            if (!it.temp) newList.add(it)
        }
        _conversations.postValue(newList.sortedBy { it.lastMessageTimestamp })
    }

    fun setDatabase(d: DatabaseAndroidViewModel) {
        this.database = d
        viewModelScope.launch {
            database.getConversations().collect { updateConversations(it) }
            database.getRecipients().collect { updateRecipients(it) }
        }
    }

    private fun updateConversations(conversations: List<ConversationRecord>) {
        _conversations.postValue(conversations)
    }

    private fun updateRecipients(list: List<Recipient>) {
        _recipients.postValue(list)
    }

    companion object {
        private val TAG = ConversationListViewModel::class.java.simpleName
    }

}