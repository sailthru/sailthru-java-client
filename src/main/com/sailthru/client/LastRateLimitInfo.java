package com.sailthru.client;

import java.util.Date;

public class LastRateLimitInfo {
    private final int limit;
    private final int remaining;
    private final Date reset;

    public LastRateLimitInfo(int limit, int remaining, Date reset) {
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
    }

    public int getLimit() {
        return limit;
    }

    public int getRemaining() {
        return remaining;
    }

    public Date getReset() {
        return reset;
    }
}
