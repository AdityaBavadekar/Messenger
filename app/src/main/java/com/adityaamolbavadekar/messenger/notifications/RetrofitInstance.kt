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

package com.adityaamolbavadekar.messenger.notifications

import com.adityaamolbavadekar.messenger.utils.Constants.FcmMessaging.FCM_MESSAGING_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private val retrofit = lazy {
            Retrofit.Builder()
                .baseUrl(FCM_MESSAGING_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api: NotificationApi by lazy {
            retrofit.value.create(NotificationApi::class.java)
        }
    }

}