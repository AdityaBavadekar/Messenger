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

package com.adityaamolbavadekar.messenger.ui.conversation_list

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.ConversationListItemBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.model.valueOf
import com.adityaamolbavadekar.messenger.utils.extensions.getDateForConversationListLastUpdated
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseItemHolder
import com.adityaamolbavadekar.messenger.utils.recyclerview.BaseListAdapter
import com.bumptech.glide.Glide

class ConversationListAdapter :
    BaseListAdapter<ConversationRecord, ConversationListAdapter.ConversationHolder>(
        ConversationDiffUtil()
    ) {

    private val meUid = AuthManager().uid
    private var recipients = listOf<Recipient>()

    fun setRecipients(list: List<Recipient>) {
        this.recipients = list
    }

    inner class ConversationHolder private constructor(
        private val b: ConversationListItemBinding
    ) :
        BaseItemHolder<ConversationRecord>(b.root) {

        override fun bind(
            t: ConversationRecord,
            position: Int,
            onItemClickCallback: OnItemClickCallback<ConversationRecord>?
        ) {
            super.bind(t, position, onItemClickCallback)
            b.title.text = t.loadDisplayName(b.root.context.getString(R.string.you))
            t.lastMessageText.also {
                b.subtitle.text = it
                if (it == null) b.subtitleHolder.isGone = true
                else b.subtitleHolder.isVisible = true
            }
            loadPhoto(t)
            b.parent.setOnClickListener { onItemClickCallback?.onItemClick(t) }
            b.parent.setOnLongClickListener {
                onItemClickCallback?.onItemLongClick(t)
                true
            }
            t.lastMessageSenderUid?.let { uid ->
                if (uid == meUid) {
                    b.subtitleTextViewZero.text = b.root.context.getString(
                        R.string.conversation_list_item_sender_formatted,
                        b.root.context.getString(R.string.you)
                    )
                } else {
                    recipients.valueOf(uid)?.let { recipient ->
                        b.subtitleTextViewZero.text = b.root.context.getString(
                            R.string.conversation_list_item_sender_formatted,
                            recipient.loadFirstName()
                        )
                    }
                }
            }
            t.lastMessageTimestamp?.let {
                b.timestamp.text = getDateForConversationListLastUpdated(it, b.root.context)
            }
        }

        private fun loadPhoto(record: ConversationRecord) {
            if (record.photoUrl != null) {
                b.profilePictureImageView.load(
                    Uri.parse(record.photoUrl),
                    true,
                    R.drawable.ic_user_profile_default
                )
            } else {
                Glide.with(b.profilePictureImageView.context).clear(b.profilePictureImageView)
                b.profilePictureImageView.setImageResource(record.defaultIcon())
            }
            b.profilePictureHolder.setOnClickListener { v ->
                record.photoUrl?.let { url ->
                    Dialogs.showProfilePictureDialog(v.context, url, b.title.text.toString()) {}
                }
            }
        }

        constructor(parent: ViewGroup) : this(
            ConversationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversationListAdapter.ConversationHolder {
        return ConversationHolder(parent)
    }

    class ConversationDiffUtil : DiffUtil.ItemCallback<ConversationRecord>() {
        override fun areItemsTheSame(
            oldItem: ConversationRecord,
            newItem: ConversationRecord
        ): Boolean {
            return (oldItem.conversationId == newItem.conversationId)
        }

        override fun areContentsTheSame(
            oldItem: ConversationRecord,
            newItem: ConversationRecord
        ): Boolean {
            return (
                    oldItem.conversationId == newItem.conversationId &&
                            oldItem.title == newItem.title &&
                            oldItem.lastMessageText == newItem.lastMessageText &&
                            oldItem.updated == newItem.updated &&
                            oldItem.archived == newItem.archived &&
                            oldItem.pinned == newItem.pinned
                    )
        }
    }
}
