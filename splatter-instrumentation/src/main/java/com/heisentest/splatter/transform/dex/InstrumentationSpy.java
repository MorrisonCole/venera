package com.heisentest.splatter.transform.dex;

import com.google.common.collect.Iterables;
import com.heisentest.splatter.instrumentation.point.InstrumentationPoint;
import com.heisentest.splatter.instrumentation.point.JumpInstrumentationPoint;
import com.heisentest.splatter.instrumentation.point.MethodEntryInstrumentationPoint;

import java.util.ArrayList;

public class InstrumentationSpy {

    private final String applicationRootNamespace;
    // TODO: This should not be hardcoded.
    private static final BaseTestCaseClassInfo[] baseTestCaseClassInfos = {
            new BaseTestCaseClassInfo("Lcom/heisentest/skeletonandroidapp/test/acceptance/SkeletonActivityInstrumentationTestCase;", "setUp", "tearDown"),
            new BaseTestCaseClassInfo("Lcom/heisentest/skeletonandroidapp/test/acceptance/SkeletonActivityUnitTestCase;", "setUp", "tearDown")
    };

    public ArrayList<InstrumentationPoint> getInstrumentationPoints() {
        return instrumentationPoints;
    }

    private final ArrayList<InstrumentationPoint> instrumentationPoints = new ArrayList<>();

    public InstrumentationSpy(String applicationRootNamespace) {
        this.applicationRootNamespace = applicationRootNamespace;
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

    public void addInstrumentationPoint(InstrumentationPoint instrumentationPoint) {
        instrumentationPoints.add(instrumentationPoint);
    }

    public MethodEntryInstrumentationPoint getMethodEntryInstrumentationPoint(String className, String methodName) {
        for (MethodEntryInstrumentationPoint methodEntryInstrumentationPoint : getMethodEntryInstrumentationPoints()) {
            if (methodEntryInstrumentationPoint.matches(className, methodName)) {
                return methodEntryInstrumentationPoint;
            }
        }

        return null;
    }

    private Iterable<MethodEntryInstrumentationPoint> getMethodEntryInstrumentationPoints() {
        return Iterables.filter(instrumentationPoints, MethodEntryInstrumentationPoint.class);
    }

    public JumpInstrumentationPoint getJumpInstrumentationPoint(String className, String methodName, int lineNumber) {
        for (JumpInstrumentationPoint jumpInstrumentationPoint : getJumpInstrumentationPoints()) {
            if (jumpInstrumentationPoint.matches(className, methodName, lineNumber)) {
                return jumpInstrumentationPoint;
            }
        }

        return null;
    }

    public Iterable<JumpInstrumentationPoint> getJumpInstrumentationPoints() {
        return Iterables.filter(instrumentationPoints, JumpInstrumentationPoint.class);
    }
}
