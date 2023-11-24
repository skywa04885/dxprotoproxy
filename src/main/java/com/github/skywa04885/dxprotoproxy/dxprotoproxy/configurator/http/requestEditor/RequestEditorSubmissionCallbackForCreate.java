package com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.http.requestEditor;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.*;

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
    public void submit(String path, List<RequestEditorQueryParameter> queryParameters, DXHttpRequestMethod method,
                       List<RequestEditorHeader> headers, List<RequestEditorField> fields, DXHttpFieldsFormat format) {
        // Parses the path template.
        final var httpPathTemplateParser = new DXHttpPathTemplateParser();
        final DXHttpPathTemplate httpPathTemplate = httpPathTemplateParser.parse(path);

        // Builds the maps for the headers, query parameters and fields.
        final Map<String, DXHttpConfigHeader> headerMap = headers.stream().collect(Collectors
                .toMap(RequestEditorHeader::name, RequestEditorHeader::httpConfigHeader));
        final Map<String, DXHttpConfigField> fieldmap = fields.stream().collect(Collectors
                .toMap(RequestEditorField::name, RequestEditorField::httpConfigField));
        final Map<String, DXHttpConfigUriQueryParameter> queryParameterMap = queryParameters.stream()
                .collect(Collectors.toMap(RequestEditorQueryParameter::key,
                        RequestEditorQueryParameter::httpConfigUriQueryParameter));

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
