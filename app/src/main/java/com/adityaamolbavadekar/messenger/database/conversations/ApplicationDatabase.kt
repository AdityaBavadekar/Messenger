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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.adityaamolbavadekar.messenger.model.*
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

@Database(
    entities =
    [
        User::class,
        ConversationRecord::class,
        MessageRecord::class,
        ReactionRecord::class,
        Recipient::class,
        ConversationRecordRecipientCrossRef::class,
        ConversationDraftMessage::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(ModelConverters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun dao(): ConversationDao

    companion object {

        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun get(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, ApplicationDatabase::class.java,
                    "database_main"
                ).addCallback(callback)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val callback = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                InternalLogger.logD("RoomDatabase.Callback","Database created")
            }
        }

    }


}