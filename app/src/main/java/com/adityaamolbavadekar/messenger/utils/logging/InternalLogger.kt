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

package com.adityaamolbavadekar.messenger.utils.logging

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.MimeTypeMap
import com.adityaamolbavadekar.messenger.BuildConfig
import com.adityaamolbavadekar.messenger.utils.AuthError
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.extensions.runOnMainThread
import com.adityaamolbavadekar.pinlog.PinLog
import com.google.firebase.auth.FirebaseAuthException

object InternalLogger {

    private val internalLoggerTag: String
        get() = javaClass.simpleName

    private var isInternalLoggerInitialized: Boolean = false
    private var initializationTime: Long = 0

    /*LOGGING METHODS START*/
    fun logW(TAG: String, m: String) {
        internalLog(TAG, m, PinLog.LogLevel.WARN)
    }

    fun logD(TAG: String, m: String) {
        internalLog(TAG, m, PinLog.LogLevel.DEBUG)
    }

    fun logW(TAG: String, m: String, e: Throwable) {
        internalLog(TAG, m, PinLog.LogLevel.WARN, e)
    }

    fun logI(TAG: String, m: String) {
        internalLog(TAG, m, PinLog.LogLevel.INFO)
    }

    fun logI(TAG: String, m: String, e: Throwable) {
        internalLog(TAG, m, PinLog.LogLevel.INFO, e)
    }

    fun logE(TAG: String, m: String) {
        internalLog(TAG, m, PinLog.LogLevel.ERROR)
    }

    fun logE(TAG: String, m: String, e: Throwable?) {
        if (e != null && e is FirebaseAuthException) {
            internalLog(
                TAG,
                "$m \n\n" +
                        " FirebaseAuthException : ErrorCode = ${e.errorCode}\n" +
                        " FirebaseAuthError : ErrorInfo = ${AuthError.fromException(e).name}\n",
                PinLog.LogLevel.ERROR,
                e
            )
        } else internalLog(TAG, m, PinLog.LogLevel.ERROR, e)
    }

    fun logE(TAG: String, e: Throwable) {
        internalLog(TAG, "", PinLog.LogLevel.ERROR, e)
    }

    private fun internalLog(
        TAG: String,
        m: String,
        logType: PinLog.LogLevel,
        e: Throwable? = null
    ) {
        internalAndroidLog(TAG, m, logType, e)
    }

    private fun internalAndroidLog(
        TAG: String,
        m: String,
        logType: PinLog.LogLevel,
        e: Throwable? = null,
        persist: Boolean = true
    ) {
        val text = "$TAG : $m"
        val logger = getAppLevelLogger()

        if (logType == PinLog.LogLevel.ERROR) logger.e(text, e)
        if (persist) persistLog(TAG, m, logType, e)
        if (!isDebugBuild) return

        return when (logType) {
            PinLog.LogLevel.INFO -> logger.i(text, e)
            PinLog.LogLevel.WARN -> logger.w(text, e)
            PinLog.LogLevel.DEBUG -> logger.d(text, e)
            else -> logger.v(text,e)
        }
    }

    private fun persistLog(
        TAG: String,
        m: String,
        logType: PinLog.LogLevel,
        e: Throwable? = null
    ) {
        if (!isInternalLoggerInitialized) return
        return PinLog.log(TAG, m, e, logType)
    }

    fun w(TAG: String, m: String) {
        internalAndroidLog(TAG, m, PinLog.LogLevel.WARN)
    }

    fun w(TAG: String, m: String, e: Throwable) {
        internalAndroidLog(TAG, m, PinLog.LogLevel.WARN, e)
    }

    fun d(TAG: String, m: String) {
        internalAndroidLog(TAG, m, PinLog.LogLevel.DEBUG)
    }

    fun d(TAG: String, m: String, e: Throwable) {
        internalAndroidLog(TAG, m, PinLog.LogLevel.DEBUG, e)
    }

    fun i(TAG: String, m: String) {
        internalAndroidLog(TAG, m, PinLog.LogLevel.INFO)
    }

    fun i(TAG: String, m: String, e: Throwable) {
        internalAndroidLog(TAG, m, PinLog.LogLevel.INFO, e)
    }

    fun e(TAG: String, m: String) {
        internalAndroidLog(TAG, m, PinLog.LogLevel.ERROR)
    }

    fun e(TAG: String, m: String, e: Throwable?) {
        internalAndroidLog(TAG, m, PinLog.LogLevel.ERROR, e)
    }

    fun e(TAG: String, e: Throwable) {
        internalAndroidLog(TAG, "", PinLog.LogLevel.ERROR, e)
    }

    /**
     * - Only log if [isDebugBuild] = `true`
     * - Log Level : Info
     * */
    fun debugInfo(TAG: String, m: String, e: Throwable? = null) {
        if (isDebugBuild) {
            return internalAndroidLog(TAG, m, PinLog.LogLevel.INFO, e, true)
        }
    }

    fun logPrivateInfo(TAG: String, m: String,e: Throwable?=null){
        debugInfo(TAG,m,e)
    }
    /*LOGGING METHODS END*/

    fun postOnApplicationCreated() {
        logD(
            internalLoggerTag,
            "Application Initialized in ${System.currentTimeMillis() - initializationTime}ms \n"
        )
    }

    fun Context.initializeInternalLogger() {
        if (isInternalLoggerInitialized) {
            return w(internalLoggerTag, "Already initialized.")
        }
        isInternalLoggerInitialized = true
        initializationTime = System.currentTimeMillis()
        d(internalLoggerTag, "Initializing...")
        initialiseProduction(this.applicationContext as Application)
    }

    private fun initialiseProduction(application: Application) {
        PinLog.initialise(
            application,
            setDevLoggingEnabled = false,
            setDoStoreLogs = true,
            BuildConfig::class.java
        )
        d(internalLoggerTag, "Initialized ProductionLogging.")
    }

    fun getShareLogsIntent(c: Context, onResultListener: (Intent) -> Unit) = runOnIOThread {
        val i = getInternalShareLogsIntent(c)

        val f = PinLog.getAllPinLogsInFile()
        if (f != null) {
            val uri = PinLog.getUriForFile(f)
            if (uri != null) {
                val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(f.extension)
                i.putExtra(Intent.EXTRA_STREAM, uri)
                i.type = mimeType
                try {
                    c.packageManager.queryIntentActivities(i, 0).forEach { resolveInfo ->
                        c.grantUriPermission(
                            resolveInfo.resolvePackageName,
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    }
                } catch (e: Exception) {
                }
                runOnMainThread { onResultListener(i) }
            } else {
                runOnMainThread { onResultListener(i) }
            }
        } else {
            runOnMainThread { onResultListener(i) }
        }
    }

    private fun getInternalShareLogsIntent(c: Context): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            putExtra(
                Intent.EXTRA_SUBJECT,
                "send_feedback_extra_email_subject"
            )
            putExtra(
                Intent.EXTRA_TEXT,
                "send_feedback_extra_email_text"
            )
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        }
    }

    fun getAppLevelLogger() = MessengerLogger.getLogger()


    fun canLog(level: Int): Boolean {
        return if (isStrictErrorOnlyLogging) level == Log.ERROR else true
    }

    val isDebugBuild: Boolean = BuildConfig.DEBUG
    private val isStrictErrorOnlyLogging: Boolean = !isDebugBuild

}