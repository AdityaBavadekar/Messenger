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

package com.adityaamolbavadekar.messenger.database.conversations

import androidx.lifecycle.*
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DatabaseAndroidViewModel(dao: ConversationDao) : ViewModel() {

    private val repo: ApplicationDatabaseRepository = ApplicationDatabaseRepository(dao)
    private val authManager: AuthManager = AuthManager()

    companion object {
        val DEFAULT_DISPATCHER = Dispatchers.IO
    }

    fun getConversations(): Flow<List<ConversationRecord>> {
        return repo.getConversations()
    }

    fun getRecipients(): Flow<List<Recipient>> {
        return repo.getRecipients()
    }

    fun getAccount(uid: String): User {
        return repo.getAccount(uid)
    }

    fun getLoggedInAccount(): User {
        return repo.getAccount(authManager.uid)
    }

    fun getLiveDataLoggedInAccount(): LiveData<User?> {
        return repo.getLiveAccount(authManager.uid)
    }

    private fun insertOrUpdateAccount(user: User) = viewModelScope.launch(DEFAULT_DISPATCHER) {
        repo.addAccount(user)
    }

    fun addLoggedInAccount(user: User) {
        insertOrUpdateAccount(user)
        insertOrUpdateRecipient(Recipient.Builder(user).build())
    }

    fun deleteAccount(uid: String) = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.deleteAccount(uid)
    }

    fun deleteDefaultAccount() = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.deleteAccount(authManager.uid)
    }

    fun deleteAllAccounts() = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.deleteAllAccounts()
    }

    /*[START] Conversation*/
    private fun internalInsertConversation(conversationRecord: ConversationRecord) =
        viewModelScope.launch(
            DEFAULT_DISPATCHER
        ) {
            repo.insertConversation(conversationRecord)
        }

    fun insertConversation(conversationRecord: ConversationRecord, recipients: List<Recipient>) =
        viewModelScope.launch(
            DEFAULT_DISPATCHER
        ) {
            var conversationRecipients = recipients
            if (conversationRecord.isSelf) {
                conversationRecipients = listOf(recipients.first())
            }
            internalInsertConversation(conversationRecord)
            conversationRecipients.forEach {
                insertOrUpdateRecipient(it)
                insertOrUpdateConversationRecipientCrossRef(
                    ConversationRecordRecipientCrossRef(
                        conversationRecord.conversationId,
                        it.uid
                    )
                )
            }
        }

    fun getConversation(conversationId: String): LiveData<ConversationRecord> {
        return repo.getConversation(conversationId)
    }

    fun updateConversation(conversationRecord: ConversationRecord) = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.updateConversation(conversationRecord)
    }

    private fun internalDeleteConversation(conversationId: String) {
        repo.deleteConversation(conversationId)
    }

    fun deleteConversation(conversationId: String) = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        internalDeleteConversation(conversationId)
        deleteConversationRecordRecipientCrossRefForConversation(conversationId)
    }
    /*[END] Conversation*/

    /*[START] Message*/
    fun getAllMessages(): LiveData<List<MessageRecord>> {
        return repo.getAllMessages()
    }

    fun getMessages(conversationId: String): LiveData<List<MessageRecord>> {
        return repo.getMessages(conversationId)
    }

    fun insertOrUpdateMessage(messageRecord: MessageRecord) = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.insertMessage(messageRecord)
    }

    fun insertOrUpdateMessages(messageRecords: List<MessageRecord>) = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        messageRecords.forEach { repo.insertMessage(it) }
    }

    /*[END] Message*/

    /*[START] Recipient*/
    fun insertOrUpdateRecipient(recipient: Recipient) = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.insertRecipient(recipient)
    }

    fun getRecipient(uid: String): LiveData<Recipient> {
        return repo.getRecipient(uid)
    }

    fun deleteRecipient(recipient: Recipient) = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.deleteRecipient(recipient)
    }

    fun deleteRecipient(recipientUid: String) = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.deleteRecipientWithUid(recipientUid)
    }
    /*[END] Recipient*/

    /*[START] Drafts*/

    fun insertOrUpdateDraft(draftMessage: ConversationDraftMessage) =
        viewModelScope.launch(DEFAULT_DISPATCHER) {
            repo.insertDraft(draftMessage)
        }

    fun getDraftForConversationId(
        conversationId: String,
        listener: (ConversationDraftMessage?) -> Unit
    ) = viewModelScope.launch(DEFAULT_DISPATCHER) {
        listener(repo.getDraftForConversationId(conversationId))
    }

    fun deleteDraftForConversationId(conversationId: String) = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.deleteDraftForConversationId(conversationId)
    }

    fun deleteAllDrafts() = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.deleteAllDrafts()
    }

    /*[END] Drafts*/


    fun deleteAll() = viewModelScope.launch(
        DEFAULT_DISPATCHER
    ) {
        repo.deleteAll()
    }

    private fun insertOrUpdateConversationRecipientCrossRef(ref: ConversationRecordRecipientCrossRef) =
        viewModelScope.launch(
            DEFAULT_DISPATCHER
        ) {
            repo.insertConversationRecipientCrossRef(ref)
        }

    private fun deleteConversationRecordRecipientCrossRefForConversation(conversationId: String) =
        viewModelScope.launch(
            DEFAULT_DISPATCHER
        ) {
            repo.deleteConversationRecordRecipientCrossRefForConversation(conversationId)
        }

    private fun deleteConversationRecordRecipientCrossRef(ref: ConversationRecordRecipientCrossRef) =
        viewModelScope.launch(
            DEFAULT_DISPATCHER
        ) {
            repo.deleteConversationRecordRecipientCrossRef(ref)
        }

    fun deleteConversationRecordRecipientCrossRefForUid(recipientUid: String) =
        viewModelScope.launch(
            DEFAULT_DISPATCHER
        ) {
            repo.deleteConversationRecordRecipientCrossRefForUid(recipientUid)
        }

    fun getRecipientsOfConversation(conversationId: String): LiveData<ConversationWithRecipients> {
        return repo.getRecipientsOfConversation(conversationId)
    }

    fun getConversationsOfRecipient(uid: String): LiveData<RecipientWithConversations> {
        return repo.getConversationsOfRecipients(uid)
    }

    fun directGetConversationsOfRecipient(
        uid: String,
        listener: (RecipientWithConversations?) -> Unit
    ): Job =
        viewModelScope.launch(DEFAULT_DISPATCHER) {
            listener(repo.directGetConversationsOfRecipient(uid))
        }

    fun insertRecipients(recipientsList: List<Recipient>) =
        viewModelScope.launch(DEFAULT_DISPATCHER) {
            recipientsList.forEach { repo.insertRecipient(it) }
        }

    fun deleteConversationAndMessages(record: ConversationRecord): Job =
        viewModelScope.launch(DEFAULT_DISPATCHER) {
            record.recipientUids.forEach { uid ->
                deleteConversationRecordRecipientCrossRef(
                    ConversationRecordRecipientCrossRef(
                        record.conversationId,
                        uid
                    )
                )
            }
            deleteConversation(record.conversationId)
            deleteMessages(record.conversationId)
        }

    private fun deleteMessages(conversationId: String) {
        repo.deleteMessages(conversationId)
    }


    class Factory(private val database: ApplicationDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DatabaseAndroidViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DatabaseAndroidViewModel(database.dao()) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}