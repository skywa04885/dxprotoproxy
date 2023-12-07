package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor.ResponseEditorWindowBuilder;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class RequestEditorWindow {
    @NotNull
    private RequestEditorController controller;
    @NotNull
    private Stage stage;

    public RequestEditorWindow(@NotNull RequestEditorController controller, @NotNull Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    @NotNull
    public static RequestEditorWindowBuilder newBuilder() {
        return new RequestEditorWindowBuilder();
    }

    @NotNull
    public Stage stage() {
        return stage;
    }
}
