package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigResponse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ResponseEditorWindowFactory {
    @NotNull
    public static ResponseEditorWindow create(@NotNull ResponseEditorController controller, @NotNull String title) {
        try {
            // Creates the loader and sets the controller.
            final var loader = new FXMLLoader(ResponseEditorWindow.class.getResource("view.fxml"));
            loader.setController(controller);

            // Creates the stage and sets its title.
            final var stage = new Stage();
            stage.setTitle(title);

            // Loads the scene, puts it in the stage and shows the stage.
            final var scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

            // Creates the response editor window.
            final var window = new ResponseEditorWindow(controller, stage);

            // Gives the controller a reference to the window.
            controller.setWindow(window);

            // Returns the window.
            return window;
        } catch (IOException ioException) {
            throw new Error("Failed to load response editor view");
        }
    }

    @NotNull
    public static ResponseEditorWindow modify(@NotNull DXHttpConfigResponse configResponse) {
        return create(ResponseEditorControllerFactory.modify(configResponse), "Modify response");
    }

    @NotNull
    public static ResponseEditorWindow create(@NotNull DXHttpConfigRequest configRequest) {
        return create(ResponseEditorControllerFactory.create(configRequest), "Create response");
    }
}
