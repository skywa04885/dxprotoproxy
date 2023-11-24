package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor;

public interface IEndpointEditorSaveCallback {
    /**
     * Gets called when the endpoint editor wants to save its values.
     * @param name the name of the endpoint.
     */
    void save(String name);
}
