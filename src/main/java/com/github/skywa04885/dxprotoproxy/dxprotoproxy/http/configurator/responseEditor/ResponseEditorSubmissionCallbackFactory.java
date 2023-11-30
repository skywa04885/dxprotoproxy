package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigResponse;

public class ResponseEditorSubmissionCallbackFactory {
    /**
     * Creates a new submission callback meant for creation of responses.
     *
     * @param configRequest the request the response will belong to.
     * @return the callback.
     */
    public static ResponseEditorSubmissionCallback create(DXHttpConfigRequest configRequest) {
        return new ResponseEditorSubmissionCallback(configRequest, null);
    }

    /**
     * Creates a new submission callback meant for the updating of an existing response.
     *
     * @param configRequest  the request that the response belongs to.
     * @param configResponse the response.
     * @return the submission callback.
     */
    public static ResponseEditorSubmissionCallback update(DXHttpConfigRequest configRequest,
                                                          DXHttpConfigResponse configResponse) {
        return new ResponseEditorSubmissionCallback(configRequest, configResponse);
    }
}
