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

package com.adityaamolbavadekar.messenger.utils

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils

class PhoneNumberEditTextFilter : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {

        if (source == null || !TextUtils.isDigitsOnly(source) || source.trim().isEmpty()) {
            return ""
        }

        source.forEachIndexed { index, c ->
            if (!c.isDigit()) {
                return if (index == 0) ""
                else source.subSequence(0 until index)
            }
        }

        return source
    }

}