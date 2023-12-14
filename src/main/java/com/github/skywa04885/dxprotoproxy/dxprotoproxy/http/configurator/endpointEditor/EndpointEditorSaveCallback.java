package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.HttpConfigEndpoints;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

public class EndpointEditorSaveCallback implements IEndpointEditorSaveCallback {
    private final @Nullable HttpConfigEndpoints configEndpoints;
    private final @Nullable DXHttpConfigEndpoint configEndpoint;

    /**
     * Creates a new endpoint editor submission callback.
     *
     * @param configEndpoints      the config endpoints.
     * @param configEndpoint the config endpoint.
     */
    public EndpointEditorSaveCallback(@Nullable HttpConfigEndpoints configEndpoints,
                                      @Nullable DXHttpConfigEndpoint configEndpoint) {
        this.configEndpoints = configEndpoints;
        this.configEndpoint = configEndpoint;
    }

    /**
     * Creates a new endpoint editor submission callback meant for creating endpoints.
     *
     * @param configEndpoints the config endpoints.
     */
    public EndpointEditorSaveCallback(@Nullable HttpConfigEndpoints configEndpoints) {
        this(configEndpoints, null);
    }

    /**
     * Creates a new endpoint editor submission callback meant for modifying endpoints.
     *
     * @param configEndpoint the endpoint that should be modified.
     */
    public EndpointEditorSaveCallback(@Nullable DXHttpConfigEndpoint configEndpoint) {
        this(null, configEndpoint);
    }

    /**
     * Updates an existing endpoint.
     *
     * @param name the name of the endpoint to update.
     */
    private void update(@NotNull String name) {
        assert configEndpoint != null;

        // gets the config api.
        final @NotNull HttpConfigEndpoints configApi =
                Objects.requireNonNull(configEndpoint.parent());

        // Updates the name of the config endpoint.
        configApi.children().remove(configEndpoint.name());
        configEndpoint.setName(name);
        configApi.children().put(name, configEndpoint);
    }

    /**
     * Creates a new endpoint.
     *
     * @param name the name of the endpoint to craete.
     */
    private void create(@NotNull String name) {
        assert configEndpoints != null;

        // Creates the new config endpoint and puts it in the map.
        final var configEndpoint = new DXHttpConfigEndpoint(name);
        configEndpoint.setParent(configEndpoints);
        configEndpoints.children().put(name, configEndpoint);
    }

    /**
     * Gets called when the endpoint editor has been submitted.
     *
     * @param name the name of the endpoint.
     */
    @Override
    public void submit(@NotNull String name) {
        // Checks whether we should create or update.
        if (configEndpoint == null) {
            create(name);
        } else {
            update(name);
        }
    }
}
