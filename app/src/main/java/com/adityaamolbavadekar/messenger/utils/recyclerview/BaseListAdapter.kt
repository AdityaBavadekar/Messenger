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

package com.adityaamolbavadekar.messenger.utils.recyclerview

import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<T, VH : BaseItemHolder<T>>(
    diffUtil: DiffUtil.ItemCallback<T>,
    private var onItemClickCallback: BaseItemHolder.OnItemClickCallback<T>? = null
) :
    ListAdapter<T, VH>(diffUtil) {

    fun setOnClickListener(callback: BaseItemHolder.OnItemClickCallback<T>?){
        this.onItemClickCallback= callback
    }

    /*START Selection*/
    private var selectionTracker: SelectionTracker<Long>? = null
    fun setItemSelectionTracker(tracker: SelectionTracker<Long>) {
        this.selectionTracker = tracker
    }

    fun getItemSelectionTracker(): SelectionTracker<Long> {
        return this.selectionTracker?:throw NullPointerException("selectionTracker was requested but was null.")
    }
    /*END Selection*/

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), position, onItemClickCallback)
    }

}