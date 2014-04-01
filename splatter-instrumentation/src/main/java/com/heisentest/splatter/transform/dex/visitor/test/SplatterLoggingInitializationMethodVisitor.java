package com.heisentest.splatter.transform.dex.visitor.test;

import com.heisentest.splatter.transform.dex.visitor.SplatterRegisterAllocatingMethodVisitor;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingInitializationMethodVisitor extends SplatterRegisterAllocatingMethodVisitor {

    private final String className;

    public SplatterLoggingInitializationMethodVisitor(int api, MethodVisitor methodVisitor, String desc, boolean isStatic, String className) {
        super(api, methodVisitor, desc, isStatic);
        this.className = className;
    }

    @Override
    protected int requiredExtraRegisters() {
        return 2;
    }

    @Override
    protected void addInstrumentation() {
        addHeisentestLoggerInitializationInstrumentation(thisRegister());
    }

    private void addHeisentestLoggerInitializationInstrumentation(int thisRegister) {
        mv.visitTypeInsn(INSN_NEW_INSTANCE, 0, 0, 0, "Lcom/heisentest/splatter/sdk/SplatterIgnoreMethodRule;");
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Lcom/heisentest/splatter/sdk/SplatterIgnoreMethodRule;", "<init>", "V", new int[] { 0 });
        mv.visitFieldInsn(INSN_IPUT_OBJECT, className, "splatterIgnoreMethodRule", "Lcom/heisentest/splatter/sdk/SplatterIgnoreMethodRule;", 0, thisRegister);
    }
}
