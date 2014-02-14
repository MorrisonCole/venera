package com.heisentest.splatter;

import org.apache.log4j.Logger;
import org.ow2.asmdex.Opcodes;

import java.io.File;

public class Splatter {

    private static final Logger logger = Logger.getLogger(Splatter.class);
    public static final String TEST_APK_LOCATION = "/home/morrison/code/ucl/heisentest/skeleton-android-app/skeleton-android-app/build/apk/skeleton-android-app-debug-unaligned.apk";
    private static final int ASM_API_LEVEL = Opcodes.ASM4;

    public static void main(String args[]) {
        logger.debug("Starting up Splatter!");

        File inputApkFile = new File(TEST_APK_LOCATION);
        File outputApkFile = new File(inputApkFile.getAbsolutePath().replaceFirst("\\.apk", ".splatter.apk"));

        SplatterApkProcessor splatterApkProcessor = new SplatterApkProcessor(ASM_API_LEVEL);
        splatterApkProcessor.process(inputApkFile, outputApkFile);
    }
}
