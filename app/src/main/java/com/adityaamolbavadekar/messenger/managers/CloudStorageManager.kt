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

package com.adityaamolbavadekar.messenger.managers

import android.net.Uri
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.Constants.Extras.EXTRA_TIMESTAMP
import com.adityaamolbavadekar.messenger.utils.Constants.Extras.EXTRA_USER_UID
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.extensions.runOnMainThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.ktx.storage

class CloudStorageManager {

    private var db: FirebaseStorage = Firebase.storage

    fun save(photoUri: Uri, userId: String, onSuccess: (Uri) -> Unit) =
        runOnIOThread {
            val ref = db.getReference(Constants.CloudPaths.getUserProfilePicturesPath(userId))
            ref.putFile(
                photoUri,
                StorageMetadata.Builder().setCustomMetadata(EXTRA_USER_UID, userId)
                    .setCustomMetadata(EXTRA_TIMESTAMP, System.currentTimeMillis().toString())
                    .build()
            )
                .addOnSuccessListener {
                    InternalLogger.logI(TAG, "Successfully uploaded photo for user.")
                    ref.downloadUrl
                        .addOnSuccessListener {
                            InternalLogger.logI(TAG, "Successfully retrieved photoUrl for user.")
                            runOnMainThread { onSuccess(it) }
                        }
                }
                .addOnFailureListener {
                    InternalLogger.logE(TAG, "Unable to upload photo for user.", it)
                }
        }

    fun savePicture(
        photoUri: Uri,
        metadata: StorageMetadata,
        onResponseCallback: OnResponseCallback<Uri, Exception>,
    ) =
        runOnIOThread {
            val ref = db.getReference(
                Constants.CloudPaths.getConversationPicturesDocument()
            )
            ref.putFile(photoUri, metadata)
                .addOnSuccessListener {
                    InternalLogger.logI(TAG, "Successfully uploaded PICTURE for user.")
                    ref.downloadUrl
                        .addOnSuccessListener {
                            InternalLogger.logI(TAG, "Successfully retrieved PICTURE URL for user.")
                            runOnMainThread { onResponseCallback.onSuccess(it) }
                        }
                }
                .addOnFailureListener {
                    InternalLogger.logE(TAG, "Unable to upload PICTURE for user.", it)
                    onResponseCallback.onFailure(it)
                }
        }

    private fun internalSavePicture(
        photoUri: Uri,
        metadata: StorageMetadata,
        onSuccess: (Uri) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val ref = db.getReference(
            Constants.CloudPaths.getConversationPicturesDocument()
        )
        ref.putFile(photoUri, metadata)
            .addOnSuccessListener {
                InternalLogger.logI(TAG, "Successfully uploaded PICTURE for user.")
                ref.downloadUrl
                    .addOnSuccessListener {
                        InternalLogger.logI(TAG, "Successfully retrieved PICTURE URL for user.")
                        onSuccess(it)
                    }
            }
            .addOnFailureListener {
                InternalLogger.logE(TAG, "Unable to upload PICTURE for user.", it)
                onFailure(it)
            }
    }

    fun savePictures(
        urisList: List<Uri>,
        getMetadata: (Uri) -> StorageMetadata,
        onResponseCallback: OnResponseCallback<List<String>, Exception>,
    ) =
        runOnIOThread {
            val urlsList = mutableListOf<String>()
            urisList.forEachIndexed { index, photoUri ->
                internalSavePicture(photoUri, getMetadata(photoUri), onSuccess = {
                    urlsList.add(it.toString())
                }, { runOnMainThread { onResponseCallback.onFailure(it) } })
            }
            runOnMainThread {
                onResponseCallback.onSuccess(urlsList)
            }
        }


    fun saveProfilePicture(
        userId: String,
        photoUri: Uri,
        metadata: StorageMetadata,
        onResponseCallback: OnResponseCallback<Uri, Exception>
    ) =
        runOnIOThread {
            val ref = db.getReference(
                Constants.CloudPaths.getUserProfilePicturesPath(userId)
            )
            ref.putFile(photoUri, metadata)
                .addOnSuccessListener {
                    InternalLogger.logI(TAG, "Successfully uploaded Profile PICTURE for user.")
                    ref.downloadUrl
                        .addOnSuccessListener {
                            InternalLogger.logI(
                                TAG,
                                "Successfully retrieved Profile PICTURE URL for user."
                            )
                            runOnMainThread { onResponseCallback.onSuccess(it) }
                        }
                }
                .addOnFailureListener {
                    InternalLogger.logE(TAG, "Unable to upload Profile PICTURE for user.", it)
                    onResponseCallback.onFailure(it)
                }
        }

    fun deleteConversationPicture(photoUri: Uri) = runOnIOThread {
        val ref = db.getReference(
            Constants.CloudPaths.getConversationPicturesDocument()
        )
        //TODO
    }

    fun delete(photoUrl: String) = runOnIOThread {
        db.getReferenceFromUrl(photoUrl)
            .delete()
            .addOnSuccessListener {
                InternalLogger.logD(TAG, "Deleted document.")
            }
            .addOnFailureListener {
                InternalLogger.logE(TAG, "Unable to deleted document.", it)
            }
    }

    companion object {
        private const val TAG = "CloudStorageManager"
    }

}