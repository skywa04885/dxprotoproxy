package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.client;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXJsonUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpClientResponseFactory {
    public static HttpClientResponse createFromHttpResponse(final @NotNull HttpResponse<byte[]> httpResponse,
                                                            final @NotNull DXHttpConfigRequest httpConfigRequest) {
        final @NotNull DXHttpConfigResponses configResponses = httpConfigRequest.responses();

        // Gets the http config response based on the http response code.
        final DXHttpConfigResponse httpConfigResponse =
                configResponses.getByCode(httpResponse.statusCode());
        if (httpConfigResponse == null) {
            throw new RuntimeException("No config response found for status code: " +
                    httpResponse.statusCode());
        }

        // Gets the response body.
        Map<String, String> body = null;
        if (httpConfigResponse.fields().format() == DXHttpFieldsFormat.JSON) {
            body = new HashMap<>();

            // Parses the json object.
            final var jsonObject = new JSONObject(new String(httpResponse.body(), StandardCharsets.UTF_8));

            // Loops over all the fields and gets the required fields from the body.
            for (final DXHttpConfigField configField : httpConfigResponse.Fields.Fields.values()) {
                // Gets the value of the field, which is the one that is supplied in the response, and if that does not
                // exist the default value given in the config.
                final Object valueInsideJsonObject = DXJsonUtils.GetRecursive(jsonObject, configField.path());
                final String value = valueInsideJsonObject != null
                        ? valueInsideJsonObject.toString()
                        : configField.value();

                // If the value still was not found throw a runtime exception.
                if (value == null)
                    throw new RuntimeException("Could not find value for field with path " + configField.path());

                // Insert the value and the name in the response body.
                body.put(configField.name(), value);
            }
        }

        // Gets all the headers of interest.
        Map<String, String> headers = null;
        if (!httpConfigResponse.headers().isEmpty()) {
            headers = new HashMap<>();

            for (final DXHttpConfigHeader header : httpConfigResponse.headers().children().values()) {
                // Gets the header value.
                final var optionalValue = httpResponse.headers().firstValue(header.key());
                final var value = optionalValue.orElseGet(() -> header.value());

                // If the value is still null throw a runtime error.
                if (value == null)
                    throw new RuntimeException("Missing value for header with key: " + header.key());

                // Puts the header value together with its key (or name) in the headers.
                headers.put(header.name(), value);
            }
        }

        return new HttpClientResponse(httpConfigResponse.code(), headers, body);
    }
}
