package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.instrumentation.logging.JsonLogger;
import com.heisentest.splatter.instrumentation.point.JumpInstrumentationPoint;
import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import org.apache.log4j.Logger;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureCommon.Label;
import org.ow2.asmdex.structureWriter.Method;

import java.lang.reflect.Field;
import java.util.Map;

import static com.heisentest.splatter.utility.DalvikTypeDescriptor.typeDescriptorForClass;
import static org.ow2.asmdex.Opcodes.*;

public class ComplexInstanceMethodEntryMethodVisitor extends SplatterRegisterAllocatingMethodVisitor {

    private final Logger logger = Logger.getLogger(ComplexInstanceMethodEntryMethodVisitor.class);
    private final String methodName;
    private final String className;
    private final InstrumentationSpy instrumentationSpy;

    public ComplexInstanceMethodEntryMethodVisitor(int api, MethodVisitor methodVisitor, String desc, String methodName, boolean isStatic, String className, InstrumentationSpy instrumentationSpy) {
        super(api, methodVisitor, desc, isStatic);
        this.methodName = methodName;
        this.className = className;
        this.instrumentationSpy = instrumentationSpy;
    }

    @Override
    public void visitJumpInsn(int opcode, Label label, int registerA, int registerB) {
        final JumpInstrumentationPoint jumpInstrumentationPoint = instrumentationSpy.getJumpInstrumentationPoint(className, methodName, label.getLine());
        if (jumpInstrumentationPoint != null) {
            // TODO: insert instrumentation!
            // We don't care if it's unconditional; only on branches.
        }

        super.visitJumpInsn(opcode, label, registerA, registerB);
    }

    @Override
    protected int requiredExtraRegisters() {
        return 8;
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

        mv.visitVarInsn(INSN_CONST_4, 0, getTotalNumberParameters()); // Set register 0 to a value representing our total number of parameters
        mv.visitTypeInsn(INSN_NEW_ARRAY, 4, 0, 0, "[Ljava/lang/String;"); // Initialize a String[] at register 4 with size whatever value is at 0

        int currentParamNumber = 0;
        if (parameters != null) {
            for (String parameterName : parameters) {
                mv.visitVarInsn(INSN_CONST_4, 7, currentParamNumber); // Set our index register to the current parameter number

                mv.visitStringInsn(INSN_CONST_STRING, 0, parameterName);
                mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 0, 4, 7); // Put the value at register 0 into the array at register 4 at index value[7]
                currentParamNumber++;
            }
        }

        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/System;", "currentTimeMillis", "J", new int[] {  });
        mv.visitIntInsn(INSN_MOVE_RESULT_WIDE, 0);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/Thread;", "currentThread", "Ljava/lang/Thread;", new int[] {  });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);

        mv.visitStringInsn(INSN_CONST_STRING, 3, methodName); // put our method name into register 3

        mv.visitVarInsn(INSN_CONST_4, 5, getTotalNumberParameters()); // Set register 5 to a value representing our total number of parameters
        mv.visitTypeInsn(INSN_NEW_ARRAY, 6, 0, 5, "[Ljava/lang/Object;"); // Initialize an Object[] at register 6 with size whatever value is at 5

        int parametersStartRegister = firstParameterRegister();
        currentParamNumber = 0;
        for (Map.Entry<Character, Integer> entry : getParameterMap().entries()) {
            int currentParameterRegister = parametersStartRegister + entry.getValue();

            mv.visitVarInsn(INSN_CONST_4, 7, currentParamNumber); // Set register 7 (our array index) to the current parameter number
            if (entry.getKey() == 'L' || entry.getKey() == '[') {
                mv.visitArrayOperationInsn(INSN_APUT_OBJECT, currentParameterRegister, 6, 7); // Put the value at the currentParameterRegister into the array at register 6 at the current parameter index (value[7])
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
                mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5); // Put our int object in register 5
                mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 5, 6, 7); // Put the value at register 5 into the array at register 6 at index (value of register 7)
            }
            currentParamNumber++;
        }

        mv.visitVarInsn(INSN_MOVE_OBJECT, 5, thisRegister());
        mv.visitMethodInsn(INSN_INVOKE_STATIC_RANGE, typeDescriptorForClass(JsonLogger.class), "complexLogInstanceMethodEntry", "VJLjava/lang/Thread;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;", new int[] { 0, 1, 2, 3, 4, 5, 6 });
    }
}
