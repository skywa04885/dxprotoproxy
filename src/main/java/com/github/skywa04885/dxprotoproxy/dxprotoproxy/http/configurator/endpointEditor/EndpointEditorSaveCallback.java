package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class EndpointEditorSaveCallback implements IEndpointEditorSaveCallback {
    private final @Nullable DXHttpConfigApi configApi;
    private final @Nullable DXHttpConfigEndpoint configEndpoint;

    /**
     * Creates a new endpoint editor submission callback.
     *
     * @param configApi      the config api.
     * @param configEndpoint the config endpoint.s
     */
    public EndpointEditorSaveCallback(@Nullable DXHttpConfigApi configApi,
                                      @Nullable DXHttpConfigEndpoint configEndpoint) {
        assert (configEndpoint == null && configApi != null) || (configEndpoint != null && configApi == null);

        this.configApi = configApi;
        this.configEndpoint = configEndpoint;
    }

    /**
     * Creates a new endpoint editor submission callback meant for creating endpoints.
     *
     * @param configApi the api that the endpoint should be created for.
     */
    public EndpointEditorSaveCallback(@Nullable DXHttpConfigApi configApi) {
        this(configApi, null);
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
        final @NotNull DXHttpConfigApi configApi = configEndpoint.parent();

        // Updates the name of the config endpoint.
        configApi.endpoints().remove(configEndpoint.name());
        configEndpoint.setName(name);
        configApi.endpoints().put(name, configEndpoint);
    }

    /**
     * Creates a new endpoint.
     *
     * @param name the name of the endpoint to craete.
     */
    private void create(@NotNull String name) {
        assert configApi != null;

        // Creates the new config endpoint and puts it in the map.
        final var configEndpoint = new DXHttpConfigEndpoint(configApi, name);
        configApi.endpoints().put(name, configEndpoint);
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
