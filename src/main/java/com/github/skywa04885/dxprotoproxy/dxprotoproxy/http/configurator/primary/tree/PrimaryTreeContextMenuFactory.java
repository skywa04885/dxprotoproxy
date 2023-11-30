package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;
import javafx.scene.control.*;

public class PrimaryTreeContextMenuFactory {
    private final IPrimaryTreeContextMenuCallbacks configuratorTreeContextMenuCallbacks;

    public PrimaryTreeContextMenuFactory(IPrimaryTreeContextMenuCallbacks configuratorTreeContextMenuCallbacks) {
        this.configuratorTreeContextMenuCallbacks = configuratorTreeContextMenuCallbacks;

    }

    private ContextMenu createEndpointContextMenu(TreeCell<IDXTreeItem> treeCell) {
        final var contextMenu = new ContextMenu();

        final var modifyMenuItem = new MenuItem("Edit");
        final var deleteMenuItem = new MenuItem("Delete");
        final var addRequestMenuItem = new MenuItem("Craete request");

        addRequestMenuItem.setOnAction(event -> {
            if (treeCell.getTreeItem().getValue() instanceof DXHttpConfigEndpoint httpConfigEndpoint) {
                configuratorTreeContextMenuCallbacks.onAddRequestToEndpoint(httpConfigEndpoint);
            } else {
                throw new Error("Endpoint context menu opened on a non-endpoint tree item");
            }
        });

        contextMenu.getItems().addAll(modifyMenuItem, deleteMenuItem, addRequestMenuItem);

        return contextMenu;
    }

    private ContextMenu createApiContextMenu(TreeCell<IDXTreeItem> treeCell) {
        final var contextMenu = new ContextMenu();

        final var addEndpointMenuItem = new MenuItem("Create endpoint");
        final var addInstanceMenuItem = new MenuItem("Create instance");
        final var deleteInstanceMenuItem = new MenuItem("Delete");

        contextMenu.getItems().addAll(addEndpointMenuItem, addInstanceMenuItem, deleteInstanceMenuItem);

        addEndpointMenuItem.setOnAction(event -> {
            final DXHttpConfigApi httpConfigApi = (DXHttpConfigApi) treeCell.getTreeItem().getValue();
            configuratorTreeContextMenuCallbacks.onAddNewEndpointToApi(httpConfigApi);
        });

        addInstanceMenuItem.setOnAction(event -> {
            final DXHttpConfigApi httpConfigApi = (DXHttpConfigApi) treeCell.getTreeItem().getValue();
            configuratorTreeContextMenuCallbacks.onAddNewInstanceToApi(httpConfigApi);
        });

        deleteInstanceMenuItem.setOnAction(event -> {
            final DXHttpConfigApi httpConfigApi = (DXHttpConfigApi) treeCell.getTreeItem().getValue();
            configuratorTreeContextMenuCallbacks.onRemoveApiFromConfig(httpConfigApi);
        });

        return contextMenu;
    }

    private ContextMenu createInstanceContextMenu(TreeCell<IDXTreeItem> treeCell) {
        final var contextMenu = new ContextMenu();

        final var modifyMenuItem = new MenuItem("Edit");
        final var deleteMenuItem = new MenuItem("Delete");

        deleteMenuItem.setOnAction(event -> {
            final DXHttpConfigApi httpConfigApi = (DXHttpConfigApi) treeCell.getTreeItem().getParent().getValue();
            final DXHttpConfigInstance httpConfigInstance = (DXHttpConfigInstance) treeCell.getTreeItem().getValue();
            configuratorTreeContextMenuCallbacks.onRemoveInstanceFromApi(httpConfigApi, httpConfigInstance);
        });

        contextMenu.getItems().addAll(modifyMenuItem, deleteMenuItem);

        return contextMenu;
    }

    private ContextMenu createRequestContextMenu(TreeCell<IDXTreeItem> treeCell) {
        final var contextMenu = new ContextMenu();

        final var modifyMenuItem = new MenuItem("Edit");
        final var deleteMenuItem = new MenuItem("Delete");
        final var createResponseMenuItem = new MenuItem("Create response");

        contextMenu.getItems().addAll(modifyMenuItem, deleteMenuItem, createResponseMenuItem);

        createResponseMenuItem.setOnAction(event -> {
            final DXHttpConfigRequest request = (DXHttpConfigRequest) treeCell.getTreeItem().getValue();
            configuratorTreeContextMenuCallbacks.createResponseForRequest(request);
        });

        return contextMenu;
    }

    private ContextMenu createEmptyContextMenu(TreeCell<IDXTreeItem> treeCell) {
        return new ContextMenu();
    }

    private ContextMenu createConfigContextMenu(TreeCell<IDXTreeItem> treeCell) {
        final var contextMenu = new ContextMenu();

        final var addApiMenuItem = new MenuItem("Create API");

        contextMenu.getItems().addAll(addApiMenuItem);

        addApiMenuItem.setOnAction(event -> {
            final DXHttpConfig config = (DXHttpConfig) treeCell.getTreeItem().getValue();
            configuratorTreeContextMenuCallbacks.onAddNewApiToConfig(config);
        });

        return contextMenu;
    }

    public ContextMenu getContextMenuForTreeCell(TreeCell<IDXTreeItem> treeCell) {
        final TreeItem<IDXTreeItem> treeItem = treeCell.getTreeItem();

        final var value = treeItem.getValue();

        if (value instanceof DXHttpConfigEndpoint) return createEndpointContextMenu(treeCell);
        else if (value instanceof DXHttpConfigInstance) return createInstanceContextMenu(treeCell);
        else if (value instanceof DXHttpConfigRequest) return createRequestContextMenu(treeCell);
        else if (value instanceof DXHttpConfigApi) return createApiContextMenu(treeCell);
        else if (value instanceof DXHttpConfig) return createConfigContextMenu(treeCell);

        return createEmptyContextMenu(treeCell);
    }
}
