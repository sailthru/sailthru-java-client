package com.sailthru.client;

import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SailthruHttpClientConfigurationTest {

    @Test
    public void testConfigValues() {
        String key = "apu_key";
        String secret = "***";
        SailthruHttpClientConfiguration httpClientConfiguration = new CustomSailthruHttpClientConfiguration();
        SailthruClient client = new SailthruClient(key, secret, httpClientConfiguration);
        HttpParams params = client.httpClient.getParams();
        assertThat(HttpConnectionParams.getConnectionTimeout(params))
                .as("connection timeout")
                .isEqualTo(httpClientConfiguration.getConnectionTimeout());
        assertThat(HttpConnectionParams.getSoTimeout(params))
                .as("socket timeout")
                .isEqualTo(httpClientConfiguration.getSoTimeout());

        ThreadSafeClientConnManager connManager = (ThreadSafeClientConnManager) client.httpClient.getConnectionManager();
        assertThat(connManager.getMaxTotal())
                .as("max total connections")
                .isEqualTo(httpClientConfiguration.getMaxTotalConnections());
        assertThat(connManager.getDefaultMaxPerRoute())
                .as("default max connections per route")
                .isEqualTo(httpClientConfiguration.getDefaultMaxConnectionsPerRoute());
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