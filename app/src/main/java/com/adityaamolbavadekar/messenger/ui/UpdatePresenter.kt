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
import android.widget.Toast
import com.adityaamolbavadekar.messenger.BuildConfig
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.utils.UpdateUtil
import com.adityaamolbavadekar.messenger.utils.base.LifecycleLoggerActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UpdatePresenter : LifecycleLoggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = FrameLayout(this).apply {
            this.background = ColorDrawable(Color.TRANSPARENT)
            this.setOnClickListener {
                if (!isFinishing) finish()
            }
        }

        setContentView(root)
        val prefsManager = PrefsManager(this)
        val currentVersionCode = BuildConfig.VERSION_CODE
        val updateInfo = prefsManager.getUpdateInfo()
        if (updateInfo == null || updateInfo.versionCode <= currentVersionCode) return finish()

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.update_available))
            .setMessage(
                getString(
                    R.string.update_dialog_message,
                    BuildConfig.VERSION_NAME,
                    updateInfo.versionName
                )
            )
            .setIcon(R.drawable.ic_chat_bubble)
            .setNeutralButton(R.string.skip) { d, _ ->
                d.cancel()
                if (!isFinishing) finish()
            }
            .setOnDismissListener {
                it.dismiss()
                if (!isFinishing) finish()
            }
            .setPositiveButton(getString(R.string.update)) { d, _ ->
                d.dismiss()
                Toast.makeText(this, getString(R.string.updating_in_background), Toast.LENGTH_SHORT)
                    .show()
                UpdateUtil.startUpdate(this, updateInfo)
                if (!isFinishing) finish()
            }
            .create()
            .show()
    }


    companion object {
        fun start(context: Context) {
            Intent(context, UpdatePresenter::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }
    }

}