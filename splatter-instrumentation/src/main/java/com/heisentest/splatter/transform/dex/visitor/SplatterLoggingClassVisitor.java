package com.heisentest.splatter.transform.dex.visitor;

import org.apache.log4j.Logger;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.Opcodes;

import java.util.ArrayList;
import java.util.Arrays;

public class SplatterLoggingClassVisitor extends ClassVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingClassVisitor.class);
    private String className;

    public SplatterLoggingClassVisitor(int asmApiLevel, ClassVisitor classVisitor, String className) {
        super(asmApiLevel, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

        // We ignore the constructors for now, since they are special (i.e. 'this' is not yet initialized).
        // See: http://stackoverflow.com/a/8517155
        // TODO: Is there a way to figure out the runtime type name of the object at this point?
        // TODO: If not, we could probably deal with these similarly to static methods (don't rely on 'this'
        // TODO: for the class name (although that would suck a bit).
        ArrayList<String> blacklistedNames = new ArrayList<String>(Arrays.asList("<init>", "<clinit>"));

        if ((access & Opcodes.ACC_ABSTRACT) == 0 && !blacklistedNames.contains(name) && !name.contains("$")) {
            logger.debug(String.format("Adding Splatter logger to method (name: '%s') (desc: '%s') (access (opcode): '%s')", name, desc, access));

            boolean isStatic = (access & Opcodes.ACC_STATIC) > 0;
            return new SplatterLoggingMethodVisitor(api, methodVisitor, desc, name, className, isStatic);
        }

        return new SplatterMethodVisitor(api, methodVisitor);
    }
}
