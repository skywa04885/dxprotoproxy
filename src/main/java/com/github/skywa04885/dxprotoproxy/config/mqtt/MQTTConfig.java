package com.github.skywa04885.dxprotoproxy.config.mqtt;

import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.config.ConfigRoot;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class MQTTConfig {
    /**
     * Static constants.
     */
    public static final @NotNull String ELEMENT_TAG_NAME = "MQTT";

    /**
     * Member variables.
     */
    private final @NotNull MQTTClientsConfig mqttClientsConfig;
    private final @NotNull SimpleObjectProperty<ConfigRoot> parentProperty;

    /**
     * Constructs a new mqtt config instance.
     * @param mqttClientsConfig the clients' config.
     * @param parent the parent.
     */
    public MQTTConfig(@NotNull MQTTClientsConfig mqttClientsConfig, @Nullable ConfigRoot parent) {
        this.mqttClientsConfig = mqttClientsConfig;
        parentProperty = new SimpleObjectProperty<>(null, "parent", parent);
    }

    /**
     * Constructs a new empty mqtt config.
     */
    public MQTTConfig() {
        this(new MQTTClientsConfig(), null);
    }

    /**
     * Gets the clients config.
     * @return the clients config.
     */
    public @NotNull MQTTClientsConfig mqttClientsConfig() {
        return mqttClientsConfig;
    }

    /**
     * Gets the parent.
     * @return the parent.
     */
    public @Nullable ConfigRoot parent() {
        return parentProperty.get();
    }

    /**
     * Sets the parent.
     * @param parent the new parent.
     */
    public void setParent(@Nullable ConfigRoot parent) {
        parentProperty.set(parent);
    }

    /**
     * Turns the current mqtt config into an XML element.
     *
     * @param document the document for which the element will be created.
     * @return the created element.
     */
    public @NotNull Element toElement(@NotNull Document document) {
        // Constructs the new element.
        @NotNull Element element = document.createElement(ELEMENT_TAG_NAME);

        // Appends the clients config element.
        element.appendChild(mqttClientsConfig().toElement(document));

        // Returns the constructed element.
        return element;
    }

    /**
     * Constructs a new mqtt config from the given element.
     *
     * @param element the element to construct it from.
     * @return the constructed instance.
     */
    public static @NotNull MQTTConfig fromElement(@NotNull Element element) {
        // Make sure that the tag name is correct.
        assert element.getTagName().equals(ELEMENT_TAG_NAME);

        // Gets all the mqtt clients config elements.
        final List<Element> mqttClientsConfigElements = DXDomUtils.GetChildElementsWithTagName(element,
                MQTTClientsConfig.ELEMENT_TAG_NAME);

        // Makes sure that there is only one clients config element.
        if (mqttClientsConfigElements.isEmpty()) {
            throw new RuntimeException("No mqtt clients config element is present");
        } else if (mqttClientsConfigElements.size() > 1) {
            throw new RuntimeException("Too many mqtt clients config elements are present, only 1 allowed");
        }

        // Parses the clients config from the element.
        final @NotNull MQTTClientsConfig mqttClientsConfig = MQTTClientsConfig
                .fromElement(mqttClientsConfigElements.get(0));

        // Constructs the mqtt config.
        final var mqttConfig = new MQTTConfig(mqttClientsConfig, null);

        // Sets the parent of the clients config.
        mqttClientsConfig.setParent(mqttConfig);

        // Returns the mqtt config.
        return mqttConfig;
    }
}
