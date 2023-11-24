package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createInstance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

/**
 * The controller of the window that creates new instances.
 */
public class CreateInstanceController {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField hostTextField;
    @FXML
    public Spinner<Integer> portSpinner;
    @FXML
    public TextField protocolTextField;

    private final CreateInstanceWindow window;

    /**
     * Creates a new instance of the controller to create instances.
     * @param window the window.
     */
    public CreateInstanceController(CreateInstanceWindow window) {
        this.window = window;
    }

    /**
     * Gets called when the user wants to cancel the instance creation.
     * @param actionEvent the event triggering the cancellation.
     */
    @FXML
    public void cancel(ActionEvent actionEvent) {
        this.window.close();
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
     * Gets called when the user wants to submit the instance.
     * @param actionEvent the event triggering the submission.
     */
    @FXML
    public void submit(ActionEvent actionEvent) {
        // Get all the values.
        final String name = nameTextField.getText();
        final String host = hostTextField.getText();
        final int port = portSpinner.getValue();
        final String protocol = protocolTextField.getText();

        // Calls the validation callback.
        final String error = window.getCallbacks().validate(name, host, port, protocol);
        if (error != null) {
            showValidationErrorAlert(error);
            return;
        }

        // Calls the submit callback.
        window.getCallbacks().submit(name, host, port, protocol);

        // Closes the stage.
        window.close();
    }
}
