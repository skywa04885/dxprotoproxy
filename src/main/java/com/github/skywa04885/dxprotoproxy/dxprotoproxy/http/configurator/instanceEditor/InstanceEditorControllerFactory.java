package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.instanceEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigInstance;
import org.jetbrains.annotations.NotNull;

public class InstanceEditorControllerFactory {
    public static @NotNull InstanceEditorController create(@NotNull DXHttpConfigApi configApi) {
        return new InstanceEditorController(null,
                new InstanceEditorValidationCallback(configApi),
                new InstanceEditorSubmissionCallback(configApi));
    }

    public static @NotNull InstanceEditorController modify(@NotNull DXHttpConfigInstance configInstance) {
        return new InstanceEditorController(configInstance,
                new InstanceEditorValidationCallback(configInstance),
                new InstanceEditorSubmissionCallback(configInstance));
    }
}
