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

package com.adityaamolbavadekar.messenger.ui.conversation_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.MainActivity
import com.adityaamolbavadekar.messenger.contact.picker.ContactPicker
import com.adityaamolbavadekar.messenger.databinding.ConversationListFragmentBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.ui.conversation.ConversationActivity
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseItemHolder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Displays list of conversations.
 * */
class ConversationListFragment : BindingHelperFragment<ConversationListFragmentBinding>(),
    BaseItemHolder.OnItemClickCallback<ConversationRecord> {

    override fun onShouldInflateBinding(): ConversationListFragmentBinding {
        return ConversationListFragmentBinding.inflate(layoutInflater)
    }

    private lateinit var conversationListAdapter: ConversationListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        conversationListAdapter = ConversationListAdapter()
        conversationListAdapter.setOnClickListener(this)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.conversationRecyclerView.apply {
            adapter = conversationListAdapter
            layoutManager = linearLayoutManager
        }
        lifecycle.coroutineScope.launch {

            database.getConversations().collect {
                conversationListAdapter.submitList(it)
                if (it.isEmpty()) {
                    binding.conversationRecyclerView.isGone = true
                    binding.noConversationsLayout.isVisible = true
                } else {
                    binding.conversationRecyclerView.isVisible = true
                    binding.noConversationsLayout.isGone = true
                }
            }

            database.getRecipients().collect {
                conversationListAdapter.setRecipients(it)
            }

        }

        (requireActivity() as MainActivity).setOnFabClickListener {
            startActivity(Intent(requireActivity(), ContactPicker::class.java))
        }

    }

    override fun onItemClick(item: ConversationRecord) {
        return startActivity(
            ConversationActivity.createNewIntent(
                requireContext(),
                item.conversationId
            )
        )
    }

    override fun onItemLongClick(item: ConversationRecord) {
        Dialogs.showDeleteConversationDialog(requireContext()) { shouldDelete ->
            if (shouldDelete) {
                database.deleteConversationAndMessages(item)
            }
        }
    }

}