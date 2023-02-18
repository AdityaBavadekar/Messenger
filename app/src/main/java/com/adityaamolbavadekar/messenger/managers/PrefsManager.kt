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

package com.adityaamolbavadekar.messenger.managers

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.widget.TextView
import androidx.core.content.edit
import androidx.core.widget.TextViewCompat
import androidx.preference.PreferenceManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.constants.PreferenceKeys
import com.adityaamolbavadekar.messenger.model.FontSize
import com.adityaamolbavadekar.messenger.model.PhoneNumberInfo
import com.adityaamolbavadekar.messenger.model.UpdateInfo
import com.adityaamolbavadekar.messenger.model.User
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.PhoneNumberUtils
import com.adityaamolbavadekar.messenger.utils.extensions.isNotNull
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.extensions.setPhoneNumber
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.auth.FirebaseUser
import java.io.File

class PrefsManager(private val context: Context) {

    val isDevModeEnabled: Boolean
        get() {
            return context.prefs.getBoolean(PreferenceKeys.DEV_MODE_ENABLE_FROM_SETTINGS, false)
        }

    val isChatStatusProtected: Boolean
        get() {
            return context.prefs.getBoolean(PreferenceKeys.CHAT_STATUS_PROTECTED, false)
        }

    fun getWallpaperUrl(): File? {
        return internalGetWallpaperFile()
    }

    private fun internalGetWallpaperFile(): File? {
        val dir = context.filesDir
        val wallpaperFile = try {
            File(dir, Constants.FilesNames.FILE_WALLPAPER)
        } catch (e: Exception) {
            return null
        }
        return if (wallpaperFile.exists() && wallpaperFile.length().toInt() != 0) {
            wallpaperFile
        } else {
            null
        }
    }


    fun getOrCreateWallpaperFile(): File? {
        val dir = context.filesDir
        val wallpaperFile = try {
            File(dir, Constants.FilesNames.FILE_WALLPAPER)
        } catch (e: Exception) {
            return null
        }
        return if (wallpaperFile.exists()) {
            wallpaperFile
        } else {
            wallpaperFile.createNewFile()
            wallpaperFile
        }
    }

    private fun internalDeleteWallpaperFile() = runOnIOThread {
        val dir = context.filesDir
        val wallpaperFile = File(dir, Constants.FilesNames.FILE_WALLPAPER)
        if (wallpaperFile.exists()) {
            wallpaperFile.delete()
        }
    }

    fun deleteWallpaper(onCompletion: () -> Unit) {
        internalDeleteWallpaperFile()
        onCompletion()
    }

    fun saveImeKeyboardHeight(int: Int) {
        context.prefs.edit {
            putInt(PreferenceKeys.IME_KEYBOARD_HEIGHT, int)
        }
    }

    fun getImeKeyboardHeight(): Int {
        return context.prefs.getInt(PreferenceKeys.IME_KEYBOARD_HEIGHT, 0)
    }

    fun saveCountryInfo(info: PhoneNumberUtils.CountryInfo) {
        saveUserPhoneNumberCode(info.code)
        context.prefs.edit {
            putString(PreferenceKeys.COUNTRY_INFO_ICON, info.icon)
            putString(PreferenceKeys.COUNTRY_INFO_CODE, info.code)
            putString(PreferenceKeys.COUNTRY_INFO_COUNTRY, info.country)
        }
        logFieldValueSaved("countryInfo=$info")
    }

    fun getCountryInfo(): PhoneNumberUtils.CountryInfo? {
        context.prefs.getString(PreferenceKeys.COUNTRY_INFO_CODE, null)?.let { code ->
            val country = context.prefs.getString(PreferenceKeys.COUNTRY_INFO_COUNTRY, null)!!
            return PhoneNumberUtils.CountryInfo(country, code, country, "")
        }
        return null
    }

