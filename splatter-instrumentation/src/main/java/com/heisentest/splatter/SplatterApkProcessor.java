package com.heisentest.splatter;

import com.heisentest.splatter.transform.dex.SplatterDexTransformer;
import org.apache.log4j.Logger;
import sun.security.tools.JarSigner;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class SplatterApkProcessor {

    private final Logger logger = Logger.getLogger(SplatterApkProcessor.class);
    private final int asmApiLevel;
    private final String applicationRootNamespace;

    public SplatterApkProcessor(int asmApiLevel, String applicationRootNamespace) {
        this.asmApiLevel = asmApiLevel;
        this.applicationRootNamespace = applicationRootNamespace;
    }

    public void process(File inputApkFile, File outputApkFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(inputApkFile);
            FileOutputStream fileOutputStream = new FileOutputStream(outputApkFile);

            processApk(fileInputStream, fileOutputStream, new SplatterDexTransformer(asmApiLevel, applicationRootNamespace));

            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();

            signApk(outputApkFile);
        } catch (IOException e) {
            logger.debug(e);
        }
    }

    private void processApk(FileInputStream fileInputStream, FileOutputStream fileOutputStream, SplatterDexTransformer splatterDexTransformer) throws FileNotFoundException {
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

                    splatterDexTransformer.transform(new FileInputStream(temporaryFile), zipOutputStream);

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
        String[] jarSignerArgs = {"-keystore", "/home/morrison/.android/debug.keystore", "-storepass", "android", "-keypass", "android", outputApkFilePath, "androiddebugkey"};
        JarSigner jarSigner = new JarSigner();
        jarSigner.run(jarSignerArgs);
    }
}
