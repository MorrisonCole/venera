package com.heisentest.skeletonandroidapp.logging;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.heisentest.skeletonandroidapp.HeisentestJsonLogger;

import java.io.IOException;
import java.lang.reflect.Field;

import static com.heisentest.skeletonandroidapp.HeisentestJsonLogger.HEISENTEST_LOGGER_TAG;

public class LogEventWriter {

    private static final Gson gson = new Gson();
    private final JsonWriter jsonWriter;

    public LogEventWriter(JsonWriter jsonWriter) {
        this.jsonWriter = jsonWriter;
    }

    public void write(InstanceMethodEntryEvent instanceMethodEntryEvent) throws IOException {
        jsonWriter.beginObject();

        jsonWriter.name("class").value(instanceMethodEntryEvent.getClassName());

        jsonWriter.name("method").value(instanceMethodEntryEvent.getMethodName());
        final Object[] parameters = instanceMethodEntryEvent.getParameters();
        if (parameters.length > 0) {
            jsonWriter.name("parameters");
            jsonWriter.beginArray();
            for (int i = 0; i < parameters.length; i++) {
                jsonWriter.beginObject();
                jsonWriter.name(instanceMethodEntryEvent.getParameterNames()[i]);

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

        final Object callee = instanceMethodEntryEvent.getCallee();
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
    }

    public void write(StaticMethodEntryEvent staticMethodEntryEvent) {

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
