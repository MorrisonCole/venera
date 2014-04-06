package com.heisentest.splatter;

import com.heisentest.splatter.controlflow.SplatterControlFlowAnalyzer;

import java.io.File;
import java.io.IOException;

public class SplatterApkInstrumenter {

    private final SplatterControlFlowAnalyzer splatterControlFlowAnalyzer;
    private final String applicationApkPath;
    private final String testApplicationApkPath;
    private final String applicationNamespace;
    private final String testApplicationNamespace;
    private final int asmApiLevel;

    public SplatterApkInstrumenter(SplatterControlFlowAnalyzer splatterControlFlowAnalyzer, String applicationApkPath, String testApplicationApkPath, String applicationNamespace, String testApplicationNamespace, int asmApiLevel) {
        this.splatterControlFlowAnalyzer = splatterControlFlowAnalyzer;
        this.applicationApkPath = applicationApkPath;
        this.testApplicationApkPath = testApplicationApkPath;
        this.applicationNamespace = applicationNamespace;
        this.testApplicationNamespace = testApplicationNamespace;
        this.asmApiLevel = asmApiLevel;
    }

    public void instrumentApks() throws IOException {
        instrumentApk(applicationApkPath, applicationNamespace);
        instrumentApk(testApplicationApkPath, testApplicationNamespace);
    }

    private void instrumentApk(String apkLocation, String appNamespace) throws IOException {
        File inputApkFile = new File(apkLocation);
        File outputApkFile = new File(inputApkFile.getAbsolutePath().replaceFirst("\\.apk", ".splatter.apk"));

        outputApkFile.createNewFile();

        SplatterApkProcessor splatterApkProcessor = new SplatterApkProcessor(asmApiLevel, appNamespace, splatterControlFlowAnalyzer);
        splatterApkProcessor.process(inputApkFile, outputApkFile);

        inputApkFile.delete();
        outputApkFile.renameTo(new File(apkLocation));
    }
}
