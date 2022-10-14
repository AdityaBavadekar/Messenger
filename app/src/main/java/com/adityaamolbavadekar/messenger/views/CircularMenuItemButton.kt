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

package com.adityaamolbavadekar.messenger.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.adityaamolbavadekar.messenger.R

class CircularMenuItemButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val buttonContainer: FrameLayout
    val button: ImageView
    private val badgeView: ImageView
    private var buttonSize: Int = 0
    private var drawable: Drawable? = null
    private var iconTint: ColorStateList? = null
    private var containerColor: Int? = null
    private var description: String? = null

    init {
        val inflatedLayout = inflate(context, R.layout.view_circular_menu_item_button, this)
        buttonContainer = inflatedLayout.findViewById(R.id.buttonContainer)
        button = inflatedLayout.findViewById(R.id.button)
        badgeView = inflatedLayout.findViewById(R.id.badge_view)
        buttonSize = button.height
        context.withStyledAttributes(attrs, R.styleable.CircularMenuItemButton) {
            drawable = getDrawable(R.styleable.CircularMenuItemButton_android_src)
            iconTint = getColorStateList(R.styleable.CircularMenuItemButton_iconTint)
            containerColor = getColor(
                R.styleable.CircularMenuItemButton_containerColor,
                Color.TRANSPARENT
            )
            description = getString(R.styleable.CircularMenuItemButton_imageContentDescription)
            internalSetBadgeVisibility(
                getBoolean(
                    R.styleable.CircularMenuItemButton_badgeVisible,
                    false
                )
            )
        }

        button.setImageDrawable(drawable)
        containerColor?.let { buttonContainer.setBackgroundColor(it) }
        iconTint?.let { button.imageTintList = (it) }
        setOnButtonClickListener()
    }

    private fun setOnButtonClickListener() {
        this.setOnClickListener { }
        this.setOnLongClickListener { true }
    }

    fun setImageContentDescription(string: String) {
        button.contentDescription = string
    }

    fun setButtonImageRes(@DrawableRes int: Int) {
        button.setImageResource(int)
    }

    fun setButtonTintList(tintList: ColorStateList) {
        button.imageTintList = tintList
    }

    fun isBadgeVisible(): Boolean {
        return badgeView.isVisible
    }

    private fun internalSetBadgeVisibility(visible: Boolean) {
        if (!visible) badgeView.isGone = true
        else badgeView.isVisible = true
    }

    fun showBadge() {
        badgeView.isVisible = true
    }

    fun hideBadge() {
        badgeView.isGone = true
    }

    fun tooltipText(textInt: Int) {
        TooltipCompat.setTooltipText(this, context.getString(textInt))
    }

}