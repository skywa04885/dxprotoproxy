package com.github.skywa04885.dxprotoproxy.config.mqtt;

import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the mqtt clients element in the config.
 */
public class MQTTClientsConfig {
    /**
     * Static constants.
     */
    public static final @NotNull String ELEMENT_TAG_NAME = "MQTTClients";

    /**
     * Member variables.
     */
    private final @NotNull SimpleListProperty<MQTTClientConfig> childrenProperty;
    private final @NotNull SimpleObjectProperty<MQTTConfig> parentProperty;

    /**
     * Constructs a new clients config that will contain the given children and has the given parent.
     *
     * @param children the children (clients) it should contain.
     * @param parent   the parent.
     */
    public MQTTClientsConfig(@NotNull List<MQTTClientConfig> children, @Nullable MQTTConfig parent) {
        childrenProperty = new SimpleListProperty<>(null, "children",
                FXCollections.observableList(children));
        parentProperty = new SimpleObjectProperty<>(null, "parent", parent);
    }

    /**
     * Constructs a new clients config that does not contain any children nor has a parent.
     */
    public MQTTClientsConfig() {
        this(new ArrayList<>(), null);
    }


    /**
     * Constructs a new clients config that has the given children, but not a parent.
     */
    public MQTTClientsConfig(@NotNull List<MQTTClientConfig> children) {
        this(children, null);
    }

    /**
     * Gets the parent property.
     *
     * @return the parent property.
     */
    public @NotNull SimpleObjectProperty<MQTTConfig> parentProperty() {
        return parentProperty;
    }

    /**
     * Gets the parent of the clients' config.
     *
     * @return the parent.
     */
    public @Nullable MQTTConfig parent() {
        return parentProperty.get();
    }

    /**
     * Sets the new parent.
     *
     * @param parent the new parent.
     */
    public void setParent(@Nullable MQTTConfig parent) {
        parentProperty.set(parent);
    }

    /**
     * Gets the children property.
     *
     * @return the children property.
     */
    public @NotNull SimpleListProperty<MQTTClientConfig> childrenProperty() {
        return childrenProperty;
    }

    /**
     * Gets the children.
     *
     * @return the children/
     */
    public @NotNull List<MQTTClientConfig> children() {
        return childrenProperty.get();
    }

    /**
     * Turns the current mqtt clients config into an XML element.
     *
     * @param document the document for which the element will be created.
     * @return the created element.
     */
    public @NotNull Element toElement(@NotNull Document document) {
        // Constructs the new element.
        @NotNull Element element = document.createElement(ELEMENT_TAG_NAME);

        // Appends all the mqtt client config elements.
        children()
                .stream()
                .map(child -> child.toElement(document))
                .forEach(element::appendChild);

        // Returns the constructed element.
        return element;
    }

    /**
     * Constructs a new mqtt clients config from the given element.
     *
     * @param element the element to construct it from.
     * @return the constructed instance.
     */
    public static @NotNull MQTTClientsConfig fromElement(@NotNull Element element) {
        // Make sure that the tag name is correct.
        assert element.getTagName().equals(ELEMENT_TAG_NAME);

        // Get all the child elements of element that describe mqtt clients.
        final List<Element> mqttClientConfigElements = DXDomUtils.GetChildElementsWithTagName(
                element, MQTTClientConfig.ELEMENT_TAG_NAME);

        // Parses all the mqtt client configurations from the elements.
        final var clientConfigs = new ArrayList<MQTTClientConfig>(
                mqttClientConfigElements.stream().map(MQTTClientConfig::fromElement).toList());

        // Creates the clients config instance using the client configs.
        final var mqttClientsConfig = new MQTTClientsConfig(clientConfigs);

        // Sets the parents of all the client configs (possible due to the way java works).
        clientConfigs.forEach(clientConfig -> clientConfig.setParent(mqttClientsConfig));

        // Returns the constructed clients config.
        return mqttClientsConfig;
    }
}
