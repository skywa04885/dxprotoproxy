package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.apiEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApiEditorSubmissionCallback implements IApiEditorSubmissionCallback {
    private final @NotNull DXHttpConfig httpConfig;
    private final @Nullable DXHttpConfigApi httpConfigApi;

    public ApiEditorSubmissionCallback(@NotNull DXHttpConfig httpConfig, @Nullable DXHttpConfigApi httpConfigApi) {
        this.httpConfig = httpConfig;
        this.httpConfigApi = httpConfigApi;
    }

    public ApiEditorSubmissionCallback(@NotNull DXHttpConfig httpConfig) {
        this(httpConfig, null);
    }

    public ApiEditorSubmissionCallback(@NotNull DXHttpConfigApi httpConfigApi) {
        this(httpConfigApi.parent(), httpConfigApi);
    }

    private void modify(@NotNull String name, @NotNull String httpVersion) {
        assert httpConfigApi != null;

        if (!httpConfigApi.name().equals(name)) {
            httpConfig.httpApis().remove(httpConfigApi.name());
            httpConfigApi.setName(name);
            httpConfig.httpApis().put(name, httpConfigApi);
        }

        if (!httpConfigApi.httpVersion().equals(httpVersion)) {
            httpConfigApi.setHttpVersion(httpVersion);
        }
    }

    private void create(@NotNull String name, @NotNull String httpVersion) {
        final var configApi = new DXHttpConfigApi(httpConfig, name, httpVersion);
        httpConfig.httpApis().put(configApi.name(), configApi);
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
