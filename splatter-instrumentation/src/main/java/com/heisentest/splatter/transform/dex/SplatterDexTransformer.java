package com.heisentest.splatter.transform.dex;

import com.heisentest.splatter.controlflow.SplatterControlFlowAnalyzer;
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
    private final String applicationRootNamespace;
    private final SplatterControlFlowAnalyzer splatterControlFlowAnalyzer;

    public SplatterDexTransformer(int asmApiLevel, String applicationRootNamespace, SplatterControlFlowAnalyzer splatterControlFlowAnalyzer) {
        this.asmApiLevel = asmApiLevel;
        this.applicationRootNamespace = applicationRootNamespace;
        this.splatterControlFlowAnalyzer = splatterControlFlowAnalyzer;
    }

    public void transform(FileInputStream fileInputStream, ZipOutputStream zipOutputStream) throws IOException {
        ApplicationReader applicationReader = new ApplicationReader(asmApiLevel, fileInputStream);

        if (TRANSFORM_DISABLED) {
            logger.info("Dex transform disabled, using original bytecode!");
            zipOutputStream.write(applicationReader.byteCode);
            return;
        }

        // First pass in case we want to save any information before generating the new dex file
        InstrumentationSpy instrumentationSpy = new InstrumentationSpy(applicationRootNamespace);
        SplatterFirstPassApplicationVisitor splatterFirstPassApplicationVisitor = new SplatterFirstPassApplicationVisitor(asmApiLevel, instrumentationSpy, splatterControlFlowAnalyzer);
        applicationReader.accept(splatterFirstPassApplicationVisitor, 0);

        logger.debug(String.format("Found %s instrumentation points", instrumentationSpy.getAvailableInstrumentationPoints()));

        // Second pass
        ApplicationWriter applicationWriter = new ApplicationWriter(applicationReader);
        ApplicationVisitor splatterApplicationVisitor = new SplatterApplicationVisitor(asmApiLevel, applicationWriter, instrumentationSpy);

        applicationReader.accept(splatterApplicationVisitor, 0);

        byte[] bytes = applicationWriter.toByteArray();
        zipOutputStream.write(bytes);
    }
}
