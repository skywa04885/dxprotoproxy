package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createApi.CreateApiWindow;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createApi.DefaultCreateApiCallbacks;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createInstance.CreateInstanceWindow;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createInstance.DefaultCreateInstanceCallbacks;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createInstance.ICreateInstanceCallbacks;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor.EndpointEditor;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor.EndpointEditorFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.commands.Command;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.commands.SelectSwaggerFileCommand;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.commands.SelectConfigFileCommand;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.createApi.CreateApiController;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.tree.IPrimaryTreeContextMenuCallbacks;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.tree.PrimaryTreeViewCellFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.tree.PrimaryTreeContextMenuFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.tree.PrimaryTreeFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor.RequestEditor;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor.RequestEditorFactory;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
                try {
                    final var requestEditorFactory = new RequestEditorFactory();
                    RequestEditor requestEditor = requestEditorFactory.forCreate(httpConfigEndpoint);
                    requestEditor.show();
                } catch (Exception ioException) {
                    ioException.printStackTrace();
                }
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