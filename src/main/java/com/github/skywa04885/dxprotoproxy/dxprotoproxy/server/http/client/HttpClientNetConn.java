package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.http.client;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net.NetInConn;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net.NetInputStreamWrapper;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net.NetOutputStreamWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.Socket;
import java.net.http.HttpClient;

public final class HttpClientNetConn extends NetInConn implements Runnable {
    /**
     * Constructs a new connection with the given socket.
     *
     * @param socket       the socket.
     * @param outputStream the output stream.
     * @param inputStream  the input stream.
     * @throws Exception the general exception.
     */
    public HttpClientNetConn(@NotNull Socket socket,
                             @NotNull NetOutputStreamWrapper outputStream,
                             @NotNull NetInputStreamWrapper inputStream) throws Exception {
        super(socket, outputStream, inputStream);
    }

    /**
     * Runs all the logic.
     */
    @Override
    public void run() {
        // Gets the net service that the connection belongs to and gets the http config from it.
        final @NotNull var httpClientNetServiceActiveState = (HttpClientNetService.Active) parent();
        final @NotNull var httpClientNetService = (HttpClientNetService) httpClientNetServiceActiveState.parent();
        final @NotNull DXHttpConfig httpConfig = httpClientNetService.httpConfig();

        // Constructs the request reader and the response writer.
        final var httpClientRequestReader = new HttpClientRequestReader(inputStream, httpConfig);
        final var httpClientResponseWriter = new HttpClientResponseWriter(outputStream);

        // Creates the http client and the request performer.
        final var httpClient = HttpClient.newHttpClient();
        final var httpClientRequestPerformer = new HttpClientRequestPerformer(httpClient);

        // Stays in loop until the connection has been closed.
        try {
            while (true) {
                // Reads the request, and if the connection has been closed, break from the loop.
                final @Nullable HttpClientRequest httpClientRequest =
                        httpClientRequestReader.read();
                if (httpClientRequest == null) {
                    break;
                }

                // Performs the http client request.
                final @NotNull HttpClientResponse httpClientResponse =
                        httpClientRequestPerformer.perform(httpClientRequest);

                // Writes the http client response to the client.
                httpClientResponseWriter.write(httpClientResponse);
            }
        } catch (IOException ioException) {

        } catch (InterruptedException interruptedException) {

        } catch (RuntimeException runtimeException) {

        }
    }

    /**
     * Closes the network connection.
     * @throws Exception the general exception.
     */
    @Override
    public void close() throws Exception {
        // Stops the thread.

        // Calls the parent close method.
        super.close();
    }
}
