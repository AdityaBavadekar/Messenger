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

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    interface KeyboardVisibilityListener {
        fun onChanged(isVisible: Boolean)
    }

    interface OnKeyboardStateListener {
        fun onChanged(isVisible: Boolean, keyboardHeight: Int)
    }

    fun addKeyboardVisibilityListener(activity: Activity, listener: KeyboardVisibilityListener) {

        val rootView = getRootView(activity)
        var lastState = false
        val layoutChangeListener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = getIsKeyboardOpen(activity)
            if (lastState == isKeyboardOpen) return@OnGlobalLayoutListener
            lastState = isKeyboardOpen
            listener.onChanged(isKeyboardOpen)
        }

        rootView.viewTreeObserver.addOnGlobalLayoutListener(layoutChangeListener)

    }

    fun addKeyboardStateListener(activity: Activity, listener: OnKeyboardStateListener) {

        val rootView = getRootView(activity)
        var lastState = false

        var rect = Rect()

        val defaultKeyboardHeightInDp = 100
        val estimatedKeyboardHeightInDp: Int = defaultKeyboardHeightInDp +
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) 48 else 0

        val layoutChangeListener = ViewTreeObserver.OnGlobalLayoutListener {
            val estimatedKeyboardHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                estimatedKeyboardHeightInDp.toFloat(), rootView.resources.displayMetrics
            )

            rootView.getWindowVisibleDisplayFrame(rect)

            val heightDiff = rootView.rootView.height - (rect.bottom - rect.top)
            val isShown = heightDiff >= estimatedKeyboardHeight

            if (lastState == isShown) return@OnGlobalLayoutListener

            lastState = isShown

            listener.onChanged(isShown, heightDiff)

        }
        rootView.viewTreeObserver.addOnGlobalLayoutListener(layoutChangeListener)

    }

    private fun getIsKeyboardOpen(activity: Activity): Boolean {

        val r = Rect()

        val root = getRootView(activity)

        root.getWindowVisibleDisplayFrame(r)

        val location = IntArray(2)

        getRootContentView(activity).getLocationOnScreen(location)

        val screenHeight = root.rootView.height
        val heightDiff = screenHeight - r.height() - location[1]

        return heightDiff > screenHeight * 0.15
    }

    private fun getRootView(activity: Activity): View {
        return activity.findViewById<ViewGroup>(android.R.id.content).rootView
    }

    private fun getRootContentView(activity: Activity): ViewGroup {
        return activity.findViewById(android.R.id.content)
    }

    fun showKeyboard(activity: Activity) {
        getInputService(activity).showSoftInput(
            getRootContentView(activity),
            InputMethodManager.SHOW_FORCED
        )
    }

    fun hideKeyboard(activity: Activity) {
        getInputService(activity).hideSoftInputFromWindow(
            activity.window.decorView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    fun getInputService(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

}