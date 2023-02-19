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

package com.adityaamolbavadekar.messenger.utils.startup

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

object AppForegroundObserver {
    private val listeners: MutableList<Listener> = mutableListOf()

    @Volatile
    private var isForegrounded: Boolean? = null

    @MainThread
    fun begin() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                onForeground()
            }

            override fun onStop(owner: LifecycleOwner) {
                onBackground()
            }
        })
    }

    /**
     * Adds a listener to be notified of when the app moves between the background and the foreground.
     * To mimic the behavior of subscribing to [ProcessLifecycleOwner], this listener will be
     * immediately notified of the foreground state if we've experienced a foreground/background event
     * already.
     */
    @AnyThread
    fun addListener(listener: Listener) {
        listeners.add(listener)
        isForegrounded?.let { foregrounded ->
            if (foregrounded) listener.onForeground()
            else listener.onBackground()
        }
    }

    @AnyThread
    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun isForegrounded(): Boolean {
        return (isForegrounded == true)
    }

    @MainThread
    private fun onForeground() {
        isForegrounded = true
        listeners.forEach { it.onForeground() }
    }

    @MainThread
    private fun onBackground() {
        isForegrounded = false
        listeners.forEach { it.onBackground() }
    }

    interface Listener {
        fun onForeground() {}
        fun onBackground() {}
    }
}