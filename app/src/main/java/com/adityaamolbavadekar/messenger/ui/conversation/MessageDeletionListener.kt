package com.adityaamolbavadekar.messenger.ui.conversation

import com.adityaamolbavadekar.messenger.model.MessageRecord

interface MessageDeletionListener {
    fun onShouldDelete(messageRecord: MessageRecord)
    fun onShouldDeleteForEveryone(messageRecord: MessageRecord)
}