package com.heisentest.splatter.instrumentation.point;

import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy;

public interface InstrumentationPoint {

    boolean matches(String className, String methodName);

    InstrumentationPolicy getInstrumentationPolicy();
}
