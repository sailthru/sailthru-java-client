package com.sailthru.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sailthru.client.handler.JsonHandler;
import com.sailthru.client.handler.SailthruResponseHandler;
import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.http.SailthruHandler;
import com.sailthru.client.http.SailthruHttpClient;
import com.sailthru.client.params.ApiFileParams;
import com.sailthru.client.params.ApiParams;
import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * Abstract class exposing genric API calls for Sailthru API as per http://docs.sailthru.com/api
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public abstract class AbstractSailthruClient {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractSailthruClient.class);

    public static final String DEFAULT_API_URL = "https://api.sailthru.com";
    public static final int DEFAULT_HTTP_PORT = 80;
    public static final int DEFAULT_HTTPS_PORT = 443;
    public static final String DEFAULT_USER_AGENT = "Sailthru Java Client";
    public static final String VERSION = "1.0";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60 * 1000; //default connection timeout of 60 seconds
    public static final int DEFAULT_SOCKET_TIMEOUT = 60 * 1000; //default socket activity timeout

    protected static enum HandlerType { JSON };    //we can also add XML but not now!
    public static enum HttpRequestMethod {GET, POST, DELETE}; //HTTP methods supported by Sailthru API

    protected String apiKey;
    protected String apiSecret;
    protected String apiUrl;
    private final HttpParams params = new BasicHttpParams();

    protected SailthruHttpClient httpClient;

    private SailthruHandler handler;

    protected Gson gson;
    
    private Map<String, String> customHeaders = null;


    /**
     * Main constructor class for setting up the client
     * @param apiKey
     * @param apiSecret
     * @param apiUrl
     * @param connectionTimeout
     * @param socketTimeout
     */
    public AbstractSailthruClient(String apiKey, String apiSecret, String apiUrl, int connectionTimeout, int socketTimeout) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.apiUrl = apiUrl;
        this.handler = new SailthruHandler(new JsonHandler());
        this.httpClient = create(connectionTimeout, socketTimeout);
        this.gson = SailthruUtil.createGson();
    }

    /**
     * Main constructor class for setting up the client
     * @param apiKey
     * @param apiSecret
     * @param apiUrl
     */
    public AbstractSailthruClient(String apiKey, String apiSecret, String apiUrl) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.apiUrl = apiUrl;
        this.handler = new SailthruHandler(new JsonHandler());
        this.httpClient = create(DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
        this.gson = SailthruUtil.createGson();
    }


    /**
     * Create SailthruHttpClient
     */
    private SailthruHttpClient create(int connectionTimeout, int socketTimeout) {
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, DEFAULT_ENCODING);
        HttpProtocolParams.setUserAgent(params, DEFAULT_USER_AGENT);
        HttpProtocolParams.setUseExpectContinue(params, true);
        HttpConnectionParams.setSoTimeout(params, socketTimeout);
        HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(getScheme());

        ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager(schemeRegistry);
        SailthruHttpClient sailthruHttpClient = new SailthruHttpClient(connManager, params);
        //sailthruHttpClient.getParams().setParameter("http.protocol.X-Sailthru-Authorization", "xxx");
        return sailthruHttpClient;
    }

    public HttpParams getHttpParams() {
        return params;
    }

    /**
     * Getter for SailthruHttpClient
     */
    public SailthruHttpClient getSailthruHttpClient() {
        return httpClient;
    }


    /**
     * Get Scheme Object
     */
    protected Scheme getScheme() {
        String scheme = null;
        try {
            URI uri = new URI(this.apiUrl);
            scheme = uri.getScheme();
        }
        catch (URISyntaxException e) {
            scheme = "http";
        }
        if (scheme.equals("https")) {
            return new Scheme(scheme, DEFAULT_HTTPS_PORT, SSLSocketFactory.getSocketFactory());
        }
        else {
            return new Scheme(scheme, DEFAULT_HTTP_PORT, PlainSocketFactory.getSocketFactory());
        }
    }


    /**
     * Make Http request to Sailthru API for given resource with given method and data
     * @param action
     * @param method
     * @param data parameter data
     * @return Object
     * @throws IOException
     */
    protected Object httpRequest(ApiAction action, HttpRequestMethod method, Map<String, Object> data) throws IOException {
        String url = this.apiUrl + "/" + action.toString();

        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        String json = gson.toJson(data, type);

        Map<String, String> params = buildPayload(json);

        return this.httpClient.executeHttpRequest(url, method, params, handler, customHeaders);
    }

    /**
     * Make HTTP Request to Sailthru API but with Api Params rather than generalized Map, this is recommended way to make request if data structure is complex
     * @param method HTTP method
     * @param apiParams 
     * @return Object
     * @throws IOException
     */
    protected Object httpRequest(HttpRequestMethod method, ApiParams apiParams) throws IOException {
        String url = this.apiUrl + "/" + apiParams.getApiCall().toString();
        String json = gson.toJson(apiParams, apiParams.getType());
        Map<String, String> params = buildPayload(json);
        return this.httpClient.executeHttpRequest(url, method, params, handler, customHeaders);
    }
    
    
    /**
     * Make HTTP Request to Sailthru API involving multi-part uploads but with Api Params rather than generalized Map, this is recommended way to make request if data structure is complex
     * @param method
     * @param apiParams
     * @param fileParams
     * @return Object
     * @throws IOException 
     */
    protected Object httpRequest(HttpRequestMethod method, ApiParams apiParams, ApiFileParams fileParams) throws IOException {
        String url = this.apiUrl + "/" + apiParams.getApiCall().toString();
        String json = gson.toJson(apiParams, apiParams.getType());
        Map<String, String> params = buildPayload(json);
        return this.httpClient.executeHttpRequest(url, method, params, fileParams.getFileParams(), handler, customHeaders);
    }
    
    
    protected JsonResponse httpRequestJson(HttpRequestMethod method, ApiParams apiParams) throws IOException {
        return new JsonResponse(httpRequest(method, apiParams));
    }
    
    protected JsonResponse httpRequestJson(HttpRequestMethod method, ApiParams apiParams, ApiFileParams fileParams) throws IOException {
        return new JsonResponse(httpRequest(method, apiParams, fileParams));
    }
    
    protected JsonResponse httpRequestJson(ApiAction action, HttpRequestMethod method, Map<String, Object> data) throws IOException {
        return new JsonResponse(httpRequest(action, method, data));
    }

    /**
     * Build HTTP Request Payload
     * @param jsonPayload JSON payload
     * @return Map Object
     */
    private Map<String, String> buildPayload(String jsonPayload) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", apiKey);
        params.put("format", handler.getSailthruResponseHandler().getFormat());
        params.put("json", jsonPayload);
        params.put("sig", getSignatureHash(params));
        LOGGER.debug("Params: {}", params.toString());
        return params;
    }

    /**
     * Get Signature Hash from given Map
     */
    protected String getSignatureHash(Map<String, String> parameters) {
        List<String> values = new ArrayList<String>();

        StringBuilder data = new StringBuilder();
        data.append(this.apiSecret);

        for (Entry<String, String> entry : parameters.entrySet()) {
           values.add(entry.getValue());
        }

        Collections.sort(values);

        for( String value:values ) {
            data.append(value);
        }
        return SailthruUtil.md5(data.toString());
    }


    /**
     * HTTP GET Request with Map
     * @param action API action
     * @param data Parameter data
     * @throws IOException
     */
    public JsonResponse apiGet(ApiAction action, Map<String, Object> data) throws IOException {
        return httpRequestJson(action, HttpRequestMethod.GET, data);
    }

    /**
     * HTTP GET Request with Interface implementation of ApiParams
     * @param data
     * @throws IOException
     */
    public JsonResponse apiGet(ApiParams data) throws IOException {
        return httpRequestJson(HttpRequestMethod.GET, data);
    }

    /**
     * HTTP POST Request with Map
     * @param action
     * @param data
     * @throws IOException
     */
    public JsonResponse apiPost(ApiAction action, Map<String, Object> data) throws IOException {
        return httpRequestJson(action, HttpRequestMethod.POST, data);
    }

    /**
     * HTTP POST Request with Interface implementation of ApiParams
     * @param data
     * @throws IOException
     */
    public JsonResponse apiPost(ApiParams data) throws IOException {
        return httpRequestJson(HttpRequestMethod.POST, data);
    }
    
    
    /**
     * HTTP POST Request with Interface implementation of ApiParams and ApiFileParams
     * @param data
     * @param fileParams
     * @throws IOException 
     */
    public JsonResponse apiPost(ApiParams data, ApiFileParams fileParams) throws IOException {
        return httpRequestJson(HttpRequestMethod.POST, data, fileParams);
    }
    

    /**
     * HTTP DELETE Request
     * @param action
     * @param data
     * @throws IOException
     */
    public JsonResponse apiDelete(ApiAction action, Map<String, Object> data) throws IOException {
        return httpRequestJson(action, HttpRequestMethod.DELETE, data);
    }

    /**
     * HTTP DELETE Request with Interface implementation of ApiParams
     * @param data
     * @throws IOException
     */
    public JsonResponse apiDelete(ApiParams data) throws IOException {
        return httpRequestJson(HttpRequestMethod.DELETE, data);
    }

    /**
     * Set response Handler, currently only JSON is supported but XML can also be supported later on
     * @param responseHandler
     */
    public void setResponseHandler(SailthruResponseHandler responseHandler) {
        this.handler.setSailthruResponseHandler(responseHandler);
    }
    
    public void setCustomHeaders(Map<String, String> headers) {
        customHeaders = headers;
    }
}
