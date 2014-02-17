package com.heisentest.splatter;

import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureCommon.Label;

import static org.ow2.asmdex.Opcodes.*;

public class LogClassWriter  {

    public void addLogClass(ApplicationVisitor applicationVisitor) {
        ClassVisitor cv = applicationVisitor.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", null, "Ljava/lang/Object;", null);
        cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", null, "Ljava/lang/Object;", null);
        cv.visitSource("HeisentestLogger.java", null);
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
            mv.visitCode();
            mv.visitMaxs(1, 0);
            mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
            mv.visitInsn(INSN_RETURN_VOID);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC + ACC_TRANSIENT, "log", "VLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMaxs(12, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 4, "");
            mv.visitVarInsn(INSN_MOVE_OBJECT, 0, 11);
            mv.visitArrayLengthInsn(2, 0);
            mv.visitVarInsn(INSN_CONST_4, 1, 0);
            Label l0 = new Label();
            mv.visitLabel(l0);
            Label l1 = new Label();
            mv.visitJumpInsn(INSN_IF_GE, l1, 1, 2);
            mv.visitArrayOperationInsn(INSN_AGET_OBJECT, 3, 0, 1);
            Label l2 = new Label();
            mv.visitJumpInsn(INSN_IF_EQZ, l2, 3, 0);
            mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { 3 });
            mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
            mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/String;", "concat", "Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5 });
            mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
            mv.visitLabel(l2);
            mv.visitOperationInsn(INSN_ADD_INT_LIT8, 1, 1, 0, 1);
            mv.visitJumpInsn(INSN_GOTO, l0, 0, 0);
            mv.visitLabel(l1);
            mv.visitStringInsn(INSN_CONST_STRING, 5, "HeisentestLogger");
            mv.visitStringInsn(INSN_CONST_STRING, 6, "Class -  %s, Method - %s, Parameters - %s");
            mv.visitVarInsn(INSN_CONST_4, 7, 3);
            mv.visitTypeInsn(INSN_NEW_ARRAY, 7, 0, 7, "[Ljava/lang/Object;");
            mv.visitVarInsn(INSN_CONST_4, 8, 0);
            mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 9, 7, 8);
            mv.visitVarInsn(INSN_CONST_4, 8, 1);
            mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 10, 7, 8);
            mv.visitVarInsn(INSN_CONST_4, 8, 2);
            mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 4, 7, 8);
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/String;", "format", "Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 6, 7 });
            mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 6);
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 5, 6 });
            mv.visitInsn(INSN_RETURN_VOID);
            mv.visitEnd();
        }
        cv.visitEnd();
    }
}
