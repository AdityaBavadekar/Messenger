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

package com.adityaamolbavadekar.messenger.contact

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.core.database.getStringOrNull
import com.adityaamolbavadekar.messenger.utils.Permissions
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.extensions.runOnMainThread
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class ContactsReader(private val context: Context) {

    fun read(listener: (List<ContactInfo>) -> Unit) = runOnIOThread {
        if (!Permissions.ReadContacts.isGranted(context)) {
            runOnMainThread { listener(listOf()) }
            return@runOnIOThread
        }
        val contactsList = mutableListOf<ContactInfo>()
        val c = getCursor()
        if (c == null || !c.moveToFirst()) {
            c?.close()
            runOnMainThread { listener(listOf()) }
            return@runOnIOThread
        } else {
            while (c.moveToNext()) {
                getContact(c)?.let { contactsList.add(it) }
            }
        }
        c.close()
        runOnMainThread { listener(contactsList.distinctBy { it.number }) }
        return@runOnIOThread
    }

    fun log() {}

    private fun getContact(c: Cursor): ContactInfo? {
        val name = c.getStringOrNull(0)
        val number = c.getStringOrNull(1)
        InternalLogger.logD("ContactsReader", "Contact : $name -> $number")
        return if (number == null || number.trim().isEmpty()) null
        else ContactInfo(name, number)
    }

    data class ContactInfo(val name: String?, val number: String)

    private fun getCursor(): Cursor? {
        return context.contentResolver.query(
            URI, COLUMNS, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
    }

    companion object {
        private val URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        private val COLUMNS = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
        )
    }

}