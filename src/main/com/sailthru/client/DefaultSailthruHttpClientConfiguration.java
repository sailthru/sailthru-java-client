package com.sailthru.client;

/**
 * Using this class will set connection and socket timeout to infinite timeout, which is certainly not recommended.
 * Default timeouts will be set as positive non-zero values (both for connection and socket) in next major relase only,
 * for maintaining compatibility with current minor release.
 * So, it's recommended to implement {@link SailthruHttpClientConfiguration} based on client requirements for now.
 */
public class DefaultSailthruHttpClientConfiguration implements SailthruHttpClientConfiguration {

    private static final int DEFAULT_CONN_TIMEOUT = 0;
    private static final int DEFAULT_SO_CONN_TIMEOUT = 0;

    public int getConnectionTimeout() {
        return DEFAULT_CONN_TIMEOUT;
    }

    public int getSoTimeout() {
        return DEFAULT_SO_CONN_TIMEOUT;
    }

    public boolean getSoReuseaddr() {
        return false;
    }

    public boolean getTcpNoDelay() {
        return true;
    }
}
