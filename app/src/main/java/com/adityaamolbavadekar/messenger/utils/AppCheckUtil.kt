package com.adityaamolbavadekar.messenger.utils

import android.content.Context
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory

class AppCheckUtil {
    companion object {
        private val TAG = AppCheckUtil::class.java.simpleName
        fun activate(context: Context) {
            InternalLogger.d(TAG, "activate : Initialising FirebaseApp")
            FirebaseApp.initializeApp(context)
            InternalLogger.d(TAG, "activate : Activating AppCheck")
            val firebaseAppCheck = FirebaseAppCheck.getInstance()
            firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance()
            )
            InternalLogger.d(TAG, "activate : AppCheck activated")
        }
    }
}