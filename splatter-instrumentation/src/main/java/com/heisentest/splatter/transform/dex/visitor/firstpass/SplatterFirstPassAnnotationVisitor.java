package com.heisentest.splatter.transform.dex.visitor.firstpass;

import com.heisentest.splatter.instrumentation.point.MethodEntryInstrumentationPoint;
import com.heisentest.splatter.sdk.Splatter;
import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import com.heisentest.splatter.utility.DalvikTypeDescriptor;
import org.ow2.asmdex.AnnotationVisitor;

import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy;
import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy.NONE;
import static com.heisentest.splatter.utility.DalvikTypeDescriptor.typeDescriptorForClass;

public class SplatterFirstPassAnnotationVisitor extends AnnotationVisitor {

    private final MethodEntryInstrumentationPoint.Builder methodEntryInstrumentationPoint;

    public SplatterFirstPassAnnotationVisitor(int api, MethodEntryInstrumentationPoint.Builder methodEntryInstrumentationPoint) {
        super(api);
        this.methodEntryInstrumentationPoint = methodEntryInstrumentationPoint;
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        if (desc.equals(typeDescriptorForClass(InstrumentationPolicy.class))) {
            methodEntryInstrumentationPoint.withInstrumentationPolicy(InstrumentationPolicy.valueOf(value));
        }
        super.visitEnum(name, desc, value);
    }
}
