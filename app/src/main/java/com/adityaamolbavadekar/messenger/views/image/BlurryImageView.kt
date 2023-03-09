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

package com.adityaamolbavadekar.messenger.views.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.roundToInt

class BlurryImageView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    var imageOnView: Drawable? = null
    var isBlurred = false

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        setImageDrawable(BitmapDrawable(resources, bm))
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        imageOnView = drawable
        setBlur(BLUR_RADIUS)
    }

    init {
        drawable?.let {
            imageOnView = drawable
            setBlur(BLUR_RADIUS)
        }
    }

    private fun setBlur(radius: Int) {
        if (imageOnView == null) imageOnView = drawable
        when (radius) {
            in (MIN_RADIUS + 1)..MAX_RADIUS -> {
                isBlurred = true
                val blurred = blurRenderScript((imageOnView as BitmapDrawable?)!!.bitmap, radius)
                setImageBitmap(blurred)
                invalidate()
            }
            else -> {
                isBlurred = false
                setImageDrawable(imageOnView)
                invalidate()
            }
        }
    }

    private fun blurRenderScript(smallBitmap: Bitmap, radius: Int): Bitmap {
        val width = (smallBitmap.width * BITMAP_SCALE).roundToInt()
        val height = (smallBitmap.height * BITMAP_SCALE).roundToInt()
        val inputBitmap = Bitmap.createScaledBitmap(smallBitmap, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)
        val renderScript = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        val tmpIn = Allocation.createFromBitmap(renderScript, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap)
        theIntrinsic.setRadius(radius.toFloat())
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }

    companion object {
        private const val BLUR_RADIUS = 10
        private const val BITMAP_SCALE = 0.1F
        private const val MAX_RADIUS = 25
        private const val MIN_RADIUS = 1
    }
}