package com.sailthru.client.params;

import com.sailthru.client.ApiAction;

import java.lang.reflect.Type;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public interface ApiParams {
    public Type getType();

    public ApiAction getApiCall();
}
