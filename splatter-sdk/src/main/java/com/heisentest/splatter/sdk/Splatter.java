package com.heisentest.splatter.sdk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Splatter {

    public enum InstrumentationPolicy {
        NONE,
        SIMPLE,
        COMPLEX
    }

    InstrumentationPolicy instrumentationPolicy() default InstrumentationPolicy.COMPLEX;
}
