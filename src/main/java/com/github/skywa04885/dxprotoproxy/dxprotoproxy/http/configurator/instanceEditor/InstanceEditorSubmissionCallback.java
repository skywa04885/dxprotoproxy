package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.instanceEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InstanceEditorSubmissionCallback implements IInstanceEditorSubmissionCallback {
    private final @Nullable DXHttpConfigApi configApi;
    private final @Nullable DXHttpConfigInstance configInstance;

    public InstanceEditorSubmissionCallback(@Nullable DXHttpConfigApi configApi,
                                            @Nullable DXHttpConfigInstance configInstance) {
        assert (configInstance == null && configApi != null) || (configInstance != null && configApi == null);

        this.configApi = configApi;
        this.configInstance = configInstance;
    }

    public InstanceEditorSubmissionCallback(@Nullable DXHttpConfigApi configApi) {
        this(configApi, null);
    }

    public InstanceEditorSubmissionCallback(@Nullable DXHttpConfigInstance configInstance) {
        this(null, configInstance);
    }

    private void update(@NotNull String name, @NotNull String host, int port, @NotNull String protocol) {
        assert configInstance != null;

        // Gets the config api.
        final DXHttpConfigApi configApi = configInstance.parent();

        // Updates the name.
        if (!configInstance.name().equals(name)) {
            configApi.instances().remove(configInstance.name());
            configInstance.setName(name);
            configApi.instances().put(name, configInstance);
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
        assert configApi != null;

        // Creates the config instance and puts it in the API.
        final var configInstance = new DXHttpConfigInstance(configApi, name, host, port, protocol);
        configApi.instances().put(name, configInstance);
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
        if (configApi == null) {
            update(name, host, port, protocol);
        } else {
            create(name, host, port, protocol);
        }
    }
}
