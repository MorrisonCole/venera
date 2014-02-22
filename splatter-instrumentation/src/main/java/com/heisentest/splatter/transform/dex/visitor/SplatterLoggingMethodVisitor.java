package com.heisentest.splatter.transform.dex.visitor;

import com.google.common.collect.ArrayListMultimap;
import com.heisentest.splatter.Descriptors;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;

import java.util.HashMap;
import java.util.Map;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingMethodVisitor extends MethodVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingMethodVisitor.class);
    private final String desc;
    private final String name;
    /**
     * This can be larger than the actual number of parameters, because some values are 32 bit,
     * so require more than 1 register.
      */
    private final int totalParameterRegisters;
    private final ArrayListMultimap<Character, Integer> parameterMap;
    private final int totalNumberPrimitiveParameters;
    private int maxStack;
    private int maxLocals;
    private int additionalNeeded;
    private int totalRequiredRegisters;
    private boolean isStatic;
    private String className;
    private int totalNumberParameters;

    public SplatterLoggingMethodVisitor(int api, MethodVisitor methodVisitor, String desc, String methodName, String className, boolean isStatic) {
        super(api, methodVisitor);
        this.desc = desc;
        this.name = methodName;
        this.className = className;
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

        if (isStatic) {
//            applyStaticInstrumentation(register1, register2);
        } else {
            applyParameterCollectingInstrumentation(totalRequiredRegisters - totalParameterRegisters - 1);
        }

        int sourceRegister = totalRequiredRegisters - totalParameterRegisters - 1;
        int destinationRegister = totalRequiredRegisters - totalParameterRegisters - 1 - additionalNeeded;

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

    private void applyStaticInstrumentation(int register1, int register2) {
        // Class name is in the form "Lcom/heisentest/skeletonandroidapp/MainActivity$PlaceholderFragment;", so we
        // just grab the last bit.
        mv.visitStringInsn(INSN_CONST_STRING, register1, className.substring(className.lastIndexOf('/') + 1, className.lastIndexOf(';')));
        mv.visitStringInsn(INSN_CONST_STRING, register2, "(static) " + name);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestJsonLogger;", "log", "VLjava/lang/String;Ljava/lang/String;", new int[] { register1, register2 });
    }

    private void applyRegularInstrumentation(int register1, int register2, int thisRegister) {
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { thisRegister });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, register1);
        mv.visitStringInsn(INSN_CONST_STRING, register2, name);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestJsonLogger;", "log", "VLjava/lang/String;Ljava/lang/String;", new int[] { register1, register2 });
    }

    /**
     * We create a new Object[] and fill it with the parameters to be logged.
     * Any primitive arguments need to be converted into their respective object
     * representations.
     */
    private void applyParameterCollectingInstrumentation(int thisRegister) {
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL_RANGE, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { thisRegister }); //TODO: should not always use range!!!
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 0); // put 'this' into register 0
        mv.visitStringInsn(INSN_CONST_STRING, 1, name); // put our method name into register 1

        mv.visitVarInsn(INSN_CONST_4, 2, totalNumberParameters); // Set register 2 to a value representing our total number of parameters
        mv.visitTypeInsn(INSN_NEW_ARRAY, 2, 0, 2, "[Ljava/lang/Object;"); // Initialize an Object[] at register 2 with size whatever value is at 2

        int parametersStartRegister = totalRequiredRegisters - totalParameterRegisters;
        int currentParamNumber = 0;
        for (Map.Entry<Character, Integer> entry : parameterMap.entries()) {
            int currentParameterRegister = parametersStartRegister + entry.getValue();

            mv.visitVarInsn(INSN_CONST_4, 3, currentParamNumber); // Set register 3 to a value of 0
            if (entry.getKey() == 'L' || entry.getKey() == '[') {
                mv.visitArrayOperationInsn(INSN_APUT_OBJECT, currentParameterRegister, 2, 3); // Put the value at the parameter register into the array at the current parameter index
            } else {
                // Convert our primitive param to an object.
                Character argumentCharacter = entry.getKey();
                switch (argumentCharacter) {
                    case 'Z':
                        mv.visitMethodInsn(INSN_INVOKE_STATIC_RANGE, "Ljava/lang/Boolean;", "toString", "Ljava/lang/String;Z", new int[] { currentParameterRegister });
                        break;
                    case 'B':
                        mv.visitMethodInsn(INSN_INVOKE_STATIC_RANGE, "Ljava/lang/Byte;", "toString", "Ljava/lang/String;B", new int[] { currentParameterRegister });
                        break;
                    case 'S':
                        mv.visitMethodInsn(INSN_INVOKE_STATIC_RANGE, "Ljava/lang/Short;", "toString", "Ljava/lang/String;S", new int[] { currentParameterRegister });
                        break;
                    case 'C':
                        mv.visitMethodInsn(INSN_INVOKE_STATIC_RANGE, "Ljava/lang/Character;", "toString", "Ljava/lang/String;C", new int[] { currentParameterRegister });
                        break;
                    case 'I':
                        mv.visitMethodInsn(INSN_INVOKE_STATIC_RANGE, "Ljava/lang/Integer;", "toString", "Ljava/lang/String;I", new int[] { currentParameterRegister });
                        break;
                    case 'J':
                        mv.visitMethodInsn(INSN_INVOKE_STATIC_RANGE, "Ljava/lang/Long;", "toString", "Ljava/lang/String;J", new int[] { currentParameterRegister, currentParameterRegister + 1 });
                        break;
                    case 'F':
                        mv.visitMethodInsn(INSN_INVOKE_STATIC_RANGE, "Ljava/lang/Float;", "toString", "Ljava/lang/String;F", new int[] { currentParameterRegister });
                        break;
                    case 'D':
                        mv.visitMethodInsn(INSN_INVOKE_STATIC_RANGE, "Ljava/lang/Double;", "toString", "Ljava/lang/String;D", new int[] { currentParameterRegister, currentParameterRegister + 1 });
                        break;
                    default:
                        logger.error(String.format("Unsupported primitive argument: %s", argumentCharacter));
                }
                mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4); // Put our int object in register 4
                mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 4, 2, 3); // Put the value at register 4 into the array at register 2 at index (value of register 3)
            }
            currentParamNumber++;
        }
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestJsonLogger;", "log", "VLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 0, 1, 2 });
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


    /**
     * Number of extra registers needed:
     * - 2 (class name / method name)
     * - 2 (object array / array index)
     * - totalPrimitiveParams (object representation)
     */
    private void forceRequiredRegisters() {
        additionalNeeded = 4 + totalNumberPrimitiveParameters;
        totalRequiredRegisters = maxStack + additionalNeeded;
        mv.visitMaxs(totalRequiredRegisters, maxLocals);
    }
}
