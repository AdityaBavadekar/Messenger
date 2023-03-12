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

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.adityaamolbavadekar.messenger.model.*
import kotlinx.coroutines.flow.Flow

class ApplicationDatabaseRepository(private val dao: ConversationDao) {


    fun getAccount(uid: String): User {
        return dao.getUser(uid)
    }

    fun getLiveAccount(uid: String): LiveData<User?> {
        return dao.getLiveUser(uid)
    }

    suspend fun updateAccount(user: User) {
        dao.updateUser(user)
    }

    suspend fun addAccount(user: User) {
        dao.insertUser(user)
    }

    fun deleteAccount(uid: String) {
        dao.deleteUser(uid)
    }

    fun deleteAllAccounts() {
        dao.deleteAllUsers()
    }

    /*[START] Conversation*/
    suspend fun insertConversation(conversationRecord: ConversationRecord) {
        dao.insertConversation(conversationRecord)
    }

    suspend fun updateConversation(conversationRecord: ConversationRecord) {
        dao.updateConversation(conversationRecord)
    }

    fun deleteConversation(conversationId: String) {
        dao.deleteConversation(conversationId)
    }

    fun getConversation(conversationId: String): LiveData<ConversationRecord> {
        return dao.getConversation(conversationId)
    }
    /*[END] Conversation*/

    /*[START] Message*/
    suspend fun insertMessage(messageRecord: MessageRecord) {
        dao.insertMessage(messageRecord)
    }

    suspend fun updateMessage(messageRecord: MessageRecord) {
        dao.updateMessage(messageRecord)
    }

    fun getMessages(conversationId: String): LiveData<List<MessageRecord>> {
        return dao.getMessages(conversationId)
    }

    fun getMessagesAsFlow(conversationId: String): Flow<List<MessageRecord>> {
        return dao.getMessagesAsFlow(conversationId)
    }

    fun deleteMessages(conversationId: String) {
        dao.deleteMessages(conversationId)
    }

    fun getAllMessages(conversationId:String,config:PagedList.Config): LiveData<PagedList<MessageRecord>> {
        val factory = dao.getMessagesListPaged(conversationId)
        return LivePagedListBuilder(factory,config)
            .build()
    }
    /*[END] Message*/

    /*[START] Recipient*/
    suspend fun insertRecipient(recipient: Recipient) {
        dao.insertRecipient(recipient)
    }

    suspend fun updateRecipient(recipient: Recipient) {
        dao.updateRecipient(recipient)
    }

    fun getConversations(): Flow<List<ConversationRecord>> {
        return dao.getConversations()
    }

    fun getRecipients(): Flow<List<Recipient>> {
        return dao.getRecipients()
    }

    fun deleteRecipient(recipient: Recipient) {
        dao.deleteRecipient(recipient)
    }

    fun deleteRecipientWithUid(recipientUid: String) {
        dao.deleteRecipientWithUid(recipientUid)
    }
    /*[END] Recipient*/

    /*[START] Drafts*/

    suspend fun insertDraft(draftMessage: ConversationDraftMessage) {
        dao.insertDraft(draftMessage)
    }

    suspend fun updateDraft(draftMessage: ConversationDraftMessage) {
        dao.updateDraft(draftMessage)
    }

    suspend fun getDraftForConversationId(conversationId: String): ConversationDraftMessage? {
        return dao.getDraftForConversationId(conversationId)
    }

    fun deleteDraftForConversationId(conversationId: String) {
        dao.deleteDraftForConversationId(conversationId)
    }

    fun deleteAllDrafts() {
        dao.deleteAllDrafts()
    }

    /*[END] Drafts*/

    fun deleteAll() {
        dao.deleteAllUsers()
        dao.deleteAllConversations()
        dao.deleteAllMessages()
        dao.deleteAllRecipients()
    }

    suspend fun insertConversationRecipientCrossRef(ref: ConversationRecordRecipientCrossRef) {
        dao.insertConversationRecordRecipientCrossRef(ref)
    }

    fun deleteConversationRecordRecipientCrossRefForConversation(conversationId: String) {
        dao.deleteConversationRecordRecipientCrossRefForConversation(conversationId)
    }

    fun deleteConversationRecordRecipientCrossRef(ref: ConversationRecordRecipientCrossRef) {
        dao.deleteConversationRecordRecipientCrossRef(ref)
    }

    fun deleteConversationRecordRecipientCrossRefForUid(recipientUid: String) {
        dao.deleteConversationRecordRecipientCrossRefForUid(recipientUid)
    }

    fun getRecipientsOfConversation(conversationId: String): LiveData<ConversationWithRecipients> {
        return dao.getRecipientsOfConversationRecord(conversationId)
    }

    fun getRecipientsOfConversationAsFlow(conversationId: String): Flow<ConversationWithRecipients> {
        return dao.getRecipientsOfConversationRecordAsFlow(conversationId)
    }

    fun getConversationsOfRecipients(uid: String): LiveData<RecipientWithConversations> {
        return dao.getConversationRecordsOfRecipient(uid)
    }

    suspend fun directGetConversationsOfRecipient(uid: String): RecipientWithConversations? {
        return dao.directGetConversationsOfRecipient(uid)
    }

    fun getRecipient(uid: String): LiveData<Recipient> {
        return dao.getRecipientWithUid(uid)
    }

}