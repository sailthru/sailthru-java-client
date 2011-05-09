package com.sailthru.client.response;

/**
 *
 * @author Prajwal Tuladhar
 */
public abstract class AbstractResponse implements ApiResponse {
    private Integer error;
    private String errormsg;

    public Integer getErrorCode() {
        return error;
    }

    public String getErrorMessage() {
        return errormsg;
    }

    public boolean isOk() {
        return (error == null);
    }
}
