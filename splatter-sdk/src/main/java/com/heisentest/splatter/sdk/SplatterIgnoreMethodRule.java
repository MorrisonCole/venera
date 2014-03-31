package com.heisentest.splatter.sdk;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class SplatterIgnoreMethodRule extends TestWatcher {

    private boolean ignored;

    @Override
    protected void starting(Description description) {
        ignored = description.getAnnotation(SplatterIgnore.class) != null;
    }

    public boolean currentTestIsIgnored() {
        return ignored;
    }
}
