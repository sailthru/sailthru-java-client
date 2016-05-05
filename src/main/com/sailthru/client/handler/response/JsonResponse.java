package com.sailthru.client.handler.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 *
 * @author Prajwal Tuladhar <a href="mailto:praj@sailthru.com">praj@sailthru.com</a>
 */
public class JsonResponse implements Response {
    
    protected Map<String, Object> response = null;
    
    private static final Logger logger = LoggerFactory.getLogger(JsonResponse.class);
    
    public JsonResponse(Object response) {
        try {
            this.response = (Map<String, Object>) response;
        } catch (ClassCastException e) {
            logger.error(e.getMessage());
        }
    }

    public boolean isOK() {
        return (this.response != null) && !this.response.containsKey("error");
    }

    public Map<String, Object> getResponse() {
        return this.response;
    }
    
}
