package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigValidators;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.HttpConfigEndpoints;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EndpointEditorValidationCallback implements IEndpointEditorValidationCallback {
    private final @Nullable HttpConfigEndpoints httpConfigEndpoints;
    private final @Nullable DXHttpConfigEndpoint configEndpoint;

    /**
     * Creates a new endpoint editor validation callback.
     *
     * @param httpConfigEndpoints      the config endpoints.
     * @param configEndpoint the config endpoint.s
     */
    public EndpointEditorValidationCallback(@Nullable HttpConfigEndpoints httpConfigEndpoints,
                                      @Nullable DXHttpConfigEndpoint configEndpoint) {
        this.httpConfigEndpoints = httpConfigEndpoints;
        this.configEndpoint = configEndpoint;
    }

    /**
     * Creates a new endpoint editor validation callback meant for creating endpoints.
     *
     * @param httpConfigEndpoints the config endpoints.
     */
    public EndpointEditorValidationCallback(@Nullable HttpConfigEndpoints httpConfigEndpoints) {
        this(httpConfigEndpoints, null);
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
            assert httpConfigEndpoints != null;

            // Checks if the name is already in use.
            if (httpConfigEndpoints.children().containsKey(name)) {
                return "Name already in use";
            }
        } else {
            // Gets the api.
            // Checks if the name changed, and if it has, whether or not it's already in use.
            if (!configEndpoint.name().equals(name) &&
                    Objects.requireNonNull(configEndpoint.parent()).children().containsKey(name)) {
                return "Name already in use";
            }
        }

        return null;
    }
}
