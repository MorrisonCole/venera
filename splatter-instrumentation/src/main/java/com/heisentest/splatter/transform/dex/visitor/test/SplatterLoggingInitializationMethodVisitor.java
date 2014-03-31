package com.heisentest.splatter.transform.dex.visitor.test;

import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import com.heisentest.splatter.transform.dex.visitor.SplatterRegisterAllocatingMethodVisitor;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingInitializationMethodVisitor extends SplatterRegisterAllocatingMethodVisitor {

    private final InstrumentationSpy instrumentationSpy;

    public SplatterLoggingInitializationMethodVisitor(int api, MethodVisitor methodVisitor, String desc, boolean isStatic, InstrumentationSpy instrumentationSpy) {
        super(api, methodVisitor, desc, isStatic);
        this.instrumentationSpy = instrumentationSpy;
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
        mv.visitFieldInsn(INSN_IPUT_OBJECT, instrumentationSpy.getBaseTestCaseClassName(), "splatterIgnoreMethodRule", "Lcom/heisentest/splatter/sdk/SplatterIgnoreMethodRule;", 0, thisRegister);
    }
}
