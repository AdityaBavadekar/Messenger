/*
 *
 *    Copyright 2022 Aditya Bavadekar
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
 *
 */

package com.adityaamolbavadekar.messenger.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.FrameLayout
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.base.LifecycleLoggerActivity
import com.adityaamolbavadekar.pinlog.PinLog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ShakePresenter : LifecycleLoggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO CAPTURE SCREENSHOT

        val root = FrameLayout(this).apply {
            this.background = ColorDrawable(Color.TRANSPARENT)
            this.setOnClickListener {
                if (!isFinishing) finish()
            }
        }

        setContentView(root)

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.shake_to_send_feedback))
            .setMessage(getString(R.string.shake_presenter_dialog_message))
            .setIcon(R.drawable.ic_feedback)
            .setNeutralButton(R.string.dismiss) { d, _ ->
                d.cancel()
                if (!isFinishing) finish()
            }
            .setOnDismissListener {
                it.dismiss()
                if (!isFinishing) finish()
            }
            .setPositiveButton(getString(R.string.send_feedback)) { d, _ ->
                d.dismiss()
                if (!isFinishing) finish()
                PinLog.CrashReporter()
                    .sendCrashReportWithEmail(Thread.currentThread(), null, arrayOf(), null, null)
            }
            .create()
            .show()
    }


    companion object {
        fun start(context: Context) {
            Intent(context, ShakePresenter::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
    }

}