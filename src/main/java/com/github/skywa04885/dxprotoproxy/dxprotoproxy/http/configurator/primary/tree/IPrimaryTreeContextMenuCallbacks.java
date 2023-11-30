package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;

public interface IPrimaryTreeContextMenuCallbacks {
    void createResponseForRequest(DXHttpConfigRequest request);

    void onAddNewApiToConfig(DXHttpConfig httpConfig);

    void onRemoveApiFromConfig(DXHttpConfigApi httpConfigApi);

    void onAddNewInstanceToApi(DXHttpConfigApi httpConfigApi);

    void onRemoveInstanceFromApi(DXHttpConfigApi httpConfigApi, DXHttpConfigInstance httpConfigInstance);

    void onAddNewEndpointToApi(DXHttpConfigApi httpConfigApi);

    void onAddRequestToEndpoint(DXHttpConfigEndpoint httpConfigEndpoint);
}
