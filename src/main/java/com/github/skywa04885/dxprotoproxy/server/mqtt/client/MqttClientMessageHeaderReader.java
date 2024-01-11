package com.github.skywa04885.dxprotoproxy.server.mqtt.client;

import com.github.skywa04885.dxprotoproxy.server.net.NetInputStreamWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class MqttClientMessageHeaderReader {
    private final @NotNull NetInputStreamWrapper inputStreamWrapper;

    public MqttClientMessageHeaderReader(@NotNull NetInputStreamWrapper inputStreamWrapper) {
        this.inputStreamWrapper = inputStreamWrapper;
    }

    public @Nullable MqttClientMessageHeader read() throws IOException, RuntimeException {
        // Reads the topic line.
        @Nullable String topic = inputStreamWrapper.readStringUntilNewLine();
        if (topic == null) return null;
        topic = topic.trim(); // More fault-tolerant.
        if (topic.isEmpty()) throw new RuntimeException("Topic cannot be empty");

        // Reads the header line.
        @Nullable String headerLine = inputStreamWrapper.readStringUntilNewLine();
        if (headerLine == null) return null;
        headerLine = headerLine.trim(); // More fault-tolerant.

        // Splits the header line into its segments.
        final @NotNull String[] headerLineSegments = headerLine.split(" ");
        if (headerLineSegments.length != 2) throw new RuntimeException("Invalid header");

        // Gets the individual segments from the header.
        final @NotNull String qosHeaderLineSegment = headerLineSegments[0].trim();
        final @NotNull String sizeHeaderLineSegment = headerLineSegments[1].trim();

        // Parses the quality of service line segment.
        int qos;
        try {
            qos = Integer.parseInt(qosHeaderLineSegment);
        } catch (@NotNull NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid quality of service");
        }

        // Parses the size header line segment.
        int size;
        try {
            size = Integer.parseInt(sizeHeaderLineSegment);
        } catch (@NotNull NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid size");
        }

        // Construct and return the header.
        return new MqttClientMessageHeader(topic, qos, size);
    }
}
