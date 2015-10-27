package com.sailthru.client;

/**
 * Interface for providing different options to customize HTTP specific flags
 */
public interface SailthruHttpClientConfiguration {
    /**
     * get connection timeout in milli seconds
     * @return int
     */
    int getConnectionTimeout();

    /**
     * get socket timeout in milli seconds
     * @return int
     */
    int getSoTimeout();

    /**
     * get socket reuse address boolean flag
     * @return boolean
     */
    boolean getSoReuseaddr();

    /**
     * get tcp no delay boolean flag
     * @return boolean
     */
    boolean getTcpNoDelay();
}
