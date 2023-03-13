package com.adityaamolbavadekar.messenger.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.views.image.RoundedBackgroundDrawable

class MessageItemBodyLayout @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attr, defStyle) {

    private val cornerRadius: Float

    init {
        cornerRadius = context.resources.getDimension(R.dimen.message_corner_radius)
        val roundedBackgroundDrawable = RoundedBackgroundDrawable(ColorStateList.valueOf(Color.TRANSPARENT),cornerRadius)
        background = roundedBackgroundDrawable
        clipToOutline = true;
        elevation = elevation;
    }

}