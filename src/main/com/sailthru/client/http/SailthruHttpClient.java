package com.sailthru.client.http;

import com.sailthru.client.AbstractSailthruClient.HttpRequestMethod;
import com.sailthru.client.response.AbstractResponse;
import com.sailthru.client.response.ApiResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

/**
 *
 * @author Prajwal Tuladhar
 */
public class SailthruHttpClient extends DefaultHttpClient {

    public SailthruHttpClient(ThreadSafeClientConnManager connManager,
			HttpParams params) {
        super(connManager, params);
    }

    private HttpUriRequest buildRequest(String urlString, HttpRequestMethod method, Map<String, String> queryParams) throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        
        for( Entry<String, String> entry : queryParams.entrySet() ) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        switch(method) {
            case GET:
                HttpGet httpRequest = new HttpGet(urlString + "?" + extractQueryString(nameValuePairs));
                return httpRequest;

            case POST:
                HttpPost httpPost = new HttpPost(urlString);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                return httpPost;

            case DELETE:
                HttpDelete httpDelete = new HttpDelete(urlString + "?" + extractQueryString(nameValuePairs));
                return httpDelete;
        }
        return null;
    }

    public Object executeHttpRequest(String urlString, HttpRequestMethod method, Map<String, String> params, ResponseHandler<Object> responseHandler)
            throws IOException {
        HttpUriRequest request = this.buildRequest(urlString, method, params);
        return super.execute(request, responseHandler);
    }

    private String extractQueryString(List<NameValuePair> params) {
        return URLEncodedUtils.format(params, "UTF-8");
    }
}
