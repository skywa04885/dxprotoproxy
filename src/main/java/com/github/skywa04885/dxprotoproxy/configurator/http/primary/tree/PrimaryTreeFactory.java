package com.github.skywa04885.dxprotoproxy.configurator.http.primary.tree;

import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.config.ConfigRoot;
import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.http.DXHttpRequestMethod;
import javafx.collections.MapChangeListener;
import javafx.scene.control.TreeItem;

public class PrimaryTreeFactory {
    private final boolean expand;

    public PrimaryTreeFactory(boolean expand) {
        this.expand = expand;
    }

    public PrimaryTreeFactory() {
        this(true);
    }

    public TreeItem<IDXTreeItem> createForHttpConfigResponse(DXHttpConfigResponse configResponse) {
        final var configResponseTreeItem = new TreeItem<IDXTreeItem>(configResponse);

        if (expand) configResponseTreeItem.setExpanded(true);

        return configResponseTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigInstance(DXHttpConfigInstance dxHttpConfigInstance) {
        final var configInstanceTreeItem = new TreeItem<IDXTreeItem>(dxHttpConfigInstance);

        if (expand) configInstanceTreeItem.setExpanded(true);

        return configInstanceTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigRequest(DXHttpConfigRequest configRequest) {
        final var requestTreeItem = new TreeItem<IDXTreeItem>(configRequest);

        if (expand) requestTreeItem.setExpanded(true);

        requestTreeItem.getChildren().addAll(configRequest.responses().children().values().stream()
                .map(this::createForHttpConfigResponse).toList());

        configRequest.responses().childrenProperty().addListener((MapChangeListener<Integer, DXHttpConfigResponse>) change -> {
            if (change.wasAdded())
                requestTreeItem.getChildren().add(createForHttpConfigResponse(change.getValueAdded()));
            if (change.wasRemoved())
                requestTreeItem.getChildren().removeIf(v -> v.getValue() == change.getValueRemoved());
        });

        return requestTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigEndpoint(DXHttpConfigEndpoint configEndpoint) {
        final var configEndpointTreeItem = new TreeItem<IDXTreeItem>(configEndpoint);

        if (expand) configEndpointTreeItem.setExpanded(true);

        configEndpointTreeItem.getChildren().addAll(configEndpoint.requests().values().stream().map(this::createForHttpConfigRequest).toList());

        configEndpoint.requestsProperty().addListener((MapChangeListener<DXHttpRequestMethod, DXHttpConfigRequest>) change -> {
            if (change.wasAdded())
                configEndpointTreeItem.getChildren().add(createForHttpConfigRequest(change.getValueAdded()));
            if (change.wasRemoved())
                configEndpointTreeItem.getChildren().removeIf(v -> v.getValue() == change.getValueRemoved());
        });

        return configEndpointTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigEndpoints(HttpConfigEndpoints configEndpoints) {
        final var endpointsTreeItem = new TreeItem<IDXTreeItem>(configEndpoints);

        if (expand) endpointsTreeItem.setExpanded(true);

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
                endpointsTreeItem.getChildren().removeIf(v -> v.getValue() == change.getValueRemoved());
        });

        return endpointsTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigInstances(HttpConfigInstances configInstances) {
        final var instancesTreeItem = new TreeItem<IDXTreeItem>(configInstances);

        if (expand) instancesTreeItem.setExpanded(true);

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
                        .removeIf(v -> v.getValue() == change.getValueRemoved());
            }
        });

        return instancesTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigApi(DXHttpConfigApi httpConfigApi) {
        final var apiTreeItem = new TreeItem<IDXTreeItem>(httpConfigApi);

        if (expand) apiTreeItem.setExpanded(true);

        apiTreeItem.getChildren().add(createForHttpConfigEndpoints(httpConfigApi.endpoints()));
        apiTreeItem.getChildren().add(createForHttpConfigInstances(httpConfigApi.instances()));

        return apiTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigApis(HttpConfigApis httpConfigApis) {
        final var httpConfigApisTreeItem = new TreeItem<IDXTreeItem>(httpConfigApis);

        if (expand) httpConfigApisTreeItem.setExpanded(true);

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
                                .removeIf(v -> v.getValue() == change.getValueRemoved());
                    }
                });

        return httpConfigApisTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfig(DXHttpConfig httpConfig) {
        final var httpConfigTreeItem = new TreeItem<IDXTreeItem>(httpConfig);

        if (expand) httpConfigTreeItem.setExpanded(true);

        httpConfigTreeItem.getChildren().add(createForHttpConfigApis(httpConfig.httpConfigApis()));

        return httpConfigTreeItem;
    }

    public TreeItem<IDXTreeItem> createForConfigRoot(ConfigRoot configRoot) {
        final var configRootTreeItem = new TreeItem<IDXTreeItem>(configRoot);

        if (expand) {
            configRootTreeItem.setExpanded(true);
        }

        configRootTreeItem.getChildren().add(createForHttpConfig(configRoot.httpConfig()));

        return configRootTreeItem;
    }
}
