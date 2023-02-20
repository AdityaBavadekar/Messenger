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

package com.adityaamolbavadekar.messenger.utils

import com.adityaamolbavadekar.messenger.model.EmojiItem

// Parse emoji like this `println(String(Character.toChars(0x23F0)))`
val emojiData = listOf(
    EmojiItem(
        hexCode = "231A..231B",
        emojiContent = "⌚..⌛",
        category = 0,
        description = "watch",
        isRange = true
    ),
    EmojiItem(
        hexCode = "23E9..23EC",
        emojiContent = "⏩..⏬",
        category = 0,
        description = "fast-forward button",
        isRange = true
    ),
    EmojiItem(
        hexCode = "23F0",
        emojiContent = "⏰",
        category = 0,
        description = "alarm clock",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23F3",
        emojiContent = "⏳",
        category = 0,
        description = "hourglass not done",
        isRange = false
    ),
    EmojiItem(
        hexCode = "25FD..25FE",
        emojiContent = "◽..◾",
        category = 0,
        description = "white medium-small square",
        isRange = true
    ),
    EmojiItem(
        hexCode = "2614..2615",
        emojiContent = "☔..☕",
        category = 0,
        description = "umbrella with rain drops",
        isRange = true
    ),
    EmojiItem(
        hexCode = "2648..2653",
        emojiContent = "♈..♓",
        category = 0,
        description = "Aries",
        isRange = true
    ),
    EmojiItem(
        hexCode = "267F",
        emojiContent = "♿",
        category = 0,
        description = "wheelchair symbol",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2693",
        emojiContent = "⚓",
        category = 0,
        description = "anchor",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26A1",
        emojiContent = "⚡",
        category = 0,
        description = "high voltage",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26AA..26AB",
        emojiContent = "⚪..⚫",
        category = 0,
        description = "white circle",
        isRange = true
    ),
    EmojiItem(
        hexCode = "26BD..26BE",
        emojiContent = "⚽..⚾",
        category = 0,
        description = "soccer ball",
        isRange = true
    ),
    EmojiItem(
        hexCode = "26C4..26C5",
        emojiContent = "⛄..⛅",
        category = 0,
        description = "snowman without snow",
        isRange = true
    ),
    EmojiItem(
        hexCode = "26CE",
        emojiContent = "⛎",
        category = 0,
        description = "Ophiuchus",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26D4",
        emojiContent = "⛔",
        category = 0,
        description = "no entry",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26EA",
        emojiContent = "⛪",
        category = 0,
        description = "church",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F2..26F3",
        emojiContent = "⛲..⛳",
        category = 0,
        description = "fountain",
        isRange = true
    ),
    EmojiItem(
        hexCode = "26F5",
        emojiContent = "⛵",
        category = 0,
        description = "sailboat",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26FA",
        emojiContent = "⛺",
        category = 0,
        description = "tent",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26FD",
        emojiContent = "⛽",
        category = 0,
        description = "fuel pump",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2705",
        emojiContent = "✅",
        category = 0,
        description = "check mark button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270A..270B",
        emojiContent = "✊..✋",
        category = 0,
        description = "raised fist",
        isRange = true
    ),
    EmojiItem(
        hexCode = "2728",
        emojiContent = "✨",
        category = 0,
        description = "sparkles",
        isRange = false
    ),
    EmojiItem(
        hexCode = "274C",
        emojiContent = "❌",
        category = 0,
        description = "cross mark",
        isRange = false
    ),
    EmojiItem(
        hexCode = "274E",
        emojiContent = "❎",
        category = 0,
        description = "cross mark button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2753..2755",
        emojiContent = "❓..❕",
        category = 0,
        description = "red question mark",
        isRange = true
    ),
    EmojiItem(
        hexCode = "2757",
        emojiContent = "❗",
        category = 0,
        description = "red exclamation mark",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2795..2797",
        emojiContent = "➕..➗",
        category = 0,
        description = "plus",
        isRange = true
    ),
    EmojiItem(
        hexCode = "27B0",
        emojiContent = "➰",
        category = 0,
        description = "curly loop",
        isRange = false
    ),
    EmojiItem(
        hexCode = "27BF",
        emojiContent = "➿",
        category = 0,
        description = "double curly loop",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2B1B..2B1C",
        emojiContent = "⬛..⬜",
        category = 0,
        description = "black large square",
        isRange = true
    ),
    EmojiItem(
        hexCode = "2B50",
        emojiContent = "⭐",
        category = 0,
        description = "star",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2B55",
        emojiContent = "⭕",
        category = 0,
        description = "hollow red circle",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F004",
        emojiContent = "🀄",
        category = 0,
        description = "mahjong red dragon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F0CF",
        emojiContent = "🃏",
        category = 0,
        description = "joker",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F18E",
        emojiContent = "🆎",
        category = 0,
        description = "AB button (blood type)",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F191..1F19A",
        emojiContent = "🆑..🆚",
        category = 0,
        description = "CL button",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F201",
        emojiContent = "🈁",
        category = 0,
        description = "Japanese “here” button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F21A",
        emojiContent = "🈚",
        category = 0,
        description = "Japanese “free of charge” button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F22F",
        emojiContent = "🈯",
        category = 0,
        description = "Japanese “reserved” button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F232..1F236",
        emojiContent = "🈲..🈶",
        category = 0,
        description = "Japanese “prohibited” button",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F238..1F23A",
        emojiContent = "🈸..🈺",
        category = 0,
        description = "Japanese “application” button",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F250..1F251",
        emojiContent = "🉐..🉑",
        category = 0,
        description = "Japanese “bargain” button",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F300..1F30C",
        emojiContent = "🌀..🌌",
        category = 0,
        description = "cyclone",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F30D..1F30E",
        emojiContent = "🌍..🌎",
        category = 0,
        description = "globe showing Europe-Africa",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F30F",
        emojiContent = "🌏",
        category = 0,
        description = "globe showing Asia-Australia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F310",
        emojiContent = "🌐",
        category = 0,
        description = "globe with meridians",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F311",
        emojiContent = "🌑",
        category = 0,
        description = "new moon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F312",
        emojiContent = "🌒",
        category = 0,
        description = "waxing crescent moon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F313..1F315",
        emojiContent = "🌓..🌕",
        category = 0,
        description = "first quarter moon",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F316..1F318",
        emojiContent = "🌖..🌘",
        category = 0,
        description = "waning gibbous moon",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F319",
        emojiContent = "🌙",
        category = 0,
        description = "crescent moon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F31A",
        emojiContent = "🌚",
        category = 0,
        description = "new moon face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F31B",
        emojiContent = "🌛",
        category = 0,
        description = "first quarter moon face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F31C",
        emojiContent = "🌜",
        category = 0,
        description = "last quarter moon face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F31D..1F31E",
        emojiContent = "🌝..🌞",
        category = 0,
        description = "full moon face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F31F..1F320",
        emojiContent = "🌟..🌠",
        category = 0,
        description = "glowing star",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F32D..1F32F",
        emojiContent = "🌭..🌯",
        category = 0,
        description = "hot dog",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F330..1F331",
        emojiContent = "🌰..🌱",
        category = 0,
        description = "chestnut",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F332..1F333",
        emojiContent = "🌲..🌳",
        category = 0,
        description = "evergreen tree",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F334..1F335",
        emojiContent = "🌴..🌵",
        category = 0,
        description = "palm tree",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F337..1F34A",
        emojiContent = "🌷..🍊",
        category = 0,
        description = "tulip",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F34B",
        emojiContent = "🍋",
        category = 0,
        description = "lemon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F34C..1F34F",
        emojiContent = "🍌..🍏",
        category = 0,
        description = "banana",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F350",
        emojiContent = "🍐",
        category = 0,
        description = "pear",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F351..1F37B",
        emojiContent = "🍑..🍻",
        category = 0,
        description = "peach",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F37C",
        emojiContent = "🍼",
        category = 0,
        description = "baby bottle",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F37E..1F37F",
        emojiContent = "🍾..🍿",
        category = 0,
        description = "bottle with popping cork",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F380..1F393",
        emojiContent = "🎀..🎓",
        category = 0,
        description = "ribbon",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F3A0..1F3C4",
        emojiContent = "🎠..🏄",
        category = 0,
        description = "carousel horse",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F3C5",
        emojiContent = "🏅",
        category = 0,
        description = "sports medal",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C6",
        emojiContent = "🏆",
        category = 0,
        description = "trophy",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C7",
        emojiContent = "🏇",
        category = 0,
        description = "horse racing",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C8",
        emojiContent = "🏈",
        category = 0,
        description = "american football",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C9",
        emojiContent = "🏉",
        category = 0,
        description = "rugby football",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CA",
        emojiContent = "🏊",
        category = 0,
        description = "person swimming",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CF..1F3D3",
        emojiContent = "🏏..🏓",
        category = 0,
        description = "cricket game",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F3E0..1F3E3",
        emojiContent = "🏠..🏣",
        category = 0,
        description = "house",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F3E4",
        emojiContent = "🏤",
        category = 0,
        description = "post office",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3E5..1F3F0",
        emojiContent = "🏥..🏰",
        category = 0,
        description = "hospital",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F3F4",
        emojiContent = "🏴",
        category = 0,
        description = "black flag",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3F8..1F407",
        emojiContent = "🏸..🐇",
        category = 0,
        description = "badminton",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F408",
        emojiContent = "🐈",
        category = 0,
        description = "cat",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F409..1F40B",
        emojiContent = "🐉..🐋",
        category = 0,
        description = "dragon",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F40C..1F40E",
        emojiContent = "🐌..🐎",
        category = 0,
        description = "snail",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F40F..1F410",
        emojiContent = "🐏..🐐",
        category = 0,
        description = "ram",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F411..1F412",
        emojiContent = "🐑..🐒",
        category = 0,
        description = "ewe",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F413",
        emojiContent = "🐓",
        category = 0,
        description = "rooster",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F414",
        emojiContent = "🐔",
        category = 0,
        description = "chicken",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F415",
        emojiContent = "🐕",
        category = 0,
        description = "dog",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F416",
        emojiContent = "🐖",
        category = 0,
        description = "pig",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F417..1F429",
        emojiContent = "🐗..🐩",
        category = 0,
        description = "boar",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F42A",
        emojiContent = "🐪",
        category = 0,
        description = "camel",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F42B..1F43E",
        emojiContent = "🐫..🐾",
        category = 0,
        description = "two-hump camel",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F440",
        emojiContent = "👀",
        category = 0,
        description = "eyes",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F442..1F464",
        emojiContent = "👂..👤",
        category = 0,
        description = "ear",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F465",
        emojiContent = "👥",
        category = 0,
        description = "busts in silhouette",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F466..1F46B",
        emojiContent = "👦..👫",
        category = 0,
        description = "boy",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F46C..1F46D",
        emojiContent = "👬..👭",
        category = 0,
        description = "men holding hands",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F46E..1F4AC",
        emojiContent = "👮..💬",
        category = 0,
        description = "police officer",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F4AD",
        emojiContent = "💭",
        category = 0,
        description = "thought balloon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4AE..1F4B5",
        emojiContent = "💮..💵",
        category = 0,
        description = "white flower",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F4B6..1F4B7",
        emojiContent = "💶..💷",
        category = 0,
        description = "euro banknote",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F4B8..1F4EB",
        emojiContent = "💸..📫",
        category = 0,
        description = "money with wings",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F4EC..1F4ED",
        emojiContent = "📬..📭",
        category = 0,
        description = "open mailbox with raised flag",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F4EE",
        emojiContent = "📮",
        category = 0,
        description = "postbox",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4EF",
        emojiContent = "📯",
        category = 0,
        description = "postal horn",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4F0..1F4F4",
        emojiContent = "📰..📴",
        category = 0,
        description = "newspaper",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F4F5",
        emojiContent = "📵",
        category = 0,
        description = "no mobile phones",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4F6..1F4F7",
        emojiContent = "📶..📷",
        category = 0,
        description = "antenna bars",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F4F8",
        emojiContent = "📸",
        category = 0,
        description = "camera with flash",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4F9..1F4FC",
        emojiContent = "📹..📼",
        category = 0,
        description = "video camera",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F4FF..1F502",
        emojiContent = "📿..🔂",
        category = 0,
        description = "prayer beads",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F503",
        emojiContent = "🔃",
        category = 0,
        description = "clockwise vertical arrows",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F504..1F507",
        emojiContent = "🔄..🔇",
        category = 0,
        description = "counterclockwise arrows button",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F508",
        emojiContent = "🔈",
        category = 0,
        description = "speaker low volume",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F509",
        emojiContent = "🔉",
        category = 0,
        description = "speaker medium volume",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F50A..1F514",
        emojiContent = "🔊..🔔",
        category = 0,
        description = "speaker high volume",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F515",
        emojiContent = "🔕",
        category = 0,
        description = "bell with slash",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F516..1F52B",
        emojiContent = "🔖..🔫",
        category = 0,
        description = "bookmark",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F52C..1F52D",
        emojiContent = "🔬..🔭",
        category = 0,
        description = "microscope",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F52E..1F53D",
        emojiContent = "🔮..🔽",
        category = 0,
        description = "crystal ball",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F54B..1F54E",
        emojiContent = "🕋..🕎",
        category = 0,
        description = "kaaba",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F550..1F55B",
        emojiContent = "🕐..🕛",
        category = 0,
        description = "one o’clock",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F55C..1F567",
        emojiContent = "🕜..🕧",
        category = 0,
        description = "one-thirty",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F57A",
        emojiContent = "🕺",
        category = 0,
        description = "man dancing",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F595..1F596",
        emojiContent = "🖕..🖖",
        category = 0,
        description = "middle finger",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F5A4",
        emojiContent = "🖤",
        category = 0,
        description = "black heart",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5FB..1F5FF",
        emojiContent = "🗻..🗿",
        category = 0,
        description = "mount fuji",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F600",
        emojiContent = "😀",
        category = 0,
        description = "grinning face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F601..1F606",
        emojiContent = "😁..😆",
        category = 0,
        description = "beaming face with smiling eyes",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F607..1F608",
        emojiContent = "😇..😈",
        category = 0,
        description = "smiling face with halo",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F609..1F60D",
        emojiContent = "😉..😍",
        category = 0,
        description = "winking face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F60E",
        emojiContent = "😎",
        category = 0,
        description = "smiling face with sunglasses",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F60F",
        emojiContent = "😏",
        category = 0,
        description = "smirking face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F610",
        emojiContent = "😐",
        category = 0,
        description = "neutral face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F611",
        emojiContent = "😑",
        category = 0,
        description = "expressionless face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F612..1F614",
        emojiContent = "😒..😔",
        category = 0,
        description = "unamused face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F615",
        emojiContent = "😕",
        category = 0,
        description = "confused face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F616",
        emojiContent = "😖",
        category = 0,
        description = "confounded face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F617",
        emojiContent = "😗",
        category = 0,
        description = "kissing face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F618",
        emojiContent = "😘",
        category = 0,
        description = "face blowing a kiss",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F619",
        emojiContent = "😙",
        category = 0,
        description = "kissing face with smiling eyes",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F61A",
        emojiContent = "😚",
        category = 0,
        description = "kissing face with closed eyes",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F61B",
        emojiContent = "😛",
        category = 0,
        description = "face with tongue",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F61C..1F61E",
        emojiContent = "😜..😞",
        category = 0,
        description = "winking face with tongue",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F61F",
        emojiContent = "😟",
        category = 0,
        description = "worried face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F620..1F625",
        emojiContent = "😠..😥",
        category = 0,
        description = "angry face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F626..1F627",
        emojiContent = "😦..😧",
        category = 0,
        description = "frowning face with open mouth",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F628..1F62B",
        emojiContent = "😨..😫",
        category = 0,
        description = "fearful face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F62C",
        emojiContent = "😬",
        category = 0,
        description = "grimacing face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F62D",
        emojiContent = "😭",
        category = 0,
        description = "loudly crying face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F62E..1F62F",
        emojiContent = "😮..😯",
        category = 0,
        description = "face with open mouth",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F630..1F633",
        emojiContent = "😰..😳",
        category = 0,
        description = "anxious face with sweat",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F634",
        emojiContent = "😴",
        category = 0,
        description = "sleeping face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F635",
        emojiContent = "😵",
        category = 0,
        description = "face with crossed-out eyes",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F636",
        emojiContent = "😶",
        category = 0,
        description = "face without mouth",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F637..1F640",
        emojiContent = "😷..🙀",
        category = 0,
        description = "face with medical mask",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F641..1F644",
        emojiContent = "🙁..🙄",
        category = 0,
        description = "slightly frowning face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F645..1F64F",
        emojiContent = "🙅..🙏",
        category = 0,
        description = "person gesturing NO",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F680",
        emojiContent = "🚀",
        category = 0,
        description = "rocket",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F681..1F682",
        emojiContent = "🚁..🚂",
        category = 0,
        description = "helicopter",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F683..1F685",
        emojiContent = "🚃..🚅",
        category = 0,
        description = "railway car",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F686",
        emojiContent = "🚆",
        category = 0,
        description = "train",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F687",
        emojiContent = "🚇",
        category = 0,
        description = "metro",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F688",
        emojiContent = "🚈",
        category = 0,
        description = "light rail",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F689",
        emojiContent = "🚉",
        category = 0,
        description = "station",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F68A..1F68B",
        emojiContent = "🚊..🚋",
        category = 0,
        description = "tram",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F68C",
        emojiContent = "🚌",
        category = 0,
        description = "bus",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F68D",
        emojiContent = "🚍",
        category = 0,
        description = "oncoming bus",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F68E",
        emojiContent = "🚎",
        category = 0,
        description = "trolleybus",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F68F",
        emojiContent = "🚏",
        category = 0,
        description = "bus stop",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F690",
        emojiContent = "🚐",
        category = 0,
        description = "minibus",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F691..1F693",
        emojiContent = "🚑..🚓",
        category = 0,
        description = "ambulance",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F694",
        emojiContent = "🚔",
        category = 0,
        description = "oncoming police car",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F695",
        emojiContent = "🚕",
        category = 0,
        description = "taxi",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F696",
        emojiContent = "🚖",
        category = 0,
        description = "oncoming taxi",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F697",
        emojiContent = "🚗",
        category = 0,
        description = "automobile",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F698",
        emojiContent = "🚘",
        category = 0,
        description = "oncoming automobile",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F699..1F69A",
        emojiContent = "🚙..🚚",
        category = 0,
        description = "sport utility vehicle",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F69B..1F6A1",
        emojiContent = "🚛..🚡",
        category = 0,
        description = "articulated lorry",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6A2",
        emojiContent = "🚢",
        category = 0,
        description = "ship",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6A3",
        emojiContent = "🚣",
        category = 0,
        description = "person rowing boat",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6A4..1F6A5",
        emojiContent = "🚤..🚥",
        category = 0,
        description = "speedboat",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6A6",
        emojiContent = "🚦",
        category = 0,
        description = "vertical traffic light",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6A7..1F6AD",
        emojiContent = "🚧..🚭",
        category = 0,
        description = "construction",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6AE..1F6B1",
        emojiContent = "🚮..🚱",
        category = 0,
        description = "litter in bin sign",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6B2",
        emojiContent = "🚲",
        category = 0,
        description = "bicycle",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B3..1F6B5",
        emojiContent = "🚳..🚵",
        category = 0,
        description = "no bicycles",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6B6",
        emojiContent = "🚶",
        category = 0,
        description = "person walking",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B7..1F6B8",
        emojiContent = "🚷..🚸",
        category = 0,
        description = "no pedestrians",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6B9..1F6BE",
        emojiContent = "🚹..🚾",
        category = 0,
        description = "men’s room",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6BF",
        emojiContent = "🚿",
        category = 0,
        description = "shower",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6C0",
        emojiContent = "🛀",
        category = 0,
        description = "person taking bath",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6C1..1F6C5",
        emojiContent = "🛁..🛅",
        category = 0,
        description = "bathtub",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6CC",
        emojiContent = "🛌",
        category = 0,
        description = "person in bed",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6D0",
        emojiContent = "🛐",
        category = 0,
        description = "place of worship",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6D1..1F6D2",
        emojiContent = "🛑..🛒",
        category = 0,
        description = "stop sign",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6D5",
        emojiContent = "🛕",
        category = 0,
        description = "hindu temple",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6D6..1F6D7",
        emojiContent = "🛖..🛗",
        category = 0,
        description = "hut",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6DD..1F6DF",
        emojiContent = "🛝..🛟",
        category = 0,
        description = "playground slide",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6EB..1F6EC",
        emojiContent = "🛫..🛬",
        category = 0,
        description = "airplane departure",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6F4..1F6F6",
        emojiContent = "🛴..🛶",
        category = 0,
        description = "kick scooter",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6F7..1F6F8",
        emojiContent = "🛷..🛸",
        category = 0,
        description = "sled",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F6F9",
        emojiContent = "🛹",
        category = 0,
        description = "skateboard",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6FA",
        emojiContent = "🛺",
        category = 0,
        description = "auto rickshaw",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6FB..1F6FC",
        emojiContent = "🛻..🛼",
        category = 0,
        description = "pickup truck",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F7E0..1F7EB",
        emojiContent = "🟠..🟫",
        category = 0,
        description = "orange circle",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F7F0",
        emojiContent = "🟰",
        category = 0,
        description = "heavy equals sign",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90C",
        emojiContent = "🤌",
        category = 0,
        description = "pinched fingers",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90D..1F90F",
        emojiContent = "🤍..🤏",
        category = 0,
        description = "white heart",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F910..1F918",
        emojiContent = "🤐..🤘",
        category = 0,
        description = "zipper-mouth face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F919..1F91E",
        emojiContent = "🤙..🤞",
        category = 0,
        description = "call me hand",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F91F",
        emojiContent = "🤟",
        category = 0,
        description = "love-you gesture",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F920..1F927",
        emojiContent = "🤠..🤧",
        category = 0,
        description = "cowboy hat face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F928..1F92F",
        emojiContent = "🤨..🤯",
        category = 0,
        description = "face with raised eyebrow",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F930",
        emojiContent = "🤰",
        category = 0,
        description = "pregnant woman",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F931..1F932",
        emojiContent = "🤱..🤲",
        category = 0,
        description = "breast-feeding",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F933..1F93A",
        emojiContent = "🤳..🤺",
        category = 0,
        description = "selfie",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F93C..1F93E",
        emojiContent = "🤼..🤾",
        category = 0,
        description = "people wrestling",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F93F",
        emojiContent = "🤿",
        category = 0,
        description = "diving mask",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F940..1F945",
        emojiContent = "🥀..🥅",
        category = 0,
        description = "wilted flower",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F947..1F94B",
        emojiContent = "🥇..🥋",
        category = 0,
        description = "1st place medal",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F94C",
        emojiContent = "🥌",
        category = 0,
        description = "curling stone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F94D..1F94F",
        emojiContent = "🥍..🥏",
        category = 0,
        description = "lacrosse",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F950..1F95E",
        emojiContent = "🥐..🥞",
        category = 0,
        description = "croissant",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F95F..1F96B",
        emojiContent = "🥟..🥫",
        category = 0,
        description = "dumpling",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F96C..1F970",
        emojiContent = "🥬..🥰",
        category = 0,
        description = "leafy green",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F971",
        emojiContent = "🥱",
        category = 0,
        description = "yawning face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F972",
        emojiContent = "🥲",
        category = 0,
        description = "smiling face with tear",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F973..1F976",
        emojiContent = "🥳..🥶",
        category = 0,
        description = "partying face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F977..1F978",
        emojiContent = "🥷..🥸",
        category = 0,
        description = "ninja",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F979",
        emojiContent = "🥹",
        category = 0,
        description = "face holding back tears",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F97A",
        emojiContent = "🥺",
        category = 0,
        description = "pleading face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F97B",
        emojiContent = "🥻",
        category = 0,
        description = "sari",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F97C..1F97F",
        emojiContent = "🥼..🥿",
        category = 0,
        description = "lab coat",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F980..1F984",
        emojiContent = "🦀..🦄",
        category = 0,
        description = "crab",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F985..1F991",
        emojiContent = "🦅..🦑",
        category = 0,
        description = "eagle",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F992..1F997",
        emojiContent = "🦒..🦗",
        category = 0,
        description = "giraffe",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F998..1F9A2",
        emojiContent = "🦘..🦢",
        category = 0,
        description = "kangaroo",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9A3..1F9A4",
        emojiContent = "🦣..🦤",
        category = 0,
        description = "mammoth",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9A5..1F9AA",
        emojiContent = "🦥..🦪",
        category = 0,
        description = "sloth",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9AB..1F9AD",
        emojiContent = "🦫..🦭",
        category = 0,
        description = "beaver",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9AE..1F9AF",
        emojiContent = "🦮..🦯",
        category = 0,
        description = "guide dog",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9B0..1F9B9",
        emojiContent = "🦰..🦹",
        category = 0,
        description = "red hair",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9BA..1F9BF",
        emojiContent = "🦺..🦿",
        category = 0,
        description = "safety vest",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9C0",
        emojiContent = "🧀",
        category = 0,
        description = "cheese wedge",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9C1..1F9C2",
        emojiContent = "🧁..🧂",
        category = 0,
        description = "cupcake",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9C3..1F9CA",
        emojiContent = "🧃..🧊",
        category = 0,
        description = "beverage box",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9CB",
        emojiContent = "🧋",
        category = 0,
        description = "bubble tea",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CC",
        emojiContent = "🧌",
        category = 0,
        description = "troll",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CD..1F9CF",
        emojiContent = "🧍..🧏",
        category = 0,
        description = "person standing",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9D0..1F9E6",
        emojiContent = "🧐..🧦",
        category = 0,
        description = "face with monocle",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1F9E7..1F9FF",
        emojiContent = "🧧..🧿",
        category = 0,
        description = "red envelope",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FA70..1FA73",
        emojiContent = "🩰..🩳",
        category = 0,
        description = "ballet shoes",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FA74",
        emojiContent = "🩴",
        category = 0,
        description = "thong sandal",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FA78..1FA7A",
        emojiContent = "🩸..🩺",
        category = 0,
        description = "drop of blood",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FA7B..1FA7C",
        emojiContent = "🩻..🩼",
        category = 0,
        description = "x-ray",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FA80..1FA82",
        emojiContent = "🪀..🪂",
        category = 0,
        description = "yo-yo",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FA83..1FA86",
        emojiContent = "🪃..🪆",
        category = 0,
        description = "boomerang",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FA90..1FA95",
        emojiContent = "🪐..🪕",
        category = 0,
        description = "ringed planet",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FA96..1FAA8",
        emojiContent = "🪖..🪨",
        category = 0,
        description = "military helmet",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FAA9..1FAAC",
        emojiContent = "🪩..🪬",
        category = 0,
        description = "mirror ball",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FAB0..1FAB6",
        emojiContent = "🪰..🪶",
        category = 0,
        description = "fly",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FAB7..1FABA",
        emojiContent = "🪷..🪺",
        category = 0,
        description = "lotus",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FAC0..1FAC2",
        emojiContent = "🫀..🫂",
        category = 0,
        description = "anatomical heart",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FAC3..1FAC5",
        emojiContent = "🫃..🫅",
        category = 0,
        description = "pregnant man",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FAD0..1FAD6",
        emojiContent = "🫐..🫖",
        category = 0,
        description = "blueberries",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FAD7..1FAD9",
        emojiContent = "🫗..🫙",
        category = 0,
        description = "pouring liquid",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FAE0..1FAE7",
        emojiContent = "🫠..🫧",
        category = 0,
        description = "melting face",
        isRange = true
    ),
    EmojiItem(
        hexCode = "1FAF0..1FAF6",
        emojiContent = "🫰..🫶",
        category = 0,
        description = "hand with index finger and thumb crossed",
        isRange = true
    ),
    EmojiItem(
        hexCode = "00A9 FE0F",
        emojiContent = "©️",
        category = 0,
        description = "copyright",
        isRange = false
    ),
    EmojiItem(
        hexCode = "00AE FE0F",
        emojiContent = "®️",
        category = 0,
        description = "registered",
        isRange = false
    ),
    EmojiItem(
        hexCode = "203C FE0F",
        emojiContent = "‼️",
        category = 0,
        description = "double exclamation mark",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2049 FE0F",
        emojiContent = "⁉️",
        category = 0,
        description = "exclamation question mark",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2122 FE0F",
        emojiContent = "™️",
        category = 0,
        description = "trade mark",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2139 FE0F",
        emojiContent = "ℹ️",
        category = 0,
        description = "information",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2194 FE0F",
        emojiContent = "↔️",
        category = 0,
        description = "left-right arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2195 FE0F",
        emojiContent = "↕️",
        category = 0,
        description = "up-down arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2196 FE0F",
        emojiContent = "↖️",
        category = 0,
        description = "up-left arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2197 FE0F",
        emojiContent = "↗️",
        category = 0,
        description = "up-right arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2198 FE0F",
        emojiContent = "↘️",
        category = 0,
        description = "down-right arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2199 FE0F",
        emojiContent = "↙️",
        category = 0,
        description = "down-left arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "21A9 FE0F",
        emojiContent = "↩️",
        category = 0,
        description = "right arrow curving left",
        isRange = false
    ),
    EmojiItem(
        hexCode = "21AA FE0F",
        emojiContent = "↪️",
        category = 0,
        description = "left arrow curving right",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2328 FE0F",
        emojiContent = "⌨️",
        category = 0,
        description = "keyboard",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23CF FE0F",
        emojiContent = "⏏️",
        category = 0,
        description = "eject button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23ED FE0F",
        emojiContent = "⏭️",
        category = 0,
        description = "next track button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23EE FE0F",
        emojiContent = "⏮️",
        category = 0,
        description = "last track button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23EF FE0F",
        emojiContent = "⏯️",
        category = 0,
        description = "play or pause button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23F1 FE0F",
        emojiContent = "⏱️",
        category = 0,
        description = "stopwatch",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23F2 FE0F",
        emojiContent = "⏲️",
        category = 0,
        description = "timer clock",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23F8 FE0F",
        emojiContent = "⏸️",
        category = 0,
        description = "pause button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23F9 FE0F",
        emojiContent = "⏹️",
        category = 0,
        description = "stop button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "23FA FE0F",
        emojiContent = "⏺️",
        category = 0,
        description = "record button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "24C2 FE0F",
        emojiContent = "Ⓜ️",
        category = 0,
        description = "circled M",
        isRange = false
    ),
    EmojiItem(
        hexCode = "25AA FE0F",
        emojiContent = "▪️",
        category = 0,
        description = "black small square",
        isRange = false
    ),
    EmojiItem(
        hexCode = "25AB FE0F",
        emojiContent = "▫️",
        category = 0,
        description = "white small square",
        isRange = false
    ),
    EmojiItem(
        hexCode = "25B6 FE0F",
        emojiContent = "▶️",
        category = 0,
        description = "play button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "25C0 FE0F",
        emojiContent = "◀️",
        category = 0,
        description = "reverse button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "25FB FE0F",
        emojiContent = "◻️",
        category = 0,
        description = "white medium square",
        isRange = false
    ),
    EmojiItem(
        hexCode = "25FC FE0F",
        emojiContent = "◼️",
        category = 0,
        description = "black medium square",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2600 FE0F",
        emojiContent = "☀️",
        category = 0,
        description = "sun",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2601 FE0F",
        emojiContent = "☁️",
        category = 0,
        description = "cloud",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2602 FE0F",
        emojiContent = "☂️",
        category = 0,
        description = "umbrella",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2603 FE0F",
        emojiContent = "☃️",
        category = 0,
        description = "snowman",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2604 FE0F",
        emojiContent = "☄️",
        category = 0,
        description = "comet",
        isRange = false
    ),
    EmojiItem(
        hexCode = "260E FE0F",
        emojiContent = "☎️",
        category = 0,
        description = "telephone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2611 FE0F",
        emojiContent = "☑️",
        category = 0,
        description = "check box with check",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2618 FE0F",
        emojiContent = "☘️",
        category = 0,
        description = "shamrock",
        isRange = false
    ),
    EmojiItem(
        hexCode = "261D FE0F",
        emojiContent = "☝️",
        category = 0,
        description = "index pointing up",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2620 FE0F",
        emojiContent = "☠️",
        category = 0,
        description = "skull and crossbones",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2622 FE0F",
        emojiContent = "☢️",
        category = 0,
        description = "radioactive",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2623 FE0F",
        emojiContent = "☣️",
        category = 0,
        description = "biohazard",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2626 FE0F",
        emojiContent = "☦️",
        category = 0,
        description = "orthodox cross",
        isRange = false
    ),
    EmojiItem(
        hexCode = "262A FE0F",
        emojiContent = "☪️",
        category = 0,
        description = "star and crescent",
        isRange = false
    ),
    EmojiItem(
        hexCode = "262E FE0F",
        emojiContent = "☮️",
        category = 0,
        description = "peace symbol",
        isRange = false
    ),
    EmojiItem(
        hexCode = "262F FE0F",
        emojiContent = "☯️",
        category = 0,
        description = "yin yang",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2638 FE0F",
        emojiContent = "☸️",
        category = 0,
        description = "wheel of dharma",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2639 FE0F",
        emojiContent = "☹️",
        category = 0,
        description = "frowning face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "263A FE0F",
        emojiContent = "☺️",
        category = 0,
        description = "smiling face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2640 FE0F",
        emojiContent = "♀️",
        category = 0,
        description = "female sign",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2642 FE0F",
        emojiContent = "♂️",
        category = 0,
        description = "male sign",
        isRange = false
    ),
    EmojiItem(
        hexCode = "265F FE0F",
        emojiContent = "♟️",
        category = 0,
        description = "chess pawn",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2660 FE0F",
        emojiContent = "♠️",
        category = 0,
        description = "spade suit",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2663 FE0F",
        emojiContent = "♣️",
        category = 0,
        description = "club suit",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2665 FE0F",
        emojiContent = "♥️",
        category = 0,
        description = "heart suit",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2666 FE0F",
        emojiContent = "♦️",
        category = 0,
        description = "diamond suit",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2668 FE0F",
        emojiContent = "♨️",
        category = 0,
        description = "hot springs",
        isRange = false
    ),
    EmojiItem(
        hexCode = "267B FE0F",
        emojiContent = "♻️",
        category = 0,
        description = "recycling symbol",
        isRange = false
    ),
    EmojiItem(
        hexCode = "267E FE0F",
        emojiContent = "♾️",
        category = 0,
        description = "infinity",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2692 FE0F",
        emojiContent = "⚒️",
        category = 0,
        description = "hammer and pick",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2694 FE0F",
        emojiContent = "⚔️",
        category = 0,
        description = "crossed swords",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2695 FE0F",
        emojiContent = "⚕️",
        category = 0,
        description = "medical symbol",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2696 FE0F",
        emojiContent = "⚖️",
        category = 0,
        description = "balance scale",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2697 FE0F",
        emojiContent = "⚗️",
        category = 0,
        description = "alembic",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2699 FE0F",
        emojiContent = "⚙️",
        category = 0,
        description = "gear",
        isRange = false
    ),
    EmojiItem(
        hexCode = "269B FE0F",
        emojiContent = "⚛️",
        category = 0,
        description = "atom symbol",
        isRange = false
    ),
    EmojiItem(
        hexCode = "269C FE0F",
        emojiContent = "⚜️",
        category = 0,
        description = "fleur-de-lis",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26A0 FE0F",
        emojiContent = "⚠️",
        category = 0,
        description = "warning",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26A7 FE0F",
        emojiContent = "⚧️",
        category = 0,
        description = "transgender symbol",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26B0 FE0F",
        emojiContent = "⚰️",
        category = 0,
        description = "coffin",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26B1 FE0F",
        emojiContent = "⚱️",
        category = 0,
        description = "funeral urn",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26C8 FE0F",
        emojiContent = "⛈️",
        category = 0,
        description = "cloud with lightning and rain",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26CF FE0F",
        emojiContent = "⛏️",
        category = 0,
        description = "pick",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26D1 FE0F",
        emojiContent = "⛑️",
        category = 0,
        description = "rescue worker’s helmet",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26D3 FE0F",
        emojiContent = "⛓️",
        category = 0,
        description = "chains",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26E9 FE0F",
        emojiContent = "⛩️",
        category = 0,
        description = "shinto shrine",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F0 FE0F",
        emojiContent = "⛰️",
        category = 0,
        description = "mountain",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F1 FE0F",
        emojiContent = "⛱️",
        category = 0,
        description = "umbrella on ground",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F4 FE0F",
        emojiContent = "⛴️",
        category = 0,
        description = "ferry",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F7 FE0F",
        emojiContent = "⛷️",
        category = 0,
        description = "skier",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F8 FE0F",
        emojiContent = "⛸️",
        category = 0,
        description = "ice skate",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F9 FE0F",
        emojiContent = "⛹️",
        category = 0,
        description = "person bouncing ball",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2702 FE0F",
        emojiContent = "✂️",
        category = 0,
        description = "scissors",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2708 FE0F",
        emojiContent = "✈️",
        category = 0,
        description = "airplane",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2709 FE0F",
        emojiContent = "✉️",
        category = 0,
        description = "envelope",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270C FE0F",
        emojiContent = "✌️",
        category = 0,
        description = "victory hand",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270D FE0F",
        emojiContent = "✍️",
        category = 0,
        description = "writing hand",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270F FE0F",
        emojiContent = "✏️",
        category = 0,
        description = "pencil",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2712 FE0F",
        emojiContent = "✒️",
        category = 0,
        description = "black nib",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2714 FE0F",
        emojiContent = "✔️",
        category = 0,
        description = "check mark",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2716 FE0F",
        emojiContent = "✖️",
        category = 0,
        description = "multiply",
        isRange = false
    ),
    EmojiItem(
        hexCode = "271D FE0F",
        emojiContent = "✝️",
        category = 0,
        description = "latin cross",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2721 FE0F",
        emojiContent = "✡️",
        category = 0,
        description = "star of David",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2733 FE0F",
        emojiContent = "✳️",
        category = 0,
        description = "eight-spoked asterisk",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2734 FE0F",
        emojiContent = "✴️",
        category = 0,
        description = "eight-pointed star",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2744 FE0F",
        emojiContent = "❄️",
        category = 0,
        description = "snowflake",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2747 FE0F",
        emojiContent = "❇️",
        category = 0,
        description = "sparkle",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2763 FE0F",
        emojiContent = "❣️",
        category = 0,
        description = "heart exclamation",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2764 FE0F",
        emojiContent = "❤️",
        category = 0,
        description = "red heart",
        isRange = false
    ),
    EmojiItem(
        hexCode = "27A1 FE0F",
        emojiContent = "➡️",
        category = 0,
        description = "right arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2934 FE0F",
        emojiContent = "⤴️",
        category = 0,
        description = "right arrow curving up",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2935 FE0F",
        emojiContent = "⤵️",
        category = 0,
        description = "right arrow curving down",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2B05 FE0F",
        emojiContent = "⬅️",
        category = 0,
        description = "left arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2B06 FE0F",
        emojiContent = "⬆️",
        category = 0,
        description = "up arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "2B07 FE0F",
        emojiContent = "⬇️",
        category = 0,
        description = "down arrow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "3030 FE0F",
        emojiContent = "〰️",
        category = 0,
        description = "wavy dash",
        isRange = false
    ),
    EmojiItem(
        hexCode = "303D FE0F",
        emojiContent = "〽️",
        category = 0,
        description = "part alternation mark",
        isRange = false
    ),
    EmojiItem(
        hexCode = "3297 FE0F",
        emojiContent = "㊗️",
        category = 0,
        description = "Japanese “congratulations” button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "3299 FE0F",
        emojiContent = "㊙️",
        category = 0,
        description = "Japanese “secret” button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F170 FE0F",
        emojiContent = "🅰️",
        category = 0,
        description = "A button (blood type)",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F171 FE0F",
        emojiContent = "🅱️",
        category = 0,
        description = "B button (blood type)",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F17E FE0F",
        emojiContent = "🅾️",
        category = 0,
        description = "O button (blood type)",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F17F FE0F",
        emojiContent = "🅿️",
        category = 0,
        description = "P button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F202 FE0F",
        emojiContent = "🈂️",
        category = 0,
        description = "Japanese “service charge” button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F237 FE0F",
        emojiContent = "🈷️",
        category = 0,
        description = "Japanese “monthly amount” button",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F321 FE0F",
        emojiContent = "🌡️",
        category = 0,
        description = "thermometer",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F324 FE0F",
        emojiContent = "🌤️",
        category = 0,
        description = "sun behind small cloud",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F325 FE0F",
        emojiContent = "🌥️",
        category = 0,
        description = "sun behind large cloud",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F326 FE0F",
        emojiContent = "🌦️",
        category = 0,
        description = "sun behind rain cloud",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F327 FE0F",
        emojiContent = "🌧️",
        category = 0,
        description = "cloud with rain",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F328 FE0F",
        emojiContent = "🌨️",
        category = 0,
        description = "cloud with snow",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F329 FE0F",
        emojiContent = "🌩️",
        category = 0,
        description = "cloud with lightning",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F32A FE0F",
        emojiContent = "🌪️",
        category = 0,
        description = "tornado",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F32B FE0F",
        emojiContent = "🌫️",
        category = 0,
        description = "fog",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F32C FE0F",
        emojiContent = "🌬️",
        category = 0,
        description = "wind face",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F336 FE0F",
        emojiContent = "🌶️",
        category = 0,
        description = "hot pepper",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F37D FE0F",
        emojiContent = "🍽️",
        category = 0,
        description = "fork and knife with plate",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F396 FE0F",
        emojiContent = "🎖️",
        category = 0,
        description = "military medal",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F397 FE0F",
        emojiContent = "🎗️",
        category = 0,
        description = "reminder ribbon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F399 FE0F",
        emojiContent = "🎙️",
        category = 0,
        description = "studio microphone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F39A FE0F",
        emojiContent = "🎚️",
        category = 0,
        description = "level slider",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F39B FE0F",
        emojiContent = "🎛️",
        category = 0,
        description = "control knobs",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F39E FE0F",
        emojiContent = "🎞️",
        category = 0,
        description = "film frames",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F39F FE0F",
        emojiContent = "🎟️",
        category = 0,
        description = "admission tickets",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CB FE0F",
        emojiContent = "🏋️",
        category = 0,
        description = "person lifting weights",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CC FE0F",
        emojiContent = "🏌️",
        category = 0,
        description = "person golfing",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CD FE0F",
        emojiContent = "🏍️",
        category = 0,
        description = "motorcycle",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CE FE0F",
        emojiContent = "🏎️",
        category = 0,
        description = "racing car",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3D4 FE0F",
        emojiContent = "🏔️",
        category = 0,
        description = "snow-capped mountain",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3D5 FE0F",
        emojiContent = "🏕️",
        category = 0,
        description = "camping",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3D6 FE0F",
        emojiContent = "🏖️",
        category = 0,
        description = "beach with umbrella",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3D7 FE0F",
        emojiContent = "🏗️",
        category = 0,
        description = "building construction",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3D8 FE0F",
        emojiContent = "🏘️",
        category = 0,
        description = "houses",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3D9 FE0F",
        emojiContent = "🏙️",
        category = 0,
        description = "cityscape",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3DA FE0F",
        emojiContent = "🏚️",
        category = 0,
        description = "derelict house",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3DB FE0F",
        emojiContent = "🏛️",
        category = 0,
        description = "classical building",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3DC FE0F",
        emojiContent = "🏜️",
        category = 0,
        description = "desert",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3DD FE0F",
        emojiContent = "🏝️",
        category = 0,
        description = "desert island",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3DE FE0F",
        emojiContent = "🏞️",
        category = 0,
        description = "national park",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3DF FE0F",
        emojiContent = "🏟️",
        category = 0,
        description = "stadium",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3F3 FE0F",
        emojiContent = "🏳️",
        category = 0,
        description = "white flag",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3F5 FE0F",
        emojiContent = "🏵️",
        category = 0,
        description = "rosette",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3F7 FE0F",
        emojiContent = "🏷️",
        category = 0,
        description = "label",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F43F FE0F",
        emojiContent = "🐿️",
        category = 0,
        description = "chipmunk",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F441 FE0F",
        emojiContent = "👁️",
        category = 0,
        description = "eye",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4FD FE0F",
        emojiContent = "📽️",
        category = 0,
        description = "film projector",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F549 FE0F",
        emojiContent = "🕉️",
        category = 0,
        description = "om",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F54A FE0F",
        emojiContent = "🕊️",
        category = 0,
        description = "dove",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F56F FE0F",
        emojiContent = "🕯️",
        category = 0,
        description = "candle",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F570 FE0F",
        emojiContent = "🕰️",
        category = 0,
        description = "mantelpiece clock",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F573 FE0F",
        emojiContent = "🕳️",
        category = 0,
        description = "hole",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F574 FE0F",
        emojiContent = "🕴️",
        category = 0,
        description = "person in suit levitating",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F575 FE0F",
        emojiContent = "🕵️",
        category = 0,
        description = "detective",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F576 FE0F",
        emojiContent = "🕶️",
        category = 0,
        description = "sunglasses",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F577 FE0F",
        emojiContent = "🕷️",
        category = 0,
        description = "spider",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F578 FE0F",
        emojiContent = "🕸️",
        category = 0,
        description = "spider web",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F579 FE0F",
        emojiContent = "🕹️",
        category = 0,
        description = "joystick",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F587 FE0F",
        emojiContent = "🖇️",
        category = 0,
        description = "linked paperclips",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F58A FE0F",
        emojiContent = "🖊️",
        category = 0,
        description = "pen",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F58B FE0F",
        emojiContent = "🖋️",
        category = 0,
        description = "fountain pen",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F58C FE0F",
        emojiContent = "🖌️",
        category = 0,
        description = "paintbrush",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F58D FE0F",
        emojiContent = "🖍️",
        category = 0,
        description = "crayon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F590 FE0F",
        emojiContent = "🖐️",
        category = 0,
        description = "hand with fingers splayed",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5A5 FE0F",
        emojiContent = "🖥️",
        category = 0,
        description = "desktop computer",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5A8 FE0F",
        emojiContent = "🖨️",
        category = 0,
        description = "printer",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5B1 FE0F",
        emojiContent = "🖱️",
        category = 0,
        description = "computer mouse",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5B2 FE0F",
        emojiContent = "🖲️",
        category = 0,
        description = "trackball",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5BC FE0F",
        emojiContent = "🖼️",
        category = 0,
        description = "framed picture",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5C2 FE0F",
        emojiContent = "🗂️",
        category = 0,
        description = "card index dividers",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5C3 FE0F",
        emojiContent = "🗃️",
        category = 0,
        description = "card file box",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5C4 FE0F",
        emojiContent = "🗄️",
        category = 0,
        description = "file cabinet",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5D1 FE0F",
        emojiContent = "🗑️",
        category = 0,
        description = "wastebasket",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5D2 FE0F",
        emojiContent = "🗒️",
        category = 0,
        description = "spiral notepad",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5D3 FE0F",
        emojiContent = "🗓️",
        category = 0,
        description = "spiral calendar",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5DC FE0F",
        emojiContent = "🗜️",
        category = 0,
        description = "clamp",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5DD FE0F",
        emojiContent = "🗝️",
        category = 0,
        description = "old key",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5DE FE0F",
        emojiContent = "🗞️",
        category = 0,
        description = "rolled-up newspaper",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5E1 FE0F",
        emojiContent = "🗡️",
        category = 0,
        description = "dagger",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5E3 FE0F",
        emojiContent = "🗣️",
        category = 0,
        description = "speaking head",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5E8 FE0F",
        emojiContent = "🗨️",
        category = 0,
        description = "left speech bubble",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5EF FE0F",
        emojiContent = "🗯️",
        category = 0,
        description = "right anger bubble",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5F3 FE0F",
        emojiContent = "🗳️",
        category = 0,
        description = "ballot box with ballot",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F5FA FE0F",
        emojiContent = "🗺️",
        category = 0,
        description = "world map",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6CB FE0F",
        emojiContent = "🛋️",
        category = 0,
        description = "couch and lamp",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6CD FE0F",
        emojiContent = "🛍️",
        category = 0,
        description = "shopping bags",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6CE FE0F",
        emojiContent = "🛎️",
        category = 0,
        description = "bellhop bell",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6CF FE0F",
        emojiContent = "🛏️",
        category = 0,
        description = "bed",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6E0 FE0F",
        emojiContent = "🛠️",
        category = 0,
        description = "hammer and wrench",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6E1 FE0F",
        emojiContent = "🛡️",
        category = 0,
        description = "shield",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6E2 FE0F",
        emojiContent = "🛢️",
        category = 0,
        description = "oil drum",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6E3 FE0F",
        emojiContent = "🛣️",
        category = 0,
        description = "motorway",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6E4 FE0F",
        emojiContent = "🛤️",
        category = 0,
        description = "railway track",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6E5 FE0F",
        emojiContent = "🛥️",
        category = 0,
        description = "motor boat",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6E9 FE0F",
        emojiContent = "🛩️",
        category = 0,
        description = "small airplane",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6F0 FE0F",
        emojiContent = "🛰️",
        category = 0,
        description = "satellite",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6F3 FE0F",
        emojiContent = "🛳️",
        category = 0,
        description = "passenger ship",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0023 FE0F 20E",
        emojiContent = "#️⃣",
        category = 1,
        description = "keycap: #",
        isRange = false
    ),
    EmojiItem(
        hexCode = "002A FE0F 20E",
        emojiContent = "*️⃣",
        category = 1,
        description = "keycap: *",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0030 FE0F 20E",
        emojiContent = "0️⃣",
        category = 1,
        description = "keycap: 0",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0031 FE0F 20E",
        emojiContent = "1️⃣",
        category = 1,
        description = "keycap: 1",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0032 FE0F 20E",
        emojiContent = "2️⃣",
        category = 1,
        description = "keycap: 2",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0033 FE0F 20E",
        emojiContent = "3️⃣",
        category = 1,
        description = "keycap: 3",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0034 FE0F 20E",
        emojiContent = "4️⃣",
        category = 1,
        description = "keycap: 4",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0035 FE0F 20E",
        emojiContent = "5️⃣",
        category = 1,
        description = "keycap: 5",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0036 FE0F 20E",
        emojiContent = "6️⃣",
        category = 1,
        description = "keycap: 6",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0037 FE0F 20E",
        emojiContent = "7️⃣",
        category = 1,
        description = "keycap: 7",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0038 FE0F 20E",
        emojiContent = "8️⃣",
        category = 1,
        description = "keycap: 8",
        isRange = false
    ),
    EmojiItem(
        hexCode = "0039 FE0F 20E",
        emojiContent = "9️⃣",
        category = 1,
        description = "keycap: 9",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1E8",
        emojiContent = "🇦🇨",
        category = 2,
        description = "flag: Ascension Island",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1E9",
        emojiContent = "🇦🇩",
        category = 2,
        description = "flag: Andorra",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1EA",
        emojiContent = "🇦🇪",
        category = 2,
        description = "flag: United Arab Emirates",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1EB",
        emojiContent = "🇦🇫",
        category = 2,
        description = "flag: Afghanistan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1EC",
        emojiContent = "🇦🇬",
        category = 2,
        description = "flag: Antigua & Barbuda",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1EE",
        emojiContent = "🇦🇮",
        category = 2,
        description = "flag: Anguilla",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1F1",
        emojiContent = "🇦🇱",
        category = 2,
        description = "flag: Albania",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1F2",
        emojiContent = "🇦🇲",
        category = 2,
        description = "flag: Armenia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1F4",
        emojiContent = "🇦🇴",
        category = 2,
        description = "flag: Angola",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1F6",
        emojiContent = "🇦🇶",
        category = 2,
        description = "flag: Antarctica",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1F7",
        emojiContent = "🇦🇷",
        category = 2,
        description = "flag: Argentina",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1F8",
        emojiContent = "🇦🇸",
        category = 2,
        description = "flag: American Samoa",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1F9",
        emojiContent = "🇦🇹",
        category = 2,
        description = "flag: Austria",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1FA",
        emojiContent = "🇦🇺",
        category = 2,
        description = "flag: Australia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1FC",
        emojiContent = "🇦🇼",
        category = 2,
        description = "flag: Aruba",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1FD",
        emojiContent = "🇦🇽",
        category = 2,
        description = "flag: Åland Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E6 1F1FF",
        emojiContent = "🇦🇿",
        category = 2,
        description = "flag: Azerbaijan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1E6",
        emojiContent = "🇧🇦",
        category = 2,
        description = "flag: Bosnia & Herzegovina",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1E7",
        emojiContent = "🇧🇧",
        category = 2,
        description = "flag: Barbados",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1E9",
        emojiContent = "🇧🇩",
        category = 2,
        description = "flag: Bangladesh",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1EA",
        emojiContent = "🇧🇪",
        category = 2,
        description = "flag: Belgium",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1EB",
        emojiContent = "🇧🇫",
        category = 2,
        description = "flag: Burkina Faso",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1EC",
        emojiContent = "🇧🇬",
        category = 2,
        description = "flag: Bulgaria",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1ED",
        emojiContent = "🇧🇭",
        category = 2,
        description = "flag: Bahrain",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1EE",
        emojiContent = "🇧🇮",
        category = 2,
        description = "flag: Burundi",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1EF",
        emojiContent = "🇧🇯",
        category = 2,
        description = "flag: Benin",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1F1",
        emojiContent = "🇧🇱",
        category = 2,
        description = "flag: St. Barthélemy",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1F2",
        emojiContent = "🇧🇲",
        category = 2,
        description = "flag: Bermuda",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1F3",
        emojiContent = "🇧🇳",
        category = 2,
        description = "flag: Brunei",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1F4",
        emojiContent = "🇧🇴",
        category = 2,
        description = "flag: Bolivia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1F6",
        emojiContent = "🇧🇶",
        category = 2,
        description = "flag: Caribbean Netherlands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1F7",
        emojiContent = "🇧🇷",
        category = 2,
        description = "flag: Brazil",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1F8",
        emojiContent = "🇧🇸",
        category = 2,
        description = "flag: Bahamas",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1F9",
        emojiContent = "🇧🇹",
        category = 2,
        description = "flag: Bhutan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1FB",
        emojiContent = "🇧🇻",
        category = 2,
        description = "flag: Bouvet Island",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1FC",
        emojiContent = "🇧🇼",
        category = 2,
        description = "flag: Botswana",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1FE",
        emojiContent = "🇧🇾",
        category = 2,
        description = "flag: Belarus",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E7 1F1FF",
        emojiContent = "🇧🇿",
        category = 2,
        description = "flag: Belize",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1E6",
        emojiContent = "🇨🇦",
        category = 2,
        description = "flag: Canada",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1E8",
        emojiContent = "🇨🇨",
        category = 2,
        description = "flag: Cocos (Keeling) Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1E9",
        emojiContent = "🇨🇩",
        category = 2,
        description = "flag: Congo - Kinshasa",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1EB",
        emojiContent = "🇨🇫",
        category = 2,
        description = "flag: Central African Republic",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1EC",
        emojiContent = "🇨🇬",
        category = 2,
        description = "flag: Congo - Brazzaville",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1ED",
        emojiContent = "🇨🇭",
        category = 2,
        description = "flag: Switzerland",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1EE",
        emojiContent = "🇨🇮",
        category = 2,
        description = "flag: Côte d’Ivoire",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1F0",
        emojiContent = "🇨🇰",
        category = 2,
        description = "flag: Cook Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1F1",
        emojiContent = "🇨🇱",
        category = 2,
        description = "flag: Chile",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1F2",
        emojiContent = "🇨🇲",
        category = 2,
        description = "flag: Cameroon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1F3",
        emojiContent = "🇨🇳",
        category = 2,
        description = "flag: China",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1F4",
        emojiContent = "🇨🇴",
        category = 2,
        description = "flag: Colombia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1F5",
        emojiContent = "🇨🇵",
        category = 2,
        description = "flag: Clipperton Island",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1F7",
        emojiContent = "🇨🇷",
        category = 2,
        description = "flag: Costa Rica",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1FA",
        emojiContent = "🇨🇺",
        category = 2,
        description = "flag: Cuba",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1FB",
        emojiContent = "🇨🇻",
        category = 2,
        description = "flag: Cape Verde",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1FC",
        emojiContent = "🇨🇼",
        category = 2,
        description = "flag: Curaçao",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1FD",
        emojiContent = "🇨🇽",
        category = 2,
        description = "flag: Christmas Island",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1FE",
        emojiContent = "🇨🇾",
        category = 2,
        description = "flag: Cyprus",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E8 1F1FF",
        emojiContent = "🇨🇿",
        category = 2,
        description = "flag: Czechia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E9 1F1EA",
        emojiContent = "🇩🇪",
        category = 2,
        description = "flag: Germany",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E9 1F1EC",
        emojiContent = "🇩🇬",
        category = 2,
        description = "flag: Diego Garcia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E9 1F1EF",
        emojiContent = "🇩🇯",
        category = 2,
        description = "flag: Djibouti",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E9 1F1F0",
        emojiContent = "🇩🇰",
        category = 2,
        description = "flag: Denmark",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E9 1F1F2",
        emojiContent = "🇩🇲",
        category = 2,
        description = "flag: Dominica",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E9 1F1F4",
        emojiContent = "🇩🇴",
        category = 2,
        description = "flag: Dominican Republic",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1E9 1F1FF",
        emojiContent = "🇩🇿",
        category = 2,
        description = "flag: Algeria",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EA 1F1E6",
        emojiContent = "🇪🇦",
        category = 2,
        description = "flag: Ceuta & Melilla",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EA 1F1E8",
        emojiContent = "🇪🇨",
        category = 2,
        description = "flag: Ecuador",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EA 1F1EA",
        emojiContent = "🇪🇪",
        category = 2,
        description = "flag: Estonia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EA 1F1EC",
        emojiContent = "🇪🇬",
        category = 2,
        description = "flag: Egypt",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EA 1F1ED",
        emojiContent = "🇪🇭",
        category = 2,
        description = "flag: Western Sahara",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EA 1F1F7",
        emojiContent = "🇪🇷",
        category = 2,
        description = "flag: Eritrea",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EA 1F1F8",
        emojiContent = "🇪🇸",
        category = 2,
        description = "flag: Spain",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EA 1F1F9",
        emojiContent = "🇪🇹",
        category = 2,
        description = "flag: Ethiopia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EA 1F1FA",
        emojiContent = "🇪🇺",
        category = 2,
        description = "flag: European Union",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EB 1F1EE",
        emojiContent = "🇫🇮",
        category = 2,
        description = "flag: Finland",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EB 1F1EF",
        emojiContent = "🇫🇯",
        category = 2,
        description = "flag: Fiji",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EB 1F1F0",
        emojiContent = "🇫🇰",
        category = 2,
        description = "flag: Falkland Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EB 1F1F2",
        emojiContent = "🇫🇲",
        category = 2,
        description = "flag: Micronesia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EB 1F1F4",
        emojiContent = "🇫🇴",
        category = 2,
        description = "flag: Faroe Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EB 1F1F7",
        emojiContent = "🇫🇷",
        category = 2,
        description = "flag: France",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1E6",
        emojiContent = "🇬🇦",
        category = 2,
        description = "flag: Gabon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1E7",
        emojiContent = "🇬🇧",
        category = 2,
        description = "flag: United Kingdom",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1E9",
        emojiContent = "🇬🇩",
        category = 2,
        description = "flag: Grenada",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1EA",
        emojiContent = "🇬🇪",
        category = 2,
        description = "flag: Georgia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1EB",
        emojiContent = "🇬🇫",
        category = 2,
        description = "flag: French Guiana",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1EC",
        emojiContent = "🇬🇬",
        category = 2,
        description = "flag: Guernsey",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1ED",
        emojiContent = "🇬🇭",
        category = 2,
        description = "flag: Ghana",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1EE",
        emojiContent = "🇬🇮",
        category = 2,
        description = "flag: Gibraltar",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1F1",
        emojiContent = "🇬🇱",
        category = 2,
        description = "flag: Greenland",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1F2",
        emojiContent = "🇬🇲",
        category = 2,
        description = "flag: Gambia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1F3",
        emojiContent = "🇬🇳",
        category = 2,
        description = "flag: Guinea",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1F5",
        emojiContent = "🇬🇵",
        category = 2,
        description = "flag: Guadeloupe",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1F6",
        emojiContent = "🇬🇶",
        category = 2,
        description = "flag: Equatorial Guinea",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1F7",
        emojiContent = "🇬🇷",
        category = 2,
        description = "flag: Greece",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1F8",
        emojiContent = "🇬🇸",
        category = 2,
        description = "flag: South Georgia & South Sandwich Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1F9",
        emojiContent = "🇬🇹",
        category = 2,
        description = "flag: Guatemala",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1FA",
        emojiContent = "🇬🇺",
        category = 2,
        description = "flag: Guam",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1FC",
        emojiContent = "🇬🇼",
        category = 2,
        description = "flag: Guinea-Bissau",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EC 1F1FE",
        emojiContent = "🇬🇾",
        category = 2,
        description = "flag: Guyana",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1ED 1F1F0",
        emojiContent = "🇭🇰",
        category = 2,
        description = "flag: Hong Kong SAR China",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1ED 1F1F2",
        emojiContent = "🇭🇲",
        category = 2,
        description = "flag: Heard & McDonald Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1ED 1F1F3",
        emojiContent = "🇭🇳",
        category = 2,
        description = "flag: Honduras",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1ED 1F1F7",
        emojiContent = "🇭🇷",
        category = 2,
        description = "flag: Croatia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1ED 1F1F9",
        emojiContent = "🇭🇹",
        category = 2,
        description = "flag: Haiti",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1ED 1F1FA",
        emojiContent = "🇭🇺",
        category = 2,
        description = "flag: Hungary",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1E8",
        emojiContent = "🇮🇨",
        category = 2,
        description = "flag: Canary Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1E9",
        emojiContent = "🇮🇩",
        category = 2,
        description = "flag: Indonesia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1EA",
        emojiContent = "🇮🇪",
        category = 2,
        description = "flag: Ireland",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1F1",
        emojiContent = "🇮🇱",
        category = 2,
        description = "flag: Israel",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1F2",
        emojiContent = "🇮🇲",
        category = 2,
        description = "flag: Isle of Man",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1F3",
        emojiContent = "🇮🇳",
        category = 2,
        description = "flag: India",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1F4",
        emojiContent = "🇮🇴",
        category = 2,
        description = "flag: British Indian Ocean Territory",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1F6",
        emojiContent = "🇮🇶",
        category = 2,
        description = "flag: Iraq",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1F7",
        emojiContent = "🇮🇷",
        category = 2,
        description = "flag: Iran",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1F8",
        emojiContent = "🇮🇸",
        category = 2,
        description = "flag: Iceland",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EE 1F1F9",
        emojiContent = "🇮🇹",
        category = 2,
        description = "flag: Italy",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EF 1F1EA",
        emojiContent = "🇯🇪",
        category = 2,
        description = "flag: Jersey",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EF 1F1F2",
        emojiContent = "🇯🇲",
        category = 2,
        description = "flag: Jamaica",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EF 1F1F4",
        emojiContent = "🇯🇴",
        category = 2,
        description = "flag: Jordan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1EF 1F1F5",
        emojiContent = "🇯🇵",
        category = 2,
        description = "flag: Japan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1EA",
        emojiContent = "🇰🇪",
        category = 2,
        description = "flag: Kenya",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1EC",
        emojiContent = "🇰🇬",
        category = 2,
        description = "flag: Kyrgyzstan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1ED",
        emojiContent = "🇰🇭",
        category = 2,
        description = "flag: Cambodia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1EE",
        emojiContent = "🇰🇮",
        category = 2,
        description = "flag: Kiribati",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1F2",
        emojiContent = "🇰🇲",
        category = 2,
        description = "flag: Comoros",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1F3",
        emojiContent = "🇰🇳",
        category = 2,
        description = "flag: St. Kitts & Nevis",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1F5",
        emojiContent = "🇰🇵",
        category = 2,
        description = "flag: North Korea",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1F7",
        emojiContent = "🇰🇷",
        category = 2,
        description = "flag: South Korea",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1FC",
        emojiContent = "🇰🇼",
        category = 2,
        description = "flag: Kuwait",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1FE",
        emojiContent = "🇰🇾",
        category = 2,
        description = "flag: Cayman Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F0 1F1FF",
        emojiContent = "🇰🇿",
        category = 2,
        description = "flag: Kazakhstan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1E6",
        emojiContent = "🇱🇦",
        category = 2,
        description = "flag: Laos",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1E7",
        emojiContent = "🇱🇧",
        category = 2,
        description = "flag: Lebanon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1E8",
        emojiContent = "🇱🇨",
        category = 2,
        description = "flag: St. Lucia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1EE",
        emojiContent = "🇱🇮",
        category = 2,
        description = "flag: Liechtenstein",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1F0",
        emojiContent = "🇱🇰",
        category = 2,
        description = "flag: Sri Lanka",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1F7",
        emojiContent = "🇱🇷",
        category = 2,
        description = "flag: Liberia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1F8",
        emojiContent = "🇱🇸",
        category = 2,
        description = "flag: Lesotho",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1F9",
        emojiContent = "🇱🇹",
        category = 2,
        description = "flag: Lithuania",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1FA",
        emojiContent = "🇱🇺",
        category = 2,
        description = "flag: Luxembourg",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1FB",
        emojiContent = "🇱🇻",
        category = 2,
        description = "flag: Latvia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F1 1F1FE",
        emojiContent = "🇱🇾",
        category = 2,
        description = "flag: Libya",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1E6",
        emojiContent = "🇲🇦",
        category = 2,
        description = "flag: Morocco",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1E8",
        emojiContent = "🇲🇨",
        category = 2,
        description = "flag: Monaco",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1E9",
        emojiContent = "🇲🇩",
        category = 2,
        description = "flag: Moldova",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1EA",
        emojiContent = "🇲🇪",
        category = 2,
        description = "flag: Montenegro",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1EB",
        emojiContent = "🇲🇫",
        category = 2,
        description = "flag: St. Martin",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1EC",
        emojiContent = "🇲🇬",
        category = 2,
        description = "flag: Madagascar",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1ED",
        emojiContent = "🇲🇭",
        category = 2,
        description = "flag: Marshall Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F0",
        emojiContent = "🇲🇰",
        category = 2,
        description = "flag: North Macedonia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F1",
        emojiContent = "🇲🇱",
        category = 2,
        description = "flag: Mali",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F2",
        emojiContent = "🇲🇲",
        category = 2,
        description = "flag: Myanmar (Burma)",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F3",
        emojiContent = "🇲🇳",
        category = 2,
        description = "flag: Mongolia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F4",
        emojiContent = "🇲🇴",
        category = 2,
        description = "flag: Macao SAR China",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F5",
        emojiContent = "🇲🇵",
        category = 2,
        description = "flag: Northern Mariana Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F6",
        emojiContent = "🇲🇶",
        category = 2,
        description = "flag: Martinique",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F7",
        emojiContent = "🇲🇷",
        category = 2,
        description = "flag: Mauritania",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F8",
        emojiContent = "🇲🇸",
        category = 2,
        description = "flag: Montserrat",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1F9",
        emojiContent = "🇲🇹",
        category = 2,
        description = "flag: Malta",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1FA",
        emojiContent = "🇲🇺",
        category = 2,
        description = "flag: Mauritius",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1FB",
        emojiContent = "🇲🇻",
        category = 2,
        description = "flag: Maldives",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1FC",
        emojiContent = "🇲🇼",
        category = 2,
        description = "flag: Malawi",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1FD",
        emojiContent = "🇲🇽",
        category = 2,
        description = "flag: Mexico",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1FE",
        emojiContent = "🇲🇾",
        category = 2,
        description = "flag: Malaysia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F2 1F1FF",
        emojiContent = "🇲🇿",
        category = 2,
        description = "flag: Mozambique",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1E6",
        emojiContent = "🇳🇦",
        category = 2,
        description = "flag: Namibia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1E8",
        emojiContent = "🇳🇨",
        category = 2,
        description = "flag: New Caledonia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1EA",
        emojiContent = "🇳🇪",
        category = 2,
        description = "flag: Niger",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1EB",
        emojiContent = "🇳🇫",
        category = 2,
        description = "flag: Norfolk Island",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1EC",
        emojiContent = "🇳🇬",
        category = 2,
        description = "flag: Nigeria",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1EE",
        emojiContent = "🇳🇮",
        category = 2,
        description = "flag: Nicaragua",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1F1",
        emojiContent = "🇳🇱",
        category = 2,
        description = "flag: Netherlands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1F4",
        emojiContent = "🇳🇴",
        category = 2,
        description = "flag: Norway",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1F5",
        emojiContent = "🇳🇵",
        category = 2,
        description = "flag: Nepal",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1F7",
        emojiContent = "🇳🇷",
        category = 2,
        description = "flag: Nauru",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1FA",
        emojiContent = "🇳🇺",
        category = 2,
        description = "flag: Niue",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F3 1F1FF",
        emojiContent = "🇳🇿",
        category = 2,
        description = "flag: New Zealand",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F4 1F1F2",
        emojiContent = "🇴🇲",
        category = 2,
        description = "flag: Oman",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1E6",
        emojiContent = "🇵🇦",
        category = 2,
        description = "flag: Panama",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1EA",
        emojiContent = "🇵🇪",
        category = 2,
        description = "flag: Peru",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1EB",
        emojiContent = "🇵🇫",
        category = 2,
        description = "flag: French Polynesia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1EC",
        emojiContent = "🇵🇬",
        category = 2,
        description = "flag: Papua New Guinea",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1ED",
        emojiContent = "🇵🇭",
        category = 2,
        description = "flag: Philippines",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1F0",
        emojiContent = "🇵🇰",
        category = 2,
        description = "flag: Pakistan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1F1",
        emojiContent = "🇵🇱",
        category = 2,
        description = "flag: Poland",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1F2",
        emojiContent = "🇵🇲",
        category = 2,
        description = "flag: St. Pierre & Miquelon",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1F3",
        emojiContent = "🇵🇳",
        category = 2,
        description = "flag: Pitcairn Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1F7",
        emojiContent = "🇵🇷",
        category = 2,
        description = "flag: Puerto Rico",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1F8",
        emojiContent = "🇵🇸",
        category = 2,
        description = "flag: Palestinian Territories",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1F9",
        emojiContent = "🇵🇹",
        category = 2,
        description = "flag: Portugal",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1FC",
        emojiContent = "🇵🇼",
        category = 2,
        description = "flag: Palau",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F5 1F1FE",
        emojiContent = "🇵🇾",
        category = 2,
        description = "flag: Paraguay",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F6 1F1E6",
        emojiContent = "🇶🇦",
        category = 2,
        description = "flag: Qatar",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F7 1F1EA",
        emojiContent = "🇷🇪",
        category = 2,
        description = "flag: Réunion",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F7 1F1F4",
        emojiContent = "🇷🇴",
        category = 2,
        description = "flag: Romania",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F7 1F1F8",
        emojiContent = "🇷🇸",
        category = 2,
        description = "flag: Serbia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F7 1F1FA",
        emojiContent = "🇷🇺",
        category = 2,
        description = "flag: Russia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F7 1F1FC",
        emojiContent = "🇷🇼",
        category = 2,
        description = "flag: Rwanda",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1E6",
        emojiContent = "🇸🇦",
        category = 2,
        description = "flag: Saudi Arabia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1E7",
        emojiContent = "🇸🇧",
        category = 2,
        description = "flag: Solomon Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1E8",
        emojiContent = "🇸🇨",
        category = 2,
        description = "flag: Seychelles",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1E9",
        emojiContent = "🇸🇩",
        category = 2,
        description = "flag: Sudan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1EA",
        emojiContent = "🇸🇪",
        category = 2,
        description = "flag: Sweden",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1EC",
        emojiContent = "🇸🇬",
        category = 2,
        description = "flag: Singapore",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1ED",
        emojiContent = "🇸🇭",
        category = 2,
        description = "flag: St. Helena",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1EE",
        emojiContent = "🇸🇮",
        category = 2,
        description = "flag: Slovenia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1EF",
        emojiContent = "🇸🇯",
        category = 2,
        description = "flag: Svalbard & Jan Mayen",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1F0",
        emojiContent = "🇸🇰",
        category = 2,
        description = "flag: Slovakia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1F1",
        emojiContent = "🇸🇱",
        category = 2,
        description = "flag: Sierra Leone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1F2",
        emojiContent = "🇸🇲",
        category = 2,
        description = "flag: San Marino",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1F3",
        emojiContent = "🇸🇳",
        category = 2,
        description = "flag: Senegal",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1F4",
        emojiContent = "🇸🇴",
        category = 2,
        description = "flag: Somalia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1F7",
        emojiContent = "🇸🇷",
        category = 2,
        description = "flag: Suriname",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1F8",
        emojiContent = "🇸🇸",
        category = 2,
        description = "flag: South Sudan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1F9",
        emojiContent = "🇸🇹",
        category = 2,
        description = "flag: São Tomé & Príncipe",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1FB",
        emojiContent = "🇸🇻",
        category = 2,
        description = "flag: El Salvador",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1FD",
        emojiContent = "🇸🇽",
        category = 2,
        description = "flag: Sint Maarten",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1FE",
        emojiContent = "🇸🇾",
        category = 2,
        description = "flag: Syria",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F8 1F1FF",
        emojiContent = "🇸🇿",
        category = 2,
        description = "flag: Eswatini",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1E6",
        emojiContent = "🇹🇦",
        category = 2,
        description = "flag: Tristan da Cunha",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1E8",
        emojiContent = "🇹🇨",
        category = 2,
        description = "flag: Turks & Caicos Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1E9",
        emojiContent = "🇹🇩",
        category = 2,
        description = "flag: Chad",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1EB",
        emojiContent = "🇹🇫",
        category = 2,
        description = "flag: French Southern Territories",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1EC",
        emojiContent = "🇹🇬",
        category = 2,
        description = "flag: Togo",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1ED",
        emojiContent = "🇹🇭",
        category = 2,
        description = "flag: Thailand",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1EF",
        emojiContent = "🇹🇯",
        category = 2,
        description = "flag: Tajikistan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1F0",
        emojiContent = "🇹🇰",
        category = 2,
        description = "flag: Tokelau",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1F1",
        emojiContent = "🇹🇱",
        category = 2,
        description = "flag: Timor-Leste",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1F2",
        emojiContent = "🇹🇲",
        category = 2,
        description = "flag: Turkmenistan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1F3",
        emojiContent = "🇹🇳",
        category = 2,
        description = "flag: Tunisia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1F4",
        emojiContent = "🇹🇴",
        category = 2,
        description = "flag: Tonga",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1F7",
        emojiContent = "🇹🇷",
        category = 2,
        description = "flag: Turkey",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1F9",
        emojiContent = "🇹🇹",
        category = 2,
        description = "flag: Trinidad & Tobago",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1FB",
        emojiContent = "🇹🇻",
        category = 2,
        description = "flag: Tuvalu",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1FC",
        emojiContent = "🇹🇼",
        category = 2,
        description = "flag: Taiwan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1F9 1F1FF",
        emojiContent = "🇹🇿",
        category = 2,
        description = "flag: Tanzania",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FA 1F1E6",
        emojiContent = "🇺🇦",
        category = 2,
        description = "flag: Ukraine",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FA 1F1EC",
        emojiContent = "🇺🇬",
        category = 2,
        description = "flag: Uganda",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FA 1F1F2",
        emojiContent = "🇺🇲",
        category = 2,
        description = "flag: U.S. Outlying Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FA 1F1F3",
        emojiContent = "🇺🇳",
        category = 2,
        description = "flag: United Nations",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FA 1F1F8",
        emojiContent = "🇺🇸",
        category = 2,
        description = "flag: United States",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FA 1F1FE",
        emojiContent = "🇺🇾",
        category = 2,
        description = "flag: Uruguay",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FA 1F1FF",
        emojiContent = "🇺🇿",
        category = 2,
        description = "flag: Uzbekistan",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FB 1F1E6",
        emojiContent = "🇻🇦",
        category = 2,
        description = "flag: Vatican City",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FB 1F1E8",
        emojiContent = "🇻🇨",
        category = 2,
        description = "flag: St. Vincent & Grenadines",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FB 1F1EA",
        emojiContent = "🇻🇪",
        category = 2,
        description = "flag: Venezuela",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FB 1F1EC",
        emojiContent = "🇻🇬",
        category = 2,
        description = "flag: British Virgin Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FB 1F1EE",
        emojiContent = "🇻🇮",
        category = 2,
        description = "flag: U.S. Virgin Islands",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FB 1F1F3",
        emojiContent = "🇻🇳",
        category = 2,
        description = "flag: Vietnam",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FB 1F1FA",
        emojiContent = "🇻🇺",
        category = 2,
        description = "flag: Vanuatu",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FC 1F1EB",
        emojiContent = "🇼🇫",
        category = 2,
        description = "flag: Wallis & Futuna",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FC 1F1F8",
        emojiContent = "🇼🇸",
        category = 2,
        description = "flag: Samoa",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FD 1F1F0",
        emojiContent = "🇽🇰",
        category = 2,
        description = "flag: Kosovo",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FE 1F1EA",
        emojiContent = "🇾🇪",
        category = 2,
        description = "flag: Yemen",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FE 1F1F9",
        emojiContent = "🇾🇹",
        category = 2,
        description = "flag: Mayotte",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FF 1F1E6",
        emojiContent = "🇿🇦",
        category = 2,
        description = "flag: South Africa",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FF 1F1F2",
        emojiContent = "🇿🇲",
        category = 2,
        description = "flag: Zambia",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F1FF 1F1FC",
        emojiContent = "🇿🇼",
        category = 2,
        description = "flag: Zimbabwe",
        isRange = false
    ),
    EmojiItem(
        hexCode = "261D 1F3FB",
        emojiContent = "☝🏻",
        category = 4,
        description = "index pointing up: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "261D 1F3FC",
        emojiContent = "☝🏼",
        category = 4,
        description = "index pointing up: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "261D 1F3FD",
        emojiContent = "☝🏽",
        category = 4,
        description = "index pointing up: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "261D 1F3FE",
        emojiContent = "☝🏾",
        category = 4,
        description = "index pointing up: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "261D 1F3FF",
        emojiContent = "☝🏿",
        category = 4,
        description = "index pointing up: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F9 1F3FB",
        emojiContent = "⛹🏻",
        category = 4,
        description = "person bouncing ball: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F9 1F3FC",
        emojiContent = "⛹🏼",
        category = 4,
        description = "person bouncing ball: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F9 1F3FD",
        emojiContent = "⛹🏽",
        category = 4,
        description = "person bouncing ball: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F9 1F3FE",
        emojiContent = "⛹🏾",
        category = 4,
        description = "person bouncing ball: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "26F9 1F3FF",
        emojiContent = "⛹🏿",
        category = 4,
        description = "person bouncing ball: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270A 1F3FB",
        emojiContent = "✊🏻",
        category = 4,
        description = "raised fist: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270A 1F3FC",
        emojiContent = "✊🏼",
        category = 4,
        description = "raised fist: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270A 1F3FD",
        emojiContent = "✊🏽",
        category = 4,
        description = "raised fist: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270A 1F3FE",
        emojiContent = "✊🏾",
        category = 4,
        description = "raised fist: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270A 1F3FF",
        emojiContent = "✊🏿",
        category = 4,
        description = "raised fist: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270B 1F3FB",
        emojiContent = "✋🏻",
        category = 4,
        description = "raised hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270B 1F3FC",
        emojiContent = "✋🏼",
        category = 4,
        description = "raised hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270B 1F3FD",
        emojiContent = "✋🏽",
        category = 4,
        description = "raised hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270B 1F3FE",
        emojiContent = "✋🏾",
        category = 4,
        description = "raised hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270B 1F3FF",
        emojiContent = "✋🏿",
        category = 4,
        description = "raised hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270C 1F3FB",
        emojiContent = "✌🏻",
        category = 4,
        description = "victory hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270C 1F3FC",
        emojiContent = "✌🏼",
        category = 4,
        description = "victory hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270C 1F3FD",
        emojiContent = "✌🏽",
        category = 4,
        description = "victory hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270C 1F3FE",
        emojiContent = "✌🏾",
        category = 4,
        description = "victory hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270C 1F3FF",
        emojiContent = "✌🏿",
        category = 4,
        description = "victory hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270D 1F3FB",
        emojiContent = "✍🏻",
        category = 4,
        description = "writing hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270D 1F3FC",
        emojiContent = "✍🏼",
        category = 4,
        description = "writing hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270D 1F3FD",
        emojiContent = "✍🏽",
        category = 4,
        description = "writing hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270D 1F3FE",
        emojiContent = "✍🏾",
        category = 4,
        description = "writing hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "270D 1F3FF",
        emojiContent = "✍🏿",
        category = 4,
        description = "writing hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F385 1F3FB",
        emojiContent = "🎅🏻",
        category = 4,
        description = "Santa Claus: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F385 1F3FC",
        emojiContent = "🎅🏼",
        category = 4,
        description = "Santa Claus: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F385 1F3FD",
        emojiContent = "🎅🏽",
        category = 4,
        description = "Santa Claus: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F385 1F3FE",
        emojiContent = "🎅🏾",
        category = 4,
        description = "Santa Claus: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F385 1F3FF",
        emojiContent = "🎅🏿",
        category = 4,
        description = "Santa Claus: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C2 1F3FB",
        emojiContent = "🏂🏻",
        category = 4,
        description = "snowboarder: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C2 1F3FC",
        emojiContent = "🏂🏼",
        category = 4,
        description = "snowboarder: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C2 1F3FD",
        emojiContent = "🏂🏽",
        category = 4,
        description = "snowboarder: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C2 1F3FE",
        emojiContent = "🏂🏾",
        category = 4,
        description = "snowboarder: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C2 1F3FF",
        emojiContent = "🏂🏿",
        category = 4,
        description = "snowboarder: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C3 1F3FB",
        emojiContent = "🏃🏻",
        category = 4,
        description = "person running: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C3 1F3FC",
        emojiContent = "🏃🏼",
        category = 4,
        description = "person running: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C3 1F3FD",
        emojiContent = "🏃🏽",
        category = 4,
        description = "person running: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C3 1F3FE",
        emojiContent = "🏃🏾",
        category = 4,
        description = "person running: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C3 1F3FF",
        emojiContent = "🏃🏿",
        category = 4,
        description = "person running: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C4 1F3FB",
        emojiContent = "🏄🏻",
        category = 4,
        description = "person surfing: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C4 1F3FC",
        emojiContent = "🏄🏼",
        category = 4,
        description = "person surfing: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C4 1F3FD",
        emojiContent = "🏄🏽",
        category = 4,
        description = "person surfing: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C4 1F3FE",
        emojiContent = "🏄🏾",
        category = 4,
        description = "person surfing: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C4 1F3FF",
        emojiContent = "🏄🏿",
        category = 4,
        description = "person surfing: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C7 1F3FB",
        emojiContent = "🏇🏻",
        category = 4,
        description = "horse racing: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C7 1F3FC",
        emojiContent = "🏇🏼",
        category = 4,
        description = "horse racing: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C7 1F3FD",
        emojiContent = "🏇🏽",
        category = 4,
        description = "horse racing: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C7 1F3FE",
        emojiContent = "🏇🏾",
        category = 4,
        description = "horse racing: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3C7 1F3FF",
        emojiContent = "🏇🏿",
        category = 4,
        description = "horse racing: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CA 1F3FB",
        emojiContent = "🏊🏻",
        category = 4,
        description = "person swimming: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CA 1F3FC",
        emojiContent = "🏊🏼",
        category = 4,
        description = "person swimming: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CA 1F3FD",
        emojiContent = "🏊🏽",
        category = 4,
        description = "person swimming: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CA 1F3FE",
        emojiContent = "🏊🏾",
        category = 4,
        description = "person swimming: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CA 1F3FF",
        emojiContent = "🏊🏿",
        category = 4,
        description = "person swimming: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CB 1F3FB",
        emojiContent = "🏋🏻",
        category = 4,
        description = "person lifting weights: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CB 1F3FC",
        emojiContent = "🏋🏼",
        category = 4,
        description = "person lifting weights: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CB 1F3FD",
        emojiContent = "🏋🏽",
        category = 4,
        description = "person lifting weights: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CB 1F3FE",
        emojiContent = "🏋🏾",
        category = 4,
        description = "person lifting weights: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CB 1F3FF",
        emojiContent = "🏋🏿",
        category = 4,
        description = "person lifting weights: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CC 1F3FB",
        emojiContent = "🏌🏻",
        category = 4,
        description = "person golfing: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CC 1F3FC",
        emojiContent = "🏌🏼",
        category = 4,
        description = "person golfing: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CC 1F3FD",
        emojiContent = "🏌🏽",
        category = 4,
        description = "person golfing: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CC 1F3FE",
        emojiContent = "🏌🏾",
        category = 4,
        description = "person golfing: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F3CC 1F3FF",
        emojiContent = "🏌🏿",
        category = 4,
        description = "person golfing: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F442 1F3FB",
        emojiContent = "👂🏻",
        category = 4,
        description = "ear: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F442 1F3FC",
        emojiContent = "👂🏼",
        category = 4,
        description = "ear: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F442 1F3FD",
        emojiContent = "👂🏽",
        category = 4,
        description = "ear: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F442 1F3FE",
        emojiContent = "👂🏾",
        category = 4,
        description = "ear: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F442 1F3FF",
        emojiContent = "👂🏿",
        category = 4,
        description = "ear: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F443 1F3FB",
        emojiContent = "👃🏻",
        category = 4,
        description = "nose: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F443 1F3FC",
        emojiContent = "👃🏼",
        category = 4,
        description = "nose: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F443 1F3FD",
        emojiContent = "👃🏽",
        category = 4,
        description = "nose: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F443 1F3FE",
        emojiContent = "👃🏾",
        category = 4,
        description = "nose: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F443 1F3FF",
        emojiContent = "👃🏿",
        category = 4,
        description = "nose: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F446 1F3FB",
        emojiContent = "👆🏻",
        category = 4,
        description = "backhand index pointing up: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F446 1F3FC",
        emojiContent = "👆🏼",
        category = 4,
        description = "backhand index pointing up: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F446 1F3FD",
        emojiContent = "👆🏽",
        category = 4,
        description = "backhand index pointing up: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F446 1F3FE",
        emojiContent = "👆🏾",
        category = 4,
        description = "backhand index pointing up: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F446 1F3FF",
        emojiContent = "👆🏿",
        category = 4,
        description = "backhand index pointing up: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F447 1F3FB",
        emojiContent = "👇🏻",
        category = 4,
        description = "backhand index pointing down: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F447 1F3FC",
        emojiContent = "👇🏼",
        category = 4,
        description = "backhand index pointing down: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F447 1F3FD",
        emojiContent = "👇🏽",
        category = 4,
        description = "backhand index pointing down: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F447 1F3FE",
        emojiContent = "👇🏾",
        category = 4,
        description = "backhand index pointing down: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F447 1F3FF",
        emojiContent = "👇🏿",
        category = 4,
        description = "backhand index pointing down: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F448 1F3FB",
        emojiContent = "👈🏻",
        category = 4,
        description = "backhand index pointing left: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F448 1F3FC",
        emojiContent = "👈🏼",
        category = 4,
        description = "backhand index pointing left: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F448 1F3FD",
        emojiContent = "👈🏽",
        category = 4,
        description = "backhand index pointing left: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F448 1F3FE",
        emojiContent = "👈🏾",
        category = 4,
        description = "backhand index pointing left: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F448 1F3FF",
        emojiContent = "👈🏿",
        category = 4,
        description = "backhand index pointing left: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F449 1F3FB",
        emojiContent = "👉🏻",
        category = 4,
        description = "backhand index pointing right: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F449 1F3FC",
        emojiContent = "👉🏼",
        category = 4,
        description = "backhand index pointing right: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F449 1F3FD",
        emojiContent = "👉🏽",
        category = 4,
        description = "backhand index pointing right: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F449 1F3FE",
        emojiContent = "👉🏾",
        category = 4,
        description = "backhand index pointing right: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F449 1F3FF",
        emojiContent = "👉🏿",
        category = 4,
        description = "backhand index pointing right: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44A 1F3FB",
        emojiContent = "👊🏻",
        category = 4,
        description = "oncoming fist: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44A 1F3FC",
        emojiContent = "👊🏼",
        category = 4,
        description = "oncoming fist: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44A 1F3FD",
        emojiContent = "👊🏽",
        category = 4,
        description = "oncoming fist: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44A 1F3FE",
        emojiContent = "👊🏾",
        category = 4,
        description = "oncoming fist: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44A 1F3FF",
        emojiContent = "👊🏿",
        category = 4,
        description = "oncoming fist: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44B 1F3FB",
        emojiContent = "👋🏻",
        category = 4,
        description = "waving hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44B 1F3FC",
        emojiContent = "👋🏼",
        category = 4,
        description = "waving hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44B 1F3FD",
        emojiContent = "👋🏽",
        category = 4,
        description = "waving hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44B 1F3FE",
        emojiContent = "👋🏾",
        category = 4,
        description = "waving hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44B 1F3FF",
        emojiContent = "👋🏿",
        category = 4,
        description = "waving hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44C 1F3FB",
        emojiContent = "👌🏻",
        category = 4,
        description = "OK hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44C 1F3FC",
        emojiContent = "👌🏼",
        category = 4,
        description = "OK hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44C 1F3FD",
        emojiContent = "👌🏽",
        category = 4,
        description = "OK hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44C 1F3FE",
        emojiContent = "👌🏾",
        category = 4,
        description = "OK hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44C 1F3FF",
        emojiContent = "👌🏿",
        category = 4,
        description = "OK hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44D 1F3FB",
        emojiContent = "👍🏻",
        category = 4,
        description = "thumbs up: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44D 1F3FC",
        emojiContent = "👍🏼",
        category = 4,
        description = "thumbs up: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44D 1F3FD",
        emojiContent = "👍🏽",
        category = 4,
        description = "thumbs up: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44D 1F3FE",
        emojiContent = "👍🏾",
        category = 4,
        description = "thumbs up: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44D 1F3FF",
        emojiContent = "👍🏿",
        category = 4,
        description = "thumbs up: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44E 1F3FB",
        emojiContent = "👎🏻",
        category = 4,
        description = "thumbs down: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44E 1F3FC",
        emojiContent = "👎🏼",
        category = 4,
        description = "thumbs down: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44E 1F3FD",
        emojiContent = "👎🏽",
        category = 4,
        description = "thumbs down: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44E 1F3FE",
        emojiContent = "👎🏾",
        category = 4,
        description = "thumbs down: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44E 1F3FF",
        emojiContent = "👎🏿",
        category = 4,
        description = "thumbs down: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44F 1F3FB",
        emojiContent = "👏🏻",
        category = 4,
        description = "clapping hands: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44F 1F3FC",
        emojiContent = "👏🏼",
        category = 4,
        description = "clapping hands: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44F 1F3FD",
        emojiContent = "👏🏽",
        category = 4,
        description = "clapping hands: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44F 1F3FE",
        emojiContent = "👏🏾",
        category = 4,
        description = "clapping hands: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F44F 1F3FF",
        emojiContent = "👏🏿",
        category = 4,
        description = "clapping hands: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F450 1F3FB",
        emojiContent = "👐🏻",
        category = 4,
        description = "open hands: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F450 1F3FC",
        emojiContent = "👐🏼",
        category = 4,
        description = "open hands: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F450 1F3FD",
        emojiContent = "👐🏽",
        category = 4,
        description = "open hands: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F450 1F3FE",
        emojiContent = "👐🏾",
        category = 4,
        description = "open hands: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F450 1F3FF",
        emojiContent = "👐🏿",
        category = 4,
        description = "open hands: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F466 1F3FB",
        emojiContent = "👦🏻",
        category = 4,
        description = "boy: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F466 1F3FC",
        emojiContent = "👦🏼",
        category = 4,
        description = "boy: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F466 1F3FD",
        emojiContent = "👦🏽",
        category = 4,
        description = "boy: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F466 1F3FE",
        emojiContent = "👦🏾",
        category = 4,
        description = "boy: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F466 1F3FF",
        emojiContent = "👦🏿",
        category = 4,
        description = "boy: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F467 1F3FB",
        emojiContent = "👧🏻",
        category = 4,
        description = "girl: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F467 1F3FC",
        emojiContent = "👧🏼",
        category = 4,
        description = "girl: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F467 1F3FD",
        emojiContent = "👧🏽",
        category = 4,
        description = "girl: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F467 1F3FE",
        emojiContent = "👧🏾",
        category = 4,
        description = "girl: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F467 1F3FF",
        emojiContent = "👧🏿",
        category = 4,
        description = "girl: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F468 1F3FB",
        emojiContent = "👨🏻",
        category = 4,
        description = "man: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F468 1F3FC",
        emojiContent = "👨🏼",
        category = 4,
        description = "man: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F468 1F3FD",
        emojiContent = "👨🏽",
        category = 4,
        description = "man: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F468 1F3FE",
        emojiContent = "👨🏾",
        category = 4,
        description = "man: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F468 1F3FF",
        emojiContent = "👨🏿",
        category = 4,
        description = "man: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F469 1F3FB",
        emojiContent = "👩🏻",
        category = 4,
        description = "woman: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F469 1F3FC",
        emojiContent = "👩🏼",
        category = 4,
        description = "woman: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F469 1F3FD",
        emojiContent = "👩🏽",
        category = 4,
        description = "woman: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F469 1F3FE",
        emojiContent = "👩🏾",
        category = 4,
        description = "woman: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F469 1F3FF",
        emojiContent = "👩🏿",
        category = 4,
        description = "woman: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46B 1F3FB",
        emojiContent = "👫🏻",
        category = 4,
        description = "woman and man holding hands: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46B 1F3FC",
        emojiContent = "👫🏼",
        category = 4,
        description = "woman and man holding hands: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46B 1F3FD",
        emojiContent = "👫🏽",
        category = 4,
        description = "woman and man holding hands: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46B 1F3FE",
        emojiContent = "👫🏾",
        category = 4,
        description = "woman and man holding hands: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46B 1F3FF",
        emojiContent = "👫🏿",
        category = 4,
        description = "woman and man holding hands: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46C 1F3FB",
        emojiContent = "👬🏻",
        category = 4,
        description = "men holding hands: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46C 1F3FC",
        emojiContent = "👬🏼",
        category = 4,
        description = "men holding hands: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46C 1F3FD",
        emojiContent = "👬🏽",
        category = 4,
        description = "men holding hands: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46C 1F3FE",
        emojiContent = "👬🏾",
        category = 4,
        description = "men holding hands: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46C 1F3FF",
        emojiContent = "👬🏿",
        category = 4,
        description = "men holding hands: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46D 1F3FB",
        emojiContent = "👭🏻",
        category = 4,
        description = "women holding hands: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46D 1F3FC",
        emojiContent = "👭🏼",
        category = 4,
        description = "women holding hands: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46D 1F3FD",
        emojiContent = "👭🏽",
        category = 4,
        description = "women holding hands: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46D 1F3FE",
        emojiContent = "👭🏾",
        category = 4,
        description = "women holding hands: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46D 1F3FF",
        emojiContent = "👭🏿",
        category = 4,
        description = "women holding hands: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46E 1F3FB",
        emojiContent = "👮🏻",
        category = 4,
        description = "police officer: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46E 1F3FC",
        emojiContent = "👮🏼",
        category = 4,
        description = "police officer: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46E 1F3FD",
        emojiContent = "👮🏽",
        category = 4,
        description = "police officer: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46E 1F3FE",
        emojiContent = "👮🏾",
        category = 4,
        description = "police officer: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F46E 1F3FF",
        emojiContent = "👮🏿",
        category = 4,
        description = "police officer: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F470 1F3FB",
        emojiContent = "👰🏻",
        category = 4,
        description = "person with veil: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F470 1F3FC",
        emojiContent = "👰🏼",
        category = 4,
        description = "person with veil: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F470 1F3FD",
        emojiContent = "👰🏽",
        category = 4,
        description = "person with veil: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F470 1F3FE",
        emojiContent = "👰🏾",
        category = 4,
        description = "person with veil: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F470 1F3FF",
        emojiContent = "👰🏿",
        category = 4,
        description = "person with veil: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F471 1F3FB",
        emojiContent = "👱🏻",
        category = 4,
        description = "person: light skin tone, blond hair",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F471 1F3FC",
        emojiContent = "👱🏼",
        category = 4,
        description = "person: medium-light skin tone, blond hair",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F471 1F3FD",
        emojiContent = "👱🏽",
        category = 4,
        description = "person: medium skin tone, blond hair",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F471 1F3FE",
        emojiContent = "👱🏾",
        category = 4,
        description = "person: medium-dark skin tone, blond hair",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F471 1F3FF",
        emojiContent = "👱🏿",
        category = 4,
        description = "person: dark skin tone, blond hair",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F472 1F3FB",
        emojiContent = "👲🏻",
        category = 4,
        description = "person with skullcap: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F472 1F3FC",
        emojiContent = "👲🏼",
        category = 4,
        description = "person with skullcap: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F472 1F3FD",
        emojiContent = "👲🏽",
        category = 4,
        description = "person with skullcap: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F472 1F3FE",
        emojiContent = "👲🏾",
        category = 4,
        description = "person with skullcap: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F472 1F3FF",
        emojiContent = "👲🏿",
        category = 4,
        description = "person with skullcap: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F473 1F3FB",
        emojiContent = "👳🏻",
        category = 4,
        description = "person wearing turban: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F473 1F3FC",
        emojiContent = "👳🏼",
        category = 4,
        description = "person wearing turban: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F473 1F3FD",
        emojiContent = "👳🏽",
        category = 4,
        description = "person wearing turban: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F473 1F3FE",
        emojiContent = "👳🏾",
        category = 4,
        description = "person wearing turban: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F473 1F3FF",
        emojiContent = "👳🏿",
        category = 4,
        description = "person wearing turban: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F474 1F3FB",
        emojiContent = "👴🏻",
        category = 4,
        description = "old man: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F474 1F3FC",
        emojiContent = "👴🏼",
        category = 4,
        description = "old man: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F474 1F3FD",
        emojiContent = "👴🏽",
        category = 4,
        description = "old man: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F474 1F3FE",
        emojiContent = "👴🏾",
        category = 4,
        description = "old man: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F474 1F3FF",
        emojiContent = "👴🏿",
        category = 4,
        description = "old man: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F475 1F3FB",
        emojiContent = "👵🏻",
        category = 4,
        description = "old woman: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F475 1F3FC",
        emojiContent = "👵🏼",
        category = 4,
        description = "old woman: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F475 1F3FD",
        emojiContent = "👵🏽",
        category = 4,
        description = "old woman: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F475 1F3FE",
        emojiContent = "👵🏾",
        category = 4,
        description = "old woman: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F475 1F3FF",
        emojiContent = "👵🏿",
        category = 4,
        description = "old woman: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F476 1F3FB",
        emojiContent = "👶🏻",
        category = 4,
        description = "baby: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F476 1F3FC",
        emojiContent = "👶🏼",
        category = 4,
        description = "baby: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F476 1F3FD",
        emojiContent = "👶🏽",
        category = 4,
        description = "baby: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F476 1F3FE",
        emojiContent = "👶🏾",
        category = 4,
        description = "baby: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F476 1F3FF",
        emojiContent = "👶🏿",
        category = 4,
        description = "baby: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F477 1F3FB",
        emojiContent = "👷🏻",
        category = 4,
        description = "construction worker: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F477 1F3FC",
        emojiContent = "👷🏼",
        category = 4,
        description = "construction worker: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F477 1F3FD",
        emojiContent = "👷🏽",
        category = 4,
        description = "construction worker: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F477 1F3FE",
        emojiContent = "👷🏾",
        category = 4,
        description = "construction worker: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F477 1F3FF",
        emojiContent = "👷🏿",
        category = 4,
        description = "construction worker: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F478 1F3FB",
        emojiContent = "👸🏻",
        category = 4,
        description = "princess: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F478 1F3FC",
        emojiContent = "👸🏼",
        category = 4,
        description = "princess: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F478 1F3FD",
        emojiContent = "👸🏽",
        category = 4,
        description = "princess: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F478 1F3FE",
        emojiContent = "👸🏾",
        category = 4,
        description = "princess: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F478 1F3FF",
        emojiContent = "👸🏿",
        category = 4,
        description = "princess: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F47C 1F3FB",
        emojiContent = "👼🏻",
        category = 4,
        description = "baby angel: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F47C 1F3FC",
        emojiContent = "👼🏼",
        category = 4,
        description = "baby angel: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F47C 1F3FD",
        emojiContent = "👼🏽",
        category = 4,
        description = "baby angel: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F47C 1F3FE",
        emojiContent = "👼🏾",
        category = 4,
        description = "baby angel: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F47C 1F3FF",
        emojiContent = "👼🏿",
        category = 4,
        description = "baby angel: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F481 1F3FB",
        emojiContent = "💁🏻",
        category = 4,
        description = "person tipping hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F481 1F3FC",
        emojiContent = "💁🏼",
        category = 4,
        description = "person tipping hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F481 1F3FD",
        emojiContent = "💁🏽",
        category = 4,
        description = "person tipping hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F481 1F3FE",
        emojiContent = "💁🏾",
        category = 4,
        description = "person tipping hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F481 1F3FF",
        emojiContent = "💁🏿",
        category = 4,
        description = "person tipping hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F482 1F3FB",
        emojiContent = "💂🏻",
        category = 4,
        description = "guard: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F482 1F3FC",
        emojiContent = "💂🏼",
        category = 4,
        description = "guard: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F482 1F3FD",
        emojiContent = "💂🏽",
        category = 4,
        description = "guard: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F482 1F3FE",
        emojiContent = "💂🏾",
        category = 4,
        description = "guard: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F482 1F3FF",
        emojiContent = "💂🏿",
        category = 4,
        description = "guard: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F483 1F3FB",
        emojiContent = "💃🏻",
        category = 4,
        description = "woman dancing: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F483 1F3FC",
        emojiContent = "💃🏼",
        category = 4,
        description = "woman dancing: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F483 1F3FD",
        emojiContent = "💃🏽",
        category = 4,
        description = "woman dancing: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F483 1F3FE",
        emojiContent = "💃🏾",
        category = 4,
        description = "woman dancing: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F483 1F3FF",
        emojiContent = "💃🏿",
        category = 4,
        description = "woman dancing: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F485 1F3FB",
        emojiContent = "💅🏻",
        category = 4,
        description = "nail polish: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F485 1F3FC",
        emojiContent = "💅🏼",
        category = 4,
        description = "nail polish: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F485 1F3FD",
        emojiContent = "💅🏽",
        category = 4,
        description = "nail polish: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F485 1F3FE",
        emojiContent = "💅🏾",
        category = 4,
        description = "nail polish: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F485 1F3FF",
        emojiContent = "💅🏿",
        category = 4,
        description = "nail polish: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F486 1F3FB",
        emojiContent = "💆🏻",
        category = 4,
        description = "person getting massage: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F486 1F3FC",
        emojiContent = "💆🏼",
        category = 4,
        description = "person getting massage: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F486 1F3FD",
        emojiContent = "💆🏽",
        category = 4,
        description = "person getting massage: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F486 1F3FE",
        emojiContent = "💆🏾",
        category = 4,
        description = "person getting massage: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F486 1F3FF",
        emojiContent = "💆🏿",
        category = 4,
        description = "person getting massage: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F487 1F3FB",
        emojiContent = "💇🏻",
        category = 4,
        description = "person getting haircut: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F487 1F3FC",
        emojiContent = "💇🏼",
        category = 4,
        description = "person getting haircut: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F487 1F3FD",
        emojiContent = "💇🏽",
        category = 4,
        description = "person getting haircut: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F487 1F3FE",
        emojiContent = "💇🏾",
        category = 4,
        description = "person getting haircut: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F487 1F3FF",
        emojiContent = "💇🏿",
        category = 4,
        description = "person getting haircut: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F48F 1F3FB",
        emojiContent = "💏🏻",
        category = 4,
        description = "kiss: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F48F 1F3FC",
        emojiContent = "💏🏼",
        category = 4,
        description = "kiss: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F48F 1F3FD",
        emojiContent = "💏🏽",
        category = 4,
        description = "kiss: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F48F 1F3FE",
        emojiContent = "💏🏾",
        category = 4,
        description = "kiss: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F48F 1F3FF",
        emojiContent = "💏🏿",
        category = 4,
        description = "kiss: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F491 1F3FB",
        emojiContent = "💑🏻",
        category = 4,
        description = "couple with heart: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F491 1F3FC",
        emojiContent = "💑🏼",
        category = 4,
        description = "couple with heart: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F491 1F3FD",
        emojiContent = "💑🏽",
        category = 4,
        description = "couple with heart: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F491 1F3FE",
        emojiContent = "💑🏾",
        category = 4,
        description = "couple with heart: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F491 1F3FF",
        emojiContent = "💑🏿",
        category = 4,
        description = "couple with heart: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4AA 1F3FB",
        emojiContent = "💪🏻",
        category = 4,
        description = "flexed biceps: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4AA 1F3FC",
        emojiContent = "💪🏼",
        category = 4,
        description = "flexed biceps: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4AA 1F3FD",
        emojiContent = "💪🏽",
        category = 4,
        description = "flexed biceps: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4AA 1F3FE",
        emojiContent = "💪🏾",
        category = 4,
        description = "flexed biceps: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F4AA 1F3FF",
        emojiContent = "💪🏿",
        category = 4,
        description = "flexed biceps: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F574 1F3FB",
        emojiContent = "🕴🏻",
        category = 4,
        description = "person in suit levitating: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F574 1F3FC",
        emojiContent = "🕴🏼",
        category = 4,
        description = "person in suit levitating: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F574 1F3FD",
        emojiContent = "🕴🏽",
        category = 4,
        description = "person in suit levitating: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F574 1F3FE",
        emojiContent = "🕴🏾",
        category = 4,
        description = "person in suit levitating: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F574 1F3FF",
        emojiContent = "🕴🏿",
        category = 4,
        description = "person in suit levitating: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F575 1F3FB",
        emojiContent = "🕵🏻",
        category = 4,
        description = "detective: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F575 1F3FC",
        emojiContent = "🕵🏼",
        category = 4,
        description = "detective: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F575 1F3FD",
        emojiContent = "🕵🏽",
        category = 4,
        description = "detective: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F575 1F3FE",
        emojiContent = "🕵🏾",
        category = 4,
        description = "detective: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F575 1F3FF",
        emojiContent = "🕵🏿",
        category = 4,
        description = "detective: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F57A 1F3FB",
        emojiContent = "🕺🏻",
        category = 4,
        description = "man dancing: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F57A 1F3FC",
        emojiContent = "🕺🏼",
        category = 4,
        description = "man dancing: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F57A 1F3FD",
        emojiContent = "🕺🏽",
        category = 4,
        description = "man dancing: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F57A 1F3FE",
        emojiContent = "🕺🏾",
        category = 4,
        description = "man dancing: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F57A 1F3FF",
        emojiContent = "🕺🏿",
        category = 4,
        description = "man dancing: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F590 1F3FB",
        emojiContent = "🖐🏻",
        category = 4,
        description = "hand with fingers splayed: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F590 1F3FC",
        emojiContent = "🖐🏼",
        category = 4,
        description = "hand with fingers splayed: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F590 1F3FD",
        emojiContent = "🖐🏽",
        category = 4,
        description = "hand with fingers splayed: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F590 1F3FE",
        emojiContent = "🖐🏾",
        category = 4,
        description = "hand with fingers splayed: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F590 1F3FF",
        emojiContent = "🖐🏿",
        category = 4,
        description = "hand with fingers splayed: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F595 1F3FB",
        emojiContent = "🖕🏻",
        category = 4,
        description = "middle finger: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F595 1F3FC",
        emojiContent = "🖕🏼",
        category = 4,
        description = "middle finger: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F595 1F3FD",
        emojiContent = "🖕🏽",
        category = 4,
        description = "middle finger: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F595 1F3FE",
        emojiContent = "🖕🏾",
        category = 4,
        description = "middle finger: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F595 1F3FF",
        emojiContent = "🖕🏿",
        category = 4,
        description = "middle finger: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F596 1F3FB",
        emojiContent = "🖖🏻",
        category = 4,
        description = "vulcan salute: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F596 1F3FC",
        emojiContent = "🖖🏼",
        category = 4,
        description = "vulcan salute: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F596 1F3FD",
        emojiContent = "🖖🏽",
        category = 4,
        description = "vulcan salute: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F596 1F3FE",
        emojiContent = "🖖🏾",
        category = 4,
        description = "vulcan salute: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F596 1F3FF",
        emojiContent = "🖖🏿",
        category = 4,
        description = "vulcan salute: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F645 1F3FB",
        emojiContent = "🙅🏻",
        category = 4,
        description = "person gesturing NO: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F645 1F3FC",
        emojiContent = "🙅🏼",
        category = 4,
        description = "person gesturing NO: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F645 1F3FD",
        emojiContent = "🙅🏽",
        category = 4,
        description = "person gesturing NO: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F645 1F3FE",
        emojiContent = "🙅🏾",
        category = 4,
        description = "person gesturing NO: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F645 1F3FF",
        emojiContent = "🙅🏿",
        category = 4,
        description = "person gesturing NO: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F646 1F3FB",
        emojiContent = "🙆🏻",
        category = 4,
        description = "person gesturing OK: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F646 1F3FC",
        emojiContent = "🙆🏼",
        category = 4,
        description = "person gesturing OK: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F646 1F3FD",
        emojiContent = "🙆🏽",
        category = 4,
        description = "person gesturing OK: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F646 1F3FE",
        emojiContent = "🙆🏾",
        category = 4,
        description = "person gesturing OK: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F646 1F3FF",
        emojiContent = "🙆🏿",
        category = 4,
        description = "person gesturing OK: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F647 1F3FB",
        emojiContent = "🙇🏻",
        category = 4,
        description = "person bowing: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F647 1F3FC",
        emojiContent = "🙇🏼",
        category = 4,
        description = "person bowing: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F647 1F3FD",
        emojiContent = "🙇🏽",
        category = 4,
        description = "person bowing: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F647 1F3FE",
        emojiContent = "🙇🏾",
        category = 4,
        description = "person bowing: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F647 1F3FF",
        emojiContent = "🙇🏿",
        category = 4,
        description = "person bowing: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64B 1F3FB",
        emojiContent = "🙋🏻",
        category = 4,
        description = "person raising hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64B 1F3FC",
        emojiContent = "🙋🏼",
        category = 4,
        description = "person raising hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64B 1F3FD",
        emojiContent = "🙋🏽",
        category = 4,
        description = "person raising hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64B 1F3FE",
        emojiContent = "🙋🏾",
        category = 4,
        description = "person raising hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64B 1F3FF",
        emojiContent = "🙋🏿",
        category = 4,
        description = "person raising hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64C 1F3FB",
        emojiContent = "🙌🏻",
        category = 4,
        description = "raising hands: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64C 1F3FC",
        emojiContent = "🙌🏼",
        category = 4,
        description = "raising hands: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64C 1F3FD",
        emojiContent = "🙌🏽",
        category = 4,
        description = "raising hands: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64C 1F3FE",
        emojiContent = "🙌🏾",
        category = 4,
        description = "raising hands: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64C 1F3FF",
        emojiContent = "🙌🏿",
        category = 4,
        description = "raising hands: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64D 1F3FB",
        emojiContent = "🙍🏻",
        category = 4,
        description = "person frowning: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64D 1F3FC",
        emojiContent = "🙍🏼",
        category = 4,
        description = "person frowning: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64D 1F3FD",
        emojiContent = "🙍🏽",
        category = 4,
        description = "person frowning: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64D 1F3FE",
        emojiContent = "🙍🏾",
        category = 4,
        description = "person frowning: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64D 1F3FF",
        emojiContent = "🙍🏿",
        category = 4,
        description = "person frowning: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64E 1F3FB",
        emojiContent = "🙎🏻",
        category = 4,
        description = "person pouting: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64E 1F3FC",
        emojiContent = "🙎🏼",
        category = 4,
        description = "person pouting: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64E 1F3FD",
        emojiContent = "🙎🏽",
        category = 4,
        description = "person pouting: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64E 1F3FE",
        emojiContent = "🙎🏾",
        category = 4,
        description = "person pouting: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64E 1F3FF",
        emojiContent = "🙎🏿",
        category = 4,
        description = "person pouting: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64F 1F3FB",
        emojiContent = "🙏🏻",
        category = 4,
        description = "folded hands: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64F 1F3FC",
        emojiContent = "🙏🏼",
        category = 4,
        description = "folded hands: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64F 1F3FD",
        emojiContent = "🙏🏽",
        category = 4,
        description = "folded hands: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64F 1F3FE",
        emojiContent = "🙏🏾",
        category = 4,
        description = "folded hands: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F64F 1F3FF",
        emojiContent = "🙏🏿",
        category = 4,
        description = "folded hands: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6A3 1F3FB",
        emojiContent = "🚣🏻",
        category = 4,
        description = "person rowing boat: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6A3 1F3FC",
        emojiContent = "🚣🏼",
        category = 4,
        description = "person rowing boat: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6A3 1F3FD",
        emojiContent = "🚣🏽",
        category = 4,
        description = "person rowing boat: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6A3 1F3FE",
        emojiContent = "🚣🏾",
        category = 4,
        description = "person rowing boat: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6A3 1F3FF",
        emojiContent = "🚣🏿",
        category = 4,
        description = "person rowing boat: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B4 1F3FB",
        emojiContent = "🚴🏻",
        category = 4,
        description = "person biking: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B4 1F3FC",
        emojiContent = "🚴🏼",
        category = 4,
        description = "person biking: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B4 1F3FD",
        emojiContent = "🚴🏽",
        category = 4,
        description = "person biking: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B4 1F3FE",
        emojiContent = "🚴🏾",
        category = 4,
        description = "person biking: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B4 1F3FF",
        emojiContent = "🚴🏿",
        category = 4,
        description = "person biking: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B5 1F3FB",
        emojiContent = "🚵🏻",
        category = 4,
        description = "person mountain biking: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B5 1F3FC",
        emojiContent = "🚵🏼",
        category = 4,
        description = "person mountain biking: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B5 1F3FD",
        emojiContent = "🚵🏽",
        category = 4,
        description = "person mountain biking: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B5 1F3FE",
        emojiContent = "🚵🏾",
        category = 4,
        description = "person mountain biking: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B5 1F3FF",
        emojiContent = "🚵🏿",
        category = 4,
        description = "person mountain biking: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B6 1F3FB",
        emojiContent = "🚶🏻",
        category = 4,
        description = "person walking: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B6 1F3FC",
        emojiContent = "🚶🏼",
        category = 4,
        description = "person walking: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B6 1F3FD",
        emojiContent = "🚶🏽",
        category = 4,
        description = "person walking: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B6 1F3FE",
        emojiContent = "🚶🏾",
        category = 4,
        description = "person walking: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6B6 1F3FF",
        emojiContent = "🚶🏿",
        category = 4,
        description = "person walking: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6C0 1F3FB",
        emojiContent = "🛀🏻",
        category = 4,
        description = "person taking bath: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6C0 1F3FC",
        emojiContent = "🛀🏼",
        category = 4,
        description = "person taking bath: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6C0 1F3FD",
        emojiContent = "🛀🏽",
        category = 4,
        description = "person taking bath: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6C0 1F3FE",
        emojiContent = "🛀🏾",
        category = 4,
        description = "person taking bath: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6C0 1F3FF",
        emojiContent = "🛀🏿",
        category = 4,
        description = "person taking bath: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6CC 1F3FB",
        emojiContent = "🛌🏻",
        category = 4,
        description = "person in bed: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6CC 1F3FC",
        emojiContent = "🛌🏼",
        category = 4,
        description = "person in bed: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6CC 1F3FD",
        emojiContent = "🛌🏽",
        category = 4,
        description = "person in bed: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6CC 1F3FE",
        emojiContent = "🛌🏾",
        category = 4,
        description = "person in bed: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F6CC 1F3FF",
        emojiContent = "🛌🏿",
        category = 4,
        description = "person in bed: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90C 1F3FB",
        emojiContent = "🤌🏻",
        category = 4,
        description = "pinched fingers: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90C 1F3FC",
        emojiContent = "🤌🏼",
        category = 4,
        description = "pinched fingers: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90C 1F3FD",
        emojiContent = "🤌🏽",
        category = 4,
        description = "pinched fingers: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90C 1F3FE",
        emojiContent = "🤌🏾",
        category = 4,
        description = "pinched fingers: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90C 1F3FF",
        emojiContent = "🤌🏿",
        category = 4,
        description = "pinched fingers: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90F 1F3FB",
        emojiContent = "🤏🏻",
        category = 4,
        description = "pinching hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90F 1F3FC",
        emojiContent = "🤏🏼",
        category = 4,
        description = "pinching hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90F 1F3FD",
        emojiContent = "🤏🏽",
        category = 4,
        description = "pinching hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90F 1F3FE",
        emojiContent = "🤏🏾",
        category = 4,
        description = "pinching hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F90F 1F3FF",
        emojiContent = "🤏🏿",
        category = 4,
        description = "pinching hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F918 1F3FB",
        emojiContent = "🤘🏻",
        category = 4,
        description = "sign of the horns: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F918 1F3FC",
        emojiContent = "🤘🏼",
        category = 4,
        description = "sign of the horns: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F918 1F3FD",
        emojiContent = "🤘🏽",
        category = 4,
        description = "sign of the horns: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F918 1F3FE",
        emojiContent = "🤘🏾",
        category = 4,
        description = "sign of the horns: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F918 1F3FF",
        emojiContent = "🤘🏿",
        category = 4,
        description = "sign of the horns: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F919 1F3FB",
        emojiContent = "🤙🏻",
        category = 4,
        description = "call me hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F919 1F3FC",
        emojiContent = "🤙🏼",
        category = 4,
        description = "call me hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F919 1F3FD",
        emojiContent = "🤙🏽",
        category = 4,
        description = "call me hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F919 1F3FE",
        emojiContent = "🤙🏾",
        category = 4,
        description = "call me hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F919 1F3FF",
        emojiContent = "🤙🏿",
        category = 4,
        description = "call me hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91A 1F3FB",
        emojiContent = "🤚🏻",
        category = 4,
        description = "raised back of hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91A 1F3FC",
        emojiContent = "🤚🏼",
        category = 4,
        description = "raised back of hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91A 1F3FD",
        emojiContent = "🤚🏽",
        category = 4,
        description = "raised back of hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91A 1F3FE",
        emojiContent = "🤚🏾",
        category = 4,
        description = "raised back of hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91A 1F3FF",
        emojiContent = "🤚🏿",
        category = 4,
        description = "raised back of hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91B 1F3FB",
        emojiContent = "🤛🏻",
        category = 4,
        description = "left-facing fist: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91B 1F3FC",
        emojiContent = "🤛🏼",
        category = 4,
        description = "left-facing fist: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91B 1F3FD",
        emojiContent = "🤛🏽",
        category = 4,
        description = "left-facing fist: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91B 1F3FE",
        emojiContent = "🤛🏾",
        category = 4,
        description = "left-facing fist: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91B 1F3FF",
        emojiContent = "🤛🏿",
        category = 4,
        description = "left-facing fist: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91C 1F3FB",
        emojiContent = "🤜🏻",
        category = 4,
        description = "right-facing fist: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91C 1F3FC",
        emojiContent = "🤜🏼",
        category = 4,
        description = "right-facing fist: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91C 1F3FD",
        emojiContent = "🤜🏽",
        category = 4,
        description = "right-facing fist: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91C 1F3FE",
        emojiContent = "🤜🏾",
        category = 4,
        description = "right-facing fist: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91C 1F3FF",
        emojiContent = "🤜🏿",
        category = 4,
        description = "right-facing fist: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91D 1F3FB",
        emojiContent = "🤝🏻",
        category = 4,
        description = "handshake: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91D 1F3FC",
        emojiContent = "🤝🏼",
        category = 4,
        description = "handshake: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91D 1F3FD",
        emojiContent = "🤝🏽",
        category = 4,
        description = "handshake: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91D 1F3FE",
        emojiContent = "🤝🏾",
        category = 4,
        description = "handshake: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91D 1F3FF",
        emojiContent = "🤝🏿",
        category = 4,
        description = "handshake: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91E 1F3FB",
        emojiContent = "🤞🏻",
        category = 4,
        description = "crossed fingers: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91E 1F3FC",
        emojiContent = "🤞🏼",
        category = 4,
        description = "crossed fingers: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91E 1F3FD",
        emojiContent = "🤞🏽",
        category = 4,
        description = "crossed fingers: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91E 1F3FE",
        emojiContent = "🤞🏾",
        category = 4,
        description = "crossed fingers: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91E 1F3FF",
        emojiContent = "🤞🏿",
        category = 4,
        description = "crossed fingers: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91F 1F3FB",
        emojiContent = "🤟🏻",
        category = 4,
        description = "love-you gesture: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91F 1F3FC",
        emojiContent = "🤟🏼",
        category = 4,
        description = "love-you gesture: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91F 1F3FD",
        emojiContent = "🤟🏽",
        category = 4,
        description = "love-you gesture: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91F 1F3FE",
        emojiContent = "🤟🏾",
        category = 4,
        description = "love-you gesture: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F91F 1F3FF",
        emojiContent = "🤟🏿",
        category = 4,
        description = "love-you gesture: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F926 1F3FB",
        emojiContent = "🤦🏻",
        category = 4,
        description = "person facepalming: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F926 1F3FC",
        emojiContent = "🤦🏼",
        category = 4,
        description = "person facepalming: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F926 1F3FD",
        emojiContent = "🤦🏽",
        category = 4,
        description = "person facepalming: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F926 1F3FE",
        emojiContent = "🤦🏾",
        category = 4,
        description = "person facepalming: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F926 1F3FF",
        emojiContent = "🤦🏿",
        category = 4,
        description = "person facepalming: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F930 1F3FB",
        emojiContent = "🤰🏻",
        category = 4,
        description = "pregnant woman: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F930 1F3FC",
        emojiContent = "🤰🏼",
        category = 4,
        description = "pregnant woman: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F930 1F3FD",
        emojiContent = "🤰🏽",
        category = 4,
        description = "pregnant woman: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F930 1F3FE",
        emojiContent = "🤰🏾",
        category = 4,
        description = "pregnant woman: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F930 1F3FF",
        emojiContent = "🤰🏿",
        category = 4,
        description = "pregnant woman: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F931 1F3FB",
        emojiContent = "🤱🏻",
        category = 4,
        description = "breast-feeding: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F931 1F3FC",
        emojiContent = "🤱🏼",
        category = 4,
        description = "breast-feeding: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F931 1F3FD",
        emojiContent = "🤱🏽",
        category = 4,
        description = "breast-feeding: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F931 1F3FE",
        emojiContent = "🤱🏾",
        category = 4,
        description = "breast-feeding: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F931 1F3FF",
        emojiContent = "🤱🏿",
        category = 4,
        description = "breast-feeding: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F932 1F3FB",
        emojiContent = "🤲🏻",
        category = 4,
        description = "palms up together: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F932 1F3FC",
        emojiContent = "🤲🏼",
        category = 4,
        description = "palms up together: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F932 1F3FD",
        emojiContent = "🤲🏽",
        category = 4,
        description = "palms up together: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F932 1F3FE",
        emojiContent = "🤲🏾",
        category = 4,
        description = "palms up together: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F932 1F3FF",
        emojiContent = "🤲🏿",
        category = 4,
        description = "palms up together: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F933 1F3FB",
        emojiContent = "🤳🏻",
        category = 4,
        description = "selfie: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F933 1F3FC",
        emojiContent = "🤳🏼",
        category = 4,
        description = "selfie: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F933 1F3FD",
        emojiContent = "🤳🏽",
        category = 4,
        description = "selfie: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F933 1F3FE",
        emojiContent = "🤳🏾",
        category = 4,
        description = "selfie: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F933 1F3FF",
        emojiContent = "🤳🏿",
        category = 4,
        description = "selfie: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F934 1F3FB",
        emojiContent = "🤴🏻",
        category = 4,
        description = "prince: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F934 1F3FC",
        emojiContent = "🤴🏼",
        category = 4,
        description = "prince: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F934 1F3FD",
        emojiContent = "🤴🏽",
        category = 4,
        description = "prince: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F934 1F3FE",
        emojiContent = "🤴🏾",
        category = 4,
        description = "prince: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F934 1F3FF",
        emojiContent = "🤴🏿",
        category = 4,
        description = "prince: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F935 1F3FB",
        emojiContent = "🤵🏻",
        category = 4,
        description = "person in tuxedo: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F935 1F3FC",
        emojiContent = "🤵🏼",
        category = 4,
        description = "person in tuxedo: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F935 1F3FD",
        emojiContent = "🤵🏽",
        category = 4,
        description = "person in tuxedo: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F935 1F3FE",
        emojiContent = "🤵🏾",
        category = 4,
        description = "person in tuxedo: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F935 1F3FF",
        emojiContent = "🤵🏿",
        category = 4,
        description = "person in tuxedo: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F936 1F3FB",
        emojiContent = "🤶🏻",
        category = 4,
        description = "Mrs. Claus: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F936 1F3FC",
        emojiContent = "🤶🏼",
        category = 4,
        description = "Mrs. Claus: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F936 1F3FD",
        emojiContent = "🤶🏽",
        category = 4,
        description = "Mrs. Claus: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F936 1F3FE",
        emojiContent = "🤶🏾",
        category = 4,
        description = "Mrs. Claus: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F936 1F3FF",
        emojiContent = "🤶🏿",
        category = 4,
        description = "Mrs. Claus: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F937 1F3FB",
        emojiContent = "🤷🏻",
        category = 4,
        description = "person shrugging: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F937 1F3FC",
        emojiContent = "🤷🏼",
        category = 4,
        description = "person shrugging: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F937 1F3FD",
        emojiContent = "🤷🏽",
        category = 4,
        description = "person shrugging: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F937 1F3FE",
        emojiContent = "🤷🏾",
        category = 4,
        description = "person shrugging: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F937 1F3FF",
        emojiContent = "🤷🏿",
        category = 4,
        description = "person shrugging: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F938 1F3FB",
        emojiContent = "🤸🏻",
        category = 4,
        description = "person cartwheeling: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F938 1F3FC",
        emojiContent = "🤸🏼",
        category = 4,
        description = "person cartwheeling: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F938 1F3FD",
        emojiContent = "🤸🏽",
        category = 4,
        description = "person cartwheeling: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F938 1F3FE",
        emojiContent = "🤸🏾",
        category = 4,
        description = "person cartwheeling: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F938 1F3FF",
        emojiContent = "🤸🏿",
        category = 4,
        description = "person cartwheeling: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F939 1F3FB",
        emojiContent = "🤹🏻",
        category = 4,
        description = "person juggling: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F939 1F3FC",
        emojiContent = "🤹🏼",
        category = 4,
        description = "person juggling: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F939 1F3FD",
        emojiContent = "🤹🏽",
        category = 4,
        description = "person juggling: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F939 1F3FE",
        emojiContent = "🤹🏾",
        category = 4,
        description = "person juggling: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F939 1F3FF",
        emojiContent = "🤹🏿",
        category = 4,
        description = "person juggling: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93D 1F3FB",
        emojiContent = "🤽🏻",
        category = 4,
        description = "person playing water polo: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93D 1F3FC",
        emojiContent = "🤽🏼",
        category = 4,
        description = "person playing water polo: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93D 1F3FD",
        emojiContent = "🤽🏽",
        category = 4,
        description = "person playing water polo: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93D 1F3FE",
        emojiContent = "🤽🏾",
        category = 4,
        description = "person playing water polo: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93D 1F3FF",
        emojiContent = "🤽🏿",
        category = 4,
        description = "person playing water polo: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93E 1F3FB",
        emojiContent = "🤾🏻",
        category = 4,
        description = "person playing handball: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93E 1F3FC",
        emojiContent = "🤾🏼",
        category = 4,
        description = "person playing handball: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93E 1F3FD",
        emojiContent = "🤾🏽",
        category = 4,
        description = "person playing handball: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93E 1F3FE",
        emojiContent = "🤾🏾",
        category = 4,
        description = "person playing handball: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F93E 1F3FF",
        emojiContent = "🤾🏿",
        category = 4,
        description = "person playing handball: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F977 1F3FB",
        emojiContent = "🥷🏻",
        category = 4,
        description = "ninja: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F977 1F3FC",
        emojiContent = "🥷🏼",
        category = 4,
        description = "ninja: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F977 1F3FD",
        emojiContent = "🥷🏽",
        category = 4,
        description = "ninja: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F977 1F3FE",
        emojiContent = "🥷🏾",
        category = 4,
        description = "ninja: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F977 1F3FF",
        emojiContent = "🥷🏿",
        category = 4,
        description = "ninja: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B5 1F3FB",
        emojiContent = "🦵🏻",
        category = 4,
        description = "leg: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B5 1F3FC",
        emojiContent = "🦵🏼",
        category = 4,
        description = "leg: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B5 1F3FD",
        emojiContent = "🦵🏽",
        category = 4,
        description = "leg: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B5 1F3FE",
        emojiContent = "🦵🏾",
        category = 4,
        description = "leg: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B5 1F3FF",
        emojiContent = "🦵🏿",
        category = 4,
        description = "leg: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B6 1F3FB",
        emojiContent = "🦶🏻",
        category = 4,
        description = "foot: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B6 1F3FC",
        emojiContent = "🦶🏼",
        category = 4,
        description = "foot: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B6 1F3FD",
        emojiContent = "🦶🏽",
        category = 4,
        description = "foot: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B6 1F3FE",
        emojiContent = "🦶🏾",
        category = 4,
        description = "foot: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B6 1F3FF",
        emojiContent = "🦶🏿",
        category = 4,
        description = "foot: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B8 1F3FB",
        emojiContent = "🦸🏻",
        category = 4,
        description = "superhero: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B8 1F3FC",
        emojiContent = "🦸🏼",
        category = 4,
        description = "superhero: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B8 1F3FD",
        emojiContent = "🦸🏽",
        category = 4,
        description = "superhero: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B8 1F3FE",
        emojiContent = "🦸🏾",
        category = 4,
        description = "superhero: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B8 1F3FF",
        emojiContent = "🦸🏿",
        category = 4,
        description = "superhero: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B9 1F3FB",
        emojiContent = "🦹🏻",
        category = 4,
        description = "supervillain: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B9 1F3FC",
        emojiContent = "🦹🏼",
        category = 4,
        description = "supervillain: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B9 1F3FD",
        emojiContent = "🦹🏽",
        category = 4,
        description = "supervillain: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B9 1F3FE",
        emojiContent = "🦹🏾",
        category = 4,
        description = "supervillain: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9B9 1F3FF",
        emojiContent = "🦹🏿",
        category = 4,
        description = "supervillain: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9BB 1F3FB",
        emojiContent = "🦻🏻",
        category = 4,
        description = "ear with hearing aid: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9BB 1F3FC",
        emojiContent = "🦻🏼",
        category = 4,
        description = "ear with hearing aid: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9BB 1F3FD",
        emojiContent = "🦻🏽",
        category = 4,
        description = "ear with hearing aid: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9BB 1F3FE",
        emojiContent = "🦻🏾",
        category = 4,
        description = "ear with hearing aid: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9BB 1F3FF",
        emojiContent = "🦻🏿",
        category = 4,
        description = "ear with hearing aid: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CD 1F3FB",
        emojiContent = "🧍🏻",
        category = 4,
        description = "person standing: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CD 1F3FC",
        emojiContent = "🧍🏼",
        category = 4,
        description = "person standing: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CD 1F3FD",
        emojiContent = "🧍🏽",
        category = 4,
        description = "person standing: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CD 1F3FE",
        emojiContent = "🧍🏾",
        category = 4,
        description = "person standing: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CD 1F3FF",
        emojiContent = "🧍🏿",
        category = 4,
        description = "person standing: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CE 1F3FB",
        emojiContent = "🧎🏻",
        category = 4,
        description = "person kneeling: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CE 1F3FC",
        emojiContent = "🧎🏼",
        category = 4,
        description = "person kneeling: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CE 1F3FD",
        emojiContent = "🧎🏽",
        category = 4,
        description = "person kneeling: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CE 1F3FE",
        emojiContent = "🧎🏾",
        category = 4,
        description = "person kneeling: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CE 1F3FF",
        emojiContent = "🧎🏿",
        category = 4,
        description = "person kneeling: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CF 1F3FB",
        emojiContent = "🧏🏻",
        category = 4,
        description = "deaf person: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CF 1F3FC",
        emojiContent = "🧏🏼",
        category = 4,
        description = "deaf person: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CF 1F3FD",
        emojiContent = "🧏🏽",
        category = 4,
        description = "deaf person: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CF 1F3FE",
        emojiContent = "🧏🏾",
        category = 4,
        description = "deaf person: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9CF 1F3FF",
        emojiContent = "🧏🏿",
        category = 4,
        description = "deaf person: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D1 1F3FB",
        emojiContent = "🧑🏻",
        category = 4,
        description = "person: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D1 1F3FC",
        emojiContent = "🧑🏼",
        category = 4,
        description = "person: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D1 1F3FD",
        emojiContent = "🧑🏽",
        category = 4,
        description = "person: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D1 1F3FE",
        emojiContent = "🧑🏾",
        category = 4,
        description = "person: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D1 1F3FF",
        emojiContent = "🧑🏿",
        category = 4,
        description = "person: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D2 1F3FB",
        emojiContent = "🧒🏻",
        category = 4,
        description = "child: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D2 1F3FC",
        emojiContent = "🧒🏼",
        category = 4,
        description = "child: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D2 1F3FD",
        emojiContent = "🧒🏽",
        category = 4,
        description = "child: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D2 1F3FE",
        emojiContent = "🧒🏾",
        category = 4,
        description = "child: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D2 1F3FF",
        emojiContent = "🧒🏿",
        category = 4,
        description = "child: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D3 1F3FB",
        emojiContent = "🧓🏻",
        category = 4,
        description = "older person: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D3 1F3FC",
        emojiContent = "🧓🏼",
        category = 4,
        description = "older person: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D3 1F3FD",
        emojiContent = "🧓🏽",
        category = 4,
        description = "older person: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D3 1F3FE",
        emojiContent = "🧓🏾",
        category = 4,
        description = "older person: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D3 1F3FF",
        emojiContent = "🧓🏿",
        category = 4,
        description = "older person: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D4 1F3FB",
        emojiContent = "🧔🏻",
        category = 4,
        description = "person: light skin tone, beard",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D4 1F3FC",
        emojiContent = "🧔🏼",
        category = 4,
        description = "person: medium-light skin tone, beard",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D4 1F3FD",
        emojiContent = "🧔🏽",
        category = 4,
        description = "person: medium skin tone, beard",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D4 1F3FE",
        emojiContent = "🧔🏾",
        category = 4,
        description = "person: medium-dark skin tone, beard",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D4 1F3FF",
        emojiContent = "🧔🏿",
        category = 4,
        description = "person: dark skin tone, beard",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D5 1F3FB",
        emojiContent = "🧕🏻",
        category = 4,
        description = "woman with headscarf: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D5 1F3FC",
        emojiContent = "🧕🏼",
        category = 4,
        description = "woman with headscarf: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D5 1F3FD",
        emojiContent = "🧕🏽",
        category = 4,
        description = "woman with headscarf: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D5 1F3FE",
        emojiContent = "🧕🏾",
        category = 4,
        description = "woman with headscarf: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D5 1F3FF",
        emojiContent = "🧕🏿",
        category = 4,
        description = "woman with headscarf: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D6 1F3FB",
        emojiContent = "🧖🏻",
        category = 4,
        description = "person in steamy room: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D6 1F3FC",
        emojiContent = "🧖🏼",
        category = 4,
        description = "person in steamy room: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D6 1F3FD",
        emojiContent = "🧖🏽",
        category = 4,
        description = "person in steamy room: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D6 1F3FE",
        emojiContent = "🧖🏾",
        category = 4,
        description = "person in steamy room: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D6 1F3FF",
        emojiContent = "🧖🏿",
        category = 4,
        description = "person in steamy room: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D7 1F3FB",
        emojiContent = "🧗🏻",
        category = 4,
        description = "person climbing: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D7 1F3FC",
        emojiContent = "🧗🏼",
        category = 4,
        description = "person climbing: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D7 1F3FD",
        emojiContent = "🧗🏽",
        category = 4,
        description = "person climbing: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D7 1F3FE",
        emojiContent = "🧗🏾",
        category = 4,
        description = "person climbing: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D7 1F3FF",
        emojiContent = "🧗🏿",
        category = 4,
        description = "person climbing: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D8 1F3FB",
        emojiContent = "🧘🏻",
        category = 4,
        description = "person in lotus position: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D8 1F3FC",
        emojiContent = "🧘🏼",
        category = 4,
        description = "person in lotus position: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D8 1F3FD",
        emojiContent = "🧘🏽",
        category = 4,
        description = "person in lotus position: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D8 1F3FE",
        emojiContent = "🧘🏾",
        category = 4,
        description = "person in lotus position: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D8 1F3FF",
        emojiContent = "🧘🏿",
        category = 4,
        description = "person in lotus position: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D9 1F3FB",
        emojiContent = "🧙🏻",
        category = 4,
        description = "mage: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D9 1F3FC",
        emojiContent = "🧙🏼",
        category = 4,
        description = "mage: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D9 1F3FD",
        emojiContent = "🧙🏽",
        category = 4,
        description = "mage: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D9 1F3FE",
        emojiContent = "🧙🏾",
        category = 4,
        description = "mage: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9D9 1F3FF",
        emojiContent = "🧙🏿",
        category = 4,
        description = "mage: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DA 1F3FB",
        emojiContent = "🧚🏻",
        category = 4,
        description = "fairy: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DA 1F3FC",
        emojiContent = "🧚🏼",
        category = 4,
        description = "fairy: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DA 1F3FD",
        emojiContent = "🧚🏽",
        category = 4,
        description = "fairy: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DA 1F3FE",
        emojiContent = "🧚🏾",
        category = 4,
        description = "fairy: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DA 1F3FF",
        emojiContent = "🧚🏿",
        category = 4,
        description = "fairy: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DB 1F3FB",
        emojiContent = "🧛🏻",
        category = 4,
        description = "vampire: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DB 1F3FC",
        emojiContent = "🧛🏼",
        category = 4,
        description = "vampire: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DB 1F3FD",
        emojiContent = "🧛🏽",
        category = 4,
        description = "vampire: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DB 1F3FE",
        emojiContent = "🧛🏾",
        category = 4,
        description = "vampire: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DB 1F3FF",
        emojiContent = "🧛🏿",
        category = 4,
        description = "vampire: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DC 1F3FB",
        emojiContent = "🧜🏻",
        category = 4,
        description = "merperson: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DC 1F3FC",
        emojiContent = "🧜🏼",
        category = 4,
        description = "merperson: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DC 1F3FD",
        emojiContent = "🧜🏽",
        category = 4,
        description = "merperson: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DC 1F3FE",
        emojiContent = "🧜🏾",
        category = 4,
        description = "merperson: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DC 1F3FF",
        emojiContent = "🧜🏿",
        category = 4,
        description = "merperson: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DD 1F3FB",
        emojiContent = "🧝🏻",
        category = 4,
        description = "elf: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DD 1F3FC",
        emojiContent = "🧝🏼",
        category = 4,
        description = "elf: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DD 1F3FD",
        emojiContent = "🧝🏽",
        category = 4,
        description = "elf: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DD 1F3FE",
        emojiContent = "🧝🏾",
        category = 4,
        description = "elf: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1F9DD 1F3FF",
        emojiContent = "🧝🏿",
        category = 4,
        description = "elf: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC3 1F3FB",
        emojiContent = "🫃🏻",
        category = 4,
        description = "pregnant man: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC3 1F3FC",
        emojiContent = "🫃🏼",
        category = 4,
        description = "pregnant man: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC3 1F3FD",
        emojiContent = "🫃🏽",
        category = 4,
        description = "pregnant man: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC3 1F3FE",
        emojiContent = "🫃🏾",
        category = 4,
        description = "pregnant man: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC3 1F3FF",
        emojiContent = "🫃🏿",
        category = 4,
        description = "pregnant man: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC4 1F3FB",
        emojiContent = "🫄🏻",
        category = 4,
        description = "pregnant person: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC4 1F3FC",
        emojiContent = "🫄🏼",
        category = 4,
        description = "pregnant person: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC4 1F3FD",
        emojiContent = "🫄🏽",
        category = 4,
        description = "pregnant person: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC4 1F3FE",
        emojiContent = "🫄🏾",
        category = 4,
        description = "pregnant person: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC4 1F3FF",
        emojiContent = "🫄🏿",
        category = 4,
        description = "pregnant person: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC5 1F3FB",
        emojiContent = "🫅🏻",
        category = 4,
        description = "person with crown: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC5 1F3FC",
        emojiContent = "🫅🏼",
        category = 4,
        description = "person with crown: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC5 1F3FD",
        emojiContent = "🫅🏽",
        category = 4,
        description = "person with crown: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC5 1F3FE",
        emojiContent = "🫅🏾",
        category = 4,
        description = "person with crown: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAC5 1F3FF",
        emojiContent = "🫅🏿",
        category = 4,
        description = "person with crown: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF0 1F3FB",
        emojiContent = "🫰🏻",
        category = 4,
        description = "hand with index finger and thumb crossed: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF0 1F3FC",
        emojiContent = "🫰🏼",
        category = 4,
        description = "hand with index finger and thumb crossed: medium-light skin to",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF0 1F3FD",
        emojiContent = "🫰🏽",
        category = 4,
        description = "hand with index finger and thumb crossed: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF0 1F3FE",
        emojiContent = "🫰🏾",
        category = 4,
        description = "hand with index finger and thumb crossed: medium-dark skin ton",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF0 1F3FF",
        emojiContent = "🫰🏿",
        category = 4,
        description = "hand with index finger and thumb crossed: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF1 1F3FB",
        emojiContent = "🫱🏻",
        category = 4,
        description = "rightwards hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF1 1F3FC",
        emojiContent = "🫱🏼",
        category = 4,
        description = "rightwards hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF1 1F3FD",
        emojiContent = "🫱🏽",
        category = 4,
        description = "rightwards hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF1 1F3FE",
        emojiContent = "🫱🏾",
        category = 4,
        description = "rightwards hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF1 1F3FF",
        emojiContent = "🫱🏿",
        category = 4,
        description = "rightwards hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF2 1F3FB",
        emojiContent = "🫲🏻",
        category = 4,
        description = "leftwards hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF2 1F3FC",
        emojiContent = "🫲🏼",
        category = 4,
        description = "leftwards hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF2 1F3FD",
        emojiContent = "🫲🏽",
        category = 4,
        description = "leftwards hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF2 1F3FE",
        emojiContent = "🫲🏾",
        category = 4,
        description = "leftwards hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF2 1F3FF",
        emojiContent = "🫲🏿",
        category = 4,
        description = "leftwards hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF3 1F3FB",
        emojiContent = "🫳🏻",
        category = 4,
        description = "palm down hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF3 1F3FC",
        emojiContent = "🫳🏼",
        category = 4,
        description = "palm down hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF3 1F3FD",
        emojiContent = "🫳🏽",
        category = 4,
        description = "palm down hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF3 1F3FE",
        emojiContent = "🫳🏾",
        category = 4,
        description = "palm down hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF3 1F3FF",
        emojiContent = "🫳🏿",
        category = 4,
        description = "palm down hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF4 1F3FB",
        emojiContent = "🫴🏻",
        category = 4,
        description = "palm up hand: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF4 1F3FC",
        emojiContent = "🫴🏼",
        category = 4,
        description = "palm up hand: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF4 1F3FD",
        emojiContent = "🫴🏽",
        category = 4,
        description = "palm up hand: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF4 1F3FE",
        emojiContent = "🫴🏾",
        category = 4,
        description = "palm up hand: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF4 1F3FF",
        emojiContent = "🫴🏿",
        category = 4,
        description = "palm up hand: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF5 1F3FB",
        emojiContent = "🫵🏻",
        category = 4,
        description = "index pointing at the viewer: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF5 1F3FC",
        emojiContent = "🫵🏼",
        category = 4,
        description = "index pointing at the viewer: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF5 1F3FD",
        emojiContent = "🫵🏽",
        category = 4,
        description = "index pointing at the viewer: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF5 1F3FE",
        emojiContent = "🫵🏾",
        category = 4,
        description = "index pointing at the viewer: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF5 1F3FF",
        emojiContent = "🫵🏿",
        category = 4,
        description = "index pointing at the viewer: dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF6 1F3FB",
        emojiContent = "🫶🏻",
        category = 4,
        description = "heart hands: light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF6 1F3FC",
        emojiContent = "🫶🏼",
        category = 4,
        description = "heart hands: medium-light skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF6 1F3FD",
        emojiContent = "🫶🏽",
        category = 4,
        description = "heart hands: medium skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF6 1F3FE",
        emojiContent = "🫶🏾",
        category = 4,
        description = "heart hands: medium-dark skin tone",
        isRange = false
    ),
    EmojiItem(
        hexCode = "1FAF6 1F3FF",
        emojiContent = "🫶🏿",
        category = 4,
        description = "heart hands: dark skin tone",
        isRange = false
    )
)
