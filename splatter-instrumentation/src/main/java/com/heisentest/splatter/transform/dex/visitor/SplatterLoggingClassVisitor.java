package com.heisentest.splatter.transform.dex.visitor;

import com.heisentest.splatter.instrumentation.point.InstrumentationPoint;
import com.heisentest.splatter.transform.dex.InstrumentationSpy;
import com.heisentest.splatter.transform.dex.visitor.noop.SplatterNoOpMethodVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.*;

import java.util.ArrayList;
import java.util.Arrays;

import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy.COMPLEX;
import static org.ow2.asmdex.Opcodes.*;

public class SplatterLoggingClassVisitor extends ClassVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingClassVisitor.class);
    private String className;
    private final InstrumentationSpy instrumentationSpy;

    // We ignore the constructors for now, since they are special (i.e. 'this' is not yet initialized).
    // See: http://stackoverflow.com/a/8517155
    // TODO: Is there a way to figure out the runtime type name of the object at this point?
    // TODO: If not, we could probably deal with these similarly to static methods (don't rely on 'this'
    // TODO: for the class name (although that would suck a bit).
    // Ignoring 'run' since runnables seem to generate bad dex files for some reason. need to fix this.
    // Ignoring 'toString' since calling 'this.toString' then causes a stack overflow :D
    // Ignoring 'hashCode' as it's causing stack overflows for some reason.
    private final ArrayList<String> blacklistedNames = new ArrayList<String>(Arrays.asList("<init>", "<clinit>", "run", "toString", "hashCode", "wakeUpDevice"));

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

        // TODO: Document this whitelist.
        boolean instrument = false;
        ArrayList<String> allowedPrefixes = new ArrayList<>(Arrays.asList("get", "set", "tile", "on", "create", ""));
        for (String prefix : allowedPrefixes) {
            final InstrumentationPoint instrumentationPoint = instrumentationSpy.getInstrumentationPoint(className, name);
            if (name.startsWith(prefix) && instrumentationPoint != null && instrumentationPoint.getInstrumentationPolicy() == COMPLEX) {

                instrument = true;
                break;
            }
        }

        boolean isStatic = isStatic(access);
        if (shouldInstrumentMethod(access, name, instrument)) {
            logger.debug(String.format("Adding HeisentestLogger to method (name: '%s') (desc: '%s') (class: '%s') (access (opcode): '%s')", name, desc, className, access));
            return new ComplexInstanceMethodEntryMethodVisitor(api, methodVisitor, desc, name, isStatic);
        }

        logger.debug(String.format("SKIPPING method (name: '%s') (desc: '%s') (class: '%s') (access (opcode): '%s')", name, desc, className, access));
        return new SplatterNoOpMethodVisitor(api, methodVisitor);
    }

    private boolean shouldInstrumentMethod(int access, String name, boolean instrument) {
        return instrument && !isAbstract(access) && !blacklistedNames.contains(name) && !name.contains(bannedAutoAccessMethodCharacter);
    }

    // TODO: Should be in a helper class somewhere else
    private boolean isStatic(int access) {
        return (access & ACC_STATIC) > 0;
    }

    // TODO: Check this returns correctly. Also as above.
    private boolean isAbstract(int access) {
        return (access & Opcodes.ACC_ABSTRACT) != 0;
    }
}
