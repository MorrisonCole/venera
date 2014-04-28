package com.heisentest.venera.classwriters;

import com.heisentest.venera.dexifier.ClassesDump;
import org.ow2.asmdex.ApplicationWriter;

public class LogEventWriter {

    public void addLogEventClasses(ApplicationWriter applicationWriter) {
        ClassesDump.dumpLogEvent(applicationWriter);

        ClassesDump.dumpMethodEntryEvent(applicationWriter);

        ClassesDump.dumpComplexInstanceMethodEntryEvent(applicationWriter);
        ClassesDump.dumpComplexInstanceMethodEntryEvent$1(applicationWriter);
        ClassesDump.dumpComplexInstanceMethodEntryEvent$Builder(applicationWriter);

        ClassesDump.dumpComplexStaticMethodEntryEvent(applicationWriter);
        ClassesDump.dumpComplexStaticMethodEntryEvent$1(applicationWriter);
        ClassesDump.dumpComplexStaticMethodEntryEvent$Builder(applicationWriter);

        ClassesDump.dumpLogEventWriter(applicationWriter);
    }
}
