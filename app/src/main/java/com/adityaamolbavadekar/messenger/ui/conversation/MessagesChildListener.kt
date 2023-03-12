package com.adityaamolbavadekar.messenger.ui.conversation

import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue

class MessagesChildListener(private val listener: Listener) {
    companion object {
        private val TAG = MessagesChildListener::class.java.simpleName
    }

    val childEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            getMessage(snapshot)?.let { m ->
                InternalLogger.debugInfo(
                    TAG,
                    "childEventListener.onChildAdded : $m"
                )
                listener.doOnAdded(m)
            }
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            getMessage(snapshot)?.let { m ->
                InternalLogger.debugInfo(
                    TAG,
                    "childEventListener.onChildChanged : $m"
                )
                listener.doOnChanged(m)
            }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            InternalLogger.debugInfo(
                TAG,
                "childEventListener.onChildRemoved : ${snapshot.ref}"
            )
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            InternalLogger.debugInfo(
                TAG,
                "childEventListener.onChildMoved : ${snapshot.ref}"
            )
        }

        override fun onCancelled(error: DatabaseError) {
            InternalLogger.logE(
                TAG,
                "childEventListener.onCancelled",
                error.toException()
            )
        }
    }

    private fun getMessage(snapshot: DataSnapshot): MessageRecord? {
        return try {
            snapshot.getValue<MessageRecord>()
        } catch (e: Exception) {
            null
        }
    }

    interface Listener {
        fun doOnAdded(messageRecord: MessageRecord)
        fun doOnChanged(messageRecord: MessageRecord)
        fun doOnRemoved(messageRecord: MessageRecord)
    }

}