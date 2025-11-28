package com.sailthru.client;

import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SailthruHttpClientConfigurationTest {

    @Test
    public void testConfigValues() {
        String key = "apu_key";
        String secret = "***";
        SailthruHttpClientConfiguration httpClientConfiguration = new CustomSailthruHttpClientConfiguration();
        SailthruClient client = new SailthruClient(key, secret, httpClientConfiguration);
        HttpParams params = client.httpClient.getParams();
        assertEquals(httpClientConfiguration.getConnectionTimeout(), HttpConnectionParams.getConnectionTimeout(params), "connection timeout");
        assertEquals(httpClientConfiguration.getSoTimeout(), HttpConnectionParams.getSoTimeout(params), "socket timeout");

        ThreadSafeClientConnManager connManager = (ThreadSafeClientConnManager) client.httpClient.getConnectionManager();
        assertEquals(httpClientConfiguration.getMaxTotalConnections(), connManager.getMaxTotal(), "max total connections");
        assertEquals(httpClientConfiguration.getDefaultMaxConnectionsPerRoute(), connManager.getDefaultMaxPerRoute(), "default max connections per route");
    }

    private static class CustomSailthruHttpClientConfiguration implements SailthruHttpClientConfiguration {

        public int getConnectionTimeout() {
            return 1000;
        }

        public int getSoTimeout() {
            return 2000;
        }

        public boolean getSoReuseaddr() {
            return false;
        }

        public boolean getTcpNoDelay() {
            return false;
        }

        @Override
        public int getMaxTotalConnections() {
            return 100;
        }

        @Override
        public int getDefaultMaxConnectionsPerRoute() {
            return 20;
        }
    }
}
