package com.github.skywa04885.dxprotoproxy.server;

import com.github.skywa04885.dxprotoproxy.server.net.NetService;
import com.github.skywa04885.dxprotoproxy.server.net.NetServiceRegistry;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ServerController implements Initializable {
    public @FXML TableView<NetService> servicesTableView;
    public @FXML TableColumn<NetService, String> servicesTableViewServiceColumn;
    public @FXML TableColumn<NetService, String> servicesTableViewDescriptionColumn;
    public @FXML TableColumn<NetService, String> servicesTableViewStateColumn;
    public @FXML Button activateAllButton;
    public @FXML Button deactivateAllButton;
    public @FXML ProgressIndicator busyProgressIndicator;
    public @FXML Label pendingFuturesLabel;

    private final @NotNull NetServiceRegistry netServiceRegistry;
    private final @NotNull ExecutorService executorService;
    private final @NotNull SimpleIntegerProperty pendingFuturesCounter;
    private @Nullable ServerWindow window;

    /**
     * Constructs a new server controller that uses the given service registry.
     * @param netServiceRegistry the service registry to use.
     */
    public ServerController(@NotNull NetServiceRegistry netServiceRegistry) {
        this.netServiceRegistry = netServiceRegistry;

        executorService = Executors.newSingleThreadExecutor();
        pendingFuturesCounter = new SimpleIntegerProperty(null, "PendingExecutions", 0);
    }

    public void setWindow(@Nullable ServerWindow window) {
        this.window = window;
    }

    private void initializeServicesTableView() {
        final var contextMenuFactory = new ServerServicesTableViewContextMenuFactory(new IServerServicesTableViewContextMenuCallbacks() {
            @Override
            public void activateService(@NotNull NetService netService) {
                runInExecutorService(() -> {
                    try {
                        Thread.sleep(500);
                        netService.activate();
                    } catch (Exception exception) {
                        Platform.runLater(() -> {
                            final var alert = new Alert(Alert.AlertType.ERROR);

                            alert.setTitle("Failed to activate net service");
                            alert.setHeaderText(exception.getClass().getName());
                            alert.setContentText(exception.getMessage());

                            alert.show();
                        });
                    }
                });
            }

            @Override
            public void deactivateService(@NotNull NetService netService) {
                runInExecutorService(() -> {
                    try {
                        Thread.sleep(500);
                        netService.deactivate();
                    } catch (Exception exception) {
                        Platform.runLater(() -> {
                            final var alert = new Alert(Alert.AlertType.ERROR);

                            alert.setTitle("Failed to deactivate net service");
                            alert.setHeaderText(exception.getClass().getName());
                            alert.setContentText(exception.getMessage());

                            alert.show();
                        });
                    }
                });
            }
        });

        servicesTableView.setRowFactory(new Callback<TableView<NetService>, TableRow<NetService>>() {
            @Override
            public TableRow<NetService> call(TableView<NetService> netServiceTableView) {
                final TableRow<NetService> tableRow = new TableRow<>();

                tableRow.setOnContextMenuRequested(event -> {
                    if (tableRow.isEmpty()) {
                        return;
                    }

                    final var contextMenu = contextMenuFactory.create(tableRow.getItem());
                    contextMenu.show(tableRow, event.getScreenX(), event.getScreenY());
                });

                return tableRow;
            }
        });

        // Creates the value factories.
        servicesTableViewServiceColumn.setCellValueFactory(cell -> cell.getValue().getName());
        servicesTableViewDescriptionColumn.setCellValueFactory(cell -> cell.getValue().getDescription());
        servicesTableViewStateColumn.setCellValueFactory(cell ->
                Bindings.createStringBinding(() -> cell.getValue().state().label(),
                        cell.getValue().stateProperty()));

        // Sets the items of the table view.
        servicesTableView.setItems(netServiceRegistry.servicesProperty());
    }

    private void activateAll() {
        // Performs the activateAll.
        try {
            Thread.sleep(500);
            netServiceRegistry.activateAll();
        } catch (Exception exception) {
            Platform.runLater(() -> {
                final var alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Failed to activate all");
                alert.setHeaderText(exception.getClass().getName());
                alert.setContentText(exception.getMessage());

                alert.show();
            });
        }
    }

    private void activateAllButtonActionHandler(ActionEvent actionEvent) {
        runInExecutorService(this::activateAll);
    }

    private void initializeActivateAllButton() {
        activateAllButton.setOnAction(this::activateAllButtonActionHandler);
    }

    private void deactivateAll() {
        // Performs the deactivateAll.
        try {
            Thread.sleep(500);
            netServiceRegistry.deactivateAll();
        } catch (Exception exception) {
            Platform.runLater(() -> {
                final var alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Failed to deactivate all");
                alert.setHeaderText(exception.getClass().getName());
                alert.setContentText(exception.getMessage());

                alert.show();
            });
        }
    }

    private void runInExecutorService(@NotNull Runnable runnable) {
        pendingFuturesCounter.set(pendingFuturesCounter.get() + 1);


        executorService.submit(() -> {
            runnable.run();

            Platform.runLater(() -> pendingFuturesCounter.set(pendingFuturesCounter.get() - 1));
        });
    }

    private void deactivateAllButtonActionHandler(ActionEvent actionEvent) {
        runInExecutorService(this::deactivateAll);
    }

    private void initializeDeactivateAllButton() {
        deactivateAllButton.setOnAction(this::deactivateAllButtonActionHandler);
    }

    private void initializeBusyProgressIndicator() {
        // Binds the visible property.
        busyProgressIndicator
                .visibleProperty()
                .bind(Bindings.createBooleanBinding(() -> pendingFuturesCounter.get() > 0, pendingFuturesCounter));
    }

    private void initializePendingFuturesLabel() {
        // Binds the visible property.
        pendingFuturesLabel
                .visibleProperty()
                .bind(Bindings.createBooleanBinding(() -> pendingFuturesCounter.get() > 0, pendingFuturesCounter));

        // Binds the text property.
        pendingFuturesLabel
                .textProperty()
                .bind(pendingFuturesCounter.asString().concat(" Remaining"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeServicesTableView();
        initializeActivateAllButton();
        initializeDeactivateAllButton();
        initializeBusyProgressIndicator();
        initializePendingFuturesLabel();
    }
}
