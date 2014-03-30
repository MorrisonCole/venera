package com.heisentest.splatter.transform.dex.visitor.noop;

import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;

public class SplatterNoOpClassVisitor extends ClassVisitor {

    public SplatterNoOpClassVisitor(int asmApiLevel, ClassVisitor classVisitor) {
        super(asmApiLevel, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new SplatterNoOpMethodVisitor(api, methodVisitor);
    }
}
