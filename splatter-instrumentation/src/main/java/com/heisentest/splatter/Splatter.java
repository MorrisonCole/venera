package com.heisentest.splatter;

import org.apache.log4j.Logger;
import org.ow2.asmdex.Opcodes;
import soot.*;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

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

        calculateControlFlowGraph(applicationApkPath, applicationTestApkPath);

        instrumentApk(applicationApkPath, HEISENTEST_SKELETON_APP_NAMESPACE);
        instrumentApk(applicationTestApkPath, HEISENTEST_SKELETON_APP_TEST_NAMESPACE);
    }

    private static void calculateControlFlowGraph(String applicationApkPath, String applicationTestApkPath) {
        setSootOptions(applicationApkPath, applicationTestApkPath);

        Scene.v().loadNecessaryClasses();

//        SootClass sootClass = Scene.v().loadClassAndSupport("com.heisentest.skeletonandroidapp.MainActivity");
//        sootClass.setApplicationClass();

        final Chain<SootClass> applicationClasses = Scene.v().getApplicationClasses();
        for (SootClass sootClass : applicationClasses) {
            // TODO: Should not be hardcoded.
            if (sootClass.getName().startsWith("com.heisentest")) {
                runFlowAnalysis(sootClass);
            }
        }
    }

    private static void setSootOptions(String applicationApkPath, String applicationTestApkPath) {
        // Prefer APKs over JARs
        Options.v().set_src_prec(Options.src_prec_apk);

        // Folder containing Android JARs for all API levels
        Options.v().set_android_jars(androidJars);

        // Not really necessary since we're not outputting anything at the moment
        Options.v().set_output_format(Options.output_format_dex);

        // Soot uses its own classpath, so we add our APKs to it
        Options.v().set_prepend_classpath(true);
        Options.v().set_soot_classpath(String.format("%s", applicationTestApkPath));

        // Verbose output for debugging
        Options.v().set_verbose(false);

        // If a unavailable class is requested, Soot creates a 'phantom' implementation. Not guaranteed to work properly
        Options.v().set_allow_phantom_refs(true);

        // Process the test APK
        Options.v().set_process_dir(Arrays.asList(applicationTestApkPath));

        // TODO: Document
        Options.v().set_whole_program(true);
        Options.v().set_validate(true);
    }

    private static void runFlowAnalysis(SootClass sClass) {
        System.out.println("***************************************");
        System.out.println(sClass.getName());
        System.out.println("***************************************");
        Iterator methodIt = sClass.getMethods().iterator();
        while (methodIt.hasNext()) {
            SootMethod m = (SootMethod)methodIt.next();
            Body b = m.retrieveActiveBody();

            if (!m.getName().startsWith("test")) {
                continue;
            }

            System.out.println("=======================================");
            System.out.println(m.toString());

            UnitGraph graph = new ExceptionalUnitGraph(b);

            Iterator gIt = graph.iterator();
            while (gIt.hasNext()) {
                Unit u = (Unit)gIt.next();

                UnitPrinter up = new NormalUnitPrinter(b);
                up.setIndent("");

                System.out.println("---------------------------------------");
                u.toString(up);
                System.out.println(up.output());
                System.out.println("---------------------------------------");
            }

            System.out.println("=======================================");
        }
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
