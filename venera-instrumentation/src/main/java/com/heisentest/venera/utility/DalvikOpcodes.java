package com.heisentest.venera.utility;

import static org.ow2.asmdex.Opcodes.ACC_ABSTRACT;
import static org.ow2.asmdex.Opcodes.ACC_STATIC;

public class DalvikOpcodes {

    public static boolean isStatic(int access) {
        return (access & ACC_STATIC) > 0;
    }

    // TODO: Check this returns correctly.
    public static boolean isAbstract(int access) {
        return (access & ACC_ABSTRACT) != 0;
    }
}
