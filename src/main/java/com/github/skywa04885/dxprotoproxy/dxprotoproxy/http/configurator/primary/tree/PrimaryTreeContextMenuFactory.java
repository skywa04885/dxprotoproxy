package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.*;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;

public class PrimaryTreeContextMenuFactory {
    private final @NotNull IPrimaryTreeContextMenuCallbacks callbacks;
    private final @NotNull PrimaryTreeClipboard treeClipboard;

    public PrimaryTreeContextMenuFactory(@NotNull IPrimaryTreeContextMenuCallbacks configuratorTreeContextMenuCallbacks,
                                         @NotNull PrimaryTreeClipboard treeClipboard) {
        this.callbacks = configuratorTreeContextMenuCallbacks;
        this.treeClipboard = treeClipboard;
    }

    private ContextMenu createResponseContextMenu(DXHttpConfigResponse httpConfigResponse) {
        final var contextMenu = new ContextMenu();

        final var modifyMenuItem = new MenuItem("Modify");
        final var deleteMenuItem = new MenuItem("Delete");

        modifyMenuItem.setOnAction(event -> callbacks.modifyResponse(httpConfigResponse));
        deleteMenuItem.setOnAction(event -> callbacks.deleteResponse(httpConfigResponse));

        contextMenu.getItems().addAll(modifyMenuItem, deleteMenuItem);

        return contextMenu;
    }

    private ContextMenu createEndpointContextMenu(DXHttpConfigEndpoint httpConfigEndpoint) {
        final var contextMenu = new ContextMenu();

        final var modifyMenuItem = new MenuItem("Edit");
        final var deleteMenuItem = new MenuItem("Delete");
        final var addRequestMenuItem = new MenuItem("Craete request");

        modifyMenuItem.setOnAction(event -> callbacks.modifyEndpoint(httpConfigEndpoint));
        addRequestMenuItem.setOnAction(event -> callbacks.createRequest(httpConfigEndpoint));
        deleteMenuItem.setOnAction(event -> callbacks.deleteEndpoint(httpConfigEndpoint));

        contextMenu.getItems().addAll(modifyMenuItem, deleteMenuItem, addRequestMenuItem);

        return contextMenu;
    }

    private ContextMenu createInstancesContextMenu(@NotNull HttpConfigInstances httpConfigInstances) {
        final var contextMenu = new ContextMenu();

        final var addInstanceMenuItem = new MenuItem("Create instance");

        contextMenu.getItems().addAll(addInstanceMenuItem);

        addInstanceMenuItem.setOnAction(event -> callbacks.createInstance(httpConfigInstances));

        return contextMenu;
    }

    private ContextMenu createEndpointsContextMenu(@NotNull HttpConfigEndpoints httpConfigEndpoints) {
        final var contextMenu = new ContextMenu();

        final var createEndpointMenuItem = new MenuItem("Create endpoint");

        contextMenu.getItems().addAll(createEndpointMenuItem);

        createEndpointMenuItem.setOnAction(event -> callbacks.createEndpoint(httpConfigEndpoints));

        return contextMenu;
    }

    private ContextMenu createApiContextMenu(DXHttpConfigApi httpConfigApi) {
        final var contextMenu = new ContextMenu();

        final var deleteInstanceMenuItem = new MenuItem("Delete");
        final var modifyInstanceMenuItem = new MenuItem("Modify");

        contextMenu.getItems().addAll(deleteInstanceMenuItem, modifyInstanceMenuItem);

        deleteInstanceMenuItem.setOnAction(event -> callbacks.deleteApi(httpConfigApi));
        modifyInstanceMenuItem.setOnAction(event -> callbacks.modifyApi(httpConfigApi));

        return contextMenu;
    }

    private ContextMenu createInstanceContextMenu(DXHttpConfigInstance httpConfigInstance) {
        final var contextMenu = new ContextMenu();

        final var modifyMenuItem = new MenuItem("Edit");
        final var deleteMenuItem = new MenuItem("Delete");

        modifyMenuItem.setOnAction(event -> callbacks.modifyInstance(httpConfigInstance));
        deleteMenuItem.setOnAction(event -> callbacks.deleteInstance(httpConfigInstance));

        contextMenu.getItems().addAll(modifyMenuItem, deleteMenuItem);

        return contextMenu;
    }

    private ContextMenu createRequestContextMenu(DXHttpConfigRequest httpConfigRequest) {
        final var contextMenu = new ContextMenu();

        final var modifyMenuItem = new MenuItem("Edit");
        final var deleteMenuItem = new MenuItem("Delete");
        final var createResponseMenuItem = new MenuItem("Create response");

        modifyMenuItem.setOnAction(event -> callbacks.modifyRequest(httpConfigRequest));
        deleteMenuItem.setOnAction(event -> callbacks.deleteRequest(httpConfigRequest));
        createResponseMenuItem.setOnAction(event -> callbacks.createResponse(httpConfigRequest));

        contextMenu.getItems().addAll(modifyMenuItem, deleteMenuItem, createResponseMenuItem);

        return contextMenu;
    }

    private ContextMenu createEmptyContextMenu(TreeCell<IDXTreeItem> treeCell) {
        return new ContextMenu();
    }

    private ContextMenu createHttpApisContextMenu(@NotNull HttpConfigApis httpConfigApis) {
        final var contextMenu = new ContextMenu();

        final var addApiMenuItem = new MenuItem("Create API");

        contextMenu.getItems().addAll(addApiMenuItem);

        addApiMenuItem.setOnAction(event -> callbacks.createApi(httpConfigApis));

        return contextMenu;
    }

    private ContextMenu createConfigContextMenu(DXHttpConfig httpConfig) {
        final var contextMenu = new ContextMenu();

        return contextMenu;
    }

    public ContextMenu getContextMenuForTreeCell(TreeCell<IDXTreeItem> treeCell) {
        final TreeItem<IDXTreeItem> treeItem = treeCell.getTreeItem();

        final var value = treeItem.getValue();

        if (value instanceof DXHttpConfigEndpoint httpConfigEndpoint) {
            return createEndpointContextMenu(httpConfigEndpoint);
        } else if (value instanceof DXHttpConfigInstance httpConfigInstance) {
            return createInstanceContextMenu(httpConfigInstance);
        } else if (value instanceof DXHttpConfigRequest httpConfigRequest) {
            return createRequestContextMenu(httpConfigRequest);
        } else if (value instanceof DXHttpConfigApi httpConfigApi) {
            return createApiContextMenu(httpConfigApi);
        } else if (value instanceof DXHttpConfig httpConfig) {
            return createConfigContextMenu(httpConfig);
        } else if (value instanceof DXHttpConfigResponse httpConfigResponse) {
            return createResponseContextMenu(httpConfigResponse);
        } else if (value instanceof HttpConfigInstances httpConfigInstances) {
            return createInstancesContextMenu(httpConfigInstances);
        } else if (value instanceof HttpConfigEndpoints httpConfigEndpoints) {
            return createEndpointsContextMenu(httpConfigEndpoints);
        } else if (value instanceof HttpConfigApis httpConfigApis) {
            return createHttpApisContextMenu(httpConfigApis);
        }

        return createEmptyContextMenu(treeCell);
    }
}
