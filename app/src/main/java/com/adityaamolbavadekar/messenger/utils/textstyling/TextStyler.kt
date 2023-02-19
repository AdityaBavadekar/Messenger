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
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.SpannedString
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import androidx.core.text.buildSpannedString
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

object TextStyler {

    fun parse(c: Context, string: String, listener: (SpannedString) -> Unit = {}) {
        listener(format(string, c))
    }

    fun parseOld(c: Context, string: String, listener: (SpannableStringBuilder) -> Unit = {}) {
        val spannableStringBuilder = SpannableStringBuilder(string)
        try {
            listener(code(italic(bold(spannableStringBuilder, c), c), c))
        } catch (e: Exception) {
            InternalLogger.logW("TextStyler", "Unable to parse string", e)
            listener(SpannableStringBuilder(string))
        }
    }

    private fun bold(string: SpannableStringBuilder, context: Context): SpannableStringBuilder {
        val p = (BOLD_REGEX.toPattern())
        val boldCharacterCount = 1 /*Size of "*" */
        var c = 0
        val transparent =
            TextAppearanceSpan(context, R.style.TextAppearance_Messenger_InvisibleSizeAndColor)
        val m = p.matcher(string)
        val spannable = SpannableStringBuilder(string)
        while (m.find()) {
            c++
            string.setSpan(
                transparent,
                m.start(), m.start() + boldCharacterCount,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            string.setSpan(
                transparent,
                m.end() - boldCharacterCount, m.end(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val span = StyleSpan(Typeface.BOLD)
            spannable.setSpan(
                span,
                m.start() + boldCharacterCount,
                m.end() - boldCharacterCount,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        InternalLogger.logD("TextStyler", "Bold : $c Items found")
        return spannable
    }

    private fun italic(string: SpannableStringBuilder, context: Context): SpannableStringBuilder {
        val p = (ITALIC_REGEX.toPattern())
        val italicCharacterCount = 1 /*Size of "_" */
        val transparent =
            TextAppearanceSpan(context, R.style.TextAppearance_Messenger_InvisibleSizeAndColor)
        var c = 0
        val m = p.matcher(string)
        while (m.find()) {
            c++
            string.setSpan(
                transparent,
                m.start(), m.start() + italicCharacterCount,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            string.setSpan(
                transparent,
                m.end() - italicCharacterCount, m.end(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val span = StyleSpan(Typeface.ITALIC)
            string.setSpan(
                span,
                m.start() + italicCharacterCount,
                m.end() - italicCharacterCount,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        InternalLogger.logD("TextStyler", "Italic : $c Items found")
        return string
    }

    private fun code(string: SpannableStringBuilder, context: Context): SpannableStringBuilder {
        val p = (CODE_REGEX.toPattern())
        val codeCharacterCount = 3 /*Size of "```" */
        val transparent =
            TextAppearanceSpan(context, R.style.TextAppearance_Messenger_InvisibleSizeAndColor)
        var c = 0
        val m = p.matcher(string)
        while (m.find()) {
            c++
            string.setSpan(
                transparent,
                m.start(), m.start() + codeCharacterCount,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            string.setSpan(
                transparent,
                m.end() - codeCharacterCount, m.end(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val span = StyleSpan(Typeface.MONOSPACE.style)
            string.setSpan(
                span,
                m.start() + codeCharacterCount,
                m.end() - codeCharacterCount,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        InternalLogger.logD("TextStyler", "Code : $c Items found")
        return string
    }

    private val BOLD_REGEX = Regex("(\\*)[^\\s](.*?)[^\\s](\\*)")

    private val CODE_REGEX = Regex("(```)[^\\s](.*?)[^\\s](```)")

    private val ITALIC_REGEX = Regex("(_)[^\\s](.*?)[^\\s](_)")

    private val pattern =
        Regex("""(`[^`]+`)|(@\w+)|(\*\w+\*)|(_\w+_)|(~\w+~)""")

    fun format(text: String, context: Context): SpannedString {
        val tokens = pattern.findAll(text)
        return buildSpannedString {
            var cursorPosition = 0
            InternalLogger.logD(TAG,tokens.joinToString())
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
                    append(matchResult.value)
                    setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        matchResult.value.lastIndex,
                        FLAGS
                    )
                }
            }
            '*' -> {
                buildSpannedString {
                    val newText = matchResult.value.trim('*')
                    append(newText)
                    setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        newText.lastIndex,
                        FLAGS
                    )
                }
            }
            '_' -> {
                buildSpannedString {
                    val newText = matchResult.value.trim('_')
                    append(newText)
                    setSpan(
                        StyleSpan(Typeface.ITALIC),
                        0,
                        newText.lastIndex,
                        FLAGS
                    )
                }
            }
            '`' -> {
                buildSpannedString {
                    val newText = matchResult.value.trim('`')
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
                    append(newText)
                    setSpan(
                        StrikethroughSpan(),
                        0,
                        newText.lastIndex,
                        FLAGS
                    )
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