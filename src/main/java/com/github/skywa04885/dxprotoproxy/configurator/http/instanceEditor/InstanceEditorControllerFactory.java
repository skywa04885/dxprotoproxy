package com.github.skywa04885.dxprotoproxy.configurator.http.instanceEditor;

import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfigInstance;
import com.github.skywa04885.dxprotoproxy.config.http.HttpConfigInstances;
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
