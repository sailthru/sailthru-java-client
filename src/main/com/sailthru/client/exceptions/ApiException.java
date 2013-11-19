package com.sailthru.client.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle API related Exceptions
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
@SuppressWarnings("serial")
public class ApiException extends IOException {

    private static final String ERRORMSG = "errormsg";

    private static final Logger logger = LoggerFactory.getLogger(ApiException.class);

    private Map<String, Object> jsonResponse;
    private int statusCode;

    public ApiException(int statusCode, String reason, Map<String, Object> jsonResponse) {
        super(reason);
        logger.warn("{}: {}", statusCode, reason);
        this.jsonResponse = jsonResponse;
        this.statusCode = statusCode;
    }

    public Map<String, Object> getResponse() {
        return jsonResponse;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @SuppressWarnings("unchecked")
    public static ApiException create(StatusLine statusLine, Object jsonResponse) {
        return create(statusLine, (Map<String, Object>)jsonResponse);
    }

    public static ApiException create(StatusLine statusLine, Map<String, Object> jsonResponse) {
        if (jsonResponse == null) {
            jsonResponse = new HashMap<String, Object>();
        }

        final String errorMessage = jsonResponse.get(ERRORMSG) == null ? "" :
            jsonResponse.get(ERRORMSG).toString();

        return new ApiException(statusLine.getStatusCode(), errorMessage, jsonResponse);
    }
}
