package com.heisentest.splatter;

import com.heisentest.splatter.transform.dex.BaseTestCaseClassInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SplatterApkInstrumenter {

    private final String applicationApkPath;
    private final String testApplicationApkPath;
    private final String applicationNamespace;
    private final String testApplicationNamespace;
    private final int asmApiLevel;
    private final ArrayList<BaseTestCaseClassInfo> baseTestCaseClassInfos;

    public SplatterApkInstrumenter(String applicationApkPath, String testApplicationApkPath,
                                   String applicationNamespace, String testApplicationNamespace,
                                   int asmApiLevel, ArrayList<BaseTestCaseClassInfo> baseTestCaseClassInfos) {
        this.applicationApkPath = applicationApkPath;
        this.testApplicationApkPath = testApplicationApkPath;
        this.applicationNamespace = applicationNamespace;
        this.testApplicationNamespace = testApplicationNamespace;
        this.asmApiLevel = asmApiLevel;
        this.baseTestCaseClassInfos = baseTestCaseClassInfos;
    }

    public void instrumentApks() throws IOException {
        instrumentApk(applicationApkPath, applicationNamespace);
        instrumentApk(testApplicationApkPath, testApplicationNamespace);
    }

    private void instrumentApk(String apkLocation, String appNamespace) throws IOException {
        File inputApkFile = new File(apkLocation);
        File outputApkFile = new File(inputApkFile.getAbsolutePath().replaceFirst("\\.apk", ".splatter.apk"));

        outputApkFile.createNewFile();

        SplatterApkProcessor splatterApkProcessor = new SplatterApkProcessor(asmApiLevel, appNamespace, baseTestCaseClassInfos);
        splatterApkProcessor.process(inputApkFile, outputApkFile);

        inputApkFile.delete();
        outputApkFile.renameTo(new File(apkLocation));
    }
}
