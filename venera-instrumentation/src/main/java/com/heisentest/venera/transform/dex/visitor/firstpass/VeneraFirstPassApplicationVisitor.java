package com.heisentest.venera.transform.dex.visitor.firstpass;

import com.heisentest.venera.transform.dex.InstrumentationSpy;
import org.apache.log4j.Logger;
import org.ow2.asmdex.*;

public class VeneraFirstPassApplicationVisitor extends ApplicationVisitor {

    private static final Logger logger = Logger.getLogger(VeneraFirstPassApplicationVisitor.class);
    private final InstrumentationSpy instrumentationSpy;

    public VeneraFirstPassApplicationVisitor(int api, InstrumentationSpy instrumentationSpy) {
        super(api);
        this.instrumentationSpy = instrumentationSpy;
    }

    @Override
    public ClassVisitor visitClass(int access, String name, String[] signature, String superName, String[] interfaces) {
        return new VeneraFirstPassClassVisitor(api, name, instrumentationSpy);
    }
}
