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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.databinding.ConversationInfoListItemBinding
import com.adityaamolbavadekar.messenger.databinding.P2pConversationInfoFragmentBinding
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

/**
 * Shows Person to Person Conversation Information data.
 * */
class P2PConversationInfoFragment : BindingHelperFragment<P2pConversationInfoFragmentBinding>() {

    override fun onShouldInflateBinding(): P2pConversationInfoFragmentBinding {
        return P2pConversationInfoFragmentBinding.inflate(layoutInflater)
    }

    private lateinit var conversationId: String
    private lateinit var recipientUid: String
    private var conversation: ConversationRecord? = null
    private lateinit var recipient: Recipient
    private val cloudDatabaseManager = CloudDatabaseManager()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* This Fragment has a custom toolbar so it as activities actionBar*/
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        /* This Fragment has a custom toolbar layout so it hide default title.*/
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        conversationId =
            requireNotNull(requireActivity().intent.getStringExtra(Constants.EXTRA_CONVERSATION_ID))
            { "ConversationId cannot be null." }
        recipientUid =
            requireNotNull(requireActivity().intent.getStringExtra(Constants.Extras.EXTRA_UID))
            { "RecipientUid cannot be null." }

        InternalLogger.logD(TAG, "ConversationId : $conversationId")
        setupViews()
    }

    private fun setupViews() {

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        val listAdapter = ListItemsAdapter()

        binding.list.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        database.getConversation(conversationId).observe(viewLifecycleOwner) {
            InternalLogger.logD(TAG, "ConversationRecipients : $it")
            conversation = it
        }

        database.getRecipient(recipientUid).observe(viewLifecycleOwner) {
            recipient = it
            listAdapter.submitList(createList())
            updateToolbar()
        }

    }

    private fun createList(): MutableList<ConversationInfoItem> {
        val list = mutableListOf<ConversationInfoItem>()
        list.add(ConversationInfoItem(0, "Phone number : ${recipient.phone ?: "Not added"}") {})
        list.add(ConversationInfoItem(1, "Description : ${recipient.tempAbout}") {})
        list.add(ConversationInfoItem(2, "Audio call") {})
        list.add(ConversationInfoItem(3, "Video call") {})
        list.add(ConversationInfoItem(4, "Share") {})
        list.add(ConversationInfoItem(5, "View in address book") {})
        list.add(ConversationInfoItem(6, "Block") {})
        list.add(ConversationInfoItem(7, "Report") {})
        return list
    }

    /**
     * Updates the toolbar title, profile picture.
     * */
    private fun updateToolbar() {
        recipient.let {
            binding.title.text = it.loadName()
            if (it.photoUrl != null) {
                binding.profilePictureImageView.load(
                    Uri.parse(it.photoUrl),
                    true,
                    ConversationRecord.DEFAULT_P2P_DRAWABLE
                )
            } else binding.profilePictureImageView.setImageResource(ConversationRecord.DEFAULT_P2P_DRAWABLE)
        }
    }

    class ListItemsAdapter :
        ListAdapter<ConversationInfoItem, ListItemsAdapter.Holder>(
            ListItemsDiffUtil()
        ) {

        inner class Holder(private val view: ConversationInfoListItemBinding) :
            RecyclerView.ViewHolder(view.rootCard) {
            fun bind(item: ConversationInfoItem) {
                view.text.text = item.title
                view.rootCard.setOnClickListener { item.action() }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = ConversationInfoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return Holder(view)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(getItem(position))
        }

        class ListItemsDiffUtil : DiffUtil.ItemCallback<ConversationInfoItem>() {
            override fun areItemsTheSame(
                oldItem: ConversationInfoItem,
                newItem: ConversationInfoItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ConversationInfoItem,
                newItem: ConversationInfoItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }

    }

    companion object {
        private val TAG = P2PConversationInfoFragment::class.java.simpleName
    }

}
