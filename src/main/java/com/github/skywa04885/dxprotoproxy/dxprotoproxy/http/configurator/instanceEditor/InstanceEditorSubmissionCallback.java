package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.instanceEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigInstance;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.HttpConfigApis;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.HttpConfigInstances;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class InstanceEditorSubmissionCallback implements IInstanceEditorSubmissionCallback {
    private final @NotNull HttpConfigInstances httpConfigInstances;
    private final @Nullable DXHttpConfigInstance configInstance;

    public InstanceEditorSubmissionCallback(@NotNull HttpConfigInstances httpConfigInstances,
                                            @Nullable DXHttpConfigInstance configInstance) {
        this.httpConfigInstances = httpConfigInstances;
        this.configInstance = configInstance;
    }

    public InstanceEditorSubmissionCallback(@NotNull HttpConfigInstances httpConfigInstances) {
        this(httpConfigInstances, null);
    }

    public InstanceEditorSubmissionCallback(@NotNull DXHttpConfigInstance configInstance) {
        this(Objects.requireNonNull(configInstance.parent()), configInstance);
    }

    private void update(@NotNull String name, @NotNull String host, int port, @NotNull String protocol) {
        assert configInstance != null;

        // Gets the config api.
        final HttpConfigInstances httpConfigInstances = Objects.requireNonNull(configInstance.parent());

        // Updates the name.
        if (!configInstance.name().equals(name)) {
            httpConfigInstances.children().remove(configInstance.name());
            configInstance.setName(name);
            httpConfigInstances.children().put(name, configInstance);
        }

        // Updates the host.
        if (!configInstance.host().equals(host)) {
            configInstance.setHost(host);
        }

        // Updates the port.
        if (configInstance.port() != port) {
            configInstance.setPort(port);
        }

        // Updates the protocol.
        if (!configInstance.protocol().equals(protocol)) {
            configInstance.setProtocol(protocol);
        }
    }

    private void create(@NotNull String name, @NotNull String host, int port, @NotNull String protocol) {
        // Creates the config instance and puts it in the API.
        final var configInstance = new DXHttpConfigInstance(name, host, port, protocol);
        configInstance.setParent(httpConfigInstances);
        httpConfigInstances.children().put(name, configInstance);
    }

    /**
     * Gets called when the instance editor submits.
     *
     * @param name     the name.
     * @param host     the host.
     * @param port     the port.
     * @param protocol the protocol.
     */
    @Override
    public void submit(@NotNull String name, @NotNull String host, int port, @NotNull String protocol) {
        if (configInstance != null) {
            update(name, host, port, protocol);
        } else {
            create(name, host, port, protocol);
        }
    }
}
