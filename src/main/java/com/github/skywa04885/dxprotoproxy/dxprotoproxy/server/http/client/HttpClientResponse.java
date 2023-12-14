package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.http.client;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class HttpClientResponse {
    private final int code;
    private final @Nullable Map<String, String> headers;
    private  final @Nullable Map<String, String> fields;

    public HttpClientResponse(final int code, @Nullable final Map<String, String> headers,
                          @Nullable final Map<String, String> fields) {
        this.code = code;
        this.headers = headers;
        this.fields = fields;
    }

    public int getCode() {
        return code;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getFields() {
        return fields;
    }
}
