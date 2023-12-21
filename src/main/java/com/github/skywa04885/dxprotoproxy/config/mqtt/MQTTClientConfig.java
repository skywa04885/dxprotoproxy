package com.github.skywa04885.dxprotoproxy.config.mqtt;

import com.github.skywa04885.dxprotoproxy.DXDomUtils;
import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import com.github.skywa04885.dxprotoproxy.configurator.ConfiguratorImageCache;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.stream.Collectors;

public class MQTTClientConfig implements IDXTreeItem {
    /**
     * Static constants.
     */
    public static final @NotNull String ELEMENT_TAG_NAME = "MQTTClient";
    public static final @NotNull String CLIENT_HOSTNAME_ATTRIBUTE_NAME = "ClientHostname";
    public static final @NotNull String CLIENT_PORT_ATTRIBUTE_NAME = "ClientPort";
    public static final @NotNull String BROKER_HOSTNAME_ATTRIBUTE_NAME = "BrokerHostname";
    public static final @NotNull String BROKER_PORT_ATTRIBUTE_NAME = "BrokerPort";
    public static final @NotNull String USERNAME_ATTRIBUTE_NAME = "Username";
    public static final @NotNull String PASSWORD_ATTRIBUTE_NAME = "Password";
    public static final @NotNull String CLIENT_IDENTIFIER_ATTRIBUTE_NAME = "ClientIdentifier";

    /**
     * Member variables.
     */
    private final @NotNull SimpleStringProperty clientHostnameProperty;
    private final @NotNull SimpleIntegerProperty clientPortProperty;
    private final @NotNull SimpleStringProperty brokerHostnameProperty;
    private final @NotNull SimpleIntegerProperty brokerPortProperty;
    private final @NotNull SimpleStringProperty usernameProperty;
    private final @NotNull SimpleStringProperty passwordProperty;
    private final @NotNull SimpleStringProperty clientIdentifierProperty;
    private final @NotNull SimpleListProperty<MQTTSubscriptionConfig> subscriptionConfigsProperty;
    private final @NotNull SimpleObjectProperty<MQTTClientsConfig> parentProperty;

    /**
     * Constructs a new mqtt client config with the given parameters.
     *
     * @param clientHostname      the hostname of the client.
     * @param clientPort          the port of the client.
     * @param brokerHostname      the hostname of the broker.
     * @param brokerPort          the port of the broker.
     * @param username            the username to authenticate with.
     * @param password            the password to authenticate with.
     * @param clientIdentifier    the client identifier to authenticate with.
     * @param subscriptionConfigs the subscription configurations.
     * @param parent              the parent.
     */
    public MQTTClientConfig(@NotNull String clientHostname, @NotNull Integer clientPort,
                            @NotNull String brokerHostname, @NotNull Integer brokerPort, @Nullable String username,
                            @Nullable String password, @Nullable String clientIdentifier,
                            @NotNull List<MQTTSubscriptionConfig> subscriptionConfigs,
                            @Nullable MQTTClientsConfig parent) {
        clientHostnameProperty = new SimpleStringProperty(null, "clientHostname", clientHostname);
        clientPortProperty = new SimpleIntegerProperty(null, "clientPort", clientPort);
        brokerHostnameProperty = new SimpleStringProperty(null, "brokerHostname", brokerHostname);
        brokerPortProperty = new SimpleIntegerProperty(null, "brokerPort", brokerPort);
        usernameProperty = new SimpleStringProperty(null, "username", username);
        passwordProperty = new SimpleStringProperty(null, "password", password);
        clientIdentifierProperty = new SimpleStringProperty(null, "clientIdentifier", clientIdentifier);
        subscriptionConfigsProperty = new SimpleListProperty<>(null, "subscriptionConfigs",
                FXCollections.observableList(subscriptionConfigs));
        parentProperty = new SimpleObjectProperty<>(null, "parent", parent);
    }

    /**
     * Gets the client hostname.
     *
     * @return the client hostname.
     */
    public @NotNull String clientHostname() {
        return clientHostnameProperty.get();
    }

    /**
     * Sets the client hostname.
     *
     * @param clientHostname the new client hostname.
     */
    public void setClientHostname(@NotNull String clientHostname) {
        clientHostnameProperty.set(clientHostname);
    }

    /**
     * Gets the client port.
     *
     * @return the client port.
     */
    public int clientPort() {
        return clientPortProperty.get();
    }

    /**
     * Sets the client port.
     *
     * @param clientPort the new client port.
     */
    public void setClientPort(int clientPort) {
        clientPortProperty.set(clientPort);
    }

    /**
     * Gets the broker hostname.
     *
     * @return the broker hostname.
     */
    public @NotNull String brokerHostname() {
        return brokerHostnameProperty.get();
    }

