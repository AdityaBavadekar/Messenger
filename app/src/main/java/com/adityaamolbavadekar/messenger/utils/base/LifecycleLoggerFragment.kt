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

package com.adityaamolbavadekar.messenger.utils.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

open class LifecycleLoggerFragment : Fragment() {

    override fun onAttach(context: Context) {
        logEvent(ON_ATTACH)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        logEvent(ON_CREATE)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logEvent(ON_VIEW_CREATED)
    }

    override fun onStart() {
        logEvent(ON_START)
        super.onStart()
    }

    override fun onResume() {
        logEvent(ON_RESUME)
        super.onResume()
    }

    override fun onPause() {
        logEvent(ON_PAUSE)
        super.onPause()
    }

    override fun onStop() {
        logEvent(ON_STOP)
        super.onStop()
    }

    override fun onDestroy() {
        logEvent(ON_DESTROY)
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logEvent("${ON_SAVED_INSTANCE_STATE}($outState)")
    }

    private fun logEvent(methodName: String) {
        InternalLogger.logD(javaClass.simpleName, methodName)
    }

}