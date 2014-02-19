package com.heisentest.skeletonandroidapp.test.acceptance;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Window;
import android.view.WindowManager;

/**
 * Provides functional testing of an Activity.
 *
 * Wakes and unlocks the device without the need to extra permissions.
 */
public class SkeletonInstrumentationTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    public SkeletonInstrumentationTestCase(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        final T activity = getActivity();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wakeUpDevice(activity);
            }
        });
    }

    private void wakeUpDevice(T activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
