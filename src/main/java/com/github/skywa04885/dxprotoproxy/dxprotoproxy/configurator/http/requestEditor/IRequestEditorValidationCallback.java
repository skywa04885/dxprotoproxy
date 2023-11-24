package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;

import java.util.List;

public interface IRequestEditorValidationCallback {
    /**
     * Validates the given values from the query editor.
     * @param path the path to validate.
     * @param queryParameters the query parameters.
     * @param method the method to validate.
     * @param headers the headers to validate.
     * @param fields the fields to validate.
     * @param format the body format.
     * @return null if everything was correct, otherwise the error message.
     */
    String validate(String path, List<RequestEditorQueryParameter> queryParameters, DXHttpRequestMethod method,
                    List<RequestEditorHeader> headers, List<RequestEditorField> fields, DXHttpFieldsFormat format);
}
