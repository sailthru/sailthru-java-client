package com.sailthru.client.http;

import com.sailthru.client.AbstractSailthruClient.HttpRequestMethod;
import com.sailthru.client.SailthruClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SailthruHttpClient extends DefaultHttpClient {
    public SailthruHttpClient(ThreadSafeClientConnManager connManager,
			HttpParams params) {
        super(connManager, params);
    }

    private HttpUriRequest buildRequest(String urlString, HttpRequestMethod method, Map<String, String> queryParams) throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        for (Entry<String, String> entry : queryParams.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        switch(method) {
            case GET:
                return new HttpGet(urlString + "?" + extractQueryString(nameValuePairs));

            case POST:
                HttpPost httpPost = new HttpPost(urlString);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, SailthruClient.DEFAULT_ENCODING));
                return httpPost;

            case DELETE:
                return new HttpDelete(urlString + "?" + extractQueryString(nameValuePairs));
        }
        return null;
    }

    private HttpUriRequest buildRequest(String urlString, HttpRequestMethod method, Map<String, String> queryParams, Map<String, InputStream> files) throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        for( Entry<String, String> entry : queryParams.entrySet() ) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        switch(method) {
            case GET:
                return new HttpGet(urlString + "?" + extractQueryString(nameValuePairs));

            case POST:
                HttpPost httpPost = new HttpPost(urlString);
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                for (Entry<String, String> entry : queryParams.entrySet()) {
                    builder.addTextBody(entry.getKey(), entry.getValue());
                }
                for (Entry<String, InputStream> fileEntry : files.entrySet()) {
                    final String fileKey = fileEntry.getKey();
                    final InputStream file = fileEntry.getValue();
                    final String filename = "import_job_data.csv";
                    builder.addBinaryBody(fileKey, file, ContentType.APPLICATION_OCTET_STREAM, filename);
                }
                httpPost.setEntity(builder.build());

                return httpPost;

            case DELETE:
                return new HttpDelete(urlString + "?" + extractQueryString(nameValuePairs));
        }
        return null;
    }

    public Object executeHttpRequest(String urlString, HttpRequestMethod method, Map<String, String> params, ResponseHandler<Object> responseHandler, Map<String, String> customHeaders)
            throws IOException {
        HttpUriRequest request = this.buildRequest(urlString, method, params);
        if (customHeaders != null && customHeaders.size() > 0) {
            for (Map.Entry<String, String> entry : customHeaders.entrySet()) {
                final String key = entry.getKey();
                final String value = entry.getValue();
                request.setHeader(key, value);
            }
        }
        
        return super.execute(request, responseHandler);
    }
    
    public Object executeHttpRequest(String urlString, HttpRequestMethod method, Map<String, String> params, Map<String, InputStream> fileParams, ResponseHandler<Object> responseHandler, Map<String, String> customHeaders)
            throws IOException {
        HttpUriRequest request = this.buildRequest(urlString, method, params, fileParams);
        if (customHeaders != null && customHeaders.size() > 0) {
            for (Map.Entry<String, String> entry : customHeaders.entrySet()) {
                final String key = entry.getKey();
                final String value = entry.getValue();
                request.setHeader(key, value);
            }
        }
        return super.execute(request, responseHandler);
    }

    private String extractQueryString(List<NameValuePair> params) {
        return URLEncodedUtils.format(params, SailthruClient.DEFAULT_ENCODING);
    }
}
