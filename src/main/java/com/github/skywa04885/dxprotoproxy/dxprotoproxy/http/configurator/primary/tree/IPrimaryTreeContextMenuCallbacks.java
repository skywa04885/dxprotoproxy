package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.primary.tree;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;
import org.jetbrains.annotations.NotNull;

public interface IPrimaryTreeContextMenuCallbacks {
    void createResponse(DXHttpConfigRequest request);

    void createApi(@NotNull DXHttpConfig httpConfig);

    void modifyApi(@NotNull DXHttpConfigApi httpConfigApi);

    void deleteApi(@NotNull DXHttpConfigApi httpConfigApi);

    void createInstance(@NotNull DXHttpConfigApi httpConfigApi);

    void modifyInstance(@NotNull DXHttpConfigInstance httpConfigInstance);

    void deleteInstance(@NotNull DXHttpConfigInstance httpConfigInstance);

    void createEndpoint(@NotNull DXHttpConfigApi httpConfigApi);

    /**
     * Deletes the given endpoint.
     *
     * @param configEndpoint the endpoint to delete.
     */
    void deleteEndpoint(@NotNull DXHttpConfigEndpoint configEndpoint);

    /**
     * Modifies the given endpoint.
     *
     * @param configEndpoint the endpoint to modify.
     */
    void modifyEndpoint(@NotNull DXHttpConfigEndpoint configEndpoint);

    void createRequest(@NotNull DXHttpConfigEndpoint httpConfigEndpoint);

    /**
     * This callback gets called when the given response needs to be edited.
     *
     * @param configResponse the response that needs to be modified.
     */
    void modifyResponse(@NotNull DXHttpConfigResponse configResponse);

    /**
     * This callback gets called when the given response needs to be deleted.
     *
     * @param configResponse the response that needs to be deleted.
     */
    void deleteResponse(@NotNull DXHttpConfigResponse configResponse);

    /**
     * Gets called when the given request needs to be deleted.
     *
     * @param configRequest the request that needs to be deleted.
     */
    void deleteRequest(@NotNull DXHttpConfigRequest configRequest);

    /**
     * Gets called when the given request needs to be modified.
     *
     * @param configRequest the request that needs to be modified.
     */
    void modifyRequest(@NotNull DXHttpConfigRequest configRequest);
}
