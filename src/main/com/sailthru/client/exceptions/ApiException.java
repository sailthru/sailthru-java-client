package com.sailthru.client.exceptions;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.http.StatusLine;

/**
 * Handle API related Exceptions
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class ApiException extends IOException {

    private static final Logger logger = Logger.getLogger(ApiException.class.getName());

    private Map<String, Object> jsonResponse;
    private int statusCode;

    public ApiException(int statusCode, String reason, Object jsonResponse) {
        super(reason);
        logger.warning(String.format("%d: %s", statusCode, reason));
        this.jsonResponse = (Map<String, Object>)jsonResponse;
        this.statusCode = statusCode;
    }

    public Map<String, Object> getResponse() {
        return jsonResponse;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static ApiException create(StatusLine statusLine, Object jsonResponse) {
        int statusCode = statusLine.getStatusCode();
        Map<String, Object> response = (Map<String, Object>)jsonResponse;
        return new ApiException(statusCode, response.get("errormsg").toString(), jsonResponse);
    }
}
