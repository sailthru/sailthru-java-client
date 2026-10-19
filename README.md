sailthru-java-client
====================

For installation instructions, documentation, and examples please visit:
[http://getstarted.sailthru.com/new-for-developers-overview/api-client-library/java](http://getstarted.sailthru.com/new-for-developers-overview/api-client-library/java)

A simple client library to remotely access the `Sailthru REST API` as per [http://getstarted.sailthru.com/developers/api](http://getstarted.sailthru.com/developers/api)

By default, it will make request in `JSON` format.

Development and compatibility checks
------------------------------------

The library is compiled with a Java 8 target. Building and running its test suite
requires JDK 17 or newer. CI checks JDK 17, 21, 25, and the latest stable Eclipse
Temurin JDK. The `current` matrix entry uses the stable version range `>=17` with
`check-latest: true`, so it follows new stable releases without editing the workflow.
Early-access runtimes are not included.

Run the complete checks locally with Maven 3.9 and JDK 17 or newer:

```bash
mvn --batch-mode --no-transfer-progress clean verify
```

This runs the existing JUnit suite, builds the JAR, and smoke-tests it through a
separate consumer project in `src/it/package-smoke`. The Maven Invoker stages the
fresh JAR and its POM in `target/package-smoke-repository` and runs the consumer
from `target/package-smoke`. The consumer loads the JAR with only its declared
consumer dependencies, creates a client with fake credentials, and sends a POST
to a loopback HTTP server. It checks the request fields, signature format, and
parsed response without contacting the Sailthru API or changing customer data.
Generated files stay under `target` and are removed by `mvn clean`.

The package smoke check is bound to `verify`, before the normal Maven `install`
and `deploy` phases. The command above validates locally; it does not publish.
For only the existing unit tests, run `mvn test`.

GitHub Actions runs the complete checks on pull requests, pushes to `main`, and
weekly on Mondays at 04:27 UTC. A manual run is also available. Schedules start
after the workflow is merged into the default branch. Maintainers should check
that schedules remain enabled: GitHub automatically disables scheduled workflows
in public repositories after 60 days without repository activity; see
[GitHub's schedule documentation](https://docs.github.com/en/actions/reference/workflows-and-actions/events-that-trigger-workflows#schedule).

Examples
--------

See the examples directory for examples invoking various api actions with the client.

### Rate Limit Information

The library allows inspection of the 'X-Rate-Limit-*' headers returned by the Sailthru API. The `getLastRateLimitInfo(action, method)` function allows you to retrieve the last known rate limit information for the given ApiAction / HttpRequestMethod combination. It must follow an API call. For example, if you do a `/send POST`, you can follow up with a call to `getLastRateLimitInfo(ApiAction.send, HttpRequestMethod.POST)`. See the examples directory for how to use this function.
