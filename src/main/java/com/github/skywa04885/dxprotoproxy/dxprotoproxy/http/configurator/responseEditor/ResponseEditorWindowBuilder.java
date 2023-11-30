package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ResponseEditorWindowBuilder {
    private String title = "Edit response";
    private ResponseEditorController controller = null;

    /**
     * Uses the given title for the window.
     * @param title the title.
     * @return the current instance.
     */
    public ResponseEditorWindowBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Uses the given controller for the window.
     * @param controller the controller to use.
     * @return the current instance.
     */
    public ResponseEditorWindowBuilder withController(ResponseEditorController controller) {
        this.controller = controller;
        return this;
    }

    /**
     * Builds the response editor window.
     * @return the built response editor window.
     * @throws IOException gets thrown when we cannot read the view file.
     */
    public ResponseEditorWindow build() throws IOException {
        assert controller != null;

        // Creates the loader and sets the controller.
        final var loader = new FXMLLoader(getClass().getResource("view.fxml"));
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
    }
}
