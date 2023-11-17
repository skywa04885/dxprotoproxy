package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.configurator.DXConfiguratorImageCache;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;

public class DXHttpConfigRequest implements IDXTreeItem {
    public static final String ELEMENT_TAG_NAME = "Request";
    public static final String METHOD_ATTRIBUTE_NAME = "Method";

    public final DXHttpConfigUri Uri;
    public final SimpleObjectProperty<DXHttpRequestMethod> Method;
    public final DXHttpConfigHeaders Headers;
    public final DXHttpConfigFields Fields;
    public final DXHttpConfigResponses Responses;

    public DXHttpConfigRequest(@NotNull final DXHttpConfigUri uri,
                               @NotNull final DXHttpRequestMethod method,
                               @NotNull final DXHttpConfigHeaders headers,
                               @NotNull final DXHttpConfigFields fields,
                               @NotNull final DXHttpConfigResponses responses) {
        Uri = uri;
        Method = new SimpleObjectProperty<>(null, "method", method);
        Headers = headers;
        Fields = fields;
        Responses = responses;
    }

    public DXHttpRequestMethod method() {
        return Method.getValue();
    }

    public SimpleObjectProperty<DXHttpRequestMethod> methodProperty() {
        return Method;
    }

    public DXHttpConfigUri uri() {
        return Uri;
    }

    public ObservableValue<String> methodStringProperty() {
        return Method.map(DXHttpRequestMethod::label);
    }

    public static DXHttpConfigRequest FromElement(final Element element) {
        if (!element.getTagName().equals(ELEMENT_TAG_NAME)) throw new RuntimeException("Tag name mismatch");

        final var uriElements = DXDomUtils.GetChildElementsWithTagName(element, DXHttpConfigUri.ELEMENT_TAG_NAME);
        if (uriElements.size() == 0) throw new RuntimeException("Uri element is missing");
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

        DXHttpConfigFields fields = null;

        if (fieldsElements.size() == 1) {
            final var fieldsElement = (Element) fieldsElements.get(0);
            fields = DXHttpConfigFields.FromElement(fieldsElement);
        } else if (fieldsElements.size() > 1) throw new RuntimeException("Too many fields elements");

        if (fields == null) fields = new DXHttpConfigFields(new HashMap<>(), DXHttpFieldsFormat.JSON);

        final List<Element> responsesElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigResponses.ELEMENT_TAG_NAME);

        if (responsesElements.size() == 0)
            throw new RuntimeException("Responses element is missing");
        else if (responsesElements.size() > 1)
            throw new RuntimeException("Too many responses elements");

        final Element responsesElement = responsesElements.get(0);

        final var responses = DXHttpConfigResponses.FromElement(responsesElement);

        return new DXHttpConfigRequest(uri, method, headers, fields, responses);
    }

    @Override
    public Node treeItemGraphic() {
        return new ImageView(DXConfiguratorImageCache.instance().read("icons/bolt_FILL0_wght400_GRAD0_opsz24.png"));
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return Bindings.createStringBinding(() -> method().label() + " " + uri().path().stringOfSegments(), methodProperty(), uri().pathProperty());
    }
}
