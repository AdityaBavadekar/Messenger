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

package com.adityaamolbavadekar.messenger.ui.conversation_info

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.contact.picker.ContactPicker.ContactPickerFragment.ContactsPickerAdapter
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.databinding.ConversationInfoParticipantsListBinding
import com.adityaamolbavadekar.messenger.databinding.P2pSelectionItemBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.base.BaseFragment
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.extensions.toast
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseItemHolder
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseListAdapter

/**
 * Shows Group Conversation Information data like number of participants, their details, title, description etc.
 * */
class ConversationParticipantsInfoFragment : BindingHelperFragment<ConversationInfoParticipantsListBinding>(),
    BaseItemHolder.OnItemClickCallback<Recipient>,
    SearchView.OnQueryTextListener {

    override fun onShouldInflateBinding(): ConversationInfoParticipantsListBinding {
        return ConversationInfoParticipantsListBinding.inflate(layoutInflater)
    }

    private lateinit var conversationId: String
    private var conversation: ConversationRecord? = null
    private var groupRecipients: MutableList<Recipient> = mutableListOf()
    private lateinit var recipientsAdapter: RecipientsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* This Fragment has a custom toolbar so it as activities actionBar*/
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        /* This Fragment has a custom toolbar layout so it hide default title.*/
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        setHasOptionsMenu(true)

        conversationId =
            requireNotNull(requireActivity().intent.getStringExtra(Constants.EXTRA_CONVERSATION_ID))
            { "ConversationId cannot be null." }

        InternalLogger.logD(TAG, "ConversationId : $conversationId")
        setupViews()
    }

    private fun setupViews() {

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        binding.list.apply {
            recipientsAdapter = RecipientsAdapter()
            recipientsAdapter.setOnClickListener(this@ConversationParticipantsInfoFragment)
            adapter = recipientsAdapter
            linearLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = linearLayoutManager
        }

        database.getRecipientsOfConversation(conversationId).observe(viewLifecycleOwner) {
            conversation = it.conversationRecord
            groupRecipients = it.recipients.toMutableList()
            recipientsAdapter.submit(groupRecipients.toList())
            updateToolbar()
        }

    }

    /**
     * Updates the toolbar title, profile picture.
     * */
    private fun updateToolbar() {
        binding.toolbar.title =
            getString(R.string.group_count_of_participants, groupRecipients.size)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_iso_selection, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                (item.actionView as SearchView).apply {
                    setOnQueryTextListener(this@ConversationParticipantsInfoFragment)
                    setOnCloseListener {

                        true
                    }
                }
                true
            }
            else -> false
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            if (it.trim().isNotEmpty())
                recipientsAdapter.search(it) { requireContext().toast { "No results found." } }
        }
        return true
    }

    override fun onItemClick(item: Recipient) {
        super.onItemClick(item)
        if (item.uid != me.uid) {
            Dialogs.showRemoveRecipientDialog(requireContext(), item.loadName()) {}
        }
    }

    private class RecipientsAdapter :
        BaseListAdapter<Recipient, RecipientsAdapter.ItemHolder>(
            ContactsPickerAdapter.RecipientDiffUtill()
        ) {

        private var tempRecipientsList = listOf<Recipient>()

        private fun submitToTempList(list: List<Recipient>) {
            tempRecipientsList = list
        }

        fun clearSearchSuggestions() {
            submitList(tempRecipientsList)
        }

        fun search(query: String, onNoSuggestionsFound: () -> Unit) {
            val searchSuggestionList = mutableListOf<Recipient>()
            tempRecipientsList.forEach { recipient ->
                if (recipient.loadName().contains(query, ignoreCase = true) ||
                    recipient.phone?.contains(query, ignoreCase = true) == true
                ) searchSuggestionList.add(recipient)
            }
            if (searchSuggestionList.isNotEmpty()) {
                submitList(searchSuggestionList)
            } else onNoSuggestionsFound()
        }

        fun submit(list: List<Recipient>) {
            submitToTempList(list)
            submitList(list)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            return ItemHolder(parent)
        }

        inner class ItemHolder(
            private val b: P2pSelectionItemBinding,
        ) :
            BaseItemHolder<Recipient>(b.root) {

            override fun bind(
                t: Recipient,
                position: Int,
                onItemClickCallback: OnItemClickCallback<Recipient>?
            ) {
                super.bind(t, position, onItemClickCallback)
                b.root.setOnClickListener {
                    onItemClickCallback?.onItemClick(t)
                }
                b.name.text = t.loadName()
                t.tempAbout?.let {
                    b.description.text = it
                    b.description.isVisible = true
                }
                if (t.photoUrl != null) {
                    b.profilePictureImageView.load(Uri.parse(t.photoUrl!!))
                } else b.profilePictureImageView.setImageResource(R.drawable.ic_user_profile_default)
            }

            constructor(viewGroup: ViewGroup) : this(
                P2pSelectionItemBinding.inflate(
                    LayoutInflater.from(viewGroup.context), viewGroup, false
                )
            )
        }

    }

    companion object{
        private val TAG = ConversationParticipantsInfoFragment::class.java.simpleName
    }
    
}