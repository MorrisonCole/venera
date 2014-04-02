package com.heisentest.skeletonandroidapp.logging;

public abstract class MethodEntryEvent extends LogEvent {

    protected String className;
    protected String methodName;
    protected Object[] parameters;

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }
}
