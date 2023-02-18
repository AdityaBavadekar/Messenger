package com.adityaamolbavadekar.messenger.ui.shake

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import com.adityaamolbavadekar.pinlog.PinLog
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import java.io.File

class ShakePresenterViewModel : ViewModel() {

    private val _filePath: MutableLiveData<String?> = MutableLiveData(null)
    val filePath: LiveData<String?> = _filePath
    private val uid = AuthManager().uid

    init {
        viewModelScope.launch {
            val timestamp = simpleDateFormat(
                System.currentTimeMillis(),
                Constants.TimestampFormats.UNDERSCORED_TIMESTAMP_FORMAT_FULL
            )
            val jsonObject = PinLog.CrashReporter().createReport(Thread.currentThread(), null)
            jsonObject.put("REPORT_TYPE", "SHAKE_INTERFACE_FEEDBACK")
            jsonObject.put("UID", uid)
            val tempFile = File.createTempFile("SHAKE_INTERFACE_FILE_${timestamp}", "txt")
            tempFile.writeText(jsonObject.toString(5))
            Firebase.storage.reference
                .child("feedbackFiles/$uid/")
                .child(tempFile.name)
                .putFile(tempFile.toUri())
                .addOnSuccessListener { _filePath.postValue("$uid/${tempFile.name}") }
            tempFile.delete()
        }
    }

}
