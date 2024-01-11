package com.github.skywa04885.dxprotoproxy.server.mqtt.client;

import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientConfig;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.jetbrains.annotations.NotNull;

public class MyMqttConnectOptionsFactory {
    /**
     * Creates a connection options instance from the given config.
     *
     * @param clientConfig the config.
     * @return the connection options.
     */
    public static @NotNull MqttConnectOptions createFromClientConfig(@NotNull MQTTClientConfig clientConfig) {
        // Creates the connection options.
        final var connectOptions = new MqttConnectOptions();

        // Sets teh connection options
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setCleanSession(true);
        connectOptions.setConnectionTimeout(10);
        if(clientConfig.username() != null) connectOptions.setUserName(clientConfig.username());
        if (clientConfig.password() != null) connectOptions.setPassword(clientConfig.password().toCharArray());

        // Returns the built connection options.
        return connectOptions;
    }
}
