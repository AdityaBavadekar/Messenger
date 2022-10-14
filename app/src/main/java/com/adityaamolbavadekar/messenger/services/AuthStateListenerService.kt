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

package com.adityaamolbavadekar.messenger.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.ui.loggedout.NotifyLoggedOutActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.internal.IdTokenListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthStateListenerService : Service() {

    private val idTokenListener = IdTokenListener {
        if (isLoggedIn && it.token != null) {
            prefsManager.saveFcmToken(it.token)
        }
    }

    private val authStateListener = FirebaseAuth.AuthStateListener { _ ->
        if (!isLoggedIn && prefsManager.getLoggedInStatus()) {
            startActivity(
                Intent(this, NotifyLoggedOutActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
        auth.currentUser?.let {
            prefsManager.refreshUser(it)
        }
    }
    private val auth = Firebase.auth
    private val prefsManager = PrefsManager(this)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        auth.addAuthStateListener(authStateListener)
        auth.addIdTokenListener(idTokenListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.removeAuthStateListener(authStateListener)
        auth.removeIdTokenListener(idTokenListener)
    }

    private val isLoggedIn: Boolean
        get() = auth.currentUser != null

    companion object {
        fun start(context: Context) {
            context.startService(Intent(context, AuthStateListenerService::class.java))
        }
    }

}