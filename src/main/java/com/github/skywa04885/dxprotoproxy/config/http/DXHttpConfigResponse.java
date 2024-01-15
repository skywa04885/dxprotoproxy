package com.github.skywa04885.dxprotoproxy.config.http;

import com.github.skywa04885.dxprotoproxy.configurator.ConfiguratorImageCache;
import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class DXHttpConfigResponse {
    public static final String ELEMENT_NAME = "Response";
    public static final String CODE_ATTRIBUTE_NAME = "Code";

    public final @NotNull DXHttpConfigResponses parent;
    public final @NotNull SimpleIntegerProperty Code;
    public final @NotNull DXHttpConfigFields Fields;
    public final @NotNull DXHttpConfigHeaders Headers;

    public DXHttpConfigResponse(@Nullable DXHttpConfigResponses parent, int code, @NotNull DXHttpConfigFields fields,
                                @NotNull DXHttpConfigHeaders headers) {
        this.parent = parent;
        Code = new SimpleIntegerProperty(null, "Code", code);
        Fields = fields;
        Headers = headers;
    }

    public @Nullable DXHttpConfigResponses parent() {
        return parent;
    }

    public int code() {
        return Code.get();
    }

    public void setCode(int code) {
        this.Code.set(code);
    }

    public @NotNull DXHttpConfigHeaders headers() {
        return Headers;
    }

    public @NotNull DXHttpConfigFields fields() {
        return Fields;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(ELEMENT_NAME);

        element.setAttribute(CODE_ATTRIBUTE_NAME, Integer.toString(code()));

        element.appendChild(fields().toElement(document));
        element.appendChild(headers().toElement(document));

        return element;
    }

    public static DXHttpConfigResponse FromElement(DXHttpConfigResponses parent, Element element) {
        final String codeString = element.getAttribute(CODE_ATTRIBUTE_NAME).trim();
        if (codeString.isEmpty())
            throw new RuntimeException("Code is missing from response");

        int code;

        try {
            code = Integer.parseInt(codeString);
        } catch (final NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid code given");
        }

        final var headersElements = DXDomUtils.GetChildElementsWithTagName(element, DXHttpConfigHeaders.ELEMENT_TAG_NAME);
        DXHttpConfigHeaders headers;

        if (headersElements.size() == 1) {
            final var headersElement = headersElements.get(0);
            headers = DXHttpConfigHeaders.FromElement(headersElement);
        } else if (headersElements.size() > 1) {
            throw new RuntimeException("Too many headers elements");
        } else {
            headers = new DXHttpConfigHeaders();

        }

        final List<Element> fieldsElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigFields.ELEMENT_TAG_NAME);
        DXHttpConfigFields configFields;

        if (fieldsElements.size() == 1) {
            final Element fieldsElement = fieldsElements.get(0);
            configFields = DXHttpConfigFields.FromElement(fieldsElement);
        } else if (fieldsElements.size() > 1) {
            throw new RuntimeException("Too many fields elements specified for response");
        } else {
            configFields = new DXHttpConfigFields();
        }

        return new DXHttpConfigResponse(parent, code, configFields, headers);
    }
}
