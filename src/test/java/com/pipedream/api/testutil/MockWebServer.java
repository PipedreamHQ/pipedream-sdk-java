package com.pipedream.api.testutil;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;

/**
 * Drop-in replacement for {@code okhttp3.mockwebserver.MockWebServer} that uses the JDK's
 * built-in {@link HttpServer}. Exists so the wire tests in this repo do not require an
 * external mock-server dependency that would have to be threaded into the Fern-managed
 * {@code build.gradle}.
 *
 * <p>Implements only the surface used here: {@code start()}, {@code shutdown()},
 * {@code url(String)}, {@code enqueue(MockResponse)}, and {@code takeRequest()}.
 */
public final class MockWebServer {
    private final HttpServer server;
    private final ExecutorService executor;
    private final BlockingDeque<MockResponse> responses = new LinkedBlockingDeque<>();
    private final BlockingDeque<RecordedRequest> requests = new LinkedBlockingDeque<>();

    public MockWebServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        this.executor = Executors.newCachedThreadPool();
        this.server.setExecutor(executor);
        this.server.createContext("/", this::handle);
    }

    public void start() {
        server.start();
    }

    public void shutdown() {
        server.stop(0);
        executor.shutdownNow();
    }

    public HttpUrl url(String path) {
        if (!path.startsWith("/")) path = "/" + path;
        return HttpUrl.parse("http://" + server.getAddress().getHostString() + ":"
                + server.getAddress().getPort() + path);
    }

    public void enqueue(MockResponse response) {
        responses.add(response);
    }

    public RecordedRequest takeRequest() throws InterruptedException {
        return requests.poll(10, TimeUnit.SECONDS);
    }

    private void handle(HttpExchange exchange) throws IOException {
        byte[] requestBody = readAllBytes(exchange.getRequestBody());
        requests.add(new RecordedRequest(
                exchange.getRequestMethod(), exchange.getRequestURI().toString(), requestBody));

        MockResponse response = responses.poll();
        if (response == null) {
            exchange.sendResponseHeaders(503, -1);
            exchange.close();
            return;
        }

        for (Map.Entry<String, String> header : response.headers.entrySet()) {
            exchange.getResponseHeaders().add(header.getKey(), header.getValue());
        }

        if (response.body.length == 0) {
            exchange.sendResponseHeaders(response.code, -1);
        } else {
            exchange.sendResponseHeaders(response.code, response.body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(response.body);
            }
        }
        exchange.close();
    }

    private static byte[] readAllBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        byte[] chunk = new byte[1024];
        int n;
        while ((n = is.read(chunk)) != -1) {
            buf.write(chunk, 0, n);
        }
        return buf.toByteArray();
    }
}
