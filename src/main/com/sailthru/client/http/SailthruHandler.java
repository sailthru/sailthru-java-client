package com.sailthru.client.http;

import com.sailthru.client.LastRateLimitInfo;
import com.sailthru.client.exceptions.ApiException;
import com.sailthru.client.handler.SailthruResponseHandler;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class SailthruHandler implements ResponseHandler<Object> {

    private SailthruResponseHandler handler;

    private static final Logger logger = LoggerFactory.getLogger(SailthruHandler.class);

    // key used to store rate limit info, for use and removal by the parent SailthruClient
    public static final String RATE_LIMIT_INFO_KEY = "x_rate_limit_info";

    /* Supported HTTP Status codes */
    // These constants are no longer used in this library. Left only for backwards compatibility.
    public static final int STATUS_OK = 200;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_METHOD_NOT_FOUND = 405;
    public static final int STATUS_INTERNAL_SERVER_ERROR = 500;

    public SailthruHandler(SailthruResponseHandler handler) {
        super();
        this.handler = handler;
    }

    public Object handleResponse(HttpResponse httpResponse) throws IOException {
        logger.debug("Received Response: {}", httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();

        String jsonString = EntityUtils.toString(httpResponse.getEntity());
        Object parseObject = handler.parseResponse(jsonString);

        addRateLimitInfoToResponseObject(httpResponse, parseObject);

        // check if status is not successful (i.e. 2xx)
        int statusCode = statusLine.getStatusCode();
        if (statusCode / 100 != 2) {
            throw ApiException.create(statusLine, parseObject);
        }
        return parseObject;
    }


    public void setSailthruResponseHandler(SailthruResponseHandler handler) {
        this.handler = handler;
    }

    public SailthruResponseHandler getSailthruResponseHandler() {
        return handler;
    }

    private void addRateLimitInfoToResponseObject(HttpResponse httpResponse, Object parsedResponse) {
        Header limitHeader = httpResponse.getFirstHeader("X-Rate-Limit-Limit");
        Header remainingHeader = httpResponse.getFirstHeader("X-Rate-Limit-Remaining");
        Header resetHeader = httpResponse.getFirstHeader("X-Rate-Limit-Reset");
        int limit = -1;
        int remaining = -1;
        Date reset = null;
        if (limitHeader != null) {
            try {
                limit = Integer.parseInt(limitHeader.getValue());
            } catch (NumberFormatException ignored) {}
        }
        if (remainingHeader != null) {
            try {
                remaining = Integer.parseInt(remainingHeader.getValue());
            } catch (NumberFormatException ignored) {}
        }
        if (resetHeader != null) {
            try {
                long resetTimestamp = Long.parseLong(resetHeader.getValue());
                reset = new Date(resetTimestamp * 1000);
            } catch (NumberFormatException ignored) {}
        }

        if (limit < 0 || remaining < 0 || reset == null) {
            return;
        }

        Map<String,Object> parseObjectMap = (Map<String,Object>) parsedResponse;
        parseObjectMap.put(RATE_LIMIT_INFO_KEY, new LastRateLimitInfo(limit, remaining, reset));
    }
}
