package com.github.skywa04885.dxprotoproxy.config.modbus;

import com.github.skywa04885.dxprotoproxy.IDXTreeItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ModbusMasterConfig {
    public static final @NotNull String ELEMENT_TAG_NAME = "ModbusMaster";
    public static final @NotNull String SERVER_ADDRESS_ATTRIBUTE_NAME = "ServerAddress";
    public static final @NotNull String SERVER_PORT_ATTRIBUTE_NAME = "ServerPort";
    public static final @NotNull String LISTEN_ADDRESS_ATTRIBUTE_NAME = "ListenAddress";
    public static final @NotNull String LISTEN_PORT_ATTRIBUTE_NAME = "ListenPort";

    private final @NotNull SimpleStringProperty serverAddressProperty;
    private final @NotNull SimpleIntegerProperty serverPortProperty;
    private final @NotNull SimpleStringProperty listenAddressProperty;
    private final @NotNull SimpleIntegerProperty listenPortProperty;
    private final @NotNull SimpleObjectProperty<ModbusMastersConfig> parentProperty;

    public ModbusMasterConfig(@NotNull String serverAddress, int serverPort,
                              @NotNull String listenAddress, int listenPort,
                              @Nullable ModbusMastersConfig parent) {
        serverAddressProperty = new SimpleStringProperty(null, "serverAddress", serverAddress);
        serverPortProperty = new SimpleIntegerProperty(serverPort);
        listenAddressProperty = new SimpleStringProperty(null, "listenAddress", listenAddress);
        listenPortProperty = new SimpleIntegerProperty(listenPort);
        parentProperty = new SimpleObjectProperty<>(parent);
    }

    public @NotNull String serverAddress() {
        return serverAddressProperty.get();
    }

    public void setServerAddress(@NotNull String serverAddress) {
        serverAddressProperty.set(serverAddress);
    }

    public @NotNull SimpleStringProperty serverAddressProperty() {
        return serverAddressProperty;
    }

    public int serverPort() {
        return serverPortProperty.get();
    }

    public void setServerPort(int serverPort) {
        serverPortProperty.set(serverPort);
    }

    public @NotNull SimpleIntegerProperty serverPortProperty() {
        return serverPortProperty;
    }

    public @NotNull String listenAddress() {
        return listenAddressProperty.get();
    }

    public void setListenAddress(@NotNull String listenAddress) {
        listenAddressProperty.set(listenAddress);
    }

    public @NotNull SimpleStringProperty listenAddressProperty() {
        return listenAddressProperty;
    }

    public int listenPort() {
        return listenPortProperty.get();
    }

    public void setListenPort(int listenPort) {
        listenPortProperty.set(listenPort);
    }

    public @NotNull SimpleIntegerProperty listenPortProperty() {
        return listenPortProperty;
    }

    public @Nullable ModbusMastersConfig parent() {
        return parentProperty.get();
    }

    public void setParent(@Nullable ModbusMastersConfig parent) {
        parentProperty.set(parent);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        @NotNull Element element = document.createElement(ELEMENT_TAG_NAME);

        element.setAttribute(SERVER_ADDRESS_ATTRIBUTE_NAME, serverAddress());
        element.setAttribute(SERVER_PORT_ATTRIBUTE_NAME, Integer.toString(serverPort()));
        element.setAttribute(LISTEN_ADDRESS_ATTRIBUTE_NAME, listenAddress());
        element.setAttribute(LISTEN_PORT_ATTRIBUTE_NAME, Integer.toString(listenPort()));

        return element;
    }

    public static @NotNull ModbusMasterConfig fromElement(@NotNull Element element) {
        assert element.getTagName().equals(ELEMENT_TAG_NAME);

        final @NotNull String serverAddress = element.getAttribute(SERVER_ADDRESS_ATTRIBUTE_NAME).trim();
        final @NotNull String serverPortString = element.getAttribute(SERVER_PORT_ATTRIBUTE_NAME).trim();
        final @NotNull String listenAddress = element.getAttribute(LISTEN_ADDRESS_ATTRIBUTE_NAME).trim();
        final @NotNull String listenPortString = element.getAttribute(LISTEN_PORT_ATTRIBUTE_NAME).trim();

        if (serverAddress.isEmpty()) throw new RuntimeException("The server address cannot be left empty");
        if (serverPortString.isEmpty()) throw new RuntimeException("The server port cannot be left empty");
        if (listenAddress.isEmpty()) throw new RuntimeException("The listen address cannot be left empty");
        if (listenPortString.isEmpty()) throw new RuntimeException("The listen port cannot be left empty");

        int serverPort;
        try {
            serverPort = Integer.parseInt(serverPortString);
        } catch (@NotNull NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid server port");
        }

        int listenPort;
        try {
            listenPort = Integer.parseInt(listenPortString);
        } catch (@NotNull NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid listen port");
        }

        return new ModbusMasterConfig(serverAddress, serverPort, listenAddress, listenPort, null);
    }
}
