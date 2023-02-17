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

package com.adityaamolbavadekar.messenger.utils.shake

import android.content.Context
import com.adityaamolbavadekar.messenger.ui.ShakePresenter
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

/**
 * Detects phone shaking. If more than 75% of the samples taken in the past 0.5s are
 * accelerating, the device is a) shaking, or b) free falling 1.84m (h =
 * 1/2*g*t^2*3/4).
 *
 * @author Bob Lee (bob@squareup.com)
 * @author Eric Burke (eric@squareup.com)
 */
object AppShakeDetector : ShakeDetector.Listener {

    private var context: Context? = null
    private var detector: ShakeDetector? = null
    fun register(c: Context): AppShakeDetector {
        context = c
        detector = ShakeDetector(this)
        return this
    }

    fun start() {
        context?.let { detector?.start(it) }
    }

    fun stop() {
        detector?.stop()
    }

    override fun onShakeDetected() {
        if (context == null) {
            InternalLogger.logW(TAG, "Context is null. Shake will be ignored.")
            return
        }

        ShakePresenter.start(context!!)

    }

    private const val TAG = "AppShakeDetector"

}

