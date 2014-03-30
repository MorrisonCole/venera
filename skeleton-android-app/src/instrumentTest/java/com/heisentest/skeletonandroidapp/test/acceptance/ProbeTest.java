package com.heisentest.skeletonandroidapp.test.acceptance;

import android.test.PerformanceTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import com.heisentest.skeletonandroidapp.MainActivity;
import com.heisentest.splatter.sdk.SplatterIgnore;

@LargeTest
public class ProbeTest extends SkeletonInstrumentationTestCase<MainActivity> implements PerformanceTestCase {

    public ProbeTest() {
        super(MainActivity.class);
    }

    public void testInstrumentedEmptyMethod() {
        int i = 0;
        while (i < 10000) {
            anEmptyMethod(i);
        }
    }

    @SplatterIgnore
    public void testUninstrumentedEmptyMethod() {
        int i = 0;
        while (i < 10000) {
            anEmptyMethod(i);
        }
    }

    /**
     * Intentionally does nothing, since this will be instrumented.
     */
    private void anEmptyMethod(int unusedParameter) {
    }

    @Override
    public int startPerformance(Intermediates intermediates) {
        return 1;
    }

    @Override
    public boolean isPerformanceOnly() {
        return false;
    }
}
