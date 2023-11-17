package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigInstance;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ConfiguratorController implements Initializable {
    public SimpleObjectProperty<DXHttpConfig> config = new SimpleObjectProperty<>(null, "Configuration",
            new DXHttpConfig());

    @FXML
    public BorderPane borderPane;
    @FXML
    public Label labelForCurrentCommandName;
    @FXML
    public ProgressIndicator busyProgressIndicator;
    @FXML
    public TreeView<IDXTreeItem> endpointsTreeView;
    @FXML
    public TreeTableView<DXConfiguratorPreviewItem> propertyTreeTable;
    @FXML
    public TreeTableColumn<DXConfiguratorPreviewItem, String> propertyTreeTableNameColumn;
    @FXML
    public TreeTableColumn<DXConfiguratorPreviewItem, String> propertyTreeTableValueColumn;

    private final LinkedList<ConfiguratorCommand> commandLinkedList = new LinkedList<>();
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
                ConfiguratorCommand command;

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

    public void addCommand(final ConfiguratorCommand command) {
        synchronized (this.commandLinkedList) {
            commandLinkedList.addLast(command);
        }

        if (commandExecutor.isDone()) {
            commandExecutor = executorService.submit(this::executeCommands);
        }
    }

    @FXML
    public void onImportOpenAPISpecificationMenuItemPressed() {
        addCommand(new ImportOpenAPISpecificationSelectFileCommand(this));
    }

    @FXML
    public void onOpenConfigMenuItemPressed() {
        addCommand(new OpenConfigSelectFileCommand(this));
    }


    private void asd() {
        final DXHttpConfig httpConfig = httpConfig();

        final var callbacks = new DXHttpConfiguratorTreeContextMenuCallbacks() {

            @Override
            public void onAddNewApiToConfig(DXHttpConfig httpConfig) {
                try {
                    final var fxmlLoader = new FXMLLoader(ConfiguratorController.class.getResource("new-api-view.fxml"));
                    final var stage = new Stage();
                    final var controller = new DXNewAPIViewController(stage, httpConfig);
                    fxmlLoader.setController(controller);
                    final var scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
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
                    final var fxmlLoader = new FXMLLoader(ConfiguratorController.class.getResource("new-instance-view.fxml"));
                    final var stage = new Stage();
                    final var controller = new DXNewInstanceController(stage, httpConfigApi);
                    fxmlLoader.setController(controller);
                    final var scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ioException) {
                    System.err.println(ioException.getMessage());
                }
            }

            @Override
            public void onRemoveInstanceFromApi(DXHttpConfigInstance httpConfigInstance) {

            }

            @Override
            public void onAddNewEndpointToApi(DXHttpConfigApi httpConfigApi) {

            }
        };

        final var contextMenuFactory = new DXHttpConfiguratorTreeContextMenuFactory(callbacks);

        final var treeViewCellFactory = new DXConfigTreeViewCellFactory(contextMenuFactory);

        endpointsTreeView.setCellFactory(treeViewCellFactory);

        final var treeFactory = new DXHttpConfiguratorTreeFactory();

        endpointsTreeView.setRoot(treeFactory.createForConfig(httpConfig));

        propertyTreeTableValueColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        propertyTreeTableNameColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        propertyTreeTableValueColumn.setCellValueFactory(v -> v.getValue().getValue().valueProperty());
        propertyTreeTableNameColumn.setCellValueFactory(v -> v.getValue().getValue().keyProperty());

        endpointsTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<IDXTreeItem>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<IDXTreeItem>> observableValue, TreeItem<IDXTreeItem> idxTreeItemTreeItem, TreeItem<IDXTreeItem> item) {
                if (item == null) {
                    propertyTreeTable.setRoot(null);
                    return;
                }

                propertyTreeTable.setRoot(new DXHttpConfiguratorPreviewFactory().create(item.getValue()));
            }
        });
    }

    /**
     * Gets called when the config has changed.
     * @param observableValue The observable config.
     * @param oldConfig The old config.
     * @param newConfig The new config.
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