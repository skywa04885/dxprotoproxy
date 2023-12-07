package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RequestEditorWindowFactory {
    @NotNull
    public static RequestEditorWindow create(@NotNull RequestEditorController controller, @NotNull String title) {
        try {
            // Creates the loader and sets the controller.
            final var loader = new FXMLLoader(RequestEditorWindowFactory.class.getResource("view.fxml"));
            loader.setController(controller);

            // Creates the stage and sets its title.
            final var stage = new Stage();
            stage.setTitle(title);

            // Loads the scene, puts it in the stage and shows the stage.
            final var scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

            // Creates the request editor window.
            final var window = new RequestEditorWindow(controller, stage);

            // Gives the controller a reference to the window.
            controller.setWindow(window);

            // Returns the window.
            return window;
        } catch (IOException ioException) {
            throw new Error("Failed to load response editor view");
        }
    }

    /**
     * Creates a request editor that modifies.
     * @param configRequest the request that should be modified.
     * @return the request editor window.
     */
    @NotNull
    public static RequestEditorWindow modify(@NotNull DXHttpConfigRequest configRequest) {
        return create(RequestEditorControllerFactory.modify(configRequest), "Modify request");
    }

    /**
     * Creates a request editor that creates.
     * @param configEndpoint the endpoint that the request should belong to.
     * @return the request editor window.
     */
    @NotNull
    public static RequestEditorWindow create(@NotNull DXHttpConfigEndpoint configEndpoint) {
        return create(RequestEditorControllerFactory.create(configEndpoint), "Create request");
    }
}
