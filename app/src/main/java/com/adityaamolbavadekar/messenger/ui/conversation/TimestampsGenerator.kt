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

import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.utils.extensions.getDateStub
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import kotlinx.coroutines.Job

class TimestampsGenerator private constructor() {

    companion object {

        fun generate(list: List<MessageRecord>): MutableList<MessageRecord> {
            val newList = mutableListOf<MessageRecord>()

            list.sortedBy { it.timestamp }.forEachIndexed { index, messageRecord ->
                if (messageRecord.isTimestampHeader()) {
                    newList.add(messageRecord)
                    return@forEachIndexed
                }

                val currentTimestampString = getDateStub(messageRecord.timestamp)
                val prev = list.getOrNull(index + 1)
                if (prev != null) {
                    if (prev.isTimestampHeader()) newList.add(messageRecord)
                    else {
                        if (getDateStub(prev.timestamp) == currentTimestampString) newList.add(
                            messageRecord
                        )
                        else newList.addMessageAndTimestamp(messageRecord)
                    }
                    return@forEachIndexed
                } else newList.addMessageAndTimestamp(messageRecord)

            }

            return newList
        }

        private fun MutableList<MessageRecord>.addMessageAndTimestamp(messageRecord: MessageRecord) {
            add(messageRecord)
            val stamp = MessageRecord.timestampHeader(
                messageRecord.timestamp,
                messageRecord.conversationId
            )
            add(stamp)
        }
    }

}