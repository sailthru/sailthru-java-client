package com.sailthru.client.http;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.sailthru.client.SailthruUtil;
import com.sailthru.client.exceptions.ApiException;
import com.sailthru.client.handler.JsonHandler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicStatusLine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SailthruHandlerTest {
    private final SailthruHandler handler = new SailthruHandler(new JsonHandler());
    private final Gson gson = SailthruUtil.createGson();
    private static final String EXAMPLE_RESPONSE = "{\"sample_response\":true}";

    @Test
    public void testHandlingSuccessfulResponse() throws IOException {
        CloseableHttpResponse httpOkResponse = getMockHttpResponseWithStatus(HttpStatus.SC_OK, "OK");
        Object okResponse = handler.handleResponse(httpOkResponse);
        Assert.assertEquals(EXAMPLE_RESPONSE, gson.toJson(okResponse));

        CloseableHttpResponse httpCreatedResponse = getMockHttpResponseWithStatus(HttpStatus.SC_CREATED, "Created");
        Object createdResponse = handler.handleResponse(httpCreatedResponse);
        Assert.assertEquals(EXAMPLE_RESPONSE, gson.toJson(createdResponse));

        CloseableHttpResponse httpNoContentResponse = getMockHttpResponseWithStatus(HttpStatus.SC_NO_CONTENT, "No Content");
        Object noContentResponse = handler.handleResponse(httpNoContentResponse);
        Assert.assertEquals(EXAMPLE_RESPONSE, gson.toJson(noContentResponse));
    }

    @Test
    public void testHandlingClientErrorResponse() throws IOException {
        CloseableHttpResponse httpBadResponse = getMockHttpResponseWithStatus(HttpStatus.SC_BAD_REQUEST, "Bad Request");
        try {
            handler.handleResponse(httpBadResponse);
            Assert.fail("Expected an APIException to be thrown");
        } catch (ApiException apiException) {
            Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, apiException.getStatusCode());
        }

        CloseableHttpResponse httpUnauthorizedResponse = getMockHttpResponseWithStatus(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
        try {
            handler.handleResponse(httpUnauthorizedResponse);
            Assert.fail("Expected an APIException to be thrown");
        } catch (ApiException apiException) {
            Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED, apiException.getStatusCode());
        }
    }

    @Test
    public void testHandlingServerErrorResponse() throws IOException {
        CloseableHttpResponse httpServerErrorResponse = getMockHttpResponseWithStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        try {
            handler.handleResponse(httpServerErrorResponse);
            Assert.fail("Expected an APIException to be thrown");
        } catch (ApiException apiException) {
            Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, apiException.getStatusCode());
        }

        CloseableHttpResponse httpBadGatewayResponse = getMockHttpResponseWithStatus(HttpStatus.SC_BAD_GATEWAY, "Bad Gateway");
        try {
            handler.handleResponse(httpBadGatewayResponse);
            Assert.fail("Expected an APIException to be thrown");
        } catch (ApiException apiException) {
            Assert.assertEquals(HttpStatus.SC_BAD_GATEWAY, apiException.getStatusCode());
        }
    }

    private CloseableHttpResponse getMockHttpResponseWithStatus(int statusCode, String reasonPhrase) {
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);

        BasicHttpEntity fakeEntity = new BasicHttpEntity();
        fakeEntity.setContent(new ByteArrayInputStream(EXAMPLE_RESPONSE.getBytes()));
        when(mockHttpResponse.getEntity()).thenReturn(fakeEntity);

        when(mockHttpResponse.getStatusLine()).thenReturn(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), statusCode, reasonPhrase));

        when(mockHttpResponse.getFirstHeader("X-Rate-Limit-Limit")).thenReturn(null);
        when(mockHttpResponse.getFirstHeader("X-Rate-Limit-Remaining")).thenReturn(null);
        when(mockHttpResponse.getFirstHeader("X-Rate-Limit-Reset")).thenReturn(null);

        return mockHttpResponse;
    }
}
