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

package com.adityaamolbavadekar.messenger.utils.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseItemHolder<T>(b: View) : RecyclerView.ViewHolder(b) {

    private var details = Details()

    open fun bind(
        t: T, position: Int,
        onItemClickCallback: OnItemClickCallback<T>?
    ) {
        details.position = position.toLong()
    }

    fun getItemDetails(): Details {
        return details
    }

    companion object {
        fun <T> emptyCallback(): OnItemClickCallback<T> =
            object : OnItemClickCallback<T> {}
    }

    interface OnItemClickCallback<T> {
        fun onItemClick(item: T) {}
        fun onItemLongClick(item: T) {}
    }

}