package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorField;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorHeader;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorQueryParameter;
import org.jetbrains.annotations.NotNull;

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
    String validate(@NotNull String path, @NotNull List<EditorQueryParameter> queryParameters,
                    @NotNull DXHttpRequestMethod method, @NotNull List<EditorHeader> headers,
                    @NotNull List<EditorField> fields, @NotNull DXHttpFieldsFormat format);
}
