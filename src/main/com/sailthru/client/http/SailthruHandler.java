package com.sailthru.client.http;

import com.sailthru.client.handler.SailthruResponseHandler;
import com.sailthru.client.response.ApiResponse;
import java.io.IOException;
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

    /* Status codes */
    public static final int STATUS_SUCCESS = 200;


    public SailthruHandler(SailthruResponseHandler handler) {
        super();
        this.handler = handler;
    }

    public Object handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = statusLine.getStatusCode();

        if (statusCode == STATUS_SUCCESS) {
            String jsonString = null;
            jsonString = EntityUtils.toString(httpResponse.getEntity());
            //return jsonString;
            return handler.parseResponse(jsonString);
        }
        else {
            return null;    //todo: handle it properly
        }
    }


    public void setSailthruResponseHandler(SailthruResponseHandler handler) {
        this.handler = handler;
    }
}
