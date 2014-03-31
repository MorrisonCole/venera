package com.heisentest.skeletonandroidapp;

import android.app.Activity;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import com.heisentest.splatter.sdk.SplatterIgnore;
import com.heisentest.splatter.sdk.SplatterIgnoreMethodRule;
import org.junit.Rule;

import java.io.File;
import java.lang.reflect.Method;

public class AnInstrumentationTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    @Rule
    SplatterIgnoreMethodRule splatterIgnoreMethodRule = new SplatterIgnoreMethodRule();
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

            if (method.isAnnotationPresent(SplatterIgnore.class)) {
                return;
            }

            File fileDirectory = Environment.getExternalStorageDirectory();
            HeisentestJsonLogger heisentestJsonLogger = new HeisentestJsonLogger(fileDirectory, method.getName());
            logThread = new Thread(heisentestJsonLogger);
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

        HeisentestJsonLogger.endLogging();

        try {
            logThread.join();
        } catch (InterruptedException e) {
            Log.e("HeisentestLogger", "Failed to complete logging", e);
        }
        logging = false;
    }
}
