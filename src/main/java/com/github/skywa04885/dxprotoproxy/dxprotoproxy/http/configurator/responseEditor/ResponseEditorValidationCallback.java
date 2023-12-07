package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigRequest;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigResponse;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * An implementation of the validation callback that is meant for the creation of a new response.
 */
public class ResponseEditorValidationCallback implements IResponseEditorValidationCallback {
    @Nullable
    private final DXHttpConfigRequest request;
    @Nullable
    private final DXHttpConfigResponse response;

    /**
     * Creates a new response editor validation callback for the given request.
     *
     * @param request  the request to which the response is going to belong.
     * @param response the existing response that's being modified.
     */
    public ResponseEditorValidationCallback(@Nullable DXHttpConfigRequest request, @Nullable DXHttpConfigResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Checks if the given code is already in use (only when creating).
     *
     * @param code the code to validate.
     * @return null if it's not yet in use, an error message otherwise.
     */
    private String checkIfCodeAlreadyUsed(int code) {
        String error = null;

        if ((this.response == null || this.response.code() != code) && request != null && request.responses().children().containsKey(code)) {
            error = "Response with code " + code + " is already specified";
        }

        return error;
    }

    /**
     * Validates a response that has the given code, headers and fields.
     *
     * @param code         the code.
     * @param headers      the headers.
     * @param fields       the fields.
     * @param fieldsFormat the format of the fields.
     * @return null if the contents are valid, a string containing the error message otherwise.
     */
    @Override
    public String validate(int code, List<EditorHeader> headers, List<EditorField> fields, DXHttpFieldsFormat fieldsFormat) {
        String error;

        error = ValidatorForResponseCode.validate(code);
        if (error == null) error = ValidatorForEditorHeaders.validate(headers);
        if (error == null) error = ValidatorForEditorFields.validate(fields);
        if (error == null) error = checkIfCodeAlreadyUsed(code);

        return error;
    }
}
