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

package com.adityaamolbavadekar.messenger.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spanned
import android.text.SpannedString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.core.text.italic
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.selection.SelectionTracker
import androidx.transition.TransitionManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.model.*
import com.adityaamolbavadekar.messenger.ui.conversation.MessageDeletionListener
import com.adityaamolbavadekar.messenger.ui.conversation.MessagesAdapter.Companion.applyMessageBodyFontSize
import com.adityaamolbavadekar.messenger.ui.conversation.MessagesAdapter.Companion.applyMessageTitleFontSize
import com.adityaamolbavadekar.messenger.ui.conversation.OnReactionListener
import com.adityaamolbavadekar.messenger.ui.zoom.ZoomableImageViewerActivity
import com.adityaamolbavadekar.messenger.utils.ClipboardUtil
import com.adityaamolbavadekar.messenger.utils.Constants.TimestampFormats.MESSAGE_TIMESTAMP_FORMAT
import com.adityaamolbavadekar.messenger.utils.Constants.TimestampFormats.TIMESTAMP_FORMAT_FULL
import com.adityaamolbavadekar.messenger.utils.CustomLinkHandlerSpan
import com.adityaamolbavadekar.messenger.utils.ImageLoader
import com.adityaamolbavadekar.messenger.utils.extensions.isNotNull
import com.adityaamolbavadekar.messenger.utils.extensions.isNull
import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.utils.textstyling.TextStyler
import com.adityaamolbavadekar.messenger.views.message.*
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialElevationScale

