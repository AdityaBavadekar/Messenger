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

/**
 * For identifying Delivery Status of a message.
 * */
interface ConversationPermissionType {

    companion object {
        /* Default */
        private const val PERMIT_ALL = 100

        /* Managers Only*/
        private const val PERMIT_MANAGERS_ONLY = 200

        /* Unknown */
        private const val UNKNOWN = -1
    }

    fun permitAll() = PERMIT_ALL
    fun permitManagersOnly() = PERMIT_MANAGERS_ONLY
    fun isRestricted(permission: Int): Boolean {
        return permission == PERMIT_MANAGERS_ONLY
    }

    fun getField(conversation: BaseConversation): Int
    fun getPermissionString(permission: Int): String {
        return when (permission) {
            PERMIT_MANAGERS_ONLY -> "Mangers Only"
            PERMIT_ALL -> "All Permitted"
            else -> "Unknown"
        }
    }

}