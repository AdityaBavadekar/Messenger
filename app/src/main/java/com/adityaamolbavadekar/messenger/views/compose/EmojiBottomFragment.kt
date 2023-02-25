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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.EmojiFragmentBinding
import com.adityaamolbavadekar.messenger.databinding.EmojiSelectorItemBinding
import com.adityaamolbavadekar.messenger.model.Emoji
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmojiBottomFragment(fragmentHeight: Int, private val onEmojiClicked: (String) -> Unit) :
    ComposeBottomFragment(fragmentHeight), TabLayout.OnTabSelectedListener {

    private var _binding: EmojiFragmentBinding? = null
    private val binding: EmojiFragmentBinding
        get() = _binding!!
    private var activeTabId = 0
    private var activeTabCategory = EmojiCategeory.Activities
    private lateinit var listAdapter: EmojiPagerAdapter
    private var tabs = mutableListOf<TabLayout.Tab>()
    private val _emojisList: MutableLiveData<List<Emoji>> = MutableLiveData(listOf())
    private val emojisList: LiveData<List<Emoji>> = _emojisList

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EmojiFragmentBinding.inflate(layoutInflater)
        runOnIOThread {
            loadEmojiData()?.let {
                _emojisList.postValue(it)
            }
        }
        doOnCreateView()
        return binding.root
    }

    private fun loadEmojiData(): List<Emoji>? {
        getEmojis()?.let {
            val emojiDataClassType = object : TypeToken<List<Emoji>>() {}.type
            return try {
                val emojiList = Gson().fromJson<List<Emoji>>(it, emojiDataClassType)
                InternalLogger.logD(
                    TAG,
                    "Loaded EmojiList :" + emojiList.size.toString()
                )
                emojiList
            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Unable to read emoji list", e)
                null
            }
        }
        return null
    }

    private fun getEmojis(): String? {
        val jsonString: String
        return try {
            val reader = requireContext().resources
                .openRawResource(R.raw.emoji_dataset)
                .bufferedReader()
            jsonString = reader.use { it.readText() }
            reader.close()
            jsonString
        } catch (e: Exception) {
            InternalLogger.logE(TAG, "Unable to read emojis", e)
            null
        }
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

        EmojiCategeory.allDrawables().forEachIndexed { index, drawableRes ->
            val item = binding.emojiCategoriesTabLayout.newTab()
                .setIcon(drawableRes)
                .setId(index)
            tabs.add(index, item)
            binding.emojiCategoriesTabLayout.addTab(item, index, (index == 0))
        }

        binding.backspaceButton.setOnClickListener {}

        binding.emojiCategoriesTabLayout.addOnTabSelectedListener(this)
        changeTab(0)
    }

    class EmojiPagerAdapter(private val onClick: (Emoji) -> Unit) :
        ListAdapter<Emoji, EmojiPagerAdapter.EmojiItemHolder>(Callback()) {

        inner class EmojiItemHolder private constructor(private val b: EmojiSelectorItemBinding) :
            RecyclerView.ViewHolder(b.root) {

            fun bind(item: Emoji) {
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

        class Callback : DiffUtil.ItemCallback<Emoji>() {

            override fun areItemsTheSame(
                oldItem: Emoji,
                newItem: Emoji
            ): Boolean {
                return oldItem.isSameAs(newItem)
            }

            override fun areContentsTheSame(
                oldItem: Emoji,
                newItem: Emoji
            ): Boolean {
                return oldItem.isSameAs(newItem)
            }
        }

    }

    enum class EmojiCategeory(private val drawableRes: Int) {
        Smileys_and_Emotion(R.drawable.smileysandpeople),
        Animals_and_Nature(R.drawable.animalsandnature),
        Food_and_Drink(R.drawable.foodanddrink),
        Travel_and_Places(R.drawable.travelandplaces),
        Activities(R.drawable.activities),
        Objects(R.drawable.objects),
        Symbols(R.drawable.symbols),
        Flags(R.drawable.flags);

        companion object {
            fun allDrawables(): List<Int> {
                return values().map { (it.drawableRes) }
            }

            fun getAt(position: Int): EmojiCategeory {
                values().forEach {
                    if (it.ordinal == position) return it
                }
                return Smileys_and_Emotion
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
        activeTabId = activeTabCategory.ordinal
        refresh()
    }

    private fun refresh() {
        loadCurrentEmojis()
    }

    private fun loadCurrentEmojis() {

    }

    companion object {
        private val TAG = EmojiBottomFragment::class.java.simpleName
    }

}