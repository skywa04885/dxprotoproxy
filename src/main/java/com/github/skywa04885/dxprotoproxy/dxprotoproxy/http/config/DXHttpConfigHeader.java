package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DXHttpConfigHeader {
    public static final String ELEMENT_TAG_NAME = "Header";
    public static final String KEY_ATTRIBUTE_NAME = "Key";
    public static final String VALUE_ATTRIBUTE_NAME = "Value";
    public static final String NAME_ATTRIBUTE_NAME = "Name";

    public final @NotNull SimpleStringProperty Key;
    public final @NotNull SimpleStringProperty Value;
    public final @NotNull SimpleStringProperty Name;

    public DXHttpConfigHeader(@NotNull String key, @Nullable String value, @NotNull String name) {
        Key = new SimpleStringProperty(null, "Sleutel", key);
        Value = new SimpleStringProperty(null, "Waarde", value);
        Name = new SimpleStringProperty(null, "Naam", name);
    }

    public @NotNull String key() {
        return Key.get();
    }

    public @Nullable String value() {
        return Value.get();
    }

    public @NotNull String name() {
        return Name.get();
    }

    public void setKey(@NotNull String key) {
        this.Key.set(key);
    }

    public void setValue(@Nullable String value) {
        this.Value.set(value);
    }

    public void setName(@NotNull String name) {
        this.Name.set(name);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(ELEMENT_TAG_NAME);

        element.setAttribute(NAME_ATTRIBUTE_NAME, name());
        element.setAttribute(KEY_ATTRIBUTE_NAME, key());

        if (value() != null) {
            element.setAttribute(VALUE_ATTRIBUTE_NAME, value());
        }

        return element;
    }

    public static DXHttpConfigHeader FromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME))
            throw new RuntimeException("Tag name mismatch");

        final String key = element.getAttribute(KEY_ATTRIBUTE_NAME);
        if (key.isBlank()) {
            throw new RuntimeException("Key attribute missing");
        }


        final String name = element.getAttribute(NAME_ATTRIBUTE_NAME);
        if (name.isBlank()) {
            throw new RuntimeException("Name attribute missing");
        }

        String value = element.getAttribute(VALUE_ATTRIBUTE_NAME);
        value = value.isBlank() ? null : value;

        return new DXHttpConfigHeader(key, value, name);
    }
}
