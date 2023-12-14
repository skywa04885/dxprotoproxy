package com.github.skywa04885.dxprotoproxy.configurator.http.requestEditor;

import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfigEndpoint;
import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfigRequest;
import com.github.skywa04885.dxprotoproxy.configurator.http.*;
import com.github.skywa04885.dxprotoproxy.config.http.HttpConfigValidators;
import com.github.skywa04885.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.http.DXHttpRequestMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RequestEditorValidationCallback implements IRequestEditorValidationCallback {
    @Nullable
    private final DXHttpConfigEndpoint configEndpoint;
    @Nullable
    private final DXHttpConfigRequest configRequest;

    /**
     * Creates a new config request that's either update or create depending on the given values.
     *
     * @param configEndpoint create if not null.
     * @param configRequest  update if not null.
     */
    public RequestEditorValidationCallback(@Nullable DXHttpConfigEndpoint configEndpoint,
                                           @Nullable DXHttpConfigRequest configRequest) {
        this.configEndpoint = configEndpoint;
        this.configRequest = configRequest;
    }

    /**
     * Creates a new request editor validation callback meant for creating.
     *
     * @param configEndpoint the endpoint that the request will belong to.
     */
    public RequestEditorValidationCallback(@NotNull DXHttpConfigEndpoint configEndpoint) {
        this(configEndpoint, null);
    }

    /**
     * Creates a new request editor validation callback meant for validating a modification.
     *
     * @param configRequest the original request that's being modified.
     */
    public RequestEditorValidationCallback(@NotNull DXHttpConfigRequest configRequest) {
        this(null, configRequest);
    }

    /**
     * Validates the given values.
     *
     * @param path            the path to validate.
     * @param queryParameters the query parameters.
     * @param method          the method to validate.
     * @param headers         the headers to validate.
     * @param fields          the fields to validate.
     * @param format          the body format.
     * @return null if all the values are valid, an error message otherwise.
     */
    @Override
    public String validate(@NotNull String path, @NotNull List<EditorQueryParameter> queryParameters,
                           @NotNull DXHttpRequestMethod method, @NotNull List<EditorHeader> headers,
                           @NotNull List<EditorField> fields, @NotNull DXHttpFieldsFormat format) {

        String error = null;

        // Make sure that the method is not yet in use.
        if (configEndpoint != null && configRequest == null) {
            if (configEndpoint.requests().containsKey(method)) {
                error = "The request method is already associated with a different request";
            }
        } else if (configEndpoint == null && configRequest != null) {
            if (!configRequest.method().equals(method) && configRequest.parent().requests().containsKey(method)) {
                error = "The request method is already associated with a different request";
            }
        }

        // Validates the template path.
        if (error == null && !HttpConfigValidators.isValidTemplatePath(path)) {
            error = "Invalid template path";
        }

        // Validates the editor headers.
        if (error == null) {
            error = ValidatorForEditorHeaders.validate(headers);
        }

        // Validates the editor fields.
        if (error == null) {
            error = ValidatorForEditorFields.validate(fields);
        }

        // Validates the query parameters.
        if (error == null) {
            error = ValidatorForEditorQueryParameters.validate(queryParameters);
        }

        return error;
    }
}
