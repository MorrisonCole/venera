package com.heisentest.venera.transform.dex.visitor.test;

import com.heisentest.venera.instrumentation.logging.JsonLogger;
import com.heisentest.venera.sdk.Venera;
import com.heisentest.venera.transform.dex.InstrumentationSpy;
import com.heisentest.venera.transform.dex.visitor.noop.VeneraNoOpMethodVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.FieldVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureCommon.Label;

import static com.heisentest.venera.sdk.Venera.InstrumentationPolicy;
import static com.heisentest.venera.utility.DalvikOpcodes.isStatic;
import static com.heisentest.venera.utility.DalvikTypeDescriptor.typeDescriptorForClass;
import static org.ow2.asmdex.Opcodes.*;

public class VeneraBaseTestCaseClassVisitor extends ClassVisitor {

    private final Logger logger = Logger.getLogger(VeneraBaseTestCaseClassVisitor.class);
    private final String className;
    private final InstrumentationSpy instrumentationSpy;

    public VeneraBaseTestCaseClassVisitor(int api, ClassVisitor classVisitor, String className, InstrumentationSpy instrumentationSpy) {
        super(api, classVisitor);
        this.className = className;
        this.instrumentationSpy = instrumentationSpy;

        {
            FieldVisitor fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "logThread", "Ljava/lang/Thread;", null, null);
            fv.visitEnd();
        }
        {
            FieldVisitor fv = cv.visitField(ACC_PRIVATE, "logging", "Z", null, null);
            fv.visitEnd();
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        final MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

        boolean isStatic = isStatic(access);
        if (instrumentationSpy.isBaseTestCaseSetUpMethod(className, name)) {
            logger.debug(String.format("Adding HeisentestLogger set up to method (name: '%s') (desc: '%s') (class: '%s')", name, desc, className));
            return new VeneraLoggingSetUpMethodVisitor(api, methodVisitor, desc, isStatic, className);
        } else if (instrumentationSpy.isBaseTestCaseTearDownMethod(className, name)) {
            logger.debug(String.format("Adding HeisentestLogger tear down to method (name: '%s') (desc: '%s') (class: '%s')", name, desc, className));
            return new VeneraLoggingTearDownMethodVisitor(api, methodVisitor, desc, isStatic, className);
        }

        return new VeneraNoOpMethodVisitor(api, methodVisitor);
    }

    @Override
    public void visitEnd() {
        addStartLoggingMethod();
        addEndLoggingMethod();

        super.visitEnd();
    }

