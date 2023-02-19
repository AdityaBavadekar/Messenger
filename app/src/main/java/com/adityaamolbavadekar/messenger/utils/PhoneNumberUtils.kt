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

package com.adityaamolbavadekar.messenger.utils

import android.app.Activity
import android.content.Context
import android.telephony.TelephonyManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PhoneNumberUtils {

    private const val TAG = "PhoneNumberUtils"
    data class CompletePhoneNumber(val phoneNumber: String, val countryCode: String)

    fun requestPhoneNumber(
        activity: Activity,
        phoneNumberHintIntentRequestLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        val request = GetPhoneNumberHintIntentRequest.builder().build()
        Identity.getSignInClient(activity)
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener {
                val intentSenderRequest = IntentSenderRequest.Builder(it.intentSender).build()
                phoneNumberHintIntentRequestLauncher.launch(intentSenderRequest)
            }
            .addOnFailureListener {
                InternalLogger.logE(
                    TAG,
                    "Unable to get phone number from PhoneNumberHintPicker. ",
                    it
                )
            }
    }

    private fun process(phoneNumber: String): CompletePhoneNumber? {

        if (phoneNumber.trim().isEmpty()) return null

        val code: String
        val phone: String
        val tempPhone = phoneNumber.reversed()
        phone = tempPhone.substring(0, 10).reversed()
        code = phoneNumber.removeSuffix(phone).removePrefix("+")
        InternalLogger.logD(
            TAG,
            "PhoneFromApi=$phoneNumber\n" +
                    "PhoneFromApi.reversed=$tempPhone\n" +
                    "MainPhone = $phone \n" +
                    "PhoneNumberCode = $code"
        )

        return CompletePhoneNumber(phone, code)
    }

    fun resolve(it: ActivityResult?, activity: Activity): CompletePhoneNumber? {
        it?.data?.let { intent ->
            return try {
                val phoneNumber =
                    Identity.getSignInClient(activity).getPhoneNumberFromIntent(intent)
                process(phoneNumber)
            } catch (e: Exception) {
                InternalLogger.logE(
                    TAG,
                    "Unable to show get phoneNumber from intent ",
                    e
                )
                null
            }
        }
        return null
    }

    fun getCurrentCountry(context: Context): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val countryIso = tm.simCountryIso
        InternalLogger.logD(TAG, "DefaultIso : ${countryIso ?: "None"}")
        return countryIso
    }

    data class CountryInfo(
        val country: String,
        val code: String,
        val short: String,
        val icon: String,
    ) {
        companion object {
            fun empty(): CountryInfo {
                return CountryInfo("", "", "", "")
            }
        }

        fun isNotEmpty(): Boolean {
            return country.trim().isNotEmpty() && code.trim().isNotEmpty()
        }

        fun isEmpty(): Boolean {
            return country.trim().isEmpty() || code.trim().isEmpty()
        }

        fun setSelected(b: Boolean): CountryInfo {
            return this
        }

    }

    private fun readCountryInfo(context: Context): String? {
        val jsonString: String
        return try {
            val reader = context.resources
                .openRawResource(R.raw.countries)
                .bufferedReader()
            jsonString = reader.use { it.readText() }
            reader.close()
            jsonString
        } catch (e: Exception) {
            InternalLogger.logE(TAG, "Unable to read country info", e)
            null
        }
    }

    fun loadCountryInfo(context: Context): List<CountryInfo>? {
        readCountryInfo(context)?.let {
            val countryDataClassType = object : TypeToken<List<CountryInfo>>() {}.type
            return try {
                val countryInfos = Gson().fromJson<List<CountryInfo>>(it, countryDataClassType)
                InternalLogger.logD(TAG, "Loaded CountryInfoList :" + countryInfos.size.toString())
                countryInfos
            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Unable to read country info list", e)
                null
            }
        }
        return null
    }

}
