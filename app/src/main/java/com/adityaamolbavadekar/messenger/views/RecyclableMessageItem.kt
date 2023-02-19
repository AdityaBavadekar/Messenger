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

package com.adityaamolbavadekar.messenger.views

import androidx.lifecycle.LifecycleOwner
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.LinkPreviewInfo
import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.model.Recipient

interface RecyclableMessageItem {

    fun bind(
        lifecycleOwner: LifecycleOwner,
        message: MessageRecord,
        previousMessage: MessageRecord?,
        nextMessage: MessageRecord?,
        conversationRecord: ConversationRecord?,
        sender: Recipient?,
        isIncomingMessage:Boolean,
        itemSelectionKey:Long,
        index:Int
    )

    fun unbind()

    fun updateTimestamp()

    fun updateSelectionInformation() {}

    interface Listeners {
        fun onItemClicked(messageRecord: MessageRecord)
        fun onLinkPreviewClicked(linkPreviewInfo: LinkPreviewInfo)
        fun onReadMoreClicked(messageRecord: MessageRecord)
        fun onShowReactionInformationClicked(messageRecord: MessageRecord)
    }

}