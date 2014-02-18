#!/bin/bash

# First arg should be a path to a directory with a .apk

unzip -d $1/debug $1/android-debug-unaligned.apk > /dev/null
dexifier $1/debug/classes.dex
rm -r $1/debug
