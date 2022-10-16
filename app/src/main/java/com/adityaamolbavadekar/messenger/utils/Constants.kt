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

package com.adityaamolbavadekar.messenger.utils

import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import java.util.*

object Constants {

    const val EXTRA_CONVERSATION_ID = "extra_conversation_id"
    const val UNKNOWN_VALUE_LONG: Long = -1
    const val UNKNOWN_VALUE_INT: Int = -1
    const val DATA_SYNC_AUTHORITY: String = "com.adityaamolbavadekar.messenger.sync"

    internal object Extras {
        const val EXTRA_UID = "extra_uid"
        const val EXTRA_IS_GROUP = "extra_is_group"
        const val EXTRA_IS_P2P = "extra_is_p2p"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_USER_UID = "extra_user_uid"
        const val EXTRA_TIMESTAMP = "extra_timestamp"
    }

    internal object Actions {
        const val ACTION_LOGIN = "com.adityaamolbavadekar.messenger.LOGIN"
        const val ACTION_LOGIN_ACCOUNT_MANAGER_ADD =
            "com.adityaamolbavadekar.messenger.ACCOUNT_MANAGER_ADD_ACCOUNT"
    }

    internal object TimestampFormats {
        const val MESSAGE_TIMESTAMP_FORMAT = "h:mm a"
        const val TIMESTAMP_FORMAT_DAY_MONTH = "d MMMM "
        const val TIMESTAMP_FORMAT_DAY_MONTH_YEAR = "E d MMMM, YYYY"
        const val UNDERSCORED_TIMESTAMP_FORMAT_FULL = "E_d_MMMM_YYYY_h_mm_ss"
        const val SLASHED_TIMESTAMP_FORMAT_FULL = "d/MM/YYYY"
    }

    internal object CloudPaths {
        private const val CLOUD_PATH_USERS_SHARABLE_DATA = "users_sharable_data"
        const val CLOUD_PATH_USERS = "users"
        private const val CLOUD_PATH_CONTACTS = "contactsOf"
        private const val CLOUD_PATH_USER_ACTIVITY = "users_activity"
        private const val CLOUD_PATH_PROFILE_PICS = "profilePictures"
        private const val CLOUD_PATH_MESSAGES = "messages"
        private const val CLOUD_PATH_GROUPS = "groups"
        private const val CLOUD_PATH_PROPERTIES = "properties"
        private const val CLOUD_PATH_CONVERSATIONS = "conversations"
        private const val CLOUD_PATH_PICTURES = "pictures"
        private const val CLOUD_PATH_PRESENCE_STATUS = "presenceStatus"

        fun getUsersPath(): String {
            return (CLOUD_PATH_USERS)
        }

        fun getUserActivityPath(uid: String): String {
            return ("$CLOUD_PATH_USER_ACTIVITY/$uid")
        }

        fun getUserPath(uid: String): String {
            return ("$CLOUD_PATH_USERS/$uid")
        }

        fun getUsersPublicPath(): String {
            return (CLOUD_PATH_USERS_SHARABLE_DATA)
        }

        fun getUserPublicPath(uid: String): String {
            return ("$CLOUD_PATH_USERS_SHARABLE_DATA/$uid")
        }

        fun getUserConversationsPath(uid: String): String {
            return ("$CLOUD_PATH_MESSAGES/$uid/$CLOUD_PATH_CONVERSATIONS/")
        }

        fun getGroupMessagesPath(id: String): String {
            return ("$CLOUD_PATH_GROUPS/$id/$CLOUD_PATH_MESSAGES")
        }

        fun getGroupPropertiesPath(id: String): String {
            return ("$CLOUD_PATH_GROUPS/$id/$CLOUD_PATH_PROPERTIES")
        }

        fun getP2PMessagesPath(of: String, with: String): String {
            return ("$CLOUD_PATH_MESSAGES/$of/$with/$CLOUD_PATH_MESSAGES/")
        }

        fun getP2PPropertiesPath(of: String, with: String): String {
            return ("$CLOUD_PATH_MESSAGES/$of/$with/$CLOUD_PATH_PROPERTIES/")
        }

        fun getUserProfilePicturesPath(uid: String): String {
            return ("$CLOUD_PATH_USERS/$CLOUD_PATH_PROFILE_PICS/$uid")
        }

        fun getConversationPicturesDocument(): String {
            return ("$CLOUD_PATH_PICTURES/PIC_" +
                    simpleDateFormat(
                        System.currentTimeMillis(),
                        TimestampFormats.UNDERSCORED_TIMESTAMP_FORMAT_FULL
                    ).toUpperCase(Locale.ROOT))
        }

        fun getPresenceStatusRootPath(): String {
            return (CLOUD_PATH_PRESENCE_STATUS)
        }

        fun getUserContactsPath(uid: String): String {
            return ("$CLOUD_PATH_CONTACTS/$uid")
        }

    }

    internal object FcmMessaging {
        const val FCM_MESSAGING_BASE_URL = com.adityaamolbavadekar.messenger.BuildConfig.FCM_MESSAGING_BASE_URL
        const val FCM_MESSAGING_SERVER_KEY =com.adityaamolbavadekar.messenger.BuildConfig.FCM_MESSAGING_SERVER_KEY
        const val FCM_MESSAGING_CONTENT_TYPE =com.adityaamolbavadekar.messenger.BuildConfig.FCM_MESSAGING_CONTENT_TYPE
    }

    internal object Application {
        const val TERMS_LINK = "https://www.messenger.org/terms"
        const val POLICY_LINK = "https://www.messenger.org/privacy-policy"
    }

    internal object FilesNames {
        const val FILE_WALLPAPER = "wallpaper.jpg"
    }

    object ChannelIds {
        const val ID_NEW_MESSAGES = "channel_new_messages"
        const val ID_UPDATES_NOTIFICATIONS = "channel_updates"
        const val ID_CALL_NOTIFICATIONS = "channel_call_notifications"
        const val ID_GROUP_NOTIFICATIONS = "channel_group_notifications"
        const val ID_MESSAGE_NOTIFICATIONS = "channel_messages_notifications"
        const val ID_OTHER_NOTIFICATIONS = "channel_other_notifications"
        const val ID_TESTING_NOTIFICATIONS = "testing"
    }

}
