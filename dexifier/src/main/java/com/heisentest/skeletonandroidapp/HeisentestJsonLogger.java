package com.heisentest.skeletonandroidapp;

import android.os.Environment;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.heisentest.skeletonandroidapp.logging.LogEvent;

import java.io.*;
import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public final class HeisentestJsonLogger implements Runnable {

    public static final String HEISENTEST_LOGGER_TAG = "HeisentestLogger";
    private static final int DEFAULT_QUEUE_CAPACITY = 10;
    private static FileWriter fileWriter;
    private static StringWriter stringWriter;
    private static File outputFile;
    private static JsonWriter jsonWriter;
    private volatile static boolean currentlyLogging = false;
    private static final Gson gson = new Gson();
    private static File outputDirectory;
    private static final BlockingQueue<LogEvent> blockingQueue = new ArrayBlockingQueue<>(DEFAULT_QUEUE_CAPACITY);

    public HeisentestJsonLogger(File fileDirectory, String methodName) {
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

    private void beginLogging() {
        try {
            Log.i(HEISENTEST_LOGGER_TAG, "Trying to begin log...");

            jsonWriter.beginArray();
            currentlyLogging = true;
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to begin logging", e);
        }
    }

    public static void endLogging() {
        Log.d(HEISENTEST_LOGGER_TAG, "Received request to end logging");

        currentlyLogging = false;
    }

    /**
     * Logs an instance method call.
     */
    public static void log(String calleeMethodName, String[] parameterNames, Object callee, Object... parameters) {
        if (!currentlyLogging) {
            Log.d(HEISENTEST_LOGGER_TAG, "Requested log instance event but finished logging");
            return;
        }

        Log.d(HEISENTEST_LOGGER_TAG, "Logging instance event");

        queueLogEvent(new LogEvent(calleeMethodName, parameterNames, callee, parameters));
    }

    /**
     * Logs a static method call.
     */
    public static void log(String calleeClassName, String calleeMethodName, Object... parameters) {
        Log.d(HEISENTEST_LOGGER_TAG, "Logging static event");

        queueLogEvent(new LogEvent(calleeClassName, calleeMethodName, parameters));
    }

    private static void queueLogEvent(LogEvent logEvent) {
        try {
            blockingQueue.put(logEvent);
        } catch (InterruptedException e) {
            Log.d(HEISENTEST_LOGGER_TAG, "Interrupted while putting log event into queue", e);
        }
    }

    @Override
    public void run() {
        beginLogging();

        while (currentlyLogging) {
            try {
                final LogEvent logEvent = blockingQueue.poll(1, TimeUnit.MILLISECONDS);
                if (logEvent != null) {
                    flush(logEvent);
                }
            } catch (InterruptedException e) {
                Log.d(HEISENTEST_LOGGER_TAG, "Interrupted while retrieving event from queue", e);
            }
        }

        cleanUpLogQueue();
    }

    private static void flush(LogEvent logEvent) {
        try {
            Log.d(HEISENTEST_LOGGER_TAG, "Flushing event");
            jsonWriter.beginObject();

            jsonWriter.name("class").value(logEvent.getCalleeClassName());

            jsonWriter.name("method").value(logEvent.getCalleeMethodName());
            final Object[] parameters = logEvent.getParameters();
            if (parameters.length > 0) {
                jsonWriter.name("parameters");
                jsonWriter.beginArray();
                for (int i = 0; i < parameters.length; i++) {
                    jsonWriter.beginObject();
                    jsonWriter.name(logEvent.getParameterNames()[i]);

                    Object parameter = parameters[i];
                    if (parameter != null) {
                        writeSerializedObjectWithFallback(parameter, parameter.toString());
                    }
                    else {
                        jsonWriter.nullValue();
                    }

                    jsonWriter.endObject();
                }
                jsonWriter.endArray();
            }

            final Object callee = logEvent.getCallee();
            if (callee != null) {
                final Field[] fields = callee.getClass().getDeclaredFields();
                if (fields.length > 0) {
                    jsonWriter.name("fields");
                    jsonWriter.beginArray();
                    for (Field field : fields) {
                        if (field != null) {
                            jsonWriter.beginObject();

                            // TODO: This can be done after the tests have run. Needless overhead.
                            jsonWriter.name(field.toString().substring(field.toString().lastIndexOf('.') + 1));
                            field.setAccessible(true);
                            try {
                                Object fieldObject = field.get(callee);
                                if (fieldObject != null) {
                                    writeSerializedObjectWithFallback(fieldObject, fieldObject.toString());
                                } else {
                                    jsonWriter.nullValue();
                                }
                            } catch (IllegalAccessException e) {
                                Log.d(HEISENTEST_LOGGER_TAG, String.format("Field '%s' could not be accessed", field.toString()), e);
                            }
                            jsonWriter.endObject();
                        }
                    }
                    jsonWriter.endArray();
                }
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
            Log.d(HEISENTEST_LOGGER_TAG, String.format("Failed to convert object '%s' to JSON", fallbackRepresentation));
            jsonWriter.value(fallbackRepresentation);
        }
    }

    private void cleanUpLogQueue() {
        try {
            Log.d(HEISENTEST_LOGGER_TAG, "Trying to end log...");

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
}
