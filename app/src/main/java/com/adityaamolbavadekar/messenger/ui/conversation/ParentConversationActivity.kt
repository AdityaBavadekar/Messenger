package com.adityaamolbavadekar.messenger.ui.conversation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.databinding.ConversationActivityBinding
import com.adityaamolbavadekar.messenger.managers.CloudStorageManager
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.ui.picture_upload_preview.PictureUploadPreviewActivity
import com.adityaamolbavadekar.messenger.utils.AndroidUtils
import com.adityaamolbavadekar.messenger.utils.ColorUtils
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity
import com.adityaamolbavadekar.messenger.utils.extensions.asApplicationClass
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

open class ParentConversationActivity : BaseActivity() {

    private val TAG = javaClass.simpleName
    protected lateinit var binding: ConversationActivityBinding
    protected lateinit var conversationId: String
    protected lateinit var prefsManager: PrefsManager
    protected var draftDeleted = false
    protected var conversation: ConversationRecord? = null
    protected var groupRecipients: MutableList<Recipient> = mutableListOf()
    protected val database: DatabaseAndroidViewModel by viewModels {
        DatabaseAndroidViewModel.Factory(application.asApplicationClass().database)
    }
    protected val viewModel: ConversationViewModel by viewModels()
    protected lateinit var me: Recipient
    protected val cloudStorageManager = CloudStorageManager()
    protected var conversationFragment: ConversationFragment? = null
    protected var latestKeyboardHeight: Int = 0
    private lateinit var colorUtils: ColorUtils
    protected var currentInputType: ConversationActivity.InputType =
        ConversationActivity.InputType.NONE

    protected val windowInsetsAnimationCallback = object : WindowInsetsAnimationCompat.Callback(
        DISPATCH_MODE_STOP
    ) {
        override fun onProgress(
            insets: WindowInsetsCompat,
            runningAnimations: MutableList<WindowInsetsAnimationCompat>
        ): WindowInsetsCompat {
            val calculatedMargin = (insets.getInsets(WindowInsetsCompat.Type.ime()).bottom)
            val defaultMargin =
                resources.getDimension(R.dimen.compose_message_keyboard_open_margin_bottom)
                    .toInt()
            var marginToBeApplied: Int = calculatedMargin
            if (marginToBeApplied < defaultMargin) marginToBeApplied = defaultMargin
            binding.composeBar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                InternalLogger.logI(
                    TAG,
                    "Updating bottomMargin og composeBar to $marginToBeApplied"
                )
                bottomMargin = marginToBeApplied
            }
            return insets
        }
    }

    protected val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val messageText = it.data!!.getStringExtra(Intent.EXTRA_TEXT) ?: ""
                val contentUris = it.data!!.getStringArrayExtra(Intent.EXTRA_STREAM)!!
                InternalLogger.debugInfo(
                    "ConversationActivity",
                    "**ActivityResult**\nMessage : $messageText\nContentUris :$contentUris"
                )
                onShouldSavePictures(
                    PictureUploadPreviewActivity.getUriList(contentUris),
                    messageText
                )
            }
        }

    protected val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            it?.let { uriList ->
                if (uriList.isNotEmpty()) {
                    onShouldShowPicturePreview(uriList)
                }
            }
        }

    protected val documentPickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let { docUri ->
                onDocumentPicked(docUri)
            }
        }

    open fun onDocumentPicked(docUri: Uri) {}
    open fun onShouldShowPicturePreview(urisList: List<Uri>) {}
    open fun onShouldSavePictures(urisList: List<Uri>, messageText: String) {}

    open fun setupTitleBar() {}
    open fun updateTitleBar() {}
    open fun setupInput() {}
    open fun setupMessagingPermissions() {}
    open fun setupKeyboardListeners() {}
    open fun setupWallpaper() {
        binding.wallpaperImageView
    }
    private fun setupViews() {
        setupTitleBar()
        setupInput()
        setupMessagingPermissions()
        setupKeyboardListeners()
        setupWallpaper()
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)

        conversationId = requireNotNull(intent.getStringExtra(Constants.EXTRA_CONVERSATION_ID))
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
                    supportFragmentManager
                        .findFragmentByTag(CONVERSATION_FRAGMENT_TAG)
                        ?.let {
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
        val conversationType = intent.getIntExtra(Constants.Extras.EXTRA_CONVERSATION_TYPE, -1)
        check(conversationType != -1) { "ConversationType cannot be null" }
        InternalLogger.logD(TAG, "ConversationType=$conversationType")
        val p2pUid = intent.getStringExtra(Constants.Extras.EXTRA_USER_UID)
        viewModel.configure(me, conversationId, database)
        viewModel.configureExtras(conversationType, p2pUid)

        /* Get the recipients and conversation info for conversationId. */
        database.getRecipientsOfConversation(conversationId)
            .observe(this) { conversationWithRecipients ->
                viewModel.onLocalConversationDataChanged(conversationWithRecipients)
                InternalLogger.logD(
                    TAG,
                    "ConversationRecipients : $conversationWithRecipients"
                )
            }

        database.getMessages(conversationId).observe(this) {
            viewModel.onLocalMessagesDataChanged(it)
        }

        viewModel.conversationWithRecipients.observe(this) {
            conversation = it.conversationRecord
            if (it.conversationRecord.isGroup) {
                groupRecipients = it.recipients.toMutableList()
            }
            updateTitleBar()
        }

        colorUtils = ColorUtils(this)
        AndroidUtils.setStatusBarColor(window, colorUtils.getSurfaceColor())
        AndroidUtils.setNavigationBarColor(window, colorUtils.getSurfaceColor())

        setupViews()
    }

    companion object {
        private const val CONVERSATION_FRAGMENT_TAG = "conversation_fragment"
    }

}