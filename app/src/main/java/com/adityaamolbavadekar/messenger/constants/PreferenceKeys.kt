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

package com.adityaamolbavadekar.messenger.constants


object PreferenceKeys {

    /*app crash data*/
    const val APP_DID_CRASH = "app.crash.happened"
    const val APP_LAST_CRASH_TIMESTAMP = "app.crash.timestamp"
    const val APP_CRASH_COUNT = "app.crash.count"
    const val APP_STATE = "app.state"
    /*app crash data*/

    /*user account data*/
    const val COUNTRY_INFO_CODE = "user.countryinfo.code"
    const val COUNTRY_INFO_COUNTRY = "user.countryinfo.country"
    const val COUNTRY_INFO_ICON = "user.countryinfo.icon"
    const val FCM_TOKEN = "android.firebase.fcm.token"
    const val LOGGED_IN = "android.auth.logged_in"
    const val USER_PHONE_NUMBER = "user.phone.number"
    const val USER_PHONE_NUMBER_CODE = "user.phone.code"
    const val USER_TEMP_EMAIL = "user.temp.email"
    const val USER_EMAIL = "user.email"
    const val USER_FIRST_NAME = "user.name.first"
    const val USER_LAST_NAME = "user.name.last"
    const val USER_NAME = "user.name"
    const val USER_USERNAME = "user.username"
    const val USER_UID = "user.uid"
    const val USER_DESCRIPTION = "user.description"
    const val USER_PHOTO_URL = "user.photoUrl"
    /*user account data*/

    /*settings data*/
    const val CHAT_STATUS_PROTECTED = "settings.conversation.status.is_protected"
    const val APP_THEME = "settings.app.theme"
    const val TEXT_SIZE = "settings.app.text_size"
    const val LAST_CONTACTS_UPLOADED = "app.lastContactsUploaded"
    /*settings data*/

    /*app data*/
    const val ABOUT_APP_VERSION = "about.version"
    const val ABOUT_OS = "about.os"
    const val ABOUT_HELP = "about.help"
    const val ABOUT_FEEDBACK = "about.send_feedback"
    const val ABOUT_OPEN_SOURCE_LICENCES = "about.open_source"
    const val ABOUT_TERMS = "about.terms"
    const val ABOUT_POLICY = "about.policy"
    const val APP_UPDATE_CHECKED = "app.update.lastChecked"
    const val APP_UPDATE_VERSION_NAME = "app.update.versionName"
    const val APP_UPDATE_VERSION_CODE = "app.update.versionCode"
    const val APP_UPDATE_TIMESTAMP = "app.update.timestamp"
    const val APP_UPDATE_LINK = "app.update.link"
    const val IME_KEYBOARD_HEIGHT = "messenger.value.ime.height"
    const val DEV_MODE_ENABLE_FROM_SETTINGS = "app.dev.enabled"
    /*app data*/

    private val allRemovableUserPreferenceKeys: Array<String> = arrayOf(
        USER_FIRST_NAME,
        USER_LAST_NAME,
        USER_DESCRIPTION,
        USER_TEMP_EMAIL,
        USER_EMAIL,
        USER_USERNAME,
        USER_PHONE_NUMBER,
        USER_PHONE_NUMBER_CODE,
        USER_PHOTO_URL,
        USER_UID,
        USER_NAME,
        FCM_TOKEN,
        COUNTRY_INFO_CODE,
        COUNTRY_INFO_COUNTRY,
    )

    private val allRemovableSettingsPreferenceKeys: Array<String> = arrayOf(
        CHAT_STATUS_PROTECTED,
        APP_THEME,
        TEXT_SIZE,
        LAST_CONTACTS_UPLOADED
    )

    val allRemovablePreferenceKeys: Array<String> = arrayOf(
        *allRemovableUserPreferenceKeys,
        *allRemovableSettingsPreferenceKeys
    )


}
