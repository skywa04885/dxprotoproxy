package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpConfigValidators;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestEditorValidationCallbackForCreate implements IRequestEditorValidationCallback {
    @Override
    public String validate(String path, List<RequestEditorQueryParameter> queryParameters, DXHttpRequestMethod method,
                           List<RequestEditorHeader> headers, List<RequestEditorField> fields, DXHttpFieldsFormat format) {
        // Validates the template path.
        if (!DXHttpConfigValidators.isValidTemplatePath(path)) {
            return "Invalid template path";
        }

        // Checks for duplicate query parameter names.
        final List<String> queryParameterKeys = queryParameters.stream().map(RequestEditorQueryParameter::key).toList();
        final Set<String> duplicateQueryParameterKeys = queryParameterKeys.stream()
                .filter(item -> Collections.frequency(queryParameterKeys, item) > 1).collect(Collectors.toSet());
        if (!duplicateQueryParameterKeys.isEmpty()) {
            return "Found duplicate query parameter keys: " + String.join(", ", duplicateQueryParameterKeys);
        }

        // Checks for duplicate header keys.
        final List<String> headerKeys = headers.stream().map(RequestEditorHeader::key).toList();
        final Set<String> duplicateHeaderKeys = headerKeys.stream()
                .filter(item -> Collections.frequency(headerKeys, item) > 1).collect(Collectors.toSet());
        if (!duplicateHeaderKeys.isEmpty()) {
            return "Found duplicate header keys: " + String.join(", ", duplicateHeaderKeys);
        }

        // Checks for duplicate header names.
        final List<String> headerNames = headers.stream().map(RequestEditorHeader::name).toList();
        final Set<String> duplicateHeaderNames = headerNames.stream()
                .filter(item -> Collections.frequency(headerNames, item) > 1).collect(Collectors.toSet());
        if (!duplicateHeaderNames.isEmpty()) {
            return "Found duplicate header names: " + String.join(", ", duplicateHeaderNames);
        }

        // Checks for duplicate header keys.
        final List<String> fieldsFields = fields.stream().map(RequestEditorField::path).toList();
        final Set<String> duplicateFields = fieldsFields.stream()
                .filter(item -> Collections.frequency(fieldsFields, item) > 1).collect(Collectors.toSet());
        if (!duplicateFields.isEmpty()) {
            return "Found duplicate fields: " + String.join(", ", duplicateFields);
        }

        // Checks for duplicate header names.
        final List<String> fieldNames = fields.stream().map(RequestEditorField::name).toList();
        final Set<String> duplicateFieldNames = fieldNames.stream()
                .filter(item -> Collections.frequency(fieldNames, item) > 1).collect(Collectors.toSet());
        if (!duplicateFieldNames.isEmpty()) {
            return "Found duplicate field names: " + String.join(", ", duplicateFieldNames);
        }

        return null;
    }
}
