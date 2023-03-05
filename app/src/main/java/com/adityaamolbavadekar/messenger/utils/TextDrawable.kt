package com.adityaamolbavadekar.messenger.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape

class TextDrawable(
    private val text: String? = null,
    private val textColor: Int = Color.WHITE,
    private val color: Int = Color.BLUE,
    private val shape: RectShape = OvalShape(),
    private val width: Int = 0,
    private val height: Int = 0,
    private val textSize: Int = 0,
) : ShapeDrawable(shape) {

    private val textPaint: Paint = Paint()
    private val borderPaint: Paint = Paint()

    init {
        textPaint.color = textColor
        textPaint.isAntiAlias = true
        textPaint.isFakeBoldText = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.strokeWidth = 0F
        textPaint.typeface = Typeface.DEFAULT_BOLD
        paint.color = color
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val r = bounds
        val count = canvas.save()
        canvas.translate(r.left.toFloat(), r.top.toFloat())
        val fontSize = if (textSize < 1) width.coerceAtMost(height) / 2 else textSize
        textPaint.textSize = fontSize.toFloat()
        text?.let {
            canvas.drawText(
                it,
                (width / 2).toFloat(),
                height / 2 - ((textPaint.descent() + textPaint.ascent()) / 2),
                textPaint
            )
        }
        canvas.restoreToCount(count)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    class Builder {

        private var text: String? = null
        private var textColor: Int = Color.WHITE
        private var color: Int = Color.BLUE
        private var shape: RectShape = OvalShape()
        private var width: Int = 0
        private var height: Int = 0
        private var textSize: Int = 0

        fun setText(text: String): Builder {
            this.text = text
            return this
        }

        fun size(width: Int, height: Int): Builder {
            this.width = width
            this.height = height
            return this
        }

        fun size(all: Int): Builder {
            this.width = all
            this.height = all
            return this
        }

        fun textSize(sizeInSp: Int): Builder {
            this.textSize = sizeInSp
            return this
        }

        fun color(color: Int): Builder {
            this.color = color
            return this
        }

        fun textColor(color: Int): Builder {
            this.textColor = color
            return this
        }

        fun getRoundDrawable(): TextDrawable {
            this.shape = OvalShape()
            return build()
        }

        fun getSquareDrawable(): TextDrawable {
            this.shape = RectShape()
            return build()
        }

        fun createDefaultWithText(text: String,context: Context): TextDrawable {
            size(AndroidUtils.toDP(24,context))
            this.text = text
            return getRoundDrawable()
        }

        private fun build(): TextDrawable {
            return TextDrawable(text, textColor, color, shape, width, height, textSize)
        }

    }

}