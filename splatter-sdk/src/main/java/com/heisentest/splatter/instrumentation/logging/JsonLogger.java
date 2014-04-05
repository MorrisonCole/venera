package com.heisentest.splatter.instrumentation.logging;

import android.os.Environment;
import android.util.Log;
import com.google.gson.stream.JsonWriter;
import com.heisentest.splatter.instrumentation.logging.complex.ComplexInstanceMethodEntryEvent;
import com.heisentest.splatter.instrumentation.logging.complex.ComplexStaticMethodEntryEvent;
import com.heisentest.splatter.instrumentation.logging.simple.SimpleInstanceMethodEntryEvent;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.heisentest.splatter.instrumentation.logging.complex.ComplexInstanceMethodEntryEvent.Builder.complexInstanceMethodEntryEvent;
import static com.heisentest.splatter.instrumentation.logging.complex.ComplexStaticMethodEntryEvent.Builder.staticMethodEntryEvent;
import static com.heisentest.splatter.instrumentation.logging.simple.SimpleInstanceMethodEntryEvent.Builder.simpleInstanceMethodEntryEvent;

public final class JsonLogger implements Runnable {

    public static final String HEISENTEST_LOGGER_TAG = "HeisentestLogger";
    private static final int DEFAULT_QUEUE_CAPACITY = 10;
    private static FileWriter fileWriter;
    private static StringWriter stringWriter;
    private static File outputFile;
    private static JsonWriter jsonWriter;
    private volatile static boolean currentlyLogging = false;
    private static File outputDirectory;
    private static final BlockingQueue<LogEvent> blockingQueue = new ArrayBlockingQueue<LogEvent>(DEFAULT_QUEUE_CAPACITY);
    private static LogEventWriter logEventWriter;

    public JsonLogger(File fileDirectory, String methodName) {
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

            logEventWriter = new LogEventWriter(jsonWriter);
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

    public static void simpleLogInstanceMethodEntry(String calleeClassName, String calleeMethodName) {
        if (warnIfNotLogging()) return;

        SimpleInstanceMethodEntryEvent simpleInstanceMethodEntryEvent = simpleInstanceMethodEntryEvent()
                .withClassName(calleeClassName)
                .withMethodName(calleeMethodName)
                .build();

        queueLogEvent(simpleInstanceMethodEntryEvent);
    }

    public static void complexLogInstanceMethodEntry(String calleeMethodName, String[] parameterNames, Object callee, Object... parameters) {
        if (warnIfNotLogging()) return;

        final ComplexInstanceMethodEntryEvent complexInstanceMethodEntryEvent = complexInstanceMethodEntryEvent()
                .withMethodName(calleeMethodName)
                .withClassName(callee.getClass().getName())
                .withCallee(callee)
                .withParameters(parameters)
                .withParameterNames(parameterNames)
                .build();

        queueLogEvent(complexInstanceMethodEntryEvent);
    }

    public static void complexLogStaticMethodEntry(String calleeClassName, String calleeMethodName, Object... parameters) {
        if (warnIfNotLogging()) return;

        final ComplexStaticMethodEntryEvent complexStaticMethodEntryEvent = staticMethodEntryEvent()
                .withClassName(calleeClassName)
                .withMethodName(calleeMethodName)
                .withParameters(parameters)
                .build();

        queueLogEvent(complexStaticMethodEntryEvent);
    }

    private static boolean warnIfNotLogging() {
        if (!currentlyLogging) {
            Log.v(HEISENTEST_LOGGER_TAG, "Requested log event but finished logging");
            return true;
        }

        Log.v(HEISENTEST_LOGGER_TAG, "Logging event");

        return false;
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

        while (currentlyLogging || !blockingQueue.isEmpty()) {
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
            Log.v(HEISENTEST_LOGGER_TAG, "Flushing event");

            logEvent.write(logEventWriter);

            jsonWriter.flush();
            fileWriter.append(stringWriter.toString());
            stringWriter.getBuffer().setLength(0); // TODO: hacky way to 'clear' the StringWriter...
        } catch (IOException e) {
            // TODO: this happens a lot; need a better way to drive the event logging.
//            Log.d(HEISENTEST_LOGGER_TAG, "Failed to write event to XML", e);
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

            Log.i(HEISENTEST_LOGGER_TAG, String.format("Heisentest output directory: %s", outputDirectory.getAbsolutePath()));

            Log.v(HEISENTEST_LOGGER_TAG, "Printing final output:");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(outputFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Log.v(HEISENTEST_LOGGER_TAG, line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e(HEISENTEST_LOGGER_TAG, "Failed to end logging", e);
        }
    }
}
