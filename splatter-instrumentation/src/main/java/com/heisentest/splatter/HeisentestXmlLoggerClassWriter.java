package com.heisentest.splatter;

import com.heisentest.splatter.dexifier.ClassesDump;
import org.ow2.asmdex.*;
import org.ow2.asmdex.structureCommon.Label;

import static org.ow2.asmdex.Opcodes.*;

public class HeisentestXmlLoggerClassWriter implements LoggerClassWriter {

    public void addLogClass(ApplicationWriter applicationWriter) {
        ClassesDump.dumpHeisentestXmlLogger(applicationWriter);
    }
}
