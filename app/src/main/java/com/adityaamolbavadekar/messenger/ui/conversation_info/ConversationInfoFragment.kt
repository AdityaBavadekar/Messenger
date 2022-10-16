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
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.ConversationInfoFragmentBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.managers.CloudStorageManager
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.model.RemoteConversation
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.storage.StorageMetadata

/**
 * Shows Group Conversation Information data like number of participants, their details, title, description etc.
 * */
class ConversationInfoFragment : BindingHelperFragment<ConversationInfoFragmentBinding>(),
        (RemoteConversation) -> Unit {

    override fun onShouldInflateBinding(): ConversationInfoFragmentBinding {
        return ConversationInfoFragmentBinding.inflate(layoutInflater)
    }

    private lateinit var conversationId: String
    private var conversation: ConversationRecord? = null
    private var groupRecipients: MutableList<Recipient> = mutableListOf()
    private var observerAdded = false
    private val cloudDatabaseManager = CloudDatabaseManager()
    private val conversationsManager = cloudDatabaseManager.getConversationsManager()
    private val cloudStorageManager = CloudStorageManager()

    /* Image Picker Launcher for Group Conversation. */
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                val loader = Dialogs.showLoadingDialog(requireContext())
                cloudStorageManager.savePicture(it,
                    StorageMetadata.Builder()
                        .setCustomMetadata("uid", me.uid)
                        .setCustomMetadata("conversationId", conversationId)
                        .setCustomMetadata(
                            "isGroup",
                            if (conversation!!.isGroup) "true" else "false"
                        )
                        .setCustomMetadata("isP2P", if (conversation!!.isP2P) "true" else "false")
                        .build(),
                    object : OnResponseCallback<Uri, Exception> {
                        override fun onSuccess(t: Uri) {
                            conversation!!.photoUrl?.let { oldUrl ->
                                cloudStorageManager.deleteConversationPicture(Uri.parse(oldUrl))
                            }
                            conversation!!.photoUrl = t.toString()
                            database.updateConversation(conversation!!)
                            conversationsManager
                                .updateGroupConversation(conversation!!.toRemoteConversation())
                                .addOnSuccessListener {
                                    loader.dismiss()
                                }
                                .addOnFailureListener {
                                    showToastMessage(R.string.oops_something_went_wrong_try_again_later)
                                }
                        }

                        override fun onFailure(e: Exception) {
                            loader.dismiss()
                            showToastMessage(R.string.oops_something_went_wrong_try_again_later)
                        }
                    }
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* This Fragment has a custom toolbar so it as activities actionBar*/
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        /* This Fragment has a custom toolbar layout so it hide default title.*/
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

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

        val listAdapter = P2PConversationInfoFragment.ListItemsAdapter()

        binding.list.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.profilePictureImageView.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        /* Get the recipients for conversationId only if it is a group conversation. */
        database.getRecipientsOfConversation(conversationId).observe(viewLifecycleOwner) {
            InternalLogger.logD(TAG, "ConversationRecipients : $it")
            conversation = it.conversationRecord
            groupRecipients = it.recipients.toMutableList()
            listAdapter.submitList(createList())
            updateToolbar()
            addConversationObserver()
        }

    }

    private fun createList(): MutableList<ConversationInfoItem> {
        val list = mutableListOf<ConversationInfoItem>()
        conversation!!.apply {
            val descriptionText = if (description?.trim()?.isNotEmpty() == true)
                conversation!!.description
            else {
                getString(R.string.add_group_description)
            }
            var creator = ""
            groupRecipients.forEach {
                if (it.uid == conversation!!.creatorUid) {
                    creator = it.loadName()
                    return@forEach
                }
            }

            list.add(ConversationInfoItem(1, "Description : $descriptionText") {})
            list.add(ConversationInfoItem(
                2,
                getString(
                    R.string.group_create_by_on, creator,
                    simpleDateFormat(conversation!!.created)
                )
            ) {})
            list.add(
                ConversationInfoItem(
                    3,
                    getString(R.string.group_count_of_participants, groupRecipients.size)
                ) {
                    startActivity(
                        ConversationParticipantsInfoActivity.createNewIntent(
                            requireContext(),
                            conversationId
                        )
                    )
                })
            list.add(ConversationInfoItem(4, "Share link") {})
            list.add(ConversationInfoItem(5, "View starred messages") {})
            list.add(ConversationInfoItem(6, "Report group") {})
            list.add(ConversationInfoItem(7, "Leave group") {})
            if (isManager(me.uid)) {
                list.add(ConversationInfoItem(8, "Delete group") {})
            }
        }
        return list
    }

    /**
     * Observes conversations from Remote Database.
     * */
    private fun addConversationObserver() {
        if (!observerAdded) {
            if (conversation!!.isGroup) {
                conversationsManager
                    .observeGroupConversationProperties(conversationId) { remoteConversation ->
                        InternalLogger.logD(
                            TAG,
                            "Updating RemoteConversationProperties: $remoteConversation"
                        )
                        conversation!!.updateFrom(remoteConversation)
                            .let { database.updateConversation(it) }
                    }
            }
            observerAdded = true
        }
    }

    /**
     * Updates the toolbar title, profile picture.
     * */
    private fun updateToolbar() {
        if (conversation != null) {
            binding.title.text =
                if (conversation!!.isSelf) getString(R.string.you)
                else conversation!!.title
            if (conversation!!.photoUrl != null) {
                binding.profilePictureImageView.load(Uri.parse(conversation!!.photoUrl))
            } else binding.profilePictureImageView.setImageResource(conversation!!.defaultIcon())
        }
    }

    override fun invoke(remoteConversation: RemoteConversation) {
        conversation?.updateFrom(remoteConversation)?.let { database.updateConversation(it) }
    }

    companion object {
        private val TAG = ConversationInfoFragment::class.java.simpleName
    }

}