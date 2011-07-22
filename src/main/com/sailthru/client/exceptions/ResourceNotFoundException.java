package com.sailthru.client.exceptions;

/**
 *
 * @author Prajwal Tuladhar
 */
public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(int statusCode, String reason, Object jsonResponse) {
        super(statusCode, reason, jsonResponse);
    }
}
