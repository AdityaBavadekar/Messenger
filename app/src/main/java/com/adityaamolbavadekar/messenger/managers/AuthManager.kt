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

package com.adityaamolbavadekar.messenger.managers

import android.app.Activity
import android.content.Context
import android.net.Uri
import com.adityaamolbavadekar.messenger.account.MessengerAccountAuthenticator
import com.adityaamolbavadekar.messenger.account.MessengerAccountInfo
import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

/** A class that interacts with the [FirebaseAuth] class to handle all
 * operations related to UserAuthentication.
 * */
class AuthManager {

    private var firebaseAuth: FirebaseAuth = Firebase.auth

    /*USER GETTERS*/

    /*Whether user is logged in*/
    val isLoggedIn: Boolean
        get() {
            val bool = (firebaseAuth.currentUser != null)
            if (!bool) InternalLogger.logW(TAG, "User is not logged in.")
            return bool
        }

    /*UID provided by Firebase*/
    val uid: String
        get() {
            return getFirebaseUserOrThrow.uid
        }

    class NullUserException(message: String? = null) :
        Exception("The current user is `null` or is not logged in; ${message ?: ""}")

    private val getFirebaseUserOrThrow: FirebaseUser
        get() {
            return firebaseAuth.currentUser ?: throw NullUserException()
        }

    val getFirebaseUser: FirebaseUser?
        get() {
            return firebaseAuth.currentUser
        }

    /*USER GETTERS*/

    /*LOGIN*/
    fun sendOtp(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        InternalLogger.logD(TAG, "Sending Sms Code to $phoneNumber")
        val phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)

    }

    fun signInUsingPhoneAuthCredential(
        cred: PhoneAuthCredential,
        accountManagerContext: Context,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebaseAuth.signInWithCredential(cred)
            .addOnSuccessListener { authResult ->
                InternalLogger.logI(
                    TAG,
                    "signInWithCredential -> Successfully Logged into account.\nPrinting UserInfo..."
                )
                printAuthResult(authResult.user!!)
                internalAddDeviceAccount(accountManagerContext, authResult.user!!.phoneNumber)
                onSuccess(authResult)
            }
            .addOnFailureListener { exception ->
                InternalLogger.logE(TAG, "signInWithCredential -> Unable to signIn.", exception)
                onFailure(exception)
            }
    }

    private fun internalAddDeviceAccount(
        accountManagerContext: Context,
        phoneNumber: String?
    ): Boolean {
        val accountManager = MessengerAccountAuthenticator.accountManager(accountManagerContext)
        val existingAccounts = accountManager.getAccountsByType(MessengerAccountInfo.ACCOUNT_TYPE)
        if (existingAccounts.isNotEmpty()) {
            existingAccounts.forEach {
                MessengerAccountAuthenticator.deleteAccount(it, accountManagerContext)
            }
        }

        MessengerAccountAuthenticator.account(phoneNumber).also { newAccount ->
            return if (MessengerAccountAuthenticator.addAccount(
                    newAccount,
                    accountManagerContext
                )
            ) {
                InternalLogger.logD("internalAddDeviceAccount", "Added.")
                true
            } else {
                InternalLogger.logE("internalAddDeviceAccount", "Unable to add.")
                false
            }
        }
    }

    private fun printAuthResult(u: FirebaseUser) {
        InternalLogger.debugInfo(
            TAG, "FirebaseLoggedInUser => \n" +
                    "Name : ${u.displayName ?: "None"}\n" +
                    "Email : ${u.email ?: "None"}\n" +
                    "PhoneNumber : ${u.phoneNumber ?: "None"}\n" +
                    "Uid : ${u.uid}\n" +
                    "isAnonymous : ${u.isAnonymous}\n" +
                    "Created : ${
                        simpleDateFormat(
                            u.metadata?.creationTimestamp ?: System.currentTimeMillis(),
                            "dd-MMMM-yyyy h:mm:ss a z",
                        )
                    }\n" +
                    "Provider Data : ${u.providerData.map { it.providerId }}\n" +
                    "PhotoUrl : ${u.photoUrl ?: ""}\n" +
                    ""
        )
    }

    /*Update FirebaseUsers' profile's Display Name as well as Photo Uri*/
    fun updateProfile(name: String?, photoUrl: String?) {
        firebaseAuth.currentUser?.let { firebaseUser ->
            val request = userProfileChangeRequest {
                name?.let { s -> this.displayName = s }
                photoUrl?.let { url -> this.photoUri = Uri.parse(url) }
                this.build()
            }
            firebaseUser.updateProfile(request)
                .addOnSuccessListener {
                    InternalLogger.logI(TAG, "UPDATE PROFILE NAME -> Successfully updated profile.")
                }
                .addOnFailureListener { exception ->
                    InternalLogger.logE(
                        TAG,
                        "UPDATE PROFILE NAME -> Unable to updated profile.",
                        exception
                    )
                }
        }
    }

    /*Logout current users*/
    fun logout() {
        InternalLogger.logW(TAG, "Logging out user ${getFirebaseUserOrThrow.uid}")
        firebaseAuth.signOut()
    }

    /*Delete the user's account permanently, immediately*/
    fun deleteAccount(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebaseAuth.currentUser?.let {
            it.delete()
                .addOnSuccessListener {
                    InternalLogger.logI(TAG, "DELETION -> Successfully deleted account.")
                    onSuccess.invoke()
                }
                .addOnFailureListener { ex ->
                    InternalLogger.logE(TAG, "DELETION -> Unable to delete account.", ex)
                    onFailure.invoke(ex)
                }
        }
    }

    companion object {
        private const val TAG = "AuthManager"
    }

}