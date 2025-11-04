package com.sailthru.client;

import com.google.common.collect.ImmutableMap;
import com.sailthru.client.http.SailthruHttpClient;
import com.sailthru.client.params.Send;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.WARN)
@ExtendWith(MockitoExtension.class)
class AbstractSailthruClientTest {
    private DummySailthruClient sailthruClient;
    private SailthruHttpClient httpClient;

    @BeforeEach void setUp() {
        ThreadSafeClientConnManager mockConnManager = mock(ThreadSafeClientConnManager.class);
        HttpParams mockHttpParams = mock(HttpParams.class);

        httpClient = new SailthruHttpClient(mockConnManager, mockHttpParams);
        httpClient = spy(httpClient);
        sailthruClient = new DummySailthruClient("api_key", "secret", "https://api.sailthru.com");
        sailthruClient.setSailthruHttpClient(httpClient);
    }

    @Test void getLastRateLimitInfo() throws Exception {
        // fake a /send GET, and make sure the rate limit info is properly captured
        int limit = 3;
        int remaining = 2;
        long resetTs = ((new Date().getTime() / 1000) * 1000) + 18000; // pretend the top of the next minute is 18 seconds from now
        Date resetDate = new Date(resetTs);
        CloseableHttpResponse httpResponse = getMockHttpResponseWithRateLimitHeaders(limit, remaining, resetDate);
        doReturn(httpResponse).when(httpClient).execute(any(HttpHost.class), any(HttpRequest.class), (HttpContext) any());

        sailthruClient.apiGet(ApiAction.send, ImmutableMap.<String, Object>of(Send.PARAM_SEND_ID, "some valid send id"));

        LastRateLimitInfo rateLimitInfo = sailthruClient.getLastRateLimitInfo(ApiAction.send, AbstractSailthruClient.HttpRequestMethod.GET);
        assertThat(rateLimitInfo.getLimit()).isEqualTo(limit);
        assertThat(rateLimitInfo.getRemaining()).isEqualTo(remaining);
        assertThat(rateLimitInfo.getReset()).isEqualTo(resetDate);
    }

    @Test void getLastRateLimitInfoDifferentActions() throws Exception {
        // fake a /send GET and a /list GET, and make sure the rate limit infos for each are properly captured

        int sendLimit = 3;
        int sendRemaining = 2;
        long sendResetTs = ((new Date().getTime() / 1000) * 1000) + 18000; // pretend the top of the next minute is 18 seconds from now
        Date sendResetDate = new Date(sendResetTs);
        CloseableHttpResponse sendHttpResponse = getMockHttpResponseWithRateLimitHeaders(sendLimit, sendRemaining, sendResetDate);

        int listLimit = 5;
        int listRemaining = 1;
        long listResetTs = ((new Date().getTime() / 1000) * 1000) + 30000; // for testing sake, pretend for this call the top of the next minute is 30 seconds from now
        Date listResetDate = new Date(listResetTs);
        CloseableHttpResponse listHttpResponse = getMockHttpResponseWithRateLimitHeaders(listLimit, listRemaining, listResetDate);

        // return is interesting to test because the enum name is upper case
        int returnLimit = 10;
        int returnRemaining = 1;
        long returnResetTs = ((new Date().getTime() / 1000) * 1000) + 40000; // for testing sake, pretend for this call the top of the next minute is 40 seconds from now
        Date returnResetDate = new Date(returnResetTs);
        CloseableHttpResponse returnHttpResponse = getMockHttpResponseWithRateLimitHeaders(returnLimit, returnRemaining, returnResetDate);

        doReturn(sendHttpResponse).doReturn(listHttpResponse)
                .doReturn(returnHttpResponse)
                .when(httpClient)
                .execute(any(HttpHost.class), any(HttpRequest.class), (HttpContext) any());

        sailthruClient.apiGet(ApiAction.send, ImmutableMap.<String, Object>of(Send.PARAM_SEND_ID, "some valid send id"));
        sailthruClient.apiGet(ApiAction.list, ImmutableMap.<String, Object>of("list", "some list"));
        sailthruClient.apiPost(ApiAction.RETURN, ImmutableMap.of("email", "foo@bar.com", "items", Collections.emptyList()));

        LastRateLimitInfo sendRateLimitInfo = sailthruClient.getLastRateLimitInfo(ApiAction.send, AbstractSailthruClient.HttpRequestMethod.GET);
        assertThat(sendRateLimitInfo.getLimit()).isEqualTo(sendLimit);
        assertThat(sendRateLimitInfo.getRemaining()).isEqualTo(sendRemaining);
        assertThat(sendRateLimitInfo.getReset()).isEqualTo(sendResetDate);

        LastRateLimitInfo listRateLimitInfo = sailthruClient.getLastRateLimitInfo(ApiAction.list, AbstractSailthruClient.HttpRequestMethod.GET);
        assertThat(listRateLimitInfo.getLimit()).isEqualTo(listLimit);
        assertThat(listRateLimitInfo.getRemaining()).isEqualTo(listRemaining);
        assertThat(listRateLimitInfo.getReset()).isEqualTo(listResetDate);

        LastRateLimitInfo returnRateLimitInfo = sailthruClient.getLastRateLimitInfo(ApiAction.RETURN, AbstractSailthruClient.HttpRequestMethod.POST);
        assertThat(returnRateLimitInfo.getLimit()).isEqualTo(returnLimit);
        assertThat(returnRateLimitInfo.getRemaining()).isEqualTo(returnRemaining);
        assertThat(returnRateLimitInfo.getReset()).isEqualTo(returnResetDate);
    }

