package com.heisentest.skeletonandroidapp;

import android.os.Environment;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Field;

public final class HeisentestJsonLogger {

    public static final String HEISENTEST_LOGGER_TAG = "HeisentestLogger";
    private static FileWriter fileWriter;
    private static StringWriter stringWriter;
    private static File outputFile;
    private static JsonWriter jsonWriter;
    private static boolean currentlyLogging = false;
    private static final Gson gson = new Gson();
    private static File outputDirectory;

    public static void init(File fileDirectory, String methodName) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(HEISENTEST_LOGGER_TAG, "MEDIA NOT MOUNTED!");
        }

        try {
            outputDirectory = new File(fileDirectory + "/heisentest");
            outputDirectory.mkdirs();

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

            Log.d(HEISENTEST_LOGGER_TAG, String.format("Heisentest output directory: %s", outputDirectory.getAbsolutePath()));

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

    /**
     * Logs a static method call.
     */
    public static void log(String calleeClassName, String calleeMethodName, Object... parameters) {
        if (!currentlyLogging) {
            return;
        }
        try {
            jsonWriter.beginObject();

            jsonWriter.name("class").value(calleeClassName);

            jsonWriter.name("method").value(calleeMethodName);
            if (parameters.length > 0) {
                jsonWriter.name("parameters");
                jsonWriter.beginArray();
                for (Object parameter : parameters) {
                    if (parameter != null) {
                        writeSerializedObjectWithFallback(parameter, parameter.toString());
                    }
                    else {
                        jsonWriter.nullValue();
                    }
                }
                jsonWriter.endArray();
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

    // TODO: should make this thread-safe
    /**
     * Logs an instance method call.
     */
    public static void log(String calleeMethodName, Object callee, Object... parameters) {
        if (!currentlyLogging) {
            return;
        }
        try {
            jsonWriter.beginObject();

            jsonWriter.name("class").value(callee.getClass().toString());

            jsonWriter.name("method").value(calleeMethodName);
            if (parameters.length > 0) {
                jsonWriter.name("parameters");
                jsonWriter.beginArray();
                for (Object parameter : parameters) {
                    if (parameter != null) {
                        writeSerializedObjectWithFallback(parameter, parameter.toString());
                    }
                    else {
                        jsonWriter.nullValue();
                    }
                }
                jsonWriter.endArray();
            }
            final Field[] fields = callee.getClass().getDeclaredFields();
            if (fields.length > 0) {
                jsonWriter.name("fields");
                jsonWriter.beginArray();
                for (Field field : fields) {
                    if (field != null) {
                        field.setAccessible(true);
                        try {
                            Object fieldObject = field.get(callee);
                            if (fieldObject != null) {
                                writeSerializedObjectWithFallback(fieldObject, fieldObject.toString());
                            }
                        } catch (IllegalAccessException e) {
                            Log.d(HEISENTEST_LOGGER_TAG, String.format("Field '%s' could not be accessed", field.toString()), e);
                        }
                    } else {
                        jsonWriter.nullValue();
                    }
                }
                jsonWriter.endArray();
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

    private static void writeSerializedObjectWithFallback(Object object, String fallbackRepresentation) throws IOException {
        try {
            JsonElement element = gson.toJsonTree(object);
            gson.toJson(element, jsonWriter);
        }
        catch (Throwable e) {
            Log.d(HEISENTEST_LOGGER_TAG, String.format("Failed to convert object '%s' to JSON", fallbackRepresentation), e);
            jsonWriter.value(fallbackRepresentation);
        }
    }
}
