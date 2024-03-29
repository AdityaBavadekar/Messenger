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

package com.adityaamolbavadekar.messenger.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class WindowInsetsAwareConstraintLayout @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attr, defStyle) {

    private var lastInsets: WindowInsetsCompat? = null
    private var isImeVisible = false
    private var imeHeight = 0
    private var imeMaxHeight = 0
    private var imeStateListener: ImeStateListener = { _, _ -> }
    private var onInsetsChangedListener: OnInsetsChangedListener = { _ -> }

    init {
        setupForInsets()
    }

    private fun setupForInsets() {
        if (ViewCompat.getFitsSystemWindows(this)) {

            ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
                val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

                v.updatePadding(
                    top = systemBarInsets.top,
                    bottom = systemBarInsets.bottom,
                    left = systemBarInsets.left,
                    right = systemBarInsets.right
                )

                afterApplyWindowInsets(insets)

                WindowInsetsCompat.CONSUMED
            }

            // Now set the sys ui flags to enable us to lay out in the window insets
            systemUiVisibility = (SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        } else ViewCompat.setOnApplyWindowInsetsListener(this, null)
    }

    private fun afterApplyWindowInsets(insets: WindowInsetsCompat) {
        lastInsets = insets
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        isImeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
        imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        if (isImeVisible && imeMaxHeight < imeHeight) {
            imeMaxHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        }
        InternalLogger.logD(
            "View",
            "SystemBars=[$systemBars]"
        )
        imeStateListener(isImeVisible, imeHeight)
        onInsetsChangedListener(insets)
    }

    private fun areSame(insets: WindowInsetsCompat): Boolean {
        return (insets.systemWindowInsetBottom == lastInsets?.systemWindowInsetBottom &&
                insets.systemWindowInsetTop == lastInsets?.systemWindowInsetTop &&
                insets.systemWindowInsetLeft == lastInsets?.systemWindowInsetLeft &&
                insets.systemWindowInsetRight == lastInsets?.systemWindowInsetRight)
    }

    fun addOnImeStateListener(listener: ImeStateListener) {
        this.imeStateListener = listener
    }

    fun getLastWindowInsets(): WindowInsetsCompat? {
        return lastInsets
    }

    /**
     * @return Maximum height recorded using Ime bottom insets.
     * */
    fun getImeMaximumHeight(): Int {
        return imeMaxHeight
    }

    /**
     * @return Height recorded using Ime bottom insets.
     * */
    fun getImeHeight(): Int {
        return imeMaxHeight
    }

    /**
     * @return Whether Ime is visible.
     * */
    fun getImeIsVisible(): Boolean {
        return isImeVisible
    }

}

typealias ImeStateListener = (isVisible: Boolean, height: Int) -> Unit
typealias OnInsetsChangedListener = (insets: WindowInsetsCompat) -> Unit