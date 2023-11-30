package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;

import java.util.HashMap;

public class EndpointEditorSaveCallbackForCreate implements IEndpointEditorSaveCallback {
    private final DXHttpConfigApi httpConfigApi;

    /**
     * Creates a new endpoint editor save callback that creates the endpoint.
     * @param httpConfigApi the api that should contain the endpoint.
     */
    public EndpointEditorSaveCallbackForCreate(DXHttpConfigApi httpConfigApi) {
        this.httpConfigApi = httpConfigApi;
    }

    /**
     * Creates the endpoint.
     * @param name the name of the endpoint.
     */
    @Override
    public void save(String name) {
        // Creates the endpoint.
        final var httpConfigEndpoint = new DXHttpConfigEndpoint(name, new HashMap<>());

        // Puts the endpoint in the map.
        httpConfigApi.endpoints().put(httpConfigEndpoint.name(), httpConfigEndpoint);
    }
}
