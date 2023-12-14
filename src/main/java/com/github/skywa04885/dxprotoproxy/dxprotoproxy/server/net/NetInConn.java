package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net;

import io.swagger.models.auth.In;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public abstract class NetInConn extends NetConn {
    private final @NotNull SimpleObjectProperty<Date> connectedSince;
    private final @NotNull SimpleStringProperty hostname;
    private final @NotNull SimpleIntegerProperty port;
    private final @NotNull SimpleObjectProperty<InetAddress> address;

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
