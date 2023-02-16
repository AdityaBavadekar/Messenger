package com.adityaamolbavadekar.messenger.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardUtil(private val context: Context) {

    fun clip(text:String){
        return clip(text,"text")
    }

    fun clip(text:String,label:String){
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(label,text))
    }

    companion object{
        fun with(context: Context): ClipboardUtil {
            return ClipboardUtil(context)
        }
    }

}