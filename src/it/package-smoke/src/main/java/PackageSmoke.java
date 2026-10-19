import com.google.gson.Gson;
import com.sailthru.client.ApiAction;
import com.sailthru.client.SailthruClient;
import com.sailthru.client.handler.response.JsonResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/** A consumer of the packaged JAR, with no library source classes or test dependencies. */
public class PackageSmoke {
    public static void main(String[] args) throws Exception {
        String location = SailthruClient.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        check(location.endsWith(".jar"), "The client must load from the installed JAR: " + location);

        AtomicReference<Throwable> serverFailure = new AtomicReference<>();
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/user", exchange -> {
            try {
                check("POST".equals(exchange.getRequestMethod()), "Expected a POST request");
                check("/user".equals(exchange.getRequestURI().getPath()), "Expected the /user endpoint");
                Map<String, String> params = formParameters(exchange);
                check("smoke-key".equals(params.get("api_key")), "API key was not preserved");
                check("json".equals(params.get("format")), "Expected the JSON response format");
                Map<?, ?> payload = new Gson().fromJson(params.get("json"), Map.class);
                check("smoke@example.com".equals(payload.get("id")), "User payload was not preserved");
                String signature = params.get("sig");
                check(signature != null && signature.matches("[a-f0-9]{32}"), "Missing or malformed signature");
                reply(exchange, 200, "{\"ok\":true}");
            } catch (Exception | AssertionError failure) {
                serverFailure.set(failure);
                reply(exchange, 500, "{\"error\":\"smoke request validation failed\"}");
            } finally {
                exchange.close();
            }
        });
        server.start();

        SailthruClient client = null;
        try {
            String apiUrl = "http://127.0.0.1:" + server.getAddress().getPort();
            client = new SailthruClient("smoke-key", "smoke-secret", apiUrl);
            Map<String, Object> user = new HashMap<>();
            user.put("id", "smoke@example.com");
            JsonResponse result = client.apiPost(ApiAction.user, user);

            if (serverFailure.get() != null) {
                throw new AssertionError("Local server rejected the client request", serverFailure.get());
            }
            check(result.isOK(), "Client reported an unsuccessful response");
            check(Boolean.TRUE.equals(result.getResponse().get("ok")), "Client did not parse the response");
            System.out.println("Packaged client loaded and completed a local API request.");
        } finally {
            if (client != null) {
                client.getSailthruHttpClient().getConnectionManager().shutdown();
            }
            server.stop(0);
        }
    }

    private static Map<String, String> formParameters(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> params = new HashMap<>();
        for (String field : body.split("&")) {
            String[] pair = field.split("=", 2);
            check(pair.length == 2, "Malformed form field");
            params.put(URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                    URLDecoder.decode(pair[1], StandardCharsets.UTF_8));
        }
        return params;
    }

    private static void reply(HttpExchange exchange, int status, String json) throws IOException {
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, body.length);
        exchange.getResponseBody().write(body);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
