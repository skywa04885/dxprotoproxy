package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.client;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.net.NetOutputStreamWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public class HttpClientResponseWriter {
    private final @NotNull NetOutputStreamWrapper outputStream;

    public HttpClientResponseWriter(@NotNull NetOutputStreamWrapper outputStreamWriter) {
        this.outputStream = outputStreamWriter;
    }

    private void writeKeyValueMap(@NotNull Map<String, String> map) throws IOException {
        for (final Map.Entry<String, String> entry : map.entrySet())
            outputStream.writeLine(entry.getKey() + ": " + entry.getValue());

        outputStream.writeLine();
    }

    /**
     * Writes the given response.
     * @param response the response to write.
     * @throws IOException gets thrown if the writing failed/
     */
    public void write(@NotNull HttpClientResponse response) throws IOException {
        // Writes the response code.
        outputStream.writeLine(Integer.toString(response.getCode()));

        // Writes the headers if they are present.
        if (response.getHeaders() != null) {
            writeKeyValueMap(response.getHeaders());
        }

        // Writes the fields if they are present.
        if (response.getFields() != null) {
            writeKeyValueMap(response.getFields());
        }

        // Flushes.
        outputStream.flush();
    }
}
