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
import android.text.format.DateUtils
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.contact.ContactsReader
import com.adityaamolbavadekar.messenger.databinding.ContactPickerFragmentBinding
import com.adityaamolbavadekar.messenger.databinding.P2pSelectionItemBinding
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.model.User
import com.adityaamolbavadekar.messenger.model.toRecipientsList
import com.adityaamolbavadekar.messenger.ui.conversation.ConversationActivity
import com.adityaamolbavadekar.messenger.ui.create.CreateNewGroupActivity
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.Permissions
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseItemHolder
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseListAdapter
import java.util.*

class ContactPicker : BaseActivity() {

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)
        setContentView(R.layout.contact_picker_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContactPickerFragment())
                .commit()
        }
    }

    class ContactPickerFragment : BindingHelperFragment<ContactPickerFragmentBinding>(), OnResponseCallback<List<User>, Exception?>,
        BaseItemHolder.OnItemClickCallback<Recipient> {

        private lateinit var contactsPickerAdapter: ContactsPickerAdapter
        private val cloudDatabaseManager = CloudDatabaseManager()

        override fun onShouldInflateBinding(): ContactPickerFragmentBinding {
            return ContactPickerFragmentBinding.inflate(layoutInflater)
        }

        private val contactsPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    ContactsReader(requireContext()).read {
                        if (it.isNotEmpty()) {
                            contactsPickerAdapter.setContactList(it)
                            val lastUploaded = prefsManager.getLastContactsUploaded()
                            if (lastUploaded == Constants.UNKNOWN_VALUE_LONG || !DateUtils.isToday(
                                    lastUploaded
                                )
                            ) {
                                prefsManager.saveLastContactsUploaded(System.currentTimeMillis())
                                cloudDatabaseManager.getContactsManager()
                                    .save(it.map { contactInfo -> contactInfo.number.trim() })
                            }
                        }
                    }
                }
            }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarBinding.toolbar)
            (requireActivity() as AppCompatActivity).supportActionBar?.title =
                getString(R.string.select_contact)
            setHasOptionsMenu(true)
            binding.list.apply {
                contactsPickerAdapter = ContactsPickerAdapter(this@ContactPickerFragment)
                adapter = contactsPickerAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            cloudDatabaseManager.getUsersManager().observeUsers(this)
            cloudDatabaseManager.getUsersManager().getPublicUsersList()
            Permissions.ReadContacts.doIfDenied(requireContext()) {
                contactsPermissionLauncher.launch(Permissions.ReadContacts.permissionName)
            }
        }

        override fun onSuccess(t: List<User>) {
            val recipientsList = t.toRecipientsList(me.uid)
            contactsPickerAdapter.submitList(recipientsList)
            database.insertRecipients(recipientsList)
        }

        override fun onFailure(e: Exception?) {}

        override fun onItemClick(item: Recipient) {
            super.onItemClick(item)
            database.directGetConversationsOfRecipient(item.uid) { recipientWithConversations ->
                InternalLogger.logD(
                    TAG,
                    "RecipientWithConversationsFor(${item.uid}) : ${recipientWithConversations ?: "NULL"}"
                )
                if (recipientWithConversations != null &&
                    recipientWithConversations.conversationRecords.isNotEmpty()
                ) {
                    recipientWithConversations.conversationRecords.forEach { conversationRecord ->
                        if (conversationRecord.isP2P) {
                            startActivity(
                                ConversationActivity.createNewIntent(
                                    requireContext(),
                                    conversationRecord
                                )
                            )
                            requireActivity().finish()
                            return@directGetConversationsOfRecipient
                        }
                    }

                    val conversation = ConversationRecord.newPerson2Person(item, me)
                    database.insertConversation(conversation, listOf(item, me))
                    startActivity(
                            ConversationActivity.createNewIntent(
                                    requireContext(),
                                    conversation
                            )
                    )
                    requireActivity().finish()
                    return@directGetConversationsOfRecipient
                } else {
                    val conversation = ConversationRecord.newPerson2Person(item, me)
                    database.insertConversation(conversation, listOf(item, me))
                    startActivity(
                        ConversationActivity.createNewIntent(
                            requireContext(),
                            conversation
                        )
                    )
                    requireActivity().finish()
                    return@directGetConversationsOfRecipient
                }
            }
        }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            super.onCreateOptionsMenu(menu, inflater)
            inflater.inflate(R.menu.contact_picker_frgment, menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (item.itemId == R.id.action_new_group) {
                startActivity(Intent(requireContext(), CreateNewGroupActivity::class.java))
            }
            return false
        }

        class ContactsPickerAdapter(
            onItemClickCallback: BaseItemHolder.OnItemClickCallback<Recipient>?,
            private val isSelection: Boolean = true
        ) :
            BaseListAdapter<Recipient, ContactsPickerAdapter.ItemHolder>(
                RecipientDiffUtill(), onItemClickCallback
            ) {

            private var contactInfoList = listOf<ContactsReader.ContactInfo>()
            private var contactNumbersList = listOf<String>()

            fun setContactList(list: List<ContactsReader.ContactInfo>) {
                contactInfoList = list
                contactNumbersList = list.map { it.number }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
                return ItemHolder(parent, isSelection)
            }

            inner class ItemHolder(
                private val b: P2pSelectionItemBinding,
                private val isSelection: Boolean
            ) :
                BaseItemHolder<Recipient>(b.root) {

                override fun bind(
                    t: Recipient,
                    position: Int,
                    onItemClickCallback: OnItemClickCallback<Recipient>?
                ) {
                    super.bind(t, position, onItemClickCallback)
                    if (isSelection) {
                        b.root.setOnClickListener {
                            onItemClickCallback?.onItemClick(t)
                        }
                    } else {
                        b.remove.setOnClickListener {
                            onItemClickCallback?.onItemClick(t)
                        }
                        b.remove.isVisible = true
                    }
                    b.name.text = t.loadName()
                    t.tempAbout?.let {
                        b.description.text = it
                        b.description.isVisible = true
                    }
                    if (t.photoUrl != null) {
                        b.profilePictureImageView.load(Uri.parse(t.photoUrl!!))
                    } else b.profilePictureImageView.setImageResource(R.drawable.ic_user_profile_default)
                    t.phone?.let {
                        if (contactNumbersList.contains(it)) {
                            b.contactIndicator.isVisible = true
                        } else {
                            b.contactIndicator.isGone = true
                        }
                    }
                }

                constructor(viewGroup: ViewGroup, isSelection: Boolean) : this(
                    P2pSelectionItemBinding.inflate(
                        LayoutInflater.from(viewGroup.context), viewGroup, false
                    ), isSelection
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

    }

    companion object{
        private val TAG = ContactPicker::class.java.simpleName
    }


}

