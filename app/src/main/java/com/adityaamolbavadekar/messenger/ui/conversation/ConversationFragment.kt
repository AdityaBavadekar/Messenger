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

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.ConversationFragmentBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.model.*
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.Constants.EXTRA_CONVERSATION_ID
import com.adityaamolbavadekar.messenger.utils.DownlodUtil
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.views.MessagesListRecyclerView

/**
 * Fragment that displays a list of messages.
 * Requires Extra : [EXTRA_CONVERSATION_ID]
 * */
class ConversationFragment : BindingHelperFragment<ConversationFragmentBinding>(),
    OnReactionListener, MessageDeletionListener {

    override fun onShouldInflateBinding(): ConversationFragmentBinding {
        return ConversationFragmentBinding.inflate(layoutInflater)
    }

    lateinit var activityToolbar: Toolbar
    var actionMode: ActionMode? = null
    lateinit var selectionTracker: SelectionTracker<Long>
    private val viewModel: ConversationViewModel by activityViewModels { ConversationViewModel.getFactory() }
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var conversationId: String
    private var conversation: ConversationRecord? = null
    private lateinit var conversationOnScrollDateHeader: ConversationOnScrollDateHeader
    private lateinit var messagesSelectionCallback: MessagesSelectionCallback
    private lateinit var messageRecyclerView: MessagesListRecyclerView

    private fun getConversationActivity(): ConversationActivity {
        return requireActivity() as ConversationActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doOnCreateView()
        setupViews()
    }

    private fun doOnCreateView() {
        messagesSelectionCallback = MessagesSelectionCallback(this)
            .addOnCopyListener { copyMessages() }
            .addOnDeleteListener { deleteMessages() }
        conversationId =
            requireNotNull(requireActivity().intent.getStringExtra(EXTRA_CONVERSATION_ID))
            { "ConversationId cannot be null." }
        InternalLogger.logD(TAG, "ConversationId : $conversationId")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupViews() {

        /* Main messages recycler view */
        binding.list.apply {
            messageRecyclerView = this
            messagesAdapter = MessagesAdapter(viewLifecycleOwner)
            adapter = messagesAdapter
            addOnScrollListener(onScrollListener)
        }
        setupSelectionTracker()
        messagesAdapter.getListenersDatabase().apply {
            setOnReactionListener(this@ConversationFragment)
            setOnAddReplyListener { recipient, messageRecord ->
                getConversationActivity().onShouldAddReply(recipient, messageRecord)
            }
            setOnNavigateToReplyListener { recipient, replyInfo ->
                navigateToMessage(replyInfo.correspondingMessageId)
            }
            setOnDeletionListener(this@ConversationFragment)
            setOpenDocumentListener {
                val attachment = viewModel.getLocalAttachment(it.id)
                Intent(Intent.ACTION_VIEW).apply {
                    val file = attachment.getFile()
                    val uri = FileProvider.getUriForFile(
                        requireContext(),
                        Constants.FILE_PROVIDER_AUTHORITY,
                        file
                    )
                    setDataAndType(uri, attachment.attachmentMimeType)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_GRANT_READ_URI_PERMISSION
                    try {
                        requireContext().startActivity(this)
                    } catch (e: Exception) {
                        InternalLogger.logE(TAG, "Unable to start file open intent.", e)
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.unable_to_open_file),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            setDownloadDocumentListener { messageId, attachment ->
                DownlodUtil.downloadFromStorage(
                    requireContext(),
                    attachment.fileNameWithExtension(),
                    attachment.downloadUrl
                ) { localFile ->
                    val localAttachment = LocalAttachment.new(
                        attachment,
                        messageId,
                        attachment.downloadUrl,
                        localFile.canonicalPath,
                        localFile.name,
                        localFile.parentFile!!.absolutePath,
                        LocalAttachment.DocumentStorage.DOCS_SENT.ordinal
                    )
                    viewModel.insertLocalAttachment(localAttachment)
                }

            }
        }
        /* Helps in showing the date header onScrolling.  */
        conversationOnScrollDateHeader =
            ConversationOnScrollDateHeader(binding.dateHeader, requireContext())

        /* Jump to bottom. */
        binding.jumpToBottom.setOnClickListener {
            binding.list.scrollToPosition(0)
            onShouldChangeFabVisibility(shouldHide = true, force = true)
        }

        viewModel.conversationWithRecipients.observe(viewLifecycleOwner) {
            conversation = it.conversationRecord
            messagesAdapter.setConversationWithRecipients(it)
        }

        viewModel.messages.observe(viewLifecycleOwner) {
            messagesAdapter.submitList(it)
            onShouldChangeFabVisibility(shouldHide = (it.isEmpty() || it.size <= 4))
        }

        viewModel.searchData.observe(viewLifecycleOwner) {
            messagesAdapter.setSearchData(it)
            messagesAdapter.notifyDataSetChanged()
            it?.searchMessages?.let { messagesPositions ->
                if (messagesPositions.isNotEmpty()) messageRecyclerView.scrollLinearLayoutToPosition(
                    messagesPositions.first()
                )
            }
        }

    }

    private fun navigateToMessage(messageId: String) {
        viewModel.findMessageIndexOf(messageId)?.let {
            messageRecyclerView.scrollLinearLayoutToPosition(it)
        }
    }


    /**
     * Sets up selectionTracker from messages Only selection.
     * */
    private fun setupSelectionTracker() {
        selectionTracker =
            ConversationFragmentSelection.new(messagesAdapter, binding.list)
        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                InternalLogger.logD(
                    TAG,
                    "onSelectionChanged(size=${selectionTracker.selection.size()})"
                )
                if (selectionTracker.selection.size() > 0) {
                    if (actionMode == null) {
                        //actionMode = activityToolbar.startActionMode(this@ChatActivity)
                        actionMode = (requireActivity() as AppCompatActivity)
                            .startSupportActionMode(messagesSelectionCallback)
                    }
                    actionMode!!.title = selectionTracker.selection.size().toString()
                } else if (actionMode != null) {
                    actionMode!!.finish()
                }
            }
        })

    }

    /**
     * Observes the scrolling and updates `fabScrollToBottom` and timestampHeader accordingly.
     * */
    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            conversationOnScrollDateHeader.updateTimestamp(getFirstVisibleMessage()?.timestamp)
            val hide = messageRecyclerView.findLastVisibleItemPosition() == 0
            onShouldChangeFabVisibility(hide, true)
        }
    }

    /**
     * Toggles the visibility of `fabScrollToBottom` according to Position and ScrollState.
     * */
    fun onShouldChangeFabVisibility(shouldHide: Boolean = false, force: Boolean = false) {
        val lastMessagePosition = messagesAdapter.currentList.lastIndex
        if (messageRecyclerView.findFirstVisibleItemPosition() == lastMessagePosition && !force) {
            binding.jumpToBottom.isGone = true
            return
        } else {
            if (shouldHide) {
                binding.jumpToBottom.isGone = true
            } else {
                binding.jumpToBottom.isVisible = true
            }
            return
        }
    }

    /**
     * Returns first message (from top) on the screen.
     * */
    private fun getFirstVisibleMessage(): MessageRecord? {
        val pos = messageRecyclerView.findLastVisibleItemPosition()
        val l = messagesAdapter.currentList

        if (l.isNotEmpty()) {
            return try {
                l[pos]
            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Error setting date on DateHeader", e)
                null
            }
        }
        return null
    }

    fun onNewMessageSent() {
        try {
            messageRecyclerView.scrollLinearLayoutToPosition(0)
        } catch (e: Exception) {
            InternalLogger.e(TAG, "Unable to scroll to position 0.", e)
        }
    }

    private fun copyMessages() {
        return
    }

    private fun deleteMessages() {
        return
    }

    override fun onShouldShowReactionChooser(messageRecord: MessageRecord) {
        Dialogs.showReactionChooserDialogV2(requireContext()) { reaction ->
            val currentList = messagesAdapter.currentList.toMutableList()
            if (currentList.contains(messageRecord)) {
                val index = currentList.indexOf(messageRecord)
                val reactionRecord = ReactionRecord.new(messageRecord.id, reaction, me.uid)
                messageRecord.addReaction(reactionRecord)
                viewModel.updateMessage(messageRecord)
                currentList[index] = messageRecord
                messagesAdapter.submitList(currentList.toList())
            }
        }
    }

    override fun onShouldShowReactionsInfo(messageRecord: MessageRecord) {
        if (messageRecord.reactions.isEmpty()) return
        Dialogs.showReactionInfoDialog(
            requireContext(),
            messageRecord.reactions,
            viewModel.conversationWithRecipients.value!!.recipients
        )
    }

    override fun onShouldDelete(messageRecord: MessageRecord) {
        viewModel.delete(messageRecord,/*forEveryone*/false)
    }

    override fun onShouldDeleteForEveryone(messageRecord: MessageRecord) {
        viewModel.delete(messageRecord,/*forEveryone*/true)
    }

    companion object {
        private const val TAG = "ConversationFragment"
    }

}
