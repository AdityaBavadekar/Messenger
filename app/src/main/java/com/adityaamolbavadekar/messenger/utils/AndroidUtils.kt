package com.adityaamolbavadekar.messenger.utils

import android.animation.ValueAnimator
import android.content.Context
import android.net.Uri
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.net.toFile
import com.adityaamolbavadekar.messenger.model.Id
import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import java.io.File
import kotlin.math.ceil

class AndroidUtils private constructor() {

    companion object {

        const val FILE_TYPE_IMAGE: Int = 10
        const val FILE_TYPE_VIDEO: Int = 11
        const val FILE_TYPE_OTHER: Int = 12

        fun toDP(value: Int, context: Context): Int {
            if (value == 0) return 0;
            val density = context.resources.displayMetrics.density
            return (ceil(density * value).toInt())
        }

        fun percent(value:Int,total:Int): Int {
            if(value == 0 || total == 0) return 0
            return (value*100)/total
        }

        fun setVisible(v: View) {
            v.visibility = View.VISIBLE
        }

        fun setGone(v: View) {
            v.visibility = View.GONE
        }

        fun getApplicationMediaDirectory(context: Context): File {
            return context.externalMediaDirs.first()
        }

        fun saveSentDocumentFile(uri: Uri,context: Context): File {
            val fileName = "DOC_"+createFileName(FILE_TYPE_OTHER) + "."
            val f = File(Constants.AppDirectories.getSentDocsDir(context).absolutePath + "/$fileName")
            f.createNewFile()
            File(uri.path).copyTo(f,true)
            return f
        }

        fun createFileName(type: Int): String {
            val timestampString = simpleDateFormat(
                System.currentTimeMillis(),
                Constants.TimestampFormats.NATURAL_FILE_NAME_FORMAT
            )
            var fileName = timestampString + Id.get(5)
            val prefix = when (type) {
                FILE_TYPE_IMAGE -> "IMG_"
                FILE_TYPE_VIDEO -> "VID_"
                else -> ""
            }
            fileName = prefix + fileName + "M"
            return fileName
        }

        fun addFlagSecure(window: Window) {
            try {
                window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
            } catch (_: Exception) {
            }
        }

        fun removeFlagSecure(window: Window) {
            try {
                window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            } catch (_: Exception) {
            }
        }

        fun setNavigationBarColor(window: Window, color: Int) {
            val animator = ValueAnimator.ofArgb(window.navigationBarColor, color)
            animator.addUpdateListener {
                val animatedColorValue = it.animatedValue as Int
                try {
                    window.navigationBarColor = animatedColorValue
                } catch (_: Exception) {
                }
            }
            animator.duration = 200
            animator.start()
        }

        fun setStatusBarColor(window: Window, color: Int) {
            val animator = ValueAnimator.ofArgb(window.statusBarColor, color)
            animator.addUpdateListener {
                val animatedColorValue = it.animatedValue as Int
                try {
                    window.statusBarColor = animatedColorValue
                } catch (_: Exception) {
                }
            }
            animator.duration = 200
            animator.start()
        }


    }

}