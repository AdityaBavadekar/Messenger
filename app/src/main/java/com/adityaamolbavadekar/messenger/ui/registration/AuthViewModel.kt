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

package com.adityaamolbavadekar.messenger.ui.registration

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.managers.CloudStorageManager
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.model.User
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.PhoneNumberUtils
import com.adityaamolbavadekar.messenger.utils.extensions.getNewActivity
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.extensions.userFrom
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.storage.StorageMetadata
import java.util.*

class AuthViewModel : ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData(User())
    private val _countryInfoList: MutableLiveData<List<PhoneNumberUtils.CountryInfo>> =
        MutableLiveData(listOf())
    private val _countryInfo: MutableLiveData<PhoneNumberUtils.CountryInfo> =
        MutableLiveData(PhoneNumberUtils.CountryInfo.empty())
    val phoneNumber: MutableLiveData<String> = MutableLiveData("")
    fun getUser(): LiveData<User> = _user
    val countryInfoList: LiveData<List<PhoneNumberUtils.CountryInfo>> = _countryInfoList
    private var countryCodesArray = arrayOf<String>()
    private var shortNamesArray = arrayOf<String>()
    val selectedCountryInfo: LiveData<PhoneNumberUtils.CountryInfo> = _countryInfo
    private val authManager = AuthManager()
    private val cloudDatabaseManager = CloudDatabaseManager()
    private val cloudStorageManager = CloudStorageManager()
    private var lastProfilePictureUrl: String? = null
    var ipToken: String? = null
    var isNewUser = false
    var justLoggedIn = false
    private var deviceFcmToken: String? = null

    fun getUserDataFromDatabase(callback: OnResponseCallback<User, Exception?>) {
        cloudDatabaseManager.getUsersManager().getUser(authManager.uid, callback)
    }

    fun updateUser(updatedUser: User) {
        this._user.postValue(updatedUser)
    }

    fun updateUserName(firstName: String, lastName: String) {
        val u = _user.value!!
        updateUser(u.toBuilder().setFirstName(firstName).setLastName(lastName).build())
    }

    fun updateUserPhotoUrl(photoUrl: String?) {
        lastProfilePictureUrl = photoUrl
        val u = _user.value!!
        updateUser(u.toBuilder().setPhotoUrl(photoUrl).build())
    }

    fun getCountryInfoList(context: Context) = runOnIOThread {
        if (_countryInfoList.value!!.isEmpty()) {
            PhoneNumberUtils.loadCountryInfo(context)?.let {
                _countryInfoList.postValue(it)
                countryCodesArray = it.map { info -> info.code.removePrefix("+") }.toTypedArray()
                shortNamesArray = countryInfoList.value!!.map { info -> info.short }.toTypedArray()
                PhoneNumberUtils.getCurrentCountry(context)?.also { countryShort ->
                    if (!TextUtils.isEmpty(countryShort)) {
                        InternalLogger.d(TAG, "Default Device Country : $countryShort")
                        onGetDeviceDefaultCountry(countryShort)
                    }
                }
            }
        }
    }

    fun getCountryCodeInfo(code: String): Boolean {
        if (countryCodesArray.isEmpty() || countryInfoList.value?.isEmpty() == true) return false
        val countryCode = code.removePrefix("+")
        if (countryCodesArray.contains(countryCode)) {
            val i = countryCodesArray.indexOf(countryCode)
            _countryInfo.postValue(countryInfoList.value!![i].setSelected(true))
            return true
        }
        return false
    }

    fun updatedSelectedCountryInfo(info: PhoneNumberUtils.CountryInfo) {
        _countryInfo.postValue(info)
    }

    private fun onGetDeviceDefaultCountry(countryString: String) {
        val country = countryString.uppercase(Locale.ENGLISH)
        if (shortNamesArray.contains(country)) {
            val i = shortNamesArray.indexOf(country)
            updatedSelectedCountryInfo(countryInfoList.value!![i].setSelected(true))
        }
    }

    fun saveProfilePicture(uri: Uri, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        lastProfilePictureUrl?.let { cloudStorageManager.delete(it) }
        cloudStorageManager.saveProfilePicture(getUser().value!!.UID, uri,
            StorageMetadata.Builder()
                .setCustomMetadata("uid", authManager.uid)
                .build(),
            object : OnResponseCallback<Uri, Exception> {
                override fun onSuccess(t: Uri) {
                    updateUserPhotoUrl(t.toString())
                    onSuccess()
                }

                override fun onFailure(e: Exception) {
                    onFailure(e)
                    return
                }
            }
        )
    }

    fun save() {
        val u = _user.value!!
        authManager.updateProfile(u.toFullName(), u.photoUrl)
        if (isNewUser || u.UID == "" || u.phoneNumber == null) {
            cloudDatabaseManager.saveUserDetails(u)
        } else {
            u.toBuilder()
            cloudDatabaseManager.updateUserProfileInfo(
                u.loggedInDataHashMap(), u.UID
            )
        }

        cloudDatabaseManager.getUsersManager()
            .saveNewActivityInfo(u.activityCount, getNewActivity(u.lastLogin, ipToken), u.UID) {}
        cloudDatabaseManager.saveUserPublicDetails(Recipient.Builder(u).setLastSeenToNow().build())

    }

    fun signInUsingPhoneAuthCredential(
        phoneAuthCredential: PhoneAuthCredential,
        accountManagerContext: Context,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authManager.signInUsingPhoneAuthCredential(phoneAuthCredential, accountManagerContext,
            onSuccess = {
                isNewUser = it.additionalUserInfo?.isNewUser == true
                justLoggedIn = true
                updateUser(
                    userFrom(it.user!!).setFcmToken(deviceFcmToken)
                        .setLastLoginOrNow(it.user!!.metadata?.lastSignInTimestamp)
                        .build()
                )
                onSuccess()
            },
            onFailure = {
                onFailure(it)
            }
        )
    }

    fun logout() {
        authManager.logout()
    }

    fun sendOtp(
        completePhoneNumber: String,
        activity: AppCompatActivity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        authManager.sendOtp(completePhoneNumber, activity, callbacks)
    }

    fun updateUserFromRemoteData(t: User): User {
        val u = _user.value!!.toBuilder()
            .setAbout(t.about)
            .setPhotoUrl(t.photoUrl)
            .setFirstName(t.firstName)
            .setLastName(t.lastName)
            .setUsername(t.username)
            .setActivityIndex(t.activityCount)
            .build()
        updateUser(u)
        return u
    }

    val isLoggedIn: Boolean
        get() = authManager.isLoggedIn && !isNewUser

    init {
        Firebase.messaging.token
            .addOnSuccessListener {
                deviceFcmToken = it
            }
    }

    companion object {
        private val TAG = AuthViewModel::class.java.simpleName
    }

}