class MessageItem @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0,
) : RelativeLayout(context, attr, defStyle), RecyclableMessageItem {

    private var mainHolderView: MaterialCardView? = null
    private var titleTextView: TextView? = null
    private var messageTextView: TextView? = null
    private var timestampTextView: TextView? = null
    private var photoAttachmentsView: PhotoAttachmentsView? = null
    private var documentAttachmentsView: DocumentView? = null
    private var deliveryStatusView: DeliveryStatusView? = null
    private var reactionsView: ReactionsView? = null
    private var linkPreviewView: LinkPreviewView? = null
    private var replyView: ReplyView? = null

    private var sender: Recipient? = null
    private var messageRecord: MessageRecord? = null
    private var previousMessageRecord: MessageRecord? = null
    private var nextMessageRecord: MessageRecord? = null
    private var conversation: ConversationRecord? = null
    private var conversationRecipients: List<Recipient> = listOf()

    private var titleClickListener: TitleClickListener? = null
    private var replyClickListener: ReplyClickListener? = null
    private var reactionListener: OnReactionListener = getEmptyOnReactionListener()
    private var deletionListener: MessageDeletionListener = getEmptyDeletionListener()
    private var searchData: SearchData? = null
    private val imageLoader = ImageLoader.with(this)
    private var itemIndex: Int = 0
    private var selectionKey: Long = 0
    private var selectionTrackerProvider: (() -> SelectionTracker<Long>)? = null

    private var isDebug: Boolean = false
    private var isGroupConversation: Boolean = false
    private var isP2PConversation: Boolean = false
    private var isSelfConversation: Boolean = false
    private var isIncoming: Boolean = false
    private var hasReply: Boolean = false
    private var hasLinkPreview: Boolean = false
    private var hasMessage: Boolean = false
    private var hasPhotoAttachments: Boolean = false
    private var hasDocumentAttachments: Boolean = false
    private var hasReactions: Boolean = false

    private var wasBindedPreviously: Boolean = false
    private var isSelectedMessageItem: Boolean = false

    override fun bind(
        lifecycleOwner: LifecycleOwner,
        message: MessageRecord,
        previousMessage: MessageRecord?,
        nextMessage: MessageRecord?,
        conversationRecord: ConversationRecord?,
        sender: Recipient?,
        isIncomingMessage: Boolean,
        itemSelectionKey: Long,
        index: Int
    ) {

        this.sender = sender
        this.messageRecord = message
        this.previousMessageRecord = previousMessage
        this.nextMessageRecord = nextMessage
        this.conversation = conversationRecord
        this.isIncoming = isIncomingMessage
        this.hasReply = requireMessageRecord().hasReply()
        this.hasLinkPreview = requireMessageRecord().hasLinkPreview()
        this.hasMessage = requireMessageRecord().hasMessage()
        this.hasPhotoAttachments = requireMessageRecord().hasPhotoAttachments()
        this.hasDocumentAttachments = requireMessageRecord().hasDocumentAttachments()
        this.hasReactions = requireMessageRecord().hasReactions()
        this.selectionKey = itemSelectionKey
        this.itemIndex = index

        if (!this.wasBindedPreviously) {
            this.wasBindedPreviously = true
            if (isDebug) InternalLogger.logD(
                TAG, "onBind()\n" +
                        "isIncoming=$isIncoming" + "\n" +
                        "hasReply=$hasReply" + "\n" +
                        "hasLinkPreview=$hasLinkPreview" + "\n" +
                        "hasMessage=$hasMessage" + "\n" +
                        "hasPhotoAttachments=$hasPhotoAttachments" + "\n" +
                        "hasDocumentAttachments=$hasDocumentAttachments" + "\n" +
                        "hasReactions=$hasReactions" + "\n" +
                        "isTextOnly=${messageRecord!!.isTextOnly()}" + "\n"
            )
        }
        conversationRecord?.let { record ->
            isGroupConversation = record.isGroup
            isP2PConversation = record.isP2P
            isSelfConversation = record.isSelf
        }

        setTitleText()
        setMessageText()
        setTimestampText()
        setLinkPreview()
        setReactions()
        setImageAttachments()
        setDocumentAttachments()
        setDeliveryStatus()
        setReply()
        bindSelection()
    }

    override fun unbind() {
        this.sender = null
        this.messageRecord = null
        this.previousMessageRecord = null
        this.nextMessageRecord = null
        this.conversation = null
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        context.resources.also {
            updateLayoutParams<MarginLayoutParams> {
                topMargin = it.getDimension(R.dimen.message_margin_top).toInt()
            }
            val messagePaddingHorizontal =
                it.getDimension(R.dimen.message_padding_horizontal).toInt()
            val messagePaddingVertical = it.getDimension(R.dimen.message_padding_vertical).toInt()
            setPadding(
                messagePaddingVertical,
                messagePaddingHorizontal,
                messagePaddingHorizontal,
                messagePaddingVertical
            )
        }

        mainHolderView = findViewById(R.id.send_message_card)
        titleTextView = findViewById(R.id.title)
        messageTextView = findViewById(R.id.message)
        timestampTextView = findViewById(R.id.stamp)
        photoAttachmentsView = findViewById(R.id.photoAttachmentView)
        documentAttachmentsView = findViewById(R.id.documentAttachmentView)
        deliveryStatusView = findViewById(R.id.deliveryStatusView)
        reactionsView = findViewById(R.id.reactionsView)
        linkPreviewView = findViewById(R.id.linkView)
        replyView = findViewById(R.id.replyView)
    }

    private fun getEmptyOnReactionListener(): OnReactionListener {
        return object : OnReactionListener {
            override fun onShouldShowReactionChooser(messageRecord: MessageRecord) {}
            override fun onShouldShowReactionsInfo(messageRecord: MessageRecord) {}
        }
    }

    private fun getEmptyDeletionListener(): MessageDeletionListener {
        return object : MessageDeletionListener {
            override fun onShouldDelete(messageRecord: MessageRecord) {}
            override fun onShouldDeleteForEveryone(messageRecord: MessageRecord) {}
        }
    }

    private fun setTitleText() {
        if (getIsSentByMe() || sender.isNull()) {
            titleTextView?.text = null
            setGone(titleTextView)
            return
        }

        checkNotNull(titleTextView) { "titleTextView cannot be null" }
        val titleText = getTitleText()
        titleTextView!!.text = titleText
        if (searchData != null && titleText.lowercase().contains(searchData!!.query.lowercase())) {
            try {
                val range = (titleText.indexOf(searchData!!.query)..searchData!!.query.length)
                titleTextView!!.text = wrapSearchResult(SpannedString(titleText), range)
            } catch (e: Exception) {
                titleTextView!!.text = wrapSearchResult(titleText)
            }
        }
        titleTextView!!.setOnClickListener {
            onTitleClicked()
        }
        titleTextView!!.applyMessageTitleFontSize()
        setVisible(titleTextView!!)
    }

    private fun setMessageText() {
        if (!requireMessageRecord().hasMessage() && !requireMessageRecord().isDeleted) {
            messageTextView?.text = null
            setGone(messageTextView)
            return
        }

        checkNotNull(messageTextView) { "messageTextView cannot be null" }

        if (requireMessageRecord().isDeleted) {
            messageTextView!!.text = buildSpannedString {
                val t = context.getString(R.string.deleted_message_placeholder)
                italic { append(t) }
            }
            messageTextView!!.applyMessageBodyFontSize()
            setVisible(messageTextView!!)
            return
        }

        val messageText = requireMessageRecord().message!!
        messageTextView!!.text = messageText
        TextStyler.parse(context, messageText) { message ->
            if (searchData != null && messageText.lowercase()
                    .contains(searchData!!.query.lowercase())
            ) {
                try {
                    val range = (messageText.indexOf(searchData!!.query)..searchData!!.query.length)
                    messageTextView!!.text = wrapSearchResult(message, range)
                } catch (e: Exception) {
                    messageTextView!!.text = wrapSearchResult(message)
                }
            } else messageTextView!!.text = message
        }

        messageTextView!!.applyMessageBodyFontSize()
        setVisible(messageTextView!!)
    }

    private fun setTimestampText() {
        checkNotNull(timestampTextView) { "timestampTextView cannot be null" }
        timestampTextView!!.text = getTimestampText()
        setVisible(timestampTextView)
    }

    override fun updateTimestamp() {
        setTimestampText()
    }

    private fun setLinkPreview() {
        if (!requireMessageRecord().hasLinkPreview() || requireMessageRecord().isDeleted) {
            linkPreviewView?.setOnClickListener(null)
            linkPreviewView?.setLinkPreviewInfo(null)
            setGone(linkPreviewView)
            return
        }

        checkNotNull(linkPreviewView) { "linkPreviewView cannot be null" }
        val linkPreviewInfo = requireMessageRecord().linkPreviewInfo!!
        linkPreviewView!!.setLinkPreviewInfo(linkPreviewInfo)
        linkPreviewView!!.setOnLinkClickListener(::onLinkPreviewClicked)
        setVisible(linkPreviewView!!)
    }

    private fun setReactions() {
        if (!requireMessageRecord().hasReactions()) {
            reactionsView?.setReactionsList(listOf())
            reactionsView?.setOnClickListener(null)
            setGone(reactionsView)
            return
        }

        checkNotNull(reactionsView) { "reactionsView cannot be null" }
        reactionsView!!.setOnClickListener {
            onReactionsViewClicked()
        }
        reactionsView!!.setReactionsList(requireMessageRecord().reactions)
        setVisible(reactionsView!!)
    }

    private fun setImageAttachments() {
        if (!requireMessageRecord().hasPhotoAttachments() || requireMessageRecord().isDeleted) {
            photoAttachmentsView?.setOnClickListener(null)
            photoAttachmentsView?.removeAllAttachments()
            setGone(photoAttachmentsView)
            return
        }

        checkNotNull(photoAttachmentsView) { "photoAttachmentsView cannot be null" }
        photoAttachmentsView!!.addAttachments(requireMessageRecord().attachments)
        photoAttachmentsView!!.setOnAttachmentsClickListener {
            onPhotoAttachmentsViewClicked(it)
        }
        setVisible(photoAttachmentsView!!)
    }

    private fun setDocumentAttachments() {
        if (!requireMessageRecord().hasDocumentAttachments() || requireMessageRecord().isDeleted) {
            documentAttachmentsView?.setOnClickListener(null)
            documentAttachmentsView?.setDocument(null)
            setGone(documentAttachmentsView)
            return
        }

        checkNotNull(documentAttachmentsView) { "photoAttachmentsView cannot be null" }
        documentAttachmentsView!!.setDocument(requireMessageRecord().documentAttachment!!)
        documentAttachmentsView!!.setOnAttachmentClickListener {
            onDocumentAttachmentClicked(it)
        }
        setVisible(documentAttachmentsView!!)
    }

    private fun setDeliveryStatus() {
        if (!getIsSentByMe() || requireMessageRecord().isDeleted) {
            deliveryStatusView?.setImageDrawable(null)
            setGone(deliveryStatusView)
            return
        }

        checkNotNull(deliveryStatusView) { "deliveryStatusView cannot be null" }
        deliveryStatusView!!.setStatus(requireMessageRecord().deliveryStatus)
        conversation?.let {
            if (it.recipientUids.size == requireMessageRecord().viewedBy.size) {
                deliveryStatusView!!.setReadByAll()
            }
        }
        setVisible(deliveryStatusView!!)
    }

    private fun setReply() {
        if (!requireMessageRecord().hasReply() || requireMessageRecord().isDeleted) {
            replyView?.setMessageRecord(null)
            replyView?.setMessageSender(null)
            replyView?.setOnClickListener(null)
            setGone(replyView)
            return
        }

        checkNotNull(replyView) { "replyView cannot be null" }
        val replyInfo = requireMessageRecord().getReply()!!
        replyView!!.setMessageRecord(replyInfo)
        val replyInfoSender = conversationRecipients.valueOf(replyInfo.senderUid)
        if (replyInfoSender.isNotNull()) {
            replyView!!.setMessageSender(replyInfoSender!!)
        }

        replyView!!.setOnClickListener {
            onReplyClicked()
        }

        setVisible(replyView!!)
    }

    private fun bindSelection() {
        this.mainHolderView!!.setOnLongClickListener { onMessageItemLongClicked() }
        this.setOnLongClickListener { this.mainHolderView!!.performLongClick() }
    }

    private fun onMessageItemLongClicked(): Boolean {
        return if (IS_SELECTION_SUPPORTED) {
            getSelectionTracker()?.apply {
                setItemsSelected(listOf(selectionKey), isSelectedMessageItem)
            }
            updateSelectionState()
            true
        } else showPopupMenu(mainHolderView!!)
    }

    private fun showPopupMenu(view: View): Boolean {
        val popup = PopupMenu(context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.popup_message_item, popup.menu)
        popup.menu.findItem(R.id.message_info).isVisible = isDebug
        val deleteForEveryone = popup.menu.findItem(R.id.action_delete_for_everyone)
        val delete = popup.menu.findItem(R.id.action_delete)
        if (isIncoming || requireMessageRecord().isDeleted) {
            deleteForEveryone.isVisible = false
            delete.isVisible = false
        } else if (requireMessageRecord().deliveryStatus == DeliveryStatus.READ) {
            deleteForEveryone.isVisible = false
            delete.isVisible = true
        } else {
            deleteForEveryone.isVisible = true
            delete.isVisible = false
        }

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_react -> {
                    reactionListener.onShouldShowReactionChooser(requireMessageRecord())
                }
                R.id.action_copy -> {
                    ClipboardUtil.with(context).clip(requireMessageRecord().message ?: "")
                }
                R.id.action_delete_for_everyone -> {
                    if (!requireMessageRecord().isDeleted)
                        deletionListener.onShouldDeleteForEveryone(requireMessageRecord())
                }
                R.id.action_delete -> {
                    if (!requireMessageRecord().isDeleted)
                        deletionListener.onShouldDelete(requireMessageRecord())
                }
                R.id.message_info -> {
                    val m = requireMessageRecord()
                    val status = context.getString(DeliveryStatus.getStatusString(m.deliveryStatus))
                    val messageText = "" +
                            "deliveryStatus=$status" + "\n" +
                            "timestamp=${
                                simpleDateFormat(
                                    m.timestamp,
                                    TIMESTAMP_FORMAT_FULL
                                )
                            }" + "\n" +
                            "sentBy=${sender?.loadName() ?: "null"}" + "\n" +
                            "index=${itemIndex}" + "\n" +
                            "isIncoming=$isIncoming" + "\n" +
                            "isDeleted=${requireMessageRecord().isDeleted}" + "\n" +
                            "hasReply=$hasReply" + "\n" +
                            "hasLinkPreview=$hasLinkPreview" + "\n" +
                            "hasMessage=$hasMessage" + "\n" +
                            "hasPhotoAttachments=$hasPhotoAttachments (${requireMessageRecord().attachments.size})" + "\n" +
                            "hasDocumentAttachments=$hasDocumentAttachments" + "\n" +
                            "hasReactions=$hasReactions" + "\n" +
                            "isTextOnly=${messageRecord!!.isTextOnly()}" + "\n"

                    MaterialAlertDialogBuilder(context)
                        .setTitle("Message Info")
                        .setMessage(messageText)
                        .setCancelable(true)
                        .create()
                        .show()
                }
                R.id.action_reply -> {

                }
                else -> return@setOnMenuItemClickListener false
            }
            true
        }
        popup.show()
        return true
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateSelectionState() {
        if (isSelectedMessageItem) {
            val transformation = MaterialElevationScale(true).apply {
                addTarget(mainHolderView!!)
            }
            transformation.startDelay = 10
            TransitionManager.beginDelayedTransition(mainHolderView!!, transformation)
            this.background =
                context!!.getDrawable(R.drawable.message_selected)!!
        } else {
            this.background = null
            val transformation = MaterialElevationScale(false).apply {
                addTarget(mainHolderView!!)
            }
            transformation.startDelay = 10
            TransitionManager.beginDelayedTransition(mainHolderView!!, transformation)
        }
    }

    private fun setGone(v: View?) {
        if (v == null) return
        v.visibility = View.GONE
    }

    private fun setVisible(v: View?) {
        if (v == null) return
        v.visibility = View.VISIBLE
    }

    private fun getTitleText(): String {
        return sender?.loadName() ?: ""
    }

    private fun getTimestampText(): String {
        return simpleDateFormat(requireMessageRecord().timestamp, MESSAGE_TIMESTAMP_FORMAT)
    }

    private fun onTitleClicked() {
        titleClickListener?.invoke(titleTextView!!, requireMessageRecord())
    }

    private fun onLinkPreviewClicked(url: String) {
        CustomLinkHandlerSpan.openBrowser(context, url)
    }

    private fun onReplyClicked() {
        replyClickListener?.invoke(
            replyView!!,
            requireMessageRecord(),
            requireMessageRecord().getReply()!!
        )
    }

    private fun onReactionsViewClicked() {
        reactionListener.onShouldShowReactionsInfo(requireMessageRecord())
    }

    private fun onPhotoAttachmentsViewClicked(attachments: List<String>) {
        context.startActivity(
            ZoomableImageViewerActivity
                .createNewIntent(
                    context, attachments,
                    getTitleText(), getTimestampText()
                )
        )
    }

    private fun onDocumentAttachmentClicked(attachment: Attachment) {
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.fromFile(attachment.file()), attachment.mimeType())
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            try {
                context.startActivity(this)
            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Unable to start file open intent.", e)
                Toast.makeText(
                    context,
                    context.getString(R.string.unable_to_open_file),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun onPhotoAttachmentsClicked(urls: List<String>) {
        context.startActivity(
            ZoomableImageViewerActivity.createNewIntent(
                context, urls, getTitleText(),
                simpleDateFormat(requireMessageRecord().timestamp)
            )
        )
    }

    private fun getSelectionTracker() = selectionTrackerProvider?.invoke()

    private fun getIsSentByMe() = !isIncoming
    fun getSender() = sender
    private fun requireMessageRecord() =
        requireNotNull(messageRecord) { "messageRecord must not be null" }

    fun getMessageRecord() = messageRecord
    fun getPreviousMessageRecord() = previousMessageRecord
    fun getNextMessageRecord() = nextMessageRecord
    fun getConversationRecord() = conversation

    fun setOnTitleClickListener(listener: TitleClickListener) {
        this.titleClickListener = listener
    }

    fun setOnReplyClickListener(listener: ReplyClickListener) {
        this.replyClickListener = listener
    }

    fun setOnReactionListener(listener: OnReactionListener) {
        this.reactionListener = listener
    }

    fun setOnDeletionListener(listener: MessageDeletionListener) {
        this.deletionListener = listener
    }

    fun setPreviousMessageRecord(message: MessageRecord?) {
        this.previousMessageRecord = message
    }

    fun setNextMessageRecord(message: MessageRecord?) {
        this.nextMessageRecord = message
    }

    fun setRecipients(recipients: List<Recipient>) {
        this.conversationRecipients = recipients
    }

    fun setSelectionTracker(selectionTrackerBlock: () -> SelectionTracker<Long>) {
        this.selectionTrackerProvider = selectionTrackerBlock
    }

    fun setSearchData(data: SearchData?) {
        searchData = data
    }

    fun setDebug(debug: Boolean) {
        isDebug = debug
    }

    //TODO
    private fun wrapSearchResult(string: String): SpannedString =
        wrapSearchResult(buildSpannedString { append(string) })

    //TODO
    private fun wrapSearchResult(string: SpannedString): SpannedString {
        val backgroundColor = context.resources.getColor(R.color.colorThemeOpposite)
        return buildSpannedString {
            inSpans(BackgroundColorSpan(backgroundColor)) {
                append(string)
            }
        }
    }

    private fun wrapSearchResult(string: SpannedString, range: IntRange): SpannedString {
        val textColor = context.resources.getColor(R.color.colorThemeOpposite)
        val backgroundColor = context.resources.getColor(R.color.colorThemeBasic)
        return buildSpannedString {
            append(string)
            setSpan(
                BackgroundColorSpan(backgroundColor),
                range.first,
                range.last,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(textColor),
                range.first,
                range.last,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    companion object {
        private val TAG = MessageItem::class.java.simpleName
        private const val IS_SELECTION_SUPPORTED = false
        fun new(context: Context): MessageItem {
            return MessageItem(context, null, 0)
        }
    }

}
