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

package com.adityaamolbavadekar.messenger.model

data class EmojiItemModel(
    val key: Int,
    val emoji: String,
    val emojiInt: Int = 0,
    val emojiCategory: Int = 0,
    val searchInfo: List<String>
) {

    constructor(emoji: String, key: Int) : this(key, emoji, 0, 0, listOf())

    fun isSameAs(x: EmojiItemModel): Boolean {
        return x.key == key && x.emoji == emoji
    }

    private fun emoji(data: Int): String {
        return String(Character.toChars(data))
    }

    fun getParsedEmoji(): String {
        return emoji(Integer.decode("0x$emoji"))
    }

}