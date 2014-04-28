package com.heisentest.skeletonandroidapp.test.acceptance;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import com.google.common.base.Stopwatch;
import com.heisentest.skeletonandroidapp.MainActivity;
import com.heisentest.splatter.sdk.Splatter;

import java.util.concurrent.TimeUnit;

import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy.NONE;
import static com.heisentest.splatter.sdk.Splatter.InstrumentationPolicy.SIMPLE;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SmallTest
public class ProbeTest extends SkeletonActivityUnitTestCase<MainActivity> {

    public ProbeTest() {
        super(MainActivity.class);
    }

    @Splatter(instrumentationPolicy = NONE)
    public void testUninstrumentedEmptyMethod() {
        Stopwatch overallStopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100; i++) {
            anUninstrumentedEmptyMethod(i, i, i, i);
        }
        Log.d("Heisentest Performance", String.format("NONE Total Duration: %s", overallStopwatch.stop()));
    }

    @Splatter(instrumentationPolicy = NONE)
    private void anUninstrumentedEmptyMethod(int unusedParameter, int i, int i1, int i2) {
    }

    @Splatter(instrumentationPolicy = SIMPLE)
    public void testSimplyInstrumentedEmptyMethod() {
        Stopwatch overallStopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100; i++) {
            aSimplyInstrumentedEmptyMethod(i, i, i, i);
        }
        Log.d("Heisentest Performance", String.format("SIMPLE Total Duration: %s", overallStopwatch.stop()));
    }

    @Splatter(instrumentationPolicy = SIMPLE)
    private void aSimplyInstrumentedEmptyMethod(int unusedParameter, int i, int i1, int i2) {
    }

    public void testInstrumentedEmptyMethod() {
        Stopwatch overallStopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100; i++) {
            anInstrumentedEmptyMethod(i, i, i, i);
        }
        Log.d("Heisentest Performance", String.format("COMPLEX Total Duration: %s", overallStopwatch.stop()));
    }

    private void anInstrumentedEmptyMethod(int unusedParameter, int i, int i1, int i2) {
    }
}
