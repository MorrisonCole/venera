package com.heisentest.venera.transform.dex.visitor.test;

import com.heisentest.venera.transform.dex.visitor.VeneraRegisterAllocatingMethodVisitor;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.INSN_INVOKE_VIRTUAL;

public class VeneraLoggingSetUpMethodVisitor extends VeneraRegisterAllocatingMethodVisitor {

    private final String className;

    public VeneraLoggingSetUpMethodVisitor(int api, MethodVisitor methodVisitor, String desc, boolean isStatic, String className) {
        super(api, methodVisitor, desc, isStatic);
        this.className = className;
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
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, className, "startLogging", "V", new int[] { thisRegister });
    }
}
