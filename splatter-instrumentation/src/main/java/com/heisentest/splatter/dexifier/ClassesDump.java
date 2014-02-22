package com.heisentest.splatter.dexifier;

import java.util.*;
import org.ow2.asmdex.*;
import org.ow2.asmdex.structureCommon.*;

public class ClassesDump implements Opcodes {

public static byte[] dump() throws Exception {

	ApplicationWriter aw = new ApplicationWriter();
	aw.visit();

	dumpBuildConfig(aw);
	dumpR$attr(aw);
	dumpR$dimen(aw);
	dumpR$drawable(aw);
	dumpR$id(aw);
	dumpR$layout(aw);
	dumpR$string(aw);
	dumpR$style(aw);
	dumpR(aw);
	dumpAnInstrumentationTestCase(aw);
	dumpHeisentestLogger(aw);
	dumpHeisentestXmlLogger(aw);
	dumpMainActivity(aw);

	aw.visitEnd();

	return aw.toByteArray();
}

public static void dumpBuildConfig(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/BuildConfig;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/BuildConfig;", null, "Ljava/lang/Object;", null);
	cv.visitSource("BuildConfig.java", null);
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "BUILD_TYPE", "Ljava/lang/String;", null, "debug");
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "DEBUG", "Z", null, false);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "FLAVOR", "Ljava/lang/String;", null, "");
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "PACKAGE_NAME", "Ljava/lang/String;", null, "com.example.android_source");
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "VERSION_CODE", "I", null, 1);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "VERSION_NAME", "Ljava/lang/String;", null, "1.0");
		fv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_STATIC + ACC_CONSTRUCTOR, "<clinit>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 0, "true");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/Boolean;", "parseBoolean", "ZLjava/lang/String;", new int[] { 0 });
		mv.visitIntInsn(INSN_MOVE_RESULT, 0);
		mv.visitFieldInsn(INSN_SPUT_BOOLEAN, "Lcom/example/android_source/BuildConfig;", "DEBUG", "Z", 0, 0);
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpR$attr(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$attr;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$attr;", null, "Ljava/lang/Object;", null);
	cv.visitSource("R.java", null);
	cv.visitInnerClass("Lcom/example/android_source/R$attr;", "Lcom/example/android_source/R;", "attr", ACC_PUBLIC + ACC_STATIC + ACC_FINAL);
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpR$dimen(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$dimen;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$dimen;", null, "Ljava/lang/Object;", null);
	cv.visitSource("R.java", null);
	cv.visitInnerClass("Lcom/example/android_source/R$dimen;", "Lcom/example/android_source/R;", "dimen", ACC_PUBLIC + ACC_STATIC + ACC_FINAL);
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "activity_horizontal_margin", "I", null, 2130968576);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "activity_vertical_margin", "I", null, 2130968577);
		fv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpR$drawable(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$drawable;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$drawable;", null, "Ljava/lang/Object;", null);
	cv.visitSource("R.java", null);
	cv.visitInnerClass("Lcom/example/android_source/R$drawable;", "Lcom/example/android_source/R;", "drawable", ACC_PUBLIC + ACC_STATIC + ACC_FINAL);
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "ic_launcher", "I", null, 2130837504);
		fv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpR$id(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$id;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$id;", null, "Ljava/lang/Object;", null);
	cv.visitSource("R.java", null);
	cv.visitInnerClass("Lcom/example/android_source/R$id;", "Lcom/example/android_source/R;", "id", ACC_PUBLIC + ACC_STATIC + ACC_FINAL);
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "container", "I", null, 2131165185);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "drawer_layout", "I", null, 2131165184);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "section_label", "I", null, 2131165186);
		fv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpR$layout(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$layout;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$layout;", null, "Ljava/lang/Object;", null);
	cv.visitSource("R.java", null);
	cv.visitInnerClass("Lcom/example/android_source/R$layout;", "Lcom/example/android_source/R;", "layout", ACC_PUBLIC + ACC_STATIC + ACC_FINAL);
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "activity_main", "I", null, 2130903040);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "fragment_main", "I", null, 2130903041);
		fv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpR$string(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$string;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$string;", null, "Ljava/lang/Object;", null);
	cv.visitSource("R.java", null);
	cv.visitInnerClass("Lcom/example/android_source/R$string;", "Lcom/example/android_source/R;", "string", ACC_PUBLIC + ACC_STATIC + ACC_FINAL);
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "app_name", "I", null, 2131034112);
		fv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpR$style(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$style;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R$style;", null, "Ljava/lang/Object;", null);
	cv.visitSource("R.java", null);
	cv.visitInnerClass("Lcom/example/android_source/R$style;", "Lcom/example/android_source/R;", "style", ACC_PUBLIC + ACC_STATIC + ACC_FINAL);
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "AppTheme", "I", null, 2131099648);
		fv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpR(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/example/android_source/R;", null, "Ljava/lang/Object;", null);
	cv.visitSource("R.java", null);
	cv.visitMemberClass("Lcom/example/android_source/R$style;", "Lcom/example/android_source/R;", "style");
	cv.visitMemberClass("Lcom/example/android_source/R$string;", "Lcom/example/android_source/R;", "string");
	cv.visitMemberClass("Lcom/example/android_source/R$layout;", "Lcom/example/android_source/R;", "layout");
	cv.visitMemberClass("Lcom/example/android_source/R$id;", "Lcom/example/android_source/R;", "id");
	cv.visitMemberClass("Lcom/example/android_source/R$drawable;", "Lcom/example/android_source/R;", "drawable");
	cv.visitMemberClass("Lcom/example/android_source/R$dimen;", "Lcom/example/android_source/R;", "dimen");
	cv.visitMemberClass("Lcom/example/android_source/R$attr;", "Lcom/example/android_source/R;", "attr");
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpAnInstrumentationTestCase(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC, "Lcom/heisentest/skeletonandroidapp/AnInstrumentationTestCase;", new String[] { "<T:", "Landroid/app/Activity;", ">", "Landroid/test/ActivityInstrumentationTestCase2", "<TT;>;" }, "Landroid/test/ActivityInstrumentationTestCase2;", null);
	cv.visit(0, ACC_PUBLIC, "Lcom/heisentest/skeletonandroidapp/AnInstrumentationTestCase;", new String[] { "<T:", "Landroid/app/Activity;", ">", "Landroid/test/ActivityInstrumentationTestCase2", "<TT;>;" }, "Landroid/test/ActivityInstrumentationTestCase2;", null);
	cv.visitSource("AnInstrumentationTestCase.java", null);
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "VLjava/lang/Class;", new String[] { "(", "Ljava/lang/Class", "<TT;>;)V" }, null);
		mv.visitCode();
		mv.visitMaxs(2, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Landroid/test/ActivityInstrumentationTestCase2;", "<init>", "VLjava/lang/Class;", new int[] { 0, 1 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PROTECTED, "setUp", "V", null, new String[] { "Ljava/lang/Exception;" });
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_SUPER, "Landroid/test/ActivityInstrumentationTestCase2;", "setUp", "V", new int[] { 0 });
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Lcom/heisentest/skeletonandroidapp/AnInstrumentationTestCase;", "startLogging", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC, "startLogging", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(7, 0);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/os/Environment;", "getExternalStorageDirectory", "Ljava/io/File;", new int[] {  });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 1);
		Label l0 = new Label();
		mv.visitLabel(l0);
		Label l1 = new Label();
		Label l2 = new Label();
		mv.visitTryCatchBlock(l0, l1, l2, "Ljava/lang/NoSuchMethodException;");
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "getClass", "Ljava/lang/Class;", new int[] { 6 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Lcom/heisentest/skeletonandroidapp/AnInstrumentationTestCase;", "getName", "Ljava/lang/String;", new int[] { 6 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
		mv.visitVarInsn(INSN_CONST_4, 3, 0);
		mv.visitTypeInsn(INSN_CHECK_CAST, 0, 3, 0, "[Ljava/lang/Class;");
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Class;", "getMethod", "Ljava/lang/reflect/Method;Ljava/lang/String;[Ljava/lang/Class;", new int[] { 4, 5, 3 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/reflect/Method;", "getName", "Ljava/lang/String;", new int[] { 2 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 3);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "init", "VLjava/io/File;Ljava/lang/String;", new int[] { 1, 3 });
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "beginLogging", "V", new int[] {  });
		mv.visitLabel(l1);
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitLabel(l2);
		mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/NoSuchMethodException;", "printStackTrace", "V", new int[] { 0 });
		mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC, "tearDown", "V", null, new String[] { "Ljava/lang/Exception;" });
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_SUPER, "Landroid/test/ActivityInstrumentationTestCase2;", "tearDown", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpHeisentestLogger(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", null, "Ljava/lang/Object;", null);
	cv.visitSource("HeisentestLogger.java", null);
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC + ACC_TRANSIENT, "log", "VLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", null, null);
		mv.visitCode();
		mv.visitMaxs(14, 0);
		mv.visitVarInsn(INSN_CONST_4, 10, 2);
		mv.visitVarInsn(INSN_CONST_4, 9, 1);
		mv.visitVarInsn(INSN_CONST_4, 8, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 4, "");
		mv.visitVarInsn(INSN_MOVE_OBJECT, 0, 13);
		mv.visitArrayLengthInsn(2, 0);
		mv.visitVarInsn(INSN_CONST_4, 1, 0);
		Label l0 = new Label();
		mv.visitLabel(l0);
		Label l1 = new Label();
		mv.visitJumpInsn(INSN_IF_GE, l1, 1, 2);
		mv.visitArrayOperationInsn(INSN_AGET_OBJECT, 3, 0, 1);
		Label l2 = new Label();
		mv.visitJumpInsn(INSN_IF_EQZ, l2, 3, 0);
		mv.visitTypeInsn(INSN_NEW_INSTANCE, 5, 0, 0, "Ljava/lang/StringBuilder;");
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/StringBuilder;", "<init>", "V", new int[] { 5 });
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { 3 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 6);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuilder;", "append", "Ljava/lang/StringBuilder;Ljava/lang/String;", new int[] { 5, 6 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
		mv.visitStringInsn(INSN_CONST_STRING, 6, ", ");
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuilder;", "append", "Ljava/lang/StringBuilder;Ljava/lang/String;", new int[] { 5, 6 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuilder;", "toString", "Ljava/lang/String;", new int[] { 5 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/String;", "concat", "Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
		mv.visitLabel(l2);
		mv.visitOperationInsn(INSN_ADD_INT_LIT8, 1, 1, 0, 1);
		mv.visitJumpInsn(INSN_GOTO, l0, 0, 0);
		mv.visitLabel(l1);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/text/TextUtils;", "isEmpty", "ZLjava/lang/CharSequence;", new int[] { 4 });
		mv.visitIntInsn(INSN_MOVE_RESULT, 5);
		Label l3 = new Label();
		mv.visitJumpInsn(INSN_IF_NEZ, l3, 5, 0);
		mv.visitVarInsn(INSN_CONST_16, 5, 44);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/String;", "lastIndexOf", "II", new int[] { 4, 5 });
		mv.visitIntInsn(INSN_MOVE_RESULT, 5);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/String;", "substring", "Ljava/lang/String;II", new int[] { 4, 8, 5 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
		mv.visitLabel(l3);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/text/TextUtils;", "isEmpty", "ZLjava/lang/CharSequence;", new int[] { 4 });
		mv.visitIntInsn(INSN_MOVE_RESULT, 5);
		Label l4 = new Label();
		mv.visitJumpInsn(INSN_IF_EQZ, l4, 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 5, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 6, "Class -  %s, Method - %s");
		mv.visitTypeInsn(INSN_NEW_ARRAY, 7, 0, 10, "[Ljava/lang/Object;");
		mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 11, 7, 8);
		mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 12, 7, 9);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/String;", "format", "Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 6, 7 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 6);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 5, 6 });
		Label l5 = new Label();
		mv.visitLabel(l5);
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitLabel(l4);
		mv.visitStringInsn(INSN_CONST_STRING, 5, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 6, "Class -  %s, Method - %s, Parameters - %s");
		mv.visitVarInsn(INSN_CONST_4, 7, 3);
		mv.visitTypeInsn(INSN_NEW_ARRAY, 7, 0, 7, "[Ljava/lang/Object;");
		mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 11, 7, 8);
		mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 12, 7, 9);
		mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 4, 7, 10);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/String;", "format", "Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 6, 7 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 6);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 5, 6 });
		mv.visitJumpInsn(INSN_GOTO, l5, 0, 0);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpHeisentestXmlLogger(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC + ACC_FINAL, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", null, "Ljava/lang/Object;", null);
	cv.visit(0, ACC_PUBLIC + ACC_FINAL, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", null, "Ljava/lang/Object;", null);
	cv.visitSource("HeisentestXmlLogger.java", null);
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "DEFAULT_OUTPUT_LOCATION", "Ljava/lang/String;", null, "/heisentestoutput");
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "HEISENTEST_LOGGER_TAG", "Ljava/lang/String;", null, "HeisentestLogger");
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "HEISENTEST_NAMESPACE", "Ljava/lang/String;", null, "heisentest");
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "NO_NAMESPACE", "Ljava/lang/String;", null, null);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "fileWriter", "Ljava/io/FileWriter;", null, null);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "outputFile", "Ljava/io/File;", null, null);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "serializer", "Lorg/xmlpull/v1/XmlSerializer;", null, null);
		fv.visitEnd();
	}
	{
		fv = cv.visitField(ACC_PRIVATE + ACC_STATIC, "stringWriter", "Ljava/io/StringWriter;", null, null);
		fv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_STATIC + ACC_CONSTRUCTOR, "<clinit>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitVarInsn(INSN_CONST_4, 0, 0);
		mv.visitFieldInsn(INSN_SPUT_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 0, 0);
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/Object;", "<init>", "V", new int[] { 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "beginLogging", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(5, 0);
		Label l0 = new Label();
		mv.visitLabel(l0);
		Label l1 = new Label();
		Label l2 = new Label();
		mv.visitTryCatchBlock(l0, l1, l2, "Ljava/io/IOException;");
		mv.visitStringInsn(INSN_CONST_STRING, 1, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 2, "Trying to begin log...");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "i", "ILjava/lang/String;Ljava/lang/String;", new int[] { 1, 2 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 2, "http://xmlpull.org/v1/doc/features.html#indent-output");
		mv.visitVarInsn(INSN_CONST_4, 3, 1);
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "setFeature", "VLjava/lang/String;Z", new int[] { 1, 2, 3 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 2, "UTF-8");
		mv.visitVarInsn(INSN_CONST_4, 3, 1);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/Boolean;", "valueOf", "Ljava/lang/Boolean;Z", new int[] { 3 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 3);
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startDocument", "VLjava/lang/String;Ljava/lang/Boolean;", new int[] { 1, 2, 3 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 2, "heisentest");
		mv.visitStringInsn(INSN_CONST_STRING, 3, "log");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 1, 2, 3 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 1, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 2, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 3, "version");
		mv.visitStringInsn(INSN_CONST_STRING, 4, "0.0.1");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "attribute", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;", new int[] { 1, 2, 3, 4 });
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
		mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "endLogging", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(9, 0);
		Label l0 = new Label();
		mv.visitLabel(l0);
		Label l1 = new Label();
		Label l2 = new Label();
		mv.visitTryCatchBlock(l0, l1, l2, "Ljava/io/IOException;");
		mv.visitStringInsn(INSN_CONST_STRING, 4, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 5, "Trying to end log...");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 4, 5 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 5, "heisentest");
		mv.visitStringInsn(INSN_CONST_STRING, 6, "log");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endDocument", "V", new int[] { 4 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "flush", "V", new int[] { 4 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "fileWriter", "Ljava/io/FileWriter;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "stringWriter", "Ljava/io/StringWriter;", 5, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/StringWriter;", "toString", "Ljava/lang/String;", new int[] { 5 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/FileWriter;", "append", "Ljava/io/Writer;Ljava/lang/CharSequence;", new int[] { 4, 5 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "stringWriter", "Ljava/io/StringWriter;", 4, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/StringWriter;", "getBuffer", "Ljava/lang/StringBuffer;", new int[] { 4 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
		mv.visitVarInsn(INSN_CONST_4, 5, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuffer;", "setLength", "VI", new int[] { 4, 5 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "fileWriter", "Ljava/io/FileWriter;", 4, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/FileWriter;", "flush", "V", new int[] { 4 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "fileWriter", "Ljava/io/FileWriter;", 4, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/FileWriter;", "close", "V", new int[] { 4 });
		mv.visitStringInsn(INSN_CONST_STRING, 4, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 5, "Ended log.");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 4, 5 });
		mv.visitStringInsn(INSN_CONST_STRING, 4, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 5, "Setting output outputFile (at '%s') readable...");
		mv.visitVarInsn(INSN_CONST_4, 6, 1);
		mv.visitTypeInsn(INSN_NEW_ARRAY, 6, 0, 6, "[Ljava/lang/Object;");
		mv.visitVarInsn(INSN_CONST_4, 7, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "outputFile", "Ljava/io/File;", 8, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/File;", "getAbsolutePath", "Ljava/lang/String;", new int[] { 8 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 8);
		mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 8, 6, 7);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/String;", "format", "Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 5, 6 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 4, 5 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "outputFile", "Ljava/io/File;", 4, 0);
		mv.visitVarInsn(INSN_CONST_4, 5, 1);
		mv.visitVarInsn(INSN_CONST_4, 6, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/File;", "setReadable", "ZZZ", new int[] { 4, 5, 6 });
		mv.visitIntInsn(INSN_MOVE_RESULT, 3);
		Label l3 = new Label();
		mv.visitJumpInsn(INSN_IF_NEZ, l3, 3, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 4, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 5, "Failed to make output outputDirectory readable!");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;", new int[] { 4, 5 });
		mv.visitLabel(l3);
		mv.visitStringInsn(INSN_CONST_STRING, 4, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 5, "Successfully made output outputDirectory readable. Printing final output:");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 4, 5 });
		mv.visitTypeInsn(INSN_NEW_INSTANCE, 0, 0, 0, "Ljava/io/BufferedReader;");
		mv.visitTypeInsn(INSN_NEW_INSTANCE, 4, 0, 0, "Ljava/io/FileReader;");
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "outputFile", "Ljava/io/File;", 5, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/io/FileReader;", "<init>", "VLjava/io/File;", new int[] { 4, 5 });
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/io/BufferedReader;", "<init>", "VLjava/io/Reader;", new int[] { 0, 4 });
		mv.visitVarInsn(INSN_CONST_4, 2, 0);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/BufferedReader;", "readLine", "Ljava/lang/String;", new int[] { 0 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);
		Label l5 = new Label();
		mv.visitJumpInsn(INSN_IF_EQZ, l5, 2, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 4, "HeisentestLogger");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "i", "ILjava/lang/String;Ljava/lang/String;", new int[] { 4, 2 });
		mv.visitLabel(l1);
		mv.visitJumpInsn(INSN_GOTO, l4, 0, 0);
		mv.visitLabel(l2);
		mv.visitIntInsn(INSN_MOVE_EXCEPTION, 1);
		mv.visitStringInsn(INSN_CONST_STRING, 4, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 5, "Failed to end logging");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;", new int[] { 4, 5, 1 });
		Label l6 = new Label();
		mv.visitLabel(l6);
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitLabel(l5);
		Label l7 = new Label();
		mv.visitTryCatchBlock(l5, l7, l2, "Ljava/io/IOException;");
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/BufferedReader;", "close", "V", new int[] { 0 });
		mv.visitLabel(l7);
		mv.visitJumpInsn(INSN_GOTO, l6, 0, 0);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "init", "VLjava/io/File;Ljava/lang/String;", null, null);
		mv.visitCode();
		mv.visitMaxs(9, 0);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/os/Environment;", "getExternalStorageState", "Ljava/lang/String;", new int[] {  });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);
		mv.visitStringInsn(INSN_CONST_STRING, 3, "mounted");
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/String;", "equals", "ZLjava/lang/Object;", new int[] { 2, 3 });
		mv.visitIntInsn(INSN_MOVE_RESULT, 2);
		Label l0 = new Label();
		mv.visitJumpInsn(INSN_IF_NEZ, l0, 2, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 2, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 3, "MEDIA NOT MOUNTED!");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;", new int[] { 2, 3 });
		mv.visitLabel(l0);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Xml;", "newSerializer", "Lorg/xmlpull/v1/XmlSerializer;", new int[] {  });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);
		mv.visitFieldInsn(INSN_SPUT_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 2, 0);
		Label l1 = new Label();
		mv.visitLabel(l1);
		Label l2 = new Label();
		Label l3 = new Label();
		mv.visitTryCatchBlock(l1, l2, l3, "Ljava/io/IOException;");
		mv.visitTypeInsn(INSN_NEW_INSTANCE, 1, 0, 0, "Ljava/io/File;");
		mv.visitTypeInsn(INSN_NEW_INSTANCE, 2, 0, 0, "Ljava/lang/StringBuilder;");
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/StringBuilder;", "<init>", "V", new int[] { 2 });
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuilder;", "append", "Ljava/lang/StringBuilder;Ljava/lang/Object;", new int[] { 2, 7 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);
		mv.visitStringInsn(INSN_CONST_STRING, 3, "/heisentest");
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuilder;", "append", "Ljava/lang/StringBuilder;Ljava/lang/String;", new int[] { 2, 3 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuilder;", "toString", "Ljava/lang/String;", new int[] { 2 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 2);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/io/File;", "<init>", "VLjava/lang/String;", new int[] { 1, 2 });
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/File;", "mkdirs", "Z", new int[] { 1 });
		mv.visitStringInsn(INSN_CONST_STRING, 2, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 3, "Heisentest output directory: %s");
		mv.visitVarInsn(INSN_CONST_4, 4, 1);
		mv.visitTypeInsn(INSN_NEW_ARRAY, 4, 0, 4, "[Ljava/lang/Object;");
		mv.visitVarInsn(INSN_CONST_4, 5, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/File;", "getAbsolutePath", "Ljava/lang/String;", new int[] { 1 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 6);
		mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 6, 4, 5);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Ljava/lang/String;", "format", "Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 3, 4 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 3);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "d", "ILjava/lang/String;Ljava/lang/String;", new int[] { 2, 3 });
		mv.visitTypeInsn(INSN_NEW_INSTANCE, 2, 0, 0, "Ljava/io/File;");
		mv.visitTypeInsn(INSN_NEW_INSTANCE, 3, 0, 0, "Ljava/lang/StringBuilder;");
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/lang/StringBuilder;", "<init>", "V", new int[] { 3 });
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuilder;", "append", "Ljava/lang/StringBuilder;Ljava/lang/String;", new int[] { 3, 8 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 3);
		mv.visitStringInsn(INSN_CONST_STRING, 4, ".xml");
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuilder;", "append", "Ljava/lang/StringBuilder;Ljava/lang/String;", new int[] { 3, 4 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 3);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuilder;", "toString", "Ljava/lang/String;", new int[] { 3 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 3);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/io/File;", "<init>", "VLjava/io/File;Ljava/lang/String;", new int[] { 2, 1, 3 });
		mv.visitFieldInsn(INSN_SPUT_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "outputFile", "Ljava/io/File;", 2, 0);
		mv.visitTypeInsn(INSN_NEW_INSTANCE, 2, 0, 0, "Ljava/io/FileWriter;");
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "outputFile", "Ljava/io/File;", 3, 0);
		mv.visitVarInsn(INSN_CONST_4, 4, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/io/FileWriter;", "<init>", "VLjava/io/File;Z", new int[] { 2, 3, 4 });
		mv.visitFieldInsn(INSN_SPUT_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "fileWriter", "Ljava/io/FileWriter;", 2, 0);
		mv.visitTypeInsn(INSN_NEW_INSTANCE, 2, 0, 0, "Ljava/io/StringWriter;");
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Ljava/io/StringWriter;", "<init>", "V", new int[] { 2 });
		mv.visitFieldInsn(INSN_SPUT_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "stringWriter", "Ljava/io/StringWriter;", 2, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 2, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "stringWriter", "Ljava/io/StringWriter;", 3, 0);
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "setOutput", "VLjava/io/Writer;", new int[] { 2, 3 });
		mv.visitLabel(l2);
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitLabel(l3);
		mv.visitIntInsn(INSN_MOVE_EXCEPTION, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 2, "HeisentestLogger");
		mv.visitStringInsn(INSN_CONST_STRING, 3, "Failed to create output outputDirectory");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Landroid/util/Log;", "e", "ILjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;", new int[] { 2, 3, 0 });
		mv.visitJumpInsn(INSN_GOTO, l2, 0, 0);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC + ACC_TRANSIENT, "log", "VLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", null, null);
		mv.visitCode();
		mv.visitMaxs(10, 0);
		Label l0 = new Label();
		mv.visitLabel(l0);
		Label l1 = new Label();
		Label l2 = new Label();
		mv.visitTryCatchBlock(l0, l1, l2, "Ljava/io/IOException;");
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "event");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "class");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "name");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "attribute", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6, 7 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "method");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "name");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "attribute", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6, 8 });
		mv.visitArrayLengthInsn(4, 9);
		Label l3 = new Label();
		mv.visitJumpInsn(INSN_IF_LEZ, l3, 4, 0);
		mv.visitVarInsn(INSN_MOVE_OBJECT, 0, 9);
		mv.visitArrayLengthInsn(2, 0);
		mv.visitVarInsn(INSN_CONST_4, 1, 0);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitJumpInsn(INSN_IF_GE, l3, 1, 2);
		mv.visitArrayOperationInsn(INSN_AGET_OBJECT, 3, 0, 1);
		Label l5 = new Label();
		mv.visitJumpInsn(INSN_IF_EQZ, l5, 3, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "parameter");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "startTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { 3 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "text", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;", new int[] { 4, 5 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "parameter");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6 });
		mv.visitLabel(l5);
		mv.visitOperationInsn(INSN_ADD_INT_LIT8, 1, 1, 0, 1);
		mv.visitJumpInsn(INSN_GOTO, l4, 0, 0);
		mv.visitLabel(l3);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "method");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "class");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "NO_NAMESPACE", "Ljava/lang/String;", 5, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 6, "event");
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "endTag", "Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;", new int[] { 4, 5, 6 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "serializer", "Lorg/xmlpull/v1/XmlSerializer;", 4, 0);
		mv.visitMethodInsn(INSN_INVOKE_INTERFACE, "Lorg/xmlpull/v1/XmlSerializer;", "flush", "V", new int[] { 4 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "fileWriter", "Ljava/io/FileWriter;", 4, 0);
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "stringWriter", "Ljava/io/StringWriter;", 5, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/StringWriter;", "toString", "Ljava/lang/String;", new int[] { 5 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 5);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/FileWriter;", "append", "Ljava/io/Writer;Ljava/lang/CharSequence;", new int[] { 4, 5 });
		mv.visitFieldInsn(INSN_SGET_OBJECT, "Lcom/heisentest/skeletonandroidapp/HeisentestXmlLogger;", "stringWriter", "Ljava/io/StringWriter;", 4, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/io/StringWriter;", "getBuffer", "Ljava/lang/StringBuffer;", new int[] { 4 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 4);
		mv.visitVarInsn(INSN_CONST_4, 5, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/StringBuffer;", "setLength", "VI", new int[] { 4, 5 });
		mv.visitLabel(l1);
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitLabel(l2);
		mv.visitIntInsn(INSN_MOVE_EXCEPTION, 4);
		mv.visitJumpInsn(INSN_GOTO, l1, 0, 0);
		mv.visitEnd();
	}
	cv.visitEnd();
}

