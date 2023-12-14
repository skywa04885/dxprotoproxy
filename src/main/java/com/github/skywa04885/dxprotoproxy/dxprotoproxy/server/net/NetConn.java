package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.Socket;

public abstract class NetConn implements Runnable {
    protected @Nullable NetService.Active parent;
    protected @NotNull Socket socket;
    protected @NotNull NetOutputStreamWrapper outputStream;
    protected @NotNull NetInputStreamWrapper inputStream;

    /**
     * Constructs a new connection with the given socket.
     *
     * @param socket       the socket.
     * @param outputStream the output stream.
     * @param inputStream  the input stream.
     * @throws Exception the general exception.
     */
    public NetConn(@NotNull Socket socket,
                   @NotNull NetOutputStreamWrapper outputStream,
                   @NotNull NetInputStreamWrapper inputStream) throws Exception {
        this.socket = socket;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    /**
     * Gets the current parent of the connection.
     *
     * @return the parent of the connection.
     */
    public @Nullable NetService.Active parent() {
        return parent;
    }

    /**
     * Sets the parent of the connection.
     *
     * @param parent the new parent.
     */
    public void setParent(@Nullable NetService.Active parent) {
        this.parent = parent;
    }

    /**
     * Gets the output stream.
     *
     * @return the output stream.
     */
    public @NotNull NetOutputStreamWrapper outputStream() {
        return outputStream;
    }

    /**
     * Gets the input stream.
     *
     * @return the input stream.
     */
    public @NotNull NetInputStreamWrapper inputStream() {
        return inputStream;
    }

    /**
     * Gets the connection type label (incoming or outgoing).
     *
     * @return the connection type label string.
     */
    public abstract @NotNull String getTypeLabel();

    /**
     * Closes the network connection.
     * @throws Exception the general exception.
     */
    public abstract void close() throws Exception;
}
