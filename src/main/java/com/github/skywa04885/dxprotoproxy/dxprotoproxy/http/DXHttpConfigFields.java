package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class DXHttpConfigFields {
    public static final String ELEMENT_TAG_NAME = "Fields";
    public static final String FORMAT_ATTRIBUTE_NAME = "Format";

    public final SimpleMapProperty<String, DXHttpConfigField> Fields;
    public final SimpleObjectProperty<DXHttpFieldsFormat> Format;

    public DXHttpConfigFields(final Map<String, DXHttpConfigField> fields, final DXHttpFieldsFormat format) {
        Fields = new SimpleMapProperty<>(null, "Velden", FXCollections.observableMap(fields));
        Format = new SimpleObjectProperty<>(null, "Formaat", format);
    }

    public DXHttpConfigField GetFieldByName(final String name) {
        return Fields.get(name);
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
