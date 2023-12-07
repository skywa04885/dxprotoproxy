package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.apiEditor;

import org.jetbrains.annotations.NotNull;

public interface IApiEditorSubmissionCallback {
    void submit(@NotNull String name, @NotNull String httpVersion);
}
