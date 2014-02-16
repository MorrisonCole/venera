package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.AnnotationRulesManager;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;

public class SplatterClassVisitor extends ClassVisitor {

    private final AnnotationRulesManager annotationRulesManager;
    private final Logger logger = Logger.getLogger(SplatterLoggingClassVisitor.class);

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
