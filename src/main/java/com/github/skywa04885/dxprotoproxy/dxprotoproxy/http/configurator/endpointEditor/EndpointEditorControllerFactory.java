package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.HttpConfigEndpoints;
import org.jetbrains.annotations.NotNull;

public class EndpointEditorControllerFactory {
    public static @NotNull EndpointEditorController create(@NotNull HttpConfigEndpoints httpConfigEndpoints) {
        return new EndpointEditorController(
                null,
                new EndpointEditorSaveCallback(httpConfigEndpoints),
                new EndpointEditorValidationCallback(httpConfigEndpoints)
        );
    }

    public static @NotNull EndpointEditorController modify(@NotNull DXHttpConfigEndpoint configEndpoint) {
        return new EndpointEditorController(
                configEndpoint,
                new EndpointEditorSaveCallback(configEndpoint),
                new EndpointEditorValidationCallback(configEndpoint)
        );
    }
}
