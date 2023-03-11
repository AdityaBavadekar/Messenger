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
import com.adityaamolbavadekar.messenger.databinding.DocumentViewLayoutBinding
import com.adityaamolbavadekar.messenger.model.Attachment
import com.adityaamolbavadekar.messenger.utils.AndroidUtils
import com.adityaamolbavadekar.messenger.utils.ImageLoader
import com.google.android.material.card.MaterialCardView

// TODO: Complete this View and add to RecyclerViewTypes and add support for documents.
class DocumentView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.Messenger_Widget_LinkPreviewView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val holder: MaterialCardView
    private val thumbnailImageView: ImageView
    private val downloadFileImageView: ImageView
    private val fileIconImageView: ImageView
    private val textView: TextView
    private val fileNameTextView: TextView
    private val imageLoader = ImageLoader.with(this)
    private var document: Attachment? = null
    private var clickListener: (Attachment) -> Unit = {}

    init {
        val inflatedView =
            DocumentViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        thumbnailImageView = inflatedView.imageView
        downloadFileImageView = inflatedView.downloadFileImageView
        fileIconImageView = inflatedView.fileTypeImageView
        textView = inflatedView.textView
        fileNameTextView = inflatedView.fileName
        holder = inflatedView.holder
        holder.setOnClickListener {
            document?.let { clickListener(it) }
        }
        downloadFileImageView.setOnClickListener {  }
        AndroidUtils.setGone(downloadFileImageView)
        AndroidUtils.setGone(thumbnailImageView)
        AndroidUtils.setGone(holder)
    }

    fun setOnAttachmentClickListener(listener: (Attachment) -> Unit) {
        clickListener = listener
    }

    fun setDocument(doc: Attachment?) {
        document = doc
        if (document != null) AndroidUtils.setVisible(holder)
        else AndroidUtils.setGone(holder)
        setup()
    }

    private fun setup() {
        document?.let {
            textView.text = StringBuilder()
                .append(it.extension.uppercase())
                .append(" | ")
                .append(it.readableSize().second.toString())
                .append(" ")
                .append(it.readableSize().first.name)
            fileNameTextView.text = it.fileNameWithExtension()

        }
    }


}
