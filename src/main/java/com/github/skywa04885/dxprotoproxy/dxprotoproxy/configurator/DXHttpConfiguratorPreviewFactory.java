package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigInstance;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;

public class DXHttpConfiguratorPreviewFactory {

    public List<TreeItem<DXConfiguratorPreviewItem>> createForApi(DXHttpConfigApi httpConfigApi) {
        final var nameItem = new DXConfiguratorPreviewItem(httpConfigApi.nameProperty().getName(), null);
        nameItem.valueProperty().bind(httpConfigApi.nameProperty());

        final var httpVersionItem = new DXConfiguratorPreviewItem(httpConfigApi.httpVersionProperty().getName(), null);
        httpVersionItem.valueProperty().bind(httpConfigApi.httpVersionProperty());

        return new ArrayList<>() {{
            add(new TreeItem<>(nameItem));
            add(new TreeItem<>(httpVersionItem));
        }};
    }

    public List<TreeItem<DXConfiguratorPreviewItem>> createForInstance(DXHttpConfigInstance httpConfigInstance) {
        final var nameItem = new DXConfiguratorPreviewItem(httpConfigInstance.nameProperty().getName(), null);
        nameItem.valueProperty().bind(httpConfigInstance.nameProperty());

        final var hostItem = new DXConfiguratorPreviewItem(httpConfigInstance.hostProperty().getName(), null);
        hostItem.valueProperty().bind(httpConfigInstance.hostProperty());

        final var portItem = new DXConfiguratorPreviewItem(httpConfigInstance.portProperty().getName(), null);
        portItem.valueProperty().bind(httpConfigInstance.portProperty().asString());

        final var protocolItem = new DXConfiguratorPreviewItem(httpConfigInstance.protocolProperty().getName(), null);
        protocolItem.valueProperty().bind(httpConfigInstance.protocolProperty());

        return new ArrayList<>() {{
            add(new TreeItem<>(nameItem));
            add(new TreeItem<>(hostItem));
            add(new TreeItem<>(portItem));
            add(new TreeItem<>(protocolItem));
        }};
    }

    public TreeItem<DXConfiguratorPreviewItem> create(Object object) {
        final var rootTreeItem = new TreeItem<>(new DXConfiguratorPreviewItem("", ""));

        if (object instanceof DXHttpConfigApi httpConfigApi)
            rootTreeItem.getChildren().addAll(createForApi(httpConfigApi));
        else if (object instanceof DXHttpConfigInstance httpConfigInstance)
            rootTreeItem.getChildren().addAll(createForInstance(httpConfigInstance));

        return rootTreeItem;
    }
}
