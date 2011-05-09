package com.sailthru.client.params;

/**
 *
 * @author Prajwal Tuladhar
 */
public class Alert extends AbstractApiParams {
    protected String email;
    public static enum Type {REALTIME, DAILY, WEEKLY};
    protected String template;
    protected String when;
}
