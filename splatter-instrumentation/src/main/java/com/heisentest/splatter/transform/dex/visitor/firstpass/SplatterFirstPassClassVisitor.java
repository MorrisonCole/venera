package com.heisentest.splatter.transform.dex.visitor.firstpass;

import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;

class SplatterFirstPassClassVisitor extends ClassVisitor {

    private final String className;
    private final InstrumentationSpy instrumentationSpy;

    public SplatterFirstPassClassVisitor(int api, String className, InstrumentationSpy instrumentationSpy) {
        super(api);
        this.className = className;
        this.instrumentationSpy = instrumentationSpy;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signatures, String[] exceptions) {
        return new SplatterFirstPassMethodVisitor(api, className, name, instrumentationSpy);
    }
}
