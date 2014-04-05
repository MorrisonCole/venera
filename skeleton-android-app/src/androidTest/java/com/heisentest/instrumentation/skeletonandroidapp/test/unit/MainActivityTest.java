package com.heisentest.instrumentation.skeletonandroidapp.test.unit;

import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.heisentest.instrumentation.skeletonandroidapp.MainActivity;

@SmallTest
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testAlwaysSucceeds() {
        assertTrue(true);
    }
}
