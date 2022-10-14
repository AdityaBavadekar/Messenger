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

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.databinding.ConversationFragmentBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.model.ReactionRecord
import com.adityaamolbavadekar.messenger.utils.Constants.EXTRA_CONVERSATION_ID
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.views.MessagesListRecyclerView

/**
 * Fragment that displays a list of messages.
 * Requires Extra : [EXTRA_CONVERSATION_ID]
 * */
class ConversationFragment : BindingHelperFragment<ConversationFragmentBinding>(),
    OnReactionListener {

    override fun onShouldInflateBinding(): ConversationFragmentBinding {
        return ConversationFragmentBinding.inflate(layoutInflater)
    }

    lateinit var activityToolbar: Toolbar
    var actionMode: ActionMode? = null
    lateinit var selectionTracker: SelectionTracker<Long>
    private val viewModel: ConversationViewModel by activityViewModels()
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var conversationId: String
    private var conversation: ConversationRecord? = null
    private lateinit var conversationOnScrollDateHeader: ConversationOnScrollDateHeader
    private lateinit var messagesSelectionCallback: MessagesSelectionCallback
    private lateinit var messageRecyclerView: MessagesListRecyclerView

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

    private fun setupViews() {

        /* Main messages recycler view */
        binding.list.apply {
            messageRecyclerView = this
            messagesAdapter = MessagesAdapter(viewLifecycleOwner)
            adapter = messagesAdapter
            addOnScrollListener(onScrollListener)
        }
        setupSelectionTracker()
        messagesAdapter.setOnReactionListener(this)
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
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                conversationOnScrollDateHeader.hide()
                onShouldChangeFabVisibility()
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            conversationOnScrollDateHeader.updateTimestamp(getFirstVisibleMessage()?.timestamp)
            onShouldChangeFabVisibility()
        }
    }

    /**
     * Toggles the visibility of `fabScrollToBottom` according to Position and ScrollState.
     * */
    fun onShouldChangeFabVisibility(shouldHide: Boolean = false, force: Boolean = false) {
        val lastMessagePosition = messagesAdapter.currentList.lastIndex
        if (messageRecyclerView.findLastVisibleItemPosition() == lastMessagePosition && !force) {
            binding.jumpToBottom.isGone = true
            return
        } else {
            if (shouldHide) {
                binding.jumpToBottom.isGone = true
            } else {
                binding.jumpToBottom.isVisible = true
            }
        }
    }

    /**
     * Returns first message (from top) on the screen.
     * */
    private fun getFirstVisibleMessage(): MessageRecord? {
        val pos = messageRecyclerView.findFirstVisibleItemPosition()
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

    fun onNewMessageSent(index: Int) {
        try {
            messageRecyclerView.scrollLinearLayoutToPosition(0)
        } catch (e: Exception) {
        }
    }

    private fun copyMessages() {
        return
    }

    private fun deleteMessages() {
        return
    }

    override fun onShouldShowReactionChooser(messageRecord: MessageRecord) {
        Dialogs.showReactionChooserDialog(requireContext()) { reaction ->
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

    companion object{
        private const val TAG = "ConversationFragment"
    }

}
