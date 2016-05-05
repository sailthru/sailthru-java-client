sailthru-java-client
====================

For installation instructions, documentation, and examples please visit:
[http://getstarted.sailthru.com/new-for-developers-overview/api-client-library/java](http://getstarted.sailthru.com/new-for-developers-overview/api-client-library/java)

A simple client library to remotely access the `Sailthru REST API` as per [http://getstarted.sailthru.com/developers/api](http://getstarted.sailthru.com/developers/api)

By default, it will make request in `JSON` format.

Examples
--------

See the examples directory for examples invoking various api actions with the client.

### Rate Limit Information

The library allows inspection of the 'X-Rate-Limit-*' headers returned by the Sailthru API. The `getLastRateLimitInfo(action, method)` function allows you to retrieve the last known rate limit information for the given ApiAction / HttpRequestMethod combination. It must follow an API call. For example, if you do a `/send POST`, you can follow up with a call to `getLastRateLimitInfo(ApiAction.send, HttpRequestMethod.POST)`. See the examples directory for how to use this function.
