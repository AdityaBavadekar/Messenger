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

/**
 * Represents a Recipient that can be fetched from Remote Database Group Conversation.
 * */
@Entity(tableName = "remote_recipients_table")
data class RemoteRecipient(
    @PrimaryKey(autoGenerate = true) val uid: String = "",
    val role: String = RecipientRole.NONE.name,
    val joined: Long = System.currentTimeMillis(),
    val addedBy: String? = null,
) : RemoteDatabasePersitable {

    /**
     * For identifying Role of a Group Conversation Recipient.
     * */
    enum class RecipientRole {
        NONE, MANAGER, PARTICIPANT,
    }

    private annotation class Keys {
        companion object {
            const val UID = "uid"
            const val ROLE = "role"
            const val JOINED = "joined"
            const val ADDED_BY = "addedBy"
        }
    }

    fun isManager(): Boolean {
        return RecipientRole.valueOf(role) == RecipientRole.MANAGER
    }

    fun isParticipant(): Boolean {
        return RecipientRole.valueOf(role) == RecipientRole.PARTICIPANT
    }

    override fun hashMap(): HashMap<String, Any?> {
        return hashMapOf(
            Keys.UID to uid,
            Keys.ROLE to role,
            Keys.JOINED to joined.toDouble(),
            Keys.ADDED_BY to addedBy
        )
    }

    companion object {

        fun newManager(creatorUid: String): RemoteRecipient {
            return RemoteRecipient(
                creatorUid,
                RecipientRole.MANAGER.name,
                System.currentTimeMillis()
            )
        }

        fun newParticipant(uid: String, addedByUid: String): RemoteRecipient {
            return RemoteRecipient(
                uid,
                RecipientRole.PARTICIPANT.name,
                System.currentTimeMillis(),
                addedByUid
            )
        }

        fun fromRecipient(r: Recipient): RemoteRecipient {
            return RemoteRecipient(r.uid)
        }

        fun fromHashMap(hashMap: HashMap<String, Any?>): RemoteRecipient {
            val uid = hashMap[Keys.UID] as String
            val role = (hashMap[Keys.ROLE] as? String) ?: RecipientRole.NONE.name
            val joined = (hashMap[Keys.JOINED] as Double).toLong()
            val addedBy = hashMap[Keys.ADDED_BY] as? String
            return RemoteRecipient(uid, role, joined, addedBy)
        }

    }

}