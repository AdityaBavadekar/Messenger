package com.adityaamolbavadekar.messenger.model

data class EmojiItem(
    val hexCode: String,
    val emojiContent: String,
    val category: Int,
    val description: String,
    val isRange: Boolean
)
