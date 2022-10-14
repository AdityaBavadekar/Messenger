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

package com.adityaamolbavadekar.messenger.utils.extensions

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.TooltipCompat
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.Constants.TimestampFormats.MESSAGE_TIMESTAMP_FORMAT
import com.adityaamolbavadekar.messenger.utils.Constants.TimestampFormats.SLASHED_TIMESTAMP_FORMAT_FULL
import com.adityaamolbavadekar.messenger.utils.Constants.TimestampFormats.TIMESTAMP_FORMAT_DAY_MONTH
import com.adityaamolbavadekar.messenger.utils.Constants.TimestampFormats.TIMESTAMP_FORMAT_DAY_MONTH_YEAR
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationBarView
import java.text.SimpleDateFormat
import java.util.*

fun String.extractUrls(): List<String> {
    val string = this
    val urls = mutableListOf<String>()
    val matcher = Patterns.WEB_URL.matcher(string)
    try {
        while (matcher.find()) {
            urls.add(string.substring(matcher.start(0), matcher.end(0)))
        }
    } catch (e: Exception) {
    }
    return urls.toList()
}

val String.containsUrls: Boolean
    get() {
        return Patterns.WEB_URL.matcher(this).find()
    }

fun Context.toast(resId: Int) {
    return toast { getString(resId) }
}

fun Context.toast(m: () -> String) {
    val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
    toast.setText(m())
    toast.show()
}

fun Toast?.showText(resId: Int) {
    this?.let {
        //Cancel toast then show again
        it.cancel()
        it.setText(resId)
        it.show()
    }
}

fun Toast?.showText(string: () -> String) {
    this?.let {
        it.setText(string())
        it.show()
    }
}

fun ImageView.load(
    uri: Uri,
    circleCrop: Boolean = true,
    error: Int? = null,
    placeholder: Int? = null
) {
    val b = Glide.with(context).load(uri)
    if (circleCrop) b.circleCrop()
    error?.let {
        b.error(error)
        if (placeholder == null) b.placeholder(error)
        else b.placeholder(placeholder)
    }
    b.into(this)
}

fun getDate(milis: Long, context: Context? = null): String {
    val m = Calendar.getInstance()
    m.timeInMillis = milis
    val cal = Calendar.getInstance()
    val dateFormat: String
    if (cal[Calendar.YEAR] == m[Calendar.YEAR]) {
        dateFormat = TIMESTAMP_FORMAT_DAY_MONTH
        //Return DD MMMM  [2]
        if (cal[Calendar.DAY_OF_MONTH] == m[Calendar.DAY_OF_MONTH]) {
            return context?.getString(R.string.today) ?: "Today"
            //Return Today
        } else if (cal[Calendar.DAY_OF_MONTH] - 1 == m[Calendar.DAY_OF_MONTH]) {
            return context?.getString(R.string.yesterday) ?: "Yesterday"
            //Return Yesterday
        }
    } else {
        dateFormat = TIMESTAMP_FORMAT_DAY_MONTH_YEAR
        //Return DD MM YYYY
    }
    return simpleDateFormat(milis, dateFormat)
}

fun getDateForConversationListLastUpdated(milis: Long, context: Context? = null): String {
    val m = Calendar.getInstance()
    m.timeInMillis = milis
    val cal = Calendar.getInstance()
    val dateFormat: String
    if (cal[Calendar.YEAR] == m[Calendar.YEAR]) {
        dateFormat = MESSAGE_TIMESTAMP_FORMAT
        if (cal[Calendar.DAY_OF_MONTH] - 1 == m[Calendar.DAY_OF_MONTH]) {
            return context?.getString(R.string.yesterday) ?: "Yesterday"
            //Return Yesterday
        }
    } else {
        dateFormat = SLASHED_TIMESTAMP_FORMAT_FULL
    }
    return simpleDateFormat(milis, dateFormat)
}

fun getDateStub(milis: Long): String {
    val m = Calendar.getInstance()
    m.timeInMillis = milis
    val cal = Calendar.getInstance()
    return if (cal[Calendar.YEAR] == m[Calendar.YEAR]) {
        when {
            cal[Calendar.DAY_OF_MONTH] == m[Calendar.DAY_OF_MONTH] -> "T"
            cal[Calendar.DAY_OF_MONTH] - 1 == m[Calendar.DAY_OF_MONTH] -> "Y"
            else -> simpleDateFormat(milis, TIMESTAMP_FORMAT_DAY_MONTH)
        }
    } else simpleDateFormat(milis, TIMESTAMP_FORMAT_DAY_MONTH_YEAR)
}

fun simpleDateFormat(long: Long, format: String = TIMESTAMP_FORMAT_DAY_MONTH_YEAR): String {
    return SimpleDateFormat(format, Locale.ENGLISH).format(Date(long))
}

fun NavigationBarView.disableTooltip() {
    setOnLongClickListener { true }
    val menuSize = menu.size()
    for (menuItemIndex in menuSize until menuSize - 1) {
        TooltipCompat.setTooltipText(menu.getItem(menuItemIndex).actionView, null)
    }
}

fun View.setOnApplyWindowInsetsPaddingListener(
    topInsetsView: View? = null, bottomInsetsView: View? = null,
    afterUpdating: (WindowInsetsCompat) -> Unit = { _ -> }
) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        var topConsumed = false
        var bottomConsumed = false

        if (topInsetsView != null) {
            topInsetsView.updatePadding(top = systemBarInsets.top)
            topConsumed = true
        }

        if (bottomInsetsView != null) {
            bottomInsetsView.updatePadding(bottom = systemBarInsets.bottom)
            bottomConsumed = true
        }

        this.updatePadding(
            top = if (topConsumed) this.paddingTop else this.paddingTop + systemBarInsets.top,
            bottom = if (bottomConsumed) this.paddingBottom else this.paddingBottom + systemBarInsets.bottom
        )

        afterUpdating(insets)

        WindowInsetsCompat.Builder(insets)
            .setInsets(
                WindowInsetsCompat.Type.systemBars(),
                Insets.of(
                    /*left*/systemBarInsets.left,
                    /*top*/0,
                    /*right*/systemBarInsets.right,
                    /*bottom*/0
                )
            )
            .build()
    }
}