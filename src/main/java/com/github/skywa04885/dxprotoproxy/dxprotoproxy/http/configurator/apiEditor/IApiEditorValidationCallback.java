package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.apiEditor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IApiEditorValidationCallback {
    @Nullable String validate(@NotNull String name, @NotNull String httpVersion);
}
