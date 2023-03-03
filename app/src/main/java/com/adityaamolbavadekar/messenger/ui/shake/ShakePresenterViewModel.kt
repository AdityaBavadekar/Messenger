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

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.pinlog.PinLog
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class ShakePresenterViewModel : ViewModel() {

    private val _filePath: MutableLiveData<String?> = MutableLiveData(null)
    val filePath: LiveData<String?> = _filePath
    private val uid = AuthManager().uid

    init {
        runOnIOThread {
            InternalLogger.logI(TAG, "Creating ShakeLogs...")
            val timestamp = simpleDateFormat(
                System.currentTimeMillis(),
                Constants.TimestampFormats.UNDERSCORED_TIMESTAMP_FORMAT_FULL
            )
            val jsonObject = PinLog.CrashReporter().createReport(Thread.currentThread(), null)
            InternalLogger.logD(TAG, "Adding custom items to ShakeLogs...")
            jsonObject.put("REPORT_TYPE", "SHAKE_INTERFACE_FEEDBACK")
            jsonObject.put("UID", uid)
            val tempFile = File.createTempFile("SHAKE_INTERFACE_FILE_${timestamp}", ".txt")
            InternalLogger.logD(TAG, "Created tempFile ${tempFile.name}")
            tempFile.writeText(jsonObject.toString(5))
            InternalLogger.logD(TAG, "Uploading...")
            Firebase.storage.reference
                .child("feedbackFiles/$uid/")
                .child(tempFile.name)
                .putFile(tempFile.toUri())
                .addOnSuccessListener {
                    InternalLogger.logI(TAG, "Successfully uploaded ShakeLogs")
                    _filePath.postValue("$uid/${tempFile.name}")
                }
                .addOnFailureListener {
                    InternalLogger.logE(TAG, "Unable uploaded ShakeLogs", it)
                }
            tempFile.delete()
        }
    }

    companion object {
        private val TAG = ShakePresenterViewModel::class.java.simpleName
    }

}
