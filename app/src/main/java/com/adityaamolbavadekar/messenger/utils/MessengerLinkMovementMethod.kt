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

package com.adityaamolbavadekar.messenger.utils

import android.graphics.RectF
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.view.MotionEvent
import android.widget.TextView

object MessengerLinkMovementMethod : LinkMovementMethod() {

    override fun onTouchEvent(
        widget: TextView, buffer: Spannable,
        event: MotionEvent
    ): Boolean {

        val action = event.action

        if (action == MotionEvent.ACTION_UP) {
            val spans = findClickableSpan(widget, buffer, event)
            if (spans.isNotEmpty()) {
                val link = spans[0]
                link.onClick(widget)
                return true
            }
        }
        return super.onTouchEvent(widget, buffer, event)
    }

    private fun findClickableSpan(
        widget: TextView, buffer: Spannable,
        event: MotionEvent
    ): List<CustomLinkHandlerSpan> {

        val bounds = RectF()

        var touchX = event.x.toInt()
        var touchY = event.y.toInt()

        touchX -= widget.totalPaddingLeft
        touchY -= widget.totalPaddingTop

        touchX += widget.scrollX
        touchY += widget.scrollY

        val layout = widget.layout
        val line = layout.getLineForVertical(touchY)
        val offset = layout.getOffsetForHorizontal(line, touchX.toFloat())

        bounds.left = layout.getLineLeft(line)
        bounds.top = layout.getLineTop(line).toFloat() + bounds.left
        bounds.right = layout.getLineWidth(line)
        bounds.bottom = layout.getLineBottom(line).toFloat()

        if (bounds.contains(touchX.toFloat(), touchY.toFloat())) {

            val spans = buffer.getSpans(
                offset, offset,
                CustomLinkHandlerSpan::class.java
            )
            return spans.toList()
        }
        return emptyList()
    }

}