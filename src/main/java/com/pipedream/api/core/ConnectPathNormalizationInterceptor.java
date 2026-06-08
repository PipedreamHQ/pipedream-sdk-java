package com.pipedream.api.core;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp interceptor that collapses the empty Connect project-ID path segment.
 *
 * <p>When authenticating with a Connect token the caller may omit the project ID (the backend
 * derives it from the token), leaving it as an empty string. The generated resource clients
 * interpolate that into paths like {@code /v1/connect//accounts}; this rewrites the outgoing URL
 * to {@code /v1/connect/accounts}, which the backend routes via the token.
 *
 * <p>This is a no-op for every other request: a non-empty project ID produces
 * {@code /v1/connect/{id}/...} (no double slash), and non-connect URLs never contain the sequence.
 */
public final class ConnectPathNormalizationInterceptor implements Interceptor {
    private static final String EMPTY_SEGMENT = "/v1/connect//";
    private static final String NO_SEGMENT = "/v1/connect/";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        String encodedPath = url.encodedPath();
        if (encodedPath.contains(EMPTY_SEGMENT)) {
            HttpUrl newUrl = url.newBuilder()
                    .encodedPath(encodedPath.replace(EMPTY_SEGMENT, NO_SEGMENT))
                    .build();
            request = request.newBuilder().url(newUrl).build();
        }
        return chain.proceed(request);
    }
}
