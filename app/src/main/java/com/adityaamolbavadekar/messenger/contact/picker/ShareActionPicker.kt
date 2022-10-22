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

package com.adityaamolbavadekar.messenger.contact.picker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.databinding.ContactPickerFragmentBinding
import com.adityaamolbavadekar.messenger.databinding.P2pSelectionItemBinding
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.model.User
import com.adityaamolbavadekar.messenger.model.toRecipientsList
import com.adityaamolbavadekar.messenger.ui.conversation.ConversationActivity
import com.adityaamolbavadekar.messenger.ui.create.CreateNewGroupActivity
import com.adityaamolbavadekar.messenger.ui.registration.AuthActivity
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity
import com.adityaamolbavadekar.messenger.utils.base.BaseFragment
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseItemHolder
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseListAdapter

class ShareActionPicker : BaseActivity() {

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)
        setContentView(R.layout.contact_picker_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PickerFragment())
                .commit()
        }
    }

    class PickerFragment : BindingHelperFragment<ContactPickerFragmentBinding>(),
        OnResponseCallback<List<User>, Exception?>, BaseItemHolder.OnItemClickCallback<Recipient> {

        override fun onShouldInflateBinding(): ContactPickerFragmentBinding {
            return ContactPickerFragmentBinding.inflate(layoutInflater)
        }

        private lateinit var contactsPickerAdapter: PickerAdapter
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val info =
                ("Intent.Action=" + requireActivity().intent.action) + "\n" +
                        ("Intent.Data=" + requireActivity().intent.data) + "\n" +
                        ("Intent.Data=" + requireActivity().intent.data) + "\n" +
                        ("Intent.Type=" + requireActivity().intent.type) + "\n" +
                        ("Intent.EXTRA_TEXT=" + requireActivity().intent.getStringExtra(Intent.EXTRA_TEXT)
                                ) + "\n" +
                        "Intent.EXTRA_STREAM.isEqualToNull=" + (requireActivity().intent.getStringExtra(
                    Intent.EXTRA_STREAM
                ) == null).toString() + "\n"
            InternalLogger.logD(TAG, info)
            (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarBinding.toolbar)
            (requireActivity() as AppCompatActivity).supportActionBar?.title =
                getString(R.string.select_contact)
            setHasOptionsMenu(true)
            binding.list.apply {
                contactsPickerAdapter = PickerAdapter(this@PickerFragment)
                adapter = contactsPickerAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            CloudDatabaseManager().getUsersManager().observeUsers(this)
            me = Recipient.Builder(prefsManager.getUserObject()!!).build()
        }

        override fun onSuccess(t: List<User>) {
            contactsPickerAdapter.submitList(t.toRecipientsList(me.uid))
        }

        override fun onFailure(e: Exception?) {}

        override fun onItemClick(item: Recipient) {
            super.onItemClick(item)
            val extraText = if (requireActivity().intent.hasExtra(Intent.EXTRA_TEXT))
                requireActivity().intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""
            else ""
            val recipientWithConversations = database.getConversationsOfRecipient(item.uid).value
            if (recipientWithConversations != null) {
                recipientWithConversations.conversationRecords.forEach {
                    if (it.isP2P) {
                        startActivity(
                            ConversationActivity.createNewIntent(
                                requireContext(),
                                it.conversationId
                            ).putExtra(Intent.EXTRA_TEXT, extraText)
                        )
                        return requireActivity().finish()
                    }
                }
            } else {
                val conversation = ConversationRecord.newPerson2Person(item, me)
                database.insertConversation(conversation, listOf(item, me))
                startActivity(
                    ConversationActivity.createNewIntent(
                        requireContext(),
                        conversation
                    ).putExtra(Intent.EXTRA_TEXT, extraText)
                )
                return requireActivity().finish()
            }
        }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            super.onCreateOptionsMenu(menu, inflater)
            inflater.inflate(R.menu.contact_picker_frgment, menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (item.itemId == R.id.action_new_group) {
                startActivity(Intent(requireContext(), CreateNewGroupActivity::class.java))
                requireActivity().finish()
            }
            return false
        }

        class PickerAdapter(
            onItemClickCallback: BaseItemHolder.OnItemClickCallback<Recipient>?,
        ) :
            BaseListAdapter<Recipient, PickerAdapter.ItemHolder>(
                RecipientDiffUtill(), onItemClickCallback
            ) {

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

            class RecipientDiffUtill : DiffUtil.ItemCallback<Recipient>() {
                override fun areItemsTheSame(oldItem: Recipient, newItem: Recipient): Boolean {
                    return oldItem.uid == newItem.uid
                }

                override fun areContentsTheSame(oldItem: Recipient, newItem: Recipient): Boolean {
                    return oldItem.uid == newItem.uid
                }
            }

        }

        companion object{
            private val TAG = PickerFragment::class.java.simpleName
        }

    }

}

