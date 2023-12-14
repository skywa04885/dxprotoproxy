package com.github.skywa04885.dxprotoproxy.server.http.client;

import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfigRequest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientRequestPerformer {
    private final @NotNull HttpClient httpClient;

    public HttpClientRequestPerformer(@NotNull HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Performs the given http client request.
     * @param httpClientRequest the http client request to perform.
     * @return the http client response.
     * @throws IOException gets thrown if http request failed.
     * @throws InterruptedException gets thrown if we got interrupted while sending the request.
     */
    public @NotNull HttpClientResponse perform(@NotNull HttpClientRequest httpClientRequest)
            throws IOException, InterruptedException {
        // Gets the http request and the http config request from the http client request.
        final @NotNull HttpRequest httpRequest = httpClientRequest.httpRequest();
        final @NotNull DXHttpConfigRequest httpConfigRequest = httpClientRequest.httpConfigRequest();

        // Sends the request and gets the response.
        final @NotNull HttpResponse<byte[]> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());

        // Creates the http client response and returns it.
        return HttpClientResponseFactory.createFromHttpResponse(response, httpConfigRequest);
    }
}
