package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.tree;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.*;
import javafx.collections.MapChangeListener;
import javafx.scene.control.TreeItem;

import java.util.Map;

public class PrimaryTreeFactory {
    private final boolean expand;

    public PrimaryTreeFactory(boolean expand) {
        this.expand = expand;
    }

    public PrimaryTreeFactory() {
        this(true);
    }

    public TreeItem<IDXTreeItem> createForHttpConfigInstance(DXHttpConfigInstance dxHttpConfigInstance) {
        final var configInstanceTreeItem = new TreeItem<IDXTreeItem>(dxHttpConfigInstance);

        if (expand) configInstanceTreeItem.setExpanded(true);

        return configInstanceTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigRequest(DXHttpConfigRequest configRequest) {
        final var requestTreeItem = new TreeItem<IDXTreeItem>(configRequest);

        if (expand) requestTreeItem.setExpanded(true);

        return requestTreeItem;
    }

    public TreeItem<IDXTreeItem> createForHttpConfigEndpoint(DXHttpConfigEndpoint configEndpoint) {
        final var configEndpointTreeItem = new TreeItem<IDXTreeItem>(configEndpoint);

        if (expand) configEndpointTreeItem.setExpanded(true);

        configEndpointTreeItem.getChildren().addAll(configEndpoint.requests().values().stream().map(this::createForHttpConfigRequest).toList());

        configEndpoint.requestsProperty().addListener((MapChangeListener<DXHttpRequestMethod, DXHttpConfigRequest>) change -> {
            if (change.wasAdded()) configEndpointTreeItem.getChildren().add(createForHttpConfigRequest(change.getValueAdded()));
            if (change.wasRemoved()) configEndpointTreeItem.getChildren().removeIf(v -> v.getValue() == change.getValueRemoved());
        });

        return configEndpointTreeItem;
    }


    public TreeItem<IDXTreeItem> createForHttpConfigApi(DXHttpConfigApi httpConfigApi) {
        final var apiTreeItem = new TreeItem<IDXTreeItem>(httpConfigApi);

        if (expand) apiTreeItem.setExpanded(true);

        apiTreeItem.getChildren().addAll(httpConfigApi.instances().values().stream().map(this::createForHttpConfigInstance).toList());
        apiTreeItem.getChildren().addAll(httpConfigApi.endpoints().values().stream().map(this::createForHttpConfigEndpoint).toList());

        httpConfigApi.instancesProperty().addListener((MapChangeListener<String, DXHttpConfigInstance>) change -> {
            if (change.wasAdded()) apiTreeItem.getChildren().add(createForHttpConfigInstance(change.getValueAdded()));
            if (change.wasRemoved()) apiTreeItem.getChildren().removeIf(v -> v.getValue() == change.getValueRemoved());
        });

        httpConfigApi.endpointsProperty().addListener((MapChangeListener<String, DXHttpConfigEndpoint>) change -> {
            if (change.wasAdded())
                apiTreeItem.getChildren().add(createForHttpConfigEndpoint(change.getValueAdded()));
            if (change.wasRemoved())
                apiTreeItem.getChildren().removeIf(v -> v.getValue() == change.getValueRemoved());
        });

        return apiTreeItem;
    }

    public TreeItem<IDXTreeItem> createForConfig(DXHttpConfig httpConfig) {
        final var configTreeItem = new TreeItem<IDXTreeItem>(httpConfig);

        if (expand) configTreeItem.setExpanded(true);

        configTreeItem.getChildren().addAll(httpConfig.httpApis().values().stream().map(this::createForHttpConfigApi).toList());

        httpConfig.httpApisProperty().addListener((MapChangeListener<String, DXHttpConfigApi>) change -> {
            if (change.wasAdded()) configTreeItem.getChildren().add(createForHttpConfigApi(change.getValueAdded()));
            if (change.wasRemoved()) configTreeItem.getChildren().removeIf(v -> v.getValue() == change.getValueRemoved());
        });

        return configTreeItem;
    }
}
