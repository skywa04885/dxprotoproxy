package com.github.skywa04885.dxprotoproxy.server.mqtt.client;

import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientConfig;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutConn;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.jetbrains.annotations.NotNull;

import java.net.Socket;

public class MqttClientNetConn extends NetOutConn {
    private final @NotNull MQTTClientConfig mqttClientConfig;

    public MqttClientNetConn(@NotNull MQTTClientConfig mqttClientConfig) {
        // Calls the parent constructor and gives it the hostname and port of the client.
        super(mqttClientConfig.clientHostname(), mqttClientConfig.clientPort());

        // Sets the instance variables.
        this.mqttClientConfig = mqttClientConfig;
    }

    /**
     * Subscribes the given mqtt client to all the topics in the config.
     *
     * @param mqttClient the mqtt client.
     * @throws MqttException gets thrown if the subscribing fails.
     */
    private void subscribeMqttClientToTopics(@NotNull MqttClient mqttClient) throws MqttException {
        mqttClient.subscribe((String[]) mqttClientConfig.topics().toArray());
    }

    @Override
    protected void runImpl(@NotNull Socket socket) {
        try {
            // Creates the mqtt client and the connect options.
            final MqttClient mqttClient =
                    MyMqttClientFactory.createFromConfig(mqttClientConfig);
            final MqttConnectOptions mqttConnectOptions =
                    MyMqttConnectOptionsFactory.createFromClientConfig(mqttClientConfig);

            mqttClient.connect();

            // Subscribes the client to all the topics.
            subscribeMqttClientToTopics(mqttClient);

        } catch (MqttException mqttException) {

        }
    }
}
