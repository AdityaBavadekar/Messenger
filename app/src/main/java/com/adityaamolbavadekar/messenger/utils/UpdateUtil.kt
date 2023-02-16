package com.adityaamolbavadekar.messenger.utils

import android.content.Context
import com.adityaamolbavadekar.messenger.BuildConfig
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.model.UpdateInfo
import com.adityaamolbavadekar.messenger.utils.extensions.simpleDateFormat
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

/**
 * Checks for Updates by comparing VersionCode.
 * */
class UpdateUtil {

    companion object {
        fun checkForUpdates(context: Context, callback: OnResponseCallback<UpdateInfo, Exception>) {
            val prefs = PrefsManager(context)
            // Don't check for updates if already checked for the day.
            prefs.getLastUpdateChecked()
                ?.let {
                    val info = prefs.getUpdateInfo()
                    if (simpleDateFormat(it) == simpleDateFormat(System.currentTimeMillis())) return
                    else if (info != null && info.versionCode > BuildConfig.VERSION_CODE) return callback.onSuccess(
                        info
                    )
                }
            Firebase.database.getReference(Constants.CloudPaths.CLOUD_PATH_UPDATES)
                .get()
                .addOnSuccessListener {
                    try {
                        val updateInfo = it.getValue<UpdateInfo>()
                        if (updateInfo == null) callback.onFailure(NullPointerException())
                        else {
                            PrefsManager(context).saveUpdateInfo(updateInfo)
                            callback.onSuccess(updateInfo)
                        }
                    } catch (e: Exception) {
                        callback.onFailure(e)
                    }
                }
                .addOnFailureListener { callback.onFailure(it) }
        }
    }
}