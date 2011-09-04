package com.sailthru.client.handler.response;

import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <praj@infynyxx.com>
 */
public interface Response {
    public boolean isOK();
    public Map<String, Object> getResponse();
}
