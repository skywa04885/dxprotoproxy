package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.apiEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.HttpConfigValidators;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.HttpConfigApis;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ApiEditorValidationCallback implements IApiEditorValidationCallback {
    private final @NotNull HttpConfigApis httpConfigApis;
    private final @Nullable DXHttpConfigApi httpConfigApi;

    public ApiEditorValidationCallback(@NotNull HttpConfigApis httpConfigApis, @Nullable DXHttpConfigApi httpConfigApi) {
        this.httpConfigApis = httpConfigApis;
        this.httpConfigApi = httpConfigApi;
    }

    public ApiEditorValidationCallback(@NotNull HttpConfigApis httpConfigApis) {
        this(httpConfigApis, null);
    }

    public ApiEditorValidationCallback(@NotNull DXHttpConfigApi httpConfigApi) {
        this(Objects.requireNonNull(httpConfigApi.parent()), httpConfigApi);
    }

    @Override
    public @Nullable String validate(@NotNull String name, @NotNull String httpVersion) {
        if (!HttpConfigValidators.isNameValid(name)) {
            return "Name is invalid";
        }

        if (!HttpConfigValidators.isHttpVersionValid(httpVersion)) {
            return "HTTP version is invalid";
        }

        if (httpConfigApi != null) {
            if (!httpConfigApi.name().equals(name) && httpConfigApis.children().containsKey(name)) {
                return "Name is already in use";
            }
        } else {
            if (httpConfigApis.children().containsKey(name)) {
                return "Name is already in use";
            }
        }

        return null;
    }
}
