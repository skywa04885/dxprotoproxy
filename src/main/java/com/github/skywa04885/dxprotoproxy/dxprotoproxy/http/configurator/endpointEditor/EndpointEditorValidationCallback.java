package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigValidators;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EndpointEditorValidationCallback implements IEndpointEditorValidationCallback {
    private final @Nullable DXHttpConfigApi configApi;
    private final @Nullable DXHttpConfigEndpoint configEndpoint;

    /**
     * Creates a new endpoint editor validation callback.
     *
     * @param configApi      the config api.
     * @param configEndpoint the config endpoint.s
     */
    public EndpointEditorValidationCallback(@Nullable DXHttpConfigApi configApi,
                                      @Nullable DXHttpConfigEndpoint configEndpoint) {
        assert (configEndpoint == null && configApi != null) || (configEndpoint != null && configApi == null);

        this.configApi = configApi;
        this.configEndpoint = configEndpoint;
    }

    /**
     * Creates a new endpoint editor validation callback meant for creating endpoints.
     *
     * @param configApi the api that the endpoint should be created for.
     */
    public EndpointEditorValidationCallback(@Nullable DXHttpConfigApi configApi) {
        this(configApi, null);
    }

    /**
     * Creates a new endpoint editor validation callback meant for modifying endpoints.
     *
     * @param configEndpoint the endpoint that should be modified.
     */
    public EndpointEditorValidationCallback(@Nullable DXHttpConfigEndpoint configEndpoint) {
        this(null, configEndpoint);
    }

    @Override
    public @Nullable String validate(@NotNull String name) {

        // Validates the name.
        if (!DXHttpConfigValidators.isNameValid(name)) {
            return "Invalid name";
        }

        // Checks if the name is already in use.
        if (configEndpoint == null) {
            assert configApi != null;

            // Checks if the name is already in use.
            if (configApi.endpoints().containsKey(name)) {
                return "Name already in use";
            }
        } else {
            // Gets the api.
            final @NotNull DXHttpConfigApi configApi = configEndpoint.parent();

            // Checks if the name changed, and if it has, whether or not it's already in use.
            if (!configEndpoint.name().equals(name) && configApi.endpoints().containsKey(name)) {
                return "Name already in use";
            }
        }

        return null;
    }
}
