package com.sailthru.client.handler.response;

import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public interface Response {
    public boolean isOK();
    public Map<String, Object> getResponse();
}
