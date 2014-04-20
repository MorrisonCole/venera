package com.heisentest.splatter;

import com.google.common.base.Stopwatch;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import org.ow2.asmdex.Opcodes;

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
    private static final String APPLICATION_APK_ARGUMENT = "applicationApk";
    private static final String TEST_APK_ARGUMENT = "testApk";
    private static String applicationApkPath;
    private static String applicationTestApkPath;
    private static final CommandLineParser commandLineParser = new DefaultParser();
    private static final Options options = new Options();

    public static void main(String arguments[]) {
        Stopwatch overallStopwatch = Stopwatch.createStarted();
        logger.debug("Starting up Splatter!");

        addDefaultOptions();

        try {
            final CommandLine commandLine = commandLineParser.parse(options, arguments, STOP_AT_NON_OPTION);

            applicationApkPath = commandLine.getOptionValue(APPLICATION_APK_ARGUMENT);
            applicationTestApkPath = commandLine.getOptionValue(TEST_APK_ARGUMENT);
        } catch (ParseException e) {
            logger.fatal("Failed to parse command line arguments.", e);

            printUsage();

            System.exit(ERROR_STATUS);
        }

        final SplatterApkInstrumenter splatterApkInstrumenter = new SplatterApkInstrumenter(applicationApkPath, applicationTestApkPath, HEISENTEST_SKELETON_APP_NAMESPACE, HEISENTEST_SKELETON_APP_TEST_NAMESPACE, ASM_API_LEVEL);
        try {
            splatterApkInstrumenter.instrumentApks();
            logger.info(String.format("Total Duration: %s", overallStopwatch.stop()));
        } catch (IOException e) {
            logger.error(e);
            System.exit(ERROR_STATUS);
        }
    }

    private static void addDefaultOptions() {
        final Option applicationApkOption = Option.builder()
                .longOpt(APPLICATION_APK_ARGUMENT)
                .desc("The fully qualified path to the Application APK")
                .required()
                .hasArg()
                .argName("PATH")
                .build();

        final Option testApkOption = Option.builder()
                .longOpt(TEST_APK_ARGUMENT)
                .desc("The fully qualified path to the Application Test APK")
                .required()
                .hasArg()
                .argName("PATH")
                .build();

        options.addOption(applicationApkOption);
        options.addOption(testApkOption);
    }

    private static void printUsage() {
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Splatter", options, PRINT_AUTO_USAGE);
    }
}
