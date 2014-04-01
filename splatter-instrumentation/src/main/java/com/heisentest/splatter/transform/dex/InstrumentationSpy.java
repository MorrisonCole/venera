package com.heisentest.splatter.transform.dex;

import com.google.common.collect.ArrayListMultimap;

public class InstrumentationSpy {

    private final String applicationRootNamespace;
    private int availableInstrumentationPoints = 0;
    private final ArrayListMultimap<String, String> instrumentableMethods = ArrayListMultimap.create();
    private static final BaseTestCaseClassInfo[] baseTestCaseClassInfos = {
            new BaseTestCaseClassInfo("Lcom/heisentest/skeletonandroidapp/test/acceptance/SkeletonActivityInstrumentationTestCase;", "setUp", "tearDown"),
            new BaseTestCaseClassInfo("Lcom/heisentest/skeletonandroidapp/test/acceptance/SkeletonActivityUnitTestCase;", "setUp", "tearDown")
    };

    public InstrumentationSpy(String applicationRootNamespace) {
        this.applicationRootNamespace = applicationRootNamespace;
    }

    public int getAvailableInstrumentationPoints() {
        return availableInstrumentationPoints;
    }

    public void setAvailableInstrumentationPoints(int availableInstrumentationPoints) {
        this.availableInstrumentationPoints = availableInstrumentationPoints;
    }

    public boolean shouldClassBeInstrumented(String name) {
        return name.startsWith(applicationRootNamespace);
    }

    // TODO: should not be hardcoded!
    public boolean isApplicationApk() {
        return !applicationRootNamespace.contains("/test");
    }

    public boolean isBaseTestCaseTearDownMethod(String className, String name) {
        for (BaseTestCaseClassInfo baseTestCaseClassInfo : baseTestCaseClassInfos) {
            if (baseTestCaseClassInfo.getClassName().equals(className)
                    && baseTestCaseClassInfo.getTearDownMethodName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public boolean isBaseTestCaseSetUpMethod(String className, String name) {
        for (BaseTestCaseClassInfo baseTestCaseClassInfo : baseTestCaseClassInfos) {
            if (baseTestCaseClassInfo.getClassName().equals(className)
                    && baseTestCaseClassInfo.getSetUpMethodName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public boolean isBaseTestClassInitMethod(String className, String name) {
        return isBaseTestCaseClass(className) && name.equals("<init>");
    }

    public boolean isBaseTestCaseClass(String className) {
        for (BaseTestCaseClassInfo baseTestCaseClassInfo : baseTestCaseClassInfos) {
            if (baseTestCaseClassInfo.getClassName().equals(className)) {
                return true;
            }
        }

        return false;
    }

    public void addInstrumentableMethod(String className, String methodName) {
        instrumentableMethods.put(className, methodName);
    }

    public boolean shouldInstrument(String className, String methodName) {
        return instrumentableMethods.containsEntry(className, methodName);
    }
}
