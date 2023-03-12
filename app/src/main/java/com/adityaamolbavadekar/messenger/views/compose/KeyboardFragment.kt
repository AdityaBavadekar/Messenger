package com.adityaamolbavadekar.messenger.views.compose

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.adityaamolbavadekar.messenger.databinding.EmojiFragmentBinding
import com.adityaamolbavadekar.messenger.model.Emoji
import com.adityaamolbavadekar.messenger.utils.EmojiUtils
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.google.android.material.tabs.TabLayout

class KeyboardFragment(private val onEmojiClicked:(String)->Unit) : BindingHelperFragment<EmojiFragmentBinding>(),TabLayout.OnTabSelectedListener {

    private var activeTabId = 0
    private var activeTabCategory = EmojiBottomFragment.EmojiCategeory.Activities
    private lateinit var listAdapter: EmojiBottomFragment.EmojiPagerAdapter
    private var tabs = mutableListOf<TabLayout.Tab>()
    private val _emojisList: MutableLiveData<List<Emoji>> = MutableLiveData(listOf())
    private val emojisList: LiveData<List<Emoji>> = _emojisList

    override fun onShouldInflateBinding(): EmojiFragmentBinding {
        return EmojiFragmentBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        runOnIOThread {
            EmojiUtils.loadEmojiData(context)?.let {
                _emojisList.postValue(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doOnCreateView()
    }

    private fun doOnCreateView() {

        binding.searchLayout.root.isGone = true

        listAdapter = EmojiBottomFragment.EmojiPagerAdapter {
            onEmojiClicked(it.getParsedEmoji())
        }

        binding.emojiRecyclerView.apply {
            layoutManager = GridLayoutManager(context, EMOJI_LIST_SPAN_COUNT)
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

    private fun refresh() {
        emojisList.value?.let { list ->
            listAdapter.submitList(list.filter { it.category == activeTabId })
        }
    }

    fun show() {}
    fun hide() {}

    companion object {

        private val TAG = EmojiPopupWindow::class.java.simpleName
        private const val EMOJI_LIST_SPAN_COUNT = 8
    }
}