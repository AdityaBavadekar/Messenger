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

@file:Suppress("DEPRECATION")

package com.adityaamolbavadekar.messenger.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.adityaamolbavadekar.messenger.utils.extensions.isNotNull
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ViewTarget

class ImageLoader private constructor(private val context: Context) {

    fun load(url: String, into: ImageView): ViewTarget<ImageView, Drawable> {
        return load(Uri.parse(url), into)
    }

    fun load(uri: Uri, into: ImageView): ViewTarget<ImageView, Drawable> {
        return load(uri, into, false, null, null)
    }

    fun load(url: String, into: ImageView, circleCrop: Boolean): ViewTarget<ImageView, Drawable> {
        return load(Uri.parse(url), into, circleCrop, null, null)
    }

    fun load(uri: Uri, into: ImageView, circleCrop: Boolean): ViewTarget<ImageView, Drawable> {
        return load(uri, into, circleCrop, null, null)
    }

    fun load(
        url: String,
        into: ImageView,
        placeholderInt: Int,
        circleCrop: Boolean = false
    ): ViewTarget<ImageView, Drawable> {
        return load(Uri.parse(url), into, circleCrop, placeholderInt, null)
    }

    fun load(
        uri: Uri,
        into: ImageView,
        placeholderInt: Int,
        circleCrop: Boolean = false
    ): ViewTarget<ImageView, Drawable> {
        return load(uri, into, circleCrop, placeholderInt, null)
    }

    fun load(
        url: String,
        into: ImageView,
        placeholderDrawable: Drawable?,
        circleCrop: Boolean = false
    ): ViewTarget<ImageView, Drawable> {
        return load(Uri.parse(url), into, circleCrop, null, placeholderDrawable)
    }

    fun load(
        uri: Uri,
        into: ImageView,
        placeholderDrawable: Drawable?,
        circleCrop: Boolean = false
    ): ViewTarget<ImageView, Drawable> {
        return load(uri, into, circleCrop, null, placeholderDrawable)
    }

    @SuppressLint("CheckResult")
    fun load(
        uri: Uri,
        into: ImageView,
        circleCrop: Boolean,
        placeholderInt: Int?,
        placeholderDrawable: Drawable?,
        overrideSize: Int? = null
    ): ViewTarget<ImageView, Drawable> {
        val builder = Glide.with(context).load(uri)
        builder.diskCacheStrategy(DiskCacheStrategy.ALL)
        overrideSize?.let { builder.override(it) }
        if (circleCrop) builder.circleCrop()
        if (placeholderDrawable.isNotNull()) builder.placeholder(placeholderDrawable!!)
        if (placeholderInt.isNotNull()) builder.placeholder(placeholderInt!!)
        return builder.into(into)
    }

    fun clear(v: ImageView?) {
        if (v == null) return
        v.setImageDrawable(null)
        return Glide.with(context).clear(v)
    }

    companion object {
        fun with(context: Context): ImageLoader {
            return ImageLoader(context)
        }

        fun with(view: View): ImageLoader {
            return with(view.context)
        }
    }

}