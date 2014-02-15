package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.AnnotationRulesManager;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.INSN_CONST_STRING;
import static org.ow2.asmdex.Opcodes.INSN_INVOKE_STATIC;

public class SplatterMethodVisitor extends MethodVisitor {

    private final AnnotationRulesManager annotationRulesManager;
    private final Logger logger = Logger.getLogger(SplatterMethodVisitor.class);

    public SplatterMethodVisitor(int api, MethodVisitor methodVisitor, AnnotationRulesManager annotationRulesManager) {
        super(api, methodVisitor);
        this.annotationRulesManager = annotationRulesManager;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, int[] arguments) {
        String logItName = annotationRulesManager.log(owner, name, desc);
        if (logItName != null) {
            logger.debug(String.format("Adding Splatter logger to method '%s' (owner: '%s')", name, owner));

            mv.visitStringInsn(INSN_CONST_STRING, 0, name);
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", "log", "VLjava/lang/String;", new int[]{0});

//            mv.visitStringInsn(INSN_CONST_STRING, 0, "A tag");
//            mv.visitStringInsn(INSN_CONST_STRING, 1, "A message");
//            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 0, 1 });

        }
        mv.visitMethodInsn(opcode, owner, name, desc, arguments);
    }
}
