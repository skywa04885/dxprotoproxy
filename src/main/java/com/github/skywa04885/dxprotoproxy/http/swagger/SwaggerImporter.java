package com.github.skywa04885.dxprotoproxy.http.swagger;

import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.config.http.*;
import com.github.skywa04885.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.http.DXHttpPathTemplateParser;
import com.github.skywa04885.dxprotoproxy.http.DXHttpRequestMethod;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import io.swagger.v3.parser.util.SchemaTypeUtil;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SwaggerImporter {
    private final Logger logger = Logger.getLogger(SwaggerImporter.class.getName());

    private List<DXHttpConfigField> parseFields(final Schema<?> schema, final String path) {
        if (schema.getType() == null) return Collections.emptyList();

        System.out.println(path);

        if (schema.getType().equalsIgnoreCase(SchemaTypeUtil.STRING_TYPE)
                || schema.getType().equalsIgnoreCase(SchemaTypeUtil.PASSWORD_FORMAT)
                || schema.getType().equalsIgnoreCase(SchemaTypeUtil.UUID_FORMAT)) {
            logger.info("Found field of type string");
        } else if (schema.getType().equalsIgnoreCase(SchemaTypeUtil.INTEGER_TYPE)
                || schema.getType().equalsIgnoreCase(SchemaTypeUtil.INTEGER32_FORMAT)
                || schema.getType().equalsIgnoreCase(SchemaTypeUtil.INTEGER64_FORMAT)) {
            logger.info("Found field of type integer");
        } else if (schema.getType().equalsIgnoreCase(SchemaTypeUtil.FLOAT_FORMAT)
                || schema.getType().equalsIgnoreCase(SchemaTypeUtil.DOUBLE_FORMAT)) {
            logger.info("Found field of type double");
        } else if (schema.getType().equalsIgnoreCase(SchemaTypeUtil.DATE_TIME_FORMAT)) {
            logger.info("Found field of type date time");
        } else if (schema.getType().equalsIgnoreCase(SchemaTypeUtil.NUMBER_TYPE)) {
            logger.info("Found field of number type");
        } else if (schema.getType().equalsIgnoreCase(SchemaTypeUtil.OBJECT_TYPE)) {
            return schema
                    .getProperties()
                    .entrySet()
                    .stream()
                    .map(entry -> parseFields(entry.getValue(), path.isEmpty() ? entry.getKey() : path + "." + entry.getKey()))
                    .flatMap(Collection::stream).toList();
        }

        return Collections.emptyList();
    }

    private DXHttpConfigRequest parsePathOperation(DXHttpConfigUri uri, DXHttpRequestMethod method, PathItem pathItem, Operation operation) {
        final var requestHeaders = new HashMap<String, DXHttpConfigHeader>();
        final var requestFields = new HashMap<String, DXHttpConfigField>();
        final var requestUriQueryParams = new HashMap<String, DXHttpConfigUriQueryParameter>();

        operation.getParameters()
                .stream()
                .filter(param -> param.getIn().equalsIgnoreCase("header"))
                .forEach(param -> {
                    final Schema<?> schema = param.getSchema();

                    final String key = param.getName();
                    final String value = schema.getDefault().toString();
                    final String name = headerNameFromHeaderKey(key);

                    requestHeaders.put(name, new DXHttpConfigHeader(key, value, name));
                });

        operation.getParameters()
                .stream()
                .filter(param -> param.getIn().equalsIgnoreCase("query"))
                .forEach(param -> {
                    final Schema<?> schema = param.getSchema();

                    final String key = param.getName();
                    final String value = schema.getDefault().toString();

                    requestUriQueryParams.put(key, new DXHttpConfigUriQueryParameter(key, value));
                });

        if (operation.getRequestBody() != null) {
            for (final Map.Entry<String, MediaType> entry : operation.getRequestBody().getContent().entrySet()) {
                final var mimeType = DXHttpFieldsFormat.GetByMimeType(entry.getKey());

                if (mimeType == null) {
                    continue;
                }

                final MediaType mediaType = entry.getValue();

                parseFields(mediaType.getSchema(), "");

                break;
            }
        }

//        return new DXHttpConfigRequest(uri, method, );
        return null;
    }

    /**
     * Generates a header name from a header key.
     *
     * @param key the header key.
     * @return the generated header name.
     */
    private String headerNameFromHeaderKey(String key) {
        return Arrays.stream(key.split("_+|-+"))
                .map(String::toLowerCase)
                .map(String::trim)
                .map(v -> v.replaceAll("[^a-z0-9]*", ""))
                .filter(seg -> !seg.isBlank())
                .map(v -> v.length() > 4 ? v.substring(0, 4) : v)
                .map(v -> v.substring(0, 1).toUpperCase() + v.substring(1))
                .collect(Collectors.joining());
    }

    /**
     * Generates an endpoint name based on the given path.
     *
     * @param path the path of the endpoint.
     * @return the generated endpoint name.
     */
    private String endpointNameFromPath(String path) {
        return Arrays.stream(path.split("/+|_+|-+"))
                .map(String::toLowerCase)
                .map(String::trim)
                .map(v -> v.replaceAll("[^a-z0-9]*", ""))
                .filter(seg -> !seg.isBlank())
                .map(v -> v.length() > 4 ? v.substring(0, 4) : v)
                .map(v -> v.substring(0, 1).toUpperCase() + v.substring(1))
                .collect(Collectors.joining());
    }

    private void parsePath(String path, PathItem pathItem) {
        final String endpointName = endpointNameFromPath(path);

        logger.info("Generated name '" + endpointName + "' for path '" + path + "'");

        final var httpPathTemplateParser = new DXHttpPathTemplateParser();
        final var httpPathTemplate = httpPathTemplateParser.parse(path);

        final var httpConfigUriQueryParameters = new HashMap<String, DXHttpConfigUriQueryParameter>();

        final var httpConfigQueryParameters = new HttpConfigQueryParameters(httpConfigUriQueryParameters);
        final var httpConfigUri = new DXHttpConfigUri(httpPathTemplate, httpConfigQueryParameters);

        final var endpointRequests = new HashMap<DXHttpRequestMethod, DXHttpConfigRequest>();

        if (pathItem.getGet() != null) {
            endpointRequests.put(DXHttpRequestMethod.Get, parsePathOperation(httpConfigUri, DXHttpRequestMethod.Get, pathItem, pathItem.getGet()));
        }

        if (pathItem.getPost() != null) {
            endpointRequests.put(DXHttpRequestMethod.Post, parsePathOperation(httpConfigUri, DXHttpRequestMethod.Get, pathItem, pathItem.getPost()));
        }

    }

    public void importFromFile(File file) {

        final var parseOptions = new ParseOptions();
        parseOptions.setResolve(true);
        parseOptions.setResolveFully(true);
        parseOptions.setResolveCombinators(true);
        parseOptions.setResolveRequestBody(true);

        final var openApiParser = new OpenAPIV3Parser();
        final SwaggerParseResult swaggerParseResult = openApiParser.readLocation(file.getPath(), null, parseOptions);
        final OpenAPI openAPI = swaggerParseResult.getOpenAPI();

        if (openAPI == null) {
            throw new RuntimeException("Failed to parse OpenAPI file");
        }

        openAPI.getPaths().forEach((path, pathItem) -> {
            parsePath(path, pathItem);
        });

    }
}
