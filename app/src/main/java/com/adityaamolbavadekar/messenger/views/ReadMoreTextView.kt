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

package com.adityaamolbavadekar.messenger.views

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.adityaamolbavadekar.messenger.R

/**
 * Wraps [TextView] to provide "Read more" facility.
 * */
class ReadMoreTextView constructor(private val textView: TextView) :
    View.OnClickListener {

    private var longText: SpannableStringBuilder? = null
    private val readMoreText: String = textView.context.getString(R.string.read_more)
    private var isExpanded = false

    init {
        textView.addTextChangedListener {
            validate(it?.toString())
        }
    }

    private fun validate(text: String?) {
        if (text == null) return
        longText = SpannableStringBuilder(text)
        if (textView.lineCount > MAX_READ_MORE_LINES) addReadMoreFunctionality()
    }

    private fun addReadMoreFunctionality() {
        val lineEndIndex = textView.layout.getLineEnd(MAX_READ_MORE_LINES - 1)
        val baseText = longText!!.subSequence(0..lineEndIndex)
        val newText = "$baseText $readMoreText"
        val indexOfReadMoreText = newText.indexOf(readMoreText)
        val clickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick(widget)
            }
        }
        val stringBuilder = SpannableStringBuilder(newText)
        stringBuilder.setSpan(
            clickable,
            indexOfReadMoreText,
            indexOfReadMoreText + readMoreText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = stringBuilder
    }

    companion object {
        private const val MAX_READ_MORE_LINES = 120
        fun wrap(textView: TextView) = ReadMoreTextView(textView)
    }

    override fun onClick(v: View?) {

    }

}