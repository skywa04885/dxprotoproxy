package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigField;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfigFields;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class DXHttpFieldsEncoder {
    public static byte[] Encode(final DXHttpConfigFields fields, final Map<String, String> Body) {
        switch (fields.Format.getValue()) {
            case JSON -> {
                final JSONObject jsonObject = new JSONObject();

                for (final DXHttpConfigField field : fields.Fields.values()) {
                    final String[] keys = field.Path.getValue().split("\\.");

                    JSONObject jsonSubObject = jsonObject;

                    for (var i = 0; i < keys.length; ++i) {
                        final var key = keys[i];

                        if (i + 1 == keys.length) {
                            if (jsonSubObject.has(key))
                                throw new RuntimeException("Key already associated with value: " + key);

                            final var value = field.Value.getValue() != null ? field.Value.getValue() : Body.get(field.Name.getValue());
                            if (value == null)
                                throw new RuntimeException("Required value not given in body for key: " + key);

                            jsonSubObject.put(key, value);
                            continue;
                        }

                        if (!jsonSubObject.has(key)) {
                            final var newJsonSubObject = new JSONObject();
                            jsonSubObject.put(key, newJsonSubObject);
                            jsonSubObject = newJsonSubObject;
                        } else {
                            final var t = jsonSubObject.get(key);
                            if (t instanceof JSONObject tt) jsonSubObject = tt;
                            else throw new RuntimeException("Invalid JSON paths given");
                        }
                    }
                }

                return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            }
            case FormURLEncoded -> {
                // TODO: Not a complete implementation, only basics, stuff like indexing is missing.
                return fields.Fields.values().stream().map(field -> {
                    final var value = field.Value.getValue() != null ? field.Value.getValue() : Body.get(field.Name.getValue());
                    if (value == null)
                        throw new RuntimeException("Required value not given in body for name: " + field.Name.getValue());

                    return URLEncoder.encode(field.Path.getValue(), StandardCharsets.UTF_8) + '='
                            + URLEncoder.encode(value, StandardCharsets.UTF_8);
                }).collect(Collectors.joining("&")).getBytes(StandardCharsets.UTF_8);
            }
            default -> throw new IllegalArgumentException("Invalid format in field");
        }
    }
}
