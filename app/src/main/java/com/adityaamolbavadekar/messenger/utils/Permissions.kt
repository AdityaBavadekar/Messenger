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

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import com.adityaamolbavadekar.messenger.managers.PrefsManager.Companion.prefs
import com.adityaamolbavadekar.pinlog.PinLog

sealed class Permissions(val permissionName: String) {
    object ReadStorage : Permissions(permission.READ_EXTERNAL_STORAGE)
    object WriteStorage : Permissions(permission.WRITE_EXTERNAL_STORAGE)
    object ReadContacts : Permissions(permission.READ_CONTACTS)
    object Camera : Permissions(permission.CAMERA)

    @RequiresApi(Build.VERSION_CODES.R)
    object AllFilesAccess : Permissions(permission.MANAGE_EXTERNAL_STORAGE) {
        override fun isGranted(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager()
            } else {
                false
            }
        }
    }

    object StorageGroup : Permissions(permission.WRITE_EXTERNAL_STORAGE) {
        override fun isGranted(context: Context): Boolean {
            return (ReadStorage.isGranted(context) && WriteStorage.isGranted(context))
        }

        fun requestStoragePermissions(
            context: Context,
            launcher: ActivityResultLauncher<Array<out String>>,
            startActivityForResultLauncher: ActivityResultLauncher<Intent>
        ) {
            val rationaleWasShown =
                context.prefs.getBoolean("STORAGE_PERMISSION_REQUESTED_KEY", false)
            val isGranted = isGranted(context)
            if (isGranted) {
                return
            } else if (rationaleWasShown && !isGranted) {
                val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                i.putExtra("package:", context.packageName)
                i.data = Uri.parse("package:" + context.packageName)
                startActivityForResultLauncher.launch(i)
                Toast.makeText(context, "Please grant the files permission.", Toast.LENGTH_SHORT)
                    .show()
            } else if (!rationaleWasShown && !isGranted) {
                context.prefs.edit {
                    putBoolean("STORAGE_PERMISSION_REQUESTED_KEY", true)
                }
                launcher.launch(arrayOf(ReadStorage.permissionName, WriteStorage.permissionName))
            }
        }

        fun requestPlatformWise(
            context: Context, launcher: ActivityResultLauncher<String>,
            launchers: ActivityResultLauncher<Array<out String>>,
            startActivityForResultLauncher: ActivityResultLauncher<Intent>
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                forHigherAndroid(context, launcher)
            } else {
                forLowerAndroid(context, launchers, startActivityForResultLauncher)
            }
        }

        @RequiresApi(Build.VERSION_CODES.R)
        private fun forHigherAndroid(context: Context, launcher: ActivityResultLauncher<String>) {
            val isGranted = AllFilesAccess.isGranted(context)
            PinLog.logI(TAG, "Android 11 : $isGranted")
            if (isGranted) return
            else AllFilesAccess.request(context, launcher)
        }

        private fun forLowerAndroid(
            context: Context,
            launcher: ActivityResultLauncher<Array<out String>>,
            startActivityForResultLauncher: ActivityResultLauncher<Intent>
        ) {
            val rationaleWasShown =
                context.prefs.getBoolean("STORAGE_PERMISSION_REQUESTED_KEY", false)
            val isGranted = isGranted(context)
            PinLog.logI(TAG, "Bellow Android 11 : $isGranted")
            if (isGranted) {
                return
            } else if (rationaleWasShown && !isGranted) {
                val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                i.putExtra("package:", context.packageName)
                i.data = Uri.parse("package:" + context.packageName)
                startActivityForResultLauncher.launch(i)
                Toast.makeText(context, "Please grant the files permission.", Toast.LENGTH_SHORT)
                    .show()
            } else if (!rationaleWasShown && !isGranted) {
                context.prefs.edit {
                    putBoolean("STORAGE_PERMISSION_REQUESTED_KEY", true)
                }
                launcher.launch(arrayOf(ReadStorage.permissionName, WriteStorage.permissionName))
            }
        }
    }

    open fun isGranted(context: Context): Boolean {
        return (context.packageManager.checkPermission(
            permissionName,
            context.packageName
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun doIfGranted(context: Context, block: () -> Unit) {
        if (isGranted(context)) block()
    }

    fun doIfDenied(context: Context, block: () -> Unit) {
        if (!isGranted(context)) block()
    }

    open fun request(context: Context, launcher: ActivityResultLauncher<String>): Boolean {
        return if (!isGranted(context)) {
            launcher.launch(permissionName)
            true
        } else false
    }

    companion object {
        private const val TAG = "Permissions"
        fun refresh(c: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val isGranted = AllFilesAccess.isGranted(c)
                PinLog.logI(TAG, "Android 11+ : $isGranted")
                isGranted
            } else {
                val isGranted = StorageGroup.isGranted(c)
                PinLog.logI(TAG, "Bellow Android 11 : $isGranted")
                isGranted
            }
        }
    }

}