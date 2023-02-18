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

package com.adityaamolbavadekar.messenger.views

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.core.view.OnReceiveContentListener
import androidx.core.view.ViewCompat
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

typealias OnImageAddedListener = (contentUri: Uri, mimeType: String, label: String) -> Unit

private val SUPPORTED_MIME_TYPES = arrayOf(
    "image/jpeg",
    "image/jpg",
    "image/png",
    "image/gif"
)

/**
 * A custom EditText with the ability to handle copy & paste of texts and images. This also works
 * with a software keyboard that can insert images.
 */
class ConversationComposeEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private var onImageAddedListener: OnImageAddedListener? = null
    private val onReceiveContentListener: OnReceiveContentListener =
        OnReceiveContentListener { _, payload ->
            val (content, remaining) = payload.partition { it.uri != null }
            if (content != null) {
                val clip = content.clip
                val mimeType = SUPPORTED_MIME_TYPES.find { clip.description.hasMimeType(it) }
                if (mimeType != null && clip.itemCount > 0) {
                    val uri = clip.getItemAt(0).uri
                    val label =
                        clip.description.label.toString()
                    InternalLogger.debugInfo(
                        TAG,
                        "Insertion : Uri = $uri, Type = $mimeType Label = $label"
                    )
                    onImageAddedListener?.invoke(
                        uri,
                        mimeType, label
                    )
                }
            }
            remaining
        }

    init {
        setAllowRichContent(true)
        setBackgroundColor(Color.TRANSPARENT)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            setTextCursorDrawable(R.drawable.compose_edit_text_cursor)
        }
    }

    /**
     * Sets a listener to be called when a new image is added. This might be coming from copy &
     * paste or a software keyboard inserting an image.
     */
    fun setOnImageAddedListener(listener: OnImageAddedListener?) {
        onImageAddedListener = listener
    }

    /**
     * Sets a listener to be called when a new image is added. This might be coming from copy &
     * paste or a software keyboard inserting an image.
     */
    fun setAllowRichContent(b: Boolean) {
        if (!b) ViewCompat.setOnReceiveContentListener(this, null, null)
        else ViewCompat.setOnReceiveContentListener(
            this,
            SUPPORTED_MIME_TYPES,
            onReceiveContentListener
        )
    }

    fun addAfterTextChangeListener(listener: (Editable?) -> Unit) {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listener(s)
            }

            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        addTextChangedListener(textWatcher)
    }

    companion object {
        private val TAG = ConversationComposeEditText::class.java.simpleName
    }

}