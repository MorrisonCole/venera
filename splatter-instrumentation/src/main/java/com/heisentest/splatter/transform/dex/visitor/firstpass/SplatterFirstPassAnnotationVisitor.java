package com.heisentest.splatter.transform.dex.visitor.firstpass;

import com.heisentest.splatter.instrumentation.point.MethodEntryInstrumentationPoint;
import com.heisentest.splatter.sdk.Splatter;
import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import org.ow2.asmdex.AnnotationVisitor;

import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy;
import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy.NONE;

public class SplatterFirstPassAnnotationVisitor extends AnnotationVisitor {

    private final MethodEntryInstrumentationPoint.Builder methodEntryInstrumentationPoint;

    public SplatterFirstPassAnnotationVisitor(int api, MethodEntryInstrumentationPoint.Builder methodEntryInstrumentationPoint) {
        super(api);
        this.methodEntryInstrumentationPoint = methodEntryInstrumentationPoint;
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        if (desc.equals("Lcom/heisentest/splatter/sdk/Splatter$InstrumentationPolicy;")) {
            methodEntryInstrumentationPoint.withInstrumentationPolicy(InstrumentationPolicy.valueOf(value));
        }
        super.visitEnum(name, desc, value);
    }
}
