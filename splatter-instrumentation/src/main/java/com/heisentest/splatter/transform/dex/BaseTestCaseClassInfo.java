package com.heisentest.splatter.transform.dex;

public class BaseTestCaseClassInfo {

    private String className;
    private String setUpMethodName;
    private String tearDownMethodName;

    public BaseTestCaseClassInfo(String className, String setUpMethodName, String tearDownMethodName) {
        this.className = className;
        this.setUpMethodName = setUpMethodName;
        this.tearDownMethodName = tearDownMethodName;
    }

    public String getClassName() {
        return className;
    }

    public String getSetUpMethodName() {
        return setUpMethodName;
    }

    public String getTearDownMethodName() {
        return tearDownMethodName;
    }
}
