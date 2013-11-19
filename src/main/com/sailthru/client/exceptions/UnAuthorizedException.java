package com.sailthru.client.exceptions;

import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class UnAuthorizedException extends ApiException {
    public UnAuthorizedException(int statusCode, String reason, Map<String, Object> jsonResponse) {
        super(statusCode, reason, jsonResponse);
    }
}
