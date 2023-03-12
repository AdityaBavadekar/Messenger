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

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.model.*
import com.adityaamolbavadekar.messenger.ui.conversation_info.ConversationInfoActivity
import com.adityaamolbavadekar.messenger.ui.picture_upload_preview.PictureUploadPreviewActivity
import com.adityaamolbavadekar.messenger.utils.*
import com.adityaamolbavadekar.messenger.utils.Constants.EXTRA_CONVERSATION_ID
import com.adityaamolbavadekar.messenger.utils.Constants.Extras.EXTRA_CONVERSATION_TYPE
import com.adityaamolbavadekar.messenger.utils.extensions.runOnMainThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.views.compose.EmojiKeyboard
import com.adityaamolbavadekar.messenger.views.compose.EmojiPopupWindow
import com.google.firebase.storage.StorageMetadata

class ConversationActivity : ParentConversationActivity(), SearchView.OnQueryTextListener {

    override fun setupInput() {
        super.setupInput()
        binding.composeBar.setOnImageAddedListener { contentUri, _, _ ->
            showPicturePreview(listOf(contentUri))
        }

        /* Listener to toggle function of sendButton according to messageEditText.
        * Also provides LinkPreview if messageEditText contains urls.
        *  */
        binding.composeBar.addAfterTextChangeListener {
            deleteDraft()
        }

        binding.composeBar.setOnEmojiButtonClickListener {
            currentInputType = InputType.EMOJI
            KeyboardUtils.hideKeyboard(this)
            EmojiPopupWindow.build({
                binding.composeBar.appendComposeText(it)
            }, binding.root, this, latestKeyboardHeight) {
                currentInputType = InputType.NONE
                KeyboardUtils.showKeyboard(this)
            }
        }

        /** Toggles visibility of add bottom sheet. */
        binding.composeBar.setOnAttachListener {
            currentInputType = InputType.ATTACHMENT
            KeyboardUtils.hideKeyboard(this)
            ComposeAddPopupWindow.build(
                ::onAttachItemSelected,
                binding.root,
                this,
                latestKeyboardHeight
            ) {
                currentInputType = InputType.NONE
                KeyboardUtils.showKeyboard(this)
            }
        }

        /* Send button
        *  if messageEditText is empty shows add image(s) icon.
        *  otherwise shows send message icon.
        */
        binding.composeBar.setOnSendListener {
            deleteDraft()
            //Send message
            val message = MessageRecord.from(
                me,
                conversationId,
                it
            )
            binding.composeBar.extraLinkInfo?.let { info -> message.addLinkInfo(info) }
            sendMessage(message)
        }

        val k = ViewUtil.Stub<EmojiKeyboard>(binding.emojiKeyboardStub)
        //TODO
        binding.composeBar.setOnScheduleListener {
            k.get().setEmojiEnteredListener { }
            k.get().show(latestKeyboardHeight)
        }

        binding.composeBar.setOnAttachImagesListener {
            //Open image picker
            imagePickerLauncher.launch("image/*")
        }

        val extraText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (extraText != null && extraText.trim().isNotEmpty()) {
            binding.composeBar.setComposeText(extraText)
        } else {
            database.getDraftForConversationId(conversationId) {
                runOnMainThread {
                    if (it != null &&
                        it.isNotEmpty() &&
                        binding.composeBar.composeText.toString().trim().isEmpty()
                    ) {
                        binding.composeBar.setComposeText(it.draftMessage)
                    }
                }
            }
        }
    }

