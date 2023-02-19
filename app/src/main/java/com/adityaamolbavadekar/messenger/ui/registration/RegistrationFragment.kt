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
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.adityaamolbavadekar.messenger.MainActivity
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.FragmentSignupPhoneBinding
import com.adityaamolbavadekar.messenger.dialogs.Dialogs
import com.adityaamolbavadekar.messenger.model.User
import com.adityaamolbavadekar.messenger.utils.*
import com.adityaamolbavadekar.messenger.utils.base.BindingHelperFragment
import com.adityaamolbavadekar.messenger.utils.extensions.getInformativeMessage
import com.adityaamolbavadekar.messenger.utils.extensions.goBack
import com.adityaamolbavadekar.messenger.utils.extensions.navigate
import com.adityaamolbavadekar.messenger.utils.extensions.toast
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.*

class RegistrationFragment : BindingHelperFragment<FragmentSignupPhoneBinding>() {

    override fun onShouldInflateBinding(): FragmentSignupPhoneBinding {
        return FragmentSignupPhoneBinding.inflate(layoutInflater)
    }

    private var verificationId = ""
    private var isAutoVerified = false
    private var resendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private val onGetUserInfo = object : OnResponseCallback<User, Exception?> {
        override fun onSuccess(t: User) {
            InternalLogger.logI(TAG, "Saving User Data to preferences.")
            viewModel.updateUserFromRemoteData(t).also { updatedUser ->
                prefsManager.saveUserObject(updatedUser)
                database.addLoggedInAccount(updatedUser)
            }
            applicationStateUtils.update(ApplicationStateUtils.ApplicationState.STATE_SETUP_PROFILE)
            dialog?.dismiss()
            navigate(R.id.action_registrationFragment_to_enterNameFragment)
        }

        override fun onFailure(e: Exception?) {
            dialog?.dismiss()
            viewModel.logout()
            handleError(e ?: FirebaseNetworkException("Network unavailable"))
        }
    }
    private var dialog: AlertDialog? = null
    private var phoneNumber: String = ""
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCountryInfoList(requireContext())
        prefsManager.getCountryInfo()?.let { info ->
            if (info.isNotEmpty()) {
                viewModel.updatedSelectedCountryInfo(info)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (applicationStateUtils.get() == ApplicationStateUtils.ApplicationState.STATE_SETUP_PROFILE) {
            RegistrationFragmentDirections.actionRegistrationFragmentToEnterNameFragment()
        } else {
            applicationStateUtils.update(ApplicationStateUtils.ApplicationState.STATE_REGISTRATION)
            setupViews()
            switchLayoutVisibility(false)
        }
    }

    private fun setupViews() {
        binding.phone.filters = binding.phone.filters + PhoneNumberEditTextFilter()
        binding.header.text = getString(R.string.enter_sms_code_we_sent_to, phoneNumber)
        binding.codeView.addTextChangedListener {
            if (it != null && it.length == 6) {
                val code = it.toString()
                onCodeViewFilled(code)
            }
        }

        binding.phone.addTextChangedListener { _ ->
            validateAndEnableButton()
        }

        viewModel.selectedCountryInfo.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                val code = it.code.removePrefix("+")
                binding.countryName.text = it.country
                if (binding.countryCode.text != null && binding.countryCode.text!!.toString() != code) {
                    binding.countryCode.setText(code)
                    binding.countryCode.setSelection(code.length)
                }
            }
        }

        binding.countryCodeHolder.setEndIconOnClickListener {
            navigate(R.id.action_registrationFragment_to_isoCountrySelectionFragment)
        }
        binding.countryCode.addTextChangedListener {
            if (it != null && it.trim().isNotEmpty()) {
                if (!viewModel.getCountryCodeInfo(it.toString())) {
                    binding.countryName.text = getString(R.string.invalid_code)
                }
            } else {
                binding.countryName.text = getString(R.string.select_a_country)
            }
            validateAndEnableButton()
        }

        binding.countryName.setOnClickListener {
            navigate(R.id.action_registrationFragment_to_isoCountrySelectionFragment)
        }

