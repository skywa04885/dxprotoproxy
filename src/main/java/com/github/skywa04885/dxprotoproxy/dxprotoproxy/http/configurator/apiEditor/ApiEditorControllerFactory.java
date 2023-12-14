package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.apiEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.HttpConfigApis;
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
