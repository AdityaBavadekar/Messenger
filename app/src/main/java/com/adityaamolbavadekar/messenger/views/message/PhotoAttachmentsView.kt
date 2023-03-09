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

import android.annotation.SuppressLint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.PhotoAttachmentsViewLayoutBinding
import com.adityaamolbavadekar.messenger.utils.ImageLoader

class PhotoAttachmentsView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.Messenger_Widget_LinkPreviewView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val linear0: LinearLayout
    private val linear1: LinearLayout
    private val imageView0: ImageView
    private val imageView1: ImageView
    private val imageView2: ImageView
    private val imageView3: ImageView
    private val imageView3Holder: FrameLayout
    private val imageView3BlurTextView: TextView
    private val imageLoader = ImageLoader.with(this)
    private var clickListener: (List<String>) -> Unit = {}

    private var attachments: MutableList<String> = mutableListOf()

    init {
        val inflatedView =
            PhotoAttachmentsViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        linear0 = inflatedView.linearZero
        linear1 = inflatedView.linearOne
        imageView0 = inflatedView.imageViewZero
        imageView1 = inflatedView.imageViewOne
        imageView2 = inflatedView.imageViewTwo
        imageView3 = inflatedView.imageViewThree
        imageView3Holder = inflatedView.imageViewThreeHolder
        imageView3BlurTextView = inflatedView.blurTextView
        inflatedView.holder.setOnClickListener {
            clickListener(attachments)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun notifyDataChanged() {
        if (attachments.isEmpty()) {
            imageLoader.clear(imageView0)
            imageLoader.clear(imageView1)
            imageLoader.clear(imageView2)
            imageLoader.clear(imageView3)
            imageView3Holder.isGone = true
            imageView3BlurTextView.text = null
            imageView3BlurTextView.isVisible = false
            isVisible = false
            return
        }

        val placeholderDrawable = R.drawable.ic_image
        when (val imagesSize = attachments.size) {
            1 -> {
                imageView0.isVisible = true
                imageLoader.load(attachments[0], imageView0, placeholderDrawable)
                imageView1.isGone = true
                linear1.isGone = true
            }
            2 -> {
                imageView0.isVisible = true
                imageLoader.load(attachments[0], imageView0, placeholderDrawable)
                imageView1.isVisible = true
                imageLoader.load(attachments[1], imageView1, placeholderDrawable)
                linear1.isGone = true
            }
            3 -> {
                imageView0.isVisible = true
                imageLoader.load(attachments[0], imageView0, placeholderDrawable)
                imageView1.isVisible = true
                imageLoader.load(attachments[1], imageView1, placeholderDrawable)
                imageView2.isVisible = true
                imageLoader.load(attachments[2], imageView2, placeholderDrawable)
                imageView3Holder.isGone = true
                imageView3BlurTextView.isVisible = false
                imageView3.isGone = true
                linear1.isVisible = true
            }
            4 -> {
                imageView0.isVisible = true
                imageLoader.load(attachments[0], imageView0, placeholderDrawable)
                imageView1.isVisible = true
                imageLoader.load(attachments[1], imageView1, placeholderDrawable)
                imageView2.isVisible = true
                imageLoader.load(attachments[2], imageView2, placeholderDrawable)
                imageView3Holder.isVisible = true
                imageView3BlurTextView.isVisible = false
                imageLoader.load(attachments[3], imageView3, placeholderDrawable)
                linear1.isVisible = true
            }
            else -> {
                imageView0.isVisible = true
                imageLoader.load(attachments[0], imageView0, placeholderDrawable)
                imageView1.isVisible = true
                imageLoader.load(attachments[1], imageView1, placeholderDrawable)
                imageView2.isVisible = true
                imageLoader.load(attachments[2], imageView2, placeholderDrawable)
                //imageView3.isVisible = true
                //imageLoader.load(attachments[3], imageView3, placeholderDrawable)
                imageView3Holder.isVisible = true
                imageView3BlurTextView.text = "+${imagesSize - 3}"
                imageView3BlurTextView.isVisible = true
                linear1.isVisible = true
            }
        }


    }

    fun setOnAttachmentsClickListener(listener: (List<String>) -> Unit) {
        clickListener = listener
    }

    fun addAttachments(urls: List<String>) {
        checkNotExistsAndAdd(urls)
        notifyDataChanged()
    }

    private fun checkNotExistsAndAdd(urls: List<String>) {
        urls.forEach { if (!attachments.contains(it)) attachments.add(it) }
    }

    fun removeAllAttachments() {
        attachments.clear()
        notifyDataChanged()
    }

}
