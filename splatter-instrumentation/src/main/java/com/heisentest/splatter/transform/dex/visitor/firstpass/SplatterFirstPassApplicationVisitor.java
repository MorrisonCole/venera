package com.heisentest.splatter.transform.dex.visitor.firstpass;

import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import org.apache.log4j.Logger;
import org.ow2.asmdex.*;

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

}
