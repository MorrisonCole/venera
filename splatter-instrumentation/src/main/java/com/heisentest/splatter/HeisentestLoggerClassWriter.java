package com.heisentest.splatter;

import com.heisentest.splatter.dexifier.ClassesDump;
import org.ow2.asmdex.ApplicationWriter;

public class HeisentestLoggerClassWriter implements LoggerClassWriter {

    public void addLogClass(ApplicationWriter applicationWriter) {
        ClassesDump.dumpHeisentestLogger(applicationWriter);
    }
}
