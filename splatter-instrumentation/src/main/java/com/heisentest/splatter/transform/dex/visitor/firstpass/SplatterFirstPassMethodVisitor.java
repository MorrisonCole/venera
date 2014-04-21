package com.heisentest.splatter.transform.dex.visitor.firstpass;

import com.heisentest.splatter.instrumentation.point.JumpInstrumentationPoint;
import com.heisentest.splatter.instrumentation.point.MethodEntryInstrumentationPoint;
import com.heisentest.splatter.sdk.Splatter;
import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import org.apache.log4j.Logger;
import org.ow2.asmdex.AnnotationVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureCommon.Label;

import static com.heisentest.splatter.instrumentation.point.JumpInstrumentationPoint.Builder.jumpInstrumentationPoint;
import static com.heisentest.splatter.instrumentation.point.MethodEntryInstrumentationPoint.Builder.methodEntryInstrumentationPoint;
import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy.COMPLEX;
import static com.heisentest.splatter.utility.DalvikTypeDescriptor.typeDescriptorForClass;

class SplatterFirstPassMethodVisitor extends MethodVisitor {

    private static final Logger logger = Logger.getLogger(SplatterFirstPassMethodVisitor.class);
    private final String className;
    private final String methodName;
    private final InstrumentationSpy instrumentationSpy;
    private final MethodEntryInstrumentationPoint.Builder methodEntryInstrumentationPoint;

    public SplatterFirstPassMethodVisitor(int api, String className, String methodName, InstrumentationSpy instrumentationSpy) {
        super(api);
        this.className = className;
        this.methodName = methodName;
        this.instrumentationSpy = instrumentationSpy;

        methodEntryInstrumentationPoint = methodEntryInstrumentationPoint()
                .withClassName(className)
                .withMethodName(methodName)
                .withInstrumentationPolicy(COMPLEX);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label, int registerA, int registerB) {
        final JumpInstrumentationPoint jumpInstrumentationPoint = jumpInstrumentationPoint()
                .withClassName(className)
                .withMethodName(methodName)
                .withLineNumber(label.getLine())
                .withInstrumentationPolicy(COMPLEX)
                .build();

        instrumentationSpy.addInstrumentationPoint(jumpInstrumentationPoint);

        super.visitJumpInsn(opcode, label, registerA, registerB);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (desc.equals(typeDescriptorForClass(Splatter.class))) {
            return new SplatterFirstPassAnnotationVisitor(api, methodEntryInstrumentationPoint);
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitEnd() {
        instrumentationSpy.addInstrumentationPoint(methodEntryInstrumentationPoint.build());

        super.visitEnd();
    }
}
