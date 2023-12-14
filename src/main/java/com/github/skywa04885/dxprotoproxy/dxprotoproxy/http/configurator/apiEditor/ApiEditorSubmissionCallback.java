package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.apiEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.HttpConfigApis;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ApiEditorSubmissionCallback implements IApiEditorSubmissionCallback {
    private final @NotNull HttpConfigApis httpConfigApis;
    private final @Nullable DXHttpConfigApi httpConfigApi;

    public ApiEditorSubmissionCallback(@NotNull HttpConfigApis httpConfigApis, @Nullable DXHttpConfigApi httpConfigApi) {
        this.httpConfigApis = httpConfigApis;
        this.httpConfigApi = httpConfigApi;
    }

    public ApiEditorSubmissionCallback(@NotNull HttpConfigApis httpConfigApis) {
        this(httpConfigApis, null);
    }

    public ApiEditorSubmissionCallback(@NotNull DXHttpConfigApi httpConfigApi) {
        this(Objects.requireNonNull(httpConfigApi.parent()), httpConfigApi);
    }

    private void modify(@NotNull String name, @NotNull String httpVersion) {
        assert httpConfigApi != null;

        if (!httpConfigApi.name().equals(name)) {
            httpConfigApis.children().remove(httpConfigApi.name());
            httpConfigApi.setName(name);
            httpConfigApis.children().put(name, httpConfigApi);
        }

        if (!httpConfigApi.httpVersion().equals(httpVersion)) {
            httpConfigApi.setHttpVersion(httpVersion);
        }
    }

    private void create(@NotNull String name, @NotNull String httpVersion) {
        final var configApi = new DXHttpConfigApi(name, httpVersion);
        configApi.setParent(httpConfigApis);
        httpConfigApis.children().put(configApi.name(), configApi);
    }

    @Override
    public void submit(@NotNull String name, @NotNull String httpVersion) {
        if (this.httpConfigApi != null) {
            modify(name, httpVersion);
        } else {
            create(name, httpVersion);
        }
    }
}
