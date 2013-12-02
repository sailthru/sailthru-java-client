package com.sailthru.client;

/**
 * Interface for providing different options to customize HTTP specific flags
 */
public interface SailthruHttpClientConfiguration {
    int getConnectionTimeout();
    int getSoTimeout();
    boolean getSoReuseaddr();
    boolean getTcpNoDelay();
}
