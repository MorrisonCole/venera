package com.heisentest.venera.transform.dex.visitor;

import com.heisentest.venera.classwriters.HeisentestJsonLoggerClassWriter;
import com.heisentest.venera.classwriters.LoggerClassWriter;
import com.heisentest.venera.transform.dex.InstrumentationSpy;
import com.heisentest.venera.transform.dex.visitor.noop.VeneraNoOpClassVisitor;
import com.heisentest.venera.transform.dex.visitor.test.VeneraBaseTestCaseClassVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ApplicationWriter;
import org.ow2.asmdex.ClassVisitor;

public class VeneraApplicationVisitor extends ApplicationVisitor {

    private final LoggerClassWriter loggerClassWriter;
    private final Logger logger = Logger.getLogger(VeneraApplicationVisitor.class);
    private final ApplicationWriter applicationWriter;
    private final InstrumentationSpy instrumentationSpy;

    public VeneraApplicationVisitor(int asmApiLevel, ApplicationWriter applicationWriter, InstrumentationSpy instrumentationSpy) {
        super(asmApiLevel, applicationWriter);
        this.applicationWriter = applicationWriter;
        this.instrumentationSpy = instrumentationSpy;
        loggerClassWriter = new HeisentestJsonLoggerClassWriter();
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String [] signature, String superName, String [] interfaces) {
        ClassVisitor classVisitor = av.visitClass(access, name, signature, superName, interfaces);

        if (instrumentationSpy.isBaseTestCaseClass(name)) {
            return new VeneraBaseTestCaseClassVisitor(api, classVisitor, name, instrumentationSpy);
        } else if (instrumentationSpy.shouldClassBeInstrumented(name)) {
            return new VeneraLoggingClassVisitor(api, classVisitor, name, instrumentationSpy);
        } else {
            return new VeneraNoOpClassVisitor(api, classVisitor);
        }
    }

    @Override
    public void visitEnd() {
        logger.info("Finishing second pass, writing classes.dex");

        // TODO: Unnecessary if using SDK
        if (instrumentationSpy.isApplicationApk()) {
//            loggerClassWriter.addLogClass(applicationWriter);
//            final LogEventWriter logEventWriter = new LogEventWriter();
//            logEventWriter.addLogEventClasses(applicationWriter);
        }

        super.visitEnd();
    }
}
