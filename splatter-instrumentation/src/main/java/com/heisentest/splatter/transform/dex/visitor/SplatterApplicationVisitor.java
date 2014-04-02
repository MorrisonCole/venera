package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.classwriters.HeisentestJsonLoggerClassWriter;
import com.heisentest.splatter.classwriters.LogEventWriter;
import com.heisentest.splatter.classwriters.LoggerClassWriter;
import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import com.heisentest.splatter.transform.dex.visitor.noop.SplatterNoOpClassVisitor;
import com.heisentest.splatter.transform.dex.visitor.test.SplatterBaseTestCaseClassVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ApplicationWriter;
import org.ow2.asmdex.ClassVisitor;

public class SplatterApplicationVisitor extends ApplicationVisitor {

    private final LoggerClassWriter loggerClassWriter;
    private final Logger logger = Logger.getLogger(SplatterApplicationVisitor.class);
    private final ApplicationWriter applicationWriter;
    private final InstrumentationSpy instrumentationSpy;

    public SplatterApplicationVisitor(int asmApiLevel, ApplicationWriter applicationWriter, InstrumentationSpy instrumentationSpy) {
        super(asmApiLevel, applicationWriter);
        this.applicationWriter = applicationWriter;
        this.instrumentationSpy = instrumentationSpy;
        loggerClassWriter = new HeisentestJsonLoggerClassWriter();
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String [] signature, String superName, String [] interfaces) {
        ClassVisitor classVisitor = av.visitClass(access, name, signature, superName, interfaces);

        if (instrumentationSpy.isBaseTestCaseClass(name)) {
            return new SplatterBaseTestCaseClassVisitor(api, classVisitor, name, instrumentationSpy);
        } else if (instrumentationSpy.shouldClassBeInstrumented(name)) {
            return new SplatterLoggingClassVisitor(api, classVisitor, name, instrumentationSpy);
        } else {
            return new SplatterNoOpClassVisitor(api, classVisitor);
        }
    }

    @Override
    public void visitEnd() {
        logger.info("Finishing second pass, writing classes.dex");

        if (instrumentationSpy.isApplicationApk()) {
            loggerClassWriter.addLogClass(applicationWriter);
            final LogEventWriter logEventWriter = new LogEventWriter();
            logEventWriter.addLogEventClasses(applicationWriter);
        }

        super.visitEnd();
    }
}
