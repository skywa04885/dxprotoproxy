package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createApi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateApiController implements Initializable {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField httpVersionTextField;
    @FXML
    public Button addButton;
    @FXML
    public Button cancelButton;

    private final CreateApiWindow window;

    /**
     * Creates a new api controller.
     * @param window the window.
     */
    public CreateApiController(CreateApiWindow window) {
        this.window = window;
    }

    /**
     * Shows the validation error alert with the given error.
     * @param error the error message.
     */
    private void showValidationErrorAlert(String error) {
        final var alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Validation failure");
        alert.setHeaderText("Validation failure");
        alert.setContentText(error);

        alert.show();
    }

    /**
     * Gets called when the user wants to submit.
     * @param actionEvent the event triggering the submission.
     */
    private void submit(ActionEvent actionEvent) {
        // Gets all the values.
        final String name = nameTextField.getText();
        final String httpVersion = httpVersionTextField.getText();

        // Calls the validation callback.
        final String error = window.getCallbacks().validate(name, httpVersion);
        if (error != null) {
            showValidationErrorAlert(error);
            return;
        }

        // Calls the submit callback.
        window.getCallbacks().submit(name, httpVersion);

        // Closes the stage.
        window.close();

        // Closes the stage.
        window.close();
    }

    /**
     * Gets called when the user wants to cancel the creation of the api.
     * @param actionEvent the event that triggered the cancellation.
     */
    private void cancel(ActionEvent actionEvent) {
        this.window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets the action event handlers for the buttons.
        addButton.setOnAction(this::submit);
        cancelButton.setOnAction(this::cancel);
    }
}
