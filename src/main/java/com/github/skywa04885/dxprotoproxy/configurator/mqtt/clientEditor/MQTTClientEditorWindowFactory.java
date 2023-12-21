package com.github.skywa04885.dxprotoproxy.configurator.mqtt.clientEditor;

import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientsConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class MQTTClientEditorWindowFactory {
    /**
     * Constructs a new client editor window with the given stage, controller and title.
     * @param stage the stage.
     * @param controller the controller.
     * @param title the title.
     * @return the constructed window.
     */
    private static @NotNull MQTTClientEditorWindow createWindow(@NotNull MQTTClientEditorController controller,
                                                                @NotNull String title) {
        try {
            final var loader = new FXMLLoader(MQTTClientEditorWindowFactory.class.getResource("view.fxml"));
            loader.setController(controller);

            final var stage = new Stage();
            stage.setTitle(title);

            final var scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

            final var window = new MQTTClientEditorWindow(controller, stage);

            controller.setWindow(window);

            return window;
        } catch (IOException ioException) {
            throw new Error("Failed to load client editor view");
        }
    }

    public static MQTTClientEditorWindow create(@NotNull MQTTClientsConfig mqttClientsConfig) {
        // Creates the controller.
        final var controller = new MQTTClientEditorController(mqttClientsConfig,
                new MQTTClientEditorSubmissionCallback(mqttClientsConfig),
                new MQTTClientEditorValidationCallback());

        // Returns the window.
        return createWindow(controller, "Create client");
    }


    public static MQTTClientEditorWindow update(@NotNull MQTTClientConfig mqttClientConfig) {
        // Creates the controller.
        final var controller = new MQTTClientEditorController(
                mqttClientConfig,
                new MQTTClientEditorSubmissionCallback(mqttClientConfig),
                new MQTTClientEditorValidationCallback());

        // Returns the window.
        return createWindow(controller, "Modify client");
    }

}
