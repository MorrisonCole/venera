package com.heisentest.venera;

import com.heisentest.venera.transform.dex.BaseTestCaseClassInfo;
import com.heisentest.venera.transform.dex.InstrumentationSpy;
import com.heisentest.venera.transform.dex.VeneraDexTransformer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class VeneraApkProcessor {

    private final Logger logger = Logger.getLogger(VeneraApkProcessor.class);
    private final int asmApiLevel;
    private final String applicationRootNamespace;
    private final ArrayList<BaseTestCaseClassInfo> baseTestCaseClassInfos;

    public VeneraApkProcessor(int asmApiLevel, String applicationRootNamespace, ArrayList<BaseTestCaseClassInfo> baseTestCaseClassInfos) {
        this.asmApiLevel = asmApiLevel;
        this.applicationRootNamespace = applicationRootNamespace;
        this.baseTestCaseClassInfos = baseTestCaseClassInfos;
    }

    public void process(File inputApkFile, File outputApkFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(inputApkFile);
            FileOutputStream fileOutputStream = new FileOutputStream(outputApkFile);

            final InstrumentationSpy instrumentationSpy = new InstrumentationSpy(applicationRootNamespace, baseTestCaseClassInfos);
            processApk(fileInputStream, fileOutputStream, new VeneraDexTransformer(asmApiLevel, instrumentationSpy));

            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();

            signApk(outputApkFile);
        } catch (IOException e) {
            logger.debug(e);
        }
    }

    private void processApk(FileInputStream fileInputStream, FileOutputStream fileOutputStream, VeneraDexTransformer veneraDexTransformer) throws FileNotFoundException {
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

        try {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                if (fileName.equals("classes.dex")) {
                    logger.debug("Transforming classes.dex");

                    // Temporary file (copy of classes.dex) to read from, since we will be writing directly to the output stream.
                    File temporaryFile = createTemporaryFileFromInputStream(zipInputStream, "classes", "dex");

                    zipOutputStream.putNextEntry(new ZipEntry("classes.dex"));

                    veneraDexTransformer.transform(new FileInputStream(temporaryFile), zipOutputStream);

                    zipOutputStream.closeEntry();
                } else if (fileName.startsWith("META-INF/")) {
                    zipInputStream.closeEntry();
                } else {
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = zipInputStream.read(buf)) > 0) {
                        zipOutputStream.write(buf, 0, len);
                    }
                    zipOutputStream.closeEntry();
                }

                zipInputStream.closeEntry();
            }

            zipInputStream.close();
            zipOutputStream.flush();
            zipOutputStream.close();
        } catch (IOException e) {
            logger.debug(e);
        }
    }

    private File createTemporaryFileFromInputStream(InputStream inputStream, String prefix, String postfix) throws IOException {
        File temporaryFile = File.createTempFile(prefix, postfix);
        FileOutputStream fileOutputStream = new FileOutputStream(temporaryFile);

        byte[] buffer = new byte[512];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, length);
        }

        fileOutputStream.close();

        return temporaryFile;
    }

    private void signApk(File outputApkFile) {
        String outputApkFilePath = outputApkFile.getAbsolutePath();
        logger.info(String.format("Resigning apk (at %s)", outputApkFilePath));
        // TODO: Don't hardcode debug keystore...
        String[] jarSignerArgs = {"-keystore", "/home/morrison/.android/debug.keystore", "-storepass", "android", "-keypass", "android", outputApkFilePath, "androiddebugkey"};
        try {
            sun.security.tools.jarsigner.Main.main(jarSignerArgs);
        } catch (Exception e) {
            logger.log(Level.FATAL, "Failed to re-sign APK", e);
        }
    }
}
