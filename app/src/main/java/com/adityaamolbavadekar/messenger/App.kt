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

package com.adityaamolbavadekar.messenger

import android.app.Application
import com.adityaamolbavadekar.messenger.database.conversations.ApplicationDatabase
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.utils.startup.AppStartup
import com.adityaamolbavadekar.pinlog.PinLog
import com.google.firebase.FirebaseApp

class App : Application(),Thread.UncaughtExceptionHandler {

    val database: ApplicationDatabase by lazy {
        ApplicationDatabase.get(this)
    }

    override fun onCreate() {
        FirebaseApp.initializeApp(this) // If called from another thread or process
        AppStartup.onApplicationCreated(this)
        super.onCreate()
        AppStartup.startApplicationInitialisation()
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        InternalLogger.logE(
            TAG,
            "**********************\nCRASH\n**********************\n$e\n",
            e
        )
        PrefsManager(this.applicationContext).saveCrashInfo()
        PinLog.CrashReporter().sendCrashReportWithEmail(t, e, arrayOf(Constants.SUPPORT_EMAIL), null, null)
    }

    companion object {
        private const val TAG = "App"
    }

}