package com.heisentest.splatter.transform.dex;

import com.heisentest.splatter.transform.dex.visitor.SplatterApplicationVisitor;
import com.heisentest.splatter.transform.dex.visitor.firstpass.SplatterFirstPassApplicationVisitor;
import org.apache.log4j.Logger;
import org.ow2.asmdex.ApplicationReader;
import org.ow2.asmdex.ApplicationVisitor;
import org.ow2.asmdex.ApplicationWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class SplatterDexTransformer {

    private static final boolean TRANSFORM_DISABLED = false;
    private final int asmApiLevel;
    private final Logger logger = Logger.getLogger(SplatterDexTransformer.class);
    private InstrumentationSpy instrumentationSpy;

    public SplatterDexTransformer(int asmApiLevel, InstrumentationSpy instrumentationSpy) {
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
        SplatterFirstPassApplicationVisitor splatterFirstPassApplicationVisitor = new SplatterFirstPassApplicationVisitor(asmApiLevel, instrumentationSpy);
        applicationReader.accept(splatterFirstPassApplicationVisitor, 0);

        logger.debug(String.format("Found %s instrumentation points", instrumentationSpy.getInstrumentationPoints().size()));
    }

    private void performSecondPass(ApplicationReader applicationReader, ApplicationWriter applicationWriter) {
        ApplicationVisitor splatterApplicationVisitor = new SplatterApplicationVisitor(asmApiLevel, applicationWriter, instrumentationSpy);
        applicationReader.accept(splatterApplicationVisitor, 0);
    }
}
