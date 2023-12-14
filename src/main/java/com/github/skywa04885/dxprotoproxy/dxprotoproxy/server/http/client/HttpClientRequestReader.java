package com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.http.client;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsEncoder;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpPathTemplateRenderer;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.server.net.NetInputStreamWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpClientRequestReader {

    private final @NotNull NetInputStreamWrapper proxyInputStreamReader;
    private final @NotNull DXHttpConfig config;

    /**
     * Constructs a new http client request reader that reads from the given input stream.
     * @param proxyInputStreamReader the input stream to read from.
     * @param config the configuration for the request building.
     */
    public HttpClientRequestReader(@NotNull NetInputStreamWrapper proxyInputStreamReader,
                                   @NotNull DXHttpConfig config) {
        this.proxyInputStreamReader = proxyInputStreamReader;
        this.config = config;
    }

    /**
     * Reads a http client request from the input stream.
     * @return the client request or null if the client disconnected.
     * @throws IOException gets thrown if the reading failed.
     */
    public HttpClientRequest read() throws IOException {
        ///
        /// Gets the initialization line.
        ///

        // Read the initialization line.
        String tempString = proxyInputStreamReader.readStringUntilNewLine();
        if (tempString == null) return null;

        // Split the initialization line into its segments.
        String[] tempStringSegments = tempString.trim().split(" ");

        // Get the segments from the temporary string,
        final String methodLabel = tempStringSegments[0].trim();
        final String apiName = tempStringSegments[1].trim();
        final String instanceName = tempStringSegments[2].trim();
        final String endpointName = tempStringSegments[3].trim();

        // Gets the request method based on the label.
        final DXHttpRequestMethod requestMethod = DXHttpRequestMethod.FromLabel(methodLabel);

        ///
        /// Get the configurations based on the initialization line.
        ///

        // Gets the http config apis.
        final HttpConfigApis httpConfigApis = config.httpConfigApis();

        // Gets the http config api based on the name.
        final DXHttpConfigApi httpConfigApi = httpConfigApis.getChildByName(apiName);
        if (httpConfigApi == null) {
            throw new RuntimeException("Could not find API with name " + apiName);
        }

        // Gets thr http config instances and endpoints.
        final HttpConfigInstances httpConfigInstances = httpConfigApi.instances();
        final HttpConfigEndpoints httpConfigEndpoints = httpConfigApi.endpoints();

        // Gets the http config instance based on the name.
        final DXHttpConfigInstance httpConfigInstance = httpConfigInstances.getChildByName(instanceName);
        if (httpConfigInstance == null) {
            throw new RuntimeException("Could not find instance with name " + instanceName + " in api " + apiName);
        }

        // Gets the http config endpoint based on the name.
        final DXHttpConfigEndpoint configEndpoint = httpConfigEndpoints.getChildByName(endpointName);
        if (configEndpoint == null) {
            throw new RuntimeException("Could not find endpoint with name " + endpointName + " in api " + apiName);
        }

        // Gets the http config request based on the method.
        final DXHttpConfigRequest httpConfigRequest = configEndpoint.getRequestByMethod(requestMethod);
        if (httpConfigRequest == null) {
            throw new RuntimeException("Could not find request with method " + requestMethod.Label + " in endpoint "
                    + endpointName + " in api " + apiName);
        }

        // Gets the http config request uri.
        final DXHttpConfigUri httpConfigUri = httpConfigRequest.Uri;

        ///
        /// Gets the remaining templating data based on the configuration.
        ///

        // gets the path substitutions if they are required by the configuration.
        HashMap<String, String> pathSubstitutionsSentByClient = null;
        if (httpConfigUri.path().shouldSubstitute()) {
            if ((tempString = proxyInputStreamReader.readStringUntilDoubleNewLine()) == null) {
                return null;
            }

            pathSubstitutionsSentByClient = HttpClientPairParser.parseFromLines(tempString.split("\\r?\\n"));
        }

        // Gets the query parameters if they are required by the configuration.
        HashMap<String, String> queryParametersSentByClient;
        if (!httpConfigUri.queryParameters().isEmpty()) {
            if ((tempString = proxyInputStreamReader.readStringUntilDoubleNewLine()) == null) {
                return null;
            }

            queryParametersSentByClient = HttpClientPairParser.parseFromLines(tempString.split("\\r?\\n"));
        } else {
            queryParametersSentByClient = null;
        }

        // Gets the headers if they are required by the configuration.
        HashMap<String, String> headersSentByClient = null;
        if (!httpConfigRequest.headers().isEmpty()) {
            if ((tempString = proxyInputStreamReader.readStringUntilDoubleNewLine()) == null) {
                return null;
            }

            headersSentByClient = HttpClientPairParser.parseFromLines(tempString.split("\\r?\\n"));
        }

        // Gets the body fieldsSentByClient if they are required by the configuration.
        HashMap<String, String> fieldsSentByClient = null;
        if (!httpConfigRequest.fields().isEmpty()) {
            if ((tempString = proxyInputStreamReader.readStringUntilDoubleNewLine()) == null) {
                return null;
            }

            fieldsSentByClient = HttpClientPairParser.parseFromLines(tempString.split("\\r?\\n"));
        }

        ///
        /// Constructs the HTTP request.
        ///

        // Creates the initial part of the URI, which does not include the query parameters.
        final var pathRenderer = new DXHttpPathTemplateRenderer(httpConfigUri.path());
        StringBuilder uriStringBuilder = new StringBuilder(httpConfigInstance.protocol() + "://" + httpConfigInstance.host()
                + ':' + httpConfigInstance.port() + pathRenderer.Render(pathSubstitutionsSentByClient));

        // Adds the query parameters to the request URI.
        if (!httpConfigUri.queryParameters().isEmpty()) {
            // The query parameters will never be null due to previous logic.
            assert queryParametersSentByClient != null;

            // Builds the query parameters.
            uriStringBuilder.append('?').append(httpConfigUri.queryParameters().children().values().stream().map(param -> {
                var value = param.value() != null ? param.value() : queryParametersSentByClient.get(param.key());

                if (value == null) {
                    throw new RuntimeException("Missing query parameter for key: " + param.key());
                }

                return URLEncoder.encode(param.key(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(value, StandardCharsets.UTF_8);
            }).collect(Collectors.joining("&")));
        }

        // Creates the request builder based on the built URI.
        var httpRequestBuilder = HttpRequest.newBuilder(URI.create(uriStringBuilder.toString()));

        // Sets the headers if required.
        if (!httpConfigRequest.headers().isEmpty()) {
            // Headers will never be null due to previous logic.
            assert headersSentByClient != null;

            // Sets all the headers.
            for (final var header : httpConfigRequest.Headers.Children.values()) {
                // Gets the value of the header.
                var value = headersSentByClient.get(header.name());
                if (value == null) {
                    value = header.value();
                }

                // If the value is still null throw a runtime exception.
                if (value == null) {
                    throw new RuntimeException("Missing value for header with key: "
                            + header.key());
                }

                // Adds the header to the request builder.
                httpRequestBuilder = httpRequestBuilder.header(header.key(), value);
            }
        }

        // Constructs the body publisher if needed.
        HttpRequest.BodyPublisher bodyPublisher = null;
        if (!httpConfigRequest.fields().isEmpty())
        {
            // Makes sure that the request method supports a body,
            if (httpConfigRequest.method() != DXHttpRequestMethod.Post && httpConfigRequest.method() != DXHttpRequestMethod.Put)
                throw new RuntimeException("Fields specified in request that does not support a body");

            // Encodes the fieldsSentByClient and creates the body publisher.
            final byte[] encodedFields = DXHttpFieldsEncoder.Encode(httpConfigRequest.Fields, fieldsSentByClient);
            bodyPublisher = HttpRequest.BodyPublishers.ofByteArray(encodedFields);

            // Adds the Content-Type header.
            httpRequestBuilder.header("Content-Type", httpConfigRequest.fields().format().mimeType());
        }

        // Sets the request method and the possible body publisher.
        switch (httpConfigRequest.method()) {
            case Get -> httpRequestBuilder = httpRequestBuilder.GET();
            case Post -> {
                httpRequestBuilder = httpRequestBuilder.POST(
                        Objects.requireNonNullElseGet(bodyPublisher, HttpRequest.BodyPublishers::noBody));
            }
            case Put -> httpRequestBuilder = httpRequestBuilder.PUT(
                    Objects.requireNonNullElseGet(bodyPublisher, HttpRequest.BodyPublishers::noBody));
            case Delete -> httpRequestBuilder = httpRequestBuilder.DELETE();
        }

        // Builds the http request.
        final HttpRequest httpRequest = httpRequestBuilder.build();

        // Returns the DX http request.
        return new HttpClientRequest(httpConfigRequest, httpRequest);
    }
}