    @Test void getLastRateLimitInfoDifferentMethods() throws Exception {
        // fake a /send GET and a /send POST, and make sure the rate limit infos for each are properly captured

        int getLimit = 3;
        int getRemaining = 2;
        long getResetTs = ((new Date().getTime() / 1000) * 1000) + 18000; // pretend the top of the next minute is 18 seconds from now
        Date getResetDate = new Date(getResetTs);
        CloseableHttpResponse getHttpResponse = getMockHttpResponseWithRateLimitHeaders(getLimit, getRemaining, getResetDate);

        int postLimit = 5;
        int postRemaining = 1;
        long postResetTs = ((new Date().getTime() / 1000) * 1000) + 30000; // for testing sake, pretend for this call the top of the next minute is 30 seconds from now
        Date postResetDate = new Date(postResetTs);
        CloseableHttpResponse postHttpResponse = getMockHttpResponseWithRateLimitHeaders(postLimit, postRemaining, postResetDate);

        doReturn(getHttpResponse).doReturn(postHttpResponse).when(httpClient).execute(any(HttpHost.class), any(HttpRequest.class), (HttpContext) any());

        sailthruClient.apiGet(ApiAction.list, ImmutableMap.<String, Object>of("list", "some list"));
        sailthruClient.apiPost(ApiAction.list, ImmutableMap.<String, Object>of("list", "some new list"));

        LastRateLimitInfo getLastRateLimitInfo = sailthruClient.getLastRateLimitInfo(ApiAction.list, AbstractSailthruClient.HttpRequestMethod.GET);
        assertThat(getLastRateLimitInfo.getLimit()).isEqualTo(getLimit);
        assertThat(getLastRateLimitInfo.getRemaining()).isEqualTo(getRemaining);
        assertThat(getLastRateLimitInfo.getReset()).isEqualTo(getResetDate);

        LastRateLimitInfo postRateLimitInfo = sailthruClient.getLastRateLimitInfo(ApiAction.list, AbstractSailthruClient.HttpRequestMethod.POST);
        assertThat(postRateLimitInfo.getLimit()).isEqualTo(postLimit);
        assertThat(postRateLimitInfo.getRemaining()).isEqualTo(postRemaining);
        assertThat(postRateLimitInfo.getReset()).isEqualTo(postResetDate);
    }

    @Test void returnUrl() throws Exception {
        CloseableHttpResponse response = getMockHttpResponseWithRateLimitHeaders(1, 1, new Date());
        doReturn(response).when(httpClient).execute(any(HttpHost.class), any(HttpRequest.class), (HttpContext) any());
        sailthruClient.apiPost(ApiAction.RETURN, Collections.<String, Object>emptyMap());
        verify(httpClient).executeHttpRequest(eq("https://api.sailthru.com/return"), eq(AbstractSailthruClient.HttpRequestMethod.POST),
                any(Map.class), any(ResponseHandler.class), (Map) any());
    }

    private CloseableHttpResponse getMockHttpResponseWithRateLimitHeaders(int limit, int remaining, Date reset) {
        CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);

        BasicHttpEntity fakeEntity = new BasicHttpEntity();
        fakeEntity.setContent(new ByteArrayInputStream("{ some_valid_return_data: true }".getBytes()));
        when(httpResponse.getEntity()).thenReturn(fakeEntity);
        when(httpResponse.getStatusLine()).thenReturn(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK"));

        when(httpResponse.getFirstHeader("X-Rate-Limit-Limit")).thenReturn(new BasicHeader("X-Rate-Limit-Limit", limit + ""));
        when(httpResponse.getFirstHeader("X-Rate-Limit-Remaining")).thenReturn(new BasicHeader("X-Rate-Limit-Remaining", remaining + ""));
        when(httpResponse.getFirstHeader("X-Rate-Limit-Reset")).thenReturn(new BasicHeader("X-Rate-Limit-Reset", (reset.getTime() / 1000) + ""));

        return httpResponse;
    }

    private class DummySailthruClient extends AbstractSailthruClient {
        public DummySailthruClient(String apiKey, String apiSecret, String apiUrl) {
            super(apiKey, apiSecret, apiUrl);
        }
    }
}
