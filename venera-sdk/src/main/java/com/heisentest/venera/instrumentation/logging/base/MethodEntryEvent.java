package com.heisentest.venera.instrumentation.logging.base;

import com.heisentest.venera.instrumentation.logging.LogEvent;

public abstract class MethodEntryEvent extends LogEvent {

    protected String className;
    protected String methodName;

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }
}
