package com.heisentest.splatter;

import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.FieldVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.structureCommon.Label;

import static org.ow2.asmdex.Opcodes.*;

public class HeisentestXmlLoggerClassWriter {

    public void addLogClass(ApplicationVisitor applicationVisitor) {
        ClassVisitor cv = applicationVisitor.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", null, "Ljava/lang/Object;", null);
        cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", null, "Ljava/lang/Object;", null);
        cv.visitSource("HeisentestXmlLogger.java", null);
        {
            FieldVisitor fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "DEFAULT_OUTPUT_LOCATION", "Ljava/lang/String;", null, "heisentestoutput.xml");
            fv.visitEnd();
        }
        {
            FieldVisitor fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "HEISENTEST_LOGGER_TAG", "Ljava/lang/String;", null, "HeisentestLogger");
            fv.visitEnd();
        }
        {
            FieldVisitor fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "HEISENTEST_NAMESPACE", "Ljava/lang/String;", null, "heisentest");
            fv.visitEnd();
        }
        {
            FieldVisitor fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "NO_NAMESPACE", "Ljava/lang/String;", null, "");
            fv.visitEnd();
        }
        {
            FieldVisitor fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "serializer", "Lorg/xmlpull/v1/XmlSerializer;", null, null);
            fv.visitEnd();
        }
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
            mv.visitCode();
            mv.visitMaxs(1, 0);
            mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
            mv.visitInsn(INSN_RETURN_VOID);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "beginLogging", "V", null, null);
            mv.visitCode();
            mv.visitMaxs(5, 0);
            Label l0 = new Label();
            mv.visitLabel(l0);
            Label l1 = new Label();
            Label l2 = new Label();
            mv.visitTryCatchBlock(l0, l1, l2, "Ljava/io/IOException;");
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 2, "No encoding");
            mv.visitVarInsn(INSN_CONST_4, 3, 1);
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/Boolean;", "valueOf", "Ljava/lang/Boolean;Z", new int[] { 3 });
            mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 3);
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startDocument", "VLjava/lang/String;Ljava/lang/Boolean;", new int[] { 1, 2, 3 });
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 2, "heisentest");
            mv.visitStringInsn(INSN_CONST_STRING, 3, "version");
            mv.visitStringInsn(INSN_CONST_STRING, 4, "0.0.1");
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "attribute", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;", new int[] { 1, 2, 3, 4 });
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 2, "");
            mv.visitStringInsn(INSN_CONST_STRING, 3, "log");
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 1, 2, 3 });
            mv.visitLabel(l1);
            mv.visitInsn(INSN_RETURN_VOID);
            mv.visitLabel(l2);
            mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 1, "HeisentestLogger");
            mv.visitStringInsn(INSN_CONST_STRING, 2, "Failed to begin logging");
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;", new int[] { 1, 2, 0 });
            mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "endLogging", "V", null, null);
            mv.visitCode();
            mv.visitMaxs(4, 0);
            Label l0 = new Label();
            mv.visitLabel(l0);
            Label l1 = new Label();
            Label l2 = new Label();
            mv.visitTryCatchBlock(l0, l1, l2, "Ljava/io/IOException;");
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 2, "heisentest");
            mv.visitStringInsn(INSN_CONST_STRING, 3, "log");
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 1, 2, 3 });
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endDocument", "V", new int[] { 1 });
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "flush", "V", new int[] { 1 });
            mv.visitLabel(l1);
            mv.visitInsn(INSN_RETURN_VOID);
            mv.visitLabel(l2);
            mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 1, "HeisentestLogger");
            mv.visitStringInsn(INSN_CONST_STRING, 2, "Failed to end logging");
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;", new int[] { 1, 2, 0 });
            mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "init", "V", null, null);
            mv.visitCode();
            mv.visitMaxs(4, 0);
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Xml;", "newSerializer", "Lorg/xmlpull/v1/XmlSerializer;", new int[] {  });
            mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);
            mv.visitFieldInsn(INSN_SPUT_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 2, 0);
            Label l0 = new Label();
            mv.visitLabel(l0);
            Label l1 = new Label();
            Label l2 = new Label();
            mv.visitTryCatchBlock(l0, l1, l2, "Ljava/io/IOException;");
            mv.visitTypeInsn(INSN_NEW_INSTANCE, 1, 0, 0, "Ljava/io/FileWriter;");
            mv.visitStringInsn(INSN_CONST_STRING, 2, "heisentestoutput.xml");
            mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/io/FileWriter;", "<init>", "VLjava/lang/String;", new int[] { 1, 2 });
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 2, 0);
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "setOutput", "VLjava/io/Writer;", new int[] { 2, 1 });
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "beginLogging", "V", new int[] {  });
            mv.visitLabel(l1);
            mv.visitInsn(INSN_RETURN_VOID);
            mv.visitLabel(l2);
            mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 2, "HeisentestLogger");
            mv.visitStringInsn(INSN_CONST_STRING, 3, "Failed to create output file");
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;", new int[] { 2, 3, 0 });
            mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
            mv.visitEnd();
        }
        {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC + ACC_TRANSIENT, "log", "VLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", null, null);
            mv.visitCode();
            mv.visitMaxs(12, 0);
            Label l0 = new Label();
            mv.visitLabel(l0);
            Label l1 = new Label();
            Label l2 = new Label();
            mv.visitTryCatchBlock(l0, l1, l2, "Ljava/io/IOException;");
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 5, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 6, "");
            mv.visitStringInsn(INSN_CONST_STRING, 7, "event");
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 5, 6, 7 });
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 5, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 6, "");
            mv.visitStringInsn(INSN_CONST_STRING, 7, "class");
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "attribute", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;", new int[] { 5, 6, 7, 9 });
            mv.visitArrayLengthInsn(5, 11);
            Label l3 = new Label();
            mv.visitJumpInsn(INSN_IF_LEZ, l3, 5, 0);
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 5, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 6, "Method");
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 5, 6, 10 });
            mv.visitVarInsn(INSN_MOVE_OBJECT, 0, 11);
            mv.visitArrayLengthInsn(3, 0);
            mv.visitVarInsn(INSN_CONST_4, 2, 0);
            Label l4 = new Label();
            mv.visitLabel(l4);
            Label l5 = new Label();
            mv.visitJumpInsn(INSN_IF_GE, l5, 2, 3);
            mv.visitArrayOperationInsn(INSN_AGET_OBJECT, 4, 0, 2);
            Label l6 = new Label();
            mv.visitJumpInsn(INSN_IF_EQZ, l6, 4, 0);
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 5, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 6, "");
            mv.visitStringInsn(INSN_CONST_STRING, 7, "Parameter");
            mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { 4 });
            mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 8);
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "attribute", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;", new int[] { 5, 6, 7, 8 });
            mv.visitLabel(l6);
            mv.visitOperationInsn(INSN_ADD_INT_LIT8, 2, 2, 0, 1);
            mv.visitJumpInsn(INSN_GOTO, l4, 0, 0);
            mv.visitLabel(l5);
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 5, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 6, "Method");
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 5, 6, 10 });
            Label l7 = new Label();
            mv.visitLabel(l7);
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 5, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 6, "");
            mv.visitStringInsn(INSN_CONST_STRING, 7, "event");
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 5, 6, 7 });
            Label l8 = new Label();
            mv.visitLabel(l8);
            mv.visitInsn(INSN_RETURN_VOID);
            mv.visitLabel(l3);
            mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 5, 0);
            mv.visitStringInsn(INSN_CONST_STRING, 6, "");
            mv.visitStringInsn(INSN_CONST_STRING, 7, "Method");
            mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "attribute", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;", new int[] { 5, 6, 7, 10 });
            mv.visitLabel(l1);
            mv.visitJumpInsn(INSN_GOTO, l7, 0, 0);
            mv.visitLabel(l2);
            mv.visitIntInsn(INSN_MOVE_EXCEPTION, 1);
            mv.visitStringInsn(INSN_CONST_STRING, 5, "HeisentestLogger");
            mv.visitStringInsn(INSN_CONST_STRING, 6, "Failed to write event to XML");
            mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;", new int[] { 5, 6, 1 });
            mv.visitJumpInsn(INSN_GOTO, l8, 0, 0);
            mv.visitEnd();
        }
        cv.visitEnd();
    }
}
