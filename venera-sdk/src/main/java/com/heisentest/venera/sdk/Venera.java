package com.heisentest.venera.sdk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Venera {

    public enum InstrumentationPolicy {
        NONE,
        SIMPLE,
        COMPLEX
    }

    InstrumentationPolicy instrumentationPolicy() default InstrumentationPolicy.COMPLEX;
}
