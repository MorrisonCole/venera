package com.heisentest.skeletonandroidapp.test.acceptance;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import com.google.common.base.Stopwatch;
import com.heisentest.skeletonandroidapp.MainActivity;
import com.heisentest.venera.sdk.Venera;

import static com.heisentest.venera.sdk.Venera.InstrumentationPolicy.NONE;
import static com.heisentest.venera.sdk.Venera.InstrumentationPolicy.SIMPLE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SmallTest
public class ProbeTest extends SkeletonActivityUnitTestCase<MainActivity> {

    public ProbeTest() {
        super(MainActivity.class);
    }

    @Venera(instrumentationPolicy = NONE)
    public void testUninstrumentedEmptyMethod() {
        Stopwatch overallStopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100; i++) {
            anUninstrumentedEmptyMethod(i);
        }
        Log.d("Heisentest Performance", String.format("NONE Total Duration: %s", overallStopwatch.stop()));
    }

    @Venera(instrumentationPolicy = NONE)
    private void anUninstrumentedEmptyMethod(int unusedParameter) {
    }

    @Venera(instrumentationPolicy = SIMPLE)
    public void testSimplyInstrumentedEmptyMethod() {
        Stopwatch overallStopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100; i++) {
            aSimplyInstrumentedEmptyMethod(i);
        }
        Log.d("Heisentest Performance", String.format("SIMPLE Total Duration: %s", overallStopwatch.stop()));
    }

    @Venera(instrumentationPolicy = SIMPLE)
    private void aSimplyInstrumentedEmptyMethod(int unusedParameter) {
    }

    public void testInstrumentedEmptyMethod() {
        Stopwatch overallStopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100; i++) {
            anInstrumentedEmptyMethod(i);
        }
        Log.d("Heisentest Performance", String.format("COMPLEX Total Duration: %s", overallStopwatch.stop()));
    }

    private void anInstrumentedEmptyMethod(int unusedParameter) {
    }
}
