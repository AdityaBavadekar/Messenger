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

package com.adityaamolbavadekar.messenger.installation

import android.content.Context
import android.os.Build
import com.adityaamolbavadekar.messenger.BuildConfig
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import java.io.File
import java.util.*

object InstallationInfo {

    private val TAG = InstallationInfo::class.java.simpleName
    private const val INSTALLATION = "APPLICATION-INSTALLATION-INFO"
    private const val ERROR_STRING = "Couldn't retrieve the Installation Identifier"

    @JvmStatic
    @Synchronized
    fun getOrCreateId(context: Context): String? {
        val installation = File(context.filesDir, INSTALLATION)
        return try {
            if (!installation.exists()) {
                installation.createNewFile()
                val id = UUID.randomUUID().toString() + "${System.currentTimeMillis()}"
                installation.writeText(id)
                InternalLogger.d(
                    TAG, "Installation Identifier generated and saved. \n" +
                            "[${id.take(10)}...] --> " +
                            "[${installation.absolutePath}]"
                )
                InternalLogger.getAppLevelLogger().d(
                    "Installation Identifier generated and saved. \n" +
                            "[${id.take(10)}...] [DEBUG=${InternalLogger.isDebugBuild}] "
                )
                InternalLogger.logD(
                    TAG,
                    "Installation : \n" +
                            "ID=$id \n" +
                            "isDebugBuild=${InternalLogger.isDebugBuild} \n" +
                            "VERSION_NAME=${BuildConfig.VERSION_NAME} \n" +
                            "VERSION_CODE=${BuildConfig.VERSION_CODE} \n" +
                            "APP_ID=${BuildConfig.APPLICATION_ID} \n" +
                            "[DEVICE=${Build.DEVICE} \n" +
                            "MODEL=${Build.MODEL} \n" +
                            "SDK_INT=${Build.VERSION.SDK_INT}"
                )
            }
            installation.readText()
        } catch (e: RuntimeException) {
            InternalLogger.e(TAG, "$ERROR_STRING : $e")
            null
        }
    }

}
