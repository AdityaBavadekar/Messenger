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

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.ui.registration.AuthActivity
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import kotlinx.coroutines.delay
import java.util.*

class NotificationHelper(private val context: Context) : NotificationHelperInterface {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    init {
        createChannels()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun internalCreateChannels() {
        notificationManagerCompat.createNotificationChannelGroups(ChannelMetadata.notificationChannelsGroups)
        notificationManagerCompat.createNotificationChannels(ChannelMetadata.notificationChannelsList)
    }

    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            internalCreateChannels()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    internal object ChannelMetadata {

        private val NewMessages = NotificationChannel(
            /*id*/Constants.ChannelIds.ID_NEW_MESSAGES,
            /*name*/"New messages",
            /*importance*/NotificationManager.IMPORTANCE_HIGH
        )
        private val Updates = NotificationChannel(
            /*id*/Constants.ChannelIds.ID_UPDATES_NOTIFICATIONS,
            /*name*/"Messenger Updates",
            /*importance*/NotificationManager.IMPORTANCE_HIGH
        )
        private val CallNotifications = NotificationChannel(
            /*id*/Constants.ChannelIds.ID_CALL_NOTIFICATIONS,
            /*name*/"Call notifications",
            /*importance*/NotificationManager.IMPORTANCE_HIGH
        )
        private val GroupNotifications = NotificationChannel(
            /*id*/Constants.ChannelIds.ID_GROUP_NOTIFICATIONS,
            /*name*/"Group notifications",
            /*importance*/NotificationManager.IMPORTANCE_HIGH
        )
        private val MessagesNotifications = NotificationChannel(
            /*id*/Constants.ChannelIds.ID_MESSAGE_NOTIFICATIONS,
            /*name*/"Message notifications",
            /*importance*/NotificationManager.IMPORTANCE_HIGH
        )
        private val OtherNotifications = NotificationChannel(
            /*id*/Constants.ChannelIds.ID_OTHER_NOTIFICATIONS,
            /*name*/"Other notifications",
            /*importance*/NotificationManager.IMPORTANCE_DEFAULT
        )

        private val GROUP_CONVERSATIONS = NotificationChannelGroup(
            "channel_group_conversations",
            "Conversations"
        )
        private val GROUP_OTHERS = NotificationChannelGroup(
            "channel_group_others",
            "Others"
        )


        val notificationChannelsGroups = mutableListOf(
            GROUP_CONVERSATIONS, GROUP_OTHERS
        )

        val notificationChannelsList = mutableListOf(
            NewMessages, Updates, CallNotifications,
            GroupNotifications, MessagesNotifications, OtherNotifications
        )

        init {
            notificationChannelsList.forEach {
                it.group =
                    if (it in listOf(NewMessages, GroupNotifications, MessagesNotifications)) {
                        GROUP_CONVERSATIONS.id
                    } else {
                        GROUP_OTHERS.id
                    }
            }
        }

    }

    override fun notifyUpdateAvailable() {
        val n = NotificationCompat.Builder(context, Constants.ChannelIds.ID_UPDATES_NOTIFICATIONS)
            .setContentTitle(context.getString(R.string.update_available))
            .setContentText(context.getString(R.string.tap_here_to_update))
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context, 0, Intent(context, AuthActivity::class.java), getFlag(false)
                )
            )
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .build()
        notificationManager.notify(9000, n)
    }

    override fun notifyLegalUpdateAvailable(title: String) {
        val n = NotificationCompat.Builder(context, Constants.ChannelIds.ID_OTHER_NOTIFICATIONS)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context, 0, Intent(context, AuthActivity::class.java), getFlag(false)
                )
            )
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .build()
        notificationManager.notify(Random().nextInt(), n)
    }

    override fun notifyCheckingForNewMessages() {
        val n = NotificationCompat.Builder(context, Constants.ChannelIds.ID_NEW_MESSAGES)
            .setContentTitle(context.getString(R.string.checking_for_new_messages))
            .setAutoCancel(false)
            .setSilent(true)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .build()
        with(n) {
            notificationManager.notify(9000, this)
        }
        runOnIOThread {
            delay(7000)
            notificationManager.cancel(9000)
        }
    }

    override fun notifyNewMessage() {}

    override fun notifyTestP2PMessage() {
        val p1 = Recipient.Builder()
            .withUid("USER1")
            .setPhone("+911234567891")
            .setTempName("User One")
            .setPhotoUrl(null)
            .build()
        NewMessagesNotificationHelper(context).parse(
            NotificationData.newP2PNotification(
                MessageRecord.from(
                    p1,
                    "CONVERSATION01",
                    "Hello, this is first p2p message for testing purpose.(${UUID.randomUUID()}) I will try to send you a message again tomorrow."
                ), "RECEIVER01", p1.loadName()
            )
        )
    }

    override fun notifyTestGroupMessage() {
        val p1 = Recipient.Builder()
            .withUid("USER16")
            .setPhone("+914434967871")
            .setTempName("User Sixteen")
            .setPhotoUrl(null)
            .build()
        NewMessagesNotificationHelper(context).parse(
            NotificationData.newGroupNotification(
                MessageRecord.from(
                    p1,
                    "CONVERSATION02",
                    "Hello, this is first group message for testing purpose.(${UUID.randomUUID()}) I will try to send you a message again tomorrow."
                ), p1.loadName(), "Android Developers India"
            )
        )
    }

    companion object {
        fun getFlag(mutable: Boolean = false): Int {
            return if (Build.VERSION.SDK_INT >= 31) {
                if (mutable) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_IMMUTABLE
            } else PendingIntent.FLAG_UPDATE_CURRENT
        }
    }

}
