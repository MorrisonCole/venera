package com.heisentest.splatter.transform.dex;

import com.heisentest.splatter.AnnotationRulesManager;
import com.heisentest.splatter.transform.dex.visitor.SplatterApplicationVisitor;
import com.heisentest.splatter.transform.dex.visitor.SplatterFirstPassApplicationVisitor;
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

    public SplatterDexTransformer(int asmApiLevel) {
        this.asmApiLevel = asmApiLevel;
    }

    public void transform(FileInputStream fileInputStream, ZipOutputStream zipOutputStream) throws IOException {
        ApplicationReader applicationReader = new ApplicationReader(asmApiLevel, fileInputStream);

        if (TRANSFORM_DISABLED) {
            logger.info("Dex transform disabled, using original bytecode!");
            zipOutputStream.write(applicationReader.byteCode);
            return;
        }

        // First pass in case we want to save any information before generating the new dex file
        SplatterFirstPassApplicationVisitor splatterFirstPassApplicationVisitor = new SplatterFirstPassApplicationVisitor(asmApiLevel);
        applicationReader.accept(splatterFirstPassApplicationVisitor, 0);

        // Second pass
        ApplicationWriter applicationWriter = new ApplicationWriter(applicationReader);
        ApplicationVisitor splatterApplicationVisitor = new SplatterApplicationVisitor(asmApiLevel, applicationWriter, new AnnotationRulesManager());

        applicationReader.accept(splatterApplicationVisitor, 0);

        byte[] bytes = applicationWriter.toByteArray();
        zipOutputStream.write(bytes);
    }
}
