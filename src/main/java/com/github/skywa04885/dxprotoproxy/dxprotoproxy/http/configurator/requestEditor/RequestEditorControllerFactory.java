package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import org.jetbrains.annotations.NotNull;

public class RequestEditorControllerFactory {
    /**
     * Creates a new request editor controller meant for the creation of new requests.
     *
     * @param configEndpoint the endpoint that the new request will belong to.
     * @return the controller.
     */
    public static RequestEditorController create(@NotNull DXHttpConfigEndpoint configEndpoint) {
        return new RequestEditorController(
                RequestEditorSubmissionCallbackFactory.create(configEndpoint),
                RequestEditorValidationCallbackFactory.create(configEndpoint)
        );
    }

    /**
     * Creates a new request editor controller meant for the modification of existing requests.
     *
     * @param configRequest the request that needs to be modified.
     * @return the controller.
     */
    public static RequestEditorController modify(@NotNull DXHttpConfigRequest configRequest) {
        return new RequestEditorController(
                RequestEditorSubmissionCallbackFactory.modify(configRequest),
                RequestEditorValidationCallbackFactory.modify(configRequest)
        );
    }
}
