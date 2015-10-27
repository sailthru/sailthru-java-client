package com.sailthru.client.exceptions;

import java.util.Map;

/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(int statusCode, String reason,
            Map<String, Object> jsonResponse) {
        super(statusCode, reason, jsonResponse);
    }
}
