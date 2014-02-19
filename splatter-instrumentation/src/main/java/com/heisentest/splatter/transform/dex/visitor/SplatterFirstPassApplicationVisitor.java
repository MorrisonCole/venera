package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;

public class SplatterFirstPassApplicationVisitor extends ApplicationVisitor {

    private final InstrumentationSpy instrumentationSpy;

    public SplatterFirstPassApplicationVisitor(int api, InstrumentationSpy instrumentationSpy) {
        super(api);
        this.instrumentationSpy = instrumentationSpy;
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String[] signature, String superName, String[] interfaces) {
        return new SplatterFirstPassClassVisitor(api, instrumentationSpy);
    }

    private class SplatterFirstPassClassVisitor extends ClassVisitor {

        private final InstrumentationSpy instrumentationSpy;

        public SplatterFirstPassClassVisitor(int api, InstrumentationSpy instrumentationSpy) {
            super(api);
            this.instrumentationSpy = instrumentationSpy;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String[] signatures, String[] exceptions) {
            instrumentationSpy.setAvailableInstrumentationPoints(instrumentationSpy.getAvailableInstrumentationPoints() + 1);
            return new SplatterFirstPassMethodVisitor(api);
        }
    }

    private class SplatterFirstPassMethodVisitor extends MethodVisitor {

        public SplatterFirstPassMethodVisitor(int api) {
            super(api);
        }
    }
}
