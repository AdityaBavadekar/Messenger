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

package com.adityaamolbavadekar.messenger

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.databinding.ActivityMainBinding
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.ui.settings.SettingsActivity
import com.adityaamolbavadekar.messenger.utils.AndroidUtils
import com.adityaamolbavadekar.messenger.utils.ColorUtils
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity
import com.adityaamolbavadekar.messenger.utils.extensions.isNotNull
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.views.CircularMenuItemButton
import com.google.android.material.navigation.NavigationBarView

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val database: DatabaseAndroidViewModel by viewModels()
    private var userPhotoUrl: String? = null
    private val authManager = AuthManager()
    private var fabClickListener: () -> Unit = {}
    private lateinit var navController: NavController

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = Navigation.findNavController(this, R.id.container)
        NavigationUI.setupWithNavController(binding.navView, navController)
        (binding.navView as NavigationBarView).setOnItemReselectedListener {}
        AndroidUtils.setNavigationBarColor(window, ColorUtils(this).getPrimaryContainerColor())
        database.getLiveDataLoggedInAccount().observe(this) {
            it?.let { user ->
                if (user.photoUrl.isNotNull()) {
                    userPhotoUrl = user.photoUrl!!
                    invalidateMenu()
                }
            }
        }

        this.binding.fab.setOnClickListener {
            fabClickListener()
        }

        this.binding.fab.setOnLongClickListener {
            fabClickListener()
            true
        }

    }

    fun setOnFabClickListener(listener: () -> Unit) {
        this.fabClickListener = listener
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.profile_picture_image_menu_item)?.let { item ->
            val view = item.actionView as CircularMenuItemButton
            view.setOnClickListener {
                startActivity(SettingsActivity.newIntent(this))
            }
            view.setButtonImageRes(R.drawable.ic_user_profile_default)
            userPhotoUrl?.let {
                view.button.load(
                    Uri.parse(it),
                    true,
                    R.drawable.ic_user_profile_default,
                    R.drawable.ic_user_profile_default
                )
            }
            view.tooltipText(R.string.account_and_settings)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!navController.popBackStack()) {
            finish()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}