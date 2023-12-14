package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net.NetInService;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net.NetService;
import org.jetbrains.annotations.NotNull;

public interface IServerServicesTableViewContextMenuCallbacks {
    void activateService(@NotNull NetService netService);

    void deactivateService(@NotNull NetService netService);
}
