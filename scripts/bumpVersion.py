import os

FILE_PATH = "..\\buildSrc\\src\\main\\java\\Versions.kt"
VARIABLE_NAME_VERSION_NAME = "VERSION_NAME"
VARIABLE_NAME_VERSION_CODE = "VERSION_CODE"
contents = []
new_version_code = -1
new_version_name = "-1"
old_version_code = -1
old_version_name = "-1"

def extract_version_code(line):
    if line.__contains__(VARIABLE_NAME_VERSION_CODE):
        line = line.replace(" ","").replace("\n","")
        index = line.find("=")
        version_code = line[index+1:]
        if version_code.isdigit() == True:
             return str(version_code)
    return ""


def extract_version_name(line):
    if line.__contains__(VARIABLE_NAME_VERSION_NAME):
        line = line.replace(" ","").replace("\n","")
        start = line.find("\"")+1
        end = line.find("\"",start)
        version_name = line[start:end]
        if version_name.__contains__("."):
             return str(version_name)
    return ""



def bump(v):
    v = v.replace(" ","")
    segments = v.split(".")
    major = segments[0]
    minor = segments[1]
    patch = segments[2]
    if (int(patch) + 1) >= 10:
        if (int(minor) + 1) >= 10:
            minor = "0"
            patch = "0"
            major = str(int(major)+1)
        else :
            patch = "0"
            minor = str(int(minor)+1)
    else:
         patch = str(int(patch)+1)
    new_version = major+"."+minor+"."+patch
    #print(f"{v} => {new_version}")
    return new_version


with open(FILE_PATH,"r+") as f:
    contents = f.readlines()
    for line in contents:
        evc = extract_version_code(line)
        if evc != "":
            old_version_code = evc
        evn = extract_version_name(line)
        if evn != "":
            old_version_name = evn

    new_version_name = bump(old_version_name)
    new_version_code = int(old_version_code)+1
    print(f"{old_version_name} [{old_version_code}] => {new_version_name} [{new_version_code}]")
   

with open(FILE_PATH,"w") as f:
    for line in contents:
        line = line.replace(str(old_version_code),str(new_version_code))
        line = line.replace(old_version_name,new_version_name)
        f.write(line)

print("Successfully bumped version.")
print("Executing gradle sync...")
int(os.system("gradlew"))
    