package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EndpointEditorController implements Initializable {
    // Text fields.
    @FXML
    public TextField nameTextField;

    // Buttons.
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;

    // Instance methods.
    private final EndpointEditor endpointEditor;

    /**
     * Creates a new endpoint editor controller.
     * @param endpointEditor the endpoint editor instance.
     */
    public EndpointEditorController(EndpointEditor endpointEditor) {
        this.endpointEditor = endpointEditor;
    }

    /**
     * Shows the validation error alert with the given message.
     * @param error error.
     */
    private void showValidationErrorAlert(String error) {
        final var alert = new Alert(Alert.AlertType.ERROR);

        final String title = "Invalid endpoint values";

        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(error);

        alert.show();
    }

    /**
     * Saves the endpoint.
     */
    private void save() {
        // Gets the values.
        final String name = nameTextField.getText();

        // Validates the values and shows the error if they're not valid.
        String error = endpointEditor.validationCallback().validate(name);
        if (error != null) {
            showValidationErrorAlert(error);
            return;
        }

        // Calls the save callback.
        endpointEditor.saveCallback().save(name);

        // Closes the endpoint editor.
        endpointEditor.close();
    }

    /**
     * Gets called on the save button action.
     * @param actionEvent the action event.
     */
    private void onSaveButtonAction(ActionEvent actionEvent) {
        save();
    }

    /**
     * Cancels the editing of the endpoint.
     */
    private void cancel() {
        endpointEditor.close();
    }

    /**
     * Gets called on the cancel button action.
     * @param actionEvent the action event.
     */
    private void onCancelButtonAction(ActionEvent actionEvent) {
        cancel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets the button action event handlers.
        saveButton.setOnAction(this::onSaveButtonAction);
        cancelButton.setOnAction(this::onCancelButtonAction);
    }
}
