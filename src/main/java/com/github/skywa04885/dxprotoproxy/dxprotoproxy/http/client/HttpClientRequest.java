package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.client;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import org.jetbrains.annotations.NotNull;

import java.net.http.HttpRequest;

public class HttpClientRequest {
    private final @NotNull DXHttpConfigRequest httpConfigRequest;
    private final @NotNull HttpRequest httpRequest;

    /**
     * Constructs a new http client request with the given request configuration and http request.
     * @param httpConfigRequest the request configuration.
     * @param httpRequest the actual http request.
     */
    public HttpClientRequest(@NotNull DXHttpConfigRequest httpConfigRequest,
                             @NotNull HttpRequest httpRequest) {
        this.httpConfigRequest = httpConfigRequest;
        this.httpRequest = httpRequest;
    }

    public @NotNull DXHttpConfigRequest httpConfigRequest() {
        return httpConfigRequest;
    }

    public @NotNull HttpRequest httpRequest() {
        return httpRequest;
    }
}
