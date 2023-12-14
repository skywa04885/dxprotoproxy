package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ServerWindowFactory {
    @NotNull
    public static ServerWindow create(@NotNull Stage stage, @NotNull ServerController controller) {
        try {
            // Creates the loader and sets the controller.
            final var loader = new FXMLLoader(ServerWindowFactory.class.getResource("view.fxml"));
            loader.setController(controller);

            // Sets the title of the stage.
            stage.setTitle("DXProtoProxy Server");

            // Loads the scene, puts it in the stage and shows the stage.
            final var scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

            // Creates the server window.
            final var window = new ServerWindow(controller, stage);
            controller.setWindow(window);

            // Returns the window.
            return window;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new Error("Failed to load server window view");
        }
    }
}
