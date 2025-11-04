package com.sailthru.client;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultSailthruHttpClientConfigurationTest {

    @Test void configValues() {
        SailthruHttpClientConfiguration httpClientConfiguration = new DefaultSailthruHttpClientConfiguration();
        String key = "apu_key";
        String secret = "***";
        SailthruClient client = new SailthruClient(key, secret); // use DefaultSailthruHttpClientConfiguration
        HttpParams params = client.httpClient.getParams();
        assertThat(HttpConnectionParams.getConnectionTimeout(params)).as("connection timeout").isEqualTo(httpClientConfiguration.getConnectionTimeout());
        assertThat(HttpConnectionParams.getSoTimeout(params)).as("socket timeout").isEqualTo(httpClientConfiguration.getSoTimeout());
    }
}
