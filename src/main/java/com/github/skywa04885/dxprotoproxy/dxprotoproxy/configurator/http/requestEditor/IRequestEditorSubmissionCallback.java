package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;

import java.util.List;
import java.util.Map;

public interface IRequestEditorSubmissionCallback {
    /**
     * Submits the given values.
     * @param path the path.
     * @param queryParameters the query parameters.
     * @param method the method.
     * @param headers the headers.
     * @param fields the fields.
     * @param format the body format.
     */
    void submit(String path, List<RequestEditorQueryParameter> queryParameters, DXHttpRequestMethod method,
                List<RequestEditorHeader> headers, List<RequestEditorField> fields, DXHttpFieldsFormat format);
}
