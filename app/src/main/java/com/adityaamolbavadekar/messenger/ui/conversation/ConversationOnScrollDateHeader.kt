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

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.extensions.getDate
import java.util.*

class ConversationOnScrollDateHeader(
    private val textView: TextView,
    private val context: Context
) {

    private var isHiding = false

    private fun show() {
        if (textView.isVisible || textView.text == null || textView.text.isEmpty()) return
        else animateIn(textView)
        //textView.isVisible = true
    }

    fun hide() {
        if (textView.isGone || isHiding) return
        isHiding = true
        textView.postDelayed({
            animateOut(textView)
        }, DELAY)
    }

    private fun animateIn(view: View) {
        if (view.isVisible) return
        isHiding = false
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        view.clearAnimation()
        animation.reset()
        animation.startTime = 0
        view.isVisible = true
        view.startAnimation(animation)
    }

    private fun animateOut(view: View) {
        if (view.isGone) return
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        view.clearAnimation()
        animation.startTime = 0
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                view.isGone = true
                isHiding = false
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
        view.startAnimation(animation)
    }

    fun updateTimestamp(timestamp: Long?) {
        if (timestamp == null) return
        textView.text = getDate(timestamp, context)
        show()
    }

    companion object {
        private const val DELAY: Long = 1500
    }

}