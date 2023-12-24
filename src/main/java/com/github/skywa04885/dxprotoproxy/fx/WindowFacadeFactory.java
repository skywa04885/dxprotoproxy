package com.github.skywa04885.dxprotoproxy.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

public class WindowFacadeFactory {
    /**
     * Creates a new window.
     *
     * @param url    the url of the fxml resource.
     * @param title  the title of the window.
     * @return the created window.
     * @throws IOException gets thrown if the resource could not be found.
     */
    public static @NotNull <T extends ControllerFacade> WindowFacade<T> create(
            @NotNull URL url, @NotNull Stage stage, @NotNull String title,
            @NotNull Class<T> controllerClass) throws IOException {
        // Sets the title of the stage.
        stage.setTitle(title);

        // Creates the FXML loader and loads the parent.
        final var fxmlLoader = new FXMLLoader(url);
        final Parent sceneParent = fxmlLoader.load();

        // Checks if the controller is of the valid type.
        final var loadedController = fxmlLoader.getController();
        if (controllerClass.isInstance(loadedController)) {
            // Gets the controller.
            final T controller = controllerClass.cast(loadedController);

            // Creates the scene from the parent.
            final var scene = new Scene(sceneParent);

            // Assigns the scene to the stage.
            stage.setScene(scene);

            // Creates the window and assigns the window to the controller.
            final var window = new WindowFacade<T>(stage, controller);
            controller.setWindow(window);

            // Returns the window.
            return window;
        } else {
            throw new RuntimeException("Controller specified in FXML must extend the controller facade class");
        }
    }

    /**
     * Creates a new popup window.
     *
     * @param url    the url of the fxml resource.
     * @param parent the parent of the popup window.
     * @param title  the title of the window.
     * @return the created window.
     * @throws IOException gets thrown if the resource could not be found.
     */
    public static @NotNull <T extends ControllerFacade> WindowFacade<T> createPopup(
            @NotNull URL url, @NotNull WindowFacade<?> parent, @NotNull String title,
            @NotNull Class<T> controllerClass) throws IOException {
        // Creates the new stage
        final var stage = new Stage();

        // Makes the window an application modal and sets the parent window.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent.stage);

        // Creates and returns the window.
        return create(url, stage, title, controllerClass);
    }
}
