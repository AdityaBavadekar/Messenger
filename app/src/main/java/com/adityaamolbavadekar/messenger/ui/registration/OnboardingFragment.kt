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

import android.os.Bundle
import android.view.View
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.OnboardingFragmentBinding
import com.adityaamolbavadekar.messenger.utils.ApplicationStateUtils
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.navigate

class OnboardingFragment : BindingHelperFragment<OnboardingFragmentBinding>() {

    override fun onShouldInflateBinding(): OnboardingFragmentBinding {
        return OnboardingFragmentBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueButton.setOnClickListener {
            applicationStateUtils.update(ApplicationStateUtils.ApplicationState.STATE_REGISTRATION)
            navigate(R.id.action_onboardingFragment_to_registrationFragment)
        }

    }

}