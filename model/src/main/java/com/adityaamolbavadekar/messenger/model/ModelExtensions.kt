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

fun List<ReactionRecord>.toHashMapList(): MutableList<HashMap<String, Any?>> {
    val newList = mutableListOf<HashMap<String, Any?>>()
    for (r in this) {
        newList.add(r.hashMap())
    }
    return newList
}

fun List<User>.toRecipientsList(): MutableList<Recipient> {
    val list = this.distinctBy { it.UID }
    val newList = mutableListOf<Recipient>()
    for (r in list) {
        newList.add(Recipient.Builder(r).build())
    }
    return newList
}

fun List<User>.toRecipientsList(ignorableUserId: String): MutableList<Recipient> {
    val newList = mutableListOf<Recipient>()
    for (r in this.distinctBy { it.UID }) {
        if (r.UID != ignorableUserId && r.UID.trim().isNotEmpty()) {
            newList.add(Recipient.Builder(r).build())
        }
    }
    return newList
}

fun List<Recipient>.toUidList(): MutableList<String> {
    val sorted = this.distinctBy { it.uid }
    val newList = mutableListOf<String>()
    for (r in sorted) {
        newList.add(r.uid)
    }
    return newList
}

fun List<Recipient>.valueOf(uid: String): Recipient? {
    for (r in this.distinctBy { it.uid }) {
        if (r.uid == uid) {
            return r
        }
    }
    return null
}

fun List<RemoteRecipient>.remoteRecipientToHashMapList(): MutableList<HashMap<String, Any?>> {
    val newList = mutableListOf<HashMap<String, Any?>>()
    for (r in this) {
        newList.add(r.hashMap())
    }
    return newList
}

fun List<String>.toStringHashMapList(): MutableList<HashMap<String, String>> {
    val newList = mutableListOf<HashMap<String, String>>()
    for ((i, r) in this.withIndex()) {
        newList.add(hashMapOf("$i" to r))
    }
    return newList
}
