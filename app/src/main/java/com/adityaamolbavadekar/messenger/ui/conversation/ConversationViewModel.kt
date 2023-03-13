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

import android.accounts.NetworkErrorException
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.adityaamolbavadekar.messenger.database.conversations.ApplicationDatabaseRepository
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.managers.InternetManager
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.model.*
import com.adityaamolbavadekar.messenger.notifications.NotificationData
import com.adityaamolbavadekar.messenger.notifications.NotificationSender
import com.adityaamolbavadekar.messenger.utils.extensions.findFirstMessage
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import kotlinx.coroutines.launch

class ConversationViewModel(private val repo: ApplicationDatabaseRepository) : ViewModel(),
    MessagesChildListener.Listener {

    private var lastMessage: MessageRecord? = null
    private var isObservingRemoteDatabase = false
    private lateinit var conversationId: String
    private val myUid: String = AuthManager().uid
    private var conversationType = 0
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
    private val _searchData: MutableLiveData<SearchData?> = MutableLiveData(null)
    val searchData: LiveData<SearchData?> = _searchData
    private lateinit var database: DatabaseAndroidViewModel
    private val messageSender = MessageSender()
    private val cloudDatabaseManager = CloudDatabaseManager()
    private val conversationsManager = cloudDatabaseManager.getConversationsManager()
    private val messagesManager = cloudDatabaseManager.getMessagesManager()
    private val statusManager = cloudDatabaseManager.getStatusManager()
    private val messageDeleter = MessagesDeleter()
    private lateinit var me: Recipient
    private var configured = false
    private var isConnected = false

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
                        .updateLastMessageData(_messages.value!!.findFirstMessage())
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
        m.markAsNotSent()
        database.insertOrUpdateMessage(m)
        InternalLogger.logD("MessageStatus", "MarkedAs : [NOT_SENT]")
    }

    private fun marksAsSent(m: MessageRecord) = runOnIOThread {
        m.markAsSent()
        database.insertOrUpdateMessage(m)
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
        type: Int,
        p2pUid: String?,
    ) {
        conversationType = type
        startObservingRemoteData(p2pUid)
    }

    private fun startObservingRemoteData(p2pUid: String?) {
        if (isObservingRemoteDatabase) return
        when (conversationType) {
            ConversationRecord.CONVERSATION_TYPE_GROUP -> startObservingRemoteGroupData()
            ConversationRecord.CONVERSATION_TYPE_P2P -> startObservingRemoteP2PData(
                requireNotNull(
                    p2pUid
                ) { "P2PUid cannot be null" })
            ConversationRecord.CONVERSATION_TYPE_SELF -> startObservingRemoteP2PData(me.uid, false)
            else -> throw IllegalStateException("ConversationType $conversationType is unknown.")
        }
        isObservingRemoteDatabase = true
    }

    private fun startObservingRemoteP2PData(p2pUid: String, observeStatus: Boolean = true) {
        messagesManager.observeMessagesFromDatabaseV2(
            me.uid, p2pUid,
            MessagesChildListener(this).childEventListener
        )
        if (observeStatus) {
            statusManager.observeStatus(p2pUid) {
                _status.postValue(it)
                // Update lastSeen
                val r =
                    conversationWithRecipients.value!!.recipients.find { recipient -> recipient.uid == p2pUid }!!
                r.lastSeen = it
                database.insertOrUpdateRecipient(r)
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
            .observeMessagesFromGroupDatabaseV2(
                conversationId,
                MessagesChildListener(this).childEventListener
            )
    }

    fun sendMessage(
        message: MessageRecord,
        sendNotifications: Boolean = true
    ): Task<Void> {
        val source = TaskCompletionSource<Void>()
        if (!isConnected) {
            markAsNotSent(message)
            source.setException(NetworkErrorException())
            return source.task
        }
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
                ) {
                    onMessageSaved(it, sendNotifications)
                }
            }
            conversation.isSelf -> {
                messageSender.sendSelfMessage(message) { onMessageSaved(it, sendNotifications) }
            }
        }
        source.setResult(null)
        return source.task
    }

    private fun onMessageSaved(isMessageSaved: Boolean, sendNotifications: Boolean) {
        lastMessage?.let { messageRecord ->
            if (isMessageSaved) {
                marksAsSent(messageRecord)
                if (sendNotifications) onShouldSendNotification(messageRecord)
                else return
            } else markAsNotSent(messageRecord)
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
                TAG,
                "Unable to send notification to other person.",
                e
            )
        }
    }

    fun updateMessage(messageRecord: MessageRecord) {
        val conversation = conversationWithRecipients.value!!.conversationRecord
        messageSender.updateMessage(messageRecord, conversation)
    }

    fun delete(messageRecord: MessageRecord, forEveryone: Boolean) {
        if (forEveryone) {
            if (conversationType == ConversationRecord.CONVERSATION_TYPE_P2P) {
                val p2pUid = conversationWithRecipients.value!!.conversationRecord.p2PRecipientUid()
                messageDeleter.deleteForEveryoneP2P(p2pUid, messageRecord)
            }
            if (conversationType == ConversationRecord.CONVERSATION_TYPE_GROUP) messageDeleter.deleteForEveryoneGroup(
                messageRecord
            )
        } else {
            if (conversationType == ConversationRecord.CONVERSATION_TYPE_P2P) {
                val p2pUid = conversationWithRecipients.value!!.conversationRecord.p2PRecipientUid()
                messageDeleter.deleteForMe(p2pUid, messageRecord)
            }
        }
    }

    fun stopSearch() {
        _searchData.postValue(null)
    }

    fun search(query: String?) {
        if (query != null && query.trim().isNotEmpty()) {
            val searchMessages = mutableListOf<Int>()
            messages.value!!.forEachIndexed { index, m ->
                if (m.containsSearchQuery(query))
                    searchMessages.add(index)
            }
            _searchData.postValue(SearchData(query, searchMessages))
        }
    }

    fun findMessageIndexOf(messageId: String): Int? {
        return messages.value!!.whereMessageId(messageId)?.first
    }

    fun setConversationId(id: String) {
        this.conversationId = id
        initialise()
    }

    private fun onLocalConversationDataChanged(data: ConversationWithRecipients) {
        _isMessagingRestrictedForMe.postValue(
            data.conversationRecord.isMessagingRestrictedForUser(myUid)
        )
        _conversationPhoto.postValue(data.conversationRecord.photoUrl)
        _conversationTitle.postValue(data.conversationRecord.title)
        conversationType = data.conversationRecord.conversationType()
        startObservingRemoteData(if (conversationType == ConversationRecord.CONVERSATION_TYPE_P2P) data.conversationRecord.p2PRecipientUid() else null)
    }

    private fun initialise() {
        collectMessages()
        collectConversationAndRecipientsInfo()
        observeNetworkConnectivity()
    }

    private fun collectMessages() = runOnIOThread {
        repo.getMessagesAsFlow(conversationId).collect {
            _messages.postValue(it)
        }
    }

    private fun collectConversationAndRecipientsInfo() = runOnIOThread {
        repo.getRecipientsOfConversationAsFlow(conversationId).collect {
            _conversationWithRecipients.postValue(it)
            onLocalConversationDataChanged(it)
        }
    }

    private fun observeNetworkConnectivity() = viewModelScope.launch {
        InternetManager.isConnected.observeForever(connectionObserver)
    }

    private val connectionObserver: Observer<Boolean> =
        Observer<Boolean> { t ->
            InternalLogger.logW(TAG, "ConnectionObserver.onChanged(${t ?: false})")
            isConnected = t ?: false
        }

    companion object {
        private val TAG = ConversationViewModel::class.java.simpleName

        @Suppress("UNCHECKED_CAST")
        fun getFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val savedStateHandle = extras.createSavedStateHandle()
                val messengerApplication = application as com.adityaamolbavadekar.messenger.App
                return ConversationViewModel(messengerApplication.database.repo()) as T
            }
        }

    }

    override fun doOnAdded(messageRecord: MessageRecord) {
        //TODO Mark messages as Read
        database.insertOrUpdateMessage(messageRecord)
    }

    override fun doOnChanged(messageRecord: MessageRecord) {
        database.insertOrUpdateMessage(messageRecord)
    }

    override fun doOnRemoved(messageRecord: MessageRecord) {
        database.insertOrUpdateMessage(messageRecord)
    }

    fun insertLocalAttachment(attachment: LocalAttachment) =viewModelScope.launch{
        repo.insertOrUpdateLocalAttachment(attachment)
    }

    fun getLocalAttachment(id: String): LocalAttachment {
        return repo.getLocalAttachment(id)
    }
}
