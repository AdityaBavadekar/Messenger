@file:Suppress("MemberVisibilityCanBePrivate")

package com.adityaamolbavadekar.messenger.utils

import android.animation.ValueAnimator
import android.content.ContentResolver
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.database.getLongOrNull
import com.adityaamolbavadekar.messenger.model.FileMetadata
import com.adityaamolbavadekar.messenger.model.Id
import com.adityaamolbavadekar.messenger.model.SizeUnit
import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import java.io.File
import java.io.FileOutputStream
import kotlin.math.ceil

class AndroidUtils private constructor() {

    companion object {
        private val TAG = AndroidUtils::class.java.simpleName
        const val FILE_TYPE_IMAGE: Int = 10
        const val FILE_TYPE_VIDEO: Int = 11
        const val FILE_TYPE_OTHER: Int = 12

        fun toDP(value: Int, context: Context): Int {
            if (value == 0) return 0;
            val density = context.resources.displayMetrics.density
            return (ceil(density * value).toInt())
        }

        fun percent(value: Int, total: Int): Int {
            if (value == 0 || total == 0) return 0
            return (value * 100) / total
        }

        fun setVisible(v: View) {
            v.visibility = View.VISIBLE
        }

        fun setGone(v: View) {
            v.visibility = View.GONE
        }

        private fun isLandscape(context:Context): Boolean {
            return context.resources.configuration.orientation === Configuration.ORIENTATION_LANDSCAPE
        }

        fun getFileMetadata(uri: Uri, contentResolver: ContentResolver): FileMetadata {
            val fileMimeType = contentResolver.getType(uri)
            val cursor = contentResolver.query(uri, null, null, null, null)!!
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            cursor.moveToFirst()
            val fileName = cursor.getString(nameIndex)
            val fileSize = cursor.getLongOrNull(sizeIndex) ?: 0
            return FileMetadata(
                name = fileName,
                nameWithoutExtension = getNameWithoutExtension(fileName),
                extension = getExtension(fileName),
                mimeType = fileMimeType,
                size = fileSize
            )
        }

        fun getNameWithoutExtension(fileName: String): String {
            return fileName.substringBeforeLast(".")
        }

        fun getExtension(fileName: String): String {
            return fileName.substringAfterLast(".").toString()
        }

        fun getApplicationMediaDirectory(context: Context): File {
            return context.externalMediaDirs.first()
        }

        fun isContentUri(uri: Uri): Boolean {
            return uri.toString().startsWith("content://")
        }

        fun isFileUri(uri: Uri): Boolean {
            return uri.toString().startsWith("file://")
        }

        fun saveSentDocumentFile(extension: String, uri: Uri, context: Context): File {
            var fileName = "DOC_" + createFileName(FILE_TYPE_OTHER) + "."
            if (extension.trim().isNotEmpty()) fileName += extension
            val f =
                File(Constants.AppDirectories.getSentDocsDir(context).absolutePath + "/$fileName")
            f.createNewFile()
            f.nameWithoutExtension
            copyUriFileContents(context, uri, f)
            if (f.length() > 0) {
                InternalLogger.debugInfo(
                    TAG,
                    "Created file [${fileName}] with size " + SizeUnit.format(f.length())
                )
            }
            return f
        }

        fun saveDownloadedDocumentFile(extension: String, uri: Uri, context: Context): File {
            var fileName = "DOC_" + createFileName(FILE_TYPE_OTHER) + "."
            if (extension.trim().isNotEmpty()) fileName += extension
            val f =
                File(Constants.AppDirectories.getDocsDir(context).absolutePath + "/$fileName")
            f.createNewFile()
            copyUriFileContents(context, uri, f)
            if (f.length() > 0) {
                InternalLogger.debugInfo(
                    TAG,
                    "Created file [${fileName}] with size " + SizeUnit.format(f.length())
                )
            }
            return f
        }

        fun copyUriFileContents(context: Context, uri: Uri, file: File): Boolean {
            try {
                val outputStream = FileOutputStream(file)
                val io = context.contentResolver!!.openInputStream(uri)!!
                io.copyTo(outputStream)
                io.close()
                outputStream.flush()
                outputStream.close()
                return true
            } catch (_: Exception) {
                return false
            }
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