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
import androidx.room.*
import com.adityaamolbavadekar.messenger.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {

    /*[START] ACCOUNT*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM accounts_table WHERE UID LIKE :uid LIMIT 1")
    fun getLiveUser(uid: String): LiveData<User?>

    @Query("SELECT * FROM accounts_table WHERE UID LIKE :uid LIMIT 1")
    fun getUser(uid: String): User

    @Query("DELETE FROM accounts_table WHERE UID LIKE :uid")
    fun deleteUser(uid: String)

    @Query("DELETE FROM accounts_table")
    fun deleteAllUsers()

    /*[END] ACCOUNT*/


    /*[START] CONVERSATIONS*/

    @Insert
    suspend fun insertConversation(conversationRecord: ConversationRecord)

    @Update
    suspend fun updateConversation(conversationRecord: ConversationRecord)

    @Query("SELECT * FROM conversation_table ORDER BY updated DESC")
    fun getConversations(): Flow<List<ConversationRecord>>

    @Query("SELECT * FROM conversation_table WHERE conversationId LIKE :conversationId LIMIT 1")
    fun getConversation(conversationId: String): LiveData<ConversationRecord>

    @Delete
    fun deleteConversation(conversationRecord: ConversationRecord)

    @Query("DELETE FROM conversation_table WHERE conversationId LIKE :conversationId")
    fun deleteConversation(conversationId: String)

    @Query("DELETE FROM conversation_table")
    fun deleteAllConversations()

    /*[END] CONVERSATIONS*/


    /*[START] MESSAGES*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageRecord: MessageRecord)

    @Update
    suspend fun updateMessage(messageRecord: MessageRecord)

    @Query("SELECT * FROM messages_table WHERE conversationId LIKE :conversationIdentifier ORDER BY timestamp DESC")
    fun getMessages(conversationIdentifier: String): LiveData<List<MessageRecord>>

    @Query("SELECT * FROM messages_table WHERE conversationId LIKE :conversationIdentifier ORDER BY timestamp DESC")
    fun getMessagesAsFlow(conversationIdentifier: String): Flow<List<MessageRecord>>

    @Query("SELECT * FROM messages_table WHERE id LIKE :messageId")
    fun getMessage(messageId: String): MessageRecord

    @Query("DELETE FROM messages_table WHERE conversationId LIKE :conversationIdentifier")
    fun deleteMessages(conversationIdentifier: String)

    @Query("DELETE FROM messages_table")
    fun deleteAllMessages()

    /*[END] MESSAGES*/


    /*[START] REACTIONS*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReaction(reactionRecord: ReactionRecord)

    @Update
    suspend fun updateReaction(reactionRecord: ReactionRecord)

    @Query("DELETE FROM reactions_table WHERE reactionId LIKE :id")
    fun deleteReaction(id: Int)

    /*[END] REACTIONS*/


    /*[START] RECIPIENTS*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipient(recipient: Recipient)

    @Update
    suspend fun updateRecipient(recipient: Recipient)

    @Query("SELECT * FROM recipients_table ORDER BY tempName ASC")
    fun getRecipients(): Flow<List<Recipient>>

    @Query("SELECT * FROM recipients_table WHERE uid LIKE :recipientUid")
    fun getRecipientWithUid(recipientUid: String): LiveData<Recipient>

    @Delete
    fun deleteRecipient(recipient: Recipient)

    @Query("DELETE FROM recipients_table WHERE uid LIKE :recipientUid")
    fun deleteRecipientWithUid(recipientUid: String)

    @Query("DELETE FROM recipients_table")
    fun deleteAllRecipients()
    /*[END] RECIPIENTS*/

    /*[START] DRAFTS*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draftMessage: ConversationDraftMessage)

    @Update
    suspend fun updateDraft(draftMessage: ConversationDraftMessage)

    @Query("SELECT * FROM message_drafts_table WHERE conversationId LIKE :conversationId")
    suspend fun getDraftForConversationId(conversationId: String): ConversationDraftMessage?

    @Query("DELETE FROM message_drafts_table WHERE conversationId LIKE :conversationId")
    fun deleteDraftForConversationId(conversationId: String)

    @Query("DELETE FROM message_drafts_table")
    fun deleteAllDrafts()

    /*[END] DRAFTS*/


    /*RELATIONS*/

    /*[START] Recipients and Conversations*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversationRecordRecipientCrossRef(conversationRecordRecipientCrossRef: ConversationRecordRecipientCrossRef)

    @Query("DELETE FROM ConversationRecordRecipientCrossRef WHERE conversationId LIKE :conversationId")
    fun deleteConversationRecordRecipientCrossRefForConversation(conversationId: String)

    @Delete
    fun deleteConversationRecordRecipientCrossRef(ref: ConversationRecordRecipientCrossRef)

    @Query("DELETE FROM ConversationRecordRecipientCrossRef WHERE uid LIKE :recipientUid")
    fun deleteConversationRecordRecipientCrossRefForUid(recipientUid: String)

    @Transaction
    @Query("SELECT * FROM conversation_table WHERE conversationId LIKE :conversationId LIMIT 1")
    fun getRecipientsOfConversationRecord(conversationId: String): LiveData<ConversationWithRecipients>

    @Transaction
    @Query("SELECT * FROM conversation_table WHERE conversationId LIKE :conversationId LIMIT 1")
    fun getRecipientsOfConversationRecordAsFlow(conversationId: String): Flow<ConversationWithRecipients>

    @Transaction
    @Query("SELECT * FROM recipients_table WHERE uid LIKE :uid LIMIT 1")
    fun getConversationRecordsOfRecipient(uid: String): LiveData<RecipientWithConversations>

    @Transaction
    @Query("SELECT * FROM recipients_table WHERE uid LIKE :uid LIMIT 1")
    suspend fun directGetConversationsOfRecipient(uid: String): RecipientWithConversations?
    /*[END] Recipients and Conversations*/

    /*RELATIONS*/

    /*LocalAttachments*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalAttachment(localAttachment: LocalAttachment)

    @Query("SELECT * FROM local_attachments_table WHERE correspondingMessageId LIKE :attachmentId")
    fun getLocalAttachment(attachmentId: String): LocalAttachment


}