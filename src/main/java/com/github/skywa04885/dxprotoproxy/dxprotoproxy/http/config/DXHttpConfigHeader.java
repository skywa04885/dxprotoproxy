package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import javafx.beans.property.SimpleStringProperty;
import org.w3c.dom.Element;

public class DXHttpConfigHeader {
    public static final String ELEMENT_TAG_NAME = "Header";
    public static final String KEY_ATTRIBUTE_NAME = "Key";
    public static final String VALUE_ATTRIBUTE_NAME = "Value";
    public static final String NAME_ATTRIBUTE_NAME = "Name";

    public final SimpleStringProperty Key;
    public final SimpleStringProperty Value;
    public final SimpleStringProperty Name;

    public DXHttpConfigHeader(final String key, final String value, final String name) {
        Key = new SimpleStringProperty(null, "Sleutel", key);
        Value = new SimpleStringProperty(null, "Waarde", value);
        Name = new SimpleStringProperty(null, "Naam", name);
    }

    public String key() {
        return Key.get();
    }

    public String value() {
        return Value.get();
    }

    public String name() {
        return Name.get();
    }

    public void setKey(String key) {
        this.Key.set(key);
    }

    public void setValue(String value) {
        this.Value.set(value);
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public static DXHttpConfigHeader FromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME))
            throw new RuntimeException("Tag name mismatch");

        final String key = element.getAttribute(KEY_ATTRIBUTE_NAME).trim();
        if (key.isEmpty()) throw new RuntimeException("Key attribute missing");

        String value = element.getAttribute(VALUE_ATTRIBUTE_NAME).trim();
        value = value.isEmpty() ? null : value;

        String name = element.getAttribute(NAME_ATTRIBUTE_NAME).trim();
        name = name.isEmpty() ? null : name;

        return new DXHttpConfigHeader(key, value, name);
    }
}
