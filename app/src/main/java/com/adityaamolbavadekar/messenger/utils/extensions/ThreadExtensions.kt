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

package com.adityaamolbavadekar.messenger.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Run the provided block on Main Thread using [Dispatchers.Main].
 * */
fun runOnMainThread(function: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.Main).launch(block = function)

/**
 * Run the provided block on IO Thread which avoids blocking
 * the application ui thread using [Dispatchers.IO].
 * */
fun runOnIOThread(function: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.IO).launch(block = function)