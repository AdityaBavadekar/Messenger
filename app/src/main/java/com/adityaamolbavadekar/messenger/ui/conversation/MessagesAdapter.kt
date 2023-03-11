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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.constants.PreferenceKeys
import com.adityaamolbavadekar.messenger.databinding.MessageHeaderBinding
import com.adityaamolbavadekar.messenger.databinding.MessageTimestampHeaderBinding
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.managers.PrefsManager.Companion.applyFontSize
import com.adityaamolbavadekar.messenger.managers.PrefsManager.Companion.prefs
import com.adityaamolbavadekar.messenger.model.*
import com.adityaamolbavadekar.messenger.model.RecyclerViewType.Companion.TYPE_HEADER
import com.adityaamolbavadekar.messenger.model.RecyclerViewType.Companion.TYPE_TIMESTAMP_HEADER
import com.adityaamolbavadekar.messenger.utils.extensions.getDate
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseItemHolder
import com.adityaamolbavadekar.messenger.utils.recyclerview.Details
import com.adityaamolbavadekar.messenger.views.*

class MessagesAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<MessageRecord, MessagesAdapter.MessageBaseItemHolder>(
        MessagesDiffCallback()
    ) {

    private var isGroup: Boolean = false
    private var searchData: SearchData? = null
    private var isDebug: Boolean = InternalLogger.isDebugBuild
    private var recipients = listOf<Recipient>()
    private var conversationRecord: ConversationRecord? = null
    private var onReactionListener: OnReactionListener? = null
    private var addReplyListener: AddReplyListener? = null
    private var navigateToReplyListener: NavigateToReplyListener? = null
    private var deletionListener: MessageDeletionListener? = null

    @Suppress("UNNECESSARY_SAFE_CALL")
    fun setConversationWithRecipients(conversationWithRecipients: ConversationWithRecipients) {
        conversationRecord = conversationWithRecipients.conversationRecord
        isGroup = conversationWithRecipients.conversationRecord.isGroup
        if (conversationWithRecipients.recipients != null || conversationWithRecipients.recipients.isNotEmpty()) {
            recipients = conversationWithRecipients.recipients
        }
    }

    fun setOnReactionListener(block: OnReactionListener) {
        this.onReactionListener = block
    }

    fun setOnAddReplyListener(block: AddReplyListener) {
        this.addReplyListener = block
    }

    fun setOnNavigateToReplyListener(block: NavigateToReplyListener) {
        this.navigateToReplyListener = block
    }

    fun setDeletionListener(block: MessageDeletionListener) {
        this.deletionListener = block
    }

    private val myUid: String = AuthManager().uid

    override fun getItemViewType(position: Int): Int {
        val messageRecord = getItem(position)
        return if (messageRecord.type == RecyclerViewType.TYPE_ITEM) {
            if (messageRecord.isSender(myUid)) {
                if (messageRecord.isTextOnly()) {
                    TYPE_OUTGOING_TEXT_ONLY_ITEM
                } else {
                    TYPE_OUTGOING_ITEM
                }
            } else {
                if (messageRecord.isTextOnly()) {
                    TYPE_INCOMING_TEXT_ONLY_ITEM
                } else {
                    TYPE_INCOMING_ITEM
                }
            }
        } else {
            messageRecord.type
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageBaseItemHolder {
        inflateMessageItemLayout(parent, viewType)?.let { return it }
        return when (viewType) {
            TYPE_TIMESTAMP_HEADER -> TimestampHeader(parent)
            TYPE_HEADER -> HeaderHolder(parent)
            else -> throw IllegalArgumentException("Cannot create viewHolder of type $viewType !")
        }
    }

    override fun onBindViewHolder(holder: MessageBaseItemHolder, position: Int) {
        holder.bind(getItem(position), position, onItemClickCallback)
    }

    override fun onViewRecycled(holder: MessageBaseItemHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    private fun inflateMessageItemLayout(parent: ViewGroup, type: Int): MessageItemHolder? {
        if (type !in messageItemViewTypes) return null
        val messageItem: MessageItem = when (type) {
            TYPE_OUTGOING_TEXT_ONLY_ITEM -> {
                inflateLayout(parent, getLayoutResForMessage(isIncoming = false, textOnly = true))
            }
            TYPE_OUTGOING_ITEM -> {
                inflateLayout(parent, getLayoutResForMessage(isIncoming = false, textOnly = false))
            }
            TYPE_INCOMING_TEXT_ONLY_ITEM -> {
                inflateLayout(parent, getLayoutResForMessage(isIncoming = true, textOnly = true))
            }
            TYPE_INCOMING_ITEM -> {
                inflateLayout(parent, getLayoutResForMessage(isIncoming = true, textOnly = false))
            }
            else -> return null
        }
        return MessageItemHolder(messageItem)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <ROOT : View> inflateLayout(container: ViewGroup, @LayoutRes layoutRes: Int): ROOT {
        return requireNotNull(
            LayoutInflater.from(container.context).inflate(layoutRes, container, false)
        ) {
            "LayoutInflater returned null root"
        } as ROOT
    }

    @LayoutRes
    private fun getLayoutResForMessage(isIncoming: Boolean, textOnly: Boolean): Int {
        return when (isIncoming) {
            true -> {
                if (textOnly) R.layout.message_item_incoming_text
                else R.layout.message_item_incoming
            }
            false -> {
                if (textOnly) R.layout.message_item_sent_text
                else R.layout.message_item_sent
            }
        }
    }

    fun getLastPosition(): Int {
        return itemCount - 1
    }

    private inner class TimestampHeader private constructor(private val b: MessageTimestampHeaderBinding) :
        MessageBaseItemHolder(b.root) {

        override fun bind(
            messageRecord: MessageRecord,
            position: Int,
            onItemClickCallback: BaseItemHolder.OnItemClickCallback<MessageRecord>?
        ) {
            super.bind(messageRecord, position, onItemClickCallback)
            b.header.text = getDate(messageRecord.timestamp, b.header.context)
        }

        constructor(parent: ViewGroup) :
                this(
                    MessageTimestampHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

    }

    private inner class HeaderHolder(private val b: MessageHeaderBinding) :
        MessageBaseItemHolder(b.root) {

        override fun bind(
            messageRecord: MessageRecord,
            position: Int,
            onItemClickCallback: BaseItemHolder.OnItemClickCallback<MessageRecord>?
        ) {
            super.bind(messageRecord, position, onItemClickCallback)
            b.header.text = messageItem.message
            b.header.applyFontSize()
        }

        constructor(parent: ViewGroup) :
                this(
                    MessageHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

    }

    inner class MessageItemHolder
    constructor(private val view: MessageItem) :
        MessageBaseItemHolder(view) {

        override fun bind(
            messageRecord: MessageRecord,
            position: Int,
            onItemClickCallback: BaseItemHolder.OnItemClickCallback<MessageRecord>?
        ) {
            super.bind(messageRecord, position, onItemClickCallback)
            onReactionListener?.let { view.setOnReactionListener(it) }
            addReplyListener?.let { view.setOnAddReplyListener(it) }
            navigateToReplyListener?.let { view.setOnNavigateToReplyListener(it) }
            view.setSelectionTracker { getItemSelectionTracker() }
            deletionListener?.let { view.setOnDeletionListener(it) }
            view.setSearchData(searchData)
            view.setDebug(isDebug)
            view.bind(
                lifecycleOwner = lifecycleOwner,
                message = messageRecord,
                previousMessage = if (position != 0) getItem(position - 1) else null,
                nextMessage = if (getLastPosition() > position) getItem(position + 1) else null,
                conversationRecord = conversationRecord,
                sender = recipients.valueOf(messageRecord.senderUid),
                isIncomingMessage = messageRecord.senderUid != myUid,
                getItemDetails().selectionKey,
                position
            )
        }

        override fun unbind() {
            super.unbind()
            view.unbind()
        }
    }

    abstract class MessageBaseItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var details = Details()
        protected lateinit var messageItem: MessageRecord

        open fun bind(
            messageRecord: MessageRecord, position: Int,
            onItemClickCallback: BaseItemHolder.OnItemClickCallback<MessageRecord>?
        ) {
            details.position = position.toLong()
            this.messageItem = messageRecord
        }

        open fun unbind() {}

        fun getItemDetails(): Details {
            return details
        }
    }

    private var onItemClickCallback: BaseItemHolder.OnItemClickCallback<MessageRecord> =
        BaseItemHolder.emptyCallback()

    fun setOnClickListener(callback: BaseItemHolder.OnItemClickCallback<MessageRecord>) {
        this.onItemClickCallback = callback
    }

    /*START Selection*/
    private var selectionTracker: SelectionTracker<Long>? = null
    fun setItemSelectionTracker(tracker: SelectionTracker<Long>) {
        this.selectionTracker = tracker
    }

    fun getItemSelectionTracker(): SelectionTracker<Long> {
        return this.selectionTracker
            ?: throw NullPointerException("selectionTracker was requested but was null.")
    }
    /*END Selection*/

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setSearchData(data: SearchData?) {
        searchData = data
    }

    class MessagesDiffCallback : DiffUtil.ItemCallback<MessageRecord>() {
        override fun areItemsTheSame(
            oldItem: MessageRecord,
            newItem: MessageRecord
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MessageRecord,
            newItem: MessageRecord
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private val TAG = MessagesAdapter::class.java.simpleName

        /* For Messages */
        private const val TYPE_OUTGOING_ITEM = 101
        private const val TYPE_OUTGOING_TEXT_ONLY_ITEM = 102
        private const val TYPE_INCOMING_ITEM = 201
        private const val TYPE_INCOMING_TEXT_ONLY_ITEM = 202

        private val messageItemViewTypes = listOf(
            TYPE_OUTGOING_ITEM,
            TYPE_OUTGOING_TEXT_ONLY_ITEM,
            TYPE_INCOMING_ITEM,
            TYPE_INCOMING_TEXT_ONLY_ITEM
        )

        fun TextView.applyMessageBodyFontSize() {
            val f = when (context.prefs.getInt(PreferenceKeys.TEXT_SIZE, FontSize.MEDIUM)) {
                FontSize.SMALL -> R.style.TextAppearance_Messenger_Message_BodySmall
                FontSize.MEDIUM -> R.style.TextAppearance_Messenger_Message_Body
                else -> R.style.TextAppearance_Messenger_Message_BodyLarge
            }
            TextViewCompat.setTextAppearance(this, f)
        }

        fun TextView.applyMessageTitleFontSize() {
            val f = when (context.prefs.getInt(PreferenceKeys.TEXT_SIZE, FontSize.MEDIUM)) {
                FontSize.SMALL -> R.style.TextAppearance_Messenger_Message_TitleSmall
                FontSize.MEDIUM -> R.style.TextAppearance_Messenger_Message_Title
                else -> R.style.TextAppearance_Messenger_Message_TitleLarge
            }
            TextViewCompat.setTextAppearance(this, f)
        }
    }

}