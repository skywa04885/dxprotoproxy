package com.github.skywa04885.dxprotoproxy.server.mqtt.client;

import com.github.skywa04885.dxprotoproxy.server.net.NetOutputStreamWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MqttClientMessageHeaderWriter {
    private final @NotNull NetOutputStreamWrapper outputStreamWrapper;

    public MqttClientMessageHeaderWriter(@NotNull NetOutputStreamWrapper outputStreamWrapper) {
        this.outputStreamWrapper = outputStreamWrapper;
    }

    /**
     * Writes the given header to the output stream.
     * @param header the header to write.
     * @throws IOException gets thrown if the writing fails
     */
    public void write(@NotNull MqttClientMessageHeader header) throws IOException {
        // Writes the line that contains the topic.
        outputStreamWrapper.writeLine(header.topic());

        // Writes the header.
        outputStreamWrapper.writeLine(header.qos() + " " + header.size());
    }
}
