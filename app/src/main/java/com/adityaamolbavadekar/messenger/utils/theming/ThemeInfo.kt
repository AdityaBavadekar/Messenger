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

package com.adityaamolbavadekar.messenger.utils.theming

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import com.adityaamolbavadekar.messenger.constants.PreferenceKeys
import com.adityaamolbavadekar.messenger.managers.PrefsManager.Companion.prefs
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

enum class ThemeInfo(val id: Int, private val nightMode: Int) {

    SystemDefault(0, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    Light(1, AppCompatDelegate.MODE_NIGHT_NO),
    Dark(2, AppCompatDelegate.MODE_NIGHT_YES);

    fun apply() {
        InternalLogger.logD(TAG, name)
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    fun save(context: Context) {
        InternalLogger.logD(TAG, name)
        context.prefs.edit {
            putInt(PreferenceKeys.APP_THEME, id)
        }
    }

    fun apply(activity: FragmentActivity) {
        activity.recreate()
    }

    companion object {
        private val TAG = ThemeInfo::class.java.simpleName
        fun Context.getPreferredThemeInfo(): ThemeInfo {
            return when (prefs.getInt(PreferenceKeys.APP_THEME, SystemDefault.id)) {
                Light.id -> Light
                Dark.id -> Dark
                else -> SystemDefault
            }
        }

        fun valueOf(id: Int): ThemeInfo {
            return when (id) {
                Light.id -> Light
                Dark.id -> Dark
                else -> SystemDefault
            }
        }

        fun isDark(context: Context): Boolean {
            return context.getPreferredThemeInfo() == Dark
        }

        fun isLight(context: Context): Boolean {
            return context.getPreferredThemeInfo() == Light
        }

        fun isSystem(context: Context): Boolean {
            return context.getPreferredThemeInfo() == SystemDefault
        }

    }
}