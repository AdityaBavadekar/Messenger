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

import android.content.Context
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import com.adityaamolbavadekar.messenger.R
@Entity(tableName = "accounts_table")
data class User(
    var username: String?,
    @PrimaryKey(autoGenerate = false) var UID: String,
    var firstName: String?,
    var lastName: String?,
    var emailAddress: String?,
    var photoUrl: String?,
    var about: String?,
    var isEmailVerified: Boolean,
    var created: Long,
    var day: Int,
    var month: Int,
    var year: Int,
    var fcmToken: String?,
    var phoneNumber: String?,
    var phoneNumberCountryCode: String?,
    var phoneNumberWithoutCode: String?,
    var lastLogin: Long,
    var activityCount: Int = -1,
) {

    constructor() : this(
        username = null,
        UID = UUID.randomUUID().toString(),
        firstName = null,
        lastName = null,
        emailAddress = null,
        photoUrl = null,
        about = "Hey there! I am using Grego.",
        isEmailVerified = false,
        created = System.currentTimeMillis(),
        day = 1,
        month = 0,
        year = 1915,
        fcmToken = null,
        phoneNumber = null,
        phoneNumberCountryCode = null,
        phoneNumberWithoutCode = null,
        lastLogin = System.currentTimeMillis(),
        activityCount = 0
    )

    fun toBuilder(): Builder {
        return Builder(this)
    }

    fun loggedInDataHashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "lastLogin" to lastLogin,
            "firstName" to firstName,
            "lastName" to lastName,
            "activityCount" to activityCount+1,
            "photoUrl" to photoUrl,
            "fcmToken" to fcmToken,
        )
    }

    fun toFullName(): String? {
        return if (firstName == null || lastName == null) null
        else "$firstName $lastName"
    }

    class Builder() {

        var user = User()

        constructor(fromUser: User) : this() {
            this.user = fromUser
        }

        fun setEmail(s: String?): Builder {
            this.user.emailAddress = s
            return this
        }

        fun setFcmToken(s: String?): Builder {
            this.user.fcmToken = s
            return this
        }

        fun setPhotoUrl(s: String?): Builder {
            this.user.photoUrl = s
            return this
        }

        fun setPhotoUrl(s: Uri?): Builder {
            s?.let {
                this.user.photoUrl = it.toString()
            }
            return this
        }

        fun setIsEmailVerified(s: Boolean): Builder {
            this.user.isEmailVerified = s
            return this
        }

        fun setCreated(s: Long?): Builder {
            s?.let { this.user.created = it }
            return this
        }

        fun setFirstName(s: String?): Builder {
            this.user.firstName = s
            return this
        }

        fun setLastName(s: String?): Builder {
            this.user.lastName = s
            return this
        }

        fun setPhoneNumber(
            phoneNumberCode: String,
            phoneNumberWithoutCode: String,
            phoneNumber: String = "+$phoneNumberCode$phoneNumberWithoutCode"
        ): Builder {
            this.user.phoneNumberCountryCode = phoneNumberCode
            this.user.phoneNumberWithoutCode = phoneNumberWithoutCode
            this.user.phoneNumber = phoneNumber
            return this
        }

        fun setAbout(s: String?): Builder {
            this.user.about = s
            return this
        }

        fun setAboutToDefault(s: String?): Builder {
            this.user.about = s ?: "Hey there! I am using Grego."
            return this
        }

        fun setDefaultAbout(s: Context? = null): Builder {
            val about = s?.getString(R.string.default_description) ?: "Hey there! I am using Grego."
            this.user.about = about
            return this
        }

        fun setUid(s: String): Builder {
            this.user.UID = s
            return this
        }

        fun setUsername(s: String?): Builder {
            this.user.username = s
            return this
        }

        fun setBirthInfo(month: Int, year: Int, day: Int): Builder {
            this.user.month = month
            this.user.year = year
            this.user.day = day
            return this
        }

        fun setLastLoginOrNow(time:Long?): Builder {
            this.user.lastLogin = time?:System.currentTimeMillis()
            return this
        }

        fun incrementActivityIndex(): Builder {
            this.user.activityCount = this.user.activityCount + 1
            return this
        }

        fun setActivityIndex(index:Int): Builder {
            this.user.activityCount = index
            return this
        }

        fun build(): User {
            return user
        }

    }

}
