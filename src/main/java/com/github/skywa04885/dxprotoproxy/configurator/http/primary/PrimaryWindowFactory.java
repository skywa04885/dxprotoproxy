package com.github.skywa04885.dxprotoproxy.configurator.http.primary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PrimaryWindowFactory {
    @NotNull
    public static PrimaryWindow create(@NotNull Stage stage, @NotNull PrimaryController controller) {
        try {
            // Creates the loader and sets the controller.
            final var loader = new FXMLLoader(PrimaryWindowFactory.class.getResource("view.fxml"));
            loader.setController(controller);

            // Sets the stage title.
            stage.setTitle("DXProtoProxy-Configurator");

            // Loads the scene, puts it in the stage and shows the stage.
            final var scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

            // Creates the request editor window.
            final var window = new PrimaryWindow(controller, stage);
            controller.setWindow(window);

            // Returns the window.
            return window;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new Error("Failed to load response editor view");
        }
    }
}
