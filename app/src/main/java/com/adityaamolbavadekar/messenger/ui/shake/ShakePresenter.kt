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

package com.adityaamolbavadekar.messenger.ui.shake

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.core.view.doOnLayout
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.ScreenCaptureUtil
import com.adityaamolbavadekar.messenger.utils.base.LifecycleLoggerActivity
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

class ShakePresenter : LifecycleLoggerActivity() {

    private val viewModel: ShakePresenterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = FrameLayout(this).apply {
            this.background = ColorDrawable(Color.TRANSPARENT)
            this.setOnClickListener { finishIfNotAlready() }
            doOnLayout {
                try {
                    val file = File.createTempFile("screen-capture-image", ".jpeg")
                    val capture = ScreenCaptureUtil.to(window.decorView.rootView, file)
                    if (capture) InternalLogger.logD(
                        TAG,
                        "Successfully saved screenCapture to ${file.absolutePath}"
                    )
                } catch (e: Exception) {
                }
            }
        }

        setContentView(root)

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.shake_to_send_feedback))
            .setMessage(getString(R.string.shake_presenter_dialog_message))
            .setIcon(R.drawable.ic_feedback)
            .setNeutralButton(R.string.dismiss) { d, _ ->
                d.cancel()
                finishIfNotAlready()
            }
            .setOnDismissListener {
                it.dismiss()
                finishIfNotAlready()
            }
            .setPositiveButton(getString(R.string.send_feedback)) { d, _ ->
                d.dismiss()
                finishIfNotAlready()
                val path = viewModel.filePath.value
                Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_EMAIL, arrayListOf(Constants.SUPPORT_EMAIL))
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.messenger_feedback))
                    putExtra(Intent.EXTRA_TEXT, "ID=${path ?: "None"}\nFeedback : \n")
                    type = "message/rfc822"
                    startActivity(Intent.createChooser(this, getString(R.string.send_feedback)))
                }
            }
            .create()
            .show()
    }

    private fun finishIfNotAlready() {
        if (!isFinishing) finish()
    }

    companion object {
        private val TAG = ShakePresenter::class.java.simpleName

        fun start(context: Context) {
            Intent(context, ShakePresenter::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
    }

}