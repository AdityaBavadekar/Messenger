package com.adityaamolbavadekar.messenger.utils

import android.content.Context
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class DownlodUtil {

    companion object {
        private const val TAG = "DownlodUtil"
        fun downloadFromStorage(
            context: Context,
            fileName: String,
            downloadUrl: String,
            onSuccess: (File) -> Unit
        ) {
            val destinationFile =
                File(Constants.AppDirectories.getDocsDir(context).absolutePath + "/" + fileName)
            if (!destinationFile.exists()) destinationFile.createNewFile()
            else if(destinationFile.length().toInt() > 0) return onSuccess(destinationFile)
            Firebase.storage
                .reference
                .getFile(destinationFile)
                .addOnSuccessListener {
                    InternalLogger.logI(
                        TAG,
                        "File downloaded to [${destinationFile.absolutePath}] given filename"
                    )
                    onSuccess(destinationFile)
                }
                .addOnFailureListener {
                    InternalLogger.logE(
                        TAG,
                        "Unable to download File to [${destinationFile.absolutePath}] given filename",
                        it
                    )
                }

        }
    }

}
