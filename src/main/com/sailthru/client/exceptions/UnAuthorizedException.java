package com.sailthru.client.exceptions;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class UnAuthorizedException extends ApiException {
    public UnAuthorizedException(int statusCode, String reason, Object jsonResponse) {
        super(statusCode, reason, jsonResponse);
    }
}
