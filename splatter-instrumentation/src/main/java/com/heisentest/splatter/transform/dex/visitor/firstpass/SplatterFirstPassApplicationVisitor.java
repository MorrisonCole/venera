package com.heisentest.splatter.transform.dex.visitor.firstpass;

import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.ow2.asmdex.AnnotationVisitor;
import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;

public class SplatterFirstPassApplicationVisitor extends ApplicationVisitor {

    private static final Logger logger = Logger.getLogger(SplatterFirstPassApplicationVisitor.class);
    private final InstrumentationSpy instrumentationSpy;

    public SplatterFirstPassApplicationVisitor(int api, InstrumentationSpy instrumentationSpy) {
        super(api);
        this.instrumentationSpy = instrumentationSpy;
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String[] signature, String superName, String[] interfaces) {
        return new SplatterFirstPassClassVisitor(api, name, instrumentationSpy);
    }

    private class SplatterFirstPassClassVisitor extends ClassVisitor {

        private final String className;
        private final InstrumentationSpy instrumentationSpy;

        public SplatterFirstPassClassVisitor(int api, String className, InstrumentationSpy instrumentationSpy) {
            super(api);
            this.className = className;
            this.instrumentationSpy = instrumentationSpy;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String[] signatures, String[] exceptions) {
            instrumentationSpy.setAvailableInstrumentationPoints(instrumentationSpy.getAvailableInstrumentationPoints() + 1);
            return new SplatterFirstPassMethodVisitor(api, className, name);
        }
    }

    private class SplatterFirstPassMethodVisitor extends MethodVisitor {

        private final String className;
        private final String methodName;
        private boolean ignoreMethod;

        public SplatterFirstPassMethodVisitor(int api, String className, String methodName) {
            super(api);
            this.className = className;
            this.methodName = methodName;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            if (desc.equals("Lcom/heisentest/splatter/sdk/SplatterIgnore;")) {
                ignoreMethod = true;
            }
            return super.visitAnnotation(desc, visible);
        }

        @Override
        public void visitEnd() {
            if (!ignoreMethod) {
                instrumentationSpy.addInstrumentableMethod(className, methodName);
            } else {
                logger.debug(String.format("Annotation found! Skipping Class: %s, Method: %s.", className, methodName));
            }

            super.visitEnd();
        }
    }
}
