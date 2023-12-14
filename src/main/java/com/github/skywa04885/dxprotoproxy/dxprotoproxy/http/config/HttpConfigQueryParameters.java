package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class HttpConfigQueryParameters {
    public static final String ELEMENT_TAG_NAME = "QueryParameters";

    public final @NotNull SimpleMapProperty<String, DXHttpConfigUriQueryParameter> children;

    public HttpConfigQueryParameters(@NotNull Map<String, DXHttpConfigUriQueryParameter> children) {
        this.children = new SimpleMapProperty<>(null, "children",
                FXCollections.observableMap(children));
    }

    public HttpConfigQueryParameters() {
        this(new HashMap<>());
    }

    public @NotNull Map<String, DXHttpConfigUriQueryParameter> children() {
        return children.get();
    }

    public @NotNull SimpleMapProperty<String, DXHttpConfigUriQueryParameter> childrenProperty() {
        return children;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(ELEMENT_TAG_NAME);

        children().values().forEach(httpConfigQueryParameter ->
                element.appendChild(httpConfigQueryParameter.toElement(document)));

        return element;
    }

    public static HttpConfigQueryParameters fromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME)) {
            throw new RuntimeException("Tag name mismatch, expected " + ELEMENT_TAG_NAME
                    + " but got " + element.getTagName());
        }

        final var queryParameterElements = element.getElementsByTagName(
                DXHttpConfigUriQueryParameter.TAG_NAME);

        final var httpConfigQueryParameters = new HttpConfigQueryParameters();

        for (var i = 0; i < queryParameterElements.getLength(); ++i) {
            final var queryParameterElement = (Element) queryParameterElements.item(i);
            final var queryParameter = DXHttpConfigUriQueryParameter.FromElement(
                    queryParameterElement);

            if (httpConfigQueryParameters.children().containsKey(queryParameter.key())) {
                throw new RuntimeException("Duplicate query parameter key: " +
                        queryParameter.key());
            }

            httpConfigQueryParameters.children().put(queryParameter.key(), queryParameter);
        }

        return httpConfigQueryParameters;
    }
}