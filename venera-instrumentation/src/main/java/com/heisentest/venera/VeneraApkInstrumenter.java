package com.heisentest.venera;

import com.heisentest.venera.transform.dex.BaseTestCaseClassInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class VeneraApkInstrumenter {

    private final String applicationApkPath;
    private final String testApplicationApkPath;
    private final String applicationNamespace;
    private final String testApplicationNamespace;
    private final int asmApiLevel;
    private final ArrayList<BaseTestCaseClassInfo> baseTestCaseClassInfos;

    public VeneraApkInstrumenter(String applicationApkPath, String testApplicationApkPath,
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
        File outputApkFile = new File(inputApkFile.getAbsolutePath().replaceFirst("\\.apk", ".venera.apk"));

        outputApkFile.createNewFile();

        VeneraApkProcessor veneraApkProcessor = new VeneraApkProcessor(asmApiLevel, appNamespace, baseTestCaseClassInfos);
        veneraApkProcessor.process(inputApkFile, outputApkFile);

        inputApkFile.delete();
        outputApkFile.renameTo(new File(apkLocation));
    }
}
