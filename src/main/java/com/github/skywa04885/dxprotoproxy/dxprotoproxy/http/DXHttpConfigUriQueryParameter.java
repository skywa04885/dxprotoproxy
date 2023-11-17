package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import javafx.beans.property.SimpleStringProperty;
import org.w3c.dom.Element;

public class DXHttpConfigUriQueryParameter {
    public static final String TAG_NAME = "Parameter";
    public static final String KEY_ATTRIBUTE_NAME = "Key";
    public static final String VALUE_ATTRIBUTE_NAME = "Value";

    public final SimpleStringProperty Key;
    public final SimpleStringProperty Value;

    public DXHttpConfigUriQueryParameter(final String key, final String value) {
        Key = new SimpleStringProperty(null, "Sleutel", key);
        Value = new SimpleStringProperty(null, "Waarde", value);
    }

    public static DXHttpConfigUriQueryParameter FromElement(final Element element) {
        if (!element.getTagName().equals(TAG_NAME)) throw new RuntimeException("Uri query parameter tag name mismatch");

        final String key = element.getAttribute(KEY_ATTRIBUTE_NAME).trim();
        if (key.isEmpty()) throw new RuntimeException("Key attribute of search query parameter missing");

        String value = element.getAttribute(VALUE_ATTRIBUTE_NAME).trim();
        value = value.isEmpty() ? null : value;

        return new DXHttpConfigUriQueryParameter(key, value);
    }
}
