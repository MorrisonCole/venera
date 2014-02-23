package com.heisentest.skeletonandroidapp;

import android.app.Activity;

import java.io.File;

public class MainActivity extends Activity {

    public MainActivity() {
        aStaticMethod();
        anInstanceMethod();
    }

    private static void aStaticMethod() {
        HeisentestJsonLogger.log("method name", "class name", "a parameter");
    }

    private void anInstanceMethod() {
        String[] parameterNames = new String[] { "string 1", "string 2" };
        HeisentestJsonLogger.log("method name", parameterNames, this, "a parameter");
    }
}
