package com.adityaamolbavadekar.messenger.utils

import androidx.annotation.AttrRes

interface ColorsUtility {
    fun getPrimaryColor(): Int
    fun getPrimaryContainerColor(): Int
    fun getSurfaceColor(): Int
    fun getThemeMainColor(): Int
    fun getOppositeColor(): Int

    fun getColorForAttribute(@AttrRes attr: Int): Int
    fun applyAlpha(color: Int, alpha: Int): Int
    /*
    fun getMessageTitleColor():Int
    fun getMessageTextColor():Int
    fun getMessageTimestampColor():Int
*/
}