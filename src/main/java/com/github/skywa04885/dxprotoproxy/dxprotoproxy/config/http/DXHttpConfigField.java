package com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http;

import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DXHttpConfigField {
    public static final String ELEMENT_TAG_NAME = "Field";
    public static final String PATH_ATTRIBUTE_NAME = "Path";
    public static final String NAME_ATTRIBUTE_NAME = "Name";
    public static final String VALUE_ATTRIBUTE_NAME = "Value";

    public final @NotNull SimpleStringProperty Path;
    public final @NotNull SimpleStringProperty Name;
    public final @NotNull SimpleStringProperty Value;

    public DXHttpConfigField(@NotNull String path, @NotNull String name, @Nullable String value) {
        Path = new SimpleStringProperty(null, "Pad", path);
        Name = new SimpleStringProperty(null, "Naam", name);
        Value = new SimpleStringProperty(null, "Waarde", value);
    }

    public @NotNull String path() {
        return Path.get();
    }

    public @NotNull String name() {
        return Name.get();
    }

    public @Nullable String value() {
        return Value.get();
    }

    public void setPath(@NotNull String path) {
        this.Path.set(path);
    }

    public void setValue(@NotNull String value) {
        this.Value.set(value);
    }

    public void setName(@Nullable String name) {
        this.Name.set(name);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(ELEMENT_TAG_NAME);

        element.setAttribute(PATH_ATTRIBUTE_NAME, path());
        element.setAttribute(NAME_ATTRIBUTE_NAME, name());

        if (value() != null) {
            element.setAttribute(VALUE_ATTRIBUTE_NAME, value());
        }

        return element;
    }

    public static DXHttpConfigField FromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME)) {
            throw new RuntimeException("Tag name mismatch, expected " + ELEMENT_TAG_NAME + ", got: "
                    + element.getTagName());
        }

        final String path = element.getAttribute(PATH_ATTRIBUTE_NAME).trim();
        if (path.isEmpty()) {
            throw new RuntimeException("Path attribute missing from body field");
        }

        final String name = element.getAttribute(NAME_ATTRIBUTE_NAME).trim();
        if (name.isEmpty()) {
            throw new RuntimeException("Name attribute missing from body field");
        }

        String value = element.getAttribute(VALUE_ATTRIBUTE_NAME).trim();
        value = value.isEmpty() ? null : value;

        return new DXHttpConfigField(path, name, value);
    }
}
