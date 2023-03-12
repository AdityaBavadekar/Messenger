package com.adityaamolbavadekar.messenger.views.compose

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.KeyboardUtils
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger


class EmojiKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.Messenger_Widget_LinkPreviewView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), KeyboardUtils.CustomKeyboard {

    private var listener: Listener? = null
    private var emojiEnteredListener: (String) -> Unit = { }
    private var isInitialised: Boolean = false
    private var latestKeyboardHeight: Int = 0
    private var keyboardFragment: KeyboardFragment? = null
    private var fragmentManager: FragmentManager? = null
    override val isShowing: Boolean
        get() = visibility == VISIBLE

    fun setListener(l: Listener) {
        this.listener = l
    }

    fun setEmojiEnteredListener(l: (String) -> Unit) {
        emojiEnteredListener = l
    }

    override fun show(height: Int) {
        initIfNeeded()
        latestKeyboardHeight = height
        val params: ViewGroup.LayoutParams = layoutParams
        params.height = latestKeyboardHeight
        layoutParams = params
        show()
    }

    override fun show() {
        initIfNeeded()
        InternalLogger.logD(TAG, "show()")
        visibility = VISIBLE
        listener?.onShown()
        keyboardFragment?.show();
    }

    override fun hide() {
        initIfNeeded()
        InternalLogger.logD(TAG, "hide()")
        visibility = GONE
        listener?.onHidden();
        keyboardFragment?.hide();
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (fragmentManager != null && isInitialised && keyboardFragment != null) {
            fragmentManager!!.beginTransaction()
                .replace(R.id.emoji_keyboard_fragment_container, keyboardFragment!!, TAG)
                .commitNowAllowingStateLoss()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (fragmentManager != null && isInitialised && keyboardFragment != null) {
            fragmentManager!!.beginTransaction()
                .remove(keyboardFragment!!)
                .commitNowAllowingStateLoss()
        }
    }

    private fun initIfNeeded() {
        if (!isInitialised) {
            initialiseAndSetup()
        }
    }

    private fun initialiseAndSetup() {
        InternalLogger.logD(TAG, "Initialising...")
        LayoutInflater.from(context).inflate(R.layout.emoji_keyboard, this, true);

        if (fragmentManager == null) {
            val activity = resolveActivity(context)
            fragmentManager = activity.supportFragmentManager
        }

        keyboardFragment = KeyboardFragment(emojiEnteredListener)
        fragmentManager!!.beginTransaction()
            .replace(R.id.emoji_keyboard_fragment_container, keyboardFragment!!, TAG)
            .commitNowAllowingStateLoss()

        latestKeyboardHeight = -1
        isInitialised = true

    }

    private fun resolveActivity(context: Context): FragmentActivity {
        return when (context) {
            is FragmentActivity -> context
            is ContextThemeWrapper -> resolveActivity(context.baseContext)
            else -> throw IllegalStateException("Could not locate FragmentActivity")
        }
    }

    interface Listener {
        fun onShown()
        fun onHidden()
    }

    companion object {
        private val TAG = EmojiKeyboard::class.java.simpleName
    }

}