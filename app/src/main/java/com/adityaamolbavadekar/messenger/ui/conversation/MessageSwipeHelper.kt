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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import kotlin.math.ceil

class MessageSwipeHelper : ItemTouchHelper.Callback() {

    private var isVibrate: Boolean = false
    private var startTacking: Boolean = false
    private var currentViewHolder: MessagesAdapter.MessageItemHolder? = null
    private var swipeBack = false
    private var lastReplyButtonAnimTime: Long = 0
    private var replyButtonProgress: Float = 0f
    private var x = 0f
    private var onShouldShowReplyListener: (() -> Unit)? = null
    fun setOnShouldShowReplyListener(listener: () -> Unit) {
        onShouldShowReplyListener = listener
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (viewHolder is MessagesAdapter.MessageItemHolder)
            makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT)
        else makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.ACTION_STATE_IDLE)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        if (viewHolder is MessagesAdapter.MessageItemHolder) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                setOnItemTouchListener(
                    recyclerView,
                    viewHolder
                )
            }

            if (viewHolder.itemView.translationX < toDensityPixels(
                    130,
                    recyclerView.context
                ) || dX < this.x
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                x = dX
                startTacking = true
            }

            currentViewHolder = viewHolder
            drawReplyButton(c)

        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnItemTouchListener(
        recyclerView: RecyclerView,
        viewHolder: MessagesAdapter.MessageItemHolder
    ) {
        recyclerView.setOnTouchListener { v, event ->
            swipeBack = event.action == MotionEvent.ACTION_CANCEL
            if (swipeBack) {
                if (Math.abs(viewHolder.itemView.translationX) >= toDensityPixels(100, v.context)) {
                    onShouldShowReplyListener?.invoke()
                }
            }
            false
        }
    }

    private fun drawReplyButton(c: Canvas) {
        if (currentViewHolder == null) return

        val translationX = currentViewHolder!!.itemView.translationX
        val time = System.currentTimeMillis()
        val dt = Math.min(17, time - lastReplyButtonAnimTime)
        lastReplyButtonAnimTime = time
        val isShowing = translationX >= toDensityPixels(30, currentViewHolder!!.itemView.context)
        if (isShowing) {
            if (replyButtonProgress < 1.0f) {
                replyButtonProgress += dt / 180.0f
                if (replyButtonProgress > 1.0f) {
                    replyButtonProgress = 1.0f
                } else {
                    currentViewHolder!!.itemView.invalidate()
                }
            }
        } else if (translationX <= 0.0f) {
            replyButtonProgress = 0f
            startTacking = false
            isVibrate = false

        } else {
            if (replyButtonProgress > 0.0f) {
                replyButtonProgress -= dt / 180.0f
                if (replyButtonProgress < 0.1f) {
                    replyButtonProgress = 0f
                } else {
                    currentViewHolder!!.itemView.invalidate()
                }
            }
        }

        val drawable =
            currentViewHolder!!.itemView.context.getDrawable(R.drawable.message_status_read)!!
        drawable.draw(c)

    }

    private fun toDensityPixels(i: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return ceil(density * i.toDouble()).toInt()
    }

}