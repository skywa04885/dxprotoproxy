package com.github.skywa04885.dxprotoproxy.server.mqtt.client;

import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MyMqttClientFactory {
    /**
     * Creates a new mqtt client from the given config.
     * @param clientConfig the config.
     * @return the created mqtt client.
     * @throws MqttException gets thrown if creating the mqtt client failed.
     */
    public static @NotNull MqttClient createFromConfig(@NotNull MQTTClientConfig clientConfig) throws MqttException {
        // Creates the connection uri.
        final var connectionUri = "tcp://" + clientConfig.brokerHostname() + ":" + clientConfig.brokerPort();

        // Creates the client identifier.
        final var clientIdentifier = clientConfig.clientIdentifier() != null
                ? clientConfig.clientIdentifier()
                : UUID.randomUUID().toString();

        // Creates the mqtt client.
        return new MqttClient(connectionUri, clientIdentifier);
    }
}
