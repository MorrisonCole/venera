package com.heisentest.splatter.transform.dex.visitor;

import com.google.common.collect.ArrayListMultimap;
import com.heisentest.splatter.Descriptors;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;

import static org.ow2.asmdex.Opcodes.*;

public abstract class SplatterRegisterAllocatingMethodVisitor extends MethodVisitor {

    private final Logger logger = Logger.getLogger(SplatterRegisterAllocatingMethodVisitor.class);
    private final String desc;
    /**
     * This can be larger than the actual number of parameters, because some values are 32 bit,
     * so require more than 1 register.
     */
    private final int totalParameterRegisters;
    private final int totalNumberParameters;
    private final int totalNumberPrimitiveParameters;
    private final ArrayListMultimap<Character, Integer> parameterMap;
    private int totalRequiredRegisters;
    private int maxStack;
    private int maxLocals;
    private boolean isStatic;

    public SplatterRegisterAllocatingMethodVisitor(int api, MethodVisitor methodVisitor, String desc, boolean isStatic) {
        super(api, methodVisitor);
        this.desc = desc;
        this.isStatic = isStatic;
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

        addInstrumentation();

        int sourceRegister = totalRequiredRegisters - totalParameterRegisters - 1;
        int destinationRegister = totalRequiredRegisters - totalParameterRegisters - 1 - requiredExtraRegisters();

        int parameterNumber = 1;
        while (sourceRegister <= totalRequiredRegisters - 1) {
            if (sourceRegister == totalRequiredRegisters - totalParameterRegisters - 1) {
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

    protected abstract int requiredExtraRegisters();

    private void forceRequiredRegisters() {
        totalRequiredRegisters = maxStack + requiredExtraRegisters();
        mv.visitMaxs(totalRequiredRegisters, maxLocals);
    }

    protected abstract void addInstrumentation();

    protected int thisRegister() {
        return totalRequiredRegisters - totalParameterRegisters - 1;
    }

    protected int firstParameterRegister() {
        return totalRequiredRegisters - totalParameterRegisters;
    }

    protected int getTotalNumberParameters() {
        return totalNumberParameters;
    }

    protected ArrayListMultimap<Character, Integer> getParameterMap() {
        return parameterMap;
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
}
