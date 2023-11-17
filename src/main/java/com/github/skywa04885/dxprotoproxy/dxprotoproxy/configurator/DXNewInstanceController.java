package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigInstance;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigValidators;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DXNewInstanceController implements Initializable {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField hostTextField;
    @FXML
    public Spinner<Integer> portSpinner;
    @FXML
    public TextField protocolTextField;

    private final Stage stage;
    private final SimpleObjectProperty<DXHttpConfigApi> httpConfigApiProperty;

    public DXNewInstanceController(Stage stage, DXHttpConfigApi httpConfigApi) {
        this.stage = stage;
        this.httpConfigApiProperty = new SimpleObjectProperty<>(null, "httpConfigApi", httpConfigApi);
    }

    public DXHttpConfigApi httpConfigApi() {
        return httpConfigApiProperty.getValue();
    }

    @FXML
    public void onCancelAction(ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    public void onAddAction(ActionEvent actionEvent) {
        // Get all the values.
        final String name = nameTextField.getText();
        final String host = hostTextField.getText();
        final int port = portSpinner.getValue();
        final String protocol = protocolTextField.getText();

        // Validate the name.
        if (!DXHttpConfigValidators.isNameValid(name)) {
            final var alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Naam is niet geldig");
            alert.show();

            return;
        }

        // Validate the host.
        if (!DXHttpConfigValidators.isValidHost(host)) {
            final var alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Host is niet geldig");
            alert.show();

            return;
        }

        // Validate the protocol.
        if (!DXHttpConfigValidators.isProtocolValid(protocol)) {
            final var alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("protocol is niet geldig");
            alert.show();

            return;
        }

        // Gets the api.
        final DXHttpConfigApi httpConfigApi = httpConfigApi();

        // Show an error alert if the instance is already present.
        if (httpConfigApi.instances().containsKey(name)) {
            final var alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Er bestaat al een instantie met de gekozen naam");
            alert.show();

            return;
        }

        // Create the instance.
        final var configInstance = new DXHttpConfigInstance(name, host, port, protocol);

        // Inserts the instance into the config.
        httpConfigApi.instances().put(configInstance.name(), configInstance);

        // Closes the stage.
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
