package com.github.skywa04885.dxprotoproxy.configurator.http.apiEditor;

import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.config.http.HttpConfigApis;
import com.github.skywa04885.dxprotoproxy.GlobalConstants;
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
        if (!GlobalConstants.isNameValid(name)) {
            return "Name is invalid";
        }

        if (!GlobalConstants.isHttpVersionValid(httpVersion)) {
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
