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

import java.util.*

class Id {
    companion object {
        fun get(): String {
            return UUID.randomUUID().toString()
        }

        fun getSpecial(): String {
            val id1 = get()
            val len = id1.length
            var id2 = get().trim('-', '_')
            val id1Comps = id1.trim('-', '_').map { it }
            while (id2.length != len) id2 += id1Comps.random().toString()
            return id2
        }
    }
}