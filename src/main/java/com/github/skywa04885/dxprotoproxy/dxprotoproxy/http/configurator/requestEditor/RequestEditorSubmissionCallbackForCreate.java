package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.*;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorField;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorHeader;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.EditorQueryParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestEditorSubmissionCallbackForCreate implements IRequestEditorSubmissionCallback {
    private final DXHttpConfigEndpoint httpConfigEndpoint;

    public RequestEditorSubmissionCallbackForCreate(DXHttpConfigEndpoint httpConfigEndpoint) {
        this.httpConfigEndpoint = httpConfigEndpoint;
    }

    @Override
    public void submit(String path, List<EditorQueryParameter> queryParameters, DXHttpRequestMethod method,
                       List<EditorHeader> headers, List<EditorField> fields, DXHttpFieldsFormat format) {
        // Parses the path template.
        final var httpPathTemplateParser = new DXHttpPathTemplateParser();
        final DXHttpPathTemplate httpPathTemplate = httpPathTemplateParser.parse(path);

        // Builds the maps for the headers, query parameters and fields.
        final Map<String, DXHttpConfigHeader> headerMap = headers.stream().collect(Collectors
                .toMap(EditorHeader::name, EditorHeader::httpConfigHeader));
        final Map<String, DXHttpConfigField> fieldmap = fields.stream().collect(Collectors
                .toMap(EditorField::name, EditorField::httpConfigField));
        final Map<String, DXHttpConfigUriQueryParameter> queryParameterMap = queryParameters.stream()
                .collect(Collectors.toMap(EditorQueryParameter::key,
                        EditorQueryParameter::httpConfigUriQueryParameter));

        // Constructs the request.
        final var httpConfigUri = new DXHttpConfigUri(httpPathTemplate, queryParameterMap);
        final var httpConfigHeaders = new DXHttpConfigHeaders(headerMap);
        final var httpConfigFields = new DXHttpConfigFields(fieldmap, format);
        final var httpConfigResponses = new DXHttpConfigResponses(new HashMap<>());
        final var httpConfigRequest = new DXHttpConfigRequest(httpConfigUri, method, httpConfigHeaders,
                httpConfigFields, httpConfigResponses);

        // Inserts the request into the endpoint.
        httpConfigEndpoint.getRequests().put(method, httpConfigRequest);
    }
}
