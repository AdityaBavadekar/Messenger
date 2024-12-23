package com.adityaamolbavadekar.messenger.model

enum class SizeUnit(val bytes:Int) {
    B(1), KB(1024), MB(1048576), GB(1073741824);

    companion object {
        fun format(size: Long): String {
            if (size == 0.toLong()) return "0 B"
            val sizeKb : Float = size.toFloat() / 1024
            if (sizeKb < 1024) return "$sizeKb KB"
            val sizeMb : Float = sizeKb / 1024
            return "$sizeMb MB"
        }
    }
}