        if (binding.phone.text.toString().trim().isEmpty()) {
            prefsManager.getUserPhoneNumberInfo()?.let {
                binding.phone.setText(it.phone)
                binding.phone.setSelection(it.phone.length)
                viewModel.getCountryCodeInfo(it.code.removePrefix("+"))
            }
        }

        binding.nextButton.setOnClickListener {
            val code = viewModel.selectedCountryInfo.value!!.code.removePrefix("+")
            val simplePhoneNumber =
                binding.phone.text.toString().trim().lowercase(Locale.getDefault())
            validate(simplePhoneNumber, code)
        }
        initDisclaimer()
    }

    private fun validateAndEnableButton() {
        val editable = binding.phone.text
        val countryCode = viewModel.selectedCountryInfo.value!!
        binding.nextButton.isEnabled =
            editable != null && editable.length > 9 && countryCode.isNotEmpty() && binding.countryCode.text.toString()
                .trim().isNotEmpty()
    }

    private fun initDisclaimer() {

        val privacyPolicyClickableSpan = CustomLinkHandlerSpan(Constants.Application.POLICY_LINK)
        val termsClickableSpan = CustomLinkHandlerSpan(Constants.Application.TERMS_LINK)

        val privacyPolicyText = getString(R.string.privacy_policy)
        val terms = getString(R.string.terms_of_service)
        val spannableStringBuilder = SpannableStringBuilder(
            getString(
                R.string.you_agree_disclaimer_phone_auth,
                privacyPolicyText,
                terms
            )
        )
        val indexOfPrivacyPolicy =
            spannableStringBuilder.indexOf(privacyPolicyText)
        spannableStringBuilder.setSpan(
            privacyPolicyClickableSpan, indexOfPrivacyPolicy,
            indexOfPrivacyPolicy + privacyPolicyText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val indexOfTerms =
            spannableStringBuilder.indexOf(terms)
        spannableStringBuilder.setSpan(
            termsClickableSpan, indexOfTerms,
            indexOfTerms + terms.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.disclaimer.linksClickable = true
        binding.disclaimer.text = spannableStringBuilder
        binding.disclaimer.movementMethod = MessengerLinkMovementMethod
    }

    private fun onSuccessfullyLoggedIn() {
        InternalLogger.logD(TAG, "onSuccessfullyLoggedIn()")
        if (viewModel.isNewUser) {
            dialog?.dismiss()
            applicationStateUtils.update(ApplicationStateUtils.ApplicationState.STATE_SETUP_PROFILE)
            Navigation.findNavController(requireActivity(), R.id.container)
                .navigate(RegistrationFragmentDirections.actionRegistrationFragmentToEnterNameFragment())
        } else {
            viewModel.getUserDataFromDatabase(onGetUserInfo)
        }
    }

    private fun onLoginFailure(e: Exception) {
        InternalLogger.logE(TAG, "onLoginFailure()", e)
        dialog?.dismiss()
        handleError(e)
        switchLayoutVisibility(false)
    }

    fun onSmsCodeAutoVerified(smsCode: String?) {
        InternalLogger.logD(TAG, "onSmsCodeAutoVerified(${smsCode ?: ""})")
        isAutoVerified = true
        smsCode?.let {
            binding.codeView.setText(it)
        }
    }

    fun onSmsCodeVerificationFailed(e: Exception) {
        InternalLogger.logE(TAG, "onSmsCodeVerificationFailed()", e)
        //smsTextWatcher.onInvalidSmsCodeEntered()
        binding.codeView.text = null
        handleError(e)
        goBack()
    }

    private fun onCodeViewFilled(code: String) {
        InternalLogger.logD(TAG, "onOtpViewCompletelyFilledListener(${code})")
        dialog = Dialogs.showLoadingDialog(requireContext())
        if (!isAutoVerified && code.trim().length == 6) {
            signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verificationId, code))
        }
    }

    private val onVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                InternalLogger.logD(TAG, "onCodeAutoRetrievalTimeOut($p0)")
                super.onCodeAutoRetrievalTimeOut(p0)
                requireContext().toast { "Verification Timeout" }
                switchLayoutVisibility(false)
            }

            override fun onCodeSent(
                verificationIdentifier: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                InternalLogger.logD(TAG, "onCodeSent($verificationIdentifier,$p1)")
                verificationId = verificationIdentifier
                resendingToken = p1
                super.onCodeSent(verificationIdentifier, p1)
                invokeOnCodeSent(null)
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val sixDigitSmsCode = phoneAuthCredential.smsCode
                InternalLogger.logD(
                    TAG,
                    "onVerificationAutoCompleted(`${sixDigitSmsCode ?: "null"}`)"
                )
                onSmsCodeAutoVerified(sixDigitSmsCode)
                signInWithPhoneAuthCredential(phoneAuthCredential)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                InternalLogger.logE(
                    TAG,
                    "onVerificationFailed(${exception.javaClass.simpleName})",
                    exception
                )
                onSmsCodeVerificationFailed(exception)
            }

        }

    private fun signInWithPhoneAuthCredential(phoneAuthCredential: PhoneAuthCredential) {
        viewModel.signInUsingPhoneAuthCredential(
            phoneAuthCredential,
            onSuccess = {
                onSuccessfullyLoggedIn()
            },
            onFailure = { exception ->
                onLoginFailure(exception)
            },
            accountManagerContext = requireContext()
        )
    }

    private fun switchLayoutVisibility(smsLayoutVisible: Boolean) {
        if (smsLayoutVisible) {
            setSmsLayoutVisible()
        } else {
            setEnterPhoneLayoutVisible()
        }
    }

    private fun setSmsLayoutVisible() {
        binding.rootEnterSmsCode.isVisible = true
        binding.rootEnterPhone.isGone = true
    }

    private fun setEnterPhoneLayoutVisible() {
        binding.rootEnterPhone.isVisible = true
        binding.rootEnterSmsCode.isGone = true
    }

    private fun saveUser(phoneNumber: String, code: String) {
        val u = viewModel.getUser().value!!
        u.phoneNumber = phoneNumber
        viewModel.updateUser(u)
        viewModel.phoneNumber.postValue("+$code $phoneNumber")
        prefsManager.saveUserPhoneNumberCode(code)
        prefsManager.saveUserPhoneNumber(phoneNumber)
    }

    private fun handleError(exception: Exception?) {
        dialog?.dismiss()
        exception?.let {
            Dialogs.showErrorDialog(
                requireContext(),
                it.getInformativeMessage(requireContext())
            ) { }
        }
    }

    private fun validate(phoneNumberWithoutCode: String, code: String) {
        if (!isVisible) return
        val validation = Validator.checkPhoneNumber(phoneNumberWithoutCode, code)
        if (validation != null) {
            handleError(validation)
            return
        }
        handleError(null)
        prefsManager.saveCountryInfo(viewModel.selectedCountryInfo.value!!)
        val completePhoneNumber = "+${code}${phoneNumberWithoutCode}"
        val completePhoneNumberWithSpace = "+${code} $phoneNumberWithoutCode"
        phoneNumber = completePhoneNumberWithSpace
        viewModel.phoneNumber.postValue(completePhoneNumberWithSpace)
        Dialogs.showConfirmPhoneNumberDialog(
            requireContext(),
            completePhoneNumberWithSpace
        ) { shouldContinue ->
            if (shouldContinue) {
                dialog = Dialogs.showLoadingDialog(
                    requireContext(),
                    getString(R.string.sending_sms_to_formatted, completePhoneNumberWithSpace)
                )
                binding.header.text = getString(R.string.enter_sms_code_we_sent_to, phoneNumber)
                saveUser(phoneNumberWithoutCode, code)
                viewModel.sendOtp(
                    completePhoneNumber.trim(),
                    (requireActivity() as AppCompatActivity),
                    onVerificationStateChangedCallbacks
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isLoggedIn
            && applicationStateUtils.get() != ApplicationStateUtils.ApplicationState.STATE_SETUP_PROFILE
        ) {
            startActivity(MainActivity.newIntent(requireContext()))
            requireActivity().finish()
        }
    }

    fun invokeOnCodeSent(it: Exception?) {
        dialog?.dismiss()
        if (it == null) {
            switchLayoutVisibility(true)
        } else {
            handleError(it)
        }
    }

    companion object {
        private const val TAG = "RegistrationFragment"
    }

}
