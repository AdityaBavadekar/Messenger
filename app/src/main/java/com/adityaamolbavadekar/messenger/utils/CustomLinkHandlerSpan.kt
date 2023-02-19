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

package com.adityaamolbavadekar.messenger.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcel
import android.provider.Browser
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.TextView

typealias OnLinkClickListener = (context: Context, url: String) -> Unit

class CustomLinkHandlerSpan(urlString: String) : ClickableSpan() {

    private var url: String? = null
    private var onClick: OnLinkClickListener? = null

    init {
        url = urlString
    }

    constructor(parcel: Parcel) : this(urlString = parcel.readString()!!)

    constructor(urlString: String, onClicked: OnLinkClickListener) : this(urlString) {
        setOnClickListener(onClicked)
    }

    fun getUrlString(): String? = url

    fun setOnClickListener(onClickListener: OnLinkClickListener) {
        onClick = onClickListener
    }

    override fun onClick(v: View) {
        if (url == null) return

        if (onClick != null) onClick!!(v.context, url!!)
        else openBrowser(v.context, url!!)
    }

    companion object {
        fun applySpans(string: String, onClick: OnLinkClickListener): SpannableStringBuilder {
            val spannableString = SpannableStringBuilder(string)
            val matcher = Patterns.WEB_URL.matcher(string)
            try {
                while (matcher.find()) {
                    val url = string.substring(matcher.start(0), matcher.end(0))
                    spannableString.setSpan(
                        CustomLinkHandlerSpan(url, onClick),
                        matcher.start(0),
                        matcher.end(0),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            } catch (e: Exception) {
            }
            return spannableString
        }


        fun openBrowser(context: Context, url: String) {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.packageName)
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.w("CustomLinkHandlerSpan", "Activity was not found for intent, $intent")
            }
        }

        fun ofSpan(textView: TextView, span: ClickableSpan) {
            val s = textView.text as Spanned

        }

        private const val URL_SPAN_ID = 1011
    }

}