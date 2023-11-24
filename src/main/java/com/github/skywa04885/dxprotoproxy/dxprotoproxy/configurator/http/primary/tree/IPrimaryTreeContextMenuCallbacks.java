package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.primary.tree;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigInstance;

public interface IPrimaryTreeContextMenuCallbacks {
    void onAddNewApiToConfig(DXHttpConfig httpConfig);

    void onRemoveApiFromConfig(DXHttpConfigApi httpConfigApi);

    void onAddNewInstanceToApi(DXHttpConfigApi httpConfigApi);

    void onRemoveInstanceFromApi(DXHttpConfigApi httpConfigApi, DXHttpConfigInstance httpConfigInstance);

    void onAddNewEndpointToApi(DXHttpConfigApi httpConfigApi);

    void onAddRequestToEndpoint(DXHttpConfigEndpoint httpConfigEndpoint);
}
