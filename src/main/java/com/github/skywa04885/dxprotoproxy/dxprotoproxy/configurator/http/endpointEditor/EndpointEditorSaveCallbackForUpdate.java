package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigEndpoint;

public class EndpointEditorSaveCallbackForUpdate implements IEndpointEditorSaveCallback {
    private final DXHttpConfigApi httpConfigApi;
    private final String oldName;

    /**
     * Creates a new save callback implementation that updates an existing endpoint.
     * @param httpConfigApi the api that the endpoint is contained in.
     * @param oldName the old endpoint name.
     */
    public EndpointEditorSaveCallbackForUpdate(DXHttpConfigApi httpConfigApi, String oldName) {
        this.httpConfigApi = httpConfigApi;
        this.oldName = oldName;
    }

    /**
     * Updates the existing endpoint.
     * @param name the name of the endpoint.
     */
    @Override
    public void save(String name) {
        // Removes the endpoint from the api.
        DXHttpConfigEndpoint httpConfigEndpoint = httpConfigApi.endpoints().remove(oldName);

        // Sets the new name of the endpoint.
        httpConfigEndpoint.setName(name);

        // Puts the endpoint in the api using the new name.
        httpConfigApi.endpoints().put(name, httpConfigEndpoint);
    }
}
