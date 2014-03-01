package com.heisentest.skeletonandroidapp.logging;

public class LogEvent {
    private String calleeClassName = "";
    private final String calleeMethodName;
    private final String[] parameterNames;
    private final Object callee;
    private final Object[] parameters;

    public LogEvent(String calleeMethodName, String[] parameterNames, Object callee, Object[] parameters) {
        this.calleeMethodName = calleeMethodName;
        this.parameterNames = parameterNames;
        this.callee = callee;
        this.parameters = parameters;
    }

    public LogEvent(String calleeClassName, String calleeMethodName, Object[] parameters) {
        this(calleeMethodName, null, null, parameters);
        this.calleeClassName = calleeClassName;
    }

    public String getCalleeClassName() {
        if (callee != null) {
            return callee.getClass().toString();
        } else {
            return calleeClassName;
        }
    }

    public String getCalleeMethodName() {
        return calleeMethodName;
    }

    public String[] getParameterNames() {
        return parameterNames;
    }

    public Object getCallee() {
        return callee;
    }

    public Object[] getParameters() {
        return parameters;
    }
}
