package com.github.skywa04885.dxprotoproxy.server.net;

import java.util.List;

public abstract class NetOutService extends NetService {
    public static abstract class Active extends NetService.Active {
        static record RegisteredConnection(NetOutConn connection, Thread thread) {}

        private List<RegisteredConnection> registeredConnections;

        protected abstract List<NetOutConn> createConnectionsImpl();

        @Override
        public void entry() throws Exception {
            // Creates all the connections and starts their threads.
            registeredConnections = createConnectionsImpl().stream().map(connection -> {
                final var thread = new Thread(connection);
                thread.start();

                return new RegisteredConnection(connection, thread);
            }).toList();
        }

        @Override
        public void exit() throws Exception {
            // Interrupts all the threads.
            registeredConnections.stream().forEach(registeredConnection -> {
                registeredConnection.connection.close();
            });
        }
    }

    public NetOutService() throws Exception {
    }
}
