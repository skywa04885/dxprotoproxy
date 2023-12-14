package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class NetServiceRegistry {
    private final @NotNull SimpleListProperty<NetService> services;

    public NetServiceRegistry(@NotNull List<NetService> services) {
        this.services = new SimpleListProperty<NetService>("", null, FXCollections.observableList(services));
    }

    public NetServiceRegistry() {
        this(new LinkedList<>());
    }

    public @NotNull List<NetService> services() {
        return services.get();
    }

    public @NotNull SimpleListProperty<NetService> servicesProperty() {
        return services;
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
     * Unregisters all the services.
     */
    public void unregisterAll() {
        services.clear();
    }

    /**
     * Activates all the registered services.
     *
     * @throws Exception gets called if a general exception occurred.
     */
    public void activateAll() throws Exception {
        for (final NetService service : services) {
            if (service.state() instanceof NetService.Inactive) {
                service.activate();
            }
        }
    }


    /**
     * Deactivates all the registered services.
     *
     * @throws Exception gets called if a general exception occurred.
     */
    public void deactivateAll() throws Exception {
        for (final NetService service : services) {
            if (service.state() instanceof NetService.Active) {
                service.deactivate();
            }
        }
    }
}
