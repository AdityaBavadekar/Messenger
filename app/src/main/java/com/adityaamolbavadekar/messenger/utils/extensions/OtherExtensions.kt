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

package com.adityaamolbavadekar.messenger.utils.extensions

import android.app.Application
import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.adityaamolbavadekar.messenger.App
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.AuthError
import com.adityaamolbavadekar.messenger.utils.Validator
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException

fun Exception.getInformativeMessage(
    context: Context,
    onElseBlockCalled: ((Exception) -> String)? = null
): String {
    return when (this) {
/*USERNAME*/
        /*is Validator.Exceptions.EmptyUsernameEmailAddressException -> context.getString(R.string.please_enter_your_email_address_or_username_to_login)
        is Validator.Exceptions.EmptyUsernameException -> context.getString(R.string.the_username_is_empty)
        is Validator.Exceptions.InvalidUsernameException -> context.getString(R.string.the_username_is_invalid)
        is Validator.Exceptions.InvalidUsernameGenerationException -> context.getString(R.string.the_username_is_invalid_it_can_contain)
*/
/*PHONE NUMBER*/
        is Validator.Exceptions.EmptyPhoneNumberException -> context.getString(R.string.the_phone_number_is_empty)
        is Validator.Exceptions.InvalidPhoneNumberException -> context.getString(R.string.the_phone_number_is_invalid)
        is Validator.Exceptions.InvalidCountryCodeException -> context.getString(R.string.the_country_code_is_invalid)

/*Firebase Exceptions*/

        is FirebaseAuthException -> {
            when (val error = AuthError.fromException(this)) {
                AuthError.ERROR_USER_DISABLED -> context.getString(R.string.user_is_disabled)
                /**
                 * Can happen in many cases:
                 * 1. Password is invalid.
                 * 2. Password is weak. (But we handle this exception with [Exceptions.WeakPasswordException])
                 * 3. User does not have a password, like when a user has signed up only using Phone Number.
                 * 4. Sms code provided when logging in using Phone Number was invalid.
                 * */
                AuthError.ERROR_INVALID_PHONE_NUMBER -> context.getString(R.string.the_phone_number_is_invalid)
                else -> error.description
            }
        }
        is FirebaseNetworkException -> context.getString(R.string.network_unavailable)
        is FirebaseTooManyRequestsException -> context.getString(R.string.too_many_requests)

/*Others*/
        else -> onElseBlockCalled?.invoke(this)
            ?: context.getString(R.string.oops_something_went_wrong_try_again_later)
    }
}

fun Fragment.navigate(@IdRes id: Int) {
    Navigation.findNavController(requireActivity(), R.id.container).navigate(id)
}

fun Fragment.goBack() {
    Navigation.findNavController(requireActivity(), R.id.container).popBackStack()
}

fun Any?.isNull(): Boolean {
    return this == null
}

fun Any?.isNotNull(): Boolean {
    return this != null
}

fun Context.asApplicationClass(): App {
    return this.applicationContext as App
}

fun Application.asApplicationClass(): App {
    return this as App
}