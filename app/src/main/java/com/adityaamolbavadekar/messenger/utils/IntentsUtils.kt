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
