package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.AnnotationRulesManager;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.ClassWriter;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.MethodWriter;

public class SplatterClassVisitor extends ClassVisitor {

    private final AnnotationRulesManager annotationRulesManager;

    public SplatterClassVisitor(int asmApiLevel, ClassVisitor classVisitor, AnnotationRulesManager annotationRulesManager) {
        super(asmApiLevel, classVisitor);
        this.annotationRulesManager = annotationRulesManager;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new SplatterMethodVisitor(api, methodVisitor, annotationRulesManager);
    }
}
