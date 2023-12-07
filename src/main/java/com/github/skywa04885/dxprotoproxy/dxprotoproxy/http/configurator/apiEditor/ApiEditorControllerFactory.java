package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.apiEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import org.jetbrains.annotations.NotNull;

public class ApiEditorControllerFactory {
    public static ApiEditorController create(@NotNull DXHttpConfig httpConfig) {
        return new ApiEditorController(null,
                new ApiEditorValidationCallback(httpConfig),
                new ApiEditorSubmissionCallback(httpConfig));
    }

    public static ApiEditorController create(@NotNull DXHttpConfigApi httpConfigApi) {
        return new ApiEditorController(httpConfigApi,
                new ApiEditorValidationCallback(httpConfigApi),
                new ApiEditorSubmissionCallback(httpConfigApi));
    }
}
