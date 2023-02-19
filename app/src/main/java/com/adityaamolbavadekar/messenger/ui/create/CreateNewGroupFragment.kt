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

package com.adityaamolbavadekar.messenger.ui.create

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.contact.picker.ContactPicker
import com.adityaamolbavadekar.messenger.databinding.CreateNewGroupActivityBinding
import com.adityaamolbavadekar.messenger.databinding.GroupSelectedConversationItemBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.managers.CloudStorageManager
import com.adityaamolbavadekar.messenger.model.Id
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.ui.conversation.ConversationActivity
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.extensions.toast
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseItemHolder
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseListAdapter
import com.google.firebase.storage.StorageMetadata

/**
 * Parent Activity is [CreateNewGroupActivity]
 * */
class CreateNewGroupFragment : BindingHelperFragment<CreateNewGroupActivityBinding>() {

    override fun onShouldInflateBinding(): CreateNewGroupActivityBinding {
        return CreateNewGroupActivityBinding.inflate(layoutInflater)
    }

    private lateinit var listAdapter: Adapter
    private val viewModel: CreateGroupViewModel by activityViewModels()
    private val cloudStorageManager = CloudStorageManager()
    private val authManager = AuthManager()
    private val conversationId = Id.getSpecial()
    private var conversationPhotoUrl: String? = null
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                val loader = Dialogs.showLoadingDialog(requireContext())
                cloudStorageManager.savePicture(it,
                    StorageMetadata.Builder()
                        .setCustomMetadata("uid", authManager.uid)
                        .setCustomMetadata("conversationId", conversationId)
                        .setCustomMetadata("isGroup", "true")
                        .setCustomMetadata("isP2P", "false")
                        .build(),
                    object : OnResponseCallback<Uri, Exception> {
                        override fun onSuccess(t: Uri) {
                            showToastMessage(R.string.profile_picture_uploaded)
                            binding.profilePictureImageView.load(t)
                            conversationPhotoUrl = t.toString()
                            loader.dismiss()
                        }

                        override fun onFailure(e: Exception) {
                            loader.dismiss()
                            showToastMessage(R.string.oops_something_went_wrong_try_again_later)
                        }
                    }
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setMeRecipient(Recipient.Builder(prefsManager.getUserObject()!!).build())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarBinding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.new_group)
        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle =
            getString(R.string.add_group_title_and_optional_photo)
        listAdapter = Adapter()
        binding.list.apply {
            adapter = listAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.recipients.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
        binding.profilePictureHolder.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }
        binding.doneButton.setOnClickListener {
            validate()
        }
    }

    private fun validate() {
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        if (title.trim().isEmpty()) {
            return Dialogs.showErrorDialog(
                requireContext(),
                "",
                "Group title is required.",
                getString(R.string.okay)
            ) {}
        } else {
            val loader = Dialogs.showLoadingDialog(requireContext())
            viewModel.createNewGroup(conversationId,
                database,
                title,
                description,
                conversationPhotoUrl,
            )
                .addOnSuccessListener {
                    loader.dismiss()
                    startActivity(
                        ConversationActivity.createNewIntent(requireContext(), it)
                    )
                    requireActivity().finish()
                }
                .addOnFailureListener {
                    loader.dismiss()
                    requireActivity().toast(R.string.oops_something_went_wrong_try_again_later)
                }
        }
    }

    private class Adapter :
        BaseListAdapter<Recipient, Adapter.ItemHolder>(
            ContactPicker.ContactPickerFragment.ContactsPickerAdapter.RecipientDiffUtill()
        ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            return ItemHolder(parent)
        }

        inner class ItemHolder(
            private val b: GroupSelectedConversationItemBinding
        ) :
            BaseItemHolder<Recipient>(b.root) {

            override fun bind(
                t: Recipient,
                position: Int,
                onItemClickCallback: OnItemClickCallback<Recipient>?
            ) {
                super.bind(t, position, onItemClickCallback)
                b.name.text = t.loadName()
                if (t.photoUrl != null) {
                    b.profilePictureImageView.load(Uri.parse(t.photoUrl!!))
                } else b.profilePictureImageView.setImageResource(R.drawable.ic_user_profile_default)
            }

            constructor(viewGroup: ViewGroup) : this(
                GroupSelectedConversationItemBinding.inflate(
                    LayoutInflater.from(viewGroup.context), viewGroup, false
                )
            )
        }
    }


}