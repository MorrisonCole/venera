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

public class ComplexInstanceMethodEntryMethodVisitor extends SplatterRegisterAllocatingMethodVisitor {

    private final Logger logger = Logger.getLogger(ComplexInstanceMethodEntryMethodVisitor.class);
    private final String methodName;

    public ComplexInstanceMethodEntryMethodVisitor(int api, MethodVisitor methodVisitor, String desc, String methodName, boolean isStatic) {
        super(api, methodVisitor, desc, isStatic);
        this.methodName = methodName;
    }

    @Override
    protected int requiredExtraRegisters() {
        return 5;
    }

    @Override
    protected void addInstrumentation() {
        String[] parameters = null;
        try {
            Field method = mv.getClass().getDeclaredField("method");
            method.setAccessible(true);
            Method methodObject = (Method) method.get(mv);
            parameters = methodObject.getParameters();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.debug(e);
        }

        mv.visitVarInsn(INSN_CONST_4, 4, 0); // Set register 4 (our array index) to a value of 0
        mv.visitVarInsn(INSN_CONST_4, 1, getTotalNumberParameters()); // Set register 1 to a value representing our total number of parameters
        mv.visitTypeInsn(INSN_NEW_ARRAY, 0, 0, 1, "[Ljava/lang/String;"); // Initialize a String[] at register 0 with size whatever value is at 1

        int currentParamNumber = 0;
        if (parameters != null) {
            for (String parameterName : parameters) {
                mv.visitVarInsn(INSN_CONST_4, 4, currentParamNumber); // Set our index register to the current parameter number

                mv.visitStringInsn(INSN_CONST_STRING, 1, parameterName);
                mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 1, 0, 4); // Put the value at register 1 into the array at register 0 at index value[4]
                currentParamNumber++;
            }
        }


        mv.visitStringInsn(INSN_CONST_STRING, 1, methodName); // put our method name into register 1

        mv.visitVarInsn(INSN_CONST_4, 2, getTotalNumberParameters()); // Set register 2 to a value representing our total number of parameters
        mv.visitTypeInsn(INSN_NEW_ARRAY, 2, 0, 2, "[Ljava/lang/Object;"); // Initialize an Object[] at register 2 with size whatever value is at 2

        int parametersStartRegister = firstParameterRegister();
        currentParamNumber = 0;
        for (Map.Entry<Character, Integer> entry : getParameterMap().entries()) {
            int currentParameterRegister = parametersStartRegister + entry.getValue();

            mv.visitVarInsn(INSN_CONST_4, 3, currentParamNumber); // Set register 3 to a value of 0
            if (entry.getKey() == 'L' || entry.getKey() == '[') {
                mv.visitArrayOperationInsn(INSN_APUT_OBJECT, currentParameterRegister, 2, 3); // Put the value at the currentParameterRegister into the array at register 2 at the current parameter index (value[3])
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

        mv.visitMethodInsn(INSN_INVOKE_STATIC, typeDescriptorForClass(JsonLogger.class), "complexLogInstanceMethodEntry", "VLjava/lang/String;[Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;", new int[] { 1, 0, thisRegister(), 2 });
    }
}
