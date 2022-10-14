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

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.adityaamolbavadekar.messenger.R

class MessagesSelectionCallback(
    private val fragment: ConversationFragment,
) : ActionMode.Callback {

    private var onCopyListener: () -> (Unit) = {}
    private var onDeleteListener: () -> (Unit) = {}

    override fun onCreateActionMode(
        mode: ActionMode?,
        menu: Menu?
    ): Boolean {
        fragment.activityToolbar.isGone = true
        return true
    }

    override fun onPrepareActionMode(
        mode: ActionMode?,
        menu: Menu?
    ): Boolean {
        fragment.requireActivity().menuInflater.inflate(
            R.menu.conversation_fragment_selection,
            menu
        )
        return true
    }

    override fun onActionItemClicked(
        mode: ActionMode?,
        item: MenuItem?
    ): Boolean {
        item?.let {
            return when (it.itemId) {
                R.id.action_delete -> {
                    onDeleteListener()
                    true
                }
                R.id.action_copy -> {
                    onCopyListener()
                    true
                }
                else -> false
            }
        }
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        fragment.activityToolbar.isVisible = true
        fragment.actionMode = null
        fragment.selectionTracker.clearSelection()
    }

    fun addOnCopyListener(block: ()->(Unit)): MessagesSelectionCallback {
        this.onCopyListener = block
        return this
    }

    fun addOnDeleteListener(block: () -> (Unit)): MessagesSelectionCallback {
        this.onDeleteListener = block
        return this
    }

}