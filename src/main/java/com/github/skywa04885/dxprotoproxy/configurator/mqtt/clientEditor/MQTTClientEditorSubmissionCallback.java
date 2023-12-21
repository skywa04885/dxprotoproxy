package com.github.skywa04885.dxprotoproxy.configurator.mqtt.clientEditor;

import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTClientsConfig;
import com.github.skywa04885.dxprotoproxy.config.mqtt.MQTTSubscriptionConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MQTTClientEditorSubmissionCallback implements IMQTTClientEditorSubmissionCallback {
    /**
     * Member variables.
     */
    private final @NotNull MQTTClientsConfig mqttClientsConfig;
    private final @Nullable MQTTClientConfig mqttClientConfig;

    /**
     * Constructs a new mqtt client editor submission callback.
     *
     * @param mqttClientsConfig the config containing all the clients.
     * @param mqttClientConfig  the possibly already existing client that's being modified.
     */
    public MQTTClientEditorSubmissionCallback(@NotNull MQTTClientsConfig mqttClientsConfig,
                                              @Nullable MQTTClientConfig mqttClientConfig) {
        this.mqttClientsConfig = mqttClientsConfig;
        this.mqttClientConfig = mqttClientConfig;
    }

    public MQTTClientEditorSubmissionCallback(@NotNull MQTTClientsConfig mqttClientsConfig) {
        this(mqttClientsConfig, null);
    }

    public MQTTClientEditorSubmissionCallback(@NotNull MQTTClientConfig mqttClientConfig) {
        this(Objects.requireNonNull(mqttClientConfig.parent()), mqttClientConfig);
    }

    /**
     * Creates a new client config from the given fields.
     *
     * @param clientHostname   the client hostname.
     * @param clientPort       the client port.
     * @param brokerHostname   the broker hostname.
     * @param brokerPort       the broker port.
     * @param username         the username.
     * @param password         the password.
     * @param clientIdentifier the client identifier.
     * @param subscriptions    the subscriptions.
     */
    private void create(@NotNull String clientHostname, int clientPort,
                        @NotNull String brokerHostname, int brokerPort,
                        @Nullable String username, @Nullable String password,
                        @Nullable String clientIdentifier,
                        @NotNull List<MQTTClientEditorSubscription> subscriptions) {
        // Creates all the subscription configs.
        final @NotNull List<MQTTSubscriptionConfig> mqttSubscriptionConfigs = subscriptions
                .stream()
                .map(editorSubscription -> editorSubscription.toConfig())
                .collect(Collectors.toList());

        // Creates the client config.
        final var mqttClientConfig = new MQTTClientConfig(clientHostname, clientPort,
                brokerHostname, brokerPort, username, password, clientIdentifier,
                mqttSubscriptionConfigs, mqttClientsConfig);

        // Sets the parent of all the mqtt subscription configs.
        mqttSubscriptionConfigs.forEach(subscriptionConfig -> subscriptionConfig.setParent(mqttClientConfig));

        // Inserts the client config into the clients.
        mqttClientsConfig.children().add(mqttClientConfig);
    }

    /**
     * Updates the existing client config using the given fields.
     *
     * @param clientHostname   the client hostname.
     * @param clientPort       the client port.
     * @param brokerHostname   the broker hostname.
     * @param brokerPort       the broker port.
     * @param username         the username.
     * @param password         the password.
     * @param clientIdentifier the client identifier.
     * @param subscriptions    the subscriptions.
     */
    private void update(@NotNull String clientHostname, int clientPort,
                        @NotNull String brokerHostname, int brokerPort,
                        @Nullable String username, @Nullable String password,
                        @Nullable String clientIdentifier,
                        @NotNull List<MQTTClientEditorSubscription> subscriptions) {
        // The client config should never be null.
        assert mqttClientConfig != null;

        // Updates the client hostname.
        if (!clientHostname.equals(mqttClientConfig.clientHostname())) {
            mqttClientConfig.setClientHostname(clientHostname);
        }

        // Updates the client port.
        if (clientPort != mqttClientConfig.clientPort()) {
            mqttClientConfig.setClientPort(clientPort);
        }

        // Updates the broker hostname.
        if (!brokerHostname.equals(mqttClientConfig.brokerHostname())) {
            mqttClientConfig.setBrokerHostname(brokerHostname);
        }

        // Updates the broker port.
        if (brokerPort != mqttClientConfig.brokerPort()) {
            mqttClientConfig.setBrokerPort(brokerPort);
        }

        // Updates the username,
        if ((username == null && mqttClientConfig.username() != null)
                || (username != null && !username.equals(mqttClientConfig.username()))) {
            mqttClientConfig.setUsername(username);
        }

        // Updates the password.
        if ((password == null && mqttClientConfig.password() != null)
                || (password != null && !password.equals(mqttClientConfig.password()))) {
            mqttClientConfig.setPassword(password);
        }

        // Updates the client identifier.
        if ((clientIdentifier == null && mqttClientConfig.clientIdentifier() != null)
                || (clientIdentifier != null && !clientIdentifier.equals(mqttClientConfig.clientIdentifier()))) {
            mqttClientConfig.setClientIdentifier(clientIdentifier);
        }

        // Removes all the subscriptions that need to be removed.
        mqttClientConfig.subscriptionConfigs().removeIf(mqttSubscriptionConfig ->
                subscriptions.stream().noneMatch(mqttClientEditorSubscription ->
                        Objects.equals(mqttClientEditorSubscription.mqttSubscriptionConfig(), mqttSubscriptionConfig)));

        // Updates all the existing subscriptions.
        subscriptions.stream()
                .filter(MQTTClientEditorSubscription::hasMqttSubscriptionConfig)
                .forEach(mqttClientEditorSubscription -> {
                    // Gets the config.
                    final MQTTSubscriptionConfig mqttSubscriptionConfig =
                            Objects.requireNonNull(mqttClientEditorSubscription.mqttSubscriptionConfig());

                    // Updates the topic if needed.
                    if (!mqttClientEditorSubscription.topic().equals(mqttSubscriptionConfig.topic())) {
                        mqttSubscriptionConfig.setTopic(mqttClientEditorSubscription.topic());
                    }
                });

        // Creates all the new subscriptions.
        mqttClientConfig.subscriptionConfigs().addAll(subscriptions.stream()
                .filter(MQTTClientEditorSubscription::hasNoMqttSubscriptionConfig)
                .map(mqttClientEditorSubscription ->
                        new MQTTSubscriptionConfig(mqttClientEditorSubscription.topic(), mqttClientConfig))
                .toList());
    }

    /**
     * Submits the given fields.
     *
     * @param clientHostname   the client hostname.
     * @param clientPort       the client port.
     * @param brokerHostname   the broker hostname.
     * @param brokerPort       the broker port.
     * @param username         the username.
     * @param password         the password.
     * @param clientIdentifier the client identifier.
     * @param subscriptions    the subscriptions.
     */
    @Override
    public void submit(@NotNull String clientHostname, int clientPort,
                       @NotNull String brokerHostname, int brokerPort,
                       @Nullable String username, @Nullable String password,
                       @Nullable String clientIdentifier,
                       @NotNull List<MQTTClientEditorSubscription> subscriptions) {
        if (mqttClientConfig == null) {
            create(clientHostname, clientPort, brokerHostname, brokerPort, username, password, clientIdentifier,
                    subscriptions);
        } else {
            update(clientHostname, clientPort, brokerHostname, brokerPort, username, password, clientIdentifier,
                    subscriptions);
        }
    }
}
