package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.instanceEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigInstance;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.HttpConfigInstances;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class InstanceEditorWindowFactory {
    public static InstanceEditorWindow create(@NotNull InstanceEditorController controller, @NotNull String title) {
        try {
            // Creates the loader and sets the controller.
            final var loader = new FXMLLoader(InstanceEditorWindowFactory.class.getResource("view.fxml"));
            loader.setController(controller);

            // Creates the stage and sets its title.
            final var stage = new Stage();
            stage.setTitle(title);

            // Loads the scene, puts it in the stage and shows the stage.
            final var scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

            // Creates the response editor window.
            final var window = new InstanceEditorWindow(controller, stage);

            // Gives the controller a reference to the window.
            controller.setWindow(window);

            // Returns the window.
            return window;
        } catch (IOException ioException) {
            throw new Error("Failed to load instance editor view");
        }
    }

    @NotNull
    public static InstanceEditorWindow modify(@NotNull DXHttpConfigInstance configInstance) {
        return create(InstanceEditorControllerFactory.modify(configInstance), "Modify instance");
    }

    @NotNull
    public static InstanceEditorWindow create(@NotNull HttpConfigInstances configInstances) {
        return create(InstanceEditorControllerFactory.create(configInstances), "Create instance");
    }
}
