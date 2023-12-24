package com.github.skywa04885.dxprotoproxy.fx;

import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class WindowFacade<T extends ControllerFacade> {
    final @NotNull Stage stage;
    final @NotNull T controller;

    WindowFacade(@NotNull Stage stage, @NotNull T controller) {
        this.stage = stage;
        this.controller = controller;
    }

    /**
     * Gets the controller.
     * @return the controller.
     */
    public @NotNull T controller() {
        return controller;
    }

    /**
     * Closes the window.
     */
    public void close() {
        stage.close();
    }

    public @NotNull Stage stage() {
        return stage;
    }
}
