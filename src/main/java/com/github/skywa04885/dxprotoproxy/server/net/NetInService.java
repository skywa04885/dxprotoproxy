package com.github.skywa04885.dxprotoproxy.server.net;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Logger;

public abstract class NetInService extends NetService {
    // TODO: Put server socket in accepting thread, create a builder-mechanism for this.

    public NetInService() throws Exception {
    }

    public static abstract class Active extends NetService.Active {
        /**
         * Represents a handle for a connection.
         *
         * @param conn   the connection.
         * @param thread the thread running the connection.
         */
        public static record NetConnHandle(@NotNull NetInConn conn, @NotNull Thread thread) {

        }

        private final @NotNull ServerSocket serverSocket;
        private final @NotNull LinkedList<NetConnHandle> internalConnHandles;
        private final @NotNull Logger logger;
        private @Nullable Thread acceptingThread;

        /**
         * Constructs a new active state that will accept on the given server socket.
         *
         * @param serverSocket the server socket to accept on.
         */
        public Active(@NotNull ServerSocket serverSocket) {
            this.serverSocket = serverSocket;

            logger = Logger.getLogger(getClass().getName());
            internalConnHandles = new LinkedList<>();
        }

        /**
         * Starts accepting the clients.
         *
         * @throws Exception gets thrown if any exception occurs.
         */
        @Override
        public void entry() throws Exception {
            // We shouldn't be accepting yet.
            assert acceptingThread == null;

            // Logs that we're entering the state.
            logger.info("Entering active state, starting to accept connections");

            // Creates the accepting thread.
            acceptingThread = new Thread(() -> {
                // Stays in loop as long as the server socket has not been closed.
                while (true) {
                    try {
                        // Accepts the socket from the server.
                        final Socket socket = serverSocket.accept();

                        // Logs that we've accepted the connection.
                        logger.info("Accepted connection with remote address "
                                + socket.getRemoteSocketAddress());

                        // Wrap the socket and set its parent.
                        final NetInConn conn = wrapSocket(socket);
                        conn.setParent(this);

                        // Creates the thread that runs the connection.
                        final var thread = new Thread(() -> {
                            conn.run();

                            System.out.println("Close");
                            try {
                                conn.close();

                                // TODO: do this better.
                                internalConnHandles.removeIf(v -> v.conn() == conn);
                            } catch (Exception exception) {
                                logger.warning("Failed to close connection, message: " + exception.getMessage());
                            }
                        });

                        // Creates the net connection handle.
                        final var connHandle = new NetConnHandle(conn, thread);

                        // Starts the thread.
                        thread.start();

                        // Puts the connection in the list of connections.
                        synchronized (internalConnHandles) {
                            internalConnHandles.add(connHandle);
                        }
                    } catch (Exception exception) {
                        // If the server socket has been closed, break from the loop.
                        if (serverSocket.isClosed()) {
                            break;
                        }

                        // Prints the stack trace. TODO: remove this.
                        exception.printStackTrace();
                    }
                }
            });

            // Starts the accepting thread.
            acceptingThread.start();
        }

        /**
         * Closes the given connection.
         *
         * @param connHandle the connection.
         * @throws Exception gets thrown if the closing fails.
         */
        public void close(@NotNull NetConnHandle connHandle) throws Exception {
            // Removes the connection handle from the list of connections handles.
            synchronized (internalConnHandles) {
                internalConnHandles.remove(connHandle);
            }

            // Closes the connection and waits for it's thread to stop.
            connHandle.conn().close();
            connHandle.thread().join();
        }

        /**
         * Stops accepting the clients and closes the server socket.
         *
         * @throws Exception gets thrown if any exception occurs.
         */
        @Override
        public void exit() throws Exception {
            // We should be accepting.
            assert acceptingThread != null;

            // Logs that we're exiting the active state.
            logger.info("Exiting active state");

            // Closes the server socket.
            serverSocket.close();

            // Wait for the accepting thread to stop and then set it to null.
            acceptingThread.join();
            acceptingThread = null;

            // Closes all the connections.
            while (true) {
                NetConnHandle connHandle;

                synchronized (internalConnHandles) {
                    if (internalConnHandles.isEmpty()) {
                        break;
                    }

                    connHandle = internalConnHandles.getFirst();
                }

                close(connHandle);
            }
        }

        /**
         * Wraps the given socket.
         *
         * @param socket the socket to wrap.
         * @return the wrapped socket.
         */
        protected abstract @NotNull NetInConn wrapSocket(@NotNull Socket socket) throws Exception;
    }
}
