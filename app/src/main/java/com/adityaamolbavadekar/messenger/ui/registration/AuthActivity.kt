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
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.ActivityAuthBinding
import com.adityaamolbavadekar.messenger.utils.base.LifecycleLoggerActivity

class AuthActivity : LifecycleLoggerActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.container)
        if (intent?.getBooleanExtra(EXTRA_FRESH_INSTALL, false) == true) {
            navController
                .navigate(
                    R.id.onboardingFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.onboardingFragment, true).build()
                )
        } else if (intent?.getBooleanExtra(EXTRA_SETUP_PROFILE, false) == true) {
            navController
                .navigate(
                    R.id.enterNameFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.enterNameFragment, true).build()
                )
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.enterNameFragment -> finish()
            R.id.onboardingFragment -> finish()
            else -> super.onBackPressed()
        }
    }


    companion object {
        private val TAG = AuthActivity::class.java.simpleName
        const val EXTRA_FRESH_INSTALL = "fresh_install"
        const val EXTRA_SETUP_PROFILE = "setup_profile"
        fun newIntent(context: Context): Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }

}