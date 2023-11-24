package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;

import java.io.IOException;

public class EndpointEditorFactory {
    /**
     * Creates a new endpoint editor meant for the creation of a new endpoint
     * @param api the api that the endpoint is contained by.
     * @return the endpoint editor.
     * @throws IOException gets thrown if the view cannot be loaded.
     */
    public EndpointEditor forCreate(DXHttpConfigApi api) throws IOException {
        return new EndpointEditor(
                new EndpointEditorSaveCallbackForCreate(api),
                new EndpointEditorValidationCallbackForCreate(api)
        );
    }

    /**
     * Creates a new endpoint editor meant for the update of an existing endpoint.
     * @param api the api that the endpoint is contained by.
     * @param oldName the old name of the endpoint.
     * @return the endpoint editor.
     * @throws IOException gets thrown if the view cannot be loaded.
     */
    public EndpointEditor forUpdate(DXHttpConfigApi api, String oldName) throws IOException {
        return new EndpointEditor(
                new EndpointEditorSaveCallbackForUpdate(api, oldName),
                new EndpointEditorValidationCallbackForUpdate(api, oldName)
        );
    }
}
