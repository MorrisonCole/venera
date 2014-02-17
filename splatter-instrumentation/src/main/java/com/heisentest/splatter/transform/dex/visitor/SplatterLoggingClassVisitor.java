package com.heisentest.splatter.transform.dex.visitor;

import org.apache.log4j.Logger;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.Opcodes;

import java.util.ArrayList;
import java.util.Arrays;

public class SplatterLoggingClassVisitor extends ClassVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingClassVisitor.class);

    public SplatterLoggingClassVisitor(int asmApiLevel, ClassVisitor classVisitor) {
        super(asmApiLevel, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

        ArrayList<String> blacklistedNames = new ArrayList<String>(Arrays.asList("<init>", "<clinit>"));

        if ((access & Opcodes.ACC_ABSTRACT) == 0 && !blacklistedNames.contains(name) && !name.contains("$")) {
            logger.debug(String.format("Adding Splatter logger to method (name: '%s') (desc: '%s') (access (opcode): '%s')", name, desc, access));

            boolean isStatic = (access & Opcodes.ACC_STATIC) > 0;
            return new SplatterLoggingMethodVisitor(api, methodVisitor, desc, name, isStatic);
        }

        return new SplatterMethodVisitor(api, methodVisitor);
    }
}
