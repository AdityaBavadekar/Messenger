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

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import javax.net.SocketFactory

object InternetManager {

    private val TAG = InternetManager::class.java.simpleName
    lateinit var isConnected: ConnectionLiveData

    class ConnectionLiveData(context: Context) : LiveData<Boolean>() {

        private lateinit var networkCallback: ConnectivityManager.NetworkCallback
        private val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        private val validNetworks: MutableSet<Network> = HashSet()
        private val networkRequestExecutor = NetworkRequestExecutor()

        private fun checkValidNetworks() {
            postValue(validNetworks.size > 0)
        }

        override fun onActive() {
            super.onActive()
            networkCallback = createNetworkCallback()
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NET_CAPABILITY_INTERNET)
                .build()
            cm.registerNetworkCallback(networkRequest, networkCallback)
        }

        override fun onInactive() {
            super.onInactive()
            cm.unregisterNetworkCallback(networkCallback)
        }

        private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                logMessage("onAvailable")
                val networkCapabilities = cm.getNetworkCapabilities(network)
                val hasInternetCapability =
                    networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
                if (hasInternetCapability == true) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val hasInternet = networkRequestExecutor.execute(network.socketFactory)
                        if (hasInternet) {
                            withContext(Dispatchers.Main) {
                                validNetworks.add(network)
                                checkValidNetworks()
                            }
                        }
                    }
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                logMessage("onLost")
                validNetworks.remove(network)
                checkValidNetworks()
            }

        }

        private fun logMessage(m: String) {
            InternalLogger.logI(TAG, m)
        }

        private class NetworkRequestExecutor {
            fun execute(socketFactory: SocketFactory?): Boolean {
                socketFactory?.let {
                    return try {
                        val socket = it.createSocket()
                        socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                        socket.close()
                        true
                    } catch (e: Exception) {
                        false
                    }
                }
                return false
            }
        }

    }

    fun onCreate(application: Application): InternetManager {
        isConnected = ConnectionLiveData(application.applicationContext)
        InternalLogger.logI(TAG, "onCreate [isConnected=${isConnected.value}]")
        return this
    }

    val isAvailable: Boolean
        get() {
            return isConnected.value == true
        }


}