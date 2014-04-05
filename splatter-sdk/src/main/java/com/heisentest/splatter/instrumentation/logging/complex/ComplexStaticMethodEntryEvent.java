package com.heisentest.splatter.instrumentation.logging.complex;

import com.heisentest.splatter.instrumentation.logging.LogEventWriter;
import com.heisentest.splatter.instrumentation.logging.base.MethodEntryEvent;

public class ComplexStaticMethodEntryEvent extends MethodEntryEvent {

    private Object[] parameters;

    private ComplexStaticMethodEntryEvent(Builder builder) {
        this.eventName = builder.eventName;
        this.eventTime = builder.eventTime;
        this.eventThreadId = builder.eventThreadId;
        this.className = builder.className;
        this.methodName = builder.methodName;
        this.parameters = builder.parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }

    @Override
    public void write(LogEventWriter logEventWriter) {
        logEventWriter.write(this);
    }

    public static class Builder {
        private String eventName;
        private long eventTime;
        private long eventThreadId;
        private String className;
        private String methodName;
        private Object[] parameters;

        public static Builder staticMethodEntryEvent() {
            return new Builder();
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

        public ComplexStaticMethodEntryEvent build() {
            return new ComplexStaticMethodEntryEvent(this);
        }
    }
}
