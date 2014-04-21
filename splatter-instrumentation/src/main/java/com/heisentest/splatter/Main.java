package com.heisentest.splatter;

import com.google.common.base.Stopwatch;
import com.heisentest.splatter.transform.dex.BaseTestCaseClassInfo;
import com.heisentest.splatter.utility.DalvikTypeDescriptor;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;
import org.ow2.asmdex.Opcodes;

import java.io.IOException;
import java.util.ArrayList;

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
    private static final String BASE_TEST_CASE_INFO_ARGUMENT = "baseTestCaseInfo";
    private static String applicationApkPath;
    private static String applicationTestApkPath;
    private static final CommandLineParser commandLineParser = new DefaultParser();
    private static final Options options = new Options();
    private static final ArrayList<BaseTestCaseClassInfo> baseTestCaseClassInfos = new ArrayList<>();

    public static void main(String arguments[]) {
        Stopwatch overallStopwatch = Stopwatch.createStarted();
        logger.debug("Starting up Splatter!");

        addDefaultOptions();

        try {
            final CommandLine commandLine = commandLineParser.parse(options, arguments, STOP_AT_NON_OPTION);

            applicationApkPath = commandLine.getOptionValue(APPLICATION_APK_ARGUMENT);
            applicationTestApkPath = commandLine.getOptionValue(TEST_APK_ARGUMENT);

            final String[] baseTestCaseInfoArguments = commandLine.getOptionValues(BASE_TEST_CASE_INFO_ARGUMENT);
            if ((baseTestCaseInfoArguments.length % 3) != 0) {
                throw new ParseException(BASE_TEST_CASE_INFO_ARGUMENT + " must be provided three arguments. If " +
                        "multiple base test classes are given, ensure each has a SETUP and TEARDOWN argument as well.");
            }

            for (int i = 0; i < baseTestCaseInfoArguments.length; i += 3) {
                final BaseTestCaseClassInfo baseTestCaseClassInfo = new BaseTestCaseClassInfo(
                        DalvikTypeDescriptor.typeDescriptorForClassWithName(baseTestCaseInfoArguments[i]),
                        baseTestCaseInfoArguments[i + 1],
                        baseTestCaseInfoArguments[i + 2]);

                baseTestCaseClassInfos.add(baseTestCaseClassInfo);
            }
        } catch (ParseException e) {
            logger.fatal("Failed to parse command line arguments.", e);

            printUsage();

            System.exit(ERROR_STATUS);
        }

        final SplatterApkInstrumenter splatterApkInstrumenter = new SplatterApkInstrumenter(applicationApkPath,
                applicationTestApkPath,
                HEISENTEST_SKELETON_APP_NAMESPACE,
                HEISENTEST_SKELETON_APP_TEST_NAMESPACE,
                ASM_API_LEVEL,
                baseTestCaseClassInfos);
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

        final Option baseTestCaseInfo = Option.builder()
                .longOpt(BASE_TEST_CASE_INFO_ARGUMENT)
                .desc("The full name of your base test class followed by the short names of the set up and tear down" +
                        " methods with comma separation. E.g., com.application.MyBaseTestCase,setUp,tearDown")
                .required()
                .hasArgs()
                .valueSeparator(',')
                .argName("FULL BASE TEST CASE NAME> <BASIC SETUP METHOD NAME> <BASIC TEARDOWN METHOD NAME")
                .build();

        options.addOption(applicationApkOption);
        options.addOption(testApkOption);
        options.addOption(baseTestCaseInfo);
    }

    private static void printUsage() {
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Splatter", options, PRINT_AUTO_USAGE);
    }
}
