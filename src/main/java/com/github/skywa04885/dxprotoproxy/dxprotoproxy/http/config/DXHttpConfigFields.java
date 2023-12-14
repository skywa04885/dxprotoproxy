package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class DXHttpConfigFields {
    public static final String ELEMENT_TAG_NAME = "Fields";
    public static final String FORMAT_ATTRIBUTE_NAME = "Format";

    public final SimpleMapProperty<String, DXHttpConfigField> Fields;
    public final SimpleObjectProperty<DXHttpFieldsFormat> Format;

    public DXHttpConfigFields(final Map<String, DXHttpConfigField> fields, final DXHttpFieldsFormat format) {
        Fields = new SimpleMapProperty<>(null, "Fields", FXCollections.observableMap(fields));
        Format = new SimpleObjectProperty<>(null, "Format", format);
    }

    public DXHttpConfigFields() {
        this(new HashMap<>(), DXHttpFieldsFormat.None);
    }

    public Map<String, DXHttpConfigField> children() {
        return Fields;
    }

    public void setFormat(DXHttpFieldsFormat format) {
        this.Format.set(format);
    }

    public DXHttpFieldsFormat format() {
        return Format.get();
    }

    public  SimpleObjectProperty<DXHttpFieldsFormat> formatProperty() {
        return Format;
    }

    public DXHttpConfigField GetFieldByName(final String name) {
        return Fields.get(name);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(ELEMENT_TAG_NAME);

        element.setAttribute(FORMAT_ATTRIBUTE_NAME, format().name());

        Fields.values().forEach(httpConfigField ->
                element.appendChild(httpConfigField.toElement(document)));

        return element;
    }

    public static DXHttpConfigFields FromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME))
            throw new RuntimeException("Tag name mismatch, expected: " + ELEMENT_TAG_NAME + ", got: "
                + element.getTagName());

        final var fieldsElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigField.ELEMENT_TAG_NAME);

        final var fields = new HashMap<String, DXHttpConfigField>();

        for (final Element fieldElement : fieldsElements) {
            final var field = DXHttpConfigField.FromElement(fieldElement);
            if (fields.containsKey(field.Name.getValue())) throw new RuntimeException("Duplicate name in fields: " + field.Name.getValue());
            fields.put(field.Name.getValue(), field);
        }

        final var formatName = element.getAttribute(FORMAT_ATTRIBUTE_NAME).trim();
        final var format = DXHttpFieldsFormat.GetByName(formatName);

        return new DXHttpConfigFields(fields, format);
    }
}