    override fun setupTitleBar() {
        super.setupTitleBar()

        binding.titleSubtitleHolder.setOnClickListener {
            startActivity(
                ConversationInfoActivity.createNewIntent(
                    this,
                    conversation!!
                )
            )
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        viewModel.conversationTitle.observe(this) {
            binding.title.text = it
        }

        viewModel.conversationPhoto.observe(this) {
            if (it != null) {
                binding.profilePictureImageView.loadImage(it)
            } else {
                conversation?.defaultIcon()?.let { icon ->
                    binding.profilePictureImageView.setImageResource(icon)
                }
            }
        }

        viewModel.status.observe(this) {
            val status = StatusParser.parse(it, this)
            if (status != null) {
                binding.subtitle.text = status
                binding.subtitle.isVisible = true
            } else {
                binding.subtitle.isVisible = false
            }
        }
    }

    override fun setupMessagingPermissions() {
        super.setupMessagingPermissions()
        viewModel.isMessagingRestrictedForMe.observe(this) { isRestricted ->
            if (isRestricted) {
                showOnlyManagersCanSendMassages()
            } else showCanSendMassages()
        }

        binding.permissionManagersOnly.root.setOnClickListener {

        }

    }

    override fun setupKeyboardListeners() {
        super.setupKeyboardListeners()

        latestKeyboardHeight = prefsManager.getImeKeyboardHeight()

        binding.root.addOnImeStateListener { isVisible, height ->
            InternalLogger.logD(TAG, "IME : [isVisible=$isVisible] [imeHeight=$height]")
            if (isVisible) {
                if (height > latestKeyboardHeight) this.latestKeyboardHeight = height
                prefsManager.saveImeKeyboardHeight(height)
            }
        }

        initWindowInsetsImeAnimations()
    }

    private fun initWindowInsetsImeAnimations() {
        ViewCompat.setWindowInsetsAnimationCallback(
            binding.composeBar,
            windowInsetsAnimationCallback
        )
    }

    private fun showOnlyManagersCanSendMassages() {
        binding.composeBar.isGone = true
        binding.permissionManagersOnly.root.isVisible = true
    }

    private fun showCanSendMassages() {
        binding.permissionManagersOnly.root.isGone = true
        binding.composeBar.isVisible = true
    }

    private fun onAttachItemSelected(id: Int) {
        val selection = try {
            ComposeAddPopupWindow.ComposeAddItem.values()[id]
        } catch (_: Exception) {
            return
        }
        when (selection) {
            ComposeAddPopupWindow.ComposeAddItem.PHOTOS -> {}
            ComposeAddPopupWindow.ComposeAddItem.CONTACT -> {}
            ComposeAddPopupWindow.ComposeAddItem.LOCATION -> {}
            ComposeAddPopupWindow.ComposeAddItem.CAMERA -> {}
            ComposeAddPopupWindow.ComposeAddItem.GIFS -> {}
            ComposeAddPopupWindow.ComposeAddItem.FILES -> {}
            ComposeAddPopupWindow.ComposeAddItem.STICKERS -> {}
            ComposeAddPopupWindow.ComposeAddItem.DOCUMENTS -> {
                documentPickerLauncher.launch("*/*")
            }
        }
    }

    private fun sendMessage(message: MessageRecord) {
        viewModel.sendMessage(message)
            .addOnSuccessListener {
                binding.composeBar.doOnMessageSent()
                conversationFragment?.onNewMessageSent()
            }
            .addOnFailureListener {
                binding.composeBar.doOnMessageSent()
                conversationFragment?.onNewMessageSent()
            }
    }

    private fun deleteDraft() {
        if (draftDeleted) return
        InternalLogger.logD(TAG, "Deleting conversation draft.")
        database.deleteDraftForConversationId(conversationId)
        draftDeleted = true
    }

    private fun saveDraft() {
        val draftContent = binding.composeBar.composeText.toString()
        if (draftContent.trim().isNotEmpty()) {
            InternalLogger.logD(TAG, "Saving conversation draft.")
            val draft = ConversationDraftMessage.new(draftContent, conversationId)
            database.insertOrUpdateDraft(draft)
            draftDeleted = false
        }
    }

    override fun updateTitleBar() {
        super.updateTitleBar()
        if (conversation != null) {

            /* Updates names names of group recipients in the subtitle. */
            conversation?.let {
                if (it.isGroup) {
                    var subtitle = ""
                    groupRecipients.forEach { r ->
                        subtitle += if (r.uid == me.uid) {
                            getString(R.string.you)
                        } else {
                            r.loadFirstName()
                        }
                        subtitle += ", "
                    }
                    binding.subtitle.text = subtitle.removeSuffix(", ")
                    binding.subtitle.isVisible = true
                } else if (it.isSelf) {
                    binding.subtitle.isVisible = false
                }
            }
        }
    }

    fun onShouldAddReply(recipient: Recipient?, messageRecord: MessageRecord) {
        binding.composeBar.setReply(recipient, messageRecord)
    }

    private fun showPicturePreview(uriList: List<Uri>) {
        val urlList = mutableListOf<String>()
        uriList.forEach { urlList.add(it.toString()) }
        val i = Intent(this, PictureUploadPreviewActivity::class.java)
        i.putExtra(Intent.EXTRA_STREAM, urlList.toTypedArray())
        activityLauncher.launch(i)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!closeImeIfVisible()) {
            if (currentInputType == InputType.NONE) finish()
        }
    }

    private fun closeImeIfVisible(): Boolean {
        return if (!binding.root.getImeIsVisible()) false
        else {
            KeyboardUtils.hideKeyboard(this)
            true
        }
    }

    private fun buildStorageMetadata(): StorageMetadata {
        return StorageMetadata.Builder()
            .setCustomMetadata("uid", me.uid)
            .setCustomMetadata("conversationId", conversationId)
            .setCustomMetadata(
                "conversationType",
                conversation!!.conversationType().toString()
            )
            .build()
    }

    private fun savePictures(contentUris: List<Uri>, onSuccess: (List<String>) -> Unit) {
        val loader = Dialogs.showLoadingDialog(this)
        val uploadedReturnUri = mutableListOf<String>()
        val responseCallback = { index: Int ->
            object : OnResponseCallback<Uri, Exception> {
                override fun onSuccess(t: Uri) {
                    uploadedReturnUri.add(t.toString())
                    if (index == contentUris.lastIndex) {
                        loader.dismiss()
                        onSuccess(uploadedReturnUri)
                    }
                }

                override fun onFailure(e: Exception) {
                    loader.dismiss()
                    showToastMessage(R.string.oops_something_went_wrong_try_again_later)
                    return
                }
            }
        }
        contentUris.forEachIndexed { index, uri ->
            cloudStorageManager.savePicture(
                uri,
                buildStorageMetadata(),
                responseCallback(index)
            )
        }

    }

