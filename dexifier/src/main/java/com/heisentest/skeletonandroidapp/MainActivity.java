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
        HeisentestJsonLogger.log("method name", this, "a parameter");
    }
}
