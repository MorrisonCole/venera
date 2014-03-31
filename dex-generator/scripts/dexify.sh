#!/bin/bash

# First arg is a path to dexifier //TODO: this should be done in Gradle
# Second arg should be a path to a directory with an .apk

unzip -d $2/debug $2/dex-generator-debug-unaligned.apk > /dev/null
$1 $2/debug/classes.dex
rm -r $2/debug
