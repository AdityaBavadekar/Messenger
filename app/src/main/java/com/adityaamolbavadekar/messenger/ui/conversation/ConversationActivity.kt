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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.*
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.databinding.ConversationActivityBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.managers.CloudStorageManager
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.model.ConversationDraftMessage
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.ui.conversation_info.ConversationInfoActivity
import com.adityaamolbavadekar.messenger.ui.picture_upload_preview.PictureUploadPreviewActivity
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.Constants.EXTRA_CONVERSATION_ID
import com.adityaamolbavadekar.messenger.utils.Constants.Extras.EXTRA_CONVERSATION_TYPE
import com.adityaamolbavadekar.messenger.utils.KeyboardUtils
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity
import com.adityaamolbavadekar.messenger.utils.extensions.asApplicationClass
import com.adityaamolbavadekar.messenger.utils.extensions.runOnMainThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.views.compose.ComposeBottomFragment
import com.adityaamolbavadekar.messenger.views.compose.EmojiBottomFragment
import com.adityaamolbavadekar.messenger.views.compose.EmojiPopupWindow
import com.google.firebase.storage.StorageMetadata

class ConversationActivity : BaseActivity() {

    private val windowInsetsAnimationCallback = object : WindowInsetsAnimationCompat.Callback(
        DISPATCH_MODE_STOP
    ) {
        override fun onProgress(
            insets: WindowInsetsCompat,
            runningAnimations: MutableList<WindowInsetsAnimationCompat>
        ): WindowInsetsCompat {
            val calculatedMargin = (insets.getInsets(WindowInsetsCompat.Type.ime()).bottom)
            var marginToBeApplied: Int =
                calculatedMargin + resources.getDimension(R.dimen.compose_message_keyboard_open_margin_bottom)
                    .toInt()
            val navBarSize = (insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom)
            if (calculatedMargin == navBarSize) marginToBeApplied = 0
            binding.composeBar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = marginToBeApplied
            }
            return insets
        }
    }

    private lateinit var binding: ConversationActivityBinding
    private lateinit var conversationId: String
    private lateinit var prefsManager: PrefsManager
    private var draftDeleted = false
    private var conversation: ConversationRecord? = null
    private var groupRecipients: MutableList<Recipient> = mutableListOf()
    private val database: DatabaseAndroidViewModel by viewModels {
        DatabaseAndroidViewModel.Factory(application.asApplicationClass().database)
    }
    private val viewModel: ConversationViewModel by viewModels()
    private lateinit var me: Recipient
    private val cloudStorageManager = CloudStorageManager()
    private var conversationFragment: ConversationFragment? = null
    private var latestKeyboardHeight: Int = 0
    private var composeBottomFragment: ComposeBottomFragment? = null
    private var bottomFragmentType = BottomFragmentType.NONE

    enum class BottomFragmentType { ADD, EMOJI, NONE }

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val messageText = it.data!!.getStringExtra(Intent.EXTRA_TEXT) ?: ""
                val contentUris = it.data!!.getStringArrayExtra(Intent.EXTRA_STREAM)!!

                savePicture(PictureUploadPreviewActivity.getUriList(contentUris)) { uris ->
                    val messageRecord =
                        MessageRecord.from(me, conversationId, messageText, uris)
                    sendMessage(messageRecord)
                }
            }
        }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            it?.let { uriList ->
                if (uriList.isNotEmpty()) {
                    showPicturePreview(uriList)
                }
            }
        }


    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)
        conversationId = requireNotNull(intent.getStringExtra(EXTRA_CONVERSATION_ID))
        { "ConversationId cannot be null." }
        binding = ConversationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    binding.conversationFragmentContainer.id,
                    ConversationFragment(),
                    CONVERSATION_FRAGMENT_TAG
                )
                .runOnCommit {
                    supportFragmentManager.findFragmentByTag(CONVERSATION_FRAGMENT_TAG)?.let {
                        conversationFragment = (it as ConversationFragment)
                    }
                }
                .commit()
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        prefsManager = PrefsManager(this)
        val user = prefsManager.getUserObject()!!
        me = Recipient.Builder(user).build()
        val conversationType = intent.getIntExtra(EXTRA_CONVERSATION_TYPE, -1)
        check(conversationType != -1) { "ConversationType cannot be null" }
        InternalLogger.logD(TAG, "ConversationType=$conversationType")
        val p2pUid = intent.getStringExtra(Constants.Extras.EXTRA_USER_UID)
        viewModel.configure(me, conversationId, database)
        viewModel.configureExtras(conversationType, p2pUid)

        /* Get the recipients and conversation info for conversationId. */
        database.getRecipientsOfConversation(conversationId)
            .observe(this) { conversationWithRecipients ->
                viewModel.onLocalConversationDataChanged(conversationWithRecipients)
                InternalLogger.logD(TAG, "ConversationRecipients : $conversationWithRecipients")
            }

        database.getMessages(conversationId).observe(this) {
            viewModel.onLocalMessagesDataChanged(it)
        }

        viewModel.conversationWithRecipients.observe(this) {
            conversation = it.conversationRecord
            if (it.conversationRecord.isGroup) {
                groupRecipients = it.recipients.toMutableList()
            }
            updateToolbar()
        }

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        setupViews()

    }

    private fun setupViews() {

        latestKeyboardHeight = prefsManager.getImeKeyboardHeight()

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.composeBar.setOnImageAddedListener { contentUri, _, _ ->
            showPicturePreview(listOf(contentUri))
        }

        /* Listener to toggle function of sendButton according to messageEditText.
        * Also provides LinkPreview if messageEditText contains urls.
        *  */
        binding.composeBar.addAfterTextChangeListener {
            deleteDraft()
        }

        /** Toggles visibility of emoji keyboard. */
        binding.composeBar.setOnEmojiButtonClickListener {
            if (bottomFragmentType != BottomFragmentType.EMOJI) {
                KeyboardUtils.hideKeyboard(this)
                composeBottomFragment = EmojiBottomFragment(latestKeyboardHeight) {
                    binding.composeBar.appendComposeText(it)
                }
                bottomFragmentType = BottomFragmentType.EMOJI
                addFragmentToBottom()
            } else removeAnyAddedBottomFragments()
        }

        binding.composeBar.setOnEmojiButtonClickListener {
            EmojiPopupWindow.build({
                binding.composeBar.appendComposeText(it)
            }, binding.root, this, latestKeyboardHeight)
        }

        /** Toggles visibility of add bottomsheet. */
        binding.composeBar.setOnAttachListener {
            if (bottomFragmentType != BottomFragmentType.ADD) {
                KeyboardUtils.hideKeyboard(this)
                composeBottomFragment = ComposeAddFragment(latestKeyboardHeight)
                bottomFragmentType = BottomFragmentType.ADD
                addFragmentToBottom()
            } else removeAnyAddedBottomFragments()
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

        viewModel.status.observe(this) {
            val status = StatusParser.parse(it, this)
            if (status != null) {
                binding.subtitle.text = status
                binding.subtitle.isVisible = true
            } else {
                binding.subtitle.isVisible = false
            }
        }

        viewModel.isMessagingRestrictedForMe.observe(this) { isRestricted ->
            if (isRestricted) {
                showOnlyManagersCanSendMassages()
            } else showCanSendMassages()
        }

        binding.permissionManagersOnly.root.setOnClickListener {

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

        binding.root.addOnImeStateListener { isVisible, height ->
            InternalLogger.logD(TAG, "IME : [isVisible=$isVisible] [imeHeight=$height]")
            if (isVisible) {
                if (height > latestKeyboardHeight) this.latestKeyboardHeight = height
                prefsManager.saveImeKeyboardHeight(height)
            }
        }

        initWindowInsetsImeAnimations()

    }

    private fun addFragmentToBottom() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, composeBottomFragment!!)
            .commit()
        KeyboardUtils.hideKeyboard(this)
        binding.composeBar.disableSoftInputKeyboardInput()
    }

    private fun removeAnyAddedBottomFragments(showKeyboard: Boolean = true): Boolean {
        if (composeBottomFragment == null) return false
        composeBottomFragment?.let { fragment ->
            bottomFragmentType = BottomFragmentType.NONE
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
        composeBottomFragment = null
        binding.composeBar.enableSoftInputKeyboardInput()
        if (showKeyboard) {
            KeyboardUtils.showKeyboard(this)
        }
        return true
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

    /**
     * Sends message with messageSender according to the type of conversation like Group,Self,P2P.
     * */
    private fun sendMessage(message: MessageRecord) {
        viewModel.sendMessage(message) {
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

    /**
     * Updates the toolbar title, profile picture and PresenceStatus.
     * */
    private fun updateToolbar() {
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

            binding.titleSubtitleHolder.setOnClickListener {
                startActivity(ConversationInfoActivity.createNewIntent(this, conversation!!))
            }

        }
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
            if (!removeAnyAddedBottomFragments(false)) finish()
        }
    }

    private fun closeImeIfVisible(): Boolean {
        return if (!binding.root.getImeIsVisible()) false
        else {
            KeyboardUtils.hideKeyboard(this)
            true
        }
    }

    private fun savePicture(contentUris: List<Uri>, onSuccess: (List<String>) -> Unit) {
        val loader = Dialogs.showLoadingDialog(this)
        val uploadedReturnUri = mutableListOf<String>()
        for ((index, uri) in contentUris.withIndex()) {
            cloudStorageManager.savePicture(uri,
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
            )
        }
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
                .putExtra(Constants.Extras.EXTRA_CONVERSATION_TYPE, conversation.conversationType())
            if (conversation.isP2P) {
                i.putExtra(Constants.Extras.EXTRA_USER_UID, conversation.p2PRecipientUid())
            }
            return i
        }

        private val TAG = ConversationActivity::class.java.simpleName
        private const val CONVERSATION_FRAGMENT_TAG = "conversation_fragment"
    }

    override fun onPause() {
        super.onPause()
        saveDraft()
        viewModel.executeOnPause()
    }

    override fun onStart() {
        super.onStart()
        if (composeBottomFragment != null && bottomFragmentType != BottomFragmentType.NONE) {
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
                if (InternalLogger.isDebugBuild && conversation?.isGroup == true) {
                    item.isVisible = true
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add_reply) {
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
            viewModel.sendMessage(messageRecord, false) {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun shouldSupportFullscreen(): Boolean {
        return true
    }

}
