package com.sailthru.client;

import java.util.Date;

/**
 * POJO holding the X-Rate-Limit-* headers from the last reponse of a given endpoint / method.
 */
public class LastRateLimitInfo {
    /**
     * The value of X-Rate-Limit-Limit, representing the limit of requests/minute for this action / method combination
     */
    private final int limit;

    /**
     * The value of X-Rate-Limit-Remaining, representing how many requests remain in the current minute
     */
    private final int remaining;

    /**
     * The value of X-rate-Limit-Reset, representing the UNIX epoch timestamp of when the next minute starts, and when the rate limit resets
     */
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
