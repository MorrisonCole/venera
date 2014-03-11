package com.heisentest.splatter.transform.dex.visitor;

import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureCommon.Label;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingInitializationMethodVisitor extends SplatterRegisterAllocatingMethodVisitor {

    public SplatterLoggingInitializationMethodVisitor(int api, MethodVisitor methodVisitor, String desc, boolean isStatic) {
        super(api, methodVisitor, desc, isStatic);
    }

    @Override
    protected int requiredExtraRegisters() {
        return 7;
    }

    @Override
    protected void addInstrumentation() {
        addHeisentestLoggerInitializationInstrumentation(thisRegister());
    }

    private void addHeisentestLoggerInitializationInstrumentation(int thisRegister) {
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/os/Environment;", "getExternalStorageDirectory", "Ljava/io/File;", new int[] {  });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 1);
        Label l0 = new Label();
        mv.visitLabel(l0);
        Label l1 = new Label();
        Label l2 = new Label();
        mv.visitTryCatchBlock(l0, l1, l2, "Ljava/lang/NoSuchMethodException;");
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "getClass", "Ljava/lang/Class;", new int[] { thisRegister });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 6);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Lcom/heisentest/skeletonandroidapp/test/acceptance/SkeletonInstrumentationTestCase;", "getName", "Ljava/lang/String;", new int[] { thisRegister });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 7);
        mv.visitVarInsn(INSN_CONST_4, 5, 0);
        mv.visitTypeInsn(INSN_CHECK_CAST, 0, 5, 0, "[Ljava/lang/Class;");
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Class;", "getMethod", "Ljava/lang/reflect/Method;Ljava/lang/String;[Ljava/lang/Class;", new int[] { 6, 7, 5 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
        mv.visitTypeInsn(INSN_NEW_INSTANCE, 2, 0, 0, "Lcom/heisentest/skeletonandroidapp/HeisentestJsonLogger;");
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/reflect/Method;", "getName", "Ljava/lang/String;", new int[] { 4 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Lcom/heisentest/skeletonandroidapp/HeisentestJsonLogger;", "<init>", "VLjava/io/File;Ljava/lang/String;", new int[] { 2, 1, 5 });
        mv.visitTypeInsn(INSN_NEW_INSTANCE, 4, 0, 0, "Ljava/lang/Thread;");
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Thread;", "<init>", "VLjava/lang/Runnable;", new int[] { 4, 2 });
        mv.visitFieldInsn(INSN_SPUT_OBJECT, "Lcom/heisentest/skeletonandroidapp/test/acceptance/SkeletonInstrumentationTestCase;", "logThread", "Ljava/lang/Thread;", 4, 0);
        mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/test/acceptance/SkeletonInstrumentationTestCase;", "logThread", "Ljava/lang/Thread;", 4, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Thread;", "start", "V", new int[] { 4 });
        mv.visitLabel(l1);
        mv.visitInsn(INSN_RETURN_VOID);
        mv.visitLabel(l2);
        mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/NoSuchMethodException;", "printStackTrace", "V", new int[] { 0 });
        mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
    }
}
