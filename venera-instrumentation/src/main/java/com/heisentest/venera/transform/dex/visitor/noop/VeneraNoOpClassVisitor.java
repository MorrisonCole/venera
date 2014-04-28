package com.heisentest.venera.transform.dex.visitor.noop;

import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;

public class VeneraNoOpClassVisitor extends ClassVisitor {

    public VeneraNoOpClassVisitor(int asmApiLevel, ClassVisitor classVisitor) {
        super(asmApiLevel, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new VeneraNoOpMethodVisitor(api, methodVisitor);
    }
}
