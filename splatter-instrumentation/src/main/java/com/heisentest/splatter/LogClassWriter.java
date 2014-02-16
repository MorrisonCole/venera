package com.heisentest.splatter;

import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureCommon.Label;

import static org.ow2.asmdex.Opcodes.*;

public class LogClassWriter  {

    public static final String LOG_CLASS_NAME = "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;";

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
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "log", "VLjava/lang/String;Ljava/lang/String;", null, null);
            mv.visitCode();
            mv.visitMaxs(6, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 0, "HeisentestLogger");
            mv.visitStringInsn(INSN_CONST_STRING, 1, "Class -  %s, Method - %s");
            mv.visitVarInsn(INSN_CONST_4, 2, 2);
            mv.visitTypeInsn(INSN_NEW_ARRAY, 2, 0, 2, "[Ljava/lang/Object;");
            mv.visitVarInsn(INSN_CONST_4, 3, 0);
            mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 4, 2, 3);
            mv.visitVarInsn(INSN_CONST_4, 3, 1);
            mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 5, 2, 3);
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/String;", "format", "Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 1, 2 });
            mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 1);
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 0, 1 });
            mv.visitInsn(INSN_RETURN_VOID);
            mv.visitEnd();
        }
        cv.visitEnd();
    }
}
