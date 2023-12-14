package com.github.skywa04885.dxprotoproxy.dxprotoproxy.net;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class NetServiceRegistry {
    private final @NotNull LinkedList<NetService> services;

    public NetServiceRegistry() {
        this.services = new LinkedList<>();
    }

    /**
     * Registers the given service.
     *
     * @param service the service to register.
     */
    public void register(@NotNull NetService service) {
        services.add(service);
    }

    /**
     * Activates all the registered services.
     *
     * @throws Exception gets called if a general exception occurred.
     */
    public void activateAll() throws Exception {
        for (final NetService service : services) {
            service.activate();
        }
    }


    /**
     * Deactivates all the registered services.
     *
     * @throws Exception gets called if a general exception occurred.
     */
    public void deactivateAll() throws Exception {
        for (final NetService service : services) {
            service.deactivate();
        }
    }
}
