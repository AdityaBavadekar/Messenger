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

package com.adityaamolbavadekar.messenger.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.core.graphics.drawable.toDrawable
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread

class WallpaperUtils private constructor() {

    companion object {

        fun save(context: Context, uri: Uri) = runOnIOThread {
            try {
                val cursor = context.contentResolver.openInputStream(uri)
                val bytes = cursor?.readBytes()
                cursor?.close()
                bytes?.let { array ->
                    PrefsManager(context).getOrCreateWallpaperFile()?.writeBytes(array)
                }
            } catch (e: Exception) {
            }
        }

        fun get(context: Context): BitmapDrawable? {
            PrefsManager(context).getWallpaperUrl()?.let { file ->
                val bytes = file.readBytes()
                return BitmapFactory.decodeStream(file.inputStream()).toDrawable(context.resources)
            }
            return null
        }

        fun delete(context: Context) {
            PrefsManager(context).deleteWallpaper {}
        }

        fun isAdded(context: Context): Boolean {
            return PrefsManager(context).getWallpaperUrl() != null
        }

    }

}