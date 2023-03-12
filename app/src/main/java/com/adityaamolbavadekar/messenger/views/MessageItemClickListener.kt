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

package com.adityaamolbavadekar.messenger.views

import android.widget.TextView
import com.adityaamolbavadekar.messenger.model.Attachment
import com.adityaamolbavadekar.messenger.model.MessageRecord
import com.adityaamolbavadekar.messenger.model.MessageReplyRecord
import com.adityaamolbavadekar.messenger.model.Recipient

typealias TitleClickListener = (v: TextView, messageRecord: MessageRecord) -> Unit
typealias MessageClickListener = (v: TextView, messageRecord: MessageRecord) -> Unit
typealias AddReplyListener = (recipient: Recipient?, messageRecord: MessageRecord) -> Unit
typealias NavigateToReplyListener = (recipient: Recipient?, replyInfo: MessageReplyRecord) -> Unit
typealias OpenDocumentListener = (attachment: Attachment) -> Unit
typealias DownloadDocumentListener = (messageId:String,attachment: Attachment) -> Unit