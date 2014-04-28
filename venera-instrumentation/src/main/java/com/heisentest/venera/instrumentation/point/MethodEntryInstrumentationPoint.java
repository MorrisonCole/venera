package com.heisentest.venera.instrumentation.point;

import static com.heisentest.venera.sdk.Venera.InstrumentationPolicy;

public class MethodEntryInstrumentationPoint implements InstrumentationPoint {

    private String className;
    private String methodName;
    private InstrumentationPolicy instrumentationPolicy;

    private MethodEntryInstrumentationPoint(Builder builder) {
        this.className = builder.className;
        this.methodName = builder.methodName;
        this.instrumentationPolicy = builder.instrumentationPolicy;
    }

    public boolean matches(String className, String methodName) {
        return this.className.equals(className) && this.methodName.equals(methodName);
    }

    @Override
    public InstrumentationPolicy getInstrumentationPolicy() {
        return instrumentationPolicy;
    }

    public static class Builder {
        private String className;
        private String methodName;
        private InstrumentationPolicy instrumentationPolicy;

        public static Builder methodEntryInstrumentationPoint() {
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

        public Builder withInstrumentationPolicy(InstrumentationPolicy instrumentationPolicy) {
            this.instrumentationPolicy = instrumentationPolicy;
            return this;
        }

        public MethodEntryInstrumentationPoint build() {
            return new MethodEntryInstrumentationPoint(this);
        }
    }
}
