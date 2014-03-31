package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import com.heisentest.splatter.transform.dex.visitor.noop.SplatterNoOpMethodVisitor;
import com.heisentest.splatter.transform.dex.visitor.test.SplatterLoggingInitializationMethodVisitor;
import com.heisentest.splatter.transform.dex.visitor.test.SplatterLoggingTearDownMethodVisitor;
import com.heisentest.splatter.transform.dex.visitor.test.SplatterLoggingSetUpMethodVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.*;
import org.ow2.asmdex.structureCommon.Label;

import java.util.ArrayList;
import java.util.Arrays;

import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingClassVisitor extends ClassVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingClassVisitor.class);
    private String className;
    private final InstrumentationSpy instrumentationSpy;

    // We ignore the constructors for now, since they are special (i.e. 'this' is not yet initialized).
    // See: http://stackoverflow.com/a/8517155
    // TODO: Is there a way to figure out the runtime type name of the object at this point?
    // TODO: If not, we could probably deal with these similarly to static methods (don't rely on 'this'
    // TODO: for the class name (although that would suck a bit).
    // Ignoring 'run' since runnables seem to generate bad dex files for some reason. need to fix this.
    // Ignoring 'toString' since calling 'this.toString' then causes a stack overflow :D
    // Ignoring 'hashCode' as it's causing stack overflows for some reason.
    private final ArrayList<String> blacklistedNames = new ArrayList<String>(Arrays.asList("<init>", "<clinit>", "run", "toString", "hashCode", "wakeUpDevice"));

    /**
     * We don't want to instrument any auto-generated enclosing accessor methods (signature access$0,
     * access$1 etc.), so we ignore any methods with '$' in their name.
     * See: http://www.retrologic.com/innerclasses.doc7.html
      */
    private final String bannedAutoAccessMethodCharacter = "$";

    public SplatterLoggingClassVisitor(int asmApiLevel, ClassVisitor classVisitor, String className, InstrumentationSpy instrumentationSpy) {
        super(asmApiLevel, classVisitor);
        this.className = className;
        this.instrumentationSpy = instrumentationSpy;

        if (this.instrumentationSpy.isBaseTestCaseClass(this.className)) {
            {
                FieldVisitor fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "logThread", "Ljava/lang/Thread;", null, null);
                fv.visitEnd();
            }
            {
                FieldVisitor fv = cv.visitField(ACC_PUBLIC, "splatterIgnoreMethodRule", "Lcom/heisentest/splatter/sdk/SplatterIgnoreMethodRule;", null, null);
                {
                    AnnotationVisitor av0 = fv.visitAnnotation("Lorg/junit/Rule;", true);
                    av0.visitEnd();
                }
                fv.visitEnd();
            }
        }
    }

    @Override
    public void visitEnd() {
        if (instrumentationSpy.isBaseTestCaseClass(this.className)) {
            addStartLoggingMethod();
            addEndLoggingMethod();
        }

        super.visitEnd();
    }

    private void addStartLoggingMethod() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "startLogging", "V", null, null);
        mv.visitCode();
        mv.visitMaxs(8, 0);
        mv.visitFieldInsn(INSN_IGET_OBJECT, instrumentationSpy.getBaseTestCaseClassName(), "splatterIgnoreMethodRule", "Lcom/heisentest/splatter/sdk/SplatterIgnoreMethodRule;", 4, 7);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Lcom/heisentest/splatter/sdk/SplatterIgnoreMethodRule;", "currentTestIsIgnored", "Z", new int[] { 4 });
        mv.visitIntInsn(INSN_MOVE_RESULT, 4);
        Label l0 = new Label();
        mv.visitJumpInsn(INSN_IF_EQZ, l0, 4, 0);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitInsn(INSN_RETURN_VOID);
        mv.visitLabel(l0);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/os/Environment;", "getExternalStorageDirectory", "Ljava/io/File;", new int[] {  });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 1);
        Label l2 = new Label();
        mv.visitLabel(l2);
        Label l3 = new Label();
        Label l4 = new Label();
        mv.visitTryCatchBlock(l2, l3, l4, "Ljava/lang/NoSuchMethodException;");
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "getClass", "Ljava/lang/Class;", new int[] { 7 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, instrumentationSpy.getBaseTestCaseClassName(), "getName", "Ljava/lang/String;", new int[] { 7 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 6);
        mv.visitVarInsn(INSN_CONST_4, 4, 0);
        mv.visitTypeInsn(INSN_CHECK_CAST, 0, 4, 0, "[Ljava/lang/Class;");
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Class;", "getMethod", "Ljava/lang/reflect/Method;Ljava/lang/String;[Ljava/lang/Class;", new int[] { 5, 6, 4 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 3);
        mv.visitTypeInsn(INSN_NEW_INSTANCE, 2, 0, 0, "Lcom/heisentest/skeletonandroidapp/HeisentestJsonLogger;");
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/reflect/Method;", "getName", "Ljava/lang/String;", new int[] { 3 });
        mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Lcom/heisentest/skeletonandroidapp/HeisentestJsonLogger;", "<init>", "VLjava/io/File;Ljava/lang/String;", new int[] { 2, 1, 4 });
        mv.visitTypeInsn(INSN_NEW_INSTANCE, 4, 0, 0, "Ljava/lang/Thread;");
        mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Thread;", "<init>", "VLjava/lang/Runnable;", new int[] { 4, 2 });
        mv.visitFieldInsn(INSN_SPUT_OBJECT, instrumentationSpy.getBaseTestCaseClassName(), "logThread", "Ljava/lang/Thread;", 4, 0);
        mv.visitFieldInsn(INSN_SGET_OBJECT, instrumentationSpy.getBaseTestCaseClassName(), "logThread", "Ljava/lang/Thread;", 4, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Thread;", "start", "V", new int[] { 4 });
        mv.visitLabel(l3);
        mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
        mv.visitLabel(l4);
        mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/NoSuchMethodException;", "printStackTrace", "V", new int[] { 0 });
        mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
        mv.visitEnd();
    }

    private void addEndLoggingMethod() {
        MethodVisitor mv = cv.visitMethod(ACC_PRIVATE, "endLogging", "V", null, null);
        mv.visitCode();
        mv.visitMaxs(4, 0);
        mv.visitFieldInsn(INSN_IGET_OBJECT, instrumentationSpy.getBaseTestCaseClassName(), "splatterIgnoreMethodRule", "Lcom/heisentest/splatter/sdk/SplatterIgnoreMethodRule;", 1, 3);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Lcom/heisentest/splatter/sdk/SplatterIgnoreMethodRule;", "currentTestIsIgnored", "Z", new int[] { 1 });
        mv.visitIntInsn(INSN_MOVE_RESULT, 1);
        Label l0 = new Label();
        mv.visitJumpInsn(INSN_IF_EQZ, l0, 1, 0);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitInsn(INSN_RETURN_VOID);
        mv.visitLabel(l0);
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestJsonLogger;", "endLogging", "V", new int[] {  });
        Label l2 = new Label();
        mv.visitLabel(l2);
        Label l3 = new Label();
        Label l4 = new Label();
        mv.visitTryCatchBlock(l2, l3, l4, "Ljava/lang/InterruptedException;");
        mv.visitFieldInsn(INSN_SGET_OBJECT, instrumentationSpy.getBaseTestCaseClassName(), "logThread", "Ljava/lang/Thread;", 1, 0);
        mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Thread;", "join", "V", new int[] { 1 });
        mv.visitLabel(l3);
        mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
        mv.visitLabel(l4);
        mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
        mv.visitStringInsn(INSN_CONST_STRING, 1, "HeisentestLogger");
        mv.visitStringInsn(INSN_CONST_STRING, 2, "Failed to complete logging");
        mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;", new int[] { 1, 2, 0 });
        mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
        mv.visitEnd();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

        // TODO: Document this whitelist.
        boolean instrument = false;
        ArrayList<String> allowedPrefixes = new ArrayList<>(Arrays.asList("get", "set", "tile", "on", "create", ""));
        for (String prefix : allowedPrefixes) {
            if (name.startsWith(prefix) && instrumentationSpy.shouldInstrument(className, name)) {
                instrument = true;
                break;
            }
        }

        boolean isStatic = isStatic(access);
        if (instrumentationSpy.isBaseTestClassInitMethod(className, name)) {
            logger.debug(String.format("Adding HeisentestLogger initialization to method (name: '%s') (desc: '%s') (class: '%s')", name, desc, className));
            return new SplatterLoggingInitializationMethodVisitor(api, methodVisitor, desc, isStatic, instrumentationSpy);
        } else if (instrumentationSpy.isBaseTestCaseSetUpMethod(className, name)) {
            logger.debug(String.format("Adding HeisentestLogger set up to method (name: '%s') (desc: '%s') (class: '%s')", name, desc, className));
            return new SplatterLoggingSetUpMethodVisitor(api, methodVisitor, desc, isStatic, instrumentationSpy);
        } else if (instrumentationSpy.isBaseTestCaseTearDownMethod(className, name)) {
            logger.debug(String.format("Adding HeisentestLogger tear down to method (name: '%s') (desc: '%s') (class: '%s')", name, desc, className));
            return new SplatterLoggingTearDownMethodVisitor(api, methodVisitor, desc, isStatic, instrumentationSpy);
        } else if (shouldInstrumentMethod(access, name, instrument)) {
            logger.debug(String.format("Adding HeisentestLogger to method (name: '%s') (desc: '%s') (class: '%s') (access (opcode): '%s')", name, desc, className, access));
            return new SplatterLoggingMethodVisitor(api, methodVisitor, desc, name, isStatic);
        }

        logger.debug(String.format("SKIPPING method (name: '%s') (desc: '%s') (class: '%s') (access (opcode): '%s')", name, desc, className, access));
        return new SplatterNoOpMethodVisitor(api, methodVisitor);
    }

    private boolean shouldInstrumentMethod(int access, String name, boolean instrument) {
        return instrument && !isAbstract(access) && !blacklistedNames.contains(name) && !name.contains(bannedAutoAccessMethodCharacter);
    }

    private boolean isStatic(int access) {
        return (access & ACC_STATIC) > 0;
    }

    // TODO: Check this returns correctly.
    private boolean isAbstract(int access) {
        return (access & Opcodes.ACC_ABSTRACT) != 0;
    }
}
