package com.sailthru.client.handler;

/**
 * Interface for Handling Server Response
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public interface SailthruResponseHandler {
    public Object parseResponse (String response);
    public String getFormat();
}