    fun saveFcmToken(token: String?) {
        context.prefs.edit { putString(PreferenceKeys.FCM_TOKEN, token) }
        logFieldValueSaved("fcmToken=$token")
    }

    fun getFcmToken(): String? {
        return context.prefs.getString(PreferenceKeys.FCM_TOKEN, null)
    }

    fun updateLoggedInStatus(boolean: Boolean = true) {
        context.prefs.edit { putBoolean(PreferenceKeys.LOGGED_IN, boolean) }
    }

    fun getLoggedInStatus(): Boolean {
        return context.prefs.getBoolean(PreferenceKeys.LOGGED_IN, false)
    }

    fun saveUserPhoneNumber(phoneNumber: String?) {
        context.prefs.edit { putString(PreferenceKeys.USER_PHONE_NUMBER, phoneNumber) }
        logFieldValueSaved("phoneNumber=$phoneNumber")
    }

    fun getUserPhoneNumberInfo(): PhoneNumberInfo? {
        val phoneNumber = getUserPhoneNumber()
        val phoneNumberCode = getUserPhoneNumberCode()
        if (phoneNumber.isNotNull() && phoneNumberCode.isNotNull()) {
            return PhoneNumberInfo(phoneNumber!!, phoneNumberCode!!)
        }
        return null
    }


    fun getUserPhoneNumber(): String? {
        return context.prefs.getString(PreferenceKeys.USER_PHONE_NUMBER, null)
    }

    fun saveUserPhoneNumberCode(phoneNumberCode: String?) {
        context.prefs.edit { putString(PreferenceKeys.USER_PHONE_NUMBER_CODE, phoneNumberCode) }
        logFieldValueSaved("phoneNumberCode=$phoneNumberCode")
    }

    fun getUserPhoneNumberCode(): String? {
        return context.prefs.getString(PreferenceKeys.USER_PHONE_NUMBER_CODE, null)
    }

    fun saveUserEmail(email: String?) {
        context.prefs.edit { putString(PreferenceKeys.USER_EMAIL, email) }
        logFieldValueSaved("email=$email")
    }

    fun getUserEmail(): String? {
        return context.prefs.getString(PreferenceKeys.USER_EMAIL, null)
    }

    fun saveLastContactsUploaded(date: Long) {
        context.prefs.edit { putLong(PreferenceKeys.LAST_CONTACTS_UPLOADED, date) }
    }

    fun getLastContactsUploaded(): Long {
        return context.prefs.getLong(PreferenceKeys.LAST_CONTACTS_UPLOADED, -1)
    }

    fun getUserTempEmail(): String? {
        return context.prefs.getString(PreferenceKeys.USER_TEMP_EMAIL, null)
    }

    fun saveUserUID(uid: String?) {
        context.prefs.edit { putString(PreferenceKeys.USER_UID, uid) }
        logFieldValueSaved("uid=$uid")
    }

    fun getUserUID(): String? {
        return context.prefs.getString(PreferenceKeys.USER_UID, null)
    }

    fun saveUserUsername(username: String?) {
        context.prefs.edit { putString(PreferenceKeys.USER_USERNAME, username) }
        logFieldValueSaved("username=$username")
    }

    fun getUserUsername(): String? {
        return context.prefs.getString(PreferenceKeys.USER_USERNAME, null)
    }

    fun saveUserDescription(description: String?) {
        context.prefs.edit { putString(PreferenceKeys.USER_DESCRIPTION, description) }
        logFieldValueSaved("description=$description")
    }

    fun getUserDescription(): String {
        return context.prefs.getString(PreferenceKeys.USER_DESCRIPTION, null)
            ?: context.getString(R.string.default_description)
    }

    fun saveUserFirstName(firstName: String?) {
        context.prefs.edit { putString(PreferenceKeys.USER_FIRST_NAME, firstName) }
        logFieldValueSaved("firstName=$firstName")
    }

