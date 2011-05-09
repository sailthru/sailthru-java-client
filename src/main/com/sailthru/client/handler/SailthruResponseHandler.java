package com.sailthru.client.handler;

/**
 * Interface for Handling Server Response
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public interface SailthruResponseHandler {
    public Object parseResponse (String response);
}