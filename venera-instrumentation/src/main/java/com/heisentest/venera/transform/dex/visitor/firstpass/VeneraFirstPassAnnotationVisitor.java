package com.heisentest.venera.transform.dex.visitor.firstpass;

import com.heisentest.venera.instrumentation.point.MethodEntryInstrumentationPoint;
import org.ow2.asmdex.AnnotationVisitor;

import static com.heisentest.venera.sdk.Venera.InstrumentationPolicy;
import static com.heisentest.venera.utility.DalvikTypeDescriptor.typeDescriptorForClass;

public class VeneraFirstPassAnnotationVisitor extends AnnotationVisitor {

    private final MethodEntryInstrumentationPoint.Builder methodEntryInstrumentationPoint;

    public VeneraFirstPassAnnotationVisitor(int api, MethodEntryInstrumentationPoint.Builder methodEntryInstrumentationPoint) {
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
