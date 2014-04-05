package com.heisentest.instrumentation.logging.simple;

import com.heisentest.instrumentation.logging.LogEventWriter;
import com.heisentest.instrumentation.logging.base.MethodEntryEvent;

import java.io.IOException;

public class SimpleInstanceMethodEntryEvent extends MethodEntryEvent {

    private SimpleInstanceMethodEntryEvent(Builder builder) {
        this.className = builder.className;
        this.methodName = builder.methodName;
        this.eventName = builder.eventName;
        this.eventTime = builder.eventTime;
        this.eventThreadId = builder.eventThreadId;
    }

    @Override
    public void write(LogEventWriter logEventWriter) throws IOException {
        logEventWriter.write(this);
    }

    public static class Builder {
        private String className;
        private String methodName;
        private String eventName;
        private long eventTime;
        private long eventThreadId;

        public static Builder simpleInstanceMethodEntryEvent() {
            return new Builder();
        }

        public Builder withClassName(String className) {
            this.className = className;
            return this;
        }

        public Builder withMethodName(String methodName) {
            this.methodName = methodName;
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

        public SimpleInstanceMethodEntryEvent build() {
            return new SimpleInstanceMethodEntryEvent(this);
        }
    }
}
