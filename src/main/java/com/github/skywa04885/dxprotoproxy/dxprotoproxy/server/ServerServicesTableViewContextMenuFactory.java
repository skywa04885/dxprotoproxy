package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.http.client.HttpClientNetService;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net.NetInService;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net.NetService;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

public class ServerServicesTableViewContextMenuFactory {
    private final @NotNull IServerServicesTableViewContextMenuCallbacks callbacks;

    public ServerServicesTableViewContextMenuFactory(@NotNull IServerServicesTableViewContextMenuCallbacks callbacks) {
        this.callbacks = callbacks;
    }


    public @NotNull ContextMenu create(@NotNull NetService netService) {
        final var contextMenu = new ContextMenu();

        final var activateMenuItem = new MenuItem("Activate");
        final var deactivateMenuItem = new MenuItem("Deactivate");

        activateMenuItem.setDisable(netService.state() instanceof NetService.Crashed
                || netService.state() instanceof NetService.Active);
        deactivateMenuItem.setDisable(netService.state() instanceof NetService.Crashed
                || netService.state() instanceof NetService.Inactive);

        activateMenuItem.setOnAction(actionEvent -> {
            contextMenu.hide();
            callbacks.activateService(netService);
        });
        deactivateMenuItem.setOnAction(actionEvent -> {
            contextMenu.hide();
            callbacks.deactivateService(netService);
        });

        contextMenu.getItems().addAll(activateMenuItem, deactivateMenuItem);

        return contextMenu;
    }
}
