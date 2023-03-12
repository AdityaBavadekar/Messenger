package com.adityaamolbavadekar.messenger.utils

import com.adityaamolbavadekar.messenger.ui.conversation.MessageDeletionListener
import com.adityaamolbavadekar.messenger.ui.conversation.OnReactionListener
import com.adityaamolbavadekar.messenger.views.*

class MessageItemListeners {

    private var onReactionListener: OnReactionListener? = null
    private var addReplyListener: AddReplyListener? = null
    private var navigateToReplyListener: NavigateToReplyListener? = null
    private var deletionListener: MessageDeletionListener? = null
    private var openDocumentListener: OpenDocumentListener? = null
    private var downloadDocumentListener: DownloadDocumentListener? = null

    fun setOnAddReplyListener(listener: AddReplyListener) {
        this.addReplyListener = listener
    }

    fun getAddReplyListener()  = addReplyListener

    fun setOnNavigateToReplyListener(listener: NavigateToReplyListener) {
        this.navigateToReplyListener = listener
    }

    fun getNavigateToReplyListener() = navigateToReplyListener

    fun setOnReactionListener(listener: OnReactionListener) {
        this.onReactionListener = listener
    }

    fun getReactionListener() = onReactionListener

    fun setOnDeletionListener(listener: MessageDeletionListener) {
        this.deletionListener = listener
    }

    fun getDeletionListener() = deletionListener

    fun setOpenDocumentListener(listener: OpenDocumentListener) {
        this.openDocumentListener = listener
    }

    fun getOpenDocumentListener() = openDocumentListener

    fun setDownloadDocumentListener(listener: DownloadDocumentListener) {
        this.downloadDocumentListener = listener
    }

    fun getDownloadDocumentListener() = downloadDocumentListener

    fun applyToMessageItem(messageItem: MessageItem) {
        addReplyListener?.let { messageItem.setOnAddReplyListener(it) }
        navigateToReplyListener?.let { messageItem.setOnNavigateToReplyListener(it) }
        onReactionListener?.let { messageItem.setOnReactionListener(it) }
        deletionListener?.let { messageItem.setOnDeletionListener(it) }
        openDocumentListener?.let { messageItem.setOpenDocumentListener(it) }
        downloadDocumentListener?.let { messageItem.setDownloadDocumentListener(it) }

    }

}
