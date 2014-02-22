package com.heisentest.skeletonandroidapp;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Method;

import static android.content.Context.MODE_WORLD_READABLE;

public class AnInstrumentationTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

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
            HeisentestXmlLogger.init(fileDirectory, method.getName());
            HeisentestXmlLogger.beginLogging();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
