package com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.http;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpPathTemplate;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpPathTemplateParser;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DXHttpConfigUri {
    public static final String ELEMENT_TAG_NAME = "Uri";
    public static final String PATH_ATTRIBUTE_NAME = "Path";

    public final @NotNull SimpleObjectProperty<DXHttpPathTemplate> Path;
    public final @NotNull HttpConfigQueryParameters queryParameters;

    public DXHttpConfigUri(@NotNull DXHttpPathTemplate path, @NotNull HttpConfigQueryParameters queryParameters) {
        Path = new SimpleObjectProperty<>(null, "path", path);
        this.queryParameters = queryParameters;
    }

    public DXHttpPathTemplate path() {
        return Path.getValue();
    }

    public void setPath(DXHttpPathTemplate pathTemplate) {
        Path.set(pathTemplate);
    }

    public SimpleObjectProperty<DXHttpPathTemplate> pathProperty() {
        return Path;
    }

    public @NotNull HttpConfigQueryParameters queryParameters() {
        return queryParameters;
    }

    public static DXHttpConfigUri FromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME)) throw new RuntimeException("Tag name mismatch");

        final var pathString = element.getAttribute(PATH_ATTRIBUTE_NAME).trim();
        if (pathString.isEmpty()) throw new RuntimeException("Path attribute is missing");

        final var pathTemplateParser = new DXHttpPathTemplateParser();
        final var pathTemplate = pathTemplateParser.parse(pathString);

        final var queryParametersElements = element.getElementsByTagName(HttpConfigQueryParameters.ELEMENT_TAG_NAME);
        if (queryParametersElements.getLength() == 0) throw new RuntimeException("Query parameters element missing");
        else if (queryParametersElements.getLength() > 1) throw new RuntimeException("Too many query parameters elements");

        final var queryParametersElement = (Element) queryParametersElements.item(0);

        final var httpConfigQueryParameters = HttpConfigQueryParameters.fromElement(queryParametersElement);

        return new DXHttpConfigUri(pathTemplate, httpConfigQueryParameters);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(ELEMENT_TAG_NAME);

        element.setAttribute(PATH_ATTRIBUTE_NAME, path().stringOfSegments());
        element.appendChild(queryParameters().toElement(document));

        return element;
    }
}
