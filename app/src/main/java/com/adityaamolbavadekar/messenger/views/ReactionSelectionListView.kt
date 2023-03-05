package com.adityaamolbavadekar.messenger.views

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityaamolbavadekar.messenger.utils.AndroidUtils

class ReactionSelectionListView @JvmOverloads constructor(
    context: Context,
    defStyleAttr: Int = 0,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs, defStyleAttr) {

    private val linearLayoutManager = LinearLayoutManager(
        context,
        /*orientation*/ LinearLayoutManager.HORIZONTAL,
        /*reverseLayout*/true
    )

    init {
        background = null
        layoutManager = linearLayoutManager
        setHasFixedSize(false)
        clipChildren = false
        clipToPadding = false
        updateLayoutParams<LayoutParams> {
            height = AndroidUtils.toDP(56, context)
            width = LayoutParams.MATCH_PARENT
        }
    }

}