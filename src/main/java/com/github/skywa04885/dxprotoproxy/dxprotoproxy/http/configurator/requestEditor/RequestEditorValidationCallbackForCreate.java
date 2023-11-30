package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigValidators;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestEditorValidationCallbackForCreate implements IRequestEditorValidationCallback {
    @Override
    public String validate(String path, List<EditorQueryParameter> queryParameters, DXHttpRequestMethod method,
                           List<EditorHeader> headers, List<EditorField> fields, DXHttpFieldsFormat format) {
        String error = null;

        // Validates the template path.
        if (!DXHttpConfigValidators.isValidTemplatePath(path)) {
            error = "Invalid template path";
        }

        if (error == null) error = ValidatorForEditorHeaders.validate(headers);
        if (error == null) error = ValidatorForEditorFields.validate(fields);
        if (error == null) error = ValidatorForEditorQueryParameters.validate(queryParameters);

        return error;
    }
}
