package com.sailthru.client;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiActionTest {

    @Test
    public void testApiActionToString() {
        ApiAction watchAction = ApiAction.content_watch;
        ApiAction profileWatchAction = ApiAction.content_watch_profile;
        assertThat(watchAction.toString()).isEqualTo("content/watch");
        assertThat(profileWatchAction.toString()).isEqualTo("content/watch/profile");
    }
}