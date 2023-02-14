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

package com.adityaamolbavadekar.messenger.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.ui.conversation.MessageSender
import com.adityaamolbavadekar.messenger.utils.Constants.EXTRA_CONVERSATION_ID
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class ReplyReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = ReplyReceiver::class.java.simpleName
        const val KEY_REPLY = "reply"
        const val EXTRA_PATH = "reply"
        const val PATH_P2P = "person"
        const val PATH_GROUP = "group"
        const val PATH_SELF = "me"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null || intent.data == null || intent.data!!.lastPathSegment == null) {
            return
        }
        val messageSender = MessageSender()
        val results = RemoteInput.getResultsFromIntent(intent)
        val input = results?.getCharSequence(KEY_REPLY) ?: return
        val path = intent.getStringExtra(EXTRA_PATH)!!
        val conversationId = intent.getStringExtra(EXTRA_CONVERSATION_ID)!!
        InternalLogger.logD(TAG, "ConversationId -> $conversationId")
        val message =
            MessageRecord.from(
                Recipient.Builder(PrefsManager(context).getUserObject()!!).build(),
                conversationId,
                input.toString()
            )
        when (path) {
            PATH_P2P -> {
                messageSender.sendP2PMessage(message, conversationId) {}
            }
            PATH_GROUP -> {
                messageSender.sendGroupMessage(message) {}
            }
            PATH_SELF -> {
                messageSender.sendSelfMessage(message) {}
            }
            else -> return
        }
    }

}
