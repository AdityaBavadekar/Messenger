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

package com.adityaamolbavadekar.messenger.views.message

import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.MessageReplyIndicatorViewBinding
import com.adityaamolbavadekar.messenger.model.MessageReplyRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.utils.ImageLoader
import com.google.android.material.card.MaterialCardView

class ReplyView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.Messenger_Widget_ReplyView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val holder: MaterialCardView
    private val titleTextView: TextView
    private val messageTextView: TextView
    private val attachmentImageView: ImageView
    private val imageLoader = ImageLoader.with(this)

    private var sender: Recipient? = null
        set(value) {
            field = value
            notifyTitleChanged()
        }

    private var messageReplyRecord: MessageReplyRecord? = null
        set(value) {
            field = value
            notifyDataChanged()
        }

    init {
        val inflatedView =
            MessageReplyIndicatorViewBinding.inflate(LayoutInflater.from(context), this, true)
        holder = inflatedView.replyHolder
        titleTextView = inflatedView.replyTitle
        messageTextView = inflatedView.replyMessage
        attachmentImageView = inflatedView.replyImageView
    }

    private fun notifyDataChanged() {
        if (messageReplyRecord == null) {
            titleTextView.text = null
            messageTextView.text = null
            titleTextView.text = null
            imageLoader.clear(attachmentImageView)
            return
        }

        messageTextView.text = messageReplyRecord!!.message
        messageReplyRecord!!.attachment?.let {
            imageLoader.load(
                it,
                attachmentImageView,
                R.drawable.ic_image
            )
        }
    }

    private fun notifyTitleChanged() {
        sender?.let { titleTextView.text = it.loadName() }
    }

    fun setMessageRecord(message: MessageReplyRecord?) {
        messageReplyRecord = message
    }

    fun setMessageSender(sender:Recipient?) {
        this.sender = sender
    }

    fun getMessageRecord() = messageReplyRecord

}