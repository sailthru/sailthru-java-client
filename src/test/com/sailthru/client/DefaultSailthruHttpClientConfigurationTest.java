package com.sailthru.client;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultSailthruHttpClientConfigurationTest {

    @Test
    public void testConfigValues() {
        SailthruHttpClientConfiguration httpClientConfiguration = new DefaultSailthruHttpClientConfiguration();
        String key = "apu_key";
        String secret = "***";
        SailthruClient client = new SailthruClient(key, secret); // use DefaultSailthruHttpClientConfiguration
        HttpParams params = client.httpClient.getParams();
        assertEquals(httpClientConfiguration.getConnectionTimeout(), HttpConnectionParams.getConnectionTimeout(params), "connection timeout");
        assertEquals(httpClientConfiguration.getSoTimeout(), HttpConnectionParams.getSoTimeout(params), "socket timeout");
    }
}
