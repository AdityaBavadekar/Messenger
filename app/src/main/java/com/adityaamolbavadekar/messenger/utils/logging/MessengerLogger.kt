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

package com.adityaamolbavadekar.messenger.utils.logging

import android.util.Log
import com.adityaamolbavadekar.messenger.BuildConfig

class MessengerLogger(private val tag: String = MESSENGER_LOGGER_TAG) {

    private val debug: Boolean = BuildConfig.DEBUG

    private fun canLog(level: Int): Boolean {
        return if (debug) true else Log.isLoggable(tag, level)
    }

    fun d(text: String, throwable: Throwable? = null) {
        if (canLog(Log.DEBUG)) {
            Log.d(tag, text, throwable)
        }
    }

    fun v(text: String, throwable: Throwable? = null) {
        if (canLog(Log.DEBUG)) {
            Log.v(tag, text, throwable)
        }
    }

    fun i(text: String, throwable: Throwable? = null) {
        if (canLog(Log.INFO)) {
            Log.i(tag, text, throwable)
        }
    }

    fun w(text: String, throwable: Throwable? = null) {
        if (canLog(Log.WARN)) {
            Log.w(tag, text, throwable)
        }
    }

    fun e(text: String, throwable: Throwable? = null) {
        if (canLog(Log.ERROR)) {
            Log.e(tag, text, throwable)
        }
    }

    fun log(priority: Int, message: String, force: Boolean = false) {
        if (force || canLog(priority)) {
            Log.println(priority, tag, message)
        }
    }

    companion object {

        fun getLogger(): MessengerLogger {
            if (DEFAULT_LOGGER == null) {
                DEFAULT_LOGGER = MessengerLogger()
            }
            return DEFAULT_LOGGER!!
        }

        private const val MESSENGER_LOGGER_TAG = "Messenger"
        private var DEFAULT_LOGGER: MessengerLogger? = null
    }

}