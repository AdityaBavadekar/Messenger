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

import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.EmojiReactionItemBinding
import com.adityaamolbavadekar.messenger.databinding.MessageReactionsIndicatorV2ItemBinding
import com.adityaamolbavadekar.messenger.model.ReactionRecord
import com.adityaamolbavadekar.messenger.utils.AndroidUtils
import com.google.android.material.card.MaterialCardView
import java.lang.Integer.min

class ReactionsView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.Messenger_Widget_ReactionsView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val holder: MaterialCardView
    private val reactionsList: RecyclerView
    private val adapter: EmojiReactionsListAdapter
    private val layoutManager: GridLayoutManager
    private var clickListener: () -> Unit = {}

    init {
        val inflatedView =
            MessageReactionsIndicatorV2ItemBinding.inflate(LayoutInflater.from(context), this, true)
        holder = inflatedView.reactionsHolder
        reactionsList = inflatedView.reactionsList
        layoutManager = GridLayoutManager(context, 4)
        reactionsList.layoutManager = layoutManager
        reactionsList.setHasFixedSize(true)
        adapter = EmojiReactionsListAdapter()
        reactionsList.adapter = adapter
        reactionsList.overScrollMode = View.OVER_SCROLL_NEVER
        reactionsList.addItemDecoration(RectionsSpaceItemDecoration(AndroidUtils.toDP(2, context)))
        holder.setOnClickListener {
            clickListener()
        }
    }

    fun setReactionsClickListener(listener: () -> Unit) {
        clickListener = listener
    }

    private class RectionsSpaceItemDecoration(private val space: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = space
            outRect.right = space
        }

    }

    fun setReactionsList(list: List<ReactionRecord>) {
        val reactionDataList = mutableListOf<ReactionData>()
        var currentReaction = ""
        var currentReactionCount = 0
        list.distinctBy { it.reaction }.forEachIndexed { index, it ->
            if (currentReaction == "") {
                currentReaction = it.reaction
                currentReactionCount = 1
            } else {
                if (currentReaction != it.reaction) {
                    reactionDataList.add(ReactionData(currentReaction, currentReactionCount))
                    currentReaction = it.reaction
                    currentReactionCount = 1
                } else {
                    currentReactionCount += 1
                }
            }
            if (index == list.lastIndex) {
                reactionDataList.add(ReactionData(currentReaction, currentReactionCount))
            }
        }
        setReactionDataList(reactionDataList)
    }

    private fun setReactionDataList(list: List<ReactionData>) {
        adapter.setReactionsDataList(list) {
            if (list.isNotEmpty()) setPadding(AndroidUtils.toDP(4, context))
        }
        if (list.isNotEmpty()) {
            layoutManager.spanCount = min(list.size, 4)
        }
    }

    data class ReactionData(val reaction: String, val count: Int)

    private class EmojiReactionsListAdapter :
        ListAdapter<ReactionData, EmojiReactionsListAdapter.ReactionItemHolder>(
            ReactionsDiffCallback()
        ) {

        private var reactionDataList = listOf<ReactionData>()

        fun setReactionsDataList(list: List<ReactionData>, block: () -> Unit) {
            this.reactionDataList = list
            submitList(reactionDataList, block)
        }

        class ReactionItemHolder(private val binding: EmojiReactionItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(reaction: ReactionData) {
                binding.emojiTextView.text = reaction.reaction
                binding.countTextView.text = reaction.count.toString()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReactionItemHolder {
            return ReactionItemHolder(
                EmojiReactionItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ReactionItemHolder, position: Int) {
            holder.bind(getItem(position))
        }
    }

    private class ReactionsDiffCallback() : DiffUtil.ItemCallback<ReactionData>() {

        override fun areItemsTheSame(oldItem: ReactionData, newItem: ReactionData): Boolean {
            return oldItem.reaction == newItem.reaction
        }

        override fun areContentsTheSame(oldItem: ReactionData, newItem: ReactionData): Boolean {
            return oldItem.reaction == newItem.reaction && oldItem.count == newItem.count
        }
    }

}