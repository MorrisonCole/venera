package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.AnnotationRulesManager;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterMethodVisitor extends MethodVisitor {

    private final AnnotationRulesManager annotationRulesManager;
    private final Logger logger = Logger.getLogger(SplatterMethodVisitor.class);

    public SplatterMethodVisitor(int api, MethodVisitor methodVisitor, AnnotationRulesManager annotationRulesManager) {
        super(api, methodVisitor);
        this.annotationRulesManager = annotationRulesManager;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, int[] arguments) {
        mv.visitMethodInsn(opcode, owner, name, desc, arguments);
    }
}
