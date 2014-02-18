package com.heisentest.splatter;

import org.apache.log4j.Logger;
import org.ow2.asmdex.Opcodes;

import java.io.File;
import java.io.IOException;

public class Splatter {

    private static final Logger logger = Logger.getLogger(Splatter.class);
    public static final String TEST_APK_LOCATION_SKELETON = "/home/morrison/code/ucl/heisentest/splatter/skeleton-android-app/build/apk/skeleton-android-app-debug-unaligned.apk";
    public static final String HEISENTEST_SKELETON_APP_NAMESPACE = "Lcom/heisentest/skeletonandroidapp/";
    public static final String TEST_APK_LOCATION = "/home/morrison//code/shazam/android/android_shazam/shazam-android/target/shazam-android-free-debug.apk";
    public static final String SHAZAM_ANDROID_NAMESPACE = "Lcom/shazam/android/";
    private static final int ASM_API_LEVEL = Opcodes.ASM4;

    public static void main(String args[]) {
        logger.debug("Starting up Splatter!");

        File inputApkFile = new File(TEST_APK_LOCATION_SKELETON);
        File outputApkFile = new File(inputApkFile.getAbsolutePath().replaceFirst("\\.apk", ".splatter.apk"));

        try {
            outputApkFile.createNewFile();
        } catch (IOException e) {
            logger.error(e);
        }

        SplatterApkProcessor splatterApkProcessor = new SplatterApkProcessor(ASM_API_LEVEL, HEISENTEST_SKELETON_APP_NAMESPACE);
        splatterApkProcessor.process(inputApkFile, outputApkFile);
    }
}
