package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RequestEditorWindowBuilder {
    private String title = "Request editor";
    private RequestEditorController controller = null;

    /**
     * Uses the given title for the window.
     * @param title the title.
     * @return the current instance.
     */
    public RequestEditorWindowBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Uses the given controller for the window.
     * @param controller the controller to use.
     * @return the current instance.
     */
    public RequestEditorWindowBuilder withController(RequestEditorController controller) {
        this.controller = controller;
        return this;
    }

    /**
     * Builds the response editor window.
     */
    public void build() {
        try {
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

            // Creates the request editor window.
            final var window = new RequestEditorWindow(controller, stage);

            // Gives the controller a reference to the window.
            controller.setWindow(window);

            // Returns the window.
        } catch (IOException ioException) {
            throw new Error("Failed to load response editor view");
        }
    }
}
