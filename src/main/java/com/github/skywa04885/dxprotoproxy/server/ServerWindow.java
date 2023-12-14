package com.github.skywa04885.dxprotoproxy.server;

import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class ServerWindow {
    private @NotNull ServerController controller;
    private @NotNull final Stage stage;

    public ServerWindow(@NotNull ServerController controller, @NotNull Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    public @NotNull Stage stage() {
        return stage;
    }
}
