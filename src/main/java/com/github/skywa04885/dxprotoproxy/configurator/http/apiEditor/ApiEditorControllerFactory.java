package com.github.skywa04885.dxprotoproxy.configurator.http.apiEditor;

import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.config.http.HttpConfigApis;
import org.jetbrains.annotations.NotNull;

public class ApiEditorControllerFactory {
    public static ApiEditorController create(@NotNull HttpConfigApis httpConfigApis) {
        return new ApiEditorController(null,
                new ApiEditorValidationCallback(httpConfigApis),
                new ApiEditorSubmissionCallback(httpConfigApis));
    }

    public static ApiEditorController create(@NotNull DXHttpConfigApi httpConfigApi) {
        return new ApiEditorController(httpConfigApi,
                new ApiEditorValidationCallback(httpConfigApi),
                new ApiEditorSubmissionCallback(httpConfigApi));
    }
}
