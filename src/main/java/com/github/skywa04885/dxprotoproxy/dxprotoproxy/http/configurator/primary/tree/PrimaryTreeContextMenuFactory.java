package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;
import javafx.scene.control.*;

public class PrimaryTreeContextMenuFactory {
    private final IPrimaryTreeContextMenuCallbacks callbacks;

    public PrimaryTreeContextMenuFactory(IPrimaryTreeContextMenuCallbacks configuratorTreeContextMenuCallbacks) {
        this.callbacks = configuratorTreeContextMenuCallbacks;

    }

    /**
     * Creates the context menu for a response.
     * @param treeCell the tree cell that contains the response.
     * @return the context menu.
     */
    private ContextMenu createResponseContextMenu(TreeCell<IDXTreeItem> treeCell) {
        // Checks if the value of the tree item is a response, if not, throw an error.
        if (treeCell.getTreeItem().getValue() instanceof DXHttpConfigResponse configResponse) {
            // Creates the context menu.
            final var contextMenu = new ContextMenu();

            // Creates the context menu items.
            final var modifyMenuItem = new MenuItem("Modify");
            final var deleteMenuItem = new MenuItem("Delete");

            // Sets the actions for the menu items.
            modifyMenuItem.setOnAction(event -> callbacks.modifyResponse(configResponse));
            deleteMenuItem.setOnAction(event -> callbacks.deleteResponse(configResponse));

            // Adds all the items to the context menu.
            contextMenu.getItems().addAll(modifyMenuItem, deleteMenuItem);

            // Returns the context menu.
            return contextMenu;
        } else {
            throw new Error("Cannot create response context menu for object of type: "
                    + treeCell.getTreeItem().getValue().getClass().getName());
        }
    }

    private ContextMenu createEndpointContextMenu(TreeCell<IDXTreeItem> treeCell) {
        final var contextMenu = new ContextMenu();

        final var modifyMenuItem = new MenuItem("Edit");
        final var deleteMenuItem = new MenuItem("Delete");
        final var addRequestMenuItem = new MenuItem("Craete request");

        addRequestMenuItem.setOnAction(event -> {
            if (treeCell.getTreeItem().getValue() instanceof DXHttpConfigEndpoint httpConfigEndpoint) {
                callbacks.onAddRequestToEndpoint(httpConfigEndpoint);
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
            callbacks.onAddNewEndpointToApi(httpConfigApi);
        });

        addInstanceMenuItem.setOnAction(event -> {
            final DXHttpConfigApi httpConfigApi = (DXHttpConfigApi) treeCell.getTreeItem().getValue();
            callbacks.onAddNewInstanceToApi(httpConfigApi);
        });

        deleteInstanceMenuItem.setOnAction(event -> {
            final DXHttpConfigApi httpConfigApi = (DXHttpConfigApi) treeCell.getTreeItem().getValue();
            callbacks.onRemoveApiFromConfig(httpConfigApi);
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
            callbacks.onRemoveInstanceFromApi(httpConfigApi, httpConfigInstance);
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
            callbacks.createResponse(request);
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
            callbacks.onAddNewApiToConfig(config);
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
        else if (value instanceof DXHttpConfigResponse) return createResponseContextMenu(treeCell);

        return createEmptyContextMenu(treeCell);
    }
}
