package com.github.skywa04885.dxprotoproxy.configurator.mqtt.clientEditor;

import com.github.skywa04885.dxprotoproxy.GlobalConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MQTTClientEditorValidationCallback implements IMQTTClientEditorValidationCallback {
    /**
     * Validates the given fields.
     *
     * @param clientHostname   the client hostname.
     * @param clientPort       the client port.
     * @param brokerHostname   the broker hostname.
     * @param brokerPort       the broker port.
     * @param username         the username.
     * @param password         the password.
     * @param clientIdentifier the client identifier.
     * @param subscriptions    the subscriptions.
     * @return the error message or null if everything was valid.
     */
    @Override
    public @Nullable String validate(@NotNull String clientHostname, int clientPort,
                                     @NotNull String brokerHostname, int brokerPort,
                                     @Nullable String username, @Nullable String password,
                                     @Nullable String clientIdentifier,
                                     @NotNull List<MQTTClientEditorSubscription> subscriptions) {
        // Validates the hostname of the client.
        if (!GlobalConstants.HOSTNAME_PATTERN.matcher(clientHostname).matches()) {
            return "Invalid hostname given for the client";
        }

        // Validates the port of the client.
        if (!(clientPort >= GlobalConstants.PORT_MIN && clientPort <= GlobalConstants.PORT_MAX)) {
            return "Invalid port given for the client";
        }

        // Validates the hostname of the broker.
        if (!GlobalConstants.HOSTNAME_PATTERN.matcher(brokerHostname).matches()) {
            return "Invalid hostname given for the broker";
        }

        // Validates the port of the broker.
        if (!(brokerPort >= GlobalConstants.PORT_MIN && brokerPort <= GlobalConstants.PORT_MAX)) {
            return "Invalid port given for the broker";
        }

        // Validates the username.
        if (username != null && !GlobalConstants.MQTT_USERNAME.matcher(username).matches()) {
            return "Invalid MQTT username";
        }

        // Validates the client identifier.
        if (clientIdentifier != null && !GlobalConstants.MQTT_CLIENT_IDENTIFIER.matcher(clientIdentifier).matches()) {
            return "Invalid MQTT client identifier";
        }

        return null;
    }
}
