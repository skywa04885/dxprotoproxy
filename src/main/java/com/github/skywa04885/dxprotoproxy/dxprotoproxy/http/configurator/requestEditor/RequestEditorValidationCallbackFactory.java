package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import org.jetbrains.annotations.NotNull;

public class RequestEditorValidationCallbackFactory {
    /**
     * Creates a new validation callback meant for the creation of requests.
     * @param configEndpoint the endpoint that the request should belong to.
     * @return the validation callback.
     */
    public static IRequestEditorValidationCallback create(@NotNull  DXHttpConfigEndpoint configEndpoint) {
        return new RequestEditorValidationCallback(configEndpoint);
    }

    /**
     * Creates a new validation callback meant for the modification of an existing request.
     * @param configRequest the request that needs to be modified.
     * @return the validation callback.
     */
    public static IRequestEditorValidationCallback modify(@NotNull DXHttpConfigRequest configRequest) {
        return new RequestEditorValidationCallback(configRequest);
    }
}
