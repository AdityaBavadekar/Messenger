package com.adityaamolbavadekar.messenger.model

enum class SizeUnit(val bytes:Int) {
    B(1), KB(1024), MB(1048576), GB(1073741824);

    companion object {
        fun format(size: Long): String {
            if (size == 0.toLong()) return "0 B"
            val sizeKb = size / 1024
            val sizeMb = size / sizeKb
            return if (sizeMb > 0) "$sizeMb MB"
            else "$sizeKb KB"
        }
    }
}
