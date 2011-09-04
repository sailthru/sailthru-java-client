package com.sailthru.client.handler.response;

import java.util.Map;
import java.util.logging.Logger;


/**
 *
 * @author Prajwal Tuladhar <praj@infynyxx.com>
 */
public class JsonResponse implements Response {
    
    protected Map<String, Object> response = null;
    
    private static final Logger logger = Logger.getLogger(JsonResponse.class.getName());
    
    public JsonResponse(Object response) {
        try {
            this.response = (Map<String, Object>) response;
        } catch (ClassCastException e) {
            logger.severe(e.getMessage());
        }
    }

    public boolean isOK() {
        return !this.response.containsKey("error");
    }

    public Map<String, Object> getResponse() {
        return this.response;
    }
    
}
