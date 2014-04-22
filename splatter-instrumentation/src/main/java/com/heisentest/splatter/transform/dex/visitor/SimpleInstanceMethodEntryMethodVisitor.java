package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.instrumentation.logging.JsonLogger;
import com.heisentest.splatter.utility.DalvikTypeDescriptor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureWriter.Method;

import java.lang.reflect.Field;
import java.util.Map;

import static com.heisentest.splatter.utility.DalvikTypeDescriptor.typeDescriptorForClass;
import static org.ow2.asmdex.Opcodes.*;

public class SimpleInstanceMethodEntryMethodVisitor extends SplatterRegisterAllocatingMethodVisitor {

    private final Logger logger = Logger.getLogger(ComplexInstanceMethodEntryMethodVisitor.class);
    private final String methodName;

    public SimpleInstanceMethodEntryMethodVisitor(int api, MethodVisitor methodVisitor, String desc, String methodName, boolean isStatic) {
        super(api, methodVisitor, desc, isStatic);
        this.methodName = methodName;
    }

    @Override
    protected int requiredExtraRegisters() {
        return 5;
    }

    @Override
    protected void addInstrumentation() {
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/System;", "currentTimeMillis", "J", new int[] {  });
        mv.visitIntInsn(INSN_MOVE_RESULT_WIDE, 0);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/Thread;", "currentThread", "Ljava/lang/Thread;", new int[] {  });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);
        mv.visitStringInsn(INSN_CONST_STRING, 3, methodName);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "getClass", "Ljava/lang/Class;", new int[] { thisRegister() });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Class;", "getName", "Ljava/lang/String;", new int[] { 4 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, typeDescriptorForClass(JsonLogger.class), "simpleLogInstanceMethodEntry", "VJLjava/lang/Thread;Ljava/lang/String;Ljava/lang/String;", new int[] { 0, 1, 2, 3, 4 });
    }
}
