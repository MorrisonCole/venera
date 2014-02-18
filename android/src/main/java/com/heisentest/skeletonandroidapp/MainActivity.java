package com.heisentest.skeletonandroidapp;

import android.app.Activity;
import android.util.Log;
import com.example.android_source.R;

import java.io.File;
import java.util.zip.ZipEntry;

public class MainActivity extends Activity {

    public MainActivity() {
        HeisentestLogger.log(this.toString(), "A string argument");
        int[] someArray = new int[3];
        aMethodWithArguments(someArray);
    }

    private void aMethodWithArguments(int[] anArray) {
        HeisentestLogger.log(this.toString(), "aMethodWithArguments", anArray);
    }

    private void aMethodWithNoArguments() {
        HeisentestLogger.log(this.toString(), "aMethodWithArguments");
    }

    public void startLogging() {
        File fileDirectory = getApplicationContext().getDir("heisentest", MODE_WORLD_READABLE);
        HeisentestXmlLogger.init(fileDirectory);
        HeisentestXmlLogger.beginLogging();
        HeisentestXmlLogger.endLogging();
    }

//    private void aMethodWithNoBullshit() {
//        HeisentestLogger.log("this class", "aMethodWithArguments", "an arg");
//        aMethodWithArguments("some things");
//        onCreate(null);
//    }
}
