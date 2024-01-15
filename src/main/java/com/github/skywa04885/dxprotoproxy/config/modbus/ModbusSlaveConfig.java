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

public class ModbusSlaveConfig {
    public static final @NotNull String ELEMENT_TAG_NAME = "ModbusSlave";
    public static final @NotNull String SERVER_ADDRESS_ATTRIBUTE_NAME = "MasterAddress";
    public static final @NotNull String SERVER_PORT_ATTRIBUTE_NAME = "MasterPort";
    public static final @NotNull String CLIENT_ADDRESS_ATTRIBUTE_NAME = "ControllerAddress";
    public static final @NotNull String CLIENT_PORT_ATTRIBUTE_NAME = "ControllerPort";

    private final @NotNull SimpleStringProperty masterAddressProperty;
    private final @NotNull SimpleIntegerProperty masterPortProperty;
    private final @NotNull SimpleStringProperty controllerAddressProperty;
    private final @NotNull SimpleIntegerProperty controllerPortProperty;
    private final @NotNull SimpleObjectProperty<ModbusSlavesConfig> parentProperty;

    public ModbusSlaveConfig(@NotNull String masterAddress, int masterPort,
                             @NotNull String controllerAddress, int controllerPort,
                             @Nullable ModbusSlavesConfig parent) {
        masterAddressProperty = new SimpleStringProperty(null, "masterAddress", masterAddress);
        masterPortProperty = new SimpleIntegerProperty(masterPort);
        controllerAddressProperty = new SimpleStringProperty(null, "controllerAddress", controllerAddress);
        controllerPortProperty = new SimpleIntegerProperty(controllerPort);
        parentProperty = new SimpleObjectProperty<>(null, "parent", parent);
    }

    public @Nullable ModbusSlavesConfig parent() {
        return parentProperty.get();
    }

    public @NotNull String masterAddress() {
        return masterAddressProperty.get();
    }

    public void setMasterAddress(@NotNull String masterAddress) {
        masterAddressProperty.set(masterAddress);
    }

    public @NotNull SimpleStringProperty masterAddressProperty() {
        return masterAddressProperty;
    }

    public int masterPort() {
        return masterPortProperty.get();
    }

    public void setMasterPort(int masterPort) {
        masterPortProperty.set(masterPort);
    }

    public @NotNull SimpleIntegerProperty masterPortProperty() {
        return masterPortProperty;
    }

    public @NotNull String controllerAddress() {
        return controllerAddressProperty.get();
    }

    public void setControllerAddress(@NotNull String controllerAddress) {
        controllerAddressProperty.set(controllerAddress);
    }

    public @NotNull SimpleStringProperty controllerAddressProperty() {
        return controllerAddressProperty;
    }

    public int controllerPort() {
        return controllerPortProperty.get();
    }

    public void setControllerPort(int controllerPort) {
        controllerPortProperty.set(controllerPort);
    }

    public @NotNull SimpleIntegerProperty controllerPortProperty() {
        return controllerPortProperty;
    }

    public void setParent(@Nullable ModbusSlavesConfig parent) {
        parentProperty.set(parent);
    }

    public @NotNull Element toElement(@NotNull Document document) {
        @NotNull Element element = document.createElement(ELEMENT_TAG_NAME);

        element.setAttribute(SERVER_ADDRESS_ATTRIBUTE_NAME, masterAddress());
        element.setAttribute(SERVER_PORT_ATTRIBUTE_NAME, Integer.toString(masterPort()));
        element.setAttribute(CLIENT_ADDRESS_ATTRIBUTE_NAME, controllerAddress());
        element.setAttribute(CLIENT_PORT_ATTRIBUTE_NAME, Integer.toString(controllerPort()));

        return element;
    }

    public static @NotNull ModbusSlaveConfig fromElement(@NotNull Element element) {
        assert element.getTagName().equals(ELEMENT_TAG_NAME);

        final @NotNull String masterAddress = element.getAttribute(SERVER_ADDRESS_ATTRIBUTE_NAME).trim();
        final @NotNull String masterPortString = element.getAttribute(SERVER_PORT_ATTRIBUTE_NAME).trim();
        final @NotNull String controllerAddress = element.getAttribute(CLIENT_ADDRESS_ATTRIBUTE_NAME).trim();
        final @NotNull String controllerPortString = element.getAttribute(CLIENT_PORT_ATTRIBUTE_NAME).trim();

        if (masterAddress.isEmpty()) throw new RuntimeException("The master address cannot be left empty");
        if (masterPortString.isEmpty()) throw new RuntimeException("The master port cannot be left empty");
        if (controllerAddress.isEmpty()) throw new RuntimeException("The controller address cannot be left empty");
        if (controllerPortString.isEmpty()) throw new RuntimeException("The controller port cannot be left empty");

        int masterPort;
        try {
            masterPort = Integer.parseInt(masterPortString);
        } catch (@NotNull NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid master port");
        }

        int controllerPort;
        try {
            controllerPort = Integer.parseInt(controllerPortString);
        } catch (@NotNull NumberFormatException numberFormatException) {
            throw new RuntimeException("Invalid controller port");
        }

        return new ModbusSlaveConfig(masterAddress, masterPort, controllerAddress, controllerPort, null);
    }

}
