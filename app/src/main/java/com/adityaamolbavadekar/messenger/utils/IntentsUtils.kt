package com.adityaamolbavadekar.messenger.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class IntentsUtils {
    companion object {
        fun shareThroughEmail(
            context: Context,
            subject: String?,
            textContent: String,
            attachments: ArrayList<Uri>,
            toEmail: String
        ): Intent {
            return Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, attachments)
                putExtra(Intent.EXTRA_EMAIL, arrayOf(toEmail))
                putExtra(Intent.EXTRA_TEXT, textContent)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                val i = Intent(Intent.ACTION_SENDTO)
                i.data = Uri.parse("mailto:")
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                selector = i
                attachments.forEach { grantPermission(this, context, it) }
            }
        }

        private fun grantPermission(i: Intent, context: Context, attachmentUri: Uri) {
            try {
                for (resolveInfo in context.packageManager.queryIntentActivities(
                    i,
                    PackageManager.MATCH_DEFAULT_ONLY
                )) {
                    context.grantUriPermission(
                        resolveInfo.resolvePackageName,
                        attachmentUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    InternalLogger.logD(
                        IntentsUtils::class.java.simpleName,
                        "Granted permission to ($attachmentUri): ${resolveInfo.resolvePackageName}"
                    )
                }
            } catch (e: Exception) {
            }
        }

    }
}
