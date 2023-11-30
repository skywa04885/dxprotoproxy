package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigResponse;

public class ResponseEditorValidationCallbackFactory {
    /**
     * Creates a new validation callback meant for creation of responses.
     *
     * @param configRequest the request the response will belong to.
     * @return the validation callback.
     */
    public static ResponseEditorValidationCallback create(DXHttpConfigRequest configRequest) {
        return new ResponseEditorValidationCallback(configRequest, null);
    }

    /**
     * Creates a new validation callback meant for the updating of an existing response.
     *
     * @param configRequest  the request that the response belongs to.
     * @param configResponse the response.
     * @return the validation callback.
     */
    public static ResponseEditorValidationCallback update(DXHttpConfigRequest configRequest,
                                                          DXHttpConfigResponse configResponse) {
        return new ResponseEditorValidationCallback(configRequest, configResponse);
    }
}
