package com.heisentest.instrumentation.generator;

import android.app.Activity;
import com.heisentest.instrumentation.logging.JsonLogger;

import java.io.File;

public class MainActivity extends Activity {

    public MainActivity() {
        aMethodThatInstantiatesOurLogger();
        aStaticMethod();
        anInstanceMethod();
    }

    private void aMethodThatInstantiatesOurLogger() {
        JsonLogger jsonLogger = new JsonLogger(new File("path"), "method_name");
        final Thread logThread = new Thread(jsonLogger);
        logThread.start();
    }

    private static void aStaticMethod() {
        JsonLogger.complexLogStaticMethodEntry("method name", "class name", "a parameter");
    }

    private void anInstanceMethod() {
        String[] parameterNames = new String[] { "string 1", "string 2" };
        JsonLogger.complexLogInstanceMethodEntry("method name", parameterNames, this, "a parameter");
    }
}
