package com.heisentest.instrumentation.logging;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.heisentest.instrumentation.logging.complex.ComplexInstanceMethodEntryEvent;
import com.heisentest.instrumentation.logging.complex.ComplexStaticMethodEntryEvent;
import com.heisentest.instrumentation.logging.simple.SimpleInstanceMethodEntryEvent;

import java.io.IOException;
import java.lang.reflect.Field;

import static com.heisentest.instrumentation.logging.JsonLogger.HEISENTEST_LOGGER_TAG;

public class LogEventWriter {

    private static final Gson gson = new Gson();
    private final JsonWriter jsonWriter;

    public LogEventWriter(JsonWriter jsonWriter) {
        this.jsonWriter = jsonWriter;
    }

    public void write(ComplexInstanceMethodEntryEvent complexInstanceMethodEntryEvent) throws IOException {
        beginEvent(complexInstanceMethodEntryEvent);

        jsonWriter.name("class").value(complexInstanceMethodEntryEvent.getClassName());

        jsonWriter.name("method").value(complexInstanceMethodEntryEvent.getMethodName());
        final Object[] parameters = complexInstanceMethodEntryEvent.getParameters();
        if (parameters.length > 0) {
            jsonWriter.name("parameters");
            jsonWriter.beginArray();
            for (int i = 0; i < parameters.length; i++) {
                jsonWriter.beginObject();
                jsonWriter.name(complexInstanceMethodEntryEvent.getParameterNames()[i]);

                Object parameter = parameters[i];
                if (parameter != null) {
                    writeSerializedObjectWithFallback(parameter, parameter.toString());
                }
                else {
                    jsonWriter.nullValue();
                }

                endEvent();
            }
            jsonWriter.endArray();
        }

        final Object callee = complexInstanceMethodEntryEvent.getCallee();
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
                        endEvent();
                    }
                }
                jsonWriter.endArray();
            }
        }

        endEvent();
    }

    public void write(SimpleInstanceMethodEntryEvent simpleInstanceMethodEntryEvent) throws IOException {
        beginEvent(simpleInstanceMethodEntryEvent);

        jsonWriter.name("class").value(simpleInstanceMethodEntryEvent.getClassName());

        jsonWriter.name("method").value(simpleInstanceMethodEntryEvent.getMethodName());

        endEvent();
    }

    private void beginEvent(LogEvent logEvent) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("eventType").value(logEvent.getEventName());
    }

    private void endEvent() throws IOException {
        jsonWriter.endObject();
    }

    public void write(ComplexStaticMethodEntryEvent complexStaticMethodEntryEvent) {

    }

    private void writeSerializedObjectWithFallback(Object object, String fallbackRepresentation) throws IOException {
        try {
            JsonElement element = gson.toJsonTree(object);
            gson.toJson(element, jsonWriter);
        }
        catch (Throwable e) {
            Log.d(HEISENTEST_LOGGER_TAG, String.format("Failed to convert object '%s' to JSON", fallbackRepresentation));
            jsonWriter.value(fallbackRepresentation);
        }
    }
}
