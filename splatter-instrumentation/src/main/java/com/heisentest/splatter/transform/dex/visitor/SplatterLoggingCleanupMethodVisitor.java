package com.heisentest.splatter.transform.dex.visitor;

import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.Opcodes;
import org.ow2.asmdex.structureCommon.Label;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingCleanupMethodVisitor extends SplatterRegisterAllocatingMethodVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingCleanupMethodVisitor.class);

    public SplatterLoggingCleanupMethodVisitor(int api, MethodVisitor methodVisitor, String desc) {
        super(api, methodVisitor, desc);
    }

    @Override
    protected int requiredExtraRegisters() {
        return 4;
    }

    @Override
    protected void addInstrumentation() {
        addHeisentestLoggerCleanupInstrumentation();
    }

    private void addHeisentestLoggerCleanupInstrumentation() {
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestJsonLogger;", "endLogging", "V", new int[] {  });
        Label l0 = new Label();
        mv.visitLabel(l0);
        Label l1 = new Label();
        Label l2 = new Label();
        mv.visitTryCatchBlock(l0, l1, l2, "Ljava/lang/InterruptedException;");
        mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/test/acceptance/SkeletonInstrumentationTestCase;", "logThread", "Ljava/lang/Thread;", 1, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Thread;", "join", "V", new int[] { 1 });
        mv.visitLabel(l1);
        mv.visitMethodInsn(INSN_INVOKE_SUPER, "Landroid/test/ActivityInstrumentationTestCase2;", "tearDown", "V", new int[] { thisRegister() });
        mv.visitInsn(INSN_RETURN_VOID);
        mv.visitLabel(l2);
        mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
        mv.visitStringInsn(INSN_CONST_STRING, 1, "HeisentestLogger");
        mv.visitStringInsn(INSN_CONST_STRING, 2, "Failed to complete logging");
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;", new int[] { 1, 2, 0 });
        mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
    }
}
