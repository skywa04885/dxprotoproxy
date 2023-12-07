package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import org.jetbrains.annotations.NotNull;

public class EndpointEditorControllerFactory {
    public static @NotNull EndpointEditorController create(@NotNull DXHttpConfigApi configApi) {
        return new EndpointEditorController(
                null,
                new EndpointEditorSaveCallback(configApi),
                new EndpointEditorValidationCallback(configApi)
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
