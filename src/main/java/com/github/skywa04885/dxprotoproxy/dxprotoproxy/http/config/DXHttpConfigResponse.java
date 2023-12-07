package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.ConfiguratorImageCache;
import io.swagger.v3.oas.models.headers.Header;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.w3c.dom.Element;

import java.util.List;

public class DXHttpConfigResponse implements IDXTreeItem {
    public static final String ELEMENT_NAME = "Response";
    public static final String CODE_ATTRIBUTE_NAME = "Code";

    public final DXHttpConfigResponses parent;
    public final SimpleIntegerProperty Code;
    public final DXHttpConfigFields Fields;
    public final DXHttpConfigHeaders Headers;

    public DXHttpConfigResponse(DXHttpConfigResponses parent, int code, DXHttpConfigFields fields,
                                DXHttpConfigHeaders headers) {
        this.parent = parent;
        Code = new SimpleIntegerProperty(null, "Code", code);
        Fields = fields;
        Headers = headers;
    }

    public DXHttpConfigResponses parent() {
        return parent;
    }

    public int code() {
        return Code.get();
    }

    public void setCode(int code) {
        this.Code.set(code);
    }

    public DXHttpConfigHeaders headers() {
        return Headers;
    }

    public DXHttpConfigFields fields() {
        return Fields;
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

    @Override
    public Node treeItemGraphic() {
        return new ImageView(ConfiguratorImageCache.instance().read("icons/reply.png"));
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return Code.asString();
    }
}
