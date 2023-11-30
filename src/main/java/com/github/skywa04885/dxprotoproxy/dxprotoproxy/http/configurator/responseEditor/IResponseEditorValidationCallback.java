package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorField;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorHeader;

import java.util.List;

/**
 * This interface represents a validation callback for the response editor.
 */
public interface IResponseEditorValidationCallback {
    /**
     * Validates a response that has the given code, headers and fields.
     * @param code the code.
     * @param headers the headers.
     * @param fields the fields.
     * @param fieldsFormat the format of the fields.
     * @return null if the contents are valid, a string containing the error message otherwise.
     */
    String validate(int code, List<EditorHeader> headers, List<EditorField> fields, DXHttpFieldsFormat fieldsFormat);
}
