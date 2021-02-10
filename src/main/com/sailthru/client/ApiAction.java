package com.sailthru.client;

/**
 * API calls
 * @author Prajwal Tuladhar <praj@sailthru.com>
 */
public enum ApiAction {
    event,
    settings,
    email,
    send,
    blast,
    blast_repeat,
    preview,
    template,
    list,
    content,
    alert,
    stats,
    purchase,
    horizon,
    job,
    trigger,
    inbox,
    user,
    RETURN, // the case is inconsistent, but "return" is a reserved keyword
    content_watch { public String toString() { return "content/watch"; } },
    content_watch_profile { public String toString() { return "content/watch/profile"; } },
}
