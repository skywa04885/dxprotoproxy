package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.endpointEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigApi;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigValidators;

public class EndpointEditorValidationCallbackForCreate implements IEndpointEditorValidationCallback {
    private final DXHttpConfigApi httpConfigApi;

    public EndpointEditorValidationCallbackForCreate(DXHttpConfigApi httpConfigApi) {
        this.httpConfigApi = httpConfigApi;
    }

    @Override
    public String validate(String name) {
        if (!DXHttpConfigValidators.isNameValid(name)) {
            return "Invalid name";
        }

        if (httpConfigApi.endpoints().containsKey(name)) {
            return "Name already in use";
        }

        return null;
    }
}
