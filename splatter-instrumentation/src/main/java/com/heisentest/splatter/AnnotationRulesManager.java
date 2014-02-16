package com.heisentest.splatter;

import org.ow2.asmdex.Opcodes;

public class AnnotationRulesManager {

    public String log(String owner, String name, String desc, int opcode) {
        if (owner.startsWith("Lcom/heisentest/skeletonandroidapp/") && !name.equals("<init>") && opcode != Opcodes.INSN_INVOKE_INTERFACE) {
            return "This is a log";
        } else {
            return null;
        }
    }
}