    fun getUserFirstName(): String? {
        return context.prefs.getString(PreferenceKeys.USER_FIRST_NAME, null)
    }

    fun saveUserLastName(lastName: String?) {
        context.prefs.edit { putString(PreferenceKeys.USER_LAST_NAME, lastName) }
        logFieldValueSaved("lastName=$lastName")
    }

    fun getUserLastName(): String? {
        return context.prefs.getString(PreferenceKeys.USER_LAST_NAME, null)
    }

    fun saveUserFullName(fullName: String?) {
        context.prefs.edit { putString(PreferenceKeys.USER_NAME, fullName) }
        logFieldValueSaved("fullName=$fullName")
    }

    fun getUserFullName(): String? {
        return context.prefs.getString(PreferenceKeys.USER_NAME, null)
    }

    private fun logFieldValueSaved(s: String) {
        InternalLogger.debugInfo(TAG, "Value saved $s")
    }

    fun saveUserPhotoUrl(photoUrl: String?) {
        photoUrl?.let {
            context.prefs.edit { putString(PreferenceKeys.USER_PHOTO_URL, photoUrl) }
            logFieldValueSaved("photoUrl=$photoUrl")
        }
    }

    fun saveUserPhotoUrl(photoUri: Uri) {
        context.prefs.edit { putString(PreferenceKeys.USER_PHOTO_URL, photoUri.toString()) }
        logFieldValueSaved("photoUrl=$photoUri")
    }

    fun getUserPhotoUrl(): String? {
        return context.prefs.getString(PreferenceKeys.USER_PHOTO_URL, null)
    }

    fun saveNotificationId(conversationId: String, notificationId: Int) {
        context.prefs.edit { putInt(conversationId, notificationId) }
        logFieldValueSaved("ConversationId:NotificationId=$conversationId : $notificationId")
    }

    fun getSavedNotificationId(conversationId: String): Int? {
        val id = context.prefs.getInt(conversationId, -1)
        return if (id == -1) null
        else id
    }

    fun resetAllPrefs() {
        context.prefs.edit {
            PreferenceKeys.allRemovablePreferenceKeys.forEach { key -> remove(key) }
        }
    }

    fun getUserObject(): User? {
        val am = AuthManager()
        val firebaseUser = am.getFirebaseUser
        return if (am.isLoggedIn && firebaseUser != null) {
            val u = User.Builder()
                .setEmail(firebaseUser.email)
                .setUid(firebaseUser.uid)
                .setFirstName(getUserFirstName() ?: "")
                .setLastName(getUserLastName() ?: "")
                .setPhotoUrl(firebaseUser.photoUrl)
                .setIsEmailVerified(firebaseUser.isEmailVerified)
                .setCreated(firebaseUser.metadata?.creationTimestamp)
                .setPhoneNumber(firebaseUser.phoneNumber)
                .setAboutToDefault(getUserDescription())
                .setUsername(getUserUsername())
                .setFcmToken(getFcmToken())
                .build()
            InternalLogger.logI(TAG, "UserObject requested : $u")
            u
        } else null
    }

    fun saveUserObject(t: User) {
        InternalLogger.logI(TAG, "saving UserObject")
        saveUserUID(t.UID)
        saveUserEmail(t.emailAddress)
        saveUserUsername(t.username)
        saveUserFirstName(t.firstName)
        saveUserLastName(t.lastName)
        saveUserFullName(t.toFullName())
        saveUserDescription(t.about)
        saveUserPhotoUrl(t.photoUrl)
        saveFcmToken(t.fcmToken)
        saveUserPhoneNumber(t.phoneNumber)
    }

    fun printAllPrefs() {
        if (!InternalLogger.isDebugBuild) return
        var string = ""
        for ((key: String, value: Any?) in (context.prefs.all)) {
            string += "[${key}=${value.toString()}] "
        }
        if (string.trim().isEmpty()) {
            InternalLogger.logI(TAG, "Preferences are Empty")
        } else {
            InternalLogger.logI(TAG, "Preferences : $string")
        }
    }

