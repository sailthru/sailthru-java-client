package com.sailthru.client;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultSailthruHttpClientConfigurationTest {

    @Test
    public void testConfigValues() {
        SailthruHttpClientConfiguration httpClientConfiguration = new DefaultSailthruHttpClientConfiguration();
        String key = "apu_key";
        String secret = "***";
        SailthruClient client = new SailthruClient(key, secret); // use DefaultSailthruHttpClientConfiguration
        HttpParams params = client.httpClient.getParams();
        assertEquals("connection timeout", httpClientConfiguration.getConnectionTimeout(), HttpConnectionParams.getConnectionTimeout(params));
        assertEquals("socket timeout", httpClientConfiguration.getSoTimeout(), HttpConnectionParams.getSoTimeout(params));
    }
}
