package com.github.skywa04885.dxprotoproxy.dxprotoproxy.net;

import org.jetbrains.annotations.NotNull;

import java.net.Socket;

public abstract class NetInConn extends NetConn {
    /**
     * Constructs a new connection with the given socket.
     * @param socket the socket.
     * @param outputStream the output stream.
     * @param inputStream the input stream.
     * @throws Exception the general exception.
     */
    public NetInConn(@NotNull Socket socket,
                      @NotNull NetOutputStreamWrapper outputStream,
                      @NotNull NetInputStreamWrapper inputStream) throws Exception {
        super(socket, outputStream, inputStream);
    }

    /**
     * Gets the connection type label (incoming or outgoing).
     * @return the connection type label string.
     */
    public @NotNull String getTypeLabel() {
        return "Incoming";
    }


    /**
     * Closes the network connection.
     * @throws Exception the general exception.
     */
    @Override
    public void close() throws Exception {
        // Closes the socket.
        socket.close();
    }
}