    override fun onPause() {
        super.onPause()
        saveDraft()
        viewModel.executeOnPause()
    }

    override fun onStart() {
        super.onStart()
        if (currentInputType != InputType.NONE) {
            binding.composeBar.disableSoftInputKeyboardInput()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.executeOnDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.conversation_fragment, menu)
        menu.let {
            it.findItem(R.id.action_add_reply)?.let { item ->
                item.isVisible =
                    (InternalLogger.isDebugBuild && conversation?.isGroup == true)
            }
            it.findItem(R.id.action_start_voice_call)?.let { item ->
                item.isVisible = Constants.VOICE_CALL_SUPPORTED
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_reply -> {
                val list = viewModel.messages.value!!
                if (list.isEmpty() || conversation?.isGroup == false) return true
                val last = list.first()
                val sender = groupRecipients.random()
                val messageRecord = MessageRecord.createCopyOf(
                    last,
                    sender.uid,
                    sender.username,
                    System.currentTimeMillis()
                )
                viewModel.sendMessage(messageRecord, false)
            }
            R.id.action_search -> {
                (item.actionView as SearchView).apply {
                    setOnQueryTextListener(this@ConversationActivity)
                    setOnCloseListener {
                        viewModel.stopSearch()
                        true
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun shouldSupportFullscreen(): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onDocumentPicked(docUri: Uri) {
        super.onDocumentPicked(docUri)
        Dialogs.showDefiniteProgressDialog(this, p = { loader, action ->
            cloudStorageManager.uploadDocument(docUri,
                metadata = buildStorageMetadata(),
                onProgress = { progress ->
                    InternalLogger.debugInfo(TAG, "Upload document progress = ${progress}%")
                    action(progress)
                },
                onSuccess = { downloadUrl ->
                    val messageId = Id.get()
                    val map = AndroidUtils.getFileMetadata(docUri, contentResolver!!)
                    val mimeType = map["mimeType"] as String?
                    val fileNameWithExtension = (map["name"] as String)
                    val fileSize = map["size"] as Long
                    val attachment = Attachment.from(
                        docUri,
                        fileNameWithExtension,
                        fileSize,
                        mimeType,
                        downloadUrl
                    )
                    AndroidUtils.saveSentDocumentFile(attachment.extension,docUri, this).also { localFile ->
                        val localAttachment = LocalAttachment.new(
                            attachment,
                            messageId,
                            downloadUrl,
                            localFile.canonicalPath,
                            localFile.name,
                            localFile.parentFile!!.absolutePath,
                            LocalAttachment.DocumentStorage.DOCS_SENT.ordinal
                        )
                        viewModel.insertLocalAttachment(localAttachment)
                    }
                    val messageRecord = MessageRecord.Builder(conversationId)
                        .setId(messageId)
                        .setSender(me)
                        .setDocumentAttachment(attachment)
                        .build()
                    loader.dismiss()
                    sendMessage(messageRecord)
                },
                onFailure = {
                    loader.dismiss()
                    Dialogs.showErrorDialog(
                        this,
                        it.message ?: getString(R.string.oops_something_went_wrong_try_again_later),
                        getString(R.string.okay)
                    ) {}
                }
            )
        })
    }

    override fun onShouldSavePictures(urisList: List<Uri>, messageText: String) {
        super.onShouldSavePictures(urisList, messageText)
        savePictures(urisList) { uris ->
            val messageRecord =
                MessageRecord.from(me, conversationId, messageText, uris)
            sendMessage(messageRecord)
        }
    }

    override fun onShouldShowPicturePreview(urisList: List<Uri>) {
        super.onShouldShowPicturePreview(urisList)
        showPicturePreview(urisList)
    }


    companion object {
        fun createNewIntent(
            context: Context,
            conversationId: String,
            conversationType: Int
        ): Intent {
            return Intent(context, ConversationActivity::class.java)
                .putExtra(EXTRA_CONVERSATION_ID, conversationId)
                .putExtra(EXTRA_CONVERSATION_TYPE, conversationType)
        }

        fun createNewIntent(context: Context, conversation: ConversationRecord): Intent {
            val i = Intent(context, ConversationActivity::class.java)
                .putExtra(EXTRA_CONVERSATION_ID, conversation.conversationId)
                .putExtra(EXTRA_CONVERSATION_TYPE, conversation.conversationType())
            if (conversation.isP2P) {
                i.putExtra(Constants.Extras.EXTRA_USER_UID, conversation.p2PRecipientUid())
            }
            return i
        }

        private val TAG = ConversationActivity::class.java.simpleName
    }

    enum class InputType { ATTACHMENT, EMOJI, NONE }

}
