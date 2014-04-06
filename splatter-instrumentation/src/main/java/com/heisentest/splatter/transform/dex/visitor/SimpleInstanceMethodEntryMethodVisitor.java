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
        return 3;
    }

    @Override
    protected void addInstrumentation() {
        mv.visitStringInsn(INSN_CONST_STRING, 0, methodName);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "getClass", "Ljava/lang/Class;", new int[] { thisRegister() });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 1);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Class;", "getName", "Ljava/lang/String;", new int[] { 1 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 1);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, typeDescriptorForClass(JsonLogger.class), "simpleLogInstanceMethodEntry", "VLjava/lang/String;Ljava/lang/String;", new int[] { 1, 0 });
    }
}
