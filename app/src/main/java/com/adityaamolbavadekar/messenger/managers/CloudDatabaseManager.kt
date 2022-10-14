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

package com.adityaamolbavadekar.messenger.managers

import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.model.RemoteConversation
import com.adityaamolbavadekar.messenger.model.User
import com.adityaamolbavadekar.messenger.utils.Constants
import com.adityaamolbavadekar.messenger.utils.OnResponseCallback
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.extensions.runOnMainThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CloudDatabaseManager {

    /*Save the whole user*/
    fun saveUserDetails(user: User) = runOnIOThread {
        val userId =
            checkNotNull(user.UID) { "CloudDatabaseManager cannot save with `null` USER_ID." }
        InternalLogger.logI(TAG, "Uploading Main UserDetails for $user.")
        Firebase.database.getReference(Constants.CloudPaths.getUserPath(userId))
            .setValue(user)
            .addOnSuccessListener {
                InternalLogger.logI(TAG, "Successfully uploaded UserDetails.")
            }
            .addOnFailureListener {
                InternalLogger.logE(TAG, "Unable to upload UserDetails.", it)
                retry(user)
            }
    }

    /*Save the users public details */
    fun saveUserPublicDetails(recipient: Recipient) = runOnIOThread {
        val userId =
            checkNotNull(recipient.uid) { "CloudDatabaseManager cannot save with `null` USER_ID." }
        InternalLogger.logI(TAG, "Uploading Main PublicUserDetails for $recipient.")
        Firebase.database.getReference(Constants.CloudPaths.getUserPublicPath(userId))
            .setValue(recipient)
            .addOnSuccessListener {
                InternalLogger.logI(TAG, "Successfully uploaded PublicUserDetails.")
            }
            .addOnFailureListener {
                InternalLogger.logE(TAG, "Unable to upload PublicUserDetails.", it)
                retryRecipient(recipient)
            }
    }

    private fun retry(user: User) {
        var retry = 0
        if (retry == 0) {
            InternalLogger.logW(TAG, "Retrying to upload UserDetails.")
            retry += 1
            saveUserDetails(user)
        }
    }

    private fun retryRecipient(recipient: Recipient) {
        var retry = 0
        if (retry == 0) {
            InternalLogger.logW(TAG, "Retrying to upload PublicUserDetails.")
            retry += 1
            saveUserPublicDetails(recipient)
        }
    }

    /*Update database display name for user*/
    fun updateUserProfileInfo(
        map: HashMap<String, Any?>,
        userId: String,
        onResult: (Boolean) -> Unit={}
    ) =
        CoroutineScope(Dispatchers.Default).launch {
           
            map.forEach { pair ->
                Firebase.database.getReference(Constants.CloudPaths.getUserPath(userId))
                .child(pair.key).setValue(pair.value)
                    .addOnSuccessListener {
                        InternalLogger.logI(
                            TAG,
                            "updateUserProfileInfo : Successfully updated [${pair.key}]."
                        )
                    }
                    .addOnFailureListener {
                        InternalLogger.logI(
                            TAG,
                            "updateUserProfileInfo : Unable to updated [${pair.key}].",
                            it
                        )
                    }
            }
        }

    inner class Users {

        fun observeUsers(
            onResponseCallback: OnResponseCallback<List<User>, Exception?>
        ) {
            Firebase.database.getReference(Constants.CloudPaths.getUsersPath())
                .get()
                .addOnSuccessListener {
                    val list = mutableListOf<User>()
                    it.children.forEach { m ->
                        m.getValue<User>()?.let { user ->
                            list.add(user)
                        }
                    }
                    InternalLogger.logI(
                        TAG,
                        "Got ${list.size} users list from database Users"
                    )
                    onResponseCallback.onSuccess(list)
                    return@addOnSuccessListener
                }
                .addOnFailureListener { error ->
                    error.let {
                        InternalLogger.logE(
                            TAG,
                            "Unable to get Users",
                            it
                        )
                        onResponseCallback.onFailure(it)
                    }
                    return@addOnFailureListener
                }
        }

        fun getPublicUsersList(
            onResponseCallback: OnResponseCallback<List<Recipient>, Exception?>? = null
        ) {
            Firebase.database.getReference(Constants.CloudPaths.getUsersPath())
                .get()
                .addOnSuccessListener {
                    val list = mutableListOf<Recipient>()
                    it.children.forEach { m ->
                        m.getValue<Recipient>()?.let { recipient ->
                            list.add(recipient)
                        }
                    }
                    InternalLogger.logI(
                        TAG,
                        "Got ${list.size} public users list from database Users"
                    )
                    onResponseCallback?.onSuccess(list)
                    return@addOnSuccessListener
                }
                .addOnFailureListener { error ->
                    error.let {
                        InternalLogger.logE(
                            TAG,
                            "Unable to get public Users",
                            it
                        )
                        onResponseCallback?.onFailure(it)
                    }
                    return@addOnFailureListener
                }
        }

        /*Get the user with uid*/
        fun getUser(uid: String, onResponseCallback: OnResponseCallback<User, Exception?>) =
            runOnIOThread {
                Firebase.database.getReference(Constants.CloudPaths.getUserPath(uid))
                    .get()
                    .addOnSuccessListener { doc ->
                        runOnMainThread {
                            InternalLogger.logI(
                                TAG,
                                "Successfully retrieved database values for user."
                            )
                            doc.getValue<User>()?.let { user -> onResponseCallback.onSuccess(user) }
                        }
                    }
                    .addOnFailureListener {
                        runOnMainThread {
                            InternalLogger.logE(
                                TAG,
                                "Unable to retrieve database values for user.",
                                it
                            )
                            onResponseCallback.onFailure(it)
                        }
                    }
            }

        /*Update database display name for user*/
        fun saveNewActivityInfo(
            index: Int,
            activity: HashMap<String, Any>,
            userId: String,
            onResult: (Boolean) -> Unit
        ) =
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    Firebase.database.getReference(Constants.CloudPaths.getUserActivityPath(userId) + "/" + index)
                        .setValue(activity)
                        .addOnSuccessListener {
                            InternalLogger.logI(
                                TAG,
                                "Successfully updated activity database values for user."
                            )
                            onResult(true)
                        }
                        .addOnFailureListener {
                            InternalLogger.logE(
                                TAG,
                                "Unable to update activity database values for user.",
                                it
                            )
                            onResult(false)
                        }
                } catch (e: Exception) {
                }
            }

    }

    inner class Conversations {

        private var listener: ValueEventListener? = null
        private var ref: DatabaseReference? = null

        fun observeConversations(
            myUid: String,
            onResponseCallback: OnResponseCallback<List<String>, Exception>
        ) {
            ref =
                Firebase.database.getReference(Constants.CloudPaths.getUserConversationsPath(myUid))
            listener = (object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    InternalLogger.logD(TAG, "onDataChange ${snapshot.children}")
                    if (!snapshot.exists()) return
                    val list = mutableListOf<String>()
                    try {
                        snapshot.children.forEach { m ->
                            InternalLogger.logI(TAG, "ConversationDataSnapshotValue: ${m.value}")
                            InternalLogger.logI(TAG, "Conversation: $m")
                        }
                    } catch (e: Exception) {
                    }
                    onResponseCallback.onSuccess(list)
                }

                override fun onCancelled(error: DatabaseError) {}

            })
            ref!!.addValueEventListener(listener!!)
        }

        fun createGroupConversation(groupInfo: RemoteConversation): Task<Void> {
            return Firebase.database
                .getReference(Constants.CloudPaths.getGroupPropertiesPath(groupInfo.conversationId))
                .setValue(groupInfo)
        }

        fun updateGroupConversation(groupInfo: RemoteConversation): Task<Void> {
            return Firebase.database
                .getReference(Constants.CloudPaths.getGroupPropertiesPath(groupInfo.conversationId))
                .setValue(groupInfo)
        }

        fun observeGroupConversationProperties(
            groupId: String,
            onDataChanged: (RemoteConversation) -> Unit
        ) {
            ref = Firebase.database
                .getReference(Constants.CloudPaths.getGroupPropertiesPath(groupId))
            listener = (object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue<HashMap<String, Any>>()?.let {
                        //onDataChanged(it)
                        InternalLogger.logD(
                            TAG,
                            "onDataChanged(observeGroupConversationProperties) : $it"
                        )
                        try {
                            snapshot.getValue(RemoteConversation::class.java)?.let { r ->
                                onDataChanged(r)
                            }
                        } catch (e: Exception) {
                            InternalLogger.logE(
                                TAG,
                                "Unable to convert data to RemoteConversation(observeGroupConversationProperties) : $e"
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
            ref!!.addValueEventListener(listener!!)
        }

        fun deleteUserConversationData(
            myUid: String,
            id: String,
            invokeOnCompletion: (Exception?) -> Unit
        ) {
            Firebase.database.getReference(Constants.CloudPaths.getUserConversationsPath(myUid))
                .child(id).setValue(null)
                .addOnSuccessListener { invokeOnCompletion(null) }
                .addOnFailureListener { invokeOnCompletion(it) }
        }

        fun removeListener() {
            listener?.let { ref?.removeEventListener(it) }
        }

    }

    inner class Messages {

        /*[START] P2P*/
        fun saveMessageToDatabase(
            m: MessageRecord, uid: String, uidB: String,
            responseCallback: (Boolean) -> Unit
        ) = runOnIOThread {
            ref = Firebase.database
                .getReference(Constants.CloudPaths.getP2PMessagesPath(uid, uidB) + m.id)
            ref!!.setValue(m.hashMap())
                .addOnSuccessListener { responseCallback(true) }
                .addOnFailureListener { responseCallback(false) }
        }

        fun observeMessagesFromDatabase(
            ofUser: String,
            withUser: String,
            onResponseCallback: OnResponseCallback<List<MessageRecord>, Exception>
        ) {
            ref = Firebase.database
                .getReference(Constants.CloudPaths.getP2PMessagesPath(ofUser, withUser))
            InternalLogger.logI(TAG, "P2P Messages Listener added on $ref")
            try {
                listener = (object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val messagesList = mutableListOf<MessageRecord>()
                        snapshot.getValue<MessageRecord>()?.let { message ->
                            InternalLogger.logI(TAG, "P2P Message : $message")
                            messagesList.add(message)
                        }
                        onResponseCallback.onSuccess(messagesList)
                        runOnIOThread {
                            notifyReadStatusForP2P(messagesList, ofUser, withUser)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
                ref!!.addValueEventListener(listener!!)
            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Unable to get Messages list", e)
                onResponseCallback.onFailure(e)
            }
        }
        /*[END] P2P*/

        private fun notifyReadStatusForGroup(messagesList: List<MessageRecord>, myUid: String) {
            messagesList.forEach { messageRecord ->
                if (messageRecord.senderUid != myUid && !messageRecord.isReadBy(myUid)) {
                    messageRecord.setReadBy(myUid)
                    saveMessageToGroupDatabase(messageRecord, messageRecord.conversationId) {
                        InternalLogger.logD(TAG, "notifiedReadStatusForGroup=$it")
                    }
                }
            }
        }

        private fun notifyReadStatusForP2P(
            messagesList: List<MessageRecord>,
            myUid: String,
            otherPersonUid: String
        ) {
            messagesList.forEach { messageRecord ->
                if (messageRecord.senderUid != myUid && !messageRecord.isReadBy(myUid)) {
                    messageRecord.setReadBy(myUid)
                    saveMessageToDatabase(messageRecord, myUid, otherPersonUid) {
                        InternalLogger.logD(TAG, "notifyReadStatusForP2P=$it (MY ROOM)")
                    }
                    saveMessageToDatabase(messageRecord, otherPersonUid, myUid) {
                        InternalLogger.logD(TAG, "notifyReadStatusForP2P=$it (RECIPIENT ROOM)")
                    }
                }
            }
        }

        /*[START] Group*/
        fun saveMessageToGroupDatabase(
            m: MessageRecord, groupId: String,
            responseCallback: (Boolean) -> Unit
        ) = runOnIOThread {
            ref = Firebase.database
                .getReference(Constants.CloudPaths.getGroupMessagesPath(groupId) + "/" + m.id)
            ref!!.setValue(m.hashMap())
                .addOnSuccessListener { responseCallback(true) }
                .addOnFailureListener { responseCallback(false) }
        }

        fun observeMessagesFromGroupDatabase(
            groupId: String, myUid: String,
            onResponseCallback: OnResponseCallback<List<MessageRecord>, Exception>
        ) {
            ref = Firebase.database
                .getReference(Constants.CloudPaths.getGroupMessagesPath(groupId))
            InternalLogger.logI(TAG, "Messages Listener added on $ref")
            try {
                listener = (object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        InternalLogger.logD(TAG, "onDataChange")
                        val messagesList = mutableListOf<MessageRecord>()
                        snapshot.children.forEach { m ->
                            m.getValue<MessageRecord>()?.let { message ->
                                InternalLogger.logI(TAG, "Message : $message")
                                messagesList.add(message)
                            }
                        }
                        onResponseCallback.onSuccess(messagesList)
                        runOnIOThread {
                            notifyReadStatusForGroup(messagesList, myUid)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}

                })
                ref!!.addValueEventListener(listener!!)
            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Unable to get Messages list", e)
            }
        }
        /*[END] Group*/

        private var listener: ValueEventListener? = null
        private var ref: DatabaseReference? = null

        fun removeListener() {
            listener?.let { ref?.removeEventListener(it) }
        }
    }

    inner class Statuses {
        private val databaseReference =
            Firebase.database.getReference(Constants.CloudPaths.getPresenceStatusRootPath())
        private var listener: ValueEventListener? = null
        private var listenerUid: String? = null

        fun observeStatus(
            uid: String,
            onResponseCallback: (Long) -> Unit
        ) {
            listenerUid = uid
            val ref = databaseReference.child(uid)
            InternalLogger.logI(TAG, "Status Listener added on $ref")
            try {
                listener = ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        InternalLogger.logD(
                            TAG,
                            "onStatusChanged : snapshot.value ${snapshot.getValue<Long>() ?: "Empty"}"
                        )
                        snapshot.getValue<Long>()?.let { status ->
                            InternalLogger.logI(TAG, "STATUS => $status")
                            onResponseCallback(status)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}

                })
            } catch (e: Exception) {
                InternalLogger.logE(TAG, "Unable to get STATUS info", e)
            }
        }

        fun removeListener() {
            listener?.let {
                listenerUid?.let { child ->
                    databaseReference.child(child).removeEventListener(it)
                    InternalLogger.logI(TAG, "Status Listener removed from $child")
                }
            }
        }

        fun updateStatus(statusValue: Long, uid: String) {
            databaseReference
                .child(uid)
                .setValue(statusValue)
        }

    }

    inner class Contacts {

        fun save(list: List<String>) {
            Firebase.database.getReference(Constants.CloudPaths.getUserContactsPath(AuthManager().uid))
                .setValue(list)
                .addOnSuccessListener { InternalLogger.logD(TAG, "Saved user contacts.") }
                .addOnFailureListener {
                    InternalLogger.logE(
                        TAG,
                        "Unable to saved user contacts.",
                        it
                    )
                }
        }

    }

    private val usersManager = Users()
    private val contactsManager = Contacts()
    private val messagesManager = Messages()
    private val conversationsManager = Conversations()
    private val statusManager = Statuses()

    fun getUsersManager(): Users {
        return usersManager
    }

    fun getContactsManager(): Contacts {
        return contactsManager
    }

    fun getMessagesManager(): Messages {
        return messagesManager
    }

    fun getConversationsManager(): Conversations {
        return conversationsManager
    }

    fun getStatusManager(): Statuses {
        return statusManager
    }

    companion object {
        private const val TAG = "CloudDatabaseManager"
    }

}
