package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;

public interface IPrimaryTreeContextMenuCallbacks {
    void createResponse(DXHttpConfigRequest request);

    void onAddNewApiToConfig(DXHttpConfig httpConfig);

    void onRemoveApiFromConfig(DXHttpConfigApi httpConfigApi);

    void onAddNewInstanceToApi(DXHttpConfigApi httpConfigApi);

    void onRemoveInstanceFromApi(DXHttpConfigApi httpConfigApi, DXHttpConfigInstance httpConfigInstance);

    void onAddNewEndpointToApi(DXHttpConfigApi httpConfigApi);

    void onAddRequestToEndpoint(DXHttpConfigEndpoint httpConfigEndpoint);

    /**
     * This callback gets called when the given response needs to be edited.
     * @param configResponse the response that needs to be modified.
     */
    void modifyResponse(DXHttpConfigResponse configResponse);

    /**
     * This callback gets called when the given response needs to be deleted.
     * @param configResponse the response that needs to be deleted.
     */
    void deleteResponse(DXHttpConfigResponse configResponse);
}
