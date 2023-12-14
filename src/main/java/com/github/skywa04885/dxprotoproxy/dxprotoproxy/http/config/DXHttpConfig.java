package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.config.ConfigRoot;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.ConfiguratorImageCache;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class DXHttpConfig implements IDXTreeItem {
    public static final String TAG_NAME = "Http";

    private @Nullable ConfigRoot parent;
    private @NotNull HttpConfigApis httpConfigApis;

    public DXHttpConfig(@NotNull HttpConfigApis httpConfigApis) {
        this.httpConfigApis = httpConfigApis;
    }

    public DXHttpConfig() {
        this(new HttpConfigApis());
    }

    public void setParent(@Nullable ConfigRoot parent) {
        this.parent = parent;
    }

    public @Nullable ConfigRoot parent() {
        return parent;
    }

    public @NotNull HttpConfigApis httpConfigApis() {
        return httpConfigApis;
    }

    private static HttpConfigApis httpConfigApisFromElement(@NotNull Element element) {
        final var apisElements = DXDomUtils.GetChildElementsWithTagName(element, HttpConfigApis.TAG_NAME);

        if (apisElements.isEmpty()) {
            throw new RuntimeException("Apis element is missing");
        } else if (apisElements.size() > 1) {
            throw new RuntimeException("Too many apis elements");
        }

        final var apisElement = (Element) apisElements.get(0);

        return HttpConfigApis.fromElement(apisElement);
    }

    public static DXHttpConfig FromElement(final Element element) {
        if (!element.getTagName().equals(TAG_NAME)) {
            throw new RuntimeException("Tag name mismatch, expected " + TAG_NAME + " got " + element.getTagName());
        }

        final HttpConfigApis httpConfigApis = httpConfigApisFromElement(element);

        final var httpConfig = new DXHttpConfig(httpConfigApis);

        httpConfigApis.setParent(httpConfig);

        return httpConfig;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(TAG_NAME);

        element.appendChild(httpConfigApis.toElement(document));

        return element;
    }

    @Override
    public Node treeItemGraphic() {
        return new ImageView(ConfiguratorImageCache.instance().read("icons/manufacturing_FILL0_wght400_GRAD0_opsz24.png"));
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return new SimpleStringProperty(null, null, "HTTP");
    }
}
