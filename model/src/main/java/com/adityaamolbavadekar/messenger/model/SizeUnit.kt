package com.adityaamolbavadekar.messenger.model

enum class SizeUnit {
    B, KB, MB, GB, TB;

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