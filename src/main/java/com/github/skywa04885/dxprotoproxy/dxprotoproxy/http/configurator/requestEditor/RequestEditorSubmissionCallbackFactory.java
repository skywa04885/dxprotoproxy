package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import org.jetbrains.annotations.NotNull;

public class RequestEditorSubmissionCallbackFactory {
    /**
     * Creates a new submission callback meant for the creation of requests.
     * @param configEndpoint the endpoint that the request should belong to.
     * @return the submission callback.
     */
    public static RequestEditorSubmissionCallback create(@NotNull  DXHttpConfigEndpoint configEndpoint) {
        return new RequestEditorSubmissionCallback(configEndpoint);
    }

    /**
     * Creates a new submission callback meant for the modification of an existing request.
     * @param configRequest the request that needs to be modified.
     * @return the submission callback.
     */
    public static RequestEditorSubmissionCallback modify(@NotNull DXHttpConfigRequest configRequest) {
        return new RequestEditorSubmissionCallback(configRequest);
    }
}
