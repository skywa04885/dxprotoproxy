package com.github.skywa04885.dxprotoproxy.server.mqtt.client;

import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientConfig;
import com.github.skywa04885.dxprotoproxy.server.net.NetInputStreamWrapper;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutConn;
import com.github.skywa04885.dxprotoproxy.server.net.NetOutputStreamWrapper;
import org.eclipse.paho.client.mqttv3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MqttClientNetConn extends NetOutConn {
    private final Logger logger = Logger.getLogger(MqttClientNetConn.class.getName());

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
        // Subscribe to all the topics specified in the configuration.
        for (String topic : mqttClientConfig.topics()) {
            logger.info("Subscribing to topic: \"" + topic + "\"");
            mqttClient.subscribe(topic);
        }
    }

    @Override
    protected void runImpl(@NotNull NetOutputStreamWrapper outputStreamWrapper,
                           @NotNull NetInputStreamWrapper inputStreamWrapper) {
        // Creates the message header reader and writer.
        final @NotNull MqttClientMessageHeaderReader messageHeaderReader =
                new MqttClientMessageHeaderReader(inputStreamWrapper);
        final @NotNull MqttClientMessageHeaderWriter messageHeaderWriter =
                new MqttClientMessageHeaderWriter(outputStreamWrapper);

        // Creates the executor service (for the writing).
        @NotNull ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Creates the connect options for the mqtt client.
        final @NotNull MqttConnectOptions mqttConnectOptions =
                MyMqttConnectOptionsFactory.createFromClientConfig(mqttClientConfig);

        // Creates and connects to the mqtt client.
        @NotNull MqttClient mqttClient;
        try {
            // Creates the mqtt client and the connect options.
            mqttClient = MyMqttClientFactory.createFromConfig(mqttClientConfig);

            // Sets the mqtt callback.
            mqttClient.setCallback(new MqttClientNetConnMqttCallback(executorService, outputStreamWrapper,
                    messageHeaderWriter));

            // Connects the MQTT client.
            logger.info("Connecting to MQTT Broker \"" + mqttClientConfig.brokerHostname() + ":"
                    + mqttClientConfig.brokerPort() + "\"");
            mqttClient.connect();
        } catch (@NotNull MqttException mqttException) {
            logger.severe("An MQTT Exception occurred: " + mqttException.getMessage());
            return;
        }

        // Subscribe to the topics and read the stuff.
        try {
            // Subscribes the client to all the topics.
            subscribeMqttClientToTopics(mqttClient);

            // Keeps reading until interrupted.
            while (true) {
                // Reads the message header or breaks from the loop if the client disconnected.
                final @Nullable MqttClientMessageHeader messageHeader = messageHeaderReader.read();
                if (messageHeader == null) break;

                // Reads the message or breaks from the loop if disconnected.
                final @Nullable byte[] message = inputStreamWrapper.readNBytes(messageHeader.size());
                if (message == null) break;

                // Prints that we're publishing.
                logger.info("Publishing message of size " + messageHeader.size() + " with quality of service "
                    + messageHeader.qos() + " to topic " + messageHeader.topic());

                // Publishes the messages (retained false to prevent persistence on disk).
                mqttClient.publish(messageHeader.topic(), message, messageHeader.qos(), false);
            }
        } catch (@NotNull MqttException mqttException) {
            logger.severe("An MQTT Exception occurred: " + mqttException.getMessage());
        } catch (@NotNull IOException ioException) {
            logger.severe("An IO Exception occurred: " + ioException.getMessage());
        }

        // Disconnect the mqtt client.
        try {
            logger.info("Disconnecting MQTT client");
            mqttClient.disconnect();
        } catch (@NotNull MqttException mqttException) {
            logger.severe("An MQTT Exception occurred while closing the mqtt client: "
                    + mqttException.getMessage());
        }
    }
}
