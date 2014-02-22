package com.heisentest.skeletonandroidapp;

import android.os.Environment;
import android.util.JsonWriter;
import android.util.Log;

import java.io.*;

public final class HeisentestJsonLogger {

    public static final String HEISENTEST_LOGGER_TAG = "HeisentestLogger";
    private static FileWriter fileWriter;
    private static StringWriter stringWriter;
    private static File outputFile;
    private static JsonWriter jsonWriter;
    private static boolean currentlyLogging = false;

    public static void init(File fileDirectory, String methodName) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(HEISENTEST_LOGGER_TAG, "MEDIA NOT MOUNTED!");
        }

        try {
            File outputDirectory = new File(fileDirectory + "/heisentest");
            outputDirectory.mkdirs();

            Log.d(HEISENTEST_LOGGER_TAG, String.format("Heisentest output directory: %s", outputDirectory.getAbsolutePath()));

            outputFile = new File(outputDirectory, methodName + ".json");

            fileWriter = new FileWriter(outputFile, false);
            stringWriter = new StringWriter();

            jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.setIndent("  ");
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to create output outputDirectory", e);
        }
    }

    public static void beginLogging() {
        try {
            Log.i(HEISENTEST_LOGGER_TAG, "Trying to begin log...");

            jsonWriter.beginArray();
            currentlyLogging = true;
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to begin logging", e);
        }
    }

    public static void endLogging() {
        try {
            Log.d(HEISENTEST_LOGGER_TAG, "Trying to end log...");

            currentlyLogging = false;
            jsonWriter.endArray();
            jsonWriter.close();

            fileWriter.append(stringWriter.toString());
            stringWriter.getBuffer().setLength(0);
            fileWriter.flush();
            fileWriter.close();
            Log.d(HEISENTEST_LOGGER_TAG, "Ended log.");

            // TODO: For debugging only. This could be massive!
            Log.d(HEISENTEST_LOGGER_TAG, "Printing final output:");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(outputFile));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Log.i(HEISENTEST_LOGGER_TAG, line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to end logging", e);
        }
    }

    // TODO: should make this thread-safe
    public static void log(String calleeClass, String calleeMethodName, Object... parameters) {
        if (!currentlyLogging) {
            return;
        }
        try {
            jsonWriter.beginObject();

            jsonWriter.name("class").value(calleeClass);

            jsonWriter.name("method").value(calleeMethodName);
            jsonWriter.name("parameters");
            if (parameters.length > 0) {
                jsonWriter.beginArray();
                for (Object parameter : parameters) {
                    if (parameter != null) {
                        jsonWriter.value(parameter.toString());
                    }
                    else {
                        jsonWriter.nullValue();
                    }
                }
                jsonWriter.endArray();
            } else {
                jsonWriter.nullValue(); // TODO: remove this if unnecessary.
            }

            jsonWriter.endObject();
            jsonWriter.flush();
            fileWriter.append(stringWriter.toString());
            stringWriter.getBuffer().setLength(0); // TODO: hacky way to 'clear' the StringWriter...
        } catch (IOException e) {
            // TODO: this happens a lot; need a better way to drive the event logging.
//            Log.d(HEISENTEST_LOGGER_TAG, "Failed to write event to XML", e);
        }
    }
}
