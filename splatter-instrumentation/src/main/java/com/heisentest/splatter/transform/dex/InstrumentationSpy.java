package com.heisentest.splatter.transform.dex;

import com.google.common.collect.ArrayListMultimap;

public class InstrumentationSpy {

    private final String applicationRootNamespace;
    private int availableInstrumentationPoints = 0;
    private final ArrayListMultimap<String, String> instrumentableMethods = ArrayListMultimap.create();

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

    // TODO: should not be hardcoded!
    public boolean isBaseTestCaseTearDownMethod(String className, String name) {
        return isBaseTestCaseClass(className) && name.equals("tearDown");
    }

    // TODO: should not be hardcoded!
    public boolean isBaseTestCaseSetUpMethod(String className, String name) {
        return isBaseTestCaseClass(className) && name.equals("setUp");
    }

    // TODO: should not be hardcoded!
    public boolean isBaseTestCaseClass(String className) {
        return className.contains("SkeletonInstrumentationTestCase");
    }

    public void addInstrumentableMethod(String className, String methodName) {
        instrumentableMethods.put(className, methodName);
    }

    public boolean shouldInstrument(String className, String methodName) {
        return instrumentableMethods.containsEntry(className, methodName);
    }
}
