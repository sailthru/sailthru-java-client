package com.sailthru.client.params;

import com.sailthru.client.ApiAction;

import java.lang.reflect.Type;

/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public interface ApiParams {
    public Type getType();

    public ApiAction getApiCall();
}
