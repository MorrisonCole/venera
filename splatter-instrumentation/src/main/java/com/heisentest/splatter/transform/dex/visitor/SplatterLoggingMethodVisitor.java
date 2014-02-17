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
    private final char returnType;
    private int maxStack;
    private int maxLocals;
    private int numRegs;
    private int additionalNeeded;
    private int totalRequiredRegisters;
    private boolean isStatic;

    public SplatterLoggingMethodVisitor(int api, MethodVisitor methodVisitor, String desc, String methodName, boolean isStatic) {
        super(api, methodVisitor);
        this.desc = desc;
        this.name = methodName;
        this.isStatic = isStatic;
        this.numberOfParameterRegisters = Descriptors.numParams(desc);
        this.returnType = desc.charAt(0);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.numRegs = maxStack - numberOfParameterRegisters;


        requireTwoRegisters();

        int register1 = totalRequiredRegisters - numberOfParameterRegisters - additionalNeeded;
        int register2 = totalRequiredRegisters - numberOfParameterRegisters - (additionalNeeded + 1);

        mv.visitStringInsn(INSN_CONST_STRING, register1, "A heisentest log");
        mv.visitStringInsn(INSN_CONST_STRING, register2, name);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[]{register1, register2});

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

    private void shiftParameters() {
        if (additionalNeeded > 0) { // Downshift all the parameter registers so that the existing code will work
            int index = 0;
            index = Descriptors.advanceOne(desc, index); // Skip past the return type
            int pdest = totalRequiredRegisters - numberOfParameterRegisters;
            int psrc = totalRequiredRegisters;

            while (index < desc.length()) {
                char c = desc.charAt(index);
                switch (c) {
                    case 'J': // long
                    case 'D': // double
                        mv.visitVarInsn(INSN_MOVE_WIDE_16, pdest, psrc);
                        // Extra register for longs and doubles
                        pdest++;
                        psrc++;
                        break;
                    case '[': // array
                    case 'L': // object
                        mv.visitVarInsn(INSN_MOVE_OBJECT_16, pdest, psrc);
                    default:
                        mv.visitVarInsn(INSN_MOVE_16, pdest, psrc);
                        break;
                }
                pdest++;
                psrc++;
                index = Descriptors.advanceOne(desc, index);
            }
        }
    }
}
