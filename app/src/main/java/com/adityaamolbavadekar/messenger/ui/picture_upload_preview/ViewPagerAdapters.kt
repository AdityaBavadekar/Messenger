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

package com.adityaamolbavadekar.messenger.ui.picture_upload_preview

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.adityaamolbavadekar.messenger.databinding.ItemImageSliderBinding
import com.adityaamolbavadekar.messenger.databinding.ItemImageSliderSmallBinding
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseItemHolder

class ViewPagerAdapters {

    class ImageSliderPagerAdapter(
        private val onClick: ((Uri) -> Unit)? = null
    ) :
        ListAdapter<Uri, BaseItemHolder<Uri>>(UriDiffCallback()) {

        class LargeItemHolder private constructor(private val b: ItemImageSliderBinding) :
            BaseItemHolder<Uri>(b.root) {

            override fun bind(
                t: Uri,
                position: Int,
                onItemClickCallback: OnItemClickCallback<Uri>?
            ) {
                b.imageView.load(t, false)
            }

            constructor(parent: ViewGroup) : this(
                ItemImageSliderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }

        class SmallItemHolder private constructor(
            private val b: ItemImageSliderSmallBinding,
            private val onClick: (Uri) -> Unit
        ) :
            BaseItemHolder<Uri>(b.root) {

            override fun bind(
                t: Uri,
                position: Int,
                onItemClickCallback: OnItemClickCallback<Uri>?
            ) {
                b.root.setOnClickListener { onClick(t) }
                b.imageView.load(t, false)
            }

            constructor(parent: ViewGroup, onClick: (Uri) -> Unit) : this(
                ItemImageSliderSmallBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onClick
            )
        }

        class UriDiffCallback : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemHolder<Uri> {
            return if (onClick == null) LargeItemHolder(parent)
            else SmallItemHolder(parent, onClick)
        }

        override fun onBindViewHolder(holder: BaseItemHolder<Uri>, position: Int) {
            holder.bind(getItem(position), position, BaseItemHolder.emptyCallback())
        }
    }

}
