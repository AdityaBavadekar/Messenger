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

package com.adityaamolbavadekar.messenger.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.adityaamolbavadekar.messenger.BuildConfig
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.AboutFragmentBinding
import com.adityaamolbavadekar.messenger.utils.base.LifecycleLoggerFragment

class AboutMessengerFragment : LifecycleLoggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AboutFragmentBinding.inflate(layoutInflater)
        binding.imageHolder.setOnClickListener { }
        binding.version.text = getString(R.string.app_version_formatted, BuildConfig.VERSION_NAME)
        binding.licenses.setOnClickListener {

        }
        return binding.root
    }
}