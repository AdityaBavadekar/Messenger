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

package com.adityaamolbavadekar.messenger.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.graphics.drawable.IconCompat
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.receivers.ReplyReceiver
import com.adityaamolbavadekar.messenger.receivers.ReplyReceiver.Companion.EXTRA_PATH
import com.adityaamolbavadekar.messenger.receivers.ReplyReceiver.Companion.KEY_REPLY
import com.adityaamolbavadekar.messenger.receivers.ReplyReceiver.Companion.PATH_GROUP
import com.adityaamolbavadekar.messenger.ui.conversation.ConversationActivity
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.Constants.EXTRA_CONVERSATION_ID
import java.util.*

class NewMessagesNotificationHelper(private val context: Context) {

    private val nm =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val prefsManager = PrefsManager(context)
    private val me = prefsManager.getUserObject()!!

    fun parse(data: NotificationData) {
        if (data.isGroup == "true") createInternalGroupNotification(data)
        else createInternalP2PNotification(data)
    }

    private fun getFlag(): Int = NotificationHelper.getFlag(true)

    private fun createInternalP2PNotification(data: NotificationData) {

        //todo Add Intent Extras

        val contentIntent = Intent(context, ConversationActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                CONVERSATION_NOTIFICATION_REQUEST_CODE,
                contentIntent,
                getFlag()
            )
        val senderIcon = IconCompat.createWithResource(context, R.drawable.ic_user_profile_default)
        val person = Person.Builder()
            .setIcon(senderIcon)
            .setKey(data.senderUid)
            .setName(data.title)
            .build()

        val message = NotificationCompat.MessagingStyle
            .Message(
                data.message,
                data.timestamp.toLongOrNull() ?: System.currentTimeMillis(),
                person
            )

        val style = NotificationCompat
            .MessagingStyle(person)
            .addMessage(message)
            .setGroupConversation(false)

        val notification =
            NotificationCompat.Builder(context, Constants.ChannelIds.ID_MESSAGE_NOTIFICATIONS)
                .setStyle(style)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setAutoCancel(true)
                .build()

        notify(data.conversationId, notification)
    }

    private fun createInternalGroupNotification(data: NotificationData) {

        val contentIntent = Intent(context, ConversationActivity::class.java)
            .putExtra(EXTRA_CONVERSATION_ID, data.conversationId)
        val pendingIntent = PendingIntent.getActivity(
            context,
            CONVERSATION_NOTIFICATION_REQUEST_CODE,
            contentIntent,
            getFlag()
        )

        val remoteInput = RemoteInput.Builder(KEY_REPLY)
            .setLabel(context.getString(R.string.type_a_message))
            .build()

        val replyIntent = Intent(context, ReplyReceiver::class.java)
            .putExtra(EXTRA_PATH, PATH_GROUP)
            .putExtra(EXTRA_CONVERSATION_ID, data.conversationId)

        val replyPendingIntent = PendingIntent.getBroadcast(context, 0, replyIntent, getFlag())

        val replyAction = NotificationCompat.Action.Builder(
            R.drawable.ic_send,
            context.getString(R.string.reply),
            replyPendingIntent
        ).addRemoteInput(remoteInput)
            .build()

        val senderIcon = IconCompat.createWithResource(context, R.drawable.ic_user_profile_default)
        val personMe = Person.Builder()
            .setIcon(senderIcon)
            .setKey(me.UID)
            .setName(context.getString(R.string.you))
            .build()

        val sender = Person.Builder()
            .setIcon(senderIcon)
            .setKey(data.senderUid)
            .setName(data.title)
            .build()

        val message =
            NotificationCompat.MessagingStyle
                .Message(
                    data.message,
                    data.timestamp.toLongOrNull() ?: System.currentTimeMillis(),
                    sender
                )

        val style = NotificationCompat
            .MessagingStyle(sender)
            .setGroupConversation(true)
            .setConversationTitle(data.title)
            .addMessage(message)

        val notification =
            NotificationCompat.Builder(context, Constants.ChannelIds.ID_MESSAGE_NOTIFICATIONS)
                .setStyle(style)
                .setGroup(data.conversationId)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setAutoCancel(true)
                .addAction(replyAction)
                .build()
        notify(data.conversationId, notification)
    }

    private fun notify(conversationId: String, notification: Notification) {
        var id = prefsManager.getSavedNotificationId(conversationId)
        return if (id != null) {
            nm.notify(id, notification)
        } else {
            id = Random().nextInt() + 100
            prefsManager.saveNotificationId(conversationId, id)
            nm.notify(id, notification)
        }
    }

    companion object {
        const val CONVERSATION_NOTIFICATION_REQUEST_CODE = 100
    }

}