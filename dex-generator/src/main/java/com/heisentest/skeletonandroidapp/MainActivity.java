package com.heisentest.skeletonandroidapp;

import android.app.Activity;

import java.io.File;

public class MainActivity extends Activity {

    public MainActivity() {
        aMethodThatInstantiatesOurLogger();
        aStaticMethod();
        anInstanceMethod();
    }

    private void aMethodThatInstantiatesOurLogger() {
        HeisentestJsonLogger heisentestJsonLogger = new HeisentestJsonLogger(new File("path"), "method_name");
        final Thread logThread = new Thread(heisentestJsonLogger);
        logThread.start();
    }

    private static void aStaticMethod() {
        HeisentestJsonLogger.complexLogStaticMethodEntry("method name", "class name", "a parameter");
    }

    private void anInstanceMethod() {
        String[] parameterNames = new String[] { "string 1", "string 2" };
        HeisentestJsonLogger.complexLogInstanceMethodEntry("method name", parameterNames, this, "a parameter");
    }
}
