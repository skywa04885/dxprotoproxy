package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorField;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorHeader;

import java.util.List;

/**
 * This interface represents an implementation of the submission callback for a response editor.
 */
public interface IResponseEditorSubmissionCallback {
    /**
     * Submits a response that has the given code, headers and fields.
     * @param code the code.
     * @param headers the headers.
     * @param fields the fields.
     * @param fieldsFormat the format of the fields.
     */
    void submit(int code, List<EditorHeader> headers, List<EditorField> fields, DXHttpFieldsFormat fieldsFormat);
}
