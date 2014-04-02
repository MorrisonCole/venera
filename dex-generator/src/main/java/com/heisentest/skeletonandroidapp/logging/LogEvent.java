package com.heisentest.skeletonandroidapp.logging;

import java.io.IOException;

public abstract class LogEvent {

    protected String eventName;
    protected long eventTime;
    protected long eventThreadId;

    public String getEventName() {
        return eventName;
    }

    public long getEventTime() {
        return eventTime;
    }

    public long getEventThreadId() {
        return eventThreadId;
    }

    public abstract void write(LogEventWriter logEventWriter) throws IOException;
}
