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

package com.adityaamolbavadekar.messenger.views.compose.emoji

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.adityaamolbavadekar.messenger.databinding.EmojiViewBinding
import com.adityaamolbavadekar.messenger.views.compose.EmojiBottomFragment

class EmojiView @JvmOverloads constructor(
    private val context: Context,
    attr: AttributeSet? = null,
    defStyle :Int= 0
) : LinearLayout(context, attr, defStyle) {

    private var textListener : (String) -> Unit = {}
    private var binding : EmojiViewBinding =
        EmojiViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val pager = binding.pager
    private val tabs = binding.tabs
    private lateinit var emojiPagerAdapter : EmojiBottomFragment.EmojiPagerAdapter

    fun intialise(rootView: View
    ){
        pager.addOnPageChangeListener(pageChangedListener)
    }

    private fun internalSetup(){
        emojiPagerAdapter = EmojiBottomFragment.EmojiPagerAdapter{
            textListener(it.emoji) }
        emojiCategories.forEach{

        }
    }

    fun setListener(listener:(String)-> Unit){
        this.textListener = listener
    }

    private val pageChangedListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {}
        override fun onPageSelected(position: Int) {}
        override fun onPageScrollStateChanged(state: Int) {}
    }

    private val emojiCategories = listOf<EmojiCategory>()

    data class EmojiCategory(val name:String)

}