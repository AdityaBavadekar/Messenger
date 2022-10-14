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

package com.adityaamolbavadekar.messenger.utils.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.ui.registration.AuthActivity
import com.adityaamolbavadekar.messenger.utils.ApplicationStateUtils
import com.adityaamolbavadekar.messenger.utils.ApplicationStateUtils.ApplicationState
import com.adityaamolbavadekar.messenger.utils.ApplicationStateUtils.ApplicationState.Companion.getUnauthenticatedStateValues
import com.adityaamolbavadekar.messenger.utils.extensions.showText

open class BaseActivity : LifecycleLoggerActivity() {

    private var toaster: Toast? = null
    private lateinit var applicationStateUtils: ApplicationStateUtils
    private val isLoggedIn = AuthManager().isLoggedIn

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toaster = Toast.makeText(this, "", Toast.LENGTH_SHORT)
        applicationStateUtils = ApplicationStateUtils(this)
        routeIfNecessary()

        if (!isFinishing) {
            initializeScreenshotSecurity()
            setupFullscreen()
            onCreateActivity(savedInstanceState)
        }

    }

    open fun onCreateActivity(savedInstanceState: Bundle?) {}

    override fun onResume() {
        super.onResume()
        initializeScreenshotSecurity()
    }

    override fun onDestroy() {
        super.onDestroy()
        toaster = null
    }

    private fun shouldOpenAuthScreen(): Boolean {
        return !isLoggedIn &&
                applicationStateUtils.get() in getUnauthenticatedStateValues()
                && !isAuthActivity()
    }

    open fun shouldKeepScreenSecurity(): Boolean {
        return false
    }

    open fun shouldSupportFullscreen(): Boolean {
        return false
    }

    private fun setupFullscreen() {
        if (!shouldSupportFullscreen()) {
            return
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun initializeScreenshotSecurity() {
        if (shouldKeepScreenSecurity()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    protected fun showToastMessage(m: Int) {
        toaster?.showText(m)
    }

    private fun routeIfNecessary() {
        getIntentForState()?.let { routingIntent ->
            startActivity(routingIntent)
            finish()
        }
    }

    private fun getIntentForState(): Intent? {
        if (isAuthActivity()) return null
        return when (applicationStateUtils.get()) {
            ApplicationState.STATE_FRESH_INSTALL -> getAuthActivityIntent(true)
            ApplicationState.STATE_REGISTRATION -> getAuthActivityIntent(false)
            ApplicationState.STATE_SETUP_PROFILE -> getSetupProfileIntent()
            ApplicationState.STATE_NORMAL -> null
        }
    }

    private fun getAuthActivityIntent(freshInstall: Boolean): Intent {
        return AuthActivity.newIntent(this)
            .putExtra(AuthActivity.EXTRA_FRESH_INSTALL, freshInstall)
    }

    private fun getSetupProfileIntent(): Intent {
        return AuthActivity.newIntent(this)
            .putExtra(AuthActivity.EXTRA_SETUP_PROFILE, true)
    }

    private fun isAuthActivity(): Boolean {
        return this::class.simpleName == AuthActivity::class.simpleName
    }

}