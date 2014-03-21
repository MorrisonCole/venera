package com.heisentest.splatter;

import com.heisentest.splatter.controlflow.SplatterControlFlowAnalyzer;
import org.apache.log4j.Logger;
import org.ow2.asmdex.Opcodes;

import java.io.File;
import java.io.IOException;

public class Splatter {

    private static final Logger logger = Logger.getLogger(Splatter.class);

    // TODO: These namespaces should not be hardcoded.
    public static final String HEISENTEST_SKELETON_APP_NAMESPACE = "Lcom/heisentest/skeletonandroidapp/";
    public static final String HEISENTEST_SKELETON_APP_TEST_NAMESPACE = "Lcom/heisentest/skeletonandroidapp/test/";
    public static final String SHAZAM_ANDROID_NAMESPACE = "Lcom/shazam/android/";

    private static final int ASM_API_LEVEL = Opcodes.ASM4;
    private static String applicationApkPath;
    private static String applicationTestApkPath;
    private static String androidJars;

    /**
     * Expected args: path to application apk, path to test apk, path to android jars.
     */
    public static void main(String args[]) {
        logger.debug("Starting up Splatter!");

        if (args.length == 3) {
            applicationApkPath = args[0];
            logger.debug(String.format("Using application APK path '%s'", applicationApkPath));

            applicationTestApkPath = args[1];
            logger.debug(String.format("Using application test APK path '%s'", applicationTestApkPath));

            androidJars = args[2];
            logger.debug(String.format("Using Android JARS path '%s'", androidJars));
        }

        final SplatterControlFlowAnalyzer splatterControlFlowAnalyzer = new SplatterControlFlowAnalyzer(applicationApkPath, applicationTestApkPath, androidJars);
        splatterControlFlowAnalyzer.loadApks();

        instrumentApk(applicationApkPath, HEISENTEST_SKELETON_APP_NAMESPACE);
        instrumentApk(applicationTestApkPath, HEISENTEST_SKELETON_APP_TEST_NAMESPACE);
    }

    private static void instrumentApk(String apkLocation, String appNamespace) {
        File inputApkFile = new File(apkLocation);
        File outputApkFile = new File(inputApkFile.getAbsolutePath().replaceFirst("\\.apk", ".splatter.apk"));

        try {
            outputApkFile.createNewFile();
        } catch (IOException e) {
            logger.error(e);
        }

        SplatterApkProcessor splatterApkProcessor = new SplatterApkProcessor(ASM_API_LEVEL, appNamespace);
        splatterApkProcessor.process(inputApkFile, outputApkFile);

        inputApkFile.delete();
        outputApkFile.renameTo(new File(apkLocation));
    }
}
