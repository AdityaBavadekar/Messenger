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

import android.content.Context
import androidx.core.content.edit
import com.adityaamolbavadekar.messenger.constants.PreferenceKeys
import com.adityaamolbavadekar.messenger.managers.PrefsManager.Companion.prefs
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class ApplicationStateUtils(private val context: Context) {

    fun update(state: ApplicationState) = internalSave(state)
    fun get() = internalGet()

    private fun internalSave(state: ApplicationState) {
        InternalLogger.logD(TAG, "internalSave(${state.name})")
        context.prefs.edit {
            putInt(PreferenceKeys.APP_STATE, state.ordinal)
        }
        return
    }

    private fun internalGet(): ApplicationState {
        val state =
            valueOrDefault(context.prefs.getInt(PreferenceKeys.APP_STATE, defaultState.ordinal))
        InternalLogger.logD(TAG, "internalGet()=${state.name}")
        return state
    }

    enum class ApplicationState {
        STATE_FRESH_INSTALL,
        STATE_REGISTRATION,
        STATE_SETUP_PROFILE,
        STATE_NORMAL;

        companion object {
            fun getUnauthenticatedStateValues() =
                listOf(STATE_FRESH_INSTALL, STATE_REGISTRATION, STATE_SETUP_PROFILE)
        }
    }

    private fun valueOrDefault(state: Int): ApplicationState {
        val indexes = ApplicationState.values().map { it.ordinal }
        var providedStateIndex = indexes.indexOf(state)
        if (providedStateIndex == -1) {
            providedStateIndex = defaultState.ordinal
        }
        return ApplicationState.values()[providedStateIndex]
    }

    private val defaultState = ApplicationState.STATE_FRESH_INSTALL

    companion object {
        private val TAG =ApplicationStateUtils::class.java.simpleName
    }

}