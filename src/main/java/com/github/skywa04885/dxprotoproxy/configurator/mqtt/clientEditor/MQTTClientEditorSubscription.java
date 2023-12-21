package com.github.skywa04885.dxprotoproxy.configurator.mqtt.clientEditor;

import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTSubscriptionConfig;
import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class is used for the editing of subscriptions,
 * so that the real one doesn't get modified immediately.
 */
public class MQTTClientEditorSubscription {
    /**
     * Member variables.
     */
    private final @NotNull SimpleStringProperty topicProperty;
    private final @Nullable MQTTSubscriptionConfig mqttSubscriptionConfig;

    /**
     * Constructs a new mqtt client editor subscription from the given topic and subscription config.
     *
     * @param topic                  the topic.
     * @param mqttSubscriptionConfig the optional subscription config for when its from an existing config.
     */
    public MQTTClientEditorSubscription(@NotNull String topic,
                                        @Nullable MQTTSubscriptionConfig mqttSubscriptionConfig) {
        topicProperty = new SimpleStringProperty(null, "topic", topic);
        this.mqttSubscriptionConfig = mqttSubscriptionConfig;
    }

    /**
     * Constructs a new blank editor subscription that has no parent.
     */
    public MQTTClientEditorSubscription() {
        this("", null);
    }

    /**
     * Gets the mqtt subscription config.
     *
     * @return the mqtt subscription config.
     */
    public @Nullable MQTTSubscriptionConfig mqttSubscriptionConfig() {
        return mqttSubscriptionConfig;
    }

    /**
     * Checks if there is a subscription config.
     * @return true if there is.
     */
    public boolean hasMqttSubscriptionConfig() {
        return mqttSubscriptionConfig != null;
    }


    /**
     * Checks if there is not a subscription config.
     * @return true if there is not.
     */
    public boolean hasNoMqttSubscriptionConfig() {
        return !hasMqttSubscriptionConfig();
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
     * Sets the topic.
     *
     * @param topic the topic to set.
     */
    public void setTopic(@NotNull String topic) {
        topicProperty.set(topic);
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
     * Constructs a new mqtt client editor subscription from the given subscription config.
     *
     * @param mqttSubscriptionConfig the subscription config.
     * @return the editor subscription instance.
     */
    public static @NotNull MQTTClientEditorSubscription fromSubscriptionConfig(@NotNull MQTTSubscriptionConfig
                                                                                       mqttSubscriptionConfig) {
        return new MQTTClientEditorSubscription(mqttSubscriptionConfig.topic(), mqttSubscriptionConfig);
    }

    /**
     * Checks if the current subscription is blank.
     *
     * @return true if it is blank.
     */
    public boolean isBlank() {
        return topic().isBlank();
    }

    /**
     * Checks if the current subscription not.
     *
     * @return true if it is not blank.
     */
    public boolean isNotBlank() {
        return !isBlank();
    }

    /**
     * Turns the editor subscription into a config subscription.
     *
     * @return the config subscription.
     */
    public @NotNull MQTTSubscriptionConfig toConfig() {
        return new MQTTSubscriptionConfig(topic(), null);
    }
}
