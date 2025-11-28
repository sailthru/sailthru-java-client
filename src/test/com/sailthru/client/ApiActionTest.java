package com.sailthru.client;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiActionTest {

    @Test
    public void testApiActionToString() {
        ApiAction watchAction = ApiAction.content_watch;
        ApiAction profileWatchAction = ApiAction.content_watch_profile;
        assertEquals("content/watch", watchAction.toString());
        assertEquals("content/watch/profile", profileWatchAction.toString());

    }
}
