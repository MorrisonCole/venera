package com.heisentest.instrumentation.skeletonandroidapp.test.acceptance;

import android.app.Activity;
import android.test.ActivityUnitTestCase;

public class SkeletonActivityUnitTestCase<T extends Activity> extends ActivityUnitTestCase<T> {

    public SkeletonActivityUnitTestCase(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
