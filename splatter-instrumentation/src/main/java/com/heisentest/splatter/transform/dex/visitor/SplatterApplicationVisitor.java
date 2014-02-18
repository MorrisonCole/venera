package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.HeisentestXmlLoggerClassWriter;
import com.heisentest.splatter.LoggerClassWriter;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ClassVisitor;

public class SplatterApplicationVisitor extends ApplicationVisitor {

    private final LoggerClassWriter loggerClassWriter;
    private final Logger logger = Logger.getLogger(SplatterApplicationVisitor.class);
    private final String applicationRootNamespace;

    public SplatterApplicationVisitor(int asmApiLevel, ApplicationVisitor applicationVisitor, String applicationRootNamespace) {
        super(asmApiLevel, applicationVisitor);
        this.applicationRootNamespace = applicationRootNamespace;
        loggerClassWriter = new HeisentestXmlLoggerClassWriter();
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String [] signature, String superName, String [] interfaces) {
        ClassVisitor classVisitor = av.visitClass(access, name, signature, superName, interfaces);

        if (name.startsWith(applicationRootNamespace)) {
            return new SplatterLoggingClassVisitor(api, classVisitor, name);
        } else {
            return new SplatterClassVisitor(api, classVisitor);
        }
    }

    @Override
    public void visitEnd() {
        logger.info("Finishing second pass, writing classes.dex");
        loggerClassWriter.addLogClass(av);
        super.visitEnd();
    }
}
