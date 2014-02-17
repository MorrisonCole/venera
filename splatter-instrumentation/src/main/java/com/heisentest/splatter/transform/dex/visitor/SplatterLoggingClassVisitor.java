package com.heisentest.splatter.transform.dex.visitor;

import org.apache.log4j.Logger;
import org.ow2.asmdex.ClassVisitor;
import org.ow2.asmdex.MethodVisitor;
import org.ow2.asmdex.Opcodes;

import java.util.ArrayList;
import java.util.Arrays;

public class SplatterLoggingClassVisitor extends ClassVisitor {

    private final Logger logger = Logger.getLogger(SplatterLoggingClassVisitor.class);
    private String className;

    public SplatterLoggingClassVisitor(int asmApiLevel, ClassVisitor classVisitor, String className) {
        super(asmApiLevel, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String[] signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

        // We ignore the constructors for now, since they are special (i.e. 'this' is not yet initialized).
        // See: http://stackoverflow.com/a/8517155
        // TODO: Is there a way to figure out the runtime type name of the object at this point?
        // TODO: If not, we could probably deal with these similarly to static methods (don't rely on 'this'
        // TODO: for the class name (although that would suck a bit).
        // Ignoring 'run' since runnables seem to generate bad dex files for some reason. need to fix this.
        // Ignoring 'toString' since calling 'this.toString' then causes a stack overflow :D
        // Ignoring 'hashCode' as it's causing stack overflows for some reason.
        ArrayList<String> blacklistedNames = new ArrayList<String>(Arrays.asList("<init>", "<clinit>", "run", "toString", "hashCode"));

        // We don't want to instrument any auto-generated enclosing accessor methods (signature access$0,
        // access$1 etc.), so we ignore any methods with '$' in their name.
        // See: http://www.retrologic.com/innerclasses.doc7.html
        // TODO: Are there legitimate usages of '$' in method names? We should only ban methods with a
        // TODO: specific 'access$' signature if so.
        String bannedAutoAccessMethodCharacter = "$";

        boolean instrument = false;
        ArrayList<String> allowedPrefixes = new ArrayList<String>(Arrays.asList("get", "set", "tile", "on", "create", ""));
        for (String prefix : allowedPrefixes) {
            if (name.startsWith(prefix)) {
                instrument = true;
                break;
            }
        }

        if (instrument && (access & Opcodes.ACC_ABSTRACT) == 0 && !blacklistedNames.contains(name) && !name.contains(bannedAutoAccessMethodCharacter)) {
//            logger.debug(String.format("Adding HeisentestLogger to method (name: '%s') (desc: '%s') (class: '%s') (access (opcode): '%s')", name, desc, className, access));

            boolean isStatic = (access & Opcodes.ACC_STATIC) > 0;
            return new SplatterLoggingMethodVisitor(api, methodVisitor, desc, name, className, isStatic);
        } else if ((access & Opcodes.ACC_ABSTRACT) == 0 && !blacklistedNames.contains(name) && !name.contains(bannedAutoAccessMethodCharacter)) {
            logger.debug(String.format("SKIPPING method (name: '%s') (desc: '%s') (class: '%s') (access (opcode): '%s')", name, desc, className, access));
        }

        return new SplatterMethodVisitor(api, methodVisitor);
    }
}
