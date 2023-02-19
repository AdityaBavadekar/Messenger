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

package com.adityaamolbavadekar.messenger.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.HashMap

@Entity(tableName = "reactions_table")
data class ReactionRecord constructor(
    @PrimaryKey(autoGenerate = false) val reactionId: String = Id.get(),
    val messageId: String = "",
    val reaction: String = "",
    val reactorUid: String = ""
) : RemoteDatabasePersitable {

    override fun hashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "reactionId" to reactionId,
            "messageId" to messageId,
            "reaction" to reaction,
            "reactorUid" to reactorUid
        )
    }

    companion object {
        fun new(messageId: String, reaction: String, reactorUid: String): ReactionRecord {
            return ReactionRecord(Id.get(),messageId, reaction, reactorUid)
        }
    }

}