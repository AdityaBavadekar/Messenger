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

package com.adityaamolbavadekar.messenger.utils

import android.content.Context
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class AppCheckUtil {
    companion object {
        private val TAG = AppCheckUtil::class.java.simpleName
        fun activate(context: Context) {
            InternalLogger.d(TAG, "activate : Initialising FirebaseApp")
            FirebaseApp.initializeApp(context)
            InternalLogger.d(TAG, "activate : Activating AppCheck")
            val firebaseAppCheck = FirebaseAppCheck.getInstance()
            firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance()
            )
            InternalLogger.d(TAG, "activate : AppCheck activated")
        }
    }
}