package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorField;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorHeader;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorQueryParameter;
import org.jetbrains.annotations.NotNull;

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
    void submit(@NotNull String path, @NotNull List<EditorQueryParameter> queryParameters,
                @NotNull DXHttpRequestMethod method, @NotNull List<EditorHeader> headers,
                @NotNull List<EditorField> fields, @NotNull DXHttpFieldsFormat format);
}
