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

package com.adityaamolbavadekar.messenger.ui.conversation

import android.content.Context
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.model.PresenceStatus
import com.adityaamolbavadekar.messenger.utils.extensions.getDate


object StatusParser {

    fun parse(s: Long, c: Context): String? {
        return when (s) {
            PresenceStatus.ONLINE -> c.getString(R.string.online_status)
            PresenceStatus.TYPING -> c.getString(R.string.status_typing)
            PresenceStatus.PROTECTED -> null
            PresenceStatus.UNKNOWN -> null
            else -> c.getString(R.string.last_seen_formatted, getDate(s, c))
        }
    }

}
