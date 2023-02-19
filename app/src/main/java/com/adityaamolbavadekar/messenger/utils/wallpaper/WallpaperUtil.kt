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

package com.adityaamolbavadekar.messenger.utils.wallpaper

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import java.io.File

class WallpaperUtil {

    companion object {
        fun save(context: Context, uri: Uri) {
            val dir = context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE)
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(DIRECTORY_NAME, FILE_NAME)
            if (!file.exists()) {
                file.createNewFile()
            }
            context.contentResolver.openInputStream(uri)?.let {
                file.writeBytes(it.readBytes())
                it.close()
            }
        }

        private const val FILE_NAME = "Wallpaper"
        private const val DIRECTORY_NAME = "Wallpapers"

    }

}