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

package com.adityaamolbavadekar.messenger

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.adityaamolbavadekar.messenger.databinding.FragmentSignupPhoneV2Binding
import com.adityaamolbavadekar.messenger.ui.registration.AuthViewModel

class TestingActivity : AppCompatActivity() {

    private lateinit var binding: FragmentSignupPhoneV2Binding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCountryInfoList(this)
        binding = FragmentSignupPhoneV2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.selectedCountryInfo.observe(this) {
            binding.autoComplete.setText(it.short + "(" + it.code + ")")
            binding.countryCodeEditTextBinding.countryCode.text = it.short + "(" + it.code + ")"
            binding.countryCode.setText(it.short + "(" + it.code + ")")
        }

        binding.countryCode.setOnClickListener {
            viewModel.updatedSelectedCountryInfo(viewModel.countryInfoList.value!!.random())
        }

        binding.countryCodeEditTextBinding.countryCode.setOnClickListener {
            viewModel.updatedSelectedCountryInfo(viewModel.countryInfoList.value!!.random())
        }

        binding.autoComplete.setOnClickListener {
            viewModel.updatedSelectedCountryInfo(viewModel.countryInfoList.value!!.random())
        }

    }


}