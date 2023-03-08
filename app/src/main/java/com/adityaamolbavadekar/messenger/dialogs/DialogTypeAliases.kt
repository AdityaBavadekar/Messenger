package com.adityaamolbavadekar.messenger.dialogs

import androidx.appcompat.app.AlertDialog

typealias ProgressDialog = (dialog: AlertDialog, actionSetProgress: (Int) -> Unit) -> Unit