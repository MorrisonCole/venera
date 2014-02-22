#!/bin/bash

outputDir=$(adb logcat -d | grep --line-buffered -i "Heisentest output directory: " | tail -1 | sed -n -e 's/^.*Heisentest output directory: //p')

echo "DETECTED OUTPUT DIR: $outputDir"

# When an Android device is running an app, it can use emulated storage. After it has been
# uninstalled, we need to correct the path to the real location. This might not work in every case!
badPart="emulated/0"
goodPart="sdcard0"

# Bash string replacement seems to keep a newline at the end, so we remove it.
correctedOutputDir=$(echo ${outputDir/$badPart/$goodPart} | tr -d '\r')

echo "Pulling results from '$correctedOutputDir' to '$1'"

adb pull $correctedOutputDir $1