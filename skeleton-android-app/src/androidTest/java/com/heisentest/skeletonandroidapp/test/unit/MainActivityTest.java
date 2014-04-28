package com.heisentest.skeletonandroidapp.test.unit;

import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.heisentest.skeletonandroidapp.MainActivity;

@SmallTest
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testAlwaysSucceeds() {
        assertTrue(true);
    }

//    private String anExampleMethod(String firstArg, int secondArg) {
//        return appendIntToString(firstArg, secondArg);
//    }
//
//    private static String appendIntToString(String baseString, int number) {
//        return baseString + number;
//    }
}
