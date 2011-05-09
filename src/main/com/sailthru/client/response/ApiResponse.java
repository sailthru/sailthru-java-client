package com.sailthru.client.response;

/**
 *
 * @author Prajwal Tuladhar
 */
public interface ApiResponse {
    public int getErrorCode();
    public String getErrorMessage();
    public boolean isOk();
}