    fun saveCrashInfo() {
        val prefs = context.prefs
        val crashCount = prefs.getInt(PreferenceKeys.APP_CRASH_COUNT, 0)
        prefs.edit {
            putBoolean(PreferenceKeys.APP_DID_CRASH, true)
            putLong(PreferenceKeys.APP_LAST_CRASH_TIMESTAMP, System.currentTimeMillis())
            putInt(PreferenceKeys.APP_CRASH_COUNT, crashCount + 1)
        }
    }

    fun refreshUser(firebaseUser: FirebaseUser) {
        saveUserEmail(firebaseUser.email)
        firebaseUser.photoUrl?.let { saveUserPhotoUrl(it) }
        saveUserPhoneNumber(firebaseUser.phoneNumber)
        saveUserUID(firebaseUser.uid)
        firebaseUser.metadata?.lastSignInTimestamp
        InternalLogger.logD(TAG, "Refreshed from firebase user.")
    }

    fun saveTextSize(int: Int) {
        context.prefs.edit {
            putInt(PreferenceKeys.TEXT_SIZE, int)
        }
    }

    fun getLastUpdateChecked(): Long? {
        val l = context.prefs.getLong(PreferenceKeys.APP_UPDATE_CHECKED, -1)
        val ln: Long = -1
        if (l == ln) return null
        else return l
    }

    fun saveUpdateInfo(updateInfo: UpdateInfo) {
        context.prefs.edit {
            putLong(PreferenceKeys.APP_UPDATE_CHECKED, System.currentTimeMillis())
            putString(PreferenceKeys.APP_UPDATE_VERSION_NAME, updateInfo.versionName)
            putInt(PreferenceKeys.APP_UPDATE_VERSION_CODE, updateInfo.versionCode)
            putLong(PreferenceKeys.APP_UPDATE_TIMESTAMP, updateInfo.timestamp)
            putString(PreferenceKeys.APP_UPDATE_LINK, updateInfo.link)
        }
    }

    fun getUpdateInfo(): UpdateInfo? {
        context.prefs.apply {
            val versionName = getString(PreferenceKeys.APP_UPDATE_VERSION_NAME, null)
            val versionCode = getInt(PreferenceKeys.APP_UPDATE_VERSION_CODE, -1)
            val timestamp = getLong(PreferenceKeys.APP_UPDATE_TIMESTAMP, -1)
            val link = getString(PreferenceKeys.APP_UPDATE_LINK, null)
            val l: Long = -1
            if (versionName != null && versionCode != -1 && timestamp != l && link != null) {
                return UpdateInfo(versionName, versionCode, timestamp, link)
            }
        }
        return null
    }

    fun isContactsSyncAllowed(): Int {
        return context.prefs.getInt(
            PreferenceKeys.CONTACTS_SYNC,
            Constants.CONTACTS_SYNC_NOT_PROMPTED
        )
    }

    fun saveIsContactsSyncAllowed(value: Int) {
        context.prefs
            .edit {
                putInt(PreferenceKeys.CONTACTS_SYNC, value)
            }
    }

    companion object {
        private val TAG = PrefsManager::class.java.simpleName
        val Context.prefs: SharedPreferences
            get() = PreferenceManager.getDefaultSharedPreferences(
                this
            )

        fun TextView.applyFontSize() {
            val f = when (context.prefs.getInt(PreferenceKeys.TEXT_SIZE, FontSize.MEDIUM)) {
                FontSize.SMALL -> R.style.TextAppearance_Messenger_Message_BodySmall
                FontSize.MEDIUM -> R.style.TextAppearance_Messenger_Message_Body
                else -> R.style.TextAppearance_Messenger_Message_BodyLarge
            }
            TextViewCompat.setTextAppearance(this, f)
        }
    }

}
