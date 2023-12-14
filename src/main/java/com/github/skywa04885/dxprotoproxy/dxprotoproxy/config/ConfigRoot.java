package com.github.skywa04885.dxprotoproxy.dxprotoproxy.config;

import com.github.skywa04885.dxprotoproxy.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.config.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.dxprotoproxy.http.configurator.ConfiguratorImageCache;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConfigRoot implements IDXTreeItem {
    public static final String TAG_NAME = "Root";

    private final @NotNull DXHttpConfig httpConfig;

    public ConfigRoot(@NotNull DXHttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    public ConfigRoot() {
        this(new DXHttpConfig());
    }

    public @NotNull DXHttpConfig httpConfig() {
        return httpConfig;
    }

    private static @NotNull DXHttpConfig httpConfigFromElement(@NotNull Element element) {
        final var httpElements = DXDomUtils.GetChildElementsWithTagName(element, DXHttpConfig.TAG_NAME);

        if (httpElements.isEmpty()) {
            throw new RuntimeException("HTTP configuration missing");
        } else if (httpElements.size() > 1) {
            throw new RuntimeException("Too many HTTP configurations given");
        }

        return DXHttpConfig.FromElement(httpElements.get(0));
    }

    public static @NotNull ConfigRoot fromElement(@NotNull Element element) {
        final DXHttpConfig httpConfig = httpConfigFromElement(element);

        final var configRoot = new ConfigRoot(httpConfig);

        httpConfig.setParent(configRoot);

        return configRoot;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final var element = document.createElement(TAG_NAME);

        element.appendChild(httpConfig.toElement(document));

        return element;
    }

    @Override
    public Node treeItemGraphic() {
        return new ImageView(ConfiguratorImageCache.instance().read("icons/settings.png"));
    }

    @Override
    public ObservableValue<String> treeItemText() {
        return Bindings.createStringBinding(() -> "Config");
    }
}
