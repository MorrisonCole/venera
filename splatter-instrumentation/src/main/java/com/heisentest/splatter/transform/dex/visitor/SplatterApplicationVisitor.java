package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.AnnotationRulesManager;
import com.heisentest.splatter.LogClassWriter;
import com.heisentest.splatter.transform.dex.visitor.SplatterClassVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ApplicationWriter;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.ClassWriter;

public class SplatterApplicationVisitor extends ApplicationVisitor {

    private final AnnotationRulesManager annotationRulesManager;
    private final LogClassWriter logClassWriter;
    private final Logger logger = Logger.getLogger(SplatterApplicationVisitor.class);

    public SplatterApplicationVisitor(int asmApiLevel, ApplicationVisitor applicationVisitor, AnnotationRulesManager annotationRulesManager) {
        super(asmApiLevel, applicationVisitor);
        this.annotationRulesManager = annotationRulesManager;
        logClassWriter = new LogClassWriter();
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String [] signature, String superName, String [] interfaces) {
        ClassVisitor classVisitor = av.visitClass(access, name, signature, superName, interfaces);
        return new SplatterClassVisitor(api, classVisitor, annotationRulesManager);
    }

    @Override
    public void visitEnd() {
        logger.info("Finishing second pass, writing classes.dex");
        logClassWriter.addLogClass(av);
        super.visitEnd();
    }
}
