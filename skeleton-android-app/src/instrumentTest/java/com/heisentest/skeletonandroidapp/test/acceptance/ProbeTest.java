package com.heisentest.skeletonandroidapp.test.acceptance;

import com.heisentest.skeletonandroidapp.MainActivity;
import com.heisentest.splatter.sdk.SplatterIgnore;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProbeTest extends SkeletonActivityUnitTestCase<MainActivity> {

    public ProbeTest() {
        super(MainActivity.class);
    }

    public void testInstrumentedEmptyMethod() {
        for (int i = 0; i < 100; i++) {
            anInstrumentedEmptyMethod(i);
        }

        assertThat(false, is(false));
    }

    private void anInstrumentedEmptyMethod(int unusedParameter) {
    }

    @SplatterIgnore
    public void testUninstrumentedEmptyMethod() {
        for (int i = 0; i < 100; i++) {
            anUninstrumentedEmptyMethod(i);
        }

        assertThat(false, is(false));
    }

    @SplatterIgnore
    private void anUninstrumentedEmptyMethod(int unusedParameter) {
    }
}
