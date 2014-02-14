package com.heisentest.splatter;

import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;

public class SplatterApplicationVisitor extends ApplicationVisitor {

    private final int asmApiLevel;
    private final AnnotationRulesManager annotationRulesManager;
    private final LogClassWriter logClassWriter;

    public SplatterApplicationVisitor(int asmApiLevel, ApplicationVisitor applicationVisitor, AnnotationRulesManager annotationRulesManager) {
        super(asmApiLevel, applicationVisitor);
        this.asmApiLevel = asmApiLevel;
        this.annotationRulesManager = annotationRulesManager;
        logClassWriter = new LogClassWriter();
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String [] signature, String superName, String [] interfaces) {
        ClassVisitor classVisitor = av.visitClass(access, name, signature, superName, interfaces);
        return new SplatterClassVisitor(asmApiLevel, classVisitor, annotationRulesManager);
    }

    @Override
    public void visitEnd() {
//        logClassWriter.addLogClass(applicationVisitor);
        av.visitEnd();
    }
}
