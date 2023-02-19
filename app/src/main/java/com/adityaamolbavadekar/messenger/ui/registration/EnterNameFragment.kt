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

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.adityaamolbavadekar.messenger.MainActivity
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.FragmentSignupNameBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.managers.InternetManager
import com.adityaamolbavadekar.messenger.utils.ApplicationStateUtils
import com.adityaamolbavadekar.messenger.utils.ImageLoader
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class EnterNameFragment : BindingHelperFragment<FragmentSignupNameBinding>() {

    override fun onShouldInflateBinding(): FragmentSignupNameBinding {
        return FragmentSignupNameBinding.inflate(layoutInflater)
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { savePicture(it) }
        }

    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationStateUtils.update(ApplicationStateUtils.ApplicationState.STATE_SETUP_PROFILE)
        imageLoader = ImageLoader.with(requireContext())
        if (!viewModel.justLoggedIn) {
            InternalLogger.logD(TAG, "JustLoggedIn : false")
            prefsManager.getUserObject()?.let {
                viewModel.updateUser(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser().observe(viewLifecycleOwner) {
            binding.firstName.setText(it.firstName)
            binding.firstName.setSelection(it.firstName?.length ?: 0)
            binding.lastName.setText(it.lastName)
            binding.lastName.setSelection(it.lastName?.length ?: 0)
            it.photoUrl?.let { url ->
                imageLoader
                    .load(url, binding.imageView, R.drawable.ic_user_profile_default, true)
            }
        }

        binding.imageView.setOnClickListener { imagePickerLauncher.launch("image/*") }

        var isConnectedToNetwork = true

        InternetManager.isConnected.observe(viewLifecycleOwner) {
            isConnectedToNetwork = it
        }

        binding.nextButton.setOnClickListener {
            val f = binding.firstName.text.toString().trim()
            val l = binding.lastName.text.toString().trim()
            if (f.isNotEmpty() && l.isNotEmpty() && isConnectedToNetwork) {
                disableInputs()
                onShouldSaveInfo()
            } else if (!isConnectedToNetwork) {
                enableInputs()
                binding.errorMessage.text =
                    getString(R.string.please_check_your_network_connection_and_try_again)
                binding.errorMessage.isVisible = true
            } else {
                enableInputs()
                binding.errorMessage.text = getString(R.string.enter_valid_name_and_last_name)
                binding.errorMessage.isVisible = true
            }
        }
    }

    private fun disableInputs() {
        binding.firstName.isEnabled = false
        binding.lastName.isEnabled = false
    }

    private fun enableInputs() {
        binding.firstName.isEnabled = true
        binding.lastName.isEnabled = true
    }

    private fun onShouldSaveInfo() {
        val loader = Dialogs.showLoadingDialog(requireContext())
        val f = binding.firstName.text.toString().trim()
        val l = binding.lastName.text.toString().trim()
        binding.errorMessage.text = null
        binding.errorMessage.isVisible = false
        val u = viewModel.getUser().value!!
        u.firstName = f
        u.lastName = l
        viewModel.updateUserName(f, l)
        prefsManager.saveUserFirstName(f)
        prefsManager.saveUserLastName(l)
        prefsManager.saveUserFullName(u.toFullName())
        viewModel.save()
        database.addLoggedInAccount(u)
        applicationStateUtils.update(ApplicationStateUtils.ApplicationState.STATE_NORMAL)
        loader.dismiss()
        startActivity(MainActivity.newIntent(requireContext()))
        requireActivity().finish()
    }

    override fun onPause() {
        super.onPause()
        val f = binding.firstName.text.toString().trim()
        val l = binding.lastName.text.toString().trim()
        viewModel.updateUserName(f, l)
    }

    private fun savePicture(uri: Uri) {
        val loader = Dialogs.showLoadingDialog(requireContext())
        viewModel.saveProfilePicture(uri,
            onSuccess = {
                loader.dismiss()
            },
            onFailure = {
                loader.dismiss()
                showToastMessage(R.string.unable_to_upload_profile_picture)
            })

    }

    companion object {
        private val TAG = EnterNameFragment::class.java.simpleName
    }

}