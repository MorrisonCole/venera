package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.LogClassWriter;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;

public class SplatterApplicationVisitor extends ApplicationVisitor {

    private final LogClassWriter logClassWriter;
    private final Logger logger = Logger.getLogger(SplatterApplicationVisitor.class);

    public SplatterApplicationVisitor(int asmApiLevel, ApplicationVisitor applicationVisitor) {
        super(asmApiLevel, applicationVisitor);
        logClassWriter = new LogClassWriter();
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String [] signature, String superName, String [] interfaces) {
        ClassVisitor classVisitor = av.visitClass(access, name, signature, superName, interfaces);

        if (name.startsWith("Lcom/heisentest/skeletonandroidapp/")) {
            return new SplatterLoggingClassVisitor(api, classVisitor, name);
        } else {
            return new SplatterClassVisitor(api, classVisitor);
        }
    }

    @Override
    public void visitEnd() {
        logger.info("Finishing second pass, writing classes.dex");
        logClassWriter.addLogClass(av);
        super.visitEnd();
    }
}
