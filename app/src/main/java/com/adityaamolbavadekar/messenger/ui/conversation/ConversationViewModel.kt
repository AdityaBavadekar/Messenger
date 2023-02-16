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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.model.*
import com.adityaamolbavadekar.messenger.notifications.NotificationData
import com.adityaamolbavadekar.messenger.notifications.NotificationSender
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.extensions.getDateStub
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import kotlinx.coroutines.Job

class ConversationViewModel : ViewModel() {

    private var lastMessage: MessageRecord? = null
    private var isObservingRemoteDatabase = false
    private lateinit var conversationId: String
    private val _messages: MutableLiveData<List<MessageRecord>> = MutableLiveData(listOf())
    val messages: LiveData<List<MessageRecord>> = _messages
    private val _status: MutableLiveData<Long> = MutableLiveData(PresenceStatus.UNKNOWN)
    val status: LiveData<Long> = _status
    private val _conversationWithRecipients: MutableLiveData<ConversationWithRecipients> =
        MutableLiveData()
    val conversationWithRecipients: LiveData<ConversationWithRecipients> =
        _conversationWithRecipients
    private val _isMessagingRestrictedForMe: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val isMessagingRestrictedForMe: LiveData<Boolean> =
        _isMessagingRestrictedForMe
    private val _conversationPhoto: MutableLiveData<String?> =
        MutableLiveData(null)
    val conversationPhoto: LiveData<String?> = _conversationPhoto
    private val _conversationTitle: MutableLiveData<String> =
        MutableLiveData("")
    val conversationTitle: LiveData<String> = _conversationTitle
    private lateinit var database: DatabaseAndroidViewModel
    private val messageSender = MessageSender()
    private val cloudDatabaseManager = CloudDatabaseManager()
    private val conversationsManager = cloudDatabaseManager.getConversationsManager()
    private val messagesManager = cloudDatabaseManager.getMessagesManager()
    private val statusManager = cloudDatabaseManager.getStatusManager()
    private lateinit var me: Recipient
    private var configured = false
    private val onGetObservedMessagesResponseCallback =
        object : OnResponseCallback<List<MessageRecord>, Exception> {
            override fun onSuccess(t: List<MessageRecord>) {
                createDateHeaders(updateConversationId(t))
            }

            override fun onFailure(e: Exception) {
                InternalLogger.logE(TAG, "Unable observe messages.", e)
            }
        }

    private fun updateConversationId(list: List<MessageRecord>): MutableList<MessageRecord> {
        val newList = mutableListOf<MessageRecord>()
        list.forEach {
            it.conversationId = conversationId
            newList.add(it)
        }
        return newList
    }

    private val onGroupConversationDataChangedCallback: (RemoteConversation) -> Unit =
        { remoteConversation ->
            _isMessagingRestrictedForMe.postValue(
                remoteConversation.isMessagingRestrictedForUser(
                    me.uid
                )
            )
            _conversationPhoto.postValue(remoteConversation.photoUrl)
            _conversationTitle.postValue(remoteConversation.title)
            conversationWithRecipients.value?.let {
                it.conversationRecord.updateFrom(remoteConversation)
                    .also { conversationRecord -> database.updateConversation(conversationRecord) }
            }
        }

    fun executeOnPause() {
        /**
         * Update conversation's lastMessage metadata.
         * */
        conversationWithRecipients.value?.let {
            if (_messages.value!!.isNotEmpty()) {
                InternalLogger.logD(TAG, "Saving last conversation message data.")
                database.updateConversation(
                    it.conversationRecord
                        .updateLastMessageData(_messages.value!!.first())
                )
            }
        }
    }

    fun executeOnDestroy() {
        onShouldRemoveListeners()
        /**
         * If a P2P conversation has zero messages, delete conversation from database.
         * */
        conversationWithRecipients.value?.let {
            if (!it.conversationRecord.isGroup && _messages.value!!.isEmpty() && it.conversationRecord.temp) {
                InternalLogger.logD(TAG, "Deleting empty non-group conversation.")
                database.deleteConversation(conversationId)
            } else {
                val record = it.conversationRecord
                record.temp = false
                database.updateConversation(record)
            }
        }
    }

    private fun onShouldRemoveListeners() {
        /*Remove all listeners*/
        statusManager.updateStatus(System.currentTimeMillis(), me.uid)
        messagesManager.removeListener()
        conversationsManager.removeListener()
        statusManager.removeListener()
    }

    private fun markAsNotSent(m: MessageRecord) = runOnIOThread {
        val l = _messages.value!!.toMutableList()
        val i = l.indexOf(m)
        if (i != -1) {
            m.markAsNotSent()
            l[i] = m
        }
        _messages.postValue(l.toList())
        InternalLogger.logD("MessageStatus", "MarkedAs : [NOT_SENT]")
    }

    private fun marksAsSent(m: MessageRecord) = runOnIOThread {
        val l = _messages.value!!.toMutableList()
        val i = l.indexOf(m)
        if (i != -1) {
            m.markAsSent()
            l[i] = m
        }
        _messages.postValue(l.toList())
        InternalLogger.logD("MessageStatus", "MarkedAs : [SENT]")
    }

    fun configure(
        r: Recipient,
        conversationID: String,
        databaseViewModel: DatabaseAndroidViewModel
    ) {
        if (configured) {
            InternalLogger.logD(TAG, "Already Configured.")
            return
        }
        this.me = r
        this.conversationId = conversationID
        this.database = databaseViewModel
        statusManager.updateStatus(PresenceStatus.ONLINE, me.uid)
        configured = true
    }


    fun configureExtras(
        conversationType: String?,
        p2pUid: String?,
    ) {
        conversationType?.let {
            val type = ConversationRecord.ConversationType.valueOf(it)
            startObservingRemoteData(type, p2pUid)
        }
    }

