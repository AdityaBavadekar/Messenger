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

package com.adityaamolbavadekar.messenger.views.message

import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.LinkPreviewLayoutBinding
import com.adityaamolbavadekar.messenger.model.LinkPreviewInfo
import com.adityaamolbavadekar.messenger.utils.ImageLoader
import com.google.android.material.card.MaterialCardView

class LinkPreviewView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.Messenger_Widget_LinkPreviewView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val holder: MaterialCardView
    private val titleTextView: TextView
    private val bodyTextView: TextView
    private val urlTextView: TextView
    private val imageView: ImageView
    private val imageLoader = ImageLoader.with(this)

    private var internalLinkPreviewInfo: LinkPreviewInfo? = null
        set(value) {
            field = value
            notifyDataChanged()
        }

    init {
        val inflatedView =
            LinkPreviewLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        holder = inflatedView.holder
        titleTextView = inflatedView.linkTitle
        bodyTextView = inflatedView.linkSubtitle
        urlTextView = inflatedView.host
        imageView = inflatedView.previewImageView
    }

    private fun notifyDataChanged() {
        if (internalLinkPreviewInfo == null) {
            titleTextView.text = null
            bodyTextView.text = null
            urlTextView.text = null
            imageLoader.clear(imageView)
            return
        }

        titleTextView.text = internalLinkPreviewInfo!!.title
        bodyTextView.text = internalLinkPreviewInfo!!.body
        urlTextView.text = internalLinkPreviewInfo!!.getHost()
        imageLoader.load(internalLinkPreviewInfo!!.imgUrl!!, imageView, R.drawable.ic_image)
    }

    fun setLinkPreviewInfo(info: LinkPreviewInfo?) {
        internalLinkPreviewInfo = info
    }

    fun getLinkPreviewInfo() = internalLinkPreviewInfo

}