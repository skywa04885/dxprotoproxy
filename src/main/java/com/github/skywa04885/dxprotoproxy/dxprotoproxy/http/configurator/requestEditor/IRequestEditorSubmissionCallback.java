package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorField;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorHeader;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorQueryParameter;

import java.util.List;

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
    void submit(String path, List<EditorQueryParameter> queryParameters, DXHttpRequestMethod method,
                List<EditorHeader> headers, List<EditorField> fields, DXHttpFieldsFormat format);
}
