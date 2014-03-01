package com.heisentest.splatter.classwriters;

import com.heisentest.splatter.dexifier.ClassesDump;
import org.ow2.asmdex.ApplicationWriter;

public class HeisentestXmlLoggerClassWriter implements LoggerClassWriter {

    public void addLogClass(ApplicationWriter applicationWriter) {
        ClassesDump.dumpHeisentestXmlLogger(applicationWriter);
    }
}
