package com.heisentest.splatter.transform.dex.visitor;

import com.google.common.collect.ArrayListMultimap;
import com.heisentest.splatter.Descriptors;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureCommon.Label;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingInitializationMethodVisitor extends MethodVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingInitializationMethodVisitor.class);
    private final ArrayListMultimap<Character, Integer> parameterMap;
    private final String desc;
    private final int totalParameterRegisters;
    private final int totalNumberParameters;
    private final int totalNumberPrimitiveParameters;
    private int additionalNeeded;
    private int totalRequiredRegisters;
    private int maxStack;
    private int maxLocals;

    public SplatterLoggingInitializationMethodVisitor(int api, MethodVisitor methodVisitor, String desc) {
        super(api, methodVisitor);
        this.desc = desc;
        this.totalParameterRegisters = Descriptors.numParamRegisters(desc);
        totalNumberParameters = Descriptors.numParams(desc);
        totalNumberPrimitiveParameters = Descriptors.numPrimitiveParams(desc);
        parameterMap = Descriptors.mappedParams(desc);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;

        forceRequiredRegisters();

        // TODO: Should not be hardcoded. Also, duplicated!
        addHeisentestLoggerInitializationInstrumentation(totalRequiredRegisters - totalParameterRegisters - 1);

        int sourceRegister = totalRequiredRegisters - totalParameterRegisters - 1;
        int destinationRegister = totalRequiredRegisters - totalParameterRegisters - 1 - additionalNeeded;

        int parameterNumber = 1;
        while (sourceRegister <= totalRequiredRegisters - 1) {
            if (sourceRegister == totalRequiredRegisters - totalParameterRegisters - 1) {
                // Every class method definition has a local ('this') that takes the final position *before* any parameters.
                // If there are no parameters, it takes the final position. It counts as an 'in'.
                // A self-reference ('this'), so it will always be an object as primitives can't have methods!
                // Is the method is static, we just skip this register, since we don't have an object reference.
                mv.visitVarInsn(INSN_MOVE_OBJECT, destinationRegister, sourceRegister); // dest, source
                sourceRegister += 1;
                destinationRegister += 1;
            } else {
                int moveOpcodeForType = moveOpcodeForType(typeOfParameterAt(parameterNumber));
                parameterNumber++;
                if (moveOpcodeForType == INSN_MOVE_WIDE_16) {
                    // TODO: Need an extra register!
                    mv.visitVarInsn(moveOpcodeForType, destinationRegister, sourceRegister); // dest, source
                    sourceRegister += 2;
                    destinationRegister += 2;
                } else {
                    mv.visitVarInsn(moveOpcodeForType, destinationRegister, sourceRegister); // dest, source
                    sourceRegister += 1;
                    destinationRegister += 1;
                }
            }
        }
    }

    private char typeOfParameterAt(int parameterPosition) {
        int index = 0;
        index = Descriptors.advanceOne(desc, index); // Skip past the return type

        int i = 1;
        while (i != parameterPosition) {
            i++;
            index = Descriptors.advanceOne(desc, index);
        }

        char c = 'a';
        try {
            c = desc.charAt(index);
        } catch (StringIndexOutOfBoundsException e) {
            logger.debug(e);
        }
        return c;
    }

    private int moveOpcodeForType(char typeChar) {
        switch (typeChar) {
            case 'J': // long
            case 'D': // double
                return INSN_MOVE_WIDE_16;
            case '[': // array
            case 'L': // object
                return INSN_MOVE_OBJECT_16;
            default:
                return INSN_MOVE_16;
        }
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
        mv.visitTypeInsn(INSN_NEW_INSTANCE, 3, 0, 0, "Ljava/lang/Thread;");
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Thread;", "<init>", "VLjava/lang/Runnable;", new int[] { 3, 2 });
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Thread;", "start", "V", new int[] { 3 });
        mv.visitLabel(l1);
        mv.visitInsn(INSN_RETURN_VOID);
        mv.visitLabel(l2);
        mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/NoSuchMethodException;", "printStackTrace", "V", new int[] { 0 });
        mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
    }

    /**
     * Number of extra registers needed:
     * - 4
     */
    private void forceRequiredRegisters() {
        additionalNeeded = 7;
        totalRequiredRegisters = maxStack + additionalNeeded;
        mv.visitMaxs(totalRequiredRegisters, maxLocals);
    }
}
