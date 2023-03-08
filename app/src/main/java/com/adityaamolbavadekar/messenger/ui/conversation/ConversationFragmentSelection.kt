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

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.model.RecyclerViewType
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class ConversationFragmentSelection private constructor(
    private val adapter: MessagesAdapter,
    recyclerView: RecyclerView
) {

    private var selectionTracker: SelectionTracker<Long>

    private inner class MessageItemsKeyProvider : ItemKeyProvider<Long>(SCOPE_MAPPED) {
        override fun getKey(position: Int): Long {
            return position.toLong()
        }

        override fun getPosition(key: Long): Int {
            return key.toInt()
        }
    }

    private inner class MessageItemDetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {

        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            view?.let {
                val viewHolder: RecyclerView.ViewHolder = recyclerView.getChildViewHolder(it)
                if (
                    viewHolder is MessagesAdapter.MessageItemHolder
                ) {
                    return (viewHolder as MessagesAdapter.MessageBaseItemHolder).getItemDetails()
                }
            }
            return null
        }
    }

    private fun createSelectionPredicate(): SelectionTracker.SelectionPredicate<Long> {
        return object : SelectionTracker.SelectionPredicate<Long>() {

            /**
             * Selection will be allowed only if the item is of type message.
             * */
            override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
                InternalLogger.logD(
                    "ConversationFragmentSelection",
                    "[Key=$key][NextState=$nextState]" +
                            "[Return=${getIsMessageItem(adapter.getItemViewType(key.toInt()))}]"
                )
                return if (getIsMessageItem(adapter.getItemViewType(key.toInt()))) {
                    return true
                } else false
            }

            /**
             * Selection will be allowed only if the item is of type message.
             * */
            override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
                InternalLogger.logD(
                    "ConversationFragmentSelection",
                    "[Position]=$position][NextState=$nextState]" +
                            "[Return=${getIsMessageItem(adapter.getItemViewType(position))}]"
                )
                return if (getIsMessageItem(adapter.getItemViewType(position))) {
                    return true
                } else false
            }

            /**
             * Always true.
             * */
            override fun canSelectMultiple(): Boolean {
                return true
            }
        }
    }

    private fun getIsMessageItem(type: Int): Boolean {
        return RecyclerViewType.TYPE_ITEM == type
    }

    fun tracker(): SelectionTracker<Long> {
        return selectionTracker
    }

    init {
        selectionTracker = SelectionTracker.Builder(
            ID,
            recyclerView,
            MessageItemsKeyProvider(),
            MessageItemDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(createSelectionPredicate())
            .build()
        adapter.setItemSelectionTracker(selectionTracker)
    }

    companion object {
        private const val ID = "messages_selection"

        fun new(adapter: MessagesAdapter, recyclerView: RecyclerView): SelectionTracker<Long> {
            return ConversationFragmentSelection(adapter, recyclerView).tracker()
        }

    }

}