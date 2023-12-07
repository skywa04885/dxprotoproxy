package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PrimaryWindowFactory {
    @NotNull
    public static PrimaryWindow create(@NotNull PrimaryController controller, @NotNull String title) {
        try {
            // Creates the loader and sets the controller.
            final var loader = new FXMLLoader(PrimaryWindowFactory.class.getResource("view.fxml"));
            loader.setController(controller);

            // Creates the stage and sets its title.
            final var stage = new Stage();
            stage.setTitle(title);

            // Loads the scene, puts it in the stage and shows the stage.
            final var scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

            // Creates the request editor window.
            final var window = new PrimaryWindow(controller, stage);

            // Returns the window.
            return window;
        } catch (IOException ioException) {
            throw new Error("Failed to load response editor view");
        }
    }
}
