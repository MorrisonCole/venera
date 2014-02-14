package com.heisentest.splatter;

import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;

public class SplatterFirstPassApplicationVisitor extends ApplicationVisitor {

    public SplatterFirstPassApplicationVisitor(int api) {
        super(api);
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String[] signature, String superName, String[] interfaces) {
        return new SplatterFirstPassClassVisitor(api);
    }

    private class SplatterFirstPassClassVisitor extends ClassVisitor {

        public SplatterFirstPassClassVisitor(int api) {
            super(api);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String[] signatures, String[] exceptions) {
            return new SplatterFirstPassMethodVisitor(api);
        }
    }

    private class SplatterFirstPassMethodVisitor extends MethodVisitor {

        public SplatterFirstPassMethodVisitor(int api) {
            super(api);
        }
    }
}
