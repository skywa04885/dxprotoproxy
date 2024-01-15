package com.github.skywa04885.dxprotoproxy.configurator.http.primary.tree;

import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.config.ConfigRoot;
import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.config.modbus.*;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientsConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTConfig;
import com.github.skywa04885.dxprotoproxy.configurator.ConfiguratorImageCache;
import com.github.skywa04885.dxprotoproxy.http.DXHttpRequestMethod;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import kotlin.io.encoding.StreamEncodingKt;
import org.jetbrains.annotations.NotNull;

public class PrimaryTreeFactory {
    private final boolean expand;

    public PrimaryTreeFactory(boolean expand) {
        this.expand = expand;
    }

    public PrimaryTreeFactory() {
        this(true);
    }

    public TreeItem<IDXTreeItem> createForHttpConfigResponse(DXHttpConfigResponse configResponse) {

        return getIdxTreeItemTreeItem(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/reply.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> configResponse.Code.get() + " - "
                        + configResponse.Fields.format().name(), configResponse.Code, configResponse.Fields.formatProperty());
            }

            @Override
            public Object value() {
                return configResponse;
            }
        }));
    }

    public TreeItem<IDXTreeItem> createForHttpConfigInstance(DXHttpConfigInstance dxHttpConfigInstance) {

        return getIdxTreeItemTreeItem(new TreeItem<IDXTreeItem>(new IDXTreeItem() {

            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/dns_FILL0_wght400_GRAD0_opsz24.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return dxHttpConfigInstance.nameProperty();
            }

            @Override
            public Object value() {
                return dxHttpConfigInstance;
            }
        }));
    }

    public TreeItem<IDXTreeItem> createForHttpConfigRequest(DXHttpConfigRequest configRequest) {
        final var requestTreeItem = getIdxTreeItemTreeItem(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/send.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> configRequest.method().label() + " "
                                + configRequest.uri().path().stringOfSegments(), configRequest.methodProperty(),
                        configRequest.uri().pathProperty());
            }

            @Override
            public Object value() {
                return configRequest;
            }
        }));

        requestTreeItem.getChildren().addAll(configRequest.responses().children().values().stream()
                .map(this::createForHttpConfigResponse).toList());

        configRequest.responses().childrenProperty().addListener((MapChangeListener<Integer, DXHttpConfigResponse>) change -> {
            if (change.wasAdded())
                requestTreeItem.getChildren().add(createForHttpConfigResponse(change.getValueAdded()));
            if (change.wasRemoved())
                requestTreeItem.getChildren().removeIf(v -> v.getValue().value() == change.getValueRemoved());
        });

        return requestTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigEndpoint(DXHttpConfigEndpoint configEndpoint) {
        final var configEndpointTreeItem = getIdxTreeItemTreeItem(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/globe_FILL0_wght400_GRAD0_opsz24.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(configEndpoint::name, configEndpoint.nameProperty());
            }

            @Override
            public Object value() {
                return configEndpoint;
            }
        }));

        configEndpointTreeItem.getChildren().addAll(configEndpoint.requests().values().stream().map(this::createForHttpConfigRequest).toList());

        configEndpoint.requestsProperty().addListener((MapChangeListener<DXHttpRequestMethod, DXHttpConfigRequest>) change -> {
            if (change.wasAdded())
                configEndpointTreeItem.getChildren().add(createForHttpConfigRequest(change.getValueAdded()));
            if (change.wasRemoved())
                configEndpointTreeItem.getChildren().removeIf(v -> v.getValue().value() == change.getValueRemoved());
        });

        return configEndpointTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigEndpoints(HttpConfigEndpoints configEndpoints) {
        final var endpointsTreeItem = getIdxTreeItemTreeItem(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return null;
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Endpoints");
            }

            @Override
            public Object value() {
                return configEndpoints;
            }
        }));

        endpointsTreeItem.getChildren().addAll(configEndpoints
                .children()
                .values()
                .stream()
                .map(this::createForHttpConfigEndpoint)
                .toList());

        configEndpoints.childrenProperty().addListener((MapChangeListener<String, DXHttpConfigEndpoint>) change -> {
            if (change.wasAdded())
                endpointsTreeItem.getChildren().add(createForHttpConfigEndpoint(change.getValueAdded()));
            if (change.wasRemoved())
                endpointsTreeItem.getChildren().removeIf(v -> v.getValue().value() == change.getValueRemoved());
        });

        return endpointsTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigInstances(HttpConfigInstances configInstances) {
        final var instancesTreeItem = getIdxTreeItemTreeItem(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return null;
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Instances");
            }

            @Override
            public Object value() {
                return configInstances;
            }
        }));

        instancesTreeItem
                .getChildren()
                .addAll(configInstances
                        .children()
                        .values()
                        .stream()
                        .map(this::createForHttpConfigInstance)
                        .toList());

        configInstances.childrenProperty().addListener((MapChangeListener<String, DXHttpConfigInstance>) change -> {
            if (change.wasAdded()) {
                instancesTreeItem
                        .getChildren()
                        .add(createForHttpConfigInstance(change.getValueAdded()));
            }

            if (change.wasRemoved()) {
                instancesTreeItem
                        .getChildren()
                        .removeIf(v -> v.getValue().value() == change.getValueRemoved());
            }
        });

        return instancesTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigApi(DXHttpConfigApi httpConfigApi) {
        final var apiTreeItem = getIdxTreeItemTreeItem(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/network_node_FILL0_wght400_GRAD0_opsz24.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> httpConfigApi.name() + " - " + httpConfigApi.httpVersion(),
                        httpConfigApi.nameProperty(), httpConfigApi.httpVersionProperty());
            }

            @Override
            public Object value() {
                return httpConfigApi;
            }
        }));

        apiTreeItem.getChildren().add(createForHttpConfigEndpoints(httpConfigApi.endpoints()));
        apiTreeItem.getChildren().add(createForHttpConfigInstances(httpConfigApi.instances()));

        return apiTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigApis(HttpConfigApis httpConfigApis) {
        final var httpConfigApisTreeItem = getIdxTreeItemTreeItem(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return null;
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Apis");
            }

            @Override
            public Object value() {
                return httpConfigApis;
            }
        }));

        httpConfigApisTreeItem.getChildren().addAll(httpConfigApis.children().values().stream().map(this::createForHttpConfigApi).toList());

        httpConfigApis
                .childrenProperty()
                .addListener((MapChangeListener<String, DXHttpConfigApi>) change -> {
                    if (change.wasAdded()) {
                        httpConfigApisTreeItem
                                .getChildren()
                                .add(createForHttpConfigApi(change.getValueAdded()));
                    }

                    if (change.wasRemoved()) {
                        httpConfigApisTreeItem
                                .getChildren()
                                .removeIf(v -> v.getValue().value() == change.getValueRemoved());
                    }
                });

        return httpConfigApisTreeItem;
    }

    private TreeItem<IDXTreeItem> getIdxTreeItemTreeItem(TreeItem<IDXTreeItem> httpConfigApis) {

        if (expand) httpConfigApis.setExpanded(true);

        return httpConfigApis;
    }

    public TreeItem<IDXTreeItem> createForHttpConfig(DXHttpConfig httpConfig) {
        final var httpConfigTreeItem = getIdxTreeItemTreeItem(new TreeItem<IDXTreeItem>(new IDXTreeItem() {

            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/manufacturing_FILL0_wght400_GRAD0_opsz24.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return new SimpleStringProperty(null, null, "HTTP");
            }

            @Override
            public Object value() {
                return httpConfig;
            }
        }));

        httpConfigTreeItem.getChildren().add(createForHttpConfigApis(httpConfig.httpConfigApis()));

        return httpConfigTreeItem;
    }


    /**
     * Constructs the mqtt clients config tree item for the given mqtt client config.
     *
     * @param mqttClientConfig the mqtt client config to use.
     * @return the tree item associated with it.
     */
    public @NotNull TreeItem<IDXTreeItem> createForMqttClientConfig(@NotNull MQTTClientConfig mqttClientConfig) {
        final var mqttClientConfigTreeItem = getIdxTreeItemTreeItem1(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/mqtt_client.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> mqttClientConfig.clientHostname() + ":" + mqttClientConfig.clientPort() + " to " + mqttClientConfig.brokerHostname()
                                + ":" + mqttClientConfig.brokerPort(), mqttClientConfig.clientHostnameProperty(), mqttClientConfig.clientPortProperty(), mqttClientConfig.brokerHostnameProperty(),
                        mqttClientConfig.brokerPortProperty());
            }

            @Override
            public Object value() {
                return mqttClientConfig;
            }
        }));

        return mqttClientConfigTreeItem;
    }

    /**
     * Constructs the mqtt clients config tree item for the given mqtt clients config.
     *
     * @param mqttClientsConfig the mqtt clients config to use.
     * @return the tree item associated with it.
     */
    public @NotNull TreeItem<IDXTreeItem> createForMqttClientsConfig(@NotNull MQTTClientsConfig mqttClientsConfig) {
        // Creates the new tree item.
        final var mqttClientsConfigTreeItem = getIdxTreeItemTreeItem1(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/mqtt_clients.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Clients");
            }

            @Override
            public Object value() {
                return mqttClientsConfig;
            }
        }));

        // Creates all the tree items for the children.
        mqttClientsConfigTreeItem.getChildren().addAll(mqttClientsConfig.children().stream().map(
                this::createForMqttClientConfig).toList());

        // Listens for changes in the children.
        mqttClientsConfig.childrenProperty().addListener(new ListChangeListener<MQTTClientConfig>() {
            @Override
            public void onChanged(Change<? extends MQTTClientConfig> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        mqttClientsConfigTreeItem.getChildren().add(createForMqttClientConfig(
                                change.getAddedSubList().get(0)));
                    }

                    if (change.wasRemoved()) {
                        mqttClientsConfigTreeItem.getChildren()
                                .removeIf(item -> item.getValue().value() == change.getRemoved().get(0));
                    }
                }
            }
        });

        // Returns the tree item.
        return mqttClientsConfigTreeItem;
    }

    private TreeItem<IDXTreeItem> getIdxTreeItemTreeItem1(TreeItem<IDXTreeItem> mqttClientsConfig) {

        // Expands it if needed.
        if (expand) {
            mqttClientsConfig.setExpanded(true);
        }
        return mqttClientsConfig;
    }

    /**
     * Constructs the mqtt tree item for the given mqtt config.
     *
     * @param mqttConfig the mqtt config.
     * @return the tree item.
     */
    public @NotNull TreeItem<IDXTreeItem> createForMqttConfig(@NotNull MQTTConfig mqttConfig) {
        final var mqttConfigTreeItem = getIdxTreeItemTreeItem1(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/mqtt.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "MQTT");
            }

            @Override
            public Object value() {
                return mqttConfig;
            }
        }));

        mqttConfigTreeItem.getChildren().add(createForMqttClientsConfig(mqttConfig.mqttClientsConfig()));

        return mqttConfigTreeItem;
    }

    /**
     * Create a new tree item for the given modbus slave config.
     * @param config the config.
     * @return the tree item.
     */
    public @NotNull TreeItem<IDXTreeItem> create(@NotNull ModbusSlaveConfig config) {
        return new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return null;
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Slave at " + config.controllerAddress() + ":"
                                + config.controllerPort(), config.controllerAddressProperty(), config.controllerPortProperty());
            }

            @Override
            public Object value() {
                return config;
            }
        });
    }


    /**
     * Create a new tree item for the given modbus master config.
     * @param config the config.
     * @return the tree item.
     */
    public @NotNull TreeItem<IDXTreeItem> create(@NotNull ModbusMasterConfig config) {
        return new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return null;
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Master at " + config.serverAddress() + ":"
                        + config.serverPort(), config.serverAddressProperty(), config.serverPortProperty());
            }

            @Override
            public Object value() {
                return config;
            }
        });
    }

    /**
     * Create a new tree item for the given modbus slaves config.
     * @param config the config.
     * @return the tree item.
     */
    public @NotNull TreeItem<IDXTreeItem> create(@NotNull ModbusSlavesConfig config) {
        // Create the tree item.
        final var treeItem = new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return null;
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Modbus slaves");
            }

            @Override
            public Object value() {
                return config;
            }
        });
        if (expand) treeItem.setExpanded(true);

        // Create all the children.
        treeItem.getChildren().addAll(config.children().stream().map(this::create).toList());

        // Create the change listener for the children property.
        final ListChangeListener<ModbusSlaveConfig> listener = change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    treeItem.getChildren().add(create(change.getAddedSubList().get(0)));
                }

                if (change.wasRemoved()) {
                    treeItem.getChildren().removeIf(treeItem1 -> change.getRemoved().get(0) == treeItem1.getValue().value());
                }
            }
        };

        // Adds the listener for the children change.
        config.childrenProperty().addListener(listener);

        return treeItem;
    }

    /**
     * Create a new tree item for the given modbus masters config.
     * @param config the config.
     * @return the tree item.
     */
    public @NotNull TreeItem<IDXTreeItem> create(@NotNull ModbusMastersConfig config) {
        // Create the tree item.
        final var treeItem = new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return null;
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Modbus masters");
            }

            @Override
            public Object value() {
                return config;
            }
        });

        if (expand) treeItem.setExpanded(true);

        // Create all the children.
        treeItem.getChildren().addAll(config.children().stream().map(this::create).toList());

        // Create the change listener for the children property.
        final ListChangeListener<ModbusMasterConfig> listener = change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    treeItem.getChildren().add(create(change.getAddedSubList().get(0)));
                }

                if (change.wasRemoved()) {
                    treeItem.getChildren().removeIf(treeItem1 -> change.getRemoved().get(0) == treeItem1.getValue().value());
                }
            }
        };

        // Adds the listener for the children change.
        config.childrenProperty().addListener(listener);

        return treeItem;
    }

    public @NotNull TreeItem<IDXTreeItem> create(@NotNull ModbusConfig config) {
        final var treeItem = new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return null;
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Modbus");
            }

            @Override
            public Object value() {
                return config;
            }
        });
        if (expand) treeItem.setExpanded(true);

        treeItem.getChildren().add(create(config.slavesConfig()));
        treeItem.getChildren().add(create(config.mastersConfig()));

        return treeItem;
    }

    public TreeItem<IDXTreeItem> createForConfigRoot(ConfigRoot configRoot) {
        final var configRootTreeItem = getIdxTreeItemTreeItem1(new TreeItem<IDXTreeItem>(new IDXTreeItem() {
            @Override
            public Node treeItemGraphic() {
                return new ImageView(ConfiguratorImageCache.instance().read("icons/settings.png"));
            }

            @Override
            public ObservableValue<String> treeItemText() {
                return Bindings.createStringBinding(() -> "Config");
            }

            @Override
            public Object value() {
                return configRoot;
            }
        }));

        configRootTreeItem.getChildren().add(createForHttpConfig(configRoot.httpConfig()));
        configRootTreeItem.getChildren().add(createForMqttConfig(configRoot.mqttConfig()));
        configRootTreeItem.getChildren().add(create(configRoot.modbusConfig()));

        return configRootTreeItem;
    }
}
