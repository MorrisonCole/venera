package com.heisentest.generator;

import android.app.Activity;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import com.heisentest.venera.instrumentation.logging.JsonLogger;
import com.heisentest.venera.sdk.Venera;

import java.io.File;
import java.lang.reflect.Method;

public class AnInstrumentationTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    private static Thread logThread;
    private boolean logging;

    public AnInstrumentationTestCase(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        startLogging();
    }

    public void startLogging() {
        logging = false;
        Method method;

        try {
            method = getClass().getMethod(getName(), (Class[]) null);

            final Venera venera = method.getAnnotation(Venera.class);
            if (venera != null && venera.instrumentationPolicy() == Venera.InstrumentationPolicy.NONE) {
                return;
            }

            File fileDirectory = Environment.getExternalStorageDirectory();
            JsonLogger jsonLogger = new JsonLogger(fileDirectory, method.getName());
            logThread = new Thread(jsonLogger);
            logThread.start();
            logging = true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tearDown() throws Exception {
        endLogging();

        super.tearDown();
    }

    private void endLogging() {
        if (!logging) {
            return;
        }

        JsonLogger.endLogging();

        try {
            logThread.join();
        } catch (InterruptedException e) {
            Log.e("HeisentestLogger", "Failed to complete logging", e);
        }
        logging = false;
    }
}
