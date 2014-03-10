package com.heisentest.skeletonandroidapp;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Method;

import static android.content.Context.MODE_WORLD_READABLE;

public class AnInstrumentationTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    private static Thread logThread;

    public AnInstrumentationTestCase(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        startLogging();
    }

    public void startLogging() {
        File fileDirectory = Environment.getExternalStorageDirectory();
        try {
            Method method = getClass().getMethod(getName(), (Class[]) null);
            HeisentestJsonLogger heisentestJsonLogger = new HeisentestJsonLogger(fileDirectory, method.getName());
            logThread = new Thread(heisentestJsonLogger);
            logThread.start();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tearDown() throws Exception {
        HeisentestJsonLogger.endLogging();

        try {
            logThread.join();
        } catch (InterruptedException e) {
            Log.e("HeisentestLogger", "Failed to complete logging", e);
        }

        super.tearDown();
    }
}
