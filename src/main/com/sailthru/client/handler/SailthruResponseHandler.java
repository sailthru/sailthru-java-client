package com.sailthru.client.handler;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public interface SailthruResponseHandler {
    public Object parseResponse (String response);
}