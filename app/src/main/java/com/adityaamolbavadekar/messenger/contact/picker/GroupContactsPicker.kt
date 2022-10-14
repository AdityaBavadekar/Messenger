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

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.contact.picker.ContactPicker.ContactPickerFragment.ContactsPickerAdapter
import com.adityaamolbavadekar.messenger.databinding.ContactPickerFragmentBinding
import com.adityaamolbavadekar.messenger.databinding.P2pSelectionItemBinding
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.model.User
import com.adityaamolbavadekar.messenger.model.toRecipientsList
import com.adityaamolbavadekar.messenger.ui.create.CreateGroupViewModel
import com.adityaamolbavadekar.messenger.ui.create.CreateNewGroupFragment
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseItemHolder
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseListAdapter

class GroupContactsPicker : BaseActivity() {

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)
        setContentView(R.layout.contact_picker_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GroupContactsPickerFragment())
                .commit()
        }
    }

    class GroupContactsPickerFragment : BindingHelperFragment<ContactPickerFragmentBinding>(),
        OnResponseCallback<List<User>, Exception?> {

        override fun onShouldInflateBinding(): ContactPickerFragmentBinding {
            return ContactPickerFragmentBinding.inflate(layoutInflater)
        }

        private lateinit var conversationsAdapter: GroupContactsPickerAdapter
        private val viewModel: CreateGroupViewModel by activityViewModels()
        private val authManager = AuthManager()
        private val cloudDatabaseManager = CloudDatabaseManager()
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarBinding.toolbar)
            (requireActivity() as AppCompatActivity).supportActionBar?.title =
                getString(R.string.new_group)
            (requireActivity() as AppCompatActivity).supportActionBar?.subtitle =
                getString(R.string.add_participants)
            setHasOptionsMenu(false)
            conversationsAdapter = GroupContactsPickerAdapter(viewModel, true)
            binding.list.apply {
                adapter = conversationsAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            viewModel.recipients.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    //binding.fab.show()
                    (requireActivity() as AppCompatActivity).supportActionBar?.subtitle =
                        it.size.toString()
                } else {
                    //binding.fab.hide()
                    (requireActivity() as AppCompatActivity).supportActionBar?.subtitle =
                        getString(R.string.add_participants)
                }
            }
            binding.fab.show()
            binding.fab.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CreateNewGroupFragment())
                    .commit()
            }
            cloudDatabaseManager.getUsersManager().observeUsers(this)
        }

        class GroupContactsPickerAdapter(
            private val viewModel: CreateGroupViewModel,
            private val isSelection: Boolean = true
        ) :
            BaseListAdapter<Recipient, GroupContactsPickerAdapter.ItemHolder>(
                ContactsPickerAdapter.RecipientDiffUtill()
            ) {

            init {
                val onItemSelectedCallback =
                    object : BaseItemHolder.OnItemClickCallback<Recipient> {
                        override fun onItemClick(item: Recipient) {
                            super.onItemClick(item)
                            if (isSelection) {
                                viewModel.onItemGroupContactsPickerItemClicked(item)
                            } else {
                                viewModel.onItemCreateNewGroupItemClicked(item)
                            }
                        }
                    }
                setOnClickListener(onItemSelectedCallback)
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
                        b.remove.setOnClickListener(null)
                        b.root.setOnClickListener {
                            b.root.isSelected = !b.root.isSelected
                            if (b.root.isSelected) {
                                b.remove.setImageResource(R.drawable.ic_check_box_primary)
                                b.remove.isVisible = true
                            } else {
                                b.remove.isGone = true
                            }
                            onItemClickCallback?.onItemClick(t)
                        }
                    } else {
                        b.remove.setImageResource(R.drawable.ic_cancel)
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
                }

                constructor(viewGroup: ViewGroup, isSelection: Boolean) : this(
                    P2pSelectionItemBinding.inflate(
                        LayoutInflater.from(viewGroup.context), viewGroup, false
                    ), isSelection
                )
            }
        }

        override fun onSuccess(t: List<User>) {
            conversationsAdapter.submitList(t.toRecipientsList(authManager.uid))
        }

        override fun onFailure(e: Exception?) {}

    }

    companion object {
        private val TAG = GroupContactsPicker::class.java.simpleName
    }

}