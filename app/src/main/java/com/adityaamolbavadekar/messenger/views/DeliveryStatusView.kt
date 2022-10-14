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

package com.adityaamolbavadekar.messenger.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.setPadding
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.model.DeliveryStatus

class DeliveryStatusView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attr, defStyle) {

    init {
        setPadding(8)
        setPending()
    }

    fun setPending() {
        setImageResource(ICON_PENDING)
    }

    fun setSent() {
        setImageResource(ICON_SENT)
    }

    fun setRead() {
        setImageResource(ICON_READ)
    }

    fun setReadByAll() {
        setImageResource(ICON_READ)
    }

    fun setStatus(deliveryStatus: Int) {
        when (deliveryStatus) {
            DeliveryStatus.SENT -> setSent()
            DeliveryStatus.READ -> setRead()
            else -> setPending()
        }
    }

    companion object {
        private const val ICON_PENDING = R.drawable.message_status_not_sent
        private const val ICON_SENT = R.drawable.message_status_saved
        private const val ICON_READ = R.drawable.message_status_read
    }
}