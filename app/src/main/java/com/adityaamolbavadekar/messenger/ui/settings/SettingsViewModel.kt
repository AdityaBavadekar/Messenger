package com.adityaamolbavadekar.messenger.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adityaamolbavadekar.messenger.utils.extensions.runOnMainThread
import com.adityaamolbavadekar.pinlog.PinLog
import kotlinx.coroutines.launch
import java.io.File

class SettingsViewModel : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading

    fun getLogsInFile(callback: (File?) -> Unit){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val file = PinLog.getAllPinLogsInFile()
            _isLoading.postValue(false)
            runOnMainThread { callback(file) }
        }
    }

}
