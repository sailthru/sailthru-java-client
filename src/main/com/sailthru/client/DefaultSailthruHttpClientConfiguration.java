package com.sailthru.client;

/**
 * Using this class will set connection and socket timeout to infinite timeout, which is certainly not recommended.
 *
 * Default timeouts will be set as positive non-zero values (both for connection and socket) in next major release only,
 * for maintaining compatibility with current minor release.
 *
 * Connection pool sizes match Apache HttpClient's defaults of 10 max and 2 max-per-route, though these may be too small
 * for high throughput requests. Perceived high response time may be queueing within the connection pool. See
 * https://hc.apache.org/httpcomponents-client-ga/tutorial/html/connmgmt.html#d5e393
 *
 * It's recommended to implement {@link SailthruHttpClientConfiguration} based on client requirements for now.
 */
public class DefaultSailthruHttpClientConfiguration implements SailthruHttpClientConfiguration {

    @Override
    public int getConnectionTimeout() {
        return 0;
    }

    @Override
    public int getSoTimeout() {
        return 0;
    }

    @Override
    public boolean getSoReuseaddr() {
        return false;
    }

    @Override
    public boolean getTcpNoDelay() {
        return true;
    }

    @Override
    public int getMaxTotalConnections() {
        return 20;
    }

    @Override
    public int getDefaultMaxConnectionsPerRoute() {
        return 2;
    }
}
