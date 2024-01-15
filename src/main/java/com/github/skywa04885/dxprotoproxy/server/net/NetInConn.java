package com.github.skywa04885.dxprotoproxy.server.net;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public abstract class NetInConn implements Runnable {
    protected @Nullable NetService.Active parent;
    protected @NotNull Socket socket;
    protected @NotNull NetOutputStreamWrapper outputStream;
    protected @NotNull NetInputStreamWrapper inputStream;
    private final @NotNull SimpleObjectProperty<Date> connectedSince;
    private final @NotNull SimpleStringProperty hostname;
    private final @NotNull SimpleIntegerProperty port;
    private final @NotNull SimpleObjectProperty<InetAddress> address;


    public NetInConn(@NotNull Socket socket,
                      @NotNull NetOutputStreamWrapper outputStream,
                      @NotNull NetInputStreamWrapper inputStream) throws Exception {
        this.socket = socket;
        this.outputStream = outputStream;
        this.inputStream = inputStream;

        connectedSince = new SimpleObjectProperty<>(null, "ConnectedSince",
                new Date());
        hostname = new SimpleStringProperty(null, "Hostname",
                ((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName());
        port = new SimpleIntegerProperty(null, "Port",
                ((InetSocketAddress) socket.getRemoteSocketAddress()).getPort());
        address = new SimpleObjectProperty<>(null, "Address",
                ((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress());
    }

    /**
     * Gets the connected since property.
     * @return the connected since property.
     */
    public SimpleObjectProperty<Date> connectedSinceProperty() {
        return connectedSince;
    }

    /**
     * Gets the hostname property.
     * @return the hostname property.
     */
    public SimpleStringProperty hostnameProperty() {
        return hostname;
    }

    /**
     * Gets the port property.
     * @return the port property.
     */
    public SimpleIntegerProperty portProperty() {
        return port;
    }

    /**
     * Gets the address property.
     * @return the address.
     */
    public SimpleObjectProperty<InetAddress> addressProperty() {
        return address;
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
     * Closes the network connection.
     * @throws Exception the general exception.
     */
    public void close() throws Exception {
        // Closes the socket.
        socket.close();
    }
}
