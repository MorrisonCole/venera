package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.AnnotationRulesManager;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.Opcodes;

import java.util.ArrayList;
import java.util.Arrays;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingClassVisitor extends ClassVisitor {

    private final AnnotationRulesManager annotationRulesManager;
    private final Logger logger = Logger.getLogger(SplatterLoggingClassVisitor.class);

    public SplatterLoggingClassVisitor(int asmApiLevel, ClassVisitor classVisitor, AnnotationRulesManager annotationRulesManager) {
        super(asmApiLevel, classVisitor);
        this.annotationRulesManager = annotationRulesManager;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

//        methodVisitor.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { 2 });
//        methodVisitor.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 0);
//        methodVisitor.visitStringInsn(INSN_CONST_STRING, 1, name);
//        methodVisitor.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", "log", "VLjava/lang/String;Ljava/lang/String;", new int[] { 0, 1 });
//
//        methodVisitor.visitStringInsn(INSN_CONST_STRING, 0, "this class");
//        methodVisitor.visitStringInsn(INSN_CONST_STRING, 1, "aMethodWithArguments");
//        methodVisitor.visitVarInsn(INSN_CONST_4, 2, 1);
//        methodVisitor.visitTypeInsn(INSN_NEW_ARRAY, 2, 0, 2, "[Ljava/lang/Object;");
//        methodVisitor.visitVarInsn(INSN_CONST_4, 3, 0);
//        methodVisitor.visitStringInsn(INSN_CONST_STRING, 4, "an arg");
//        methodVisitor.visitArrayOperationInsn(INSN_APUT_OBJECT, 4, 2, 3);
//        methodVisitor.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", "log", "VLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 0, 1, 2 });

        ArrayList<String> excludedFunctions = new ArrayList<String>(Arrays.asList("<init>", "<clinit>", "getActionBar", "onSectionAttached", "onCreate", "newInstance"));

//        if ((access & Opcodes.ACC_ABSTRACT) == 0 && !excludedFunctions.contains(name) && !name.contains("$")) {
        if ((access & Opcodes.ACC_ABSTRACT) == 0 && name.equals("getActionBar") && !name.contains("$")) {
            logger.debug(String.format("Adding Splatter logger to method (name: '%s') (desc: '%s') (access (opcode): '%s')", name, desc, access));

            methodVisitor.visitMaxs(4, 0);
            methodVisitor.visitStringInsn(INSN_CONST_STRING, 2, "A heisentest log");
            methodVisitor.visitStringInsn(INSN_CONST_STRING, 3, name);
            methodVisitor.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[]{2, 3});
        }

        return new SplatterMethodVisitor(api, methodVisitor, annotationRulesManager);
    }
}
