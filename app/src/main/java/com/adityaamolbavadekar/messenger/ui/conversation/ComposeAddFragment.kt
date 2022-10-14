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

package com.adityaamolbavadekar.messenger.ui.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.ComposeAddFragmentBinding
import com.adityaamolbavadekar.messenger.databinding.ComposeAddItemBinding
import com.adityaamolbavadekar.messenger.views.compose.ComposeBottomFragment

class ComposeAddFragment(fragmentHeight: Int) : ComposeBottomFragment(fragmentHeight) {

    private var _binding: ComposeAddFragmentBinding? = null
    private val binding: ComposeAddFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ComposeAddFragmentBinding.inflate(layoutInflater)
        doOnCreateView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun doOnCreateView() {

        val listAdapter = ComposeAddFragmentAdapter {

        }

        binding.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 5)
            adapter = listAdapter
        }

        listAdapter.submitList(
            listOf(
                ComposeAddFragmentItem(0, "Photos", R.drawable.ic_image),
                ComposeAddFragmentItem(1, "Contacts", R.drawable.ic_contact),
                ComposeAddFragmentItem(2, "Location", R.drawable.ic_my_location_),
                ComposeAddFragmentItem(3, "Camera", R.drawable.ic_camera),
                ComposeAddFragmentItem(4, "GIFs", R.drawable.ic_gif),
                ComposeAddFragmentItem(5, "Files", R.drawable.ic_file),
                ComposeAddFragmentItem(6, "Stickers", R.drawable.ic_gif),
                ComposeAddFragmentItem(7, "Schedule message", R.drawable.message_status_not_sent),
                ComposeAddFragmentItem(8, "Documents", R.drawable.ic_file),
            )
        )

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

}