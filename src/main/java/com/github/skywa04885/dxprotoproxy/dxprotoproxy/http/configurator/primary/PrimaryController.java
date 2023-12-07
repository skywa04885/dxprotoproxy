package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.apiEditor.ApiEditorWindowFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor.EndpointEditorWindowFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.instanceEditor.InstanceEditorWindowFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands.Command;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands.SelectSwaggerFileCommand;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands.SelectConfigFileCommand;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree.*;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor.RequestEditorWindowFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor.ResponseEditorWindowFactory;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class PrimaryController implements Initializable {
    public SimpleObjectProperty<DXHttpConfig> config = new SimpleObjectProperty<>(null, "Configuration", new DXHttpConfig());

    @FXML
    public BorderPane borderPane;
    @FXML
    public Label labelForCurrentCommandName;
    @FXML
    public ProgressIndicator busyProgressIndicator;
    @FXML
    public TreeView<IDXTreeItem> endpointsTreeView;

    private final LinkedList<Command> commandLinkedList = new LinkedList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private Future<Void> commandExecutor = CompletableFuture.completedFuture(null);
    private final PrimaryTreeClipboard treeClipboard = new PrimaryTreeClipboard();

    public DXHttpConfig httpConfig() {
        return config.getValue();
    }

    private Void executeCommands() {
        try {
            Platform.runLater(() -> {
                busyProgressIndicator.setVisible(true);
            });

            while (true) {
                Command command;

                synchronized (this.commandLinkedList) {
                    if (this.commandLinkedList.isEmpty()) break;
                    command = this.commandLinkedList.removeFirst();
                }

                Platform.runLater(() -> labelForCurrentCommandName.setText(command.name));

                command.execute();
            }

            Platform.runLater(() -> {
                labelForCurrentCommandName.setText("");
                busyProgressIndicator.setVisible(false);
            });
        } catch (final InterruptedException ignore) {

        }

        return null;
    }

    public void addCommand(final Command command) {
        synchronized (this.commandLinkedList) {
            commandLinkedList.addLast(command);
        }

        if (commandExecutor.isDone()) {
            commandExecutor = executorService.submit(this::executeCommands);
        }
    }

    @FXML
    public void onImportOpenAPISpecificationMenuItemPressed() {
        addCommand(new SelectSwaggerFileCommand(this));
    }

    @FXML
    public void onOpenConfigMenuItemPressed() {
        addCommand(new SelectConfigFileCommand(this));
    }

    private void asd() {
        final DXHttpConfig httpConfig = httpConfig();

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
            public void createApi(@NotNull DXHttpConfig httpConfig) {
                ApiEditorWindowFactory.create(httpConfig);
            }

            @Override
            public void modifyApi(@NotNull DXHttpConfigApi httpConfigApi) {
                ApiEditorWindowFactory.create(httpConfigApi);
            }

            @Override
            public void deleteApi(@NotNull DXHttpConfigApi httpConfigApi) {
                final DXHttpConfig httpConfig = httpConfigApi.parent();
                httpConfig.httpApis().remove(httpConfigApi.name());
            }

            @Override
            public void createInstance(@NotNull DXHttpConfigApi httpConfigApi) {
                InstanceEditorWindowFactory.create(httpConfigApi);
            }

            @Override
            public void modifyInstance(@NotNull DXHttpConfigInstance httpConfigInstance) {
                InstanceEditorWindowFactory.modify(httpConfigInstance);
            }

            @Override
            public void deleteInstance(@NotNull DXHttpConfigInstance configInstance) {
                configInstance
                        .parent()
                        .instances()
                        .remove(configInstance.name());
            }

            @Override
            public void createEndpoint(@NotNull DXHttpConfigApi httpConfigApi) {
                EndpointEditorWindowFactory.create(httpConfigApi);
            }

            /**
             * Deletes the given endpoint.
             *
             * @param configEndpoint the endpoint to delete.
             */
            @Override
            public void deleteEndpoint(@NotNull DXHttpConfigEndpoint configEndpoint) {
                configEndpoint
                        .parent()
                        .endpoints()
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

        endpointsTreeView.setRoot(treeFactory.createForConfig(httpConfig));
    }

    /**
     * Gets called when the config has changed.
     *
     * @param observableValue The observable config.
     * @param oldConfig       The old config.
     * @param newConfig       The new config.
     */
    private void onConfigChangedListener(ObservableValue<? extends DXHttpConfig> observableValue, DXHttpConfig oldConfig, DXHttpConfig newConfig) {
        asd();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        config.addListener(this::onConfigChangedListener);
        asd();
    }
}