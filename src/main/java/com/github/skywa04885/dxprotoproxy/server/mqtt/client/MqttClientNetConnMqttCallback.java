package com.github.skywa04885.dxprotoproxy.server.mqtt.client;

import com.github.skywa04885.dxprotoproxy.server.net.NetOutputStreamWrapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class MqttClientNetConnMqttCallback implements MqttCallback {
    private final @NotNull Logger logger = Logger.getLogger(MqttClientNetConnMqttCallback.class.getName());
    private final @NotNull ExecutorService executorService;
    private final @NotNull NetOutputStreamWrapper outputStreamWrapper;
    private final @NotNull MqttClientMessageHeaderWriter messageHeaderWriter;

    public MqttClientNetConnMqttCallback(@NotNull ExecutorService executorService,
                                         @NotNull NetOutputStreamWrapper outputStreamWrapper,
                                         @NotNull MqttClientMessageHeaderWriter messageHeaderWriter) {
        this.executorService = executorService;
        this.outputStreamWrapper = outputStreamWrapper;
        this.messageHeaderWriter = messageHeaderWriter;

    }

    @Override
    public void connectionLost(@NotNull Throwable throwable) {

    }

    /**
     * Handles the reception of a single message.
     * @param topic the topic that the message was received on.
     * @param mqttMessage the mqtt message.
     * @throws Exception gets possibly thrown.
     */
    @Override
    public void messageArrived(@NotNull String topic, @NotNull MqttMessage mqttMessage) throws Exception {
        // Creates the message header.
        final @NotNull MqttClientMessageHeader messageHeader = new MqttClientMessageHeader(topic,
                mqttMessage.getQos(), mqttMessage.getPayload().length);

        // Writes the message header followed by the message itself.
        executorService.submit(() -> {
            try {
                messageHeaderWriter.write(messageHeader);
                outputStreamWrapper.write(mqttMessage.getPayload());
            } catch (@NotNull IOException ioException) {
                // Log the error.
                logger.severe("Got IOException while writing message, exception: " + ioException.getMessage());

                // Close the output stream.
                try {
                    outputStreamWrapper.close();
                } catch (IOException e) {
                    logger.severe("Got IOException while closing output stream, exception: "
                            + ioException.getMessage());
                }
            }
        });
    }

    @Override
    public void deliveryComplete(@NotNull IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