public static void dumpMainActivity(ApplicationWriter aw) {
	ClassVisitor cv;
	FieldVisitor fv;
	MethodVisitor mv;
	AnnotationVisitor av0;

	cv = aw.visitClass(ACC_PUBLIC, "Lcom/heisentest/skeletonandroidapp/MainActivity;", null, "Landroid/app/Activity;", null);
	cv.visit(0, ACC_PUBLIC, "Lcom/heisentest/skeletonandroidapp/MainActivity;", null, "Landroid/app/Activity;", null);
	cv.visitSource("MainActivity.java", null);
	{
		mv = cv.visitMethod(ACC_PUBLIC + ACC_CONSTRUCTOR, "<init>", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(5, 0);
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Landroid/app/Activity;", "<init>", "V", new int[] { 4 });
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { 4 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 1);
		mv.visitStringInsn(INSN_CONST_STRING, 2, "A string argument");
		mv.visitVarInsn(INSN_CONST_4, 3, 0);
		mv.visitTypeInsn(INSN_NEW_ARRAY, 3, 0, 3, "[Ljava/lang/Object;");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", "log", "VLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 1, 2, 3 });
		mv.visitVarInsn(INSN_CONST_4, 1, 3);
		mv.visitTypeInsn(INSN_NEW_ARRAY, 0, 0, 1, "[I");
		mv.visitMethodInsn(INSN_INVOKE_DIRECT, "Lcom/heisentest/skeletonandroidapp/MainActivity;", "aMethodWithArguments", "V[I", new int[] { 4, 0 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PRIVATE, "aMethodWithArguments", "V[I", null, null);
		mv.visitCode();
		mv.visitMaxs(6, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { 4 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 1, "aMethodWithArguments");
		mv.visitVarInsn(INSN_CONST_4, 2, 1);
		mv.visitTypeInsn(INSN_NEW_ARRAY, 2, 0, 2, "[Ljava/lang/Object;");
		mv.visitVarInsn(INSN_CONST_4, 3, 0);
		mv.visitArrayOperationInsn(INSN_APUT_OBJECT, 5, 2, 3);
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", "log", "VLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 0, 1, 2 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PRIVATE, "aMethodWithNoArguments", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(4, 0);
		mv.visitMethodInsn(INSN_INVOKE_VIRTUAL, "Ljava/lang/Object;", "toString", "Ljava/lang/String;", new int[] { 3 });
		mv.visitIntInsn(INSN_MOVE_RESULT_OBJECT, 0);
		mv.visitStringInsn(INSN_CONST_STRING, 1, "aMethodWithArguments");
		mv.visitVarInsn(INSN_CONST_4, 2, 0);
		mv.visitTypeInsn(INSN_NEW_ARRAY, 2, 0, 2, "[Ljava/lang/Object;");
		mv.visitMethodInsn(INSN_INVOKE_STATIC, "Lcom/heisentest/skeletonandroidapp/HeisentestLogger;", "log", "VLjava/lang/String;Ljava/lang/String;[Ljava/lang/Object;", new int[] { 0, 1, 2 });
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	{
		mv = cv.visitMethod(ACC_PUBLIC, "startLogging", "V", null, null);
		mv.visitCode();
		mv.visitMaxs(1, 0);
		mv.visitInsn(INSN_RETURN_VOID);
		mv.visitEnd();
	}
	cv.visitEnd();
}

}

