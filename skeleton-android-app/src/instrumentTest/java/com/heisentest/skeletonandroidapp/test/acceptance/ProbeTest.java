package com.heisentest.skeletonandroidapp.test.acceptance;

import android.test.suitebuilder.annotation.SmallTest;
import com.heisentest.skeletonandroidapp.MainActivity;
import com.heisentest.splatter.sdk.Splatter;

import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy.NONE;
import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy.SIMPLE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SmallTest
public class ProbeTest extends SkeletonActivityUnitTestCase<MainActivity> {

    public ProbeTest() {
        super(MainActivity.class);
    }

    @Splatter(instrumentationPolicy = NONE)
    public void testUninstrumentedEmptyMethod() {
        for (int i = 0; i < 100; i++) {
            anUninstrumentedEmptyMethod(i);
        }
    }

    @Splatter(instrumentationPolicy = NONE)
    private void anUninstrumentedEmptyMethod(int unusedParameter) {
    }

    @Splatter(instrumentationPolicy = SIMPLE)
    public void testSimplyInstrumentedEmptyMethod() {
        for (int i = 0; i < 100; i++) {
            aSimplyInstrumentedEmptyMethod(i);
        }
    }

    @Splatter(instrumentationPolicy = SIMPLE)
    private void aSimplyInstrumentedEmptyMethod(int unusedParameter) {
    }

    public void testInstrumentedEmptyMethod() {
        for (int i = 0; i < 100; i++) {
            anInstrumentedEmptyMethod(i);
        }
    }

    private void anInstrumentedEmptyMethod(int unusedParameter) {
    }
}
