package com.heisentest.venera.classwriters;

import com.heisentest.venera.dexifier.ClassesDump;
import org.ow2.asmdex.ApplicationWriter;

public class HeisentestJsonLoggerClassWriter implements LoggerClassWriter {

    public void addLogClass(ApplicationWriter applicationWriter) {
        ClassesDump.dumpJsonLogger(applicationWriter);
    }
}
