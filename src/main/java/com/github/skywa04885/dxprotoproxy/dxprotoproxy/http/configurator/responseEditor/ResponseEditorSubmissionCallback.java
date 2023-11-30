package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.responseEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorField;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorHeader;

import java.util.HashMap;
import java.util.List;

/**
 * An implementation of the response editor submission callback that either updates or creates responses.
 */
public class ResponseEditorSubmissionCallback implements IResponseEditorSubmissionCallback {
    private final DXHttpConfigRequest request;
    private final DXHttpConfigResponse response;

    /**
     * Creates a new response editor submission callback.
     *
     * @param request  the request that the response belongs to.
     * @param response the original response that's being modified, if it's there.
     */
    public ResponseEditorSubmissionCallback(DXHttpConfigRequest request, DXHttpConfigResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Creates a new response.
     *
     * @param code          the code.
     * @param editorHeaders the headers.
     * @param editorFields  the fields.
     * @param fieldsFormat  the format of the fields.
     */
    private void create(int code, List<EditorHeader> editorHeaders, List<EditorField> editorFields, DXHttpFieldsFormat fieldsFormat) {
        assert response == null;

        // Creates the editor headers and the editor fields objects.
        final var configHeaders = new DXHttpConfigHeaders(new HashMap<>());
        final var configFields = new DXHttpConfigFields(new HashMap<>(), fieldsFormat);

        // Inserts all the editor headers.
        editorHeaders.forEach(editorHeader -> {
            final var header = new DXHttpConfigHeader(editorHeader.key(), editorHeader.value(), editorHeader.name());
            configHeaders.children().put(editorHeader.name(), header);
        });

        // Inserts all the editor fields.
        editorFields.forEach(editorField -> {
            final var configField = new DXHttpConfigField(editorField.path(), editorField.name(), editorField.value());
            configFields.children().put(editorField.name(), configField);
        });

        // Creates the response.
        final var response = new DXHttpConfigResponse(code, configFields, configHeaders);

        // Puts the response in the request.
        request.responses().children().put(code, response);
    }

    /**
     * Updates the headers.
     *
     * @param editorHeaders the editor headers.
     */
    private void updateHeaders(List<EditorHeader> editorHeaders) {
        assert response != null;

        // Gets the headers from the config.
        final DXHttpConfigHeaders configHeaders = response.headers();

        // Creates all the new headers.
        editorHeaders.stream().filter(EditorHeader::hasNoConfigHeader).forEach(editorHeader -> {
            final var configHeader = new DXHttpConfigHeader(editorHeader.key(), editorHeader.value(), editorHeader.name());
            configHeaders.children().put(editorHeader.name(), configHeader);
        });

        // Updates all the existing headers.
        editorHeaders.stream().filter(EditorHeader::hasConfigHeader).forEach(editorHeader -> {
            final DXHttpConfigHeader configHeader = editorHeader.configHeader();
            if (!configHeader.key().equals(editorHeader.key())) configHeader.setKey(editorHeader.key());
            if (!configHeader.value().equals(editorHeader.value())) configHeader.setValue(editorHeader.value());
            if (!configHeader.name().equals(editorHeader.name())) configHeader.setName(editorHeader.name());
        });

        // Removes all the removed headers.
        configHeaders.children().keySet().removeIf(configHeaderName -> editorHeaders.stream()
                .noneMatch(editorHeader -> editorHeader.name().equals(configHeaderName)));
    }

    /**
     * Updates the fields.
     *
     * @param editorFields the editor fields.
     */
    private void updateFields(List<EditorField> editorFields) {
        assert response != null;

        // Gets the fields from the config.
        final DXHttpConfigFields configFields = response.fields();

        // Creates all the new fields.
        editorFields.stream().filter(EditorField::hasNoConfigField).forEach(editorField -> {
            final var configField = new DXHttpConfigField(editorField.path(), editorField.name(), editorField.value());
            configFields.children().put(editorField.name(), configField);
        });

        // Updates all the existing fields.
        editorFields.stream().filter(EditorField::hasConfigField).forEach(editorField -> {
            final DXHttpConfigField configField = editorField.configField();
            if (!configField.path().equals(editorField.path())) configField.setPath(editorField.path());
            if (!configField.value().equals(editorField.value())) configField.setValue(editorField.value());
            if (!configField.name().equals(editorField.name())) configField.setName(editorField.name());
        });

        // Removes all the removed fields.
        configFields.children().keySet().removeIf(configFieldName -> editorFields.stream()
                .noneMatch(editorField -> editorField.name().equals(configFieldName)));
    }

    /**
     * Updates an existing response.
     *
     * @param code          the code.
     * @param editorHeaders the headers.
     * @param editorFields  the fields.
     * @param fieldsFormat  the format of the fields.
     */
    private void update(int code, List<EditorHeader> editorHeaders, List<EditorField> editorFields, DXHttpFieldsFormat fieldsFormat) {
        assert response != null;

        // Updates the field format.
        if (response.fields().format() != fieldsFormat) response.fields().setFormat(fieldsFormat);

        // Updates the headers and the fields.
        updateHeaders(editorHeaders);
        updateFields(editorFields);

        // Updates the status code in both the response and the responses map.
        if (response.code() != code) {
            request.responses().children().remove(response.code());
            response.setCode(code);
            request.responses().children().put(response.code(), response);
        }
    }

    /**
     * Saves the response editor by either creating a new response or updating an existing one.
     *
     * @param code          the code.
     * @param editorHeaders the headers.
     * @param editorFields  the fields.
     * @param fieldsFormat  the format of the fields.
     */
    @Override
    public void submit(int code, List<EditorHeader> editorHeaders, List<EditorField> editorFields, DXHttpFieldsFormat fieldsFormat) {
        if (response == null) create(code, editorHeaders, editorFields, fieldsFormat);
        else update(code, editorHeaders, editorFields, fieldsFormat);
    }
}
