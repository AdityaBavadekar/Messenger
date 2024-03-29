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

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.model.ConversationRecordRecipientCrossRef
import com.adityaamolbavadekar.messenger.model.Recipient

data class ConversationWithRecipients(
    @Embedded val conversationRecord: ConversationRecord,
    @Relation(
        parentColumn = "conversationId", /*Key of Conversation Record*/
        entityColumn = "uid", /*Key of Message Record*/
        associateBy = Junction(ConversationRecordRecipientCrossRef::class)
    )
    val recipients: List<Recipient>
)
