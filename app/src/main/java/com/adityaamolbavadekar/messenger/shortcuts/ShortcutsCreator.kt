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

package com.adityaamolbavadekar.messenger.shortcuts

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.utils.Constants.EXTRA_CONVERSATION_ID
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class ShortcutsCreator {

    fun createForConversation(conversationRecord: ConversationRecord, context: Context) {
        val extras = PersistableBundle()
        extras.putString(EXTRA_CONVERSATION_ID, conversationRecord.conversationId)
        val shortcut = ShortcutInfoCompat.Builder(context, "1")
            .setShortLabel(conversationRecord.title)
            .setLongLabel(conversationRecord.title)
            .setIcon(getIcon(context, conversationRecord.defaultIcon()))
            .setIsConversation()
            .setIntent(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.my")
                )
            )
            .setExtras(extras)
        if (conversationRecord.hasPicture()) getIcon(context, conversationRecord.photoUrl!!) {
            shortcut.setIcon(it)
        }
        ShortcutManagerCompat.pushDynamicShortcut(context, shortcut.build())
    }

    fun createPinnedShortcut(conversationRecord: ConversationRecord, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            createPinForConversation(conversationRecord, context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun createPinForConversation(conversationRecord: ConversationRecord, context: Context) {
        val shortcutManager = context.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            shortcutManager.isRequestPinShortcutSupported
        ) {
            val extras = PersistableBundle()
            extras.putString(EXTRA_CONVERSATION_ID, conversationRecord.conversationId)
            val shortcut = ShortcutInfo.Builder(context, "1")
                .setShortLabel(conversationRecord.title)
                .setLongLabel(conversationRecord.title)
                .setIcon(getIcon(context, conversationRecord.defaultIcon()).toIcon(context))
                .setIntent(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.my")
                    )
                )
                .setExtras(extras)
            if (conversationRecord.hasPicture()) getIcon(context, conversationRecord.photoUrl!!) {
                shortcut.setIcon(it.toIcon(context))
            }
            try {
                shortcutManager.requestPinShortcut(shortcut.build(), null)
            } catch (e: Exception) {
            }
        } else return
    }

    private fun getIcon(context: Context, url: String, observer: (IconCompat) -> Unit) {
        download(context, url) {
            observer(IconCompat.createWithBitmap(it))
        }
    }

    private fun download(context: Context, url: String, observer: (Bitmap) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    observer(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun getIcon(context: Context, ic: Int): IconCompat {
        return IconCompat.createWithResource(context, ic)
    }

}