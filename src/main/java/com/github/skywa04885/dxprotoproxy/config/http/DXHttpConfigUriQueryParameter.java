package com.github.skywa04885.dxprotoproxy.config.http;

import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DXHttpConfigUriQueryParameter {
    public static final String TAG_NAME = "Parameter";
    public static final String KEY_ATTRIBUTE_NAME = "Key";
    public static final String VALUE_ATTRIBUTE_NAME = "Value";

    public final SimpleStringProperty Key;
    public final SimpleStringProperty Value;

    public DXHttpConfigUriQueryParameter(@NotNull String key, @Nullable String value) {
        Key = new SimpleStringProperty(null, "Sleutel", key);
        Value = new SimpleStringProperty(null, "Waarde", value);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(TAG_NAME);

        element.setAttribute(KEY_ATTRIBUTE_NAME, key());

        if (value() != null) {
            element.setAttribute(VALUE_ATTRIBUTE_NAME, value());
        }

        return element;
    }

    public static DXHttpConfigUriQueryParameter FromElement(final Element element) {
        if (!element.getTagName().equals(TAG_NAME)) throw new RuntimeException("Uri query parameter tag name mismatch");

        final String key = element.getAttribute(KEY_ATTRIBUTE_NAME).trim();
        if (key.isEmpty()) throw new RuntimeException("Key attribute of search query parameter missing");

        String value = element.getAttribute(VALUE_ATTRIBUTE_NAME).trim();
        value = value.isEmpty() ? null : value;

        return new DXHttpConfigUriQueryParameter(key, value);
    }

    @NotNull
    public String key() {
        return Key.get();
    }

    public void setKey(@NotNull  String key) {
        Key.set(key);
    }

    @Nullable
    public String value() {
        return Value.get();
    }

    public void setValue(@Nullable String value) {
        Value.set(value);
    }
}
