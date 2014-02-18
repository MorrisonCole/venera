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
    public static final String NO_NAMESPACE = "";
    private static XmlSerializer serializer;
    private static FileWriter fileWriter;
    private static StringWriter stringWriter;

    public static void init(File fileDirectory) {
        serializer = Xml.newSerializer();
        try {
            String filename = fileDirectory.getAbsolutePath() + DEFAULT_OUTPUT_LOCATION;
            File file = new File(filename);
            file.setReadable(true, false); // Not a security risk since we're just debugging.
            fileWriter = new FileWriter(file);
            stringWriter = new StringWriter();
            serializer.setOutput(stringWriter);
            beginLogging();
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to create output file", e);
        }
    }

    public static void beginLogging() {
        try {
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startDocument("UTF-8", true);
            serializer.startTag(NO_NAMESPACE, "log");
            serializer.attribute(NO_NAMESPACE, "version", "0.0.1");
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to begin logging", e);
        }
    }

    public static void endLogging() {
        try {
            serializer.endTag(NO_NAMESPACE, "log");
            serializer.endDocument();
            Log.d(HEISENTEST_LOGGER_TAG, String.format("Ending log: \n %s", stringWriter.toString()));
            fileWriter.append(stringWriter.toString());
            fileWriter.close();
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to end logging", e);
        }
    }

    public static void log(String calleeClass, String calleeMethodName, Object... parameters) {
        try {
            serializer.startTag(NO_NAMESPACE, "event");

            serializer.startTag(NO_NAMESPACE, "class");
            serializer.text(calleeClass);
            serializer.endTag(NO_NAMESPACE, "class");

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

            serializer.endTag(NO_NAMESPACE, "event");

            fileWriter.append(stringWriter.toString());
        } catch (IOException e) {
            Log.d(HEISENTEST_LOGGER_TAG, "Failed to write event to XML", e);
        }
    }
}
