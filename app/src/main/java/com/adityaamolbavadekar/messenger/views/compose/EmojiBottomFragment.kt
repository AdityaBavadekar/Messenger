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

package com.adityaamolbavadekar.messenger.views.compose

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.EmojiFragmentBinding
import com.adityaamolbavadekar.messenger.databinding.EmojiSelectorItemBinding
import com.adityaamolbavadekar.messenger.model.EmojiItemModel
import com.google.android.material.tabs.TabLayout

class EmojiBottomFragment(fragmentHeight: Int, private val onEmojiClicked: (String) -> Unit) :
    ComposeBottomFragment(fragmentHeight), TabLayout.OnTabSelectedListener {

    private var _binding: EmojiFragmentBinding? = null
    private val binding: EmojiFragmentBinding
        get() = _binding!!
    private var activeTabId = 0
    private var activeTabCategory = EmojiCategeory.Activities
    private lateinit var listAdapter: EmojiPagerAdapter
    private var tabs = mutableListOf<TabLayout.Tab>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EmojiFragmentBinding.inflate(layoutInflater)
        doOnCreateView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun doOnCreateView() {

        binding.searchLayout.root.isGone = true

        listAdapter = EmojiPagerAdapter {
            onEmojiClicked(it.getParsedEmoji())
        }

        binding.emojiRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 6)
            adapter = listAdapter
        }

        EmojiCategeory.allTitles().forEachIndexed { index, s ->
            val item = binding.emojiCategoriesTabLayout.newTab()
                .setText(s)
                .setId(index)
            tabs.add(index, item)
            binding.emojiCategoriesTabLayout.addTab(item, index, (index == 0))
        }

        binding.emojiCategoriesTabLayout.addOnTabSelectedListener(this)
        changeTab(0)
    }

    class EmojiPagerAdapter(private val onClick: (EmojiItemModel) -> Unit) :
        ListAdapter<EmojiItemModel, EmojiPagerAdapter.EmojiItemHolder>(Callback()) {

        inner class EmojiItemHolder private constructor(private val b: EmojiSelectorItemBinding) :
            RecyclerView.ViewHolder(b.root) {

            fun bind(item: EmojiItemModel) {
                b.emojiTextView.text = item.getParsedEmoji()
                b.root.setOnClickListener { onClick(item) }
            }

            constructor(parent: ViewGroup) : this(
                EmojiSelectorItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiItemHolder {
            return EmojiItemHolder(parent)
        }

        override fun onBindViewHolder(holder: EmojiItemHolder, position: Int) {
            holder.bind(getItem(position))
        }

        class Callback : DiffUtil.ItemCallback<EmojiItemModel>() {
            override fun areItemsTheSame(
                oldItem: EmojiItemModel,
                newItem: EmojiItemModel
            ): Boolean {
                return oldItem.isSameAs(newItem)
            }

            override fun areContentsTheSame(
                oldItem: EmojiItemModel,
                newItem: EmojiItemModel
            ): Boolean {
                return oldItem.isSameAs(newItem)
            }
        }

    }

    enum class EmojiCategeory(@ArrayRes private val resId: Int, val id: Int,@DrawableRes private val drawableRes:Int=0) {
        Activities(R.array.emoji_activities, 0),
        Emotions(R.array.emoji_emotions, 1),
        Flags(R.array.emoji_flags, 2),
        Food(R.array.emoji_food, 3),
        Nature(R.array.emoji_nature, 4),
        Objects(R.array.emoji_objects, 5),
        People(R.array.emoji_people, 6),
        Places(R.array.emoji_places, 7),
        Symbols(R.array.emoji_symbols, 8);

        fun load(context: Context): Array<out String> {
            return context.resources.getStringArray(resId)
        }

        fun loadEmojiModelsList(context: Context): MutableList<EmojiItemModel> {
            val emojis = load(context)
            val emojiItemModelList = mutableListOf<EmojiItemModel>()
            for ((i, string) in emojis.withIndex()) {
                emojiItemModelList.add(EmojiItemModel(string, i))
            }
            return emojiItemModelList
        }

        companion object {
            fun allTitles(): List<String> {
                return values().map { it.name }
            }

            fun getAt(position: Int): EmojiCategeory {
                values().forEach {
                    if (it.id == position) return it
                }
                return Activities
            }
        }

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab == null) return
        if (activeTabId != tab.id) {
            changeTab(tab.id)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        return
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        return
    }

    private fun changeTab(id: Int) {
        activeTabCategory = EmojiCategeory.getAt(id)
        activeTabId = activeTabCategory.id
        refresh()
    }

    private fun refresh() {
        listAdapter.submitList(activeTabCategory.loadEmojiModelsList(requireContext()))
    }


}