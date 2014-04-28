package com.heisentest.venera.transform.dex.visitor.noop;

import org.ow2.asmdex.MethodVisitor;

public class VeneraNoOpMethodVisitor extends MethodVisitor {

    public VeneraNoOpMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }
}
