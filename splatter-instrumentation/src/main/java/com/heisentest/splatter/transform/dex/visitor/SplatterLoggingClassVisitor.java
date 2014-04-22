package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.instrumentation.point.InstrumentationPoint;
import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import com.heisentest.splatter.transform.dex.visitor.noop.SplatterNoOpMethodVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.*;

import java.util.ArrayList;
import java.util.Arrays;

import static com.heisentest.splatter.utility.DalvikOpcodes.isAbstract;
import static com.heisentest.splatter.utility.DalvikOpcodes.isStatic;

public class SplatterLoggingClassVisitor extends ClassVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingClassVisitor.class);
    private String className;
    private final InstrumentationSpy instrumentationSpy;

    // We ignore the constructors for now, since they are special (i.e. 'this' is not yet initialized).
    // See: http://stackoverflow.com/a/8517155
    // Ignoring 'run' since runnables seem to generate bad dex files for some reason. TODO: need to fix this.
    // Ignoring 'toString' and 'hashCode' since we use them in our instrumentation, so they cause stack overflows!
    private final ArrayList<String> blacklistedMethodNames = new ArrayList<>(Arrays.asList("<init>", "<clinit>", "run", "toString", "hashCode"));

    /**
     * We don't want to instrument any auto-generated enclosing accessor methods (signature access$0,
     * access$1 etc.), so we ignore any methods with '$' in their name.
     * See: http://www.retrologic.com/innerclasses.doc7.html
      */
    private final String bannedAutoAccessMethodCharacter = "$";

    public SplatterLoggingClassVisitor(int asmApiLevel, ClassVisitor classVisitor, String className, InstrumentationSpy instrumentationSpy) {
        super(asmApiLevel, classVisitor);
        this.className = className;
        this.instrumentationSpy = instrumentationSpy;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

        // TODO: Document this whitelist. Should be moved into InstrumentationSpy.
        boolean instrument = false;
        ArrayList<String> allowedPrefixes = new ArrayList<>(Arrays.asList("get", "set", "tile", "on", "create", ""));
        for (String prefix : allowedPrefixes) {
            if (name.startsWith(prefix)) {
                instrument = true;
                break;
            }
        }

        boolean isStatic = isStatic(access);

        final InstrumentationPoint instrumentationPoint = instrumentationSpy.getMethodEntryInstrumentationPoint(className, name);

        if (instrumentationPoint != null && shouldInstrumentMethod(access, name, instrument)) {
            switch (instrumentationPoint.getInstrumentationPolicy()) {
                case NONE:
                    return new SplatterNoOpMethodVisitor(api, methodVisitor);
                case SIMPLE:
                    logger.debug(String.format("SIMPLE method (name: '%s') (desc: '%s') (class: '%s') (access (opcode): '%s')", name, desc, className, access));
                    return new SimpleInstanceMethodEntryMethodVisitor(api, methodVisitor, desc, name, isStatic);
                case COMPLEX:
                    logger.debug(String.format("COMPLEX method (name: '%s') (desc: '%s') (class: '%s') (access (opcode): '%s')", name, desc, className, access));
                    return new ComplexInstanceMethodEntryMethodVisitor(api, methodVisitor, desc, name, isStatic, className, instrumentationSpy);
            }
        }

        logger.debug(String.format("SKIPPING method (name: '%s') (desc: '%s') (class: '%s') (access (opcode): '%s')", name, desc, className, access));
        return new SplatterNoOpMethodVisitor(api, methodVisitor);
    }

    // TODO: Should be moved into InstrumentationSpy
    private boolean shouldInstrumentMethod(int access, String name, boolean instrument) {
        return instrument && !isAbstract(access) && !blacklistedMethodNames.contains(name) && !name.contains(bannedAutoAccessMethodCharacter);
    }
}
