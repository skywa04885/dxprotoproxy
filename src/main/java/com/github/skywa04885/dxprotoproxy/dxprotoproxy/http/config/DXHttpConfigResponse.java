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

    public final SimpleIntegerProperty Code;
    public final DXHttpConfigFields Fields;
    public final DXHttpConfigHeaders Headers;

    public DXHttpConfigResponse(final int code, final DXHttpConfigFields fields, final DXHttpConfigHeaders headers) {
        Code = new SimpleIntegerProperty(null, "Code", code);
        Fields = fields;
        Headers = headers;
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

    public static DXHttpConfigResponse FromElement(final Element element) {
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
        DXHttpConfigHeaders headers = null;
        if (headersElements.size() == 1) {
            final var headersElement = headersElements.get(0);
            headers = DXHttpConfigHeaders.FromElement(headersElement);
        }
        if (headersElements.size() > 1) throw new RuntimeException("Too many headers elements");

        final List<Element> fieldsElements = DXDomUtils.GetChildElementsWithTagName(element,
                DXHttpConfigFields.ELEMENT_TAG_NAME);

        if (fieldsElements.size() == 0)
            return new DXHttpConfigResponse(code, null, headers);
        else if (fieldsElements.size() > 1)
            throw new RuntimeException("Too many fields elements specified for response");

        final Element fieldsElement = fieldsElements.get(0);
        final var fields = DXHttpConfigFields.FromElement(fieldsElement);

        return new DXHttpConfigResponse(code, fields, headers);
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
