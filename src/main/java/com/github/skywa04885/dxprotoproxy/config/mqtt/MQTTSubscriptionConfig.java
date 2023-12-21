package com.github.skywa04885.dxprotoproxy.config.mqtt;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MQTTSubscriptionConfig {
    /**
     * Static constants.
     */
    public static final @NotNull String ELEMENT_TAG_NAME = "MQTTSubscription";

    /**
     * Member variables.
     */
    private final @NotNull SimpleStringProperty topicProperty;
    private final @NotNull SimpleObjectProperty<MQTTClientConfig> parentProperty;

    /**
     * Constructs a new subscription config with the given topic and parent.
     *
     * @param topic  the topic.
     * @param parent the parent.
     */
    public MQTTSubscriptionConfig(@NotNull String topic, @Nullable MQTTClientConfig parent) {
        topicProperty = new SimpleStringProperty(null, "topic", topic);
        parentProperty = new SimpleObjectProperty<>(null, "parent", parent);
    }

    /**
     * Gets the topic property.
     *
     * @return the topic property.
     */
    public @NotNull SimpleStringProperty topicProperty() {
        return topicProperty;
    }

    /**
     * Gets the topic.
     *
     * @return the topic.
     */
    public @NotNull String topic() {
        return topicProperty.get();
    }

    /**
     * Gets the parent.
     *
     * @return the parent.
     */
    public @Nullable MQTTClientConfig parent() {
        return parentProperty.get();
    }

    /**
     * Sets the parent.
     *
     * @param parent the new parent.
     */
    public void setParent(@Nullable MQTTClientConfig parent) {
        parentProperty.set(parent);
    }

    /**
     * Turns the current mqtt subscription config into an XML element.
     *
     * @param document the document for which the element will be created.
     * @return the created element.
     */
    public @NotNull Element toElement(@NotNull Document document) {
        // Constructs the new element.
        @NotNull Element element = document.createElement(ELEMENT_TAG_NAME);

        // Sets the topic.
        element.setTextContent(topic());

        // Returns the constructed element.
        return element;
    }

    /**
     * Constructs a new mqtt subscription config from the given element.
     *
     * @param element the element to construct it from.
     * @return the constructed instance.
     */
    public static @NotNull MQTTSubscriptionConfig fromElement(@NotNull Element element) {
        // Make sure that the tag name is correct.
        assert element.getTagName().equals(ELEMENT_TAG_NAME);

        // Gets the topic.
        final @Nullable String topic = element.getTextContent();
        if (topic == null) {
            throw new RuntimeException("The topic must be given in the node text content");
        } else if (topic.isBlank()) {
            throw new RuntimeException("The topic is not allowed to be blank");
        }

        // Constructs the new mqtt subscription config and returns it.
        return new MQTTSubscriptionConfig(topic, null);
    }
}
