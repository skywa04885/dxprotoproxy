package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigEndpoint;

import java.io.IOException;

public class RequestEditorFactory {
    public RequestEditor forCreate(DXHttpConfigEndpoint httpConfigEndpoint) throws IOException {
        return new RequestEditor(
                new RequestEditorValidationCallbackForCreate(),
                new RequestEditorSubmissionCallbackForCreate(httpConfigEndpoint)
        );
    }
}
