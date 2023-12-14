package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpFieldsFormat;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.DXHttpRequestMethod;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.ConfiguratorImageCache;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;

public class DXHttpConfigRequest implements IDXTreeItem {
    public static final String ELEMENT_TAG_NAME = "Request";
    public static final String METHOD_ATTRIBUTE_NAME = "Method";

    private @Nullable DXHttpConfigEndpoint parent;
    public final @NotNull DXHttpConfigUri Uri;
    public final @NotNull SimpleObjectProperty<DXHttpRequestMethod> Method;
    public final @NotNull DXHttpConfigHeaders Headers;
    public final @NotNull DXHttpConfigFields Fields;
    public final @NotNull DXHttpConfigResponses Responses;

    public DXHttpConfigRequest(@NotNull DXHttpConfigUri uri,
                               @NotNull DXHttpRequestMethod method,
                               @NotNull DXHttpConfigHeaders headers,
                               @NotNull DXHttpConfigFields fields,
                               @NotNull DXHttpConfigResponses responses) {
        Uri = uri;
        Method = new SimpleObjectProperty<>(null, "method", method);
        Headers = headers;
        Fields = fields;
        Responses = responses;
    }

    public @Nullable DXHttpConfigEndpoint parent() {
        return parent;
    }

    public void setParent(@Nullable DXHttpConfigEndpoint parent) {
        this.parent = parent;
    }

    public @NotNull DXHttpConfigFields fields() {
        return Fields;
    }

    public @NotNull DXHttpConfigHeaders headers() {
        return Headers;
    }

    public @NotNull DXHttpRequestMethod method() {
        return Method.getValue();
    }

    public void setMethod(@NotNull DXHttpRequestMethod method) {
        Method.set(method);
    }

    public SimpleObjectProperty<DXHttpRequestMethod> methodProperty() {
        return Method;
    }

    @NotNull
    public DXHttpConfigResponses responses() {
        return Responses;
    }

    @NotNull
    public DXHttpConfigUri uri() {
        return Uri;
    }

    @NotNull
    public ObservableValue<String> methodStringProperty() {
        return Method.map(DXHttpRequestMethod::label);
    }

    public static DXHttpConfigRequest FromElement(@NotNull Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME)) throw new RuntimeException("Tag name mismatch");

        final var uriElements = DXDomUtils.GetChildElementsWithTagName(element, DXHttpConfigUri.ELEMENT_TAG_NAME);
        if (uriElements.isEmpty()) throw new RuntimeException("Uri element is missing");
        else if (uriElements.size() > 1) throw new RuntimeException("Too many uri elements");
        final var uriElement = (Element) uriElements.get(0);

        final var uri = DXHttpConfigUri.FromElement(uriElement);

        final var methodString = element.getAttribute(METHOD_ATTRIBUTE_NAME);
        final var method = DXHttpRequestMethod.FromLabel(methodString);

        final var headersElements = DXDomUtils.GetChildElementsWithTagName(element, DXHttpConfigHeaders.ELEMENT_TAG_NAME);

        DXHttpConfigHeaders headers = null;

        if (headersElements.size() == 1) {
            final var headersElement = headersElements.get(0);
            headers = DXHttpConfigHeaders.FromElement(headersElement);
        } else if (headersElements.size() > 1) throw new RuntimeException("Too many headers elements");

        final List<Element> fieldsElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigFields.ELEMENT_TAG_NAME);

        if (headers == null) headers = new DXHttpConfigHeaders(new HashMap<>());

        DXHttpConfigFields fields;
        if (fieldsElements.size() == 1) {
            final var fieldsElement = (Element) fieldsElements.get(0);
            fields = DXHttpConfigFields.FromElement(fieldsElement);
        } else if (fieldsElements.size() > 1) {
            throw new RuntimeException("Too many fields elements");
        } else {
            fields = new DXHttpConfigFields();
        }

        final List<Element> responsesElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigResponses.ELEMENT_TAG_NAME);

        if (responsesElements.isEmpty())
            throw new RuntimeException("Responses element is missing");
        else if (responsesElements.size() > 1)
            throw new RuntimeException("Too many responses elements");

        final Element responsesElement = responsesElements.get(0);

        final var responses = DXHttpConfigResponses.FromElement(responsesElement);

        return new DXHttpConfigRequest(uri, method, headers, fields, responses);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(ELEMENT_TAG_NAME);

        element.setAttribute(METHOD_ATTRIBUTE_NAME, method().label());

        element.appendChild(uri().toElement(document));
        element.appendChild(responses().toElement(document));
        element.appendChild(fields().toElement(document));
        element.appendChild(headers().toElement(document));

        return element;
    }

    @Override
    public Node treeItemGraphic() {
        return new ImageView(ConfiguratorImageCache.instance().read("icons/send.png"));
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return Bindings.createStringBinding(() -> method().label() + " " + uri().path().stringOfSegments(), methodProperty(), uri().pathProperty());
    }
}
