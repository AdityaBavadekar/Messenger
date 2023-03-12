package com.adityaamolbavadekar.messenger.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

@Entity(tableName = "local_attachments_table")
data class LocalAttachment(
    @PrimaryKey(autoGenerate = false)
    val id: String = Id.get(),
    val correspondingAttachmentId: String,
    val correspondingMessageId: String,
    val attachmentFileSize: Long,
    val attachmentMimeType: String?,
    val attachmentFileName: String,
    val attachmentFileExtension: String,
    val remoteDownloadUrl: String,
    val documentStorageType: Int,
    val localFilePath: String,
    val localFileName: String,
    val localStorageDir: String,
) {

    fun getFile(): File {
        return File(localFilePath)
    }

    enum class DocumentStorage {
        DOCS_SENT, DOCS_RECEIVED;

    }

    companion object {
        fun new(
            attachment: Attachment,
            correspondingMessageId: String,
            remoteDownloadUrl: String,
            localFilePath: String,
            localFileName: String,
            localStorageDir: String,
            documentStorageType: Int
        ): LocalAttachment {
            return LocalAttachment(
                id = Id.get(),
                correspondingAttachmentId = attachment.id,
                correspondingMessageId = correspondingMessageId,
                attachmentFileSize = attachment.size,
                attachmentMimeType = attachment.mimeType,
                attachmentFileName = attachment.fileName,
                attachmentFileExtension = attachment.extension,
                remoteDownloadUrl = remoteDownloadUrl,
                documentStorageType = documentStorageType,
                localFilePath = localFilePath,
                localFileName = localFileName,
                localStorageDir = localStorageDir
            )
        }
    }

}

/*
*
* Picker (Uri) => Upload (Uri) => Attachment(ID,Uri) (ID) => LocalAttachment(id,correspondingAttachmentId=ID, RemoteUri,)
*
* */