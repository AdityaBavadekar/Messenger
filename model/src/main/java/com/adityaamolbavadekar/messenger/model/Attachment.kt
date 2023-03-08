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

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.File

@Entity(tableName = "attachments_table")
data class Attachment(
    @PrimaryKey(autoGenerate = false)
    val id: String = Id.get(),
    val size: Long,
    val mimeType: String?,
    val fileName: String,
    val extension: String,
    val uploadTimestamp: Long,
    val width: Int,
    val height: Int,
    val absolutePath: String,
) {

    constructor() : this(
        id = Id.get(),
        size = 0,
        mimeType = null,
        fileName = "",
        extension = "",
        uploadTimestamp = 0,
        width = 0,
        height = 0,
        absolutePath = ""
    )

    fun fileNameWithExtension() = "$fileName.$extension"

    fun readableSize(): Pair<SizeUnit, Int> {
        if (size == 0.toLong()) return Pair(SizeUnit.B, 0)
        val sizeKb = size / 1024
        val sizeMb = size / sizeKb
        return if (sizeMb > 0) Pair(SizeUnit.MB, sizeMb.toInt())
        else Pair(SizeUnit.KB, sizeKb.toInt())
    }

    @Ignore
    fun mimeType(): String? {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }

    fun file(): File {
        return File(absolutePath)
    }

    companion object {
        fun from(file: File): Attachment {
            return Attachment(
                size = file.length(),
                fileName = file.nameWithoutExtension,
                extension = file.extension,
                uploadTimestamp = System.currentTimeMillis(),
                width = 0,
                height = 0,
                absolutePath = file.absolutePath,
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
            )
        }

        fun from(uri: Uri, fileName: String, size: Long, mimeType: String?): Attachment {
            var fileExtension = ""
            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)?.let {
                fileExtension = it
            }
            if (fileExtension == "" && fileName.contains(".")) {
                fileExtension =
                    fileName.subSequence(fileName.lastIndexOf("."), fileName.lastIndex).toString()
            }

            return Attachment(
                size = size,
                fileName = fileName,
                extension = fileExtension,
                uploadTimestamp = System.currentTimeMillis(),
                width = 0,
                height = 0,
                absolutePath = uri.toString(),
                mimeType = mimeType
            )
        }
    }
}
