package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.endpointEditor;

public interface IEndpointEditorValidationCallback {
    /**
     * Gets called when the endpoint editor needs to validate its values.
     * @param name the name value.
     * @return null if all the values are valid, otherwise the error message.
     */
    String validate(String name);
}
