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

import androidx.appcompat.app.AppCompatActivity
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.ktx.Firebase

object LinksHandler {

    fun checkForLinks(activity: AppCompatActivity) {
        activity.intent?.data?.let {
            InternalLogger.logI(getTag(activity), "intent.data = $it")
        }
        activity.intent?.action?.let {
            InternalLogger.logI(getTag(activity), "intent.action = $it")
        }
    }

    private fun getTag(activity: AppCompatActivity): String {
        return activity.javaClass.simpleName + "#LinksHandler"
    }

}