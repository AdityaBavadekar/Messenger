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

package com.adityaamolbavadekar.messenger.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_drafts_table")
data class ConversationDraftMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val conversationId: String,
    var draftMessage: String,
    val attachments: List<String> = listOf(),
    val timestamp: Long = System.currentTimeMillis(),
    var mentions: List<String> = listOf(),
) {

    fun isNotEmpty(): Boolean {
        return draftMessage.trim().isNotEmpty()
    }

    companion object {

        fun new(string: String, conversationId: String): ConversationDraftMessage {
            return ConversationDraftMessage(id = 0, conversationId, string)
        }

    }

}
