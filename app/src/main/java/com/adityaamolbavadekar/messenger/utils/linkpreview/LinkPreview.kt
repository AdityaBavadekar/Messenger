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

package com.adityaamolbavadekar.messenger.utils.linkpreview

import com.adityaamolbavadekar.messenger.model.LinkPreviewInfo
import com.adityaamolbavadekar.messenger.utils.Operation
import com.adityaamolbavadekar.messenger.utils.extensions.runOnIOThread
import com.adityaamolbavadekar.messenger.utils.extensions.runOnMainThread
import org.jsoup.Jsoup

object LinkPreview {

    fun get(url: String, operation: Operation<LinkPreviewInfo>) = runOnIOThread {
        try {
            val info = LinkPreviewInfo(url = url)

            if (info.isTypeEmail()) return@runOnIOThread

            val connection = Jsoup.connect(url)
                .timeout(40 * 1000)

            val doc = connection.get()

            val elements = doc.getElementsByTag("meta")

            var title: String? = doc.select("meta[property=og:title]").attr("content")
            if (title == null || title.trim().isEmpty()) {
                title = doc.title()
            }
            info.title = title

            var body: String? = doc.select("meta[name=description]").attr("content")
            if (body == null || body.trim().isEmpty()) {
                body = doc.select("meta[name=Description]").attr("content")
            }
            if (body == null || body.trim().isEmpty()) {
                body = doc.select("meta[property=og:description]").attr("content")
            }
            info.body = body

            var imgElements = doc.select("meta[property=og:image]")
            if (imgElements.size > 0) {
                val image = imgElements.attr("content")
                if (image != null && image.trim().isNotEmpty()) {
                    info.imgUrl = image
                }
            }
            if (info.imgUrl == null || info.imgUrl?.trim()?.isEmpty() == true) {
                imgElements = doc.select("meta[name=og:image]")
                if (imgElements.size > 0) {
                    val image = imgElements.attr("content")
                    if (image != null && image.trim().isNotEmpty()) {
                        info.imgUrl = image
                    }
                }
            }

            runOnMainThread { operation.addOnSuccessListener(info) }

        } catch (e: Exception) {
            runOnMainThread { operation.addOnFailureListener(e) }
        }
    }

}