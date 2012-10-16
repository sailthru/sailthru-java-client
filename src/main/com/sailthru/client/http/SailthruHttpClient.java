package com.sailthru.client.http;

import com.sailthru.client.AbstractSailthruClient.HttpRequestMethod;
import com.sailthru.client.SailthruClient;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public class SailthruHttpClient extends DefaultHttpClient {

    protected static Logger logger = Logger.getLogger(SailthruHttpClient.class.getName());

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
                logger.info("Making HTTP GET Request");
                HttpGet httpRequest = new HttpGet(urlString + "?" + extractQueryString(nameValuePairs));
                return httpRequest;

            case POST:
                logger.info("Making HTTP POST Request");
                HttpPost httpPost = new HttpPost(urlString);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, SailthruClient.DEFAULT_ENCODING));
                return httpPost;

            case DELETE:
                logger.info("Making HTTP DELETE Request");
                HttpDelete httpDelete = new HttpDelete(urlString + "?" + extractQueryString(nameValuePairs));
                return httpDelete;
        }
        return null;
    }
    
    private HttpUriRequest buildRequest(String urlString, HttpRequestMethod method, Map<String, String> queryParams, Map<String, File> files) throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        
        for( Entry<String, String> entry : queryParams.entrySet() ) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        switch(method) {
            case GET:
                logger.info("Making HTTP GET Request");
                HttpGet httpRequest = new HttpGet(urlString + "?" + extractQueryString(nameValuePairs));
                return httpRequest;

            case POST:
                logger.info("Making HTTP POST Request with multipart");
                HttpPost httpPost = new HttpPost(urlString);
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                for( Entry<String, String> entry : queryParams.entrySet() ) {
                    multipartEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));
                }
                for ( Entry<String, File> fileEntry : files.entrySet() ) {
                    ContentBody contentBody = new FileBody(fileEntry.getValue(), "application/octet-stream");
                    multipartEntity.addPart(fileEntry.getKey(), contentBody);
                }
                httpPost.setEntity(multipartEntity);
                
                return httpPost;

            case DELETE:
                logger.info("Making HTTP DELETE Request");
                HttpDelete httpDelete = new HttpDelete(urlString + "?" + extractQueryString(nameValuePairs));
                return httpDelete;
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
    
    public Object executeHttpRequest(String urlString, HttpRequestMethod method, Map<String, String> params, Map<String, File> fileParams, ResponseHandler<Object> responseHandler, Map<String, String> customHeaders)
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
