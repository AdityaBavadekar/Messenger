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

package com.adityaamolbavadekar.messenger.utils

import android.content.Context
import com.adityaamolbavadekar.messenger.BuildConfig
import com.adityaamolbavadekar.messenger.model.Id
import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import java.io.File
import java.util.*

object Constants {

    const val FILE_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + "." + "fileprovider"
    const val VOICE_CALL_SUPPORTED = false


    const val CONTACTS_SYNC_NOT_PROMPTED: Int = 1
    const val CONTACTS_SYNC_ALLOWED: Int = 2
    const val CONTACTS_SYNC_DENIED: Int = 3

    const val EXTRA_CONVERSATION_ID = "extra_conversation_id"
    const val UNKNOWN_VALUE_LONG: Long = -1
    const val UNKNOWN_VALUE_INT: Int = -1
    const val DATA_SYNC_AUTHORITY: String = "com.adityaamolbavadekar.messenger.sync"

    internal object Extras {
        const val EXTRA_UID = "extra_uid"
        const val EXTRA_IS_GROUP = "extra_is_group"
        const val EXTRA_IS_P2P = "extra_is_p2p"
        const val EXTRA_CONVERSATION_TYPE = "extra_conversation_type"
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
        const val NATURAL_FILE_NAME_FORMAT = "yyyyMMdd_HHmmss"
        const val MESSAGE_TIMESTAMP_FORMAT = "h:mm a"
        const val TIMESTAMP_FORMAT_DAY = "d"
        const val TIMESTAMP_FORMAT_DAY_MONTH = "d MMMM "
        const val TIMESTAMP_FORMAT_DAY_MONTH_YEAR = "E d MMMM, yyyy"
        const val TIMESTAMP_FORMAT_FULL = "E, d MMMM YYYY, h:mm:ss"
        const val UNDERSCORED_TIMESTAMP_FORMAT_FULL = "E_d_MMMM_yyyy_h_mm_ss"
        const val SLASHED_TIMESTAMP_FORMAT_FULL = "d/MM/yyyy"
    }

    internal object AppDirectories {
        private const val NAME_PARENT_MEDIA_DIR = "Messenger"
        private const val NAME_PARENT_IMAGES_DIR = NAME_PARENT_MEDIA_DIR + "/Messenger Images"
        private const val NAME_PARENT_VIDEOS_DIR = NAME_PARENT_MEDIA_DIR + "/Messenger Videos"
        private const val NAME_PARENT_DOCS_DIR = NAME_PARENT_MEDIA_DIR + "/Messenger Documents"
        private const val NAME_PARENT_SENT_DOCS_DIR = NAME_PARENT_DOCS_DIR + "/Sent"

        fun createDefaultDirs(context: Context) {
            try {
                getMediaDir(context)
                getDocsDir(context)
                getImagesDir(context)
                getVideosDir(context)
            } catch (e: Exception) {
                InternalLogger.logE("Constants", "Unable create dirs", e)
            }
        }

        fun getMediaDir(context: Context): File {
            val dir =
                AndroidUtils.getApplicationMediaDirectory(context).absolutePath + "/" + NAME_PARENT_MEDIA_DIR
            val f = File(dir)
            if (!f.exists()) f.mkdir()
            return f
        }

        fun getSentDocsDir(context: Context): File {
            val dir =
                AndroidUtils.getApplicationMediaDirectory(context).absolutePath + "/" + NAME_PARENT_SENT_DOCS_DIR
            val f = File(dir)
            if (!f.exists()) f.mkdirs()
            return f
        }

        fun getDocsDir(context: Context): File {
            val dir =
                AndroidUtils.getApplicationMediaDirectory(context).absolutePath + "/" + NAME_PARENT_DOCS_DIR
            val f = File(dir)
            if (!f.exists()) f.mkdirs()
            return f
        }

        fun getImagesDir(context: Context): File {
            val dir =
                AndroidUtils.getApplicationMediaDirectory(context).absolutePath + "/" + NAME_PARENT_IMAGES_DIR
            val f = File(dir)
            if (!f.exists()) f.mkdirs()
            return f
        }

