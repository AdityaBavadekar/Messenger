package com.adityaamolbavadekar.messenger.utils

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewStub
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class ViewUtil private constructor() {

    companion object {

        fun <T : View> findStubById(@IdRes id: Int, parent: View): Stub<T> {
            return Stub(parent.findViewById(id))
        }

        @SuppressLint("InternalInsetResource")
        fun getStatusBarHeight(view: View): Int {
            val rootWindowInsets = ViewCompat.getRootWindowInsets(view)
            return if (Build.VERSION.SDK_INT > 29 && rootWindowInsets != null) {
                rootWindowInsets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            } else {
                var result = 0
                val resourceId =
                    view.resources.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    result = view.resources.getDimensionPixelSize(resourceId)
                }
                result
            }
        }

        @SuppressLint("DiscouragedApi")
        fun getNavigationBarHeight(view: View): Int {
            val rootWindowInsets = ViewCompat.getRootWindowInsets(view)
            return if (Build.VERSION.SDK_INT > 29 && rootWindowInsets != null) {
                rootWindowInsets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            } else {
                var result = 0
                val resourceId =
                    view.resources.getIdentifier("navigation_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    result = view.resources.getDimensionPixelSize(resourceId)
                }
                result
            }
        }

    }

    @Suppress("UNCHECKED_CAST")
    class Stub<T : View>(private var viewStub: ViewStub?) {
        private var view: T? = null
        fun get(): T {
            if (view == null) {
                view = viewStub!!.inflate() as T
                viewStub = null
            }
            return view!!
        }

        private val isViewInstantiated: Boolean
            get() = view != null

        val visibility: Int
            get() = view?.visibility ?: View.GONE

        fun setVisibility(visibility: Int) {
            if (isViewInstantiated || visibility == View.VISIBLE) {
                view!!.visibility = visibility
            }
        }

    }

}