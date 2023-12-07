package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class DXHttpConfigHeaders {
    public static final String ELEMENT_TAG_NAME = "Headers";

    public final SimpleMapProperty<String, DXHttpConfigHeader> Children;

    public DXHttpConfigHeaders(final Map<String, DXHttpConfigHeader> children) {
        Children = new SimpleMapProperty<>(null, "Headers", FXCollections.observableMap(children));
    }

    public DXHttpConfigHeaders() {
        this(new HashMap<>());
    }

    public Map<String, DXHttpConfigHeader> children() {
        return Children;
    }

    public static DXHttpConfigHeaders FromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME))
            throw new RuntimeException("Element tag name mismatch, expected: " + ELEMENT_TAG_NAME + ", got: " +
                    element.getTagName());

        final var headerElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigHeader.ELEMENT_TAG_NAME);

        final var headers = new HashMap<String, DXHttpConfigHeader>();

        for (final Element value : headerElements) {
            final var header = DXHttpConfigHeader.FromElement(value);

            if (headers.containsKey(header.Key.getValue()))
                throw new RuntimeException("Duplicate header with key: " + header.Key.getValue());

            headers.put(header.Key.getValue(), header);
        }

        return new DXHttpConfigHeaders(headers);
    }
}