    /**
     * Sets the broker hostname.
     *
     * @param brokerHostname the new broker hostname.
     */
    public void setBrokerHostname(@NotNull String brokerHostname) {
        brokerHostnameProperty.set(brokerHostname);
    }

    /**
     * Gets the broker port.
     *
     * @return the broker port.
     */
    public int brokerPort() {
        return brokerPortProperty.get();
    }

    /**
     * Sets the broker port.
     *
     * @param brokerPort the new broker port.
     */
    public void setBrokerPort(int brokerPort) {
        brokerPortProperty.set(brokerPort);
    }

    /**
     * Gets the username.
     *
     * @return the username.
     */
    public @Nullable String username() {
        return usernameProperty.get();
    }

    /**
     * Sets the username.
     *
     * @param username the new username.
     */
    public void setUsername(@Nullable String username) {
        usernameProperty.set(username);
    }

    /**
     * Gets the password.
     *
     * @return the password.
     */
    public @Nullable String password() {
        return passwordProperty.get();
    }

    /**
     * Sets the password.
     *
     * @param password the new password.
     */
    public void setPassword(@Nullable String password) {
        passwordProperty.set(password);
    }

    /**
     * Gets the client identifier.
     *
     * @return the client identifier.
     */
    public @Nullable String clientIdentifier() {
        return clientIdentifierProperty.get();
    }

    /**
     * Sets the client identifier.
     *
     * @param clientIdentifier the new client identifier.
     */
    public void setClientIdentifier(@Nullable String clientIdentifier) {
        clientIdentifierProperty.set(clientIdentifier);
    }

    /**
     * Gets the parent.
     *
     * @return the parent.
     */
    public @Nullable MQTTClientsConfig parent() {
        return parentProperty.get();
    }

    /**
     * Sets the parent.
     *
     * @param parent the new parent.
     */
    public void setParent(@Nullable MQTTClientsConfig parent) {
        parentProperty.set(parent);
    }

    /**
     * Gets the client hostname property.
     *
     * @return the client hostname property.
     */
    public @NotNull SimpleStringProperty clientHostnameProperty() {
        return clientHostnameProperty;
    }

    /**
     * Gets the client port property.
     *
     * @return the client port property.
     */
    public @NotNull SimpleIntegerProperty clientPortProperty() {
        return clientPortProperty;
    }

    /**
     * Gets the broker hostname property.
     *
     * @return the broker hostname property.
     */
    public @NotNull SimpleStringProperty brokerHostnameProperty() {
        return brokerHostnameProperty;
    }

    /**
     * Gets the broker port property.
     *
     * @return the broker port property.
     */
    public @NotNull SimpleIntegerProperty brokerPortProperty() {
        return brokerPortProperty;
    }

    /**
     * Gets the username property.
     *
     * @return the username property.
     */
    public @NotNull SimpleStringProperty usernameProperty() {
        return usernameProperty;
    }

    /**
     * Gets the password property.
     *
     * @return the password property.
     */
    public @NotNull SimpleStringProperty passwordProperty() {
        return passwordProperty;
    }

    /**
     * Gets the client identifier property.
     *
     * @return the client identifier property.
     */
    public @NotNull SimpleStringProperty clientIdentifierProperty() {
        return clientIdentifierProperty;
    }

    /**
     * Gets the subscriptions.
     *
     * @return the subscriptions.
     */
    public @NotNull List<MQTTSubscriptionConfig> subscriptionConfigs() {
        return subscriptionConfigsProperty.get();
    }

    /**
     * Gets the subscriptions property.
     *
     * @return the subscriptions property.
     */
    public @NotNull SimpleListProperty<MQTTSubscriptionConfig> subscriptionConfigsProperty() {
        return subscriptionConfigsProperty;
    }

    /**
     * Gets the parent property.
     *
     * @return the parent property.
     */
    public @NotNull SimpleObjectProperty<MQTTClientsConfig> parentProperty() {
        return parentProperty;
    }

    /**
     * Turns the current mqtt client config into an XML element.
     *
     * @param document the document for which the element will be created.
     * @return the created element.
     */
    public @NotNull Element toElement(@NotNull Document document) {
        @NotNull Element element = document.createElement(ELEMENT_TAG_NAME);

        element.setAttribute(CLIENT_HOSTNAME_ATTRIBUTE_NAME, clientHostname());
        element.setAttribute(CLIENT_PORT_ATTRIBUTE_NAME, Integer.toString(clientPort()));
        element.setAttribute(BROKER_HOSTNAME_ATTRIBUTE_NAME, brokerHostname());
        element.setAttribute(BROKER_PORT_ATTRIBUTE_NAME, Integer.toString(brokerPort()));

        if (username() != null) {
            element.setAttribute(USERNAME_ATTRIBUTE_NAME, username());
        }

        if (password() != null) {
            element.setAttribute(PASSWORD_ATTRIBUTE_NAME, password());
        }

        if (clientIdentifier() != null) {
            element.setAttribute(CLIENT_IDENTIFIER_ATTRIBUTE_NAME, clientIdentifier());
        }

        // Creates all the subscription configurations.
        subscriptionConfigs().forEach(mqttSubscriptionConfig ->
                element.appendChild(mqttSubscriptionConfig.toElement(document)));

        return element;
    }

