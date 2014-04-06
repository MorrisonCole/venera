package com.heisentest.splatter;

import com.heisentest.splatter.controlflow.SplatterControlFlowAnalyzer;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import org.ow2.asmdex.Opcodes;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    // TODO: These namespaces should not be hardcoded.
    public static final String HEISENTEST_SKELETON_APP_NAMESPACE = "Lcom/heisentest/skeletonandroidapp/";
    public static final String HEISENTEST_SKELETON_APP_TEST_NAMESPACE = "Lcom/heisentest/skeletonandroidapp/test/";

    private static final int ASM_API_LEVEL = Opcodes.ASM4;
    public static final int ERROR_STATUS = 1;
    public static final boolean STOP_AT_NON_OPTION = true;
    public static final boolean PRINT_AUTO_USAGE = true;
    private static String applicationApkPath;
    private static String applicationTestApkPath;
    private static String androidJars;
    private static final CommandLineParser commandLineParser = new DefaultParser();
    private static final Options options = new Options();

    public static void main(String arguments[]) {
        logger.debug("Starting up Splatter!");

        addDefaultOptions();

        try {
            final CommandLine commandLine = commandLineParser.parse(options, arguments, STOP_AT_NON_OPTION);

            applicationApkPath = commandLine.getOptionValue("applicationApk");
            applicationTestApkPath = commandLine.getOptionValue("testApk");
            androidJars = commandLine.getOptionValue("androidPlatforms");
        } catch (ParseException e) {
            logger.fatal("Failed to parse command line arguments.", e);

            final HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("Splatter", options, PRINT_AUTO_USAGE);

            System.exit(ERROR_STATUS);
        }

        final SplatterControlFlowAnalyzer splatterControlFlowAnalyzer = new SplatterControlFlowAnalyzer(applicationApkPath, applicationTestApkPath, androidJars);
        splatterControlFlowAnalyzer.loadApks();

        instrumentApk(applicationApkPath, HEISENTEST_SKELETON_APP_NAMESPACE);
        instrumentApk(applicationTestApkPath, HEISENTEST_SKELETON_APP_TEST_NAMESPACE);
    }

    private static void addDefaultOptions() {
        final Option applicationApkOption = Option.builder()
                .longOpt("applicationApk")
                .desc("The fully qualified path to the Application APK")
                .required()
                .hasArg()
                .argName("PATH")
                .build();

        final Option testApkOption = Option.builder()
                .longOpt("testApk")
                .desc("The fully qualified path to the Application Test APK")
                .required()
                .hasArg()
                .argName("PATH")
                .build();

        final Option androidPlatformsOption = Option.builder()
                .longOpt("androidPlatforms")
                .desc("The fully qualified path to your Android SDK/platforms/ folder")
                .required()
                .hasArg()
                .argName("PATH")
                .build();

        options.addOption(applicationApkOption);
        options.addOption(testApkOption);
        options.addOption(androidPlatformsOption);
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
