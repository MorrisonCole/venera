package com.heisentest.splatter.controlflow;

import soot.*;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;

import java.util.Arrays;
import java.util.Iterator;

public class SplatterControlFlowAnalyzer {

    private final String applicationApkPath;
    private final String applicationTestApkPath;
    private final String androidJars;

    public SplatterControlFlowAnalyzer(String applicationApkPath, String applicationTestApkPath, String androidJars) {
        this.applicationApkPath = applicationApkPath;
        this.applicationTestApkPath = applicationTestApkPath;
        this.androidJars = androidJars;
    }

    public void loadApks() {
        setSootOptions(applicationApkPath, applicationTestApkPath);

        Scene.v().loadNecessaryClasses();
    }

    private void setSootOptions(String applicationApkPath, String applicationTestApkPath) {
        // Prefer APKs over JARs
        Options.v().set_src_prec(Options.src_prec_apk);

        // Folder containing Android JARs for all API levels.
        // Note: if we use this with multiple APKs on the Soot classpath, it fails to
        // detect the API level and exits. Perhaps submit a pull request to choose a
        // default and print a warning, or just determine the API level here.
        // Options.v().set_android_jars(androidJars);

        // TODO: Should detect the API level by parsing the manifest of the main application APK.
        Options.v().set_force_android_jar(String.format("%s/android-19/android.jar", androidJars));

        // Not really necessary since we're not outputting anything at the moment
        Options.v().set_output_format(Options.output_format_dex);

        // Soot uses its own classpath, so we add our APKs to it
        Options.v().set_prepend_classpath(true);
        Options.v().set_soot_classpath(String.format("%s:%s", applicationTestApkPath, applicationApkPath));

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

    /**
     * This should calculate the next places of interest to instrument given a method.
      */
    public void calculateControlFlowGraph(String methodName) {
        final Chain<SootClass> applicationClasses = Scene.v().getApplicationClasses();
        for (SootClass sootClass : applicationClasses) {
            // TODO: Should not be hardcoded.
            if (sootClass.getName().startsWith("com.heisentest")) {
                runFlowAnalysis(sootClass);
            }
        }
    }

    private void runFlowAnalysis(SootClass sClass) {
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
}
