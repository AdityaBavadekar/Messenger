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

package com.adityaamolbavadekar.messenger.utils.extensions

import android.os.Build
import com.adityaamolbavadekar.messenger.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

fun userFrom(firebaseUser: FirebaseUser): User.Builder {
    val displayName = firebaseUser.displayName?.split(" ") ?: listOf(null, null)
    return User.Builder()
        .setEmail(firebaseUser.email)
        .setUid(firebaseUser.uid)
        .setFirstName(displayName.first())
        .setLastName(displayName.last())
        .setPhotoUrl(firebaseUser.photoUrl)
        .setIsEmailVerified(firebaseUser.isEmailVerified)
        .setCreated(firebaseUser.metadata?.creationTimestamp)
        .setPhoneNumber(firebaseUser.phoneNumber)
        .setDefaultAbout()
}

fun User.Builder.setPhoneNumber(phone: Phonenumber.PhoneNumber): User.Builder {
    this.user.phoneNumberCountryCode = phone.countryCode.toString()
    this.user.phoneNumberWithoutCode = phone.nationalNumber.toString()
    this.user.phoneNumber = user.phoneNumberCountryCode + user.phoneNumberWithoutCode

    return this
}

fun User.Builder.setPhoneNumber(s: String?): User.Builder {
    if (s != null) {
        try {
            setPhoneNumber(PhoneNumberUtil.getInstance().parse(s, null))
        } catch (e: NumberParseException) {
        }
    } else {
        this.user.phoneNumberCountryCode = null
        this.user.phoneNumberWithoutCode = null
        this.user.phoneNumber = s
    }
    return this
}

fun User.updateWith(firebaseUser: FirebaseUser) {
    isEmailVerified = (firebaseUser.isEmailVerified)
    UID = (firebaseUser.uid)
    emailAddress = firebaseUser.email
    phoneNumber = firebaseUser.phoneNumber
    isEmailVerified = firebaseUser.isEmailVerified
    firebaseUser.metadata?.creationTimestamp?.let { this.created = it }
}

fun User.onLoggedIn(): User {
    lastLogin = System.currentTimeMillis()
    return this
}

fun getNewActivity(timestamp: Long, ipToken: String?): HashMap<String, Any> {
    return getGenerateNewActivity(timestamp, ipToken ?: "")
}

fun getGenerateNewActivity(timestamp: Long, ipToken: String): HashMap<String, Any> {
    val isDebuggable: Boolean = Build.TYPE != "user"
    return hashMapOf(
        UserActivityUtils.MODEL to Build.MODEL,
        UserActivityUtils.MANUFACTURER to Build.MANUFACTURER,
        UserActivityUtils.DEBUGGABLE to "$isDebuggable",
        UserActivityUtils.ANDROID_VERSION to Build.VERSION.SDK_INT,
        UserActivityUtils.TIMESTAMP to timestamp,
        UserActivityUtils.IP_ADDRESS to ipToken,
        UserActivityUtils.VERSION_NAME to com.adityaamolbavadekar.messenger.BuildConfig.VERSION_NAME,
        UserActivityUtils.VERSION_CODE to com.adityaamolbavadekar.messenger.BuildConfig.VERSION_CODE
    )

}

    object UserActivityUtils {
    const val MODEL = "deviceModel"
    const val MANUFACTURER = "manufacturer"
    const val DEBUGGABLE = "debuggable"
    const val ANDROID_VERSION = "android"
    const val TIMESTAMP = "timestamp"
    const val IP_ADDRESS = "ipAddress"
    const val VERSION_NAME = "versionName"
    const val VERSION_CODE = "versionCode"
}
