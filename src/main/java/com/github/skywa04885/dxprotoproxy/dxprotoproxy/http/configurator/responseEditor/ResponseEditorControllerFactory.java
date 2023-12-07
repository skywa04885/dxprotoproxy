package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigResponse;

public class ResponseEditorControllerFactory {
    /**
     * Creates a new response editor controller meant for the creation of a new response.
     *
     * @param configRequest the request that the response will belong to.
     * @return the controller.
     */
    public static ResponseEditorController create(DXHttpConfigRequest configRequest) {
        return new ResponseEditorController(
                null,
                ResponseEditorValidationCallbackFactory.create(configRequest),
                ResponseEditorSubmissionCallbackFactory.create(configRequest)
        );
    }

    /**
     * Creates a new response editor controller meant for updating an existing response.
     *
     * @param configResponse the response that must be updated.
     * @return the controller.
     */
    public static ResponseEditorController update(DXHttpConfigResponse configResponse) {
        return new ResponseEditorController(
                configResponse,
                ResponseEditorValidationCallbackFactory.update(configResponse),
                ResponseEditorSubmissionCallbackFactory.update(configResponse)
        );
    }
}
