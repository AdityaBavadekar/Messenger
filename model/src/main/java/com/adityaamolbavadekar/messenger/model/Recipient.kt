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

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Represents a User that can be fetched from Remote Database and also saved to Room.
 * */
@Entity(tableName = "recipients_table")
data class Recipient(
    var tempName: String?,
    @PrimaryKey(autoGenerate = false) var uid: String,
    var username: String,
    var fcmTokens: MutableList<String>,
    var tempAbout: String? = null,
    var verified: Boolean = false,
    var lastSeen: Long = 0,
    var email: String? = null,
    var phone: String?= null,
    var photoUrl: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        tempName = parcel.readString(),
        uid = parcel.readString()!!,
        username = parcel.readString()!!,
        fcmTokens = mutableListOf<String>().also {
            parcel.readStringList(it)
        },
        tempAbout = parcel.readString(),
        verified = parcel.readByte() != 0.toByte(),
        lastSeen = parcel.readLong(),
        email = parcel.readString(),
        phone = parcel.readString(),
        photoUrl = parcel.readString()
    )

    constructor() : this(
        null,
        "",
        "", mutableListOf(),
        null,
        false,
        0,
        null,
        null,
        null
    )

    class Builder constructor() {
        private var recipient: Recipient = empty()

        public constructor(user: User) : this() {
            withUid(user.UID)
            setUsername(user.username ?: "")
            setEmail(user.emailAddress)
            setPhone(user.phoneNumber)
            setVerified(true)
            setPhotoUrl(user.photoUrl)
            setTempAbout(user.about)
            setTempName(user.toFullName() ?: "")
            user.fcmToken?.let { addFcmToken(it) }
        }

        fun setTempName(s: String?): Builder {
            recipient.tempName = s
            return this
        }

        fun setTempAbout(s: String?): Builder {
            recipient.tempAbout = s
            return this
        }

        fun withUid(s: String): Builder {
            recipient.uid = s
            return this
        }

        fun from(r: Recipient) {
            recipient = r
        }

        fun setUsername(s: String): Builder {
            recipient.username = s
            return this
        }

        fun setVerified(s: Boolean): Builder {
            recipient.verified = s
            return this
        }

        fun setLastSeen(s: Long): Builder {
            recipient.lastSeen = s
            return this
        }

        fun setLastSeenToNow(): Builder {
            recipient.lastSeen = System.currentTimeMillis()
            return this
        }

        fun setEmail(s: String?): Builder {
            recipient.email = s
            return this
        }

        fun setPhone(s: String?): Builder {
            recipient.phone = s
            return this
        }

        fun setPhotoUrl(s: String?): Builder {
            recipient.photoUrl = s
            return this
        }

        fun addFcmToken(s: String): Builder {
            recipient.fcmTokens.add(s)
            return this
        }

        fun build(): Recipient {
            return recipient
        }

    }


    override fun describeContents(): Int {
        return 0
    }

    fun loadName(): String {
        return tempName ?: username
    }

    fun loadFirstName(): String {
        val n = loadName()
        return n.split(" ").first()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tempName)
        parcel.writeString(uid)
        parcel.writeString(username)
        parcel.writeStringList(fcmTokens)
        parcel.writeString(tempAbout)
        parcel.writeByte(if (verified) 1 else 0)
        parcel.writeLong(lastSeen)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(photoUrl)
    }

    companion object CREATOR : Parcelable.Creator<Recipient> {
        override fun createFromParcel(parcel: Parcel): Recipient {
            return Recipient(parcel)
        }

        override fun newArray(size: Int): Array<Recipient?> {
            return arrayOfNulls(size)
        }

        fun empty(): Recipient {
            return Recipient()
        }
    }

    override fun toString(): String {
        return "**RECIPIENT_START**" + "\n" +
                "Uid : $uid" + "\n" +
                "Username : $username" + "\n" +
                "FcmTokens : $fcmTokens" + "\n" +
                "TempName : $tempName" + "\n" +
                "TempAbout : $tempAbout" + "\n" +
                "Verified : $verified" + "\n" +
                "Email : $email" + "\n" +
                "LastSeen : $lastSeen" + "\n" +
                "Phone : $phone" + "\n" +
                "PhotoUrl : $photoUrl" + "\n" +
                "**RECIPIENT_END**"
    }

}