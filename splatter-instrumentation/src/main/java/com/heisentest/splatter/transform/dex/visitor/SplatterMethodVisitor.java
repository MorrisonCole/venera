package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.AnnotationRulesManager;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;

public class SplatterMethodVisitor extends MethodVisitor {

    private final AnnotationRulesManager annotationRulesManager;
    private final Logger logger = Logger.getLogger(SplatterMethodVisitor.class);

    public SplatterMethodVisitor(int api, MethodVisitor methodVisitor, AnnotationRulesManager annotationRulesManager) {
        super(api, methodVisitor);
        this.annotationRulesManager = annotationRulesManager;
    }

//    @Override
//    public void visitMethodInsn(int opcode, String owner, String name, String desc, int[] arguments) {
////        boolean isStatic;
////        String signature;
////        switch (opcode) {
////            case Opcodes.INSN_INVOKE_STATIC:
////            case Opcodes.INSN_INVOKE_STATIC_RANGE:
////                isStatic = true;
////                break;
////            default:
////                isStatic = false;
////        }
////        String logItName = annotationRulesManager.log(owner, name, desc, isStatic);
////        if (logItName != null) {
////            logger.debug(String.format("Adding Splatter logger to method '%s' (owner: '%s')", name, owner));
////            if (isStatic) signature = "V" + MethodSignature.popType(desc);
////            else signature = "V" + owner + MethodSignature.popType(desc);
////            int opcodeStatic = (opcode < 0x74) ? Opcodes.INSN_INVOKE_STATIC : Opcodes.INSN_INVOKE_STATIC_RANGE;
////            mv.visitMethodInsn(opcodeStatic, LogClassWriter.LOG_CLASS_NAME, logItName, signature, arguments);
//////            mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
//////            mv.visitCode();
//////            mv.visitMaxs(2, 0);
//////            mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Landroid/app/Activity;", "<init>", "V", new int[] { 1 });
//////            mv.visitStringInsn(INSN_CONST_STRING, 0, "A log");
//////            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", "log", "VLjava/lang/String;", new int[] { 0 });
//////            mv.visitInsn(INSN_RETURN_VOID);
//////            mv.visitEnd();
////
////        }
//        mv.visitMethodInsn(opcode, owner, name, desc, arguments);
//    }
}
