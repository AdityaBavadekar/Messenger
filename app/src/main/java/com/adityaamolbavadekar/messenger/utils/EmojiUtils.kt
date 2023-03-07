package com.adityaamolbavadekar.messenger.utils

import android.content.Context
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.model.Emoji
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmojiUtils private constructor() {
    companion object {
        private val TAG = EmojiUtils::class.java.simpleName

        fun loadEmojiData(context: Context): List<Emoji>? {
            getEmojis(context)?.let {
                val emojiDataClassType = object : TypeToken<List<Emoji>>() {}.type
                return try {
                    val emojiList = Gson().fromJson<List<Emoji>>(it, emojiDataClassType)
                    InternalLogger.logD(
                        TAG,
                        "Loaded EmojiList :" + emojiList.size.toString()
                    )
                    emojiList
                } catch (e: Exception) {
                    InternalLogger.logE(TAG, "Unable to read emoji list", e)
                    null
                }
            }
            return null
        }

        private fun getEmojis(context: Context): String? {
            val jsonString: String
            return try {
                val reader = context.resources
                    .openRawResource(R.raw.emoji_dataset)
                    .bufferedReader()
                jsonString = reader.use { it.readText() }
                reader.close()
                jsonString
            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Unable to read emojis", e)
                null
            }
        }

    }
}