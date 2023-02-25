# Created By Aditya Bavadekar 12:28 24-02-2023

import os
import json

#### TODO ####
SOURCE_FILE = "D:\\emoji-sequences.txt"
OUTPUT_DIR = "D:\\GeneratedEmojiFiles\\"
EXTRA_FILE_PATH = OUTPUT_DIR + "RangeEmojisDescriptionChangeNeeded.kt"
#### TODO ####

INDEX_TEXT_BASIC_EMOJI = 39
CATEGORIES = ["Basic_Emoji", "Emoji_Keycap_Sequence", "RGI_Emoji_Flag_Sequence", "RGI_Emoji_Tag_Sequence",
              "RGI_Emoji_Modifier_Sequence"]
SPACE = "231A..231B    ; Basic_Emoji                  "
INDEX_OF_OPEN_BRACKET = 123
INDEX_OF_FIRST_HASHTAG = 110
INDEX_OF_FIRST_SEP = 14
INDEX_OF_SECOND_SEP = 45
TEXT = "package com.example //TODO" \
       "\n\n" \
       "/*\n" \
       "* Created by Aditya Bavadekar" \
       "* Use following to convert to emoji :\n" \
       "* 1. for \"23F0\" -> \"23F0\".toInt(16)\n" \
       "* 2. for \"0x23F0\" -> Integer.decode(\"0x23F0\")\n" \
       "* use this with int from above `String(Character.toChars(int))`\n" \
       "**/\n"
FLAG_PRINT = False


def get_hex_code(line):
    first_index = line.find(";")
    return str(line[:first_index - 1]).strip()


def get_description(line):
    first_index = line.find(";") + 1
    first_index_hashtag = line.find("#")
    return str(line[line.find(";", first_index) + 1:first_index_hashtag - 1]).strip()


def get_category(line):
    first_index = line.find(";") + 1
    return str(line[first_index:line.find(";", first_index)]).strip()


def get_emoji(line):
    first_index = line.find("]") + 2
    return str(line[first_index:]).replace(")", "").replace("(", "").strip()


def write_data(file, hex_code, emoji, category, desc, str_is_range, write_emoji):
    if write_emoji:
        file.write(
            f"    EmojiItem(hexCode=\"{hex_code}\",emojiContent=\"{emoji}\",category={category},description=\"{desc}\"), \n")
    else:
        file.write(
            f"    EmojiItem(hexCode=\"{hex_code}\",category={category},description=\"{desc}\"), \n")


def get_json_data(hex_code, emoji, category, desc):
    return {"hex_code": hex_code, "emoji": emoji, "description": desc, "category": category}


def fprint(line):
    if FLAG_PRINT:
        print(line)


def get_in_between_hex(start, end):
    hexes = []
    for i in range(int(start, 16), int(end, 16) + 1):
        print(chr(i))
        hexes.append({"hex": hex(i).upper(), "emoji": chr(i)})

    return hexes


def read_lines(path):
    f = open(path, "r", encoding="UTF-8")
    content = f.readlines()[INDEX_TEXT_BASIC_EMOJI:]
    f.close()
    return content


def get_file_for_category(category, mode="a"):
    file_path = OUTPUT_DIR + category + ".kt"
    if category not in CATEGORIES:
        print(category)
        print(f"Get file -> {file_path}")
        exit(-1)
    return open(file_path, mode, encoding="UTF-8")


def write_required_pre_text():
    for category in CATEGORIES:
        with get_file_for_category(category, "w+") as f:
            f.write(TEXT + "val emojiData = listOf(\n")


def print_file_size(path):
    size = os.path.getsize(path) / 1024
    print(f"All data saved to {path} ({round(size)} KB)")


def write_required_end_text():
    print()
    CATEGORIES.sort()
    for category in CATEGORIES:
        file_path = OUTPUT_DIR + category + ".kt"
        with open(file_path, "a", encoding="UTF-8") as f:
            f.write(")\n")
        print_file_size(file_path)
    print_file_size(EXTRA_FILE_PATH)
    print_file_size(OUTPUT_DIR + "emoji-json-data.json")


def main(contents):
    print(f"Writing main...")
    emojis_count = 0
    unknown_cat_count = 0
    finished_cats = []
    json_data = []
    prev_category = CATEGORIES[0]
    f = get_file_for_category(CATEGORIES[0])
    for line in contents:
        fprint(f"Main id -> {emojis_count}")
        emoji = get_emoji(line)
        cat = get_category(line).strip()
        desc = get_description(line)
        hex_code = get_hex_code(line)
        hex_code_2 = hex_code
        is_range = hex_code.__contains__("..")
        str_is_range = "false"
        if cat == "" or line.startswith("#"):
            fprint("Line without emoji : " + line)
            emojis_count += 1
            continue
        if prev_category != cat:
            prev_category = cat
            fprint(f"Changed category to {cat} -> {hex_code}")
            f.close()
            f = get_file_for_category(cat)

        if emoji != "":
            emojis_count += 1
            fprint("---")
            fprint("Hex : " + hex_code)
            if is_range:
                start = hex_code.find("..")
                hex_code = "0x" + hex_code[:start:]
                hex_code_2 = "0x" + hex_code_2[start + 2:]
                str_is_range = "true"
                fprint("Emoji : " + emoji[0])
                fprint("Emoji : " + emoji[3])
            else:
                if hex_code.__contains__(" "):
                    hex_code = "0x" + hex_code[:hex_code.find(" ") - 1]
                fprint("Emoji : " + emoji)

            if cat not in finished_cats:
                finished_cats.append(cat)
                print(f"{emojis_count}) Category : " + cat)
            fprint("Description : " + desc)

            if cat in CATEGORIES:
                category = CATEGORIES.index(cat)
                fprint(f"CategoryId={category}")

                if is_range:
                    sub = open(EXTRA_FILE_PATH, "a", encoding="UTF-8")
                    for i in range(int(hex_code, 16), int(hex_code_2, 16) + 1):
                        # inp = input(f"What is this {chr(i)}?: ")
                        # if inp != ".":
                        #     desc = inp
                        json_data.append(get_json_data(hex(i), chr(i), category, desc))
                        write_data(f, hex(i), chr(i), category, desc, str_is_range, False)
                        write_data(sub, hex(i), chr(i), category, desc, str_is_range, True)
                    sub.close()
                else:
                    json_data.append(get_json_data(hex_code, emoji, category, desc))
                    write_data(f, hex_code, emoji, category, desc, str_is_range, False)
            else:
                print("Unknown : " + cat)
                unknown_cat_count += 1
                fprint("CategoryId=Unknown!!!")

    print("Writing main done.")
    print("Writing all json data...")
    with open(OUTPUT_DIR + "emoji-json-data.json", "w+") as jfile:
        jfile.writelines(json.dumps({"data": json_data}, indent=2))

    print(f"Found {emojis_count} emojis, Unknown categories : {unknown_cat_count}")
    f.close()


def clear(path):
    with open(path, "w+", encoding="UTF-8") as f:
        f.write("")


if __name__ == "__main__":

    try:
        os.mkdir(OUTPUT_DIR)
    except OSError as e:
        pass
    clear(EXTRA_FILE_PATH)
    write_required_pre_text()
    contents = read_lines(SOURCE_FILE)[39:]
    main(contents)
    write_required_end_text()
