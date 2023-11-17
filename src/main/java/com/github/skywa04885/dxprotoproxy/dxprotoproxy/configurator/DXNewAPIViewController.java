package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigValidators;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DXNewAPIViewController implements Initializable {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField httpVersionTextField;
    @FXML
    public Button addButton;
    @FXML
    public Button cancelButton;

    private final Stage stage;
    private final SimpleObjectProperty<DXHttpConfig> httpConfig;

    public DXNewAPIViewController(Stage stage, DXHttpConfig httpConfig) {
        this.stage = stage;
        this.httpConfig = new SimpleObjectProperty<>(null, "httpConfig", httpConfig);
    }

    /**
     * Gets the http config property.
     * @return the http config property.
     */
    public SimpleObjectProperty<DXHttpConfig> httpConfigProperty() {
        return httpConfig;
    }

    /**
     * Gets called once the add button has been pressed.
     * @param actionEvent the event.
     */
    private void onAddButtonAction(ActionEvent actionEvent) {
        final String name = nameTextField.getText();
        final String httpVersion = httpVersionTextField.getText();

        // Validate the name.
        if (!DXHttpConfigValidators.isNameValid(name)) {
            final Alert alert = new Alert(Alert.AlertType.ERROR, "De naam van de API is niet geldig");

            alert.setTitle("Ongeldige naam");
            alert.show();

            return;
        }

        // Validate the http version.
        if (!DXHttpConfigValidators.isHttpVersionValid(httpVersion)) {
            final Alert alert = new Alert(Alert.AlertType.ERROR, "De ingevulde http versie is niet geldig.");

            alert.setTitle("Ongeldige http versie");
            alert.show();

            return;
        }

        // Gets the config.
        final DXHttpConfig httpConfig = this.httpConfig.getValue();

        // Makes sure that the name is not in use yet.
        if (httpConfig.httpApis().containsKey(name)) {
            final Alert alert = new Alert(Alert.AlertType.ERROR, "De gekozen naam is al in gebruik.");

            alert.setTitle("Ongeldige naam");
            alert.show();

            return;
        }

        // Creates the API.
        final var configApi = new DXHttpConfigApi(name, httpVersion, httpConfig);

        // Inserts the API in the config.
        httpConfig.httpApis().put(configApi.name(), configApi);

        // Closes the stage.
        stage.close();
    }

    /**
     * Gets called when the close button has been pressed.
     * @param actionEvent the event.
     */
    private void onCancelButtonAction(ActionEvent actionEvent) {
        // Closes the stage.
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets the action event handlers for the buttons.
        addButton.setOnAction(this::onAddButtonAction);
        cancelButton.setOnAction(this::onCancelButtonAction);
    }
}
