package com.heisentest.generator;

import android.app.Activity;
import com.heisentest.splatter.instrumentation.logging.JsonLogger;

public class MainActivity extends Activity {

    private static void aComplexStaticMethod() {
        JsonLogger.complexLogStaticMethodEntry("method name", "class name", "a parameter");
    }

    private void aComplexInstanceMethod() {
        String[] parameterNames = new String[] { "string 1", "string 2" };
        JsonLogger.complexLogInstanceMethodEntry(System.currentTimeMillis(), Thread.currentThread(), "method name", parameterNames, this, "a parameter");
    }

    private void aSimpleInstanceMethod() {
        JsonLogger.simpleLogInstanceMethodEntry("method name", this.getClass().getName());
    }
}
