package com.github.skywa04885.dxprotoproxy.server.mqtt.client;

import org.jetbrains.annotations.NotNull;

public class MqttClientMessageHeader {
    private final @NotNull String topic;
    private final int qos;
    private final int size;

    /**
     * Constructs a new mqtt client message header.
     * @param topic the topic of the messages.
     * @param qos the quality of the service of the message.
     * @param size the size of the message.
     */
    public MqttClientMessageHeader(@NotNull String topic, int qos, int size) {
        this.topic = topic;
        this.qos = qos;
        this.size = size;
    }

    public @NotNull String topic() {
        return topic;
    }

    public int qos() {
        return qos;
    }

    public int size() {
        return size;
    }
}