    fun onLocalConversationDataChanged(data: ConversationWithRecipients) {
        _isMessagingRestrictedForMe.postValue(
            data.conversationRecord.isMessagingRestrictedForUser(
                me.uid
            )
        )
        _conversationPhoto.postValue(data.conversationRecord.photoUrl)
        _conversationTitle.postValue(data.conversationRecord.title)
        startObservingRemoteData(
            data.conversationRecord.conversationType(),
            data.conversationRecord.p2PRecipientUid()
        )
        this._conversationWithRecipients.postValue(data)
    }

    fun onLocalMessagesDataChanged(data: List<MessageRecord>) {
        this._messages.postValue(data)
    }

    private fun createDateHeaders(list: List<MessageRecord>): Job {
        return generateDateHeaders(list)
    }

    private fun generateDateHeaders(list: List<MessageRecord>): Job {
        return runOnIOThread {
            val newList = mutableListOf<MessageRecord>()
            list.sortedBy { it.timestamp }
                .forEachIndexed { index, messageRecord ->
                    val currentTimestampString = getDateStub(messageRecord.timestamp)
                    // Previous index is +1 as we set reverseLayout=true
                    val prev = list.getOrNull(index + 1)

                    if (index == 0 && !messageRecord.isTimestampHeader()) {
                        //index is 0 means that there is no previous message, so we add
                        // a timestamp for current message.
                        val stamp = MessageRecord.timestampHeader(
                            messageRecord.timestamp,
                            conversationId
                        )
                        newList.add(stamp)
                    } else if (prev != null && !prev.isTimestampHeader() && !messageRecord.isTimestampHeader()) {
                        val prevTimestampString = getDateStub(prev.timestamp)
                        if (prevTimestampString != currentTimestampString) {
                            //A timestamp is needed
                            val stamp = MessageRecord.timestampHeader(
                                prev.timestamp,
                                conversationId
                            )
                            newList.add(stamp)
                        }
                    }
                    newList.add(messageRecord)
                }
            database.insertOrUpdateMessages(newList)
        }
    }

    private fun startObservingRemoteData(
        conversationType: ConversationRecord.ConversationType,
        p2pUid: String?
    ) {
        if (isObservingRemoteDatabase) return
        when (conversationType) {
            ConversationRecord.ConversationType.GROUP -> startObservingRemoteGroupData()
            ConversationRecord.ConversationType.P2P -> startObservingRemoteP2PData(
                requireNotNull(
                    p2pUid
                ) { "P2PUid cannot be null" })
            ConversationRecord.ConversationType.SELF -> startObservingRemoteP2PData(me.uid, false)
        }
        isObservingRemoteDatabase = true
    }

    private fun startObservingRemoteP2PData(p2pUid: String, observeStatus: Boolean = true) {
        messagesManager
            .observeMessagesFromDatabase(
                me.uid, p2pUid, onGetObservedMessagesResponseCallback
            )
        if (observeStatus) {
            statusManager.observeStatus(p2pUid) {
                _status.postValue(it)
            }
        }
    }

    private fun startObservingRemoteGroupData() {
        conversationsManager
            .observeGroupConversationProperties(
                conversationId,
                onGroupConversationDataChangedCallback
            )
        messagesManager
            .observeMessagesFromGroupDatabase(
                conversationId,
                me.uid,
                onGetObservedMessagesResponseCallback
            )
    }

    fun sendMessage(
        message: MessageRecord,
        sendNotifications: Boolean = true,
        invokeBlock: (Int) -> Unit
    ) {
        val messageIndex = messages.value!!.size
        lastMessage = message
        val conversation = conversationWithRecipients.value!!.conversationRecord
        database.insertOrUpdateMessage(message)
        when {
            conversation.isGroup -> {
                messageSender.sendGroupMessage(message) { onMessageSaved(it, sendNotifications) }
            }
            conversation.isP2P -> {
                messageSender.sendP2PMessage(
                    message,
                    conversation.recipientUids.last()
                ) { onMessageSaved(it, sendNotifications) }
            }
            conversation.isSelf -> {
                messageSender.sendSelfMessage(message) { onMessageSaved(it, sendNotifications) }
            }
        }
        invokeBlock(messageIndex)
    }

    private fun onMessageSaved(isMessageSaved: Boolean, sendNotifications: Boolean) {
        lastMessage?.let { messageRecord ->
            if (isMessageSaved) {
                marksAsSent(messageRecord)
                if (!sendNotifications) {
                    return onShouldSendNotification(messageRecord)
                } else return
            } else {
                markAsNotSent(messageRecord)
            }
        }
    }

    private fun onShouldSendNotification(messageRecord: MessageRecord) {
        val recipients = conversationWithRecipients.value!!.recipients
        val conversation = conversationWithRecipients.value!!.conversationRecord
        if (conversation.isSelf) return
        try {
            val data = if (conversation.isGroup) {
                NotificationData.newGroupNotification(
                    messageRecord,
                    me.loadName(),
                    conversation.title,
                    conversation.photoUrl
                )
            } else {
                NotificationData
                    .newP2PNotification(
                        messageRecord,
                        conversation.p2PRecipientUid(),
                        me.loadName(),
                        me.photoUrl
                    )
            }
            NotificationSender.sendNotification(data, recipients)
        } catch (e: Exception) {
            InternalLogger.logE(
                "ChatViewModel",
                "Unable to send notification to other person.",
                e
            )
        }
    }

    fun updateMessage(messageRecord: MessageRecord) {
        val conversation = conversationWithRecipients.value!!.conversationRecord
        messageSender.updateMessage(messageRecord, conversation)
    }

    companion object {
        private val TAG = ConversationViewModel::class.java.simpleName
    }

}
