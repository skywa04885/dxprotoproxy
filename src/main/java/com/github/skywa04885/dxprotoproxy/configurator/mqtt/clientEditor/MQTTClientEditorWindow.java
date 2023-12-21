package com.github.skywa04885.dxprotoproxy.configurator.mqtt.clientEditor;

import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class MQTTClientEditorWindow {
    private final @NotNull MQTTClientEditorController controller;
    private final @NotNull Stage stage;

    /**
     * Constructs a new mqtt client editor window.
     * @param controller the controller.
     * @param stage the stage.
     */
    public MQTTClientEditorWindow(@NotNull MQTTClientEditorController controller, @NotNull Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    /**
     * Gets the stage.
     * @return the stage.
     */
    public @NotNull Stage getStage() {
        return stage;
    }
}
