package com.sailthru.client;

/**
 * Interface for customizing some HTTP client settings.
 *
 * {@link DefaultSailthruHttpClientConfiguration} provides default values but
 * users may want to override those defaults by extending it or implementing
 * this interface.
 */
public interface SailthruHttpClientConfiguration {
    /**
     * @return connection timeout in milliseconds
     */
    int getConnectionTimeout();

    /**
     * @return socket timeout in milliseconds
     */
    int getSoTimeout();

    /**
     * @return socket reuse address boolean flag
     */
    boolean getSoReuseaddr();

    /**
     * @return TCP_NODELAY boolean setting
     */
    boolean getTcpNoDelay();

    /**
     * @return connection pool max total
     */
    int getMaxTotalConnections();

    /**
     * @return connection pool max per route
     */
    int getDefaultMaxConnectionsPerRoute();
}
