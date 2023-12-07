package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.Window;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;


public class PrimaryWindow extends Window<PrimaryController> {
    private @NotNull PrimaryController controller;
    private final @NotNull Stage stage;

    public PrimaryWindow(@NotNull PrimaryController controller, @NotNull Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    public @NotNull Stage stage() {
        return stage;
    }
}
