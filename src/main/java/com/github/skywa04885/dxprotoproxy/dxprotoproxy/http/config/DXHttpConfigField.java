package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import javafx.beans.property.SimpleStringProperty;
import org.w3c.dom.Element;

public class DXHttpConfigField {
    public static final String ELEMENT_TAG_NAME = "Field";
    public static final String PATH_ATTRIBUTE_NAME = "Path";
    public static final String NAME_ATTRIBUTE_NAME = "Name";
    public static final String VALUE_ATTRIBUTE_NAME = "Value";

    public final SimpleStringProperty Path;
    public final SimpleStringProperty Name;
    public final SimpleStringProperty Value;

    public DXHttpConfigField(final String path, final String name, final String value) {
        Path = new SimpleStringProperty(null, "Pad", path);
        Name = new SimpleStringProperty(null, "Naam", name);
        Value = new SimpleStringProperty(null, "Waarde", value);
    }

    public String path() {
        return Path.get();
    }

    public String name() {
        return Name.get();
    }

    public String value() {
        return Value.get();
    }

    public void setPath(String path) {
        this.Path.set(path);
    }

    public void setValue(String value) {
        this.Value.set(value);
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public static DXHttpConfigField FromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME))
            throw new RuntimeException("Tag name mismatch, expected " + ELEMENT_TAG_NAME + ", got: "
                    + element.getTagName());

        final String path = element.getAttribute(PATH_ATTRIBUTE_NAME).trim();
        if (path.isEmpty()) throw new RuntimeException("Path attribute missing from body field");

        final String name = element.getAttribute(NAME_ATTRIBUTE_NAME).trim();
        if (name.isEmpty()) throw new RuntimeException("Name attribute missing from body field");

        System.out.println(name);

        String value = element.getAttribute(VALUE_ATTRIBUTE_NAME).trim();
        value = value.isEmpty() ? null : value;

        return new DXHttpConfigField(path, name, value);
    }
}
