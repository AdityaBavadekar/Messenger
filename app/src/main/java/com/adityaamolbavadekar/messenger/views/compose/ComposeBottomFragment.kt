/*
 *
 *    Copyright 2022 Aditya Bavadekar
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
 *
 */

package com.adityaamolbavadekar.messenger.views.compose

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.adityaamolbavadekar.messenger.utils.base.BaseFragment

abstract class ComposeBottomFragment(height: Int = 0) : BaseFragment() {

    private var fragmentHeight : Int = height

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateHeight(view)
    }

    private fun updateHeight(v: View) {
        val windowHeight = if (fragmentHeight != 0) fragmentHeight
        else ViewGroup.LayoutParams.MATCH_PARENT
        v.updateLayoutParams<ViewGroup.LayoutParams> {
            this.height = windowHeight
        }
    }


}