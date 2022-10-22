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

@file:Suppress("unused")

object Versions {

    const val VERSION_NAME = "0.0.5" //XX.YY.ZZ [X:Major][Y:Minor][Z:Patch]
    val VERSION_CODE = versionCodeGet()

    const val COMPILE_SDK = 31
    const val BUILD_TOOLS_VERSION = "30.0.3"
    const val MIN_SDK = 21
    const val TARGET_SDK = 31

    const val PLUGIN_ANDROID_GRADLE = "4.0.0-alpha09"
    const val PLUGIN_KOTLIN_GRADLE = "1.4.21"
    const val PLUGIN_GOOGLE_PLAY_SERVICES = "4.3.10"

    const val ANDROIDX_CORE = "1.7.0"
    const val ANDROIDX_APPCOMPAT = "1.4.1"
    const val ACTIVITY_KTX = "1.4.0"
    const val CONSTRAINT_LAYOUT = "1.4.0"
    const val COROUTINES_CORE = "1.5.2"
    const val EMOJI2 = "1.2.0-alpha04"
    const val FIREBASE_BOM = "29.1.0"
    const val FRAGMENT_KTX = "1.4.0"
    const val GLIDE = "4.12.0"
    const val JSOUP = "1.11.3"
    const val KOTLIN = PLUGIN_KOTLIN_GRADLE
    const val LIBPHONENUMBER = "8.12.52"
    const val LIFECYCLE_EXT = "2.2.0"
    const val LIFECYCLE_VIEWMODEL = "2.4.0"
    const val LOTTIE = "3.6.0"
    const val MATERIAL = "1.6.0"
    const val NAVIGATION = "2.3.5"
    const val PHOTOVIEW = "2.0.0"
    const val PINLOG = "1.0.1"
    const val PLAY_SERVICES_AUTH = "20.2.0"
    const val PREFERENCE = "1.1.1"
    const val RECYCLER_VIEW = "1.2.1"
    const val RECYCLER_VIEW_SELECTION = "1.0.0"
    const val RETROFIT = "2.6.2"
    const val ROOM = "2.3.0"

    private fun versionCodeGet(): Int {
        val size = 10000
        var returnVersionCode = ""

        //Indicates a pre-release
        if (VERSION_NAME.startsWith("0")) {
            return 1 * size
        }

        VERSION_NAME.forEach {
            if (it.isDigit()) {
                if (returnVersionCode == "" || returnVersionCode.startsWith("0")) {
                    returnVersionCode = it.toString()
                } else returnVersionCode += it.toString()
            }
        }

        while (returnVersionCode.startsWith("0")) {
            returnVersionCode = returnVersionCode.removePrefix("0")
        }

        if (returnVersionCode.trim().isEmpty()) return 1 * size

        while (returnVersionCode.length != size.toString().length) {
            returnVersionCode += "0"
        }

        return returnVersionCode.toInt()

    }

}
