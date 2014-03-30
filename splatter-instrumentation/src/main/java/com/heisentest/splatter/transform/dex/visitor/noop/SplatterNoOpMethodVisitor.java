package com.heisentest.splatter.transform.dex.visitor.noop;

import org.ow2.asmdex.MethodVisitor;

public class SplatterNoOpMethodVisitor extends MethodVisitor {

    public SplatterNoOpMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }
}
