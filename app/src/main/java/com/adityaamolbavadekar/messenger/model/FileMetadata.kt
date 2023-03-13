package com.adityaamolbavadekar.messenger.model

data class FileMetadata(
    val name:String,
    val nameWithoutExtension:String,
    val extension:String,
    val mimeType:String?,
    val size:Long
)
