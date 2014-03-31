package com.heisentest.splatter.transform.dex.visitor.test;

import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import com.heisentest.splatter.transform.dex.visitor.SplatterRegisterAllocatingMethodVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureCommon.Label;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingSetUpMethodVisitor extends SplatterRegisterAllocatingMethodVisitor {

    private final InstrumentationSpy instrumentationSpy;

    public SplatterLoggingSetUpMethodVisitor(int api, MethodVisitor methodVisitor, String desc, boolean isStatic, InstrumentationSpy instrumentationSpy) {
        super(api, methodVisitor, desc, isStatic);
        this.instrumentationSpy = instrumentationSpy;
    }

    @Override
    protected int requiredExtraRegisters() {
        return 1;
    }

    @Override
    protected void addInstrumentation() {
        addHeisentestLoggerInitializationInstrumentation(thisRegister());
    }

    private void addHeisentestLoggerInitializationInstrumentation(int thisRegister) {
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, instrumentationSpy.getBaseTestCaseClassName(), "startLogging", "V", new int[] { thisRegister });
    }
}
