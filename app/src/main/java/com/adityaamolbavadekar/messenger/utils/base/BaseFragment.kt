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
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.adityaamolbavadekar.messenger.database.conversations.DatabaseAndroidViewModel
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.utils.ApplicationStateUtils
import com.adityaamolbavadekar.messenger.utils.extensions.asApplicationClass
import com.adityaamolbavadekar.messenger.utils.extensions.showText

open class BaseFragment : LifecycleLoggerFragment() {

    lateinit var prefsManager: PrefsManager
    lateinit var applicationStateUtils: ApplicationStateUtils
    private val authManager = AuthManager()
    val database: DatabaseAndroidViewModel by activityViewModels {
        DatabaseAndroidViewModel.Factory(requireActivity().application.asApplicationClass().database)
    }
    lateinit var me: Recipient
    private var toaster: Toast? = null

    @SuppressLint("ShowToast")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        toaster = Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT)
        prefsManager = PrefsManager(requireContext())
        applicationStateUtils = ApplicationStateUtils(requireContext())
        if (authManager.isLoggedIn) {
            prefsManager.getUserObject()?.let { me = Recipient.Builder(it).build() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (authManager.isLoggedIn) {
            database.getLiveDataLoggedInAccount().observe(viewLifecycleOwner) {
                it?.let { user -> me = Recipient.Builder(user).build() }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        toaster = null
    }

    protected fun showToastMessage(m: Int) {
        toaster?.showText(m)
    }

    protected fun showToastMessage(m: () -> String) {
        toaster?.showText(m)
    }

}