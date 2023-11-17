package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigInstance;

public interface DXHttpConfiguratorTreeContextMenuCallbacks {
    void onAddNewApiToConfig(DXHttpConfig httpConfig);

    void onRemoveApiFromConfig(DXHttpConfigApi httpConfigApi);

    void onAddNewInstanceToApi(DXHttpConfigApi httpConfigApi);

    void onRemoveInstanceFromApi(DXHttpConfigInstance httpConfigInstance);

    void onAddNewEndpointToApi(DXHttpConfigApi httpConfigApi);
}
