package com.github.skywa04885.dxprotoproxy.configurator.http.primary;

import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.configurator.http.endpointEditor.EndpointEditorWindowFactory;
import com.github.skywa04885.dxprotoproxy.configurator.http.primary.tree.*;
import com.github.skywa04885.dxprotoproxy.configurator.http.requestEditor.RequestEditorWindowFactory;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.config.ConfigReader;
import com.github.skywa04885.dxprotoproxy.config.ConfigRoot;
import com.github.skywa04885.dxprotoproxy.config.ConfigWriter;
import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.configurator.http.apiEditor.ApiEditorWindowFactory;
import com.github.skywa04885.dxprotoproxy.configurator.http.instanceEditor.InstanceEditorWindowFactory;
import com.github.skywa04885.dxprotoproxy.configurator.http.primary.tree.*;
import com.github.skywa04885.dxprotoproxy.configurator.http.responseEditor.ResponseEditorWindowFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {
    private @NotNull SimpleObjectProperty<File> configFile;

    private @NotNull SimpleObjectProperty<ConfigRoot> config;

    @FXML
    public TreeView<IDXTreeItem> endpointsTreeView;

    public @FXML MenuItem saveConfigAsMenuItem;
    public @FXML MenuItem saveConfigMenuItem;

    private final PrimaryTreeClipboard treeClipboard = new PrimaryTreeClipboard();
    private @Nullable PrimaryWindow window;
    private final FileChooser fileChooser = new FileChooser();

    public PrimaryController() {
        configFile = new SimpleObjectProperty<>(null);
        config = new SimpleObjectProperty<>(null, "Configuration", new ConfigRoot());
    }

    public ConfigRoot configRoot() {
        return config.getValue();
    }

    public void setWindow(@NotNull PrimaryWindow window) {
        this.window = window;
    }

    @FXML
    public void onImportOpenAPISpecificationMenuItemPressed() {
    }

    @FXML
    public void onOpenConfigMenuItemPressed() {
        assert window != null;

        final File file = fileChooser.showOpenDialog(window.stage());
        if (file == null) {
            return;
        }

        try {
            config.set(ConfigReader.read(file));
        } catch (IOException | ParserConfigurationException | SAXException ioException) {

        }

        configFile.set(file);
    }

    private void asd() {
        final var callbacks = new IPrimaryTreeContextMenuCallbacks() {
            /**
             * Creates a new response.
             * @param configRequest the request to which the response will belong.
             */
            @Override
            public void createResponse(DXHttpConfigRequest configRequest) {
                ResponseEditorWindowFactory.create(configRequest);
            }

            @Override
            public void createApi(@NotNull HttpConfigApis httpConfigApis) {
                ApiEditorWindowFactory.create(httpConfigApis);
            }

            @Override
            public void modifyApi(@NotNull DXHttpConfigApi httpConfigApi) {
                ApiEditorWindowFactory.create(httpConfigApi);
            }

            @Override
            public void deleteApi(@NotNull DXHttpConfigApi httpConfigApi) {
                Objects.requireNonNull(httpConfigApi.parent())
                        .children()
                        .remove(httpConfigApi.name());
            }

            @Override
            public void createInstance(@NotNull HttpConfigInstances httpConfigInstances) {
                InstanceEditorWindowFactory.create(httpConfigInstances);
            }

            @Override
            public void modifyInstance(@NotNull DXHttpConfigInstance httpConfigInstance) {
                InstanceEditorWindowFactory.modify(httpConfigInstance);
            }

            @Override
            public void deleteInstance(@NotNull DXHttpConfigInstance configInstance) {
                Objects.requireNonNull(configInstance.parent())
                        .children()
                        .remove(configInstance.name());
            }

            @Override
            public void createEndpoint(@NotNull HttpConfigEndpoints httpConfigEndpoints) {
                EndpointEditorWindowFactory.create(httpConfigEndpoints);
            }

            /**
             * Deletes the given endpoint.
             *
             * @param configEndpoint the endpoint to delete.
             */
            @Override
            public void deleteEndpoint(@NotNull DXHttpConfigEndpoint configEndpoint) {
                Objects.requireNonNull(configEndpoint.parent())
                        .children()
                        .remove(configEndpoint.name());
            }

            /**
             * Modifies the given endpoint.
             *
             * @param configEndpoint the endpoint to modify.
             */
            @Override
            public void modifyEndpoint(@NotNull DXHttpConfigEndpoint configEndpoint) {
                EndpointEditorWindowFactory.modify(configEndpoint);
            }


            @Override
            public void createRequest(@NotNull DXHttpConfigEndpoint httpConfigEndpoint) {
                RequestEditorWindowFactory.create(httpConfigEndpoint);
            }

            /**
             * Opens the window that will modify the given response.
             * @param configResponse the response that needs to be modified.
             */
            @Override
            public void modifyResponse(@NotNull DXHttpConfigResponse configResponse) {
                ResponseEditorWindowFactory.modify(configResponse);
            }

            /**
             * Deletes the given response.
             * @param configResponse the response that needs to be deleted.
             */
            @Override
            public void deleteResponse(@NotNull DXHttpConfigResponse configResponse) {
                configResponse
                        .parent()
                        .children()
                        .remove(configResponse.code());
            }

            /**
             * Deletes the given config request.
             * @param configRequest the request that needs to be deleted.
             */
            @Override
            public void deleteRequest(@NotNull DXHttpConfigRequest configRequest) {
                configRequest
                        .parent()
                        .requests()
                        .remove(configRequest.method());
            }

            /**
             * Opens the modification window for the given config request.
             * @param configRequest the request that needs to be modified.
             */
            @Override
            public void modifyRequest(@NotNull DXHttpConfigRequest configRequest) {
                RequestEditorWindowFactory.modify(configRequest);
            }
        };

        final var contextMenuFactory = new PrimaryTreeContextMenuFactory(callbacks, treeClipboard);

        final var treeViewCellFactory = new PrimaryTreeViewCellFactory(contextMenuFactory);

        endpointsTreeView.setCellFactory(treeViewCellFactory);

        final var treeFactory = new PrimaryTreeFactory();

        endpointsTreeView.setRoot(treeFactory.createForConfigRoot(configRoot()));
    }

    /**
     * Gets called when the config has changed.
     *
     * @param observableValue The observable config.
     * @param oldConfig       The old config.
     * @param newConfig       The new config.
     */
    private void onConfigChangedListener(ObservableValue<? extends ConfigRoot> observableValue, ConfigRoot oldConfig, ConfigRoot newConfig) {
        asd();
    }

    private void writeToConfigFile() {
        try {
            ConfigWriter.write(config.get(), configFile.get());
        } catch (ParserConfigurationException | TransformerException | IOException exception) {
            final var alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Failed to write config");
            alert.setContentText(exception.getMessage());

            alert.show();
        }
    }

    private void saveConfigAsMenuItemActionHandler(@NotNull ActionEvent actionEvent) {
        assert window != null;

        final File file = fileChooser.showSaveDialog(window.stage());
        if (file == null) {
            return;
        }

        configFile.set(file);

        writeToConfigFile();
    }

    private void initializeFileChooser() {
        final var fileChooserExtensionFilter = new FileChooser.ExtensionFilter("Config file (*.xml)",
                "*.xml");

        fileChooser
                .setSelectedExtensionFilter(fileChooserExtensionFilter);
    }

    private void initializeSaveConfigAsMenuItem() {
        saveConfigAsMenuItem
                .setOnAction(this::saveConfigAsMenuItemActionHandler);
    }

    private void saveConfigActionHandler(@NotNull ActionEvent actionEvent) {
        // If the config file has not been set yet, call the save as action handler.
        if (configFile.get() == null) {
            saveConfigAsMenuItemActionHandler(actionEvent);
            return;
        }

        // Writes to the config file.
        writeToConfigFile();
    }

    private void initializeSaveConfigMenuItem() {
        saveConfigMenuItem
                .setOnAction(this::saveConfigActionHandler);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        config.addListener(this::onConfigChangedListener);
        asd();

        initializeFileChooser();
        initializeSaveConfigAsMenuItem();
        initializeSaveConfigMenuItem();
    }
}