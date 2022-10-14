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
import android.widget.TextView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.MessageReactionsIndicatorItemBinding
import com.google.android.material.card.MaterialCardView

class ReactionsView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.Messenger_Widget_ReactionsView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val holder: MaterialCardView
    private val textView: TextView
    private var reactionsCount: Int = 0
        set(value) {
            field = value
            if (value <= 0) textView.text = null
            else textView.text = context.getString(R.string.reacted_count_formatted, value)
        }

    init {
        val inflatedView =
            MessageReactionsIndicatorItemBinding.inflate(LayoutInflater.from(context), this, true)
        holder = inflatedView.reactionsHolder
        textView = inflatedView.reactionIndicator
    }

    fun setCount(count: Int) {
        reactionsCount = count
    }

}