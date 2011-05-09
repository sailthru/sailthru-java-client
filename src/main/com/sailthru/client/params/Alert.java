package com.sailthru.client.params;

/**
 *
 * @author Prajwal Tuladhar
 */
public class Alert extends ApiParams {
    protected String email;
    public static enum Type {REALTIME, DAILY, WEEKLY};
    protected String template;
    protected String when;
}
