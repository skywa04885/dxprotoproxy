package com.github.skywa04885.dxprotoproxy.config;

import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTConfig;
import com.github.skywa04885.dxprotoproxy.configurator.http.ConfiguratorImageCache;
import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class ConfigRoot implements IDXTreeItem {
    public static final String TAG_NAME = "Root";

    private final @NotNull DXHttpConfig httpConfig;
    private final @NotNull MQTTConfig mqttConfig;

    public ConfigRoot(@NotNull DXHttpConfig httpConfig, @NotNull MQTTConfig mqttConfig) {
        this.httpConfig = httpConfig;
        this.mqttConfig = mqttConfig;
    }

    public ConfigRoot() {
        this(new DXHttpConfig(), new MQTTConfig());
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

    /**
     * Constructs a new mqtt config from the mqtt config element inside the given element.
     * @param element the element that contains the mqtt config element.
     * @return the mqtt config.
     */
    private static @NotNull MQTTConfig mqttConfigFromElement(@NotNull Element element) {
        // Gets all the mqtt config elements.
        final @NotNull List<Element> mqttConfigElements = DXDomUtils.GetChildElementsWithTagName(element,
                MQTTConfig.ELEMENT_TAG_NAME);

        // Make sure there is only one mqtt config element.
        if (mqttConfigElements.isEmpty()) {
            throw new RuntimeException("mqtt config not given");
        } else if (mqttConfigElements.size() > 1) {
            throw new RuntimeException("too many mqtt configs given, only 1 is allwoed");
        }

        // Constructs and returns the mqtt config.
        return MQTTConfig.fromElement(mqttConfigElements.get(0));
    }

    public static @NotNull ConfigRoot fromElement(@NotNull Element element) {
        final @NotNull DXHttpConfig httpConfig = httpConfigFromElement(element);
        final @NotNull MQTTConfig mqttConfig = mqttConfigFromElement(element);

        final var configRoot = new ConfigRoot(httpConfig, mqttConfig);

        httpConfig.setParent(configRoot);
        mqttConfig.setParent(configRoot);

        return configRoot;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final @NotNull Element element = document.createElement(TAG_NAME);

        element.appendChild(httpConfig.toElement(document));
        element.appendChild(mqttConfig.toElement(document));

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
