package com.heisentest.skeletonandroidapp;

import android.util.Log;
import android.util.Xml;
import com.example.android_source.R;
import org.xmlpull.v1.XmlSerializer;

import java.io.*;

public final class HeisentestXmlLogger {

    public static final String DEFAULT_OUTPUT_LOCATION = "/heisentestoutput.xml";
    public static final String HEISENTEST_LOGGER_TAG = "HeisentestLogger";
    public static final String HEISENTEST_NAMESPACE = "heisentest";
    public static final String NO_NAMESPACE = null;
    private static XmlSerializer serializer;
    private static FileWriter fileWriter;
    private static StringWriter stringWriter;
    private static File file;

    public static void init(File fileDirectory) {
        serializer = Xml.newSerializer();
        try {
            String filename = fileDirectory.getAbsolutePath() + DEFAULT_OUTPUT_LOCATION;
            file = new File(filename);
            fileWriter = new FileWriter(file, false);
            stringWriter = new StringWriter();
            serializer.setOutput(stringWriter);
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to create output file", e);
        }
    }

    public static void beginLogging() {
        try {
            Log.i(HEISENTEST_LOGGER_TAG, "Trying to begin log...");
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startDocument("UTF-8", true);
            serializer.startTag(HEISENTEST_NAMESPACE, "log");
            serializer.attribute(NO_NAMESPACE, "version", "0.0.1");
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to begin logging", e);
        }
    }

    public static void endLogging() {
        try {
            Log.d(HEISENTEST_LOGGER_TAG, "Trying to end log...");
            serializer.endTag(HEISENTEST_NAMESPACE, "log");
            serializer.endDocument();
            serializer.flush();
            fileWriter.append(stringWriter.toString());
            stringWriter.getBuffer().setLength(0);
            fileWriter.flush();
            fileWriter.close();
            Log.d(HEISENTEST_LOGGER_TAG, "Ended log.");

            Log.d(HEISENTEST_LOGGER_TAG, String.format("Setting output file (at '%s') readable...", file.getAbsolutePath()));
            boolean setReadable = file.setReadable(true, false);
            if (!setReadable) {
                Log.e(HEISENTEST_LOGGER_TAG, "Failed to make output file readable!");
            }

            // TODO: For debugging only. This could be massive!
            Log.d(HEISENTEST_LOGGER_TAG, "Successfully made output file readable. Printing final output:");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Log.i(HEISENTEST_LOGGER_TAG, line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to end logging", e);
        }
    }

    public static void log(String calleeClass, String calleeMethodName, Object... parameters) {
        try {
            serializer.startTag(NO_NAMESPACE, "event");

            serializer.startTag(NO_NAMESPACE, "class");
            serializer.attribute(NO_NAMESPACE, "name", calleeClass);

            serializer.startTag(NO_NAMESPACE, "method");
            serializer.attribute(NO_NAMESPACE, "name", calleeMethodName);
            if (parameters.length > 0) {
                for (Object parameter : parameters) {
                    if (parameter != null) {
                        serializer.startTag(NO_NAMESPACE, "parameter");
                        serializer.text(parameter.toString());
                        serializer.endTag(NO_NAMESPACE, "parameter");
                    }
                }
            }
            serializer.endTag(NO_NAMESPACE, "method");

            serializer.endTag(NO_NAMESPACE, "class");

            serializer.endTag(NO_NAMESPACE, "event");

            serializer.flush();
            fileWriter.append(stringWriter.toString());
            stringWriter.getBuffer().setLength(0); // TODO: hacky way to 'clear' the StringWriter...
        } catch (IOException e) {
            Log.d(HEISENTEST_LOGGER_TAG, "Failed to write event to XML", e);
        }
    }
}
