package com.heisentest.splatter.classwriters;

import com.heisentest.splatter.dexifier.ClassesDump;
import org.ow2.asmdex.ApplicationWriter;

public class LogEventWriter {

    public void addLogEventClasses(ApplicationWriter applicationWriter) {
        ClassesDump.dumpLogEvent(applicationWriter);

        ClassesDump.dumpMethodEntryEvent(applicationWriter);

        ClassesDump.dumpComplexInstanceMethodEntryEvent(applicationWriter);
        ClassesDump.dumpComplexInstanceMethodEntryEvent$1(applicationWriter);
        ClassesDump.dumpComplexInstanceMethodEntryEvent$Builder(applicationWriter);

        ClassesDump.dumpStaticMethodEntryEvent(applicationWriter);
        ClassesDump.dumpStaticMethodEntryEvent$1(applicationWriter);
        ClassesDump.dumpStaticMethodEntryEvent$Builder(applicationWriter);

        ClassesDump.dumpLogEventWriter(applicationWriter);
    }
}
