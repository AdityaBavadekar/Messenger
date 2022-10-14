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

package com.adityaamolbavadekar.messenger.utils.tracer

import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

class MethodTracer(private val methodName: String) {

    constructor(TAG: String, methodName: String) : this("$TAG#$methodName")

    private var startTimeMillis: Long = 0
    private var startTimeNano: Long = 0

    fun start(): MethodTracer {
        startTimeNano = System.nanoTime()
        startTimeMillis = System.currentTimeMillis()
        InternalLogger.logI(TAG, "Tracing [${methodName}]")
        return this
    }

    fun putTraceData(key: String, value: Any): MethodTracer {
        val nano = System.nanoTime() - startTimeNano
        val millis = System.currentTimeMillis() - startTimeMillis
        InternalLogger.logI(
            TAG,
            "TraceData [$methodName] [${millis}ms] [${nano}ns] : $key = $value "
        )
        return this
    }

    fun end() {
        val nano = System.nanoTime() - startTimeNano
        val millis = System.currentTimeMillis() - startTimeMillis
        reset()
        InternalLogger.logI(TAG, "TraceInfo [$methodName] [${millis}ms] [${nano}ns]")

    }

    private fun reset() {
        startTimeNano = 0
        startTimeMillis = 0
    }

    companion object{
        private val TAG = MethodTracer::class.java.simpleName
    }

}