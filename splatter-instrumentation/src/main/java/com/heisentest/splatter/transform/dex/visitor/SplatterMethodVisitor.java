package com.heisentest.splatter.transform.dex.visitor;

import org.ow2.asmdex.MethodVisitor;

public class SplatterMethodVisitor extends MethodVisitor {

    public SplatterMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }
}
