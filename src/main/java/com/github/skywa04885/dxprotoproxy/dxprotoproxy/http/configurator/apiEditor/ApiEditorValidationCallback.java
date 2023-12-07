package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.apiEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigValidators;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApiEditorValidationCallback implements IApiEditorValidationCallback {
    private final @NotNull DXHttpConfig httpConfig;
    private final @Nullable DXHttpConfigApi httpConfigApi;

    public ApiEditorValidationCallback(@NotNull DXHttpConfig httpConfig, @Nullable DXHttpConfigApi httpConfigApi) {
        this.httpConfig = httpConfig;
        this.httpConfigApi = httpConfigApi;
    }

    public ApiEditorValidationCallback(@NotNull DXHttpConfig httpConfig) {
        this(httpConfig, null);
    }

    public ApiEditorValidationCallback(@NotNull DXHttpConfigApi httpConfigApi) {
        this(httpConfigApi.parent(), httpConfigApi);
    }

    @Override
    public @Nullable String validate(@NotNull String name, @NotNull String httpVersion) {
        if (!DXHttpConfigValidators.isNameValid(name)) {
            return "Name is invalid";
        }

        if (!DXHttpConfigValidators.isHttpVersionValid(httpVersion)) {
            return "HTTP version is invalid";
        }

        if (httpConfigApi != null) {
            if (!httpConfigApi.name().equals(name) && httpConfig.httpApis().containsKey(name)) {
                return "Name is already in use";
            }
        } else {
            if (httpConfig.httpApis().containsKey(name)) {
                return "Name is already in use";
            }
        }

        return null;
    }
}
