/*
 *
 *    Copyright 2022 Aditya Bavadekar
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
 *
 */

package com.adityaamolbavadekar.messenger.ui.conversation_list

import androidx.lifecycle.ViewModel
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.managers.CloudDatabaseManager
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class ConversationListViewModel : ViewModel(),
    OnResponseCallback<List<CloudDatabaseManager.ConversationResponse>, Exception> {

    private val cloudDatabaseManager = CloudDatabaseManager()
    private val authManager = AuthManager()
    private var isLoading = false

    override fun onSuccess(t: List<CloudDatabaseManager.ConversationResponse>) {
        InternalLogger.logD(TAG, "ConversationsList : $t")
    }

    override fun onFailure(e: Exception) {
        InternalLogger.logD(TAG, "ConversationsList : Failure :$e")
    }

    fun load(me: Recipient) {
        if (isLoading) return
        cloudDatabaseManager.Conversations().observeConversations(authManager.uid, this)
        isLoading = true
    }

    companion object {
        private val TAG = ConversationListViewModel::class.java.simpleName
    }

}