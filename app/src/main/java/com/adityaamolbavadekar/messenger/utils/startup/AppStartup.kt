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

package com.adityaamolbavadekar.messenger.utils.startup

import android.content.ContentResolver
import android.os.Bundle
import com.adityaamolbavadekar.messenger.App
import com.adityaamolbavadekar.messenger.BuildConfig
import com.adityaamolbavadekar.messenger.account.MessengerAccountAuthenticator
import com.adityaamolbavadekar.messenger.managers.FcmTokenManager
import com.adityaamolbavadekar.messenger.managers.InternetManager
import com.adityaamolbavadekar.messenger.model.UpdateInfo
import com.adityaamolbavadekar.messenger.notifications.NotificationHelper
import com.adityaamolbavadekar.messenger.ui.UpdatePresenter
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.UpdateUtil
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.utils.shake.AppShakeDetector
import com.adityaamolbavadekar.messenger.utils.theming.ThemeInfo.Companion.getPreferredThemeInfo
import com.adityaamolbavadekar.messenger.utils.tracer.MethodTracer

object AppStartup {

    private val appForegroundListener: AppForegroundObserver.Listener =
        object : AppForegroundObserver.Listener {
            val listenerTag = "AppForegroundObserver"
            override fun onForeground() {
                super.onForeground()
                InternalLogger.logW(listenerTag, "onForeground : VISIBLE")
            }

            override fun onBackground() {
                super.onBackground()
                InternalLogger.logW(
                    listenerTag,
                    "onBackground : NOT_VISIBLE"
                )
            }
        }

    private const val TAG = "AppStartup"
    private lateinit var application: App
    private var methodTracer: MethodTracer

    init {
        InternalLogger.postOnApplicationCreated()
        methodTracer = MethodTracer(TAG, "init").start()
    }

    fun onApplicationCreated(app: App) {
        application = app
        methodTracer.putTraceData("onApplicationCreated", "called")
        application.getPreferredThemeInfo().apply()
    }

    fun startApplicationInitialisation() {
        componentsInitialisation()
    }

    private fun componentsInitialisation() {
        InternetManager.onCreate(application)
        AppForegroundObserver.addListener(appForegroundListener)
        AppForegroundObserver.begin()
        FcmTokenManager().instance()
        UpdateUtil.checkForUpdates(application.applicationContext, UpdateInfoResponseCallback)
        AppShakeDetector.register(application).start()
        NotificationHelper(application.applicationContext)
        MessengerAccountAuthenticator.account(application.applicationContext)?.let {
            try {
                ContentResolver.requestSync(it, Constants.DATA_SYNC_AUTHORITY, Bundle())
            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Unable to request sync", e)
            }
        }

        Constants.AppDirectories.createDefaultDirs(application.applicationContext)

        methodTracer.putTraceData("onApplicationCreated", "finished").end()
    }

    object UpdateInfoResponseCallback :
        OnResponseCallback<UpdateInfo, Exception> {
        override fun onSuccess(t: UpdateInfo) {
            if (BuildConfig.VERSION_CODE < t.versionCode) {
                InternalLogger.logW(
                    TAG,
                    "A newer version (CURRENT[${BuildConfig.VERSION_CODE}],LATEST=${t.versionCode}) is available." +
                            " NAME[CURRENT=${BuildConfig.VERSION_NAME},LATEST=[${t.versionName}]"
                )
                UpdatePresenter.start(application.applicationContext)
            }
        }

        override fun onFailure(e: Exception) {
            InternalLogger.logE(TAG, "Unable to check for updates", e)
        }
    }

}
