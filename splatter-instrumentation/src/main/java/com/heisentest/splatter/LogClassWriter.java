package com.heisentest.splatter;

import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.*;

public class LogClassWriter  {

    public static final String LOG_CLASS_NAME = "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;";

    public void addLogClass(ApplicationVisitor applicationVisitor) {
        ClassVisitor classVisitor = applicationVisitor.visitClass(ACC_PUBLIC, LOG_CLASS_NAME, null, "Ljava/lang/Object;", null);
        classVisitor.visit(0, ACC_PUBLIC, LOG_CLASS_NAME, null, "Ljava/lang/Object;", null);
        classVisitor.visitSource("HeisentestLogger.java", null);
        {
            MethodVisitor methodVisitor = classVisitor.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitMaxs(1, 0);
            methodVisitor.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[]{0});
            methodVisitor.visitInsn(INSN_RETURN_VOID);
            methodVisitor.visitEnd();
        }
        {
            MethodVisitor methodVisitor = classVisitor.visitMethod(ACC_PUBLIC, "log", "VLjava/lang/String;", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitMaxs(3, 0);
            methodVisitor.visitStringInsn(INSN_CONST_STRING, 0, "HeisentestInstrument");
            methodVisitor.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[]{0, 1});
            methodVisitor.visitInsn(INSN_RETURN_VOID);
            methodVisitor.visitEnd();
        }
        classVisitor.visitEnd();
    }
}