    /**
     * Constructs a new mqtt client config from the given element.
     *
     * @param element the element to construct it from.
     * @return the constructed instance.
     */
    public static @NotNull MQTTClientConfig fromElement(@NotNull Element element) {
        // Make sure that the tag name is correct.
        assert element.getTagName().equals(ELEMENT_TAG_NAME);

        // Gets the client hostname.
        final @NotNull String clientHostname = element.getAttribute(CLIENT_HOSTNAME_ATTRIBUTE_NAME);

        if (clientHostname.isBlank()) {
            throw new RuntimeException("The client hostname cannot be left blank in the configuration");
        }

        // Gets the client port.
        final @NotNull String clientPortString = element.getAttribute(CLIENT_PORT_ATTRIBUTE_NAME);

        if (clientPortString.isBlank()) {
            throw new RuntimeException("The client port cannot be left blank in the configuration");
        }

        int clientPort;
        try {
            clientPort = Integer.parseInt(clientPortString);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("The client port is not a valid integer");
        }

        // Gets the broker hostname.
        final @NotNull String brokerHostname = element.getAttribute(BROKER_HOSTNAME_ATTRIBUTE_NAME);

        if (brokerHostname.isBlank()) {
            throw new RuntimeException("The broker hostname cannot be left blank in the configuration");
        }

        // Gets the broker port.
        final @NotNull String brokerPortString = element.getAttribute(BROKER_PORT_ATTRIBUTE_NAME);

        if (brokerPortString.isBlank()) {
            throw new RuntimeException("The broker port cannot be left blank in the configuration");
        }

        int brokerPort;
        try {
            brokerPort = Integer.parseInt(brokerPortString);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("The broker port is not a valid integer");
        }

        // Gets the username.
        final @Nullable String username = element.getAttribute(USERNAME_ATTRIBUTE_NAME).isBlank()
                ? null
                : element.getAttribute(USERNAME_ATTRIBUTE_NAME);

        // Gets the password.
        final @Nullable String password = element.getAttribute(PASSWORD_ATTRIBUTE_NAME).isBlank()
                ? null
                : element.getAttribute(PASSWORD_ATTRIBUTE_NAME);

        // Gets the client identifier.
        final @Nullable String clientIdentifier = element.getAttribute(CLIENT_IDENTIFIER_ATTRIBUTE_NAME).isBlank()
                ? null
                : element.getAttribute(CLIENT_IDENTIFIER_ATTRIBUTE_NAME);

        // Gets the subscription configuration elements.
        final @NotNull List<Element> subscriptionConfigElements = DXDomUtils.GetChildElementsWithTagName(element,
                MQTTSubscriptionConfig.ELEMENT_TAG_NAME);

        // Parses all the subscription config elements.
        final @NotNull List<MQTTSubscriptionConfig> subscriptionConfigs = subscriptionConfigElements
                .stream()
                .map(MQTTSubscriptionConfig::fromElement)
                .collect(Collectors.toList());

        // Constructs the new mqtt client config.
        final var mqttClientConfig = new MQTTClientConfig(clientHostname, clientPort, brokerHostname, brokerPort, username, password,
                clientIdentifier, subscriptionConfigs, null);

        // Sets the parent of all the subscription configs.
        subscriptionConfigs.forEach(subscriptionConfig -> subscriptionConfig.setParent(mqttClientConfig));

        // Returns the mqtt client config.
        return mqttClientConfig;
    }

    /**
     * Gets the graphic for this config tree item.
     *
     * @return the graphic node.
     */
    @Override
    public Node treeItemGraphic() {
        return new ImageView(ConfiguratorImageCache.instance().read("icons/mqtt_client.png"));
    }

    /**
     * Generates the text that's being used if this config element is put in a tree.
     *
     * @return the text.
     */
    @Override
    public ObservableValue<String> treeItemText() {
        return Bindings.createStringBinding(() -> clientHostname() + ":" + clientPort() + " to " + brokerHostname()
                        + ":" + brokerPort(), clientHostnameProperty(), clientPortProperty(), brokerHostnameProperty(),
                brokerPortProperty());
    }
}
