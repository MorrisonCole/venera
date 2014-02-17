package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.Descriptors;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingMethodVisitor extends MethodVisitor {

    private static final int[] reg = {0, 1};

    private final Logger logger = Logger.getLogger(SplatterLoggingMethodVisitor.class);
    private final String desc;
    private final String name;
    /**
     * This can be larger than the actual number of parameters, because some values are 32 bit,
     * so require more than 1 register.
      */
    private final int numberOfParameterRegisters;
    private int maxStack;
    private int maxLocals;
    private int additionalNeeded;
    private int totalRequiredRegisters;
    private boolean isStatic;
    private String className;

    public SplatterLoggingMethodVisitor(int api, MethodVisitor methodVisitor, String desc, String methodName, String className, boolean isStatic) {
        super(api, methodVisitor);
        this.desc = desc;
        this.name = methodName;
        this.className = className;
        this.isStatic = isStatic;
        this.numberOfParameterRegisters = Descriptors.numParams(desc);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;


        requireTwoRegisters();

        int register1 = totalRequiredRegisters - numberOfParameterRegisters - additionalNeeded;
        int register2 = totalRequiredRegisters - numberOfParameterRegisters - (additionalNeeded + 1);

        if (isStatic) {
            applyStaticInstrumentation(register1, register2);
        } else {
            applyRegularInstrumentation(register1, register2, totalRequiredRegisters - numberOfParameterRegisters - 1);
        }

        int sourceRegister = totalRequiredRegisters - numberOfParameterRegisters - 1;
        int destinationRegister = totalRequiredRegisters - numberOfParameterRegisters - 1 - additionalNeeded;

        int parameterNumber = 1;
        while (sourceRegister <= totalRequiredRegisters - 1) {
            if (sourceRegister == totalRequiredRegisters - numberOfParameterRegisters - 1) {
                if (!isStatic) {
                    // Every class method definition has a local ('this') that takes the final position *before* any parameters.
                    // If there are no parameters, it takes the final position. It counts as an 'in'.
                    // A self-reference ('this'), so it will always be an object as primitives can't have methods!
                    // Is the method is static, we just skip this register, since we don't have an object reference.
                    mv.visitVarInsn(INSN_MOVE_OBJECT, destinationRegister, sourceRegister); // dest, source
                }
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

    private void applyStaticInstrumentation(int register1, int register2) {
        // Class name is in the form "Lcom/heisentest/skeletonandroidapp/MainActivity$PlaceholderFragment;", so we
        // just grab the last bit.
        mv.visitStringInsn(INSN_CONST_STRING, register1, className.substring(className.lastIndexOf('/') + 1, className.lastIndexOf(';')));
        mv.visitStringInsn(INSN_CONST_STRING, register2, "(static) " + name);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", "log", "VLjava/lang/String;Ljava/lang/String;", new int[] { register1, register2 });
    }

    private void applyRegularInstrumentation(int register1, int register2, int thisRegister) {
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { thisRegister });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, register1);
        mv.visitStringInsn(INSN_CONST_STRING, register2, name);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", "log", "VLjava/lang/String;Ljava/lang/String;", new int[] { register1, register2 });
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

    private void requireTwoRegisters() {
        additionalNeeded = 2;
        totalRequiredRegisters = maxStack + 2;
        mv.visitMaxs(totalRequiredRegisters, maxLocals);
    }
}
