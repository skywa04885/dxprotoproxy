package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.instanceEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigInstance;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.HttpConfigInstances;
import org.jetbrains.annotations.NotNull;

public class InstanceEditorControllerFactory {
    public static @NotNull InstanceEditorController create(@NotNull HttpConfigInstances httpConfigInstances) {
        return new InstanceEditorController(null,
                new InstanceEditorValidationCallback(httpConfigInstances),
                new InstanceEditorSubmissionCallback(httpConfigInstances));
    }

    public static @NotNull InstanceEditorController modify(@NotNull DXHttpConfigInstance configInstance) {
        return new InstanceEditorController(configInstance,
                new InstanceEditorValidationCallback(configInstance),
                new InstanceEditorSubmissionCallback(configInstance));
    }
}
