package com.github.skywa04885.dxprotoproxy.config;

import com.github.skywa04885.dxprotoproxy.config.http.DXHttpConfig;
import com.github.skywa04885.dxprotoproxy.config.modbus.ModbusConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTConfig;
import com.github.skywa04885.dxprotoproxy.configurator.ConfiguratorImageCache;
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

public class ConfigRoot {
    public static final String TAG_NAME = "Root";

    private final @NotNull DXHttpConfig httpConfig;
    private final @NotNull MQTTConfig mqttConfig;
    private final @NotNull ModbusConfig modbusConfig;

    public ConfigRoot(@NotNull DXHttpConfig httpConfig, @NotNull MQTTConfig mqttConfig,
                      @NotNull ModbusConfig modbusConfig) {
        this.httpConfig = httpConfig;
        this.mqttConfig = mqttConfig;
        this.modbusConfig = modbusConfig;
    }

    public ConfigRoot() {
        this(new DXHttpConfig(), new MQTTConfig(), new ModbusConfig());
    }

    public @NotNull DXHttpConfig httpConfig() {
        return httpConfig;
    }

    /**
     * Gets the mqtt config.
     * @return the mqtt config.
     */
    public @NotNull MQTTConfig mqttConfig() {
        return mqttConfig;
    }

    public @NotNull ModbusConfig modbusConfig() {
        return modbusConfig;
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

    private static @NotNull ModbusConfig modbusConfigFromElement(@NotNull Element element) {
        final @NotNull List<Element> elements = DXDomUtils.GetChildElementsWithTagName(element,
                ModbusConfig.ELEMENT_TAG_NAME);

        if (elements.isEmpty()) throw new RuntimeException("Modbus config missing");
        else if (elements.size() > 1) throw new RuntimeException("Too many modbus configs present");

        return ModbusConfig.fromElement(elements.get(0));
    }

    public static @NotNull ConfigRoot fromElement(@NotNull Element element) {
        final @NotNull DXHttpConfig httpConfig = httpConfigFromElement(element);
        final @NotNull MQTTConfig mqttConfig = mqttConfigFromElement(element);
        final @NotNull ModbusConfig modbusConfig = modbusConfigFromElement(element);

        final var configRoot = new ConfigRoot(httpConfig, mqttConfig, modbusConfig);

        httpConfig.setParent(configRoot);
        mqttConfig.setParent(configRoot);

        return configRoot;
    }

    public @NotNull Element toElement(@NotNull Document document) {
        final @NotNull Element element = document.createElement(TAG_NAME);

        element.appendChild(httpConfig.toElement(document));
        element.appendChild(mqttConfig.toElement(document));
        element.appendChild(modbusConfig.toElement(document));

        return element;
    }
}
