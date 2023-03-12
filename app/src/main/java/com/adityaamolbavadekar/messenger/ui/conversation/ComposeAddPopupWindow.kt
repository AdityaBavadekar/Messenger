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
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.ComposeAddFragmentBinding
import com.adityaamolbavadekar.messenger.databinding.ComposeAddItemBinding

class ComposeAddPopupWindow private constructor(
    private val clicked: (Int) -> Unit,
    private val parent: View,
    private val binding: ComposeAddFragmentBinding,
    setWidth: Int,
    setHeight: Int,
    setElevation: Float,
    private val onCloseListener: () -> Unit
) :
    PopupWindow(binding.root, setWidth, setHeight, /*focusable*/true) {

    init {
        isOutsideTouchable = true
        elevation = setElevation
        doOnCreateView()
        showAtLocation(/*parent*/parent,/*gravity*/Gravity.BOTTOM,/*x*/0,/*y*/0)
        setOnDismissListener { onCloseListener() }
    }

    enum class ComposeAddItem(val stringRes: Int, val drawableRes: Int) {
        PHOTOS(R.string.photos, R.drawable.ic_image),
        CONTACT(R.string.contact, R.drawable.ic_contact),
        LOCATION(R.string.location, R.drawable.ic_my_location_),
        CAMERA(R.string.camera, R.drawable.ic_camera),
        GIFS(R.string.gifs, R.drawable.ic_gif),
        FILES(R.string.files, R.drawable.ic_file),
        STICKERS(R.string.stickers, R.drawable.ic_gif),
        DOCUMENTS(R.string.document, R.drawable.ic_file);

        companion object {
            fun getList(context: Context): List<ComposeAddFragmentItem> {
                return values().map {
                    ComposeAddFragmentItem(
                        it.ordinal,
                        context.getString(it.stringRes),
                        it.drawableRes
                    )
                }
            }
        }
    }

    private fun doOnCreateView() {
        val listAdapter = ComposeAddFragmentAdapter {
            dismiss()
            clicked(it)
        }
        binding.recycler.apply {
            layoutManager = GridLayoutManager(this@ComposeAddPopupWindow.parent.context, 5)
            adapter = listAdapter
        }

        listAdapter.submitList(ComposeAddItem.getList(parent.context))
    }

    data class ComposeAddFragmentItem(val key: Int, val title: String, val src: Int) {
        class Callback : DiffUtil.ItemCallback<ComposeAddFragmentItem>() {

            override fun areItemsTheSame(
                oldItem: ComposeAddFragmentItem,
                newItem: ComposeAddFragmentItem
            ): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(
                oldItem: ComposeAddFragmentItem,
                newItem: ComposeAddFragmentItem
            ): Boolean {
                return oldItem.key == newItem.key
            }
        }
    }

    private class ComposeAddFragmentAdapter(private val onClick: (Int) -> Unit) :
        ListAdapter<ComposeAddFragmentItem, ComposeAddFragmentAdapter.ComposeAddFragmentHolder>(
            ComposeAddFragmentItem.Callback()
        ) {


        inner class ComposeAddFragmentHolder(
            private val root: View,
            private val icon: ImageView,
            private val title: TextView
        ) : RecyclerView.ViewHolder(root) {

            fun bind(item: ComposeAddFragmentItem) {
                icon.setImageResource(item.src)
                title.text = item.title
                root.setOnClickListener { onClick(item.key) }
            }

        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ComposeAddFragmentHolder {
            val root =
                ComposeAddItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ComposeAddFragmentHolder(root.root, root.imageView, root.textView)
        }

        override fun onBindViewHolder(holder: ComposeAddFragmentHolder, position: Int) {
            holder.bind(getItem(position))
        }

    }

    companion object {

        fun build(
            clicked: (Int) -> Unit,
            parent: View,
            context: Context,
            windowHeight: Int,
            onCloseListener: () -> Unit
        ): ComposeAddPopupWindow {
            val binding =
                ComposeAddFragmentBinding.inflate(LayoutInflater.from(context), null, false)
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val elevation = context.resources.getDimension(R.dimen.emoji_popup_elevation)
            var height = context.resources.getDimension(R.dimen.emoji_popup_min_height).toInt()
            if (height < windowHeight) height = windowHeight
            return ComposeAddPopupWindow(
                clicked,
                parent,
                binding,
                width,
                height,
                elevation,
                onCloseListener
            )
        }
    }

}