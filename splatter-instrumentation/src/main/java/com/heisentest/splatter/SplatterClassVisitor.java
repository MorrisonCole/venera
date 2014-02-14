package com.heisentest.splatter;

import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;

public class SplatterClassVisitor extends ClassVisitor {

    private final int asmApiLevel;
    private final AnnotationRulesManager annotationRulesManager;

    public SplatterClassVisitor(int asmApiLevel, ClassVisitor classVisitor, AnnotationRulesManager annotationRulesManager) {
        super(asmApiLevel, classVisitor);
        this.asmApiLevel = asmApiLevel;
        this.annotationRulesManager = annotationRulesManager;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature,
                                     String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new SplatterMethodVisitor(asmApiLevel, methodVisitor, annotationRulesManager);
    }
}
