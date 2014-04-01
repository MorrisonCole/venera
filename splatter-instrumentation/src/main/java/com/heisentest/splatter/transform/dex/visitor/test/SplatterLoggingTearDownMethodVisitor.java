package com.heisentest.splatter.transform.dex.visitor.test;

import com.heisentest.splatter.transform.dex.visitor.SplatterRegisterAllocatingMethodVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.INSN_INVOKE_DIRECT;

public class SplatterLoggingTearDownMethodVisitor extends SplatterRegisterAllocatingMethodVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingTearDownMethodVisitor.class);
    private final String className;

    public SplatterLoggingTearDownMethodVisitor(int api, MethodVisitor methodVisitor, String desc, boolean isStatic, String className) {
        super(api, methodVisitor, desc, isStatic);
        this.className = className;
    }

    @Override
    protected int requiredExtraRegisters() {
        return 1;
    }

    @Override
    protected void addInstrumentation() {
        addHeisentestLoggerCleanupInstrumentation(thisRegister());
    }

    private void addHeisentestLoggerCleanupInstrumentation(int thisRegister) {
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, className, "endLogging", "V", new int[] { thisRegister });
    }
}
