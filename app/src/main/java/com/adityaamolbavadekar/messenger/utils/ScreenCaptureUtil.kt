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
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@Suppress("DEPRECATION")
class ScreenCaptureUtil {
    companion object {

        private const val QUALITY_HIGH = 100
        private const val QUALITY_MEDIUM = 50
        private const val QUALITY_LOW = 25

        fun capture(v: View): Bitmap? {
            val stream = ByteArrayOutputStream()
            val bitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            v.background?.draw(canvas)
            v.draw(canvas)
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_HIGH, stream as OutputStream?)
            val byteArray = stream.toByteArray()
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }

        fun to(view: View, file: File): Boolean {
            return capture(view)?.saveTo(file) ?: false
        }

        private fun Bitmap.saveTo(file: File): Boolean {
            return try {
                if (!file.exists()) file.createNewFile()
                val stream = FileOutputStream(file)
                compress(Bitmap.CompressFormat.PNG, QUALITY_HIGH, stream)
                stream.close()
                stream.flush()
                true
            } catch (e: Exception) {
                false
            }
        }

    }
}