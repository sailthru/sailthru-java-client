package com.sailthru.client.http;

import com.sailthru.client.exceptions.ApiException;
import com.sailthru.client.exceptions.ResourceNotFoundException;
import com.sailthru.client.exceptions.UnAuthorizedException;
import com.sailthru.client.handler.SailthruResponseHandler;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Prajwal Tuladhar
 */
public class SailthruHandler implements ResponseHandler<Object> {

    private SailthruResponseHandler handler;

    private static Logger logger = Logger.getLogger(SailthruHandler.class.getName());

    /* Supported HTTP Status codes */
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

    public Object handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        logger.info("Received Response: " + httpResponse.toString());

        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        HttpEntity httpEntity = httpResponse.getEntity();

        String jsonString = null;
        jsonString = EntityUtils.toString(httpResponse.getEntity());
        Object parseObject =  handler.parseResponse(jsonString);

        switch (statusCode) {
            case STATUS_OK:
                break;

            case STATUS_BAD_REQUEST:
                throw ApiException.create(httpEntity, statusLine, parseObject);

            case STATUS_UNAUTHORIZED:
                throw UnAuthorizedException.create(httpEntity, statusLine, parseObject);

            case STATUS_FORBIDDEN:
                throw ApiException.create(httpEntity, statusLine, parseObject);

            case STATUS_NOT_FOUND:
                throw ResourceNotFoundException.create(httpEntity, statusLine, parseObject);

            case STATUS_METHOD_NOT_FOUND:
                throw ApiException.create(httpEntity, statusLine, parseObject);

            case STATUS_INTERNAL_SERVER_ERROR:
                throw ApiException.create(httpEntity, statusLine, parseObject);

            default:
                throw ApiException.create(httpEntity, statusLine, parseObject);
        }
        
        return parseObject;
    }


    public void setSailthruResponseHandler(SailthruResponseHandler handler) {
        this.handler = handler;
    }

    public SailthruResponseHandler getSailthruResponseHandler() {
        return handler;
    }
}
