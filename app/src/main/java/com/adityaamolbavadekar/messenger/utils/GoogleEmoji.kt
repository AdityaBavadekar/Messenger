package com.adityaamolbavadekar.messenger.utils

class GoogleEmoji(
  val unicode: String,
  val shortcodes: List<String>,
  val x: Int,
  val y: Int,
  val isDuplicate: Boolean,
  val variants: List<GoogleEmoji> = emptyList(),
  private var parent: GoogleEmoji? = null,
){

  init {
    @Suppress("LeakingThis")
    for (variant in variants) {
      variant.parent = this
    }
  }

}
