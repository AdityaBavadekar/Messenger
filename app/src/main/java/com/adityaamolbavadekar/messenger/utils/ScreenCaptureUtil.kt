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

import android.graphics.Bitmap
import android.view.View
import java.io.File

@Suppress("DEPRECATION")
class ScreenCaptureUtil {
    companion object {
        fun cap(v: View): Bitmap {
            v.isDrawingCacheEnabled = true
            v.buildDrawingCache(true)
            val b = Bitmap.createBitmap(v.drawingCache)
            v.isDrawingCacheEnabled = false
            return b!!
        }

        fun to(view: View, file: File): Boolean {
            return cap(view).to(file)
        }

        fun Bitmap.to(file: File): Boolean {
            return try {
                compress(Bitmap.CompressFormat.JPEG, 100, file.outputStream())
            } catch (e: Exception) {
                false
            }
        }

    }
}