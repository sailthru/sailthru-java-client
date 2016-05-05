package com.sailthru.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sailthru.client.handler.JsonHandler;
import com.sailthru.client.handler.SailthruResponseHandler;
import com.sailthru.client.handler.response.JsonResponse;
import com.sailthru.client.http.SailthruHandler;
import com.sailthru.client.http.SailthruHttpClient;
import com.sailthru.client.params.ApiFileParams;
import com.sailthru.client.params.ApiParams;
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

/**
 * Abstract class exposing genric API calls for Sailthru API as per http://docs.sailthru.com/api
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public abstract class AbstractSailthruClient {

    private static final Gson GSON = SailthruUtil.createGson();

    public static final String DEFAULT_API_URL = "https://api.sailthru.com";
    public static final int DEFAULT_HTTP_PORT = 80;
    public static final int DEFAULT_HTTPS_PORT = 443;
    public static final String DEFAULT_USER_AGENT = "Sailthru Java Client";
    public static final String DEFAULT_ENCODING = "UTF-8";

    //HTTP methods supported by Sailthru API
    public static enum HttpRequestMethod {
        GET,
        POST,
        DELETE
    }

    protected String apiKey;
    protected String apiSecret;
    protected String apiUrl;

    protected SailthruHttpClient httpClient;

    private SailthruHandler handler;

    private Map<String, String> customHeaders = null;

    private final SailthruHttpClientConfiguration sailthruHttpClientConfiguration;

    private final Map<ApiActionHttpMethod, LastRateLimitInfo> lastRateLimitInfoMap = new HashMap<ApiActionHttpMethod, LastRateLimitInfo>();

    /**
     * Main constructor class for setting up the client
     * @param apiKey
     * @param apiSecret
     * @param apiUrl
     */
    public AbstractSailthruClient(String apiKey, String apiSecret, String apiUrl) {
        this(apiKey, apiSecret, apiUrl, new DefaultSailthruHttpClientConfiguration());
    }

    /**
     *
     * @param apiKey
     * @param apiSecret
     * @param apiUrl
     * @param sailthruHttpClientConfiguration
     */
    public AbstractSailthruClient(String apiKey, String apiSecret, String apiUrl, SailthruHttpClientConfiguration sailthruHttpClientConfiguration) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.apiUrl = apiUrl;
        handler = new SailthruHandler(new JsonHandler());
        this.sailthruHttpClientConfiguration = sailthruHttpClientConfiguration;
        httpClient = create();
    }


    /**
     * Create SailthruHttpClient
     */
    private SailthruHttpClient create() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, DEFAULT_ENCODING);
        HttpProtocolParams.setUserAgent(params, DEFAULT_USER_AGENT);
        HttpProtocolParams.setUseExpectContinue(params, true);

        HttpConnectionParams.setConnectionTimeout(params, sailthruHttpClientConfiguration.getConnectionTimeout());
        HttpConnectionParams.setSoTimeout(params, sailthruHttpClientConfiguration.getSoTimeout());
        HttpConnectionParams.setSoReuseaddr(params, sailthruHttpClientConfiguration.getSoReuseaddr());
        HttpConnectionParams.setTcpNoDelay(params, sailthruHttpClientConfiguration.getTcpNoDelay());

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(getScheme());

        ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager(schemeRegistry);
        return new SailthruHttpClient(connManager, params);
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
        String scheme;
        try {
            URI uri = new URI(this.apiUrl);
            scheme = uri.getScheme();
        } catch (URISyntaxException e) {
            scheme = "http";
        }
        if (scheme.equals("https")) {
            return new Scheme(scheme, DEFAULT_HTTPS_PORT, SSLSocketFactory.getSocketFactory());
        } else {
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
        String json = GSON.toJson(data, type);
        Map<String, String> params = buildPayload(json);
        Object response = httpClient.executeHttpRequest(url, method, params, handler, customHeaders);
        recordRateLimitInfo(action, method, response);
        return response;
    }

    /**
     * Make HTTP Request to Sailthru API but with Api Params rather than generalized Map, this is recommended way to make request if data structure is complex
     * @param method HTTP method
     * @param apiParams 
     * @return Object
     * @throws IOException
     */
    protected Object httpRequest(HttpRequestMethod method, ApiParams apiParams) throws IOException {
        ApiAction action = apiParams.getApiCall();
        String url = apiUrl + "/" + action.toString();
        String json = GSON.toJson(apiParams, apiParams.getType());
        Map<String, String> params = buildPayload(json);
        Object response = httpClient.executeHttpRequest(url, method, params, handler, customHeaders);
        recordRateLimitInfo(action, method, response);
        return response;
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
        ApiAction action = apiParams.getApiCall();
        String url = apiUrl + "/" + action.toString();
        String json = GSON.toJson(apiParams, apiParams.getType());
        Map<String, String> params = buildPayload(json);
        Object response = httpClient.executeHttpRequest(url, method, params, fileParams.getFileParams(), handler, customHeaders);
        recordRateLimitInfo(action, method, response);
        return response;
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
     * Gets the X-Rate-Limit-* headers from the last response for the given action / method, stored as a POJO.
     * @param action
     * @param method
     * @return POJO storing X-Rate-Limit-* headers from last invocation. Returns null if this object has never invoked the given action / method.
     */
    public LastRateLimitInfo getLastRateLimitInfo(ApiAction action, HttpRequestMethod method) {
        return lastRateLimitInfoMap.get(new ApiActionHttpMethod(action, method));
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

    @VisibleForTesting
    void setSailthruHttpClient(SailthruHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    private void recordRateLimitInfo(ApiAction action, HttpRequestMethod method, Object response) {
        Map<String,Object> responseMap = (Map<String,Object>) response;
        Object lastRateLimitInfo = responseMap.get(SailthruHandler.RATE_LIMIT_INFO_KEY);
        responseMap.remove(SailthruHandler.RATE_LIMIT_INFO_KEY);
        if (lastRateLimitInfo instanceof LastRateLimitInfo) {
            ApiActionHttpMethod key = new ApiActionHttpMethod(action, method);
            lastRateLimitInfoMap.put(key, (LastRateLimitInfo) lastRateLimitInfo);
        }
    }

    private class ApiActionHttpMethod {
        private final ApiAction action;
        private final HttpRequestMethod method;

        private ApiActionHttpMethod(ApiAction action, HttpRequestMethod method) {
            this.action = action;
            this.method = method;
        }

        @Override public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ApiActionHttpMethod that = (ApiActionHttpMethod) o;

            if (action != that.action) {
                return false;
            }
            return method == that.method;

        }

        @Override public int hashCode() {
            int result = action.hashCode();
            result = 31 * result + method.hashCode();
            return result;
        }
    }
}
