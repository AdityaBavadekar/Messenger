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

package com.adityaamolbavadekar.messenger.database.conversations

import androidx.room.TypeConverter
import com.adityaamolbavadekar.messenger.model.LinkPreviewInfo
import com.adityaamolbavadekar.messenger.model.MessageReplyRecord
import com.adityaamolbavadekar.messenger.model.ReactionRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ModelConverters {
    companion object {

        @JvmStatic
        @TypeConverter
        fun fromMutableRecipientList(recipients: MutableList<Recipient>): String {
            val type = object : TypeToken<MutableList<Recipient>>() {}.type
            return Gson().toJson(recipients, type)
        }

        @JvmStatic
        @TypeConverter
        fun toMutableRecipientList(json: String): MutableList<Recipient> {
            val type = object : TypeToken<MutableList<Recipient>>() {}.type
            return Gson().fromJson(json, type)
        }

        @JvmStatic
        @TypeConverter
        fun fromStringList(list: List<String>): String {
            val type = object : TypeToken<List<String>>() {}.type
            return Gson().toJson(list, type)
        }

        @JvmStatic
        @TypeConverter
        fun toStringList(json: String): List<String> {
            val type = object : TypeToken<List<String>>() {}.type
            return Gson().fromJson(json, type)
        }

        @JvmStatic
        @TypeConverter
        fun fromReactionRecordList(list: List<ReactionRecord>): String {
            val type = object : TypeToken<List<ReactionRecord>>() {}.type
            return Gson().toJson(list, type)
        }

        @JvmStatic
        @TypeConverter
        fun toReactionRecordList(json: String): List<ReactionRecord> {
            val type = object : TypeToken<List<ReactionRecord>>() {}.type
            return Gson().fromJson(json, type)
        }

        @JvmStatic
        @TypeConverter
        fun fromLinkPreview(list: LinkPreviewInfo?): String? {
            if (list == null) return null
            val type = object : TypeToken<LinkPreviewInfo>() {}.type
            return Gson().toJson(list, type)
        }

        @JvmStatic
        @TypeConverter
        fun toLinkPreview(json: String?): LinkPreviewInfo? {
            if (json == null) return null
            val type = object : TypeToken<LinkPreviewInfo>() {}.type
            return Gson().fromJson(json, type)
        }

        @JvmStatic
        @TypeConverter
        fun fromMessageReplyRecord(list: MessageReplyRecord?): String? {
            if (list == null) return null
            val type = object : TypeToken<MessageReplyRecord>() {}.type
            return Gson().toJson(list, type)
        }

        @JvmStatic
        @TypeConverter
        fun toMessageReplyRecord(json: String?): MessageReplyRecord? {
            if (json == null) return null
            val type = object : TypeToken<MessageReplyRecord>() {}.type
            return Gson().fromJson(json, type)
        }

        @JvmStatic
        @TypeConverter
        fun fromUserActivityList(list: List<HashMap<String, Any>>): String {
            val type = object : TypeToken<List<HashMap<String, Any>>>() {}.type
            return Gson().toJson(list, type)
        }

        @JvmStatic
        @TypeConverter
        fun toUserActivityList(json: String): List<HashMap<String, Any>> {
            val type = object : TypeToken<List<HashMap<String, Any>>>() {}.type
            return Gson().fromJson(json, type)
        }

    }
}