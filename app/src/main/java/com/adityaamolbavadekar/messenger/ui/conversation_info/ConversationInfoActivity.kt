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

package com.adityaamolbavadekar.messenger.ui.conversation_info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.ActivityMain2Binding
import com.adityaamolbavadekar.messenger.model.ConversationRecord
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.base.BaseActivity

class ConversationInfoActivity : BaseActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        super.onCreateActivity(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val isP2P = intent.getBooleanExtra(Constants.Extras.EXTRA_IS_P2P, false)
        val fragment = if (isP2P) P2PConversationInfoFragment()
        else ConversationInfoFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    companion object {
        private const val TAG = "ConversationInfoActivity"
        fun createNewIntent(context: Context, conversation: ConversationRecord): Intent {
            val i = Intent(context, ConversationInfoActivity::class.java)
                .putExtra(Constants.EXTRA_CONVERSATION_ID, conversation.conversationId)
            if (conversation.isP2P) {
                i.putExtra(Constants.Extras.EXTRA_UID, conversation.p2PRecipientUid())
                i.putExtra(Constants.Extras.EXTRA_IS_P2P, true)
            }
            return i
        }
    }

}