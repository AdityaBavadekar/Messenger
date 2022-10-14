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

package com.adityaamolbavadekar.messenger.views.compose

import android.content.Context
import android.content.res.ColorStateList
import android.net.Uri
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.ComposeLayoutBinding
import com.adityaamolbavadekar.messenger.databinding.LinkPreviewLayoutShortBinding
import com.adityaamolbavadekar.messenger.model.LinkPreviewInfo
import com.adityaamolbavadekar.messenger.utils.Operation
import com.adityaamolbavadekar.messenger.utils.extensions.containsUrls
import com.adityaamolbavadekar.messenger.utils.extensions.extractUrls
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.linkpreview.LinkPreview
import com.adityaamolbavadekar.messenger.views.CircularMenuItemButton
import com.adityaamolbavadekar.messenger.views.ConversationComposeEditText
import com.adityaamolbavadekar.messenger.views.OnImageAddedListener

class ComposeMessageBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), Operation<LinkPreviewInfo> {

    private val input: ConversationComposeEditText
    private val addButton: CircularMenuItemButton
    private val emojiButton: CircularMenuItemButton
    private val sendButton: CircularMenuItemButton
    private val linkPreview: LinkPreviewLayoutShortBinding
    private val innerContainer: LinearLayout
    private val fragmentContainer: FrameLayout
    private var afterInputTextChangedListener: () -> Unit = {}
    private var onSendListener: (String) -> Unit = {}
    private var onAttachImagesListener: () -> Unit = {}
    private var currentlyExtractedLinks: List<String> = listOf()
    private var linkInfo: LinkPreviewInfo? = null
    private var buttonsTint: ColorStateList? = null
    private var isSentButtonInSendState = false
    private var isPlainTextOnly = false

    init {
        val binding =
            ComposeLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        input = binding.messageInput
        addButton = binding.addButton
        emojiButton = binding.emojiButton
        sendButton = binding.sendButton
        linkPreview = binding.linkPreview
        innerContainer = binding.editContainer
        fragmentContainer = binding.fragmentContainer
        context.withStyledAttributes(attrs, R.styleable.ComposeMessageBar) {
            toggleVisibility(
                addButton,
                getBoolean(R.styleable.ComposeMessageBar_addButtonVisible, true)
            )
            toggleVisibility(
                emojiButton,
                getBoolean(R.styleable.ComposeMessageBar_emojiButtonVisible, true)
            )
            buttonsTint = getColorStateList(R.styleable.ComposeMessageBar_buttonIconTint)
            input.hint = getString(R.styleable.ComposeMessageBar_inputEditTextHint)
            input.maxLines = getInt(R.styleable.ComposeMessageBar_inputEditTextMaxLines, 4)
            setAllowRichContent(
                getBoolean(
                    R.styleable.ComposeMessageBar_inputAllowRichContent,
                    true
                )
            )
            isPlainTextOnly = getBoolean(R.styleable.ComposeMessageBar_plainTextOnly, false)
            getDrawable(
                R.styleable.ComposeMessageBar_innerContainerBackground
            )?.let {
                innerContainer.background = it
            }
            getText(R.styleable.ComposeMessageBar_android_text)?.let {
                input.setText(it)
            }
        }
        buttonsTint?.let {
            addButton.setButtonTintList(it)
            emojiButton.setButtonTintList(it)
            sendButton.setButtonTintList(it)
        }
        if (isPlainTextOnly) {
            sendButton.setButtonImageRes(R.drawable.ic_send)
            setAllowRichContent(false)
        } else {
            sendButton.setButtonImageRes(R.drawable.ic_add_image)
            ViewCompat.setTooltipText(this.emojiButton, context.getString(R.string.add_emojis))
            setAllowRichContent(true)
        }

        if (isPlainTextOnly) {
            toggleVisibility(addButton, false)
            ViewCompat.setTooltipText(sendButton, context.getString(R.string.send))
            input.addAfterTextChangeListener(getPlainTextOnlyTextChangedListener())
            sendButton.setOnClickListener {
                onSendListener(input.text.toString())
            }
        } else {
            input.addAfterTextChangeListener(getDefaultTextChangedListener())
            sendButton.setOnClickListener {
                if (isSentButtonInSendState) onSendListener(input.text.toString())
                else onAttachImagesListener()
            }
        }

        ViewGroupCompat.setTransitionGroup(this, true)
    }

    private fun toggleVisibility(v: View, visible: Boolean) {
        if (visible) v.isVisible = true
        else v.isGone = true
    }

    fun setOnSendListener(listener: (String) -> Unit) {
        this.onSendListener = listener
    }

    fun setOnEmojiButtonClickListener(listener: OnClickListener) {
        this.emojiButton.setOnClickListener(listener)
    }