    private void addStartLoggingMethod() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "startLogging", "V", null, null);
        mv.visitCode();
        mv.visitMaxs(9, 0);
        mv.visitVarInsn(INSN_CONST_4, 5, 0);
        mv.visitFieldInsn(INSN_IPUT_BOOLEAN, className, "logging", "Z", 5, 8);
        Label l0 = new Label();
        mv.visitLabel(l0);
        Label l1 = new Label();
        Label l2 = new Label();
        mv.visitTryCatchBlock(l0, l1, l2, "Ljava/lang/NoSuchMethodException;");
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "getClass", "Ljava/lang/Class;", new int[] { 8 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 6);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, className, "getName", "Ljava/lang/String;", new int[] { 8 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 7);
        mv.visitVarInsn(INSN_CONST_4, 5, 0);
        mv.visitTypeInsn(INSN_CHECK_CAST, 0, 5, 0, "[Ljava/lang/Class;");
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Class;", "getMethod", "Ljava/lang/reflect/Method;Ljava/lang/String;[Ljava/lang/Class;", new int[] { 6, 7, 5 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 3);
        mv.visitTypeInsn(INSN_CONST_CLASS, 5, 0, 0, typeDescriptorForClass(Venera.class));
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/reflect/Method;", "getAnnotation", "Ljava/lang/annotation/Annotation;Ljava/lang/Class;", new int[] { 3, 5 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
        mv.visitTypeInsn(INSN_CHECK_CAST, 0, 4, 0, typeDescriptorForClass(Venera.class));
        Label l3 = new Label();
        mv.visitJumpInsn(INSN_IF_EQZ, l3, 4, 0);
        mv.visitMethodInsn(INSN_INVOKE_INTERFACE, typeDescriptorForClass(Venera.class), "instrumentationPolicy", typeDescriptorForClass(InstrumentationPolicy.class), new int[] { 4 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
        mv.visitFieldInsn(INSN_SGET_OBJECT, typeDescriptorForClass(InstrumentationPolicy.class), "NONE", typeDescriptorForClass(InstrumentationPolicy.class), 6, 0);
        mv.visitJumpInsn(INSN_IF_NE, l3, 5, 6);
        Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitInsn(INSN_RETURN_VOID);
        mv.visitLabel(l3);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/os/Environment;", "getExternalStorageDirectory", "Ljava/io/File;", new int[] {  });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 1);
        mv.visitTypeInsn(INSN_NEW_INSTANCE, 2, 0, 0, typeDescriptorForClass(JsonLogger.class));
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/reflect/Method;", "getName", "Ljava/lang/String;", new int[] { 3 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, typeDescriptorForClass(JsonLogger.class), "<init>", "VLjava/io/File;Ljava/lang/String;", new int[] { 2, 1, 5 });
        mv.visitTypeInsn(INSN_NEW_INSTANCE, 5, 0, 0, "Ljava/lang/Thread;");
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Thread;", "<init>", "VLjava/lang/Runnable;", new int[] { 5, 2 });
        mv.visitFieldInsn(INSN_SPUT_OBJECT, className, "logThread", "Ljava/lang/Thread;", 5, 0);
        mv.visitFieldInsn(INSN_SGET_OBJECT, className, "logThread", "Ljava/lang/Thread;", 5, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Thread;", "start", "V", new int[] { 5 });
        mv.visitVarInsn(INSN_CONST_4, 5, 1);
        mv.visitFieldInsn(INSN_IPUT_BOOLEAN, className, "logging", "Z", 5, 8);
        mv.visitLabel(l1);
        mv.visitJumpInsn(INSN_GOTO, l4, 0, 0);
        mv.visitLabel(l2);
        mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/NoSuchMethodException;", "printStackTrace", "V", new int[] { 0 });
        mv.visitJumpInsn(INSN_GOTO, l4, 0, 0);
        mv.visitEnd();
    }

    private void addEndLoggingMethod() {
        MethodVisitor mv = cv.visitMethod(ACC_PRIVATE, "endLogging", "V", null, null);
        mv.visitCode();
        mv.visitMaxs(4, 0);
        mv.visitFieldInsn(INSN_IGET_BOOLEAN, className, "logging", "Z", 1, 3);
        Label l0 = new Label();
        mv.visitJumpInsn(INSN_IF_NEZ, l0, 1, 0);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitInsn(INSN_RETURN_VOID);
        mv.visitLabel(l0);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, typeDescriptorForClass(JsonLogger.class), "endLogging", "V", new int[] {  });
        Label l2 = new Label();
        mv.visitLabel(l2);
        Label l3 = new Label();
        Label l4 = new Label();
        mv.visitTryCatchBlock(l2, l3, l4, "Ljava/lang/InterruptedException;");
        mv.visitFieldInsn(INSN_SGET_OBJECT, className, "logThread", "Ljava/lang/Thread;", 1, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Thread;", "join", "V", new int[] { 1 });
        mv.visitLabel(l3);
        mv.visitVarInsn(INSN_CONST_4, 1, 0);
        mv.visitFieldInsn(INSN_IPUT_BOOLEAN, className, "logging", "Z", 1, 3);
        mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
        mv.visitLabel(l4);
        mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
        mv.visitStringInsn(INSN_CONST_STRING, 1, "HeisentestLogger");
        mv.visitStringInsn(INSN_CONST_STRING, 2, "Failed to complete logging");
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;", new int[] { 1, 2, 0 });
        mv.visitJumpInsn(INSN_GOTO, l3, 0, 0);
        mv.visitEnd();
    }
}
