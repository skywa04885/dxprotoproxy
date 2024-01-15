package com.github.skywa04885.dxprotoproxy.config.http;

import com.github.skywa04885.dxprotoproxy.configurator.ConfiguratorImageCache;
import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.config.ConfigRoot;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DXHttpConfig {
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
}
