package com.heisentest.venera.transform.dex.visitor.test;

import com.heisentest.venera.transform.dex.visitor.VeneraRegisterAllocatingMethodVisitor;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.*;

public class VeneraLoggingInitializationMethodVisitor extends VeneraRegisterAllocatingMethodVisitor {

    private final String className;

    public VeneraLoggingInitializationMethodVisitor(int api, MethodVisitor methodVisitor, String desc, boolean isStatic, String className) {
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
        mv.visitTypeInsn(INSN_NEW_INSTANCE, 0, 0, 0, "Lcom/heisentest/venera/sdk/VeneraIgnoreMethodRule;");
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Lcom/heisentest/venera/sdk/VeneraIgnoreMethodRule;", "<init>", "V", new int[] { 0 });
        mv.visitFieldInsn(INSN_IPUT_OBJECT, className, "veneraIgnoreMethodRule", "Lcom/heisentest/venera/sdk/VeneraIgnoreMethodRule;", 0, thisRegister);
    }
}
