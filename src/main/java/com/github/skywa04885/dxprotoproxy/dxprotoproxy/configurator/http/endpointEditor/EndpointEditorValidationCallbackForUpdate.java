package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigValidators;

public class EndpointEditorValidationCallbackForUpdate implements IEndpointEditorValidationCallback {
    private final DXHttpConfigApi httpConfigApi;
    private final String oldName;

    public EndpointEditorValidationCallbackForUpdate(DXHttpConfigApi httpConfigApi, String oldName) {
        this.httpConfigApi = httpConfigApi;
        this.oldName = oldName;
    }

    @Override
    public String validate(String name) {
        if (!DXHttpConfigValidators.isNameValid(name)) {
            return "Invalid name";
        }

        if (!name.equals(oldName) && httpConfigApi.endpoints().containsKey(name)) {
            return "Name is already in use";
        }

        return null;
    }
}
