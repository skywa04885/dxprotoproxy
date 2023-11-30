package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import javafx.stage.Stage;

public class ResponseEditorWindow {
    private ResponseEditorController controller;
    private Stage stage;

    public ResponseEditorWindow(ResponseEditorController controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    public static ResponseEditorWindowBuilder newBuilder() {
        return new ResponseEditorWindowBuilder();
    }

    public Stage stage() {
        return stage;
    }
}
