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

package com.adityaamolbavadekar.messenger.utils.textstyling

import android.content.Context
import android.text.Spanned
import android.text.SpannedString
import android.text.style.TextAppearanceSpan
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.italic
import androidx.core.text.strikeThrough
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

object TextStyler {

    fun parse(c: Context, string: String, listener: (SpannedString) -> Unit = {}) {
        listener(format(string, c))
    }

    private val pattern =
        Regex("""(```[^`]+```)|(@\w+)|(\*[\w\s]+\*)|(_[\w\s]+_)|(~[\w\s]+~)""")

    fun format(text: String, context: Context): SpannedString {
        val tokens = pattern.findAll(text)
        return buildSpannedString {
            var cursorPosition = 0
            InternalLogger.logD(TAG, tokens.joinToString())
            tokens.forEach { token ->
                append(text.slice(cursorPosition until token.range.first))
                val annotatedString = getSymbolAnnotation(context, token)
                append(annotatedString)
                cursorPosition = token.range.last + 1
            }

            if (!tokens.none()) {
                append(text.slice(cursorPosition..text.lastIndex))
            } else {
                append(text)
            }
        }
    }

    private fun getSymbolAnnotation(context: Context, matchResult: MatchResult): SpannedString {
        return when (matchResult.value.first()) {
            '@' -> {
                buildSpannedString {
                    bold {
                        append(matchResult.value)
                    }
                }
            }
            '*' -> {
                buildSpannedString {
                    val newText = matchResult.value.removePrefix("*").removeSuffix("*")
                    bold {
                        append(newText)
                    }
                }
            }
            '_' -> {
                buildSpannedString {
                    val newText = matchResult.value.removePrefix("_").removeSuffix("_")
                    italic {
                        append(newText)
                    }
                }
            }
            '`' -> {
                buildSpannedString {
                    val newText = matchResult.value.removePrefix("```").removeSuffix("```")
                    append(newText)
                    setSpan(
                        TextAppearanceSpan(context, R.style.TextAppearance_Messenger_CodeText),
                        0,
                        newText.lastIndex,
                        FLAGS
                    )
                }
            }
            '~' -> {
                buildSpannedString {
                    val newText = matchResult.value.trim('~')
                    strikeThrough { append(newText) }
                }
            }
            'h' -> {
                buildSpannedString { }
            }
            else -> buildSpannedString { }
        }
    }

    private val TAG = TextStyler::class.java.simpleName
    private const val FLAGS = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

}