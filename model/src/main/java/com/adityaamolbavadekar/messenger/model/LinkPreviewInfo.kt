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

import android.net.Uri
import android.util.Patterns
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "link_previews_table")
data class LinkPreviewInfo(
    @PrimaryKey(autoGenerate = false)
    val id: String = Id.get(),
    var title: String? = null,
    var body: String? = null,
    var imgUrl: String? = null,
    val url: String = "https://example.com"
) : RemoteDatabasePersitable {

    override fun hashMap(): HashMap<String, Any?> {
        return hashMapOf(
            Fields.ID to id,
            Fields.TITLE to title,
            Fields.BODY to body,
            Fields.IMAGE_URL to imgUrl,
            Fields.URL to url,
        )
    }

    private annotation class Fields {
        companion object {
            const val ID = "id"
            const val TITLE = "title"
            const val BODY = "body"
            const val IMAGE_URL = "imgUrl"
            const val URL = "url"
        }
    }

    fun getHost(): String? {
        return Uri.parse(url).host?.lowercase(Locale.ROOT)
    }

    fun uri(): Uri {
        return Uri.parse(url)
    }

    fun isTypeEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.toRegex().matches(url)
    }

    fun lacksRequiredData(): Boolean {
        return title == null || body == null || imgUrl == null
    }

}

