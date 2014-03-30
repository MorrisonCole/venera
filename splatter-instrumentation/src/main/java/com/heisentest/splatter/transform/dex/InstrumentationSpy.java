package com.heisentest.splatter.transform.dex;

public class InstrumentationSpy {

    private final String applicationRootNamespace;
    private int availableInstrumentationPoints = 0;

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
}
