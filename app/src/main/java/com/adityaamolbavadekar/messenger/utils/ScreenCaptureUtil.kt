package com.adityaamolbavadekar.messenger.utils

import android.graphics.Bitmap
import android.view.View
import java.io.File

@Suppress("DEPRECATION")
class ScreenCaptureUtil {
    companion object {
        fun cap(view: View): Bitmap {
            val v = view.rootView
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