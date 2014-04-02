package com.heisentest.skeletonandroidapp.logging;

import java.io.IOException;

public class InstanceMethodEntryEvent extends MethodEntryEvent {

    private Object callee;
    private String[] parameterNames;

    private InstanceMethodEntryEvent(Builder builder) {
        this.callee = builder.callee;
        this.parameterNames = builder.parameterNames;
        this.className = builder.className;
        this.methodName = builder.methodName;
        this.parameters = builder.parameters;
        this.eventName = builder.eventName;
        this.eventTime = builder.eventTime;
        this.eventThreadId = builder.eventThreadId;
    }

    public Object getCallee() {
        return callee;
    }

    public String[] getParameterNames() {
        return parameterNames;
    }

    @Override
    public void write(LogEventWriter logEventWriter) throws IOException {
        logEventWriter.write(this);
    }

    public static class Builder {
        private Object callee;
        private String[] parameterNames;
        private String className;
        private String methodName;
        private Object[] parameters;
        private String eventName;
        private long eventTime;
        private long eventThreadId;

        public static Builder instanceMethodEntryEvent() {
            return new Builder();
        }

        public Builder withCallee(Object callee) {
            this.callee = callee;
            return this;
        }

        public Builder withParameterNames(String[] parameterNames) {
            this.parameterNames = parameterNames;
            return this;
        }

        public Builder withClassName(String className) {
            this.className = className;
            return this;
        }

        public Builder withMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder withParameters(Object[] parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder withEventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public Builder withEventTime(long eventTime) {
            this.eventTime = eventTime;
            return this;
        }

        public Builder withEventThreadId(long eventThreadId) {
            this.eventThreadId = eventThreadId;
            return this;
        }

        public InstanceMethodEntryEvent build() {
            return new InstanceMethodEntryEvent(this);
        }
    }
}
