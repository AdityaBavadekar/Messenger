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
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.withStyledAttributes
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.bumptech.glide.Glide

class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.style.Messenger_Widget_AvatarImageView_Default
) : AppCompatImageView(context, attrs, defStyle) {

    private var radius: Int = 0
    private var elevation: Int = 0
    private var avatarImageViewBackgroundColor: ColorStateList? = null
    private val impl: ImageViewApiImpl = ImageViewApiImpl()

    init {
        impl.create()
        context.withStyledAttributes(attrs, R.styleable.AvatarImageView) {
            radius = getDimension(R.styleable.AvatarImageView_cornerRadius, 0F).toInt()
            elevation = getDimension(R.styleable.AvatarImageView_elevation, 0F).toInt()
            avatarImageViewBackgroundColor =
                getColorStateList(R.styleable.AvatarImageView_imageBackgroundColor)
            if (avatarImageViewBackgroundColor == null) {
                avatarImageViewBackgroundColor = ColorStateList.valueOf(Color.TRANSPARENT)
            }
        }

        impl.initialise(this, avatarImageViewBackgroundColor!!, radius, elevation)

    }

    fun loadImage(url: String?) {
        if (url == null) return
        load(Uri.parse(url))
    }

    fun loadImage(uri: Uri?) {
        if (uri == null) return
        load(uri)
    }

    fun clear() {
        Glide.with(context).clear(this)
    }


    private class ImageViewApiImpl {

        fun initialise(
            view: View,
            backgroundColor: ColorStateList,
            radius: Int,
            elevation: Int,
        ) {
            val background = RoundRectDrawable(
                backgroundColor,
                radius.toFloat()
            )
            view.background = (background)
            view.clipToOutline = true
            view.elevation = elevation.toFloat()
        }

        fun create() {}
    }

}