    fun setOnEmojiButtonLongClickListener(listener: OnLongClickListener) {
        this.emojiButton.setOnLongClickListener(listener)
    }

    fun setOnAttachListener(listener: OnClickListener?) {
        this.addButton.setOnClickListener(listener)
    }

    fun setOnAttachImagesListener(listener: () -> Unit) {
        this.onAttachImagesListener = listener
    }

    fun setAllowRichContent(allow: Boolean) {
        this.input.setAllowRichContent(allow)
    }

    fun addAfterTextChangeListener(listener: () -> Unit) {
        this.afterInputTextChangedListener = listener
    }

    fun doOnMessageSent() {
        clearComposeText()
        linkInfo = null
        initLinks()
    }

    fun disableSoftInputKeyboardInput() {
        input.showSoftInputOnFocus = false
    }

    fun enableSoftInputKeyboardInput() {
        input.showSoftInputOnFocus = true
    }

    fun clearComposeText() {
        input.text = null
    }

    fun setComposeText(text: String?) {
        input.setText(text, TextView.BufferType.EDITABLE)
        input.setSelection(text?.length ?: 0)
    }

    fun appendComposeText(text: String) {
        val inputText = composeText
        if (inputText != null) {
            setComposeText(inputText.toString() + text)
        } else {
            setComposeText(text)
        }
    }

    val composeText: Editable?
        get() {
            return input.text
        }

    val extraLinkInfo: LinkPreviewInfo?
        get() {
            return linkInfo
        }

    /**
     * Sets a listener to be called when a new image is added. This might be coming from copy &
     * paste or a software keyboard inserting an image.
     */
    fun setOnImageAddedListener(listener: OnImageAddedListener?) {
        input.setOnImageAddedListener(listener)
    }

    override fun addOnSuccessListener(result: LinkPreviewInfo) {
        this.linkInfo = result
        initLinks()
    }

    override fun addOnFailureListener(error: Exception?) {
        this.linkInfo = null
        initLinks()
    }

    /**
     * Shows link preview.
     * This function may also be called when binding is null
     * like after destroyView so we need to perform a check with [isAttachedToWindow]
     * */
    private fun initLinks() {
        linkInfo?.let { result ->
            if (currentlyExtractedLinks.contains(result.url) && isAttachedToWindow) {
                linkPreview.root.isVisible = true
                linkPreview.apply {

                    if (result.getHost() != null) {
                        linkPreview.host.text = result.getHost()
                        linkPreview.host.isVisible = true
                    } else {
                        linkPreview.host.isGone = true
                    }

                    if (result.title != null) {
                        linkPreview.linkTitle.text = result.title
                        linkPreview.linkTitle.isVisible = true
                    } else {
                        linkPreview.linkTitle.isGone = true
                    }

                    if (result.body != null) {
                        linkPreview.linkSubtitle.text = result.body
                        linkPreview.linkSubtitle.isVisible = true
                    } else {
                        linkPreview.linkSubtitle.isGone = true
                    }

                    if (result.imgUrl != null) {
                        linkPreview.previewImageView.load(
                            Uri.parse(result.imgUrl),
                            false
                        )
                        linkPreview.previewImageViewCard.isVisible = true
                    } else {
                        linkPreview.previewImageViewCard.isGone = true
                    }
                }
            } else {
                linkPreview.root.isGone = true
            }
        }
        if (linkInfo == null) {
            linkPreview.root.isGone = true
        }
    }

    private fun getPlainTextOnlyTextChangedListener(): (Editable?) -> Unit = {
        sendButton.isEnabled = true
        if (it.toString().trim().isNotEmpty()) {
            checkIfLinksChanged(it)
        } else linkPreview.root.isGone = true
        afterInputTextChangedListener()
    }

    private fun getDefaultTextChangedListener(): (Editable?) -> Unit = {
        if (it.toString().trim().isNotEmpty()) {
            sendButton.setButtonImageRes(R.drawable.ic_send)
            ViewCompat.setTooltipText(sendButton, context.getString(R.string.send))
            isSentButtonInSendState = true
            checkIfLinksChanged(it)
        } else {
            sendButton.setButtonImageRes(R.drawable.ic_add_image)
            ViewCompat.setTooltipText(sendButton, context.getString(R.string.attach_pictures))
            isSentButtonInSendState = false
            linkPreview.root.isGone = true
        }
        afterInputTextChangedListener()
    }

    fun checkIfLinksChanged(it:Editable?){
        if (it.toString().containsUrls) {
            //Get urls to first url preview
            currentlyExtractedLinks = it.toString().extractUrls()
            LinkPreview.get(currentlyExtractedLinks[0], this)
        }
    }

}