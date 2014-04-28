package com.heisentest.venera.instrumentation.point;

import static com.heisentest.venera.sdk.Venera.InstrumentationPolicy;

public class JumpInstrumentationPoint implements InstrumentationPoint {

    private String className;
    private String methodName;
    private InstrumentationPolicy instrumentationPolicy;
    private int lineNumber;

    private JumpInstrumentationPoint(Builder builder) {
        this.className = builder.className;
        this.methodName = builder.methodName;
        this.instrumentationPolicy = builder.instrumentationPolicy;
        this.lineNumber = builder.lineNumber;
    }

    public boolean matches(String className, String methodName, int lineNumber) {
        return this.className.equals(className) && this.methodName.equals(methodName) && this.lineNumber == lineNumber;
    }

    @Override
    public InstrumentationPolicy getInstrumentationPolicy() {
        return instrumentationPolicy;
    }

    public static class Builder {
        private String className;
        private String methodName;
        private InstrumentationPolicy instrumentationPolicy;
        private int lineNumber;

        public static Builder jumpInstrumentationPoint() {
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

        public Builder withLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
            return this;
        }

        public JumpInstrumentationPoint build() {
            return new JumpInstrumentationPoint(this);
        }
    }
}
