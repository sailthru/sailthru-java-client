package com.sailthru.client.response;

/**
 *
 * @author Prajwal Tuladhar
 */
public abstract class AbstractResponse implements ApiResponse {
    private Integer errorCode;
    private String errorMessage;

    public AbstractResponse() {
        
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isOk() {
        return (errorCode == null);
    }
}
