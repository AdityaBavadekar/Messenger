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

package com.adityaamolbavadekar.messenger.utils;

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import com.adityaamolbavadekar.messenger.R

class ColorUtils(private val context: Context) : ColorsUtility, Color() {

    override fun getPrimaryColor(): Int {
        return getColorForAttribute(R.attr.colorPrimary)
    }

    override fun getSurfaceColor(): Int {
        return getColorForAttribute(R.attr.colorSurface)
    }

    override fun getThemeMainColor(): Int {
        return context.resources.getColor(R.color.colorThemeBasic)
    }

    override fun getOppositeColor(): Int {
        return context.resources.getColor(R.color.colorThemeOpposite)
    }

    override fun applyAlpha(color: Int, alpha: Int): Int {
        return androidx.core.graphics.ColorUtils.setAlphaComponent(color, alpha)
    }

    override fun getColorForAttribute(@AttrRes attr: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        return ContextCompat.getColor(context, typedValue.resourceId)
    }

}

