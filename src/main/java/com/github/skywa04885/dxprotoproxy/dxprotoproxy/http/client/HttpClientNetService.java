package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.client;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigInstance;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.net.*;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpClientNetService extends NetInService {
    /**
     * Represents the active http net service.
     */
    public static class Active extends NetInService.Active {

        /**
         * Constructs a new active state that will accept on the given server socket.
         *
         * @param serverSocket the server socket to accept on.
         */
        public Active(@NotNull ServerSocket serverSocket) {
            super(serverSocket);
        }

        /**
         * Wraps the given socket.
         * @param socket the socket to wrap.
         * @return the wrapped socket.
         */
        @Override
        protected @NotNull NetInConn wrapSocket(@NotNull Socket socket) throws Exception {
            final var outputStream = new NetOutputStreamWrapper(socket.getOutputStream());
            final var inputStream = new NetInputStreamWrapper(socket.getInputStream());
            final var conn = new HttpClientNetConn(socket, outputStream, inputStream);
            return conn;
        }
    }

    public static final String NAME = "HTTP-Client";
    public static final String DESCRIPTION = "Simplified HTTP client for PLCs";
    public static final int PORT = 45000;
    public static final int BACKLOG = 100;
    public static final InetAddress ADDRESS;

    // Initializes all the static variables.
    static {
        try {
            ADDRESS = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    protected @NotNull DXHttpConfig httpConfig;

    /**
     * Constructs a new http client net service that uses the given config.
     * @param httpConfig the config to use.
     * @throws Exception gets thrown when a general exception occurs.
     */
    public HttpClientNetService(@NotNull DXHttpConfig httpConfig) throws Exception {
        this.httpConfig = httpConfig;
    }

    /**
     * Gets the HTTP config.
     * @return the http config.
     */
    public @NotNull DXHttpConfig httpConfig() {
        return httpConfig;
    }

    /**
     * Sets the http config.
     * @param httpConfig the new http config.
     */
    public void setHttpConfig(@NotNull DXHttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    /**
     * Gets the name of the service.
     * @return the string containing the name of the service.
     */
    public @NotNull String getName() {
        return NAME;
    }

    /**
     * Gets the description of the service.
     * @return the string containing the description of the service.
     */
    public @NotNull String getDescription() {
        return DESCRIPTION;
    }

    /**
     * Creates the active state.
     * @return the active state.
     * @throws Exception gets thrown if we failed to create the server socket.
     */
    @Override
    protected NetService.Active createActiveState() throws Exception {
        // Creates the server socket that listens on the given port and address with the given backlog.
        final var serverSocket = new ServerSocket(PORT, BACKLOG, ADDRESS);

        // Creates the active state with the given server socket.
        return new Active(serverSocket);
    }

}
