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
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.view.isGone
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.EmojiFragmentBinding
import com.adityaamolbavadekar.messenger.model.Emoji
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmojiPopupWindow
private constructor(
    private val onEmojiClicked: (String) -> Unit,
    private val parent: View,
    private val binding: EmojiFragmentBinding,
    setWidth: Int,
    setHeight: Int,
    setElevation: Float
) :
    PopupWindow(binding.root, setWidth, setHeight, /*focusable*/true),
    TabLayout.OnTabSelectedListener {

    private var activeTabId = 0
    private var activeTabCategory = EmojiBottomFragment.EmojiCategeory.Activities
    private lateinit var listAdapter: EmojiBottomFragment.EmojiPagerAdapter
    private var tabs = mutableListOf<TabLayout.Tab>()
    private val _emojisList: MutableLiveData<List<Emoji>> = MutableLiveData(listOf())
    private val emojisList: LiveData<List<Emoji>> = _emojisList

    init {
        isOutsideTouchable = true
        elevation = setElevation
        runOnIOThread {
            loadEmojiData()?.let {
                _emojisList.postValue(it)
            }
        }
        doOnCreateView()
        showAtLocation(/*parent*/parent,/*gravity*/Gravity.BOTTOM,/*x*/0,/*y*/0)
    }

    private fun doOnCreateView() {

        binding.searchLayout.root.isGone = true

        listAdapter = EmojiBottomFragment.EmojiPagerAdapter {
            onEmojiClicked(it.getParsedEmoji())
        }

        binding.emojiRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 6)
            adapter = listAdapter
        }

        EmojiBottomFragment.EmojiCategeory.allDrawables().forEachIndexed { index, drawableRes ->
            val item = binding.emojiCategoriesTabLayout.newTab()
                .setIcon(drawableRes)
                .setId(index)
            tabs.add(index, item)
            binding.emojiCategoriesTabLayout.addTab(item, index, (index == 0))
        }

        binding.emojiCategoriesTabLayout.addOnTabSelectedListener(this)
        changeTab(0)
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
        activeTabCategory = EmojiBottomFragment.EmojiCategeory.getAt(id)
        activeTabId = activeTabCategory.ordinal
        refresh()
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
            val reader = parent.context.resources
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

    private fun refresh() {
        emojisList.value?.let { list ->
            listAdapter.submitList(list.filter { it.category == activeTabId })
        }
    }

    companion object {

        private val TAG = EmojiPopupWindow::class.java.simpleName

        fun build(
            onEmojiClicked: (String) -> Unit,
            parent: View,
            context: Context,
            windowHeight: Int
        ): EmojiPopupWindow {
            val binding =
                EmojiFragmentBinding.inflate(LayoutInflater.from(context), null, false)
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val elevation = context.resources.getDimension(R.dimen.emoji_popup_elevation)
            var height = context.resources.getDimension(R.dimen.emoji_popup_min_height).toInt()
            if (height < windowHeight) height = windowHeight
            return EmojiPopupWindow(onEmojiClicked, parent, binding, width, height, elevation)
        }
    }
}
