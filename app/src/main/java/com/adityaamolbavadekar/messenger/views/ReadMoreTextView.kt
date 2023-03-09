package com.adityaamolbavadekar.messenger.views

import android.content.Context
import android.text.SpannedString
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import com.adityaamolbavadekar.messenger.R

class ReadMoreTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyle) {

    private var text: CharSequence? = null
    private var state: State = State.READ_MORE
    private var trimSize = DEFAULT_READ_MORE_LINES
    private var lineEndIndex = 0
    private val readMoreText = context.getString(R.string.read_more)

    init {
        onGlobalLayoutLineEndIndex()
        internalSetText()
    }

    fun isInReadMore() = state == State.READ_MORE

    fun toggleReadMore() {
        state = when (state) {
            State.READ_MORE -> State.NORMAL
            State.NORMAL -> State.READ_MORE
        }
        internalSetText()
    }

    fun setTrimLines(lines: Int) {
        trimSize = lines
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        this.text = text
        internalSetText()
    }

    private fun internalSetText() {
        super.setText(getSpannedText(), BufferType.SPANNABLE)
    }

    private fun getSpannedText(): SpannedString {
        return when (state) {
            State.READ_MORE -> {
                if (lineCount > trimSize) addReadMoreFunctionality()
                else buildSpannedString { append(text) }
            }
            State.NORMAL -> buildSpannedString { append(text) }
        }
    }

    private fun addReadMoreFunctionality(): SpannedString {
        val trimLength = lineEndIndex - (ELLIPSE.length + readMoreText.length)
        return buildSpannedString {
            append(text, 0, trimLength)
            inSpans(ReadMoreSpan()) {
                append(ELLIPSE + readMoreText)
            }
        }
    }

    private fun onGlobalLayoutLineEndIndex() {
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    private val onGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            refreshLineIndexData()
            internalSetText()
        }
    }

    private fun refreshLineIndexData() {
        try {
            if (lineCount >= trimSize) {
                lineEndIndex = layout.getLineEnd(trimSize - 1)
            }
        } catch (_: Exception) {
        }
    }

    enum class State { READ_MORE, NORMAL }

    companion object {
        private const val DEFAULT_READ_MORE_LINES = 25
        private const val ELLIPSE = "... "
    }

    inner class ReadMoreSpan : ClickableSpan() {
        override fun onClick(p0: View) = toggleReadMore()
    }

}