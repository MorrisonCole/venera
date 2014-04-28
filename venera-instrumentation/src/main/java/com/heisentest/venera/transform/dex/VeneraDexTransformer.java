package com.heisentest.venera.transform.dex;

import com.heisentest.venera.transform.dex.visitor.VeneraApplicationVisitor;
import com.heisentest.venera.transform.dex.visitor.firstpass.VeneraFirstPassApplicationVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ApplicationReader;
import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ApplicationWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class VeneraDexTransformer {

    private static final boolean TRANSFORM_DISABLED = false;
    private final int asmApiLevel;
    private final Logger logger = Logger.getLogger(VeneraDexTransformer.class);
    private InstrumentationSpy instrumentationSpy;

    public VeneraDexTransformer(int asmApiLevel, InstrumentationSpy instrumentationSpy) {
        this.asmApiLevel = asmApiLevel;
        this.instrumentationSpy = instrumentationSpy;
    }

    public void transform(FileInputStream fileInputStream, ZipOutputStream zipOutputStream) throws IOException {
        ApplicationReader applicationReader = new ApplicationReader(asmApiLevel, fileInputStream);
        ApplicationWriter applicationWriter = new ApplicationWriter(applicationReader);

        if (TRANSFORM_DISABLED) {
            logger.info("Dex transform disabled, using original bytecode!");
            zipOutputStream.write(applicationReader.byteCode);
            return;
        }

        performFirstPass(applicationReader);

        performSecondPass(applicationReader, applicationWriter);

        byte[] bytes = applicationWriter.toByteArray();
        zipOutputStream.write(bytes);
    }

    private void performFirstPass(ApplicationReader applicationReader) {
        VeneraFirstPassApplicationVisitor veneraFirstPassApplicationVisitor = new VeneraFirstPassApplicationVisitor(asmApiLevel, instrumentationSpy);
        applicationReader.accept(veneraFirstPassApplicationVisitor, 0);

        logger.debug(String.format("Found %s instrumentation points", instrumentationSpy.getInstrumentationPoints().size()));
    }

    private void performSecondPass(ApplicationReader applicationReader, ApplicationWriter applicationWriter) {
        ApplicationVisitor veneraApplicationVisitor = new VeneraApplicationVisitor(asmApiLevel, applicationWriter, instrumentationSpy);
        applicationReader.accept(veneraApplicationVisitor, 0);
    }
}
