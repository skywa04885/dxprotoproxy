package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.createApi.CreateApiWindow;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.createApi.DefaultCreateApiCallbacks;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.createInstance.CreateInstanceWindow;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.createInstance.DefaultCreateInstanceCallbacks;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor.EndpointEditor;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor.EndpointEditorFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands.Command;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands.SelectSwaggerFileCommand;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.commands.SelectConfigFileCommand;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree.IPrimaryTreeContextMenuCallbacks;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree.PrimaryTreeViewCellFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree.PrimaryTreeContextMenuFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree.PrimaryTreeFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor.RequestEditorControllerFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor.RequestEditorWindow;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor.ResponseEditorControllerFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor.ResponseEditorWindow;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
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
                ResponseEditorWindow
                        .newBuilder()
                        .withController(ResponseEditorControllerFactory.create(configRequest))
                        .build();
            }

            @Override
            public void onAddNewApiToConfig(DXHttpConfig httpConfig) {
                try {
                    CreateApiWindow createApiWindow = new CreateApiWindow(new DefaultCreateApiCallbacks(httpConfig));
                    createApiWindow.show();
                } catch (IOException ioException) {
                    System.err.println(ioException.getMessage());
                }
            }

            @Override
            public void onRemoveApiFromConfig(DXHttpConfigApi httpConfigApi) {
                final DXHttpConfig httpConfig = httpConfigApi.parent();
                httpConfig.httpApis().remove(httpConfigApi.name());
            }

            @Override
            public void onAddNewInstanceToApi(DXHttpConfigApi httpConfigApi) {
                try {
                    CreateInstanceWindow createInstanceWindow = new CreateInstanceWindow(new DefaultCreateInstanceCallbacks(httpConfigApi));
                    createInstanceWindow.show();
                } catch (IOException ioException) {
                    System.err.println(ioException.getMessage());
                }
            }

            @Override
            public void onRemoveInstanceFromApi(DXHttpConfigApi httpConfigApi, DXHttpConfigInstance httpConfigInstance) {
                httpConfigApi.instances().remove(httpConfigInstance.name());
            }

            @Override
            public void onAddNewEndpointToApi(DXHttpConfigApi httpConfigApi) {
                try {
                    final var endpointEditorFactory = new EndpointEditorFactory();
                    EndpointEditor endpointEditor = endpointEditorFactory.forCreate(httpConfigApi);
                    endpointEditor.show();
                } catch (Exception ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void onAddRequestToEndpoint(DXHttpConfigEndpoint httpConfigEndpoint) {
                RequestEditorWindow.
                        newBuilder()
                        .withController(RequestEditorControllerFactory.create(httpConfigEndpoint))
                        .build();
            }

            /**
             * Opens the window that will modify the given response.
             * @param configResponse the response that needs to be modified.
             */
            @Override
            public void modifyResponse(DXHttpConfigResponse configResponse) {
                ResponseEditorWindow
                        .newBuilder()
                        .withController(ResponseEditorControllerFactory.update(configResponse))
                        .build();
            }

            /**
             * Deletes the given response.
             * @param configResponse the response that needs to be deleted.
             */
            @Override
            public void deleteResponse(DXHttpConfigResponse configResponse) {
                configResponse
                        .parent()
                        .children()
                        .remove(configResponse.code());
            }
        };

        final var contextMenuFactory = new PrimaryTreeContextMenuFactory(callbacks);

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