        fun getVideosDir(context: Context): File {
            val dir =
                AndroidUtils.getApplicationMediaDirectory(context).absolutePath + "/" + NAME_PARENT_VIDEOS_DIR
            val f = File(dir)
            if (!f.exists()) f.mkdirs()
            return f
        }

    }

    internal object CloudPaths {
        private const val CLOUD_PATH_USERS_SHARABLE_DATA = "usersPublicData"
        const val CLOUD_PATH_USERS = "users"
        const val CLOUD_PATH_UPDATES = "updates/messenger/latest/"
        private const val CLOUD_PATH_CONTACTS = "contactsOf"
        private const val CLOUD_PATH_USER_ACTIVITY = "usersActivity"
        private const val CLOUD_PATH_PROFILE_PICS = "profilePictures"
        private const val CLOUD_PATH_MESSAGES = "messages"
        private const val CLOUD_PATH_P2P_MESSAGES = "p2pMessages"
        private const val CLOUD_PATH_GROUPS = "groups"
        private const val CLOUD_PATH_PROPERTIES = "properties"
        private const val CLOUD_PATH_CONVERSATIONS = "conversations"
        private const val CLOUD_PATH_PICTURES = "pictures"
        private const val CLOUD_PATH_DOCUMENTS = "documents"
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
            return ("$CLOUD_PATH_CONVERSATIONS/$uid/")
        }

        fun getGroupMessagesPath(id: String): String {
            return ("$CLOUD_PATH_GROUPS/$id/$CLOUD_PATH_MESSAGES")
        }

        fun getGroupPropertiesPath(id: String): String {
            return ("$CLOUD_PATH_GROUPS/$id/$CLOUD_PATH_PROPERTIES")
        }

        fun getP2PMessagesPath(of: String, with: String): String {
            return ("$CLOUD_PATH_P2P_MESSAGES/$of/$with/$CLOUD_PATH_MESSAGES/")
        }

        fun getP2PPropertiesPath(of: String, with: String): String {
            return ("$CLOUD_PATH_P2P_MESSAGES/$of/$with/$CLOUD_PATH_PROPERTIES/")
        }

        fun getUserProfilePicturesPath(uid: String): String {
            return ("$CLOUD_PATH_USERS/$CLOUD_PATH_PROFILE_PICS/$uid")
        }

        fun getConversationPicturesDocument(): String {
            return ("$CLOUD_PATH_PICTURES/PIC_" + Id.get(5) + "_" +
                    simpleDateFormat(
                        System.currentTimeMillis(),
                        TimestampFormats.UNDERSCORED_TIMESTAMP_FORMAT_FULL
                    ).uppercase(Locale.getDefault()))
        }

        fun getConversationDocumentPath(fileName: String): String {
            return ("$CLOUD_PATH_DOCUMENTS/" + fileName)
        }

        fun getPresenceStatusRootPath(): String {
            return (CLOUD_PATH_PRESENCE_STATUS)
        }

        fun getUserContactsPath(uid: String): String {
            return ("$CLOUD_PATH_CONTACTS/$uid")
        }

    }

    internal object FcmMessaging {
        const val FCM_MESSAGING_BASE_URL = "https://fcm.googleapis.com/fcm/send/"
        const val FCM_MESSAGING_SERVER_KEY =
            com.adityaamolbavadekar.messenger.BuildConfig.FCM_MESSAGING_SERVER_KEY
        const val FCM_MESSAGING_CONTENT_TYPE = "application/json"
    }

    internal object Application {
        const val TERMS_LINK =
            "https://github.com/AdityaBavadekar/Messenger/blob/main/PrivacyPolicy.md"
        const val POLICY_LINK =
            "https://github.com/AdityaBavadekar/Messenger/blob/main/PrivacyPolicy.md"
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

    const val SUPPORT_EMAIL = "projectsandstudiesaditya@gmail.com"

}
