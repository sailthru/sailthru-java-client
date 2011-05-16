package com.sailthru.client.exceptions;

/**
 *
 * @author Prajwal Tuladhar
 */
public class UnAuthorizedException extends ApiException {
    public UnAuthorizedException(int statusCode, String reason) {
        super(statusCode, reason);
    }
}
