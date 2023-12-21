package com.github.skywa04885.dxprotoproxy.server.net;

import com.github.skywa04885.dxprotoproxy.Producer;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

public abstract class NetOutConn {
    /**
     * This enum represents the state of an outgoing network connection.
     */
    public enum State {
        Disconnected("Disconnected"),
        Connecting("Connecting"),
        Connected("Connected");

        private final @NotNull String label;

        /**
         * Constructs a new state that has the given label.
         * @param label the label for the state.
         */
        State(@NotNull String label) {
            this.label = label;
        }

        /**
         * Gets the label of the state.
         * @return the label of the state.
         */
        public @NotNull String label() {
            return label;
        }
    }

    /**
     * Member variables
     */
    private final @NotNull String hostname;
    private final int port;
    private @NotNull State state;
    private @NotNull Producer<State> stateProducer;

    public NetOutConn(@NotNull String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Gets the current state.
     * @return the current state.
     */
    public @NotNull State state() {
        return state;
    }

    /**
     * Gets the state producer
     * @return the state producer.
     */
    public @NotNull Producer<State> stateProducer() {
        return stateProducer;
    }

    /**
     * Gets the connection type label (incoming or outgoing).
     * @return the connection type label string.
     */
    public @NotNull String getTypeLabel() {
        return "Outgoing";
    }

    protected abstract void runImpl(@NotNull Socket socket);

    public void run() {
        while (true) {
            try {
                while (state != State.Connected) {
                    // Changes the state to connecting.
                    stateProducer.produce(state = State.Connecting);

                    // Attempts to connect.
                    try {
                        // Creates the socket and connects to the remote system.
                        final var socket = new Socket(hostname, port);

                        // Changes the state to connected.
                        stateProducer.produce(state = State.Connected);

                        // Calls the user code for handling the socket.
                        runImpl(socket);

                        // Closes the socket since we're done with it.
                        socket.close();
                    } catch (IOException e) {
                        // Set the state back to disconnected.;
                        stateProducer.produce(state = State.Disconnected);

                        // Wait half a second before we attempt to connect again.
                        Thread.sleep(500);
                    }
                }
            } catch (InterruptedException interruptedException) {
                break;
            }
        }
    }
}
