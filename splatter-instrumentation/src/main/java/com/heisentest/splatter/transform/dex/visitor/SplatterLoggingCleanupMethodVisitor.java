package com.heisentest.splatter.transform.dex.visitor;

import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.INSN_INVOKE_STATIC;

public class SplatterLoggingCleanupMethodVisitor extends MethodVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingCleanupMethodVisitor.class);

    public SplatterLoggingCleanupMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        addHeisentestLoggerCleanupInstrumentation();

        super.visitMaxs(maxStack, maxLocals);
    }

    private void addHeisentestLoggerCleanupInstrumentation() {
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "endLogging", "V", new int[] {  });
    }